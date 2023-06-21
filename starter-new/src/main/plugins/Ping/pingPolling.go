package Ping

import (
	"encoding/json"
	"fmt"
	"os/exec"
	"regexp"
	"strconv"
	"strings"
	"sync"
)

type Result struct {
	Loss   string `json:"%loss"`
	Min    string `json:"min"`
	Avg    string `json:"avg"`
	Max    string `json:"max"`
	Status string `json:"status"`
	IP     string `json:"ip"`
	ID     int    `json:"ID"`
}

type SafeResults struct {
	sync.Mutex
	Results []Result
}

type Ping struct{}

func (p Ping) PingDiscovery(ip []string) string {

	for _, ip := range ip {

		cmd := exec.Command("fping", "-c", "3", ip)

		_, err := cmd.CombinedOutput()

		if err != nil {
			return "failed"
		}

	}

	return "success"
}

func (p Ping) PingPolling(requestJSON string) (map[string]Result, error) {
	var request DiscoveryRequest

	err := json.Unmarshal([]byte(requestJSON), &request)
	if err != nil {
		return nil, fmt.Errorf("failed to unmarshal JSON: %v", err)
	}

	ips := request.DiscoveryProfile.IP
	ids := request.DiscoveryProfile.IDs

	if len(ips) != len(ids) {
		return nil, fmt.Errorf("number of IPs and IDs do not match")
	}

	ipsStr := strings.Join(ips, " ")

	command := fmt.Sprintf("fping -c 3 -q %s", ipsStr)

	cmd := exec.Command("bash", "-c", command)

	output, err := cmd.CombinedOutput()

	if err != nil {
	}

	lines := strings.Split(string(output), "\n")

	resultMap := make(map[string]Result)

	for i, line := range lines {
		line = strings.TrimSpace(line)

		if line == "" {
			continue
		}

		r := regexp.MustCompile(`(?m)^([^:]+)\s+:\s+xmt/rcv/%loss = (\d+)/(\d+)/(\d+)%,\s+min/avg/max = ([\d.]+)/([\d.]+)/([\d.]+)$`)

		matches := r.FindStringSubmatch(line)

		ip := ips[i]
		result := Result{
			IP: ip,
			ID: ids[i],
		}

		if len(matches) != 8 {
			result.Loss = "100%"
			result.Min = "0"
			result.Avg = "0"
			result.Max = "0"
			result.Status = "failed"
		} else {
			loss := matches[4]
			min := matches[5]
			avg := matches[6]
			max := matches[7]

			status := "success"
			if lossPercentage, err := strconv.Atoi(loss); err == nil && lossPercentage == 100 {
				status = "failed"
			}

			result.Loss = loss + "%"
			result.Min = min
			result.Avg = avg
			result.Max = max
			result.Status = status
		}

		resultMap[ip] = result
	}

	return resultMap, nil
}
