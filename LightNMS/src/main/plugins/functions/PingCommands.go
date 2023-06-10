package functions

import (
	"encoding/json"
	"fmt"
	"os/exec"
	"regexp"
	"strconv"
	"strings"
	"sync"
)

type DiscoveryRequest struct {
	Operation         string `json:"Operation"`
	CredentialProfile struct {
		Username string `json:"username"`
		Password string `json:"password"`
	} `json:"credentialProfile"`
	DiscoveryProfile struct {
		IP   []string `json:"ip"`
		Port int      `json:"port"`
		IDs  []int    `json:"id"`
	} `json:"discoveryProfile"`
}

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

func PerformfpingDiscovery(ip []string) string {

	for _, ip := range ip {

		cmd := exec.Command("fping", "-c", "3", ip)

		_, err := cmd.CombinedOutput()

		if err != nil {
			return "failed"
		}

	}

	return "success"
}

func PerformfpingPolling(requestJSON string) (map[string]Result, error) {
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

	resultMap := make(map[string]Result)

	for i, ip := range ips {
		command := fmt.Sprintf("fping -c 3 -q %s", ip)
		cmd := exec.Command("bash", "-c", command)
		output, err := cmd.CombinedOutput()

		if err != nil {
			result := Result{
				Loss:   "100%",
				Min:    "0",
				Avg:    "0",
				Max:    "0",
				Status: "failed",
				IP:     ip,
				ID:     ids[i],
			}

			resultMap[ip] = result
			continue
		}

		lines := strings.Split(string(output), "\n")
		successfulOutput := false

		for _, line := range lines {
			line = strings.TrimSpace(line)

			if line == "" || strings.Contains(line, "failed") {
				continue
			}

			r := regexp.MustCompile(`(?m)^([^:]+)\s+:\s+xmt/rcv/%loss = (\d+)/(\d+)/(\d+)%,\s+min/avg/max = ([\d.]+)/([\d.]+)/([\d.]+)$`)
			matches := r.FindStringSubmatch(line)

			if len(matches) != 8 {
				continue
			}

			loss := matches[4]
			min := matches[5]
			avg := matches[6]
			max := matches[7]

			status := "success"
			if lossPercentage, err := strconv.Atoi(loss); err == nil && lossPercentage >= 50 {
				status = "failed"
			}

			result := Result{
				Loss:   loss + "%",
				Min:    min,
				Avg:    avg,
				Max:    max,
				Status: status,
				IP:     ip,
				ID:     ids[i],
			}

			resultMap[ip] = result
			successfulOutput = true
			break
		}

		if !successfulOutput {
			result := Result{
				Loss:   "0%",
				Min:    "0",
				Avg:    "0",
				Max:    "0",
				Status: "failed",
				IP:     ip,
				ID:     ids[i],
			}

			resultMap[ip] = result
		}
	}

	return resultMap, nil
}
