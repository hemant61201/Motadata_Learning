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
		IP   string `json:"ip"`
		Port int    `json:"port"`
		IDs  []int  `json:"id"`
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

func PerformfpingDiscovery(ip string) string {

	cmd := exec.Command("fping", "-c", "3", ip)

	_, err := cmd.CombinedOutput()

	if err != nil {
		return "failed"
	}

	return "success"
}

func PerformfpingPolling(requestJSON string) ([]Result, error) {

	var request DiscoveryRequest

	err := json.Unmarshal([]byte(requestJSON), &request)

	if err != nil {
		return nil, fmt.Errorf("Failed to parse JSON data: %v", err)
	}

	ips := strings.Split(request.DiscoveryProfile.IP, ",")

	ids := request.DiscoveryProfile.IDs

	results := make([]Result, len(ids))

	var mutex sync.Mutex

	var wg sync.WaitGroup

	wg.Add(len(ips))

	worker := func(ip string, id int) {

		defer wg.Done()

		result := performPing(ip)

		result.ID = id

		mutex.Lock()

		defer mutex.Unlock()

		results[id-1] = result
	}

	for i, ip := range ips {

		id := ids[i%len(ids)]

		go worker(ip, id)
	}

	wg.Wait()

	return results, nil
}

func performPing(ip string) Result {

	command := fmt.Sprintf("fping -c 3 -q %s", ip)

	cmd := exec.Command("bash", "-c", command)

	output, err := cmd.CombinedOutput()

	if err != nil {

		return Result{
			Loss:   "100%",
			Min:    "0",
			Avg:    "0",
			Max:    "0",
			Status: "failed",
			IP:     ip,
		}

	}

	lines := strings.Split(string(output), "\n")

	for _, line := range lines {

		line = strings.TrimSpace(line)

		if line == "" || strings.Contains(line, "failed") {
			continue
		}

		r := regexp.MustCompile(`(?m)^([^:]+)\s+:\s+xmt/rcv/%loss = (\d+)/(\d+)/(\d+)%,\s+min/avg/max = ([\d.]+)/([\d.]+)/([\d.]+)$`)

		matches := r.FindStringSubmatch(line)

		if len(matches) != 9 {
			continue
		}

		_ = matches[1]
		loss := matches[3]
		min := matches[6]
		avg := matches[7]
		max := matches[8]

		status := "success"

		if lossPercentage, err := strconv.Atoi(loss); err == nil && lossPercentage >= 50 {
			status = "failed"
		}

		return Result{
			Loss:   loss + "%",
			Min:    min,
			Avg:    avg,
			Max:    max,
			Status: status,
			IP:     ip,
		}
	}

	return Result{
		Loss:   "100%",
		Min:    "0",
		Avg:    "0",
		Max:    "0",
		Status: "failed",
		IP:     ip,
	}
}
