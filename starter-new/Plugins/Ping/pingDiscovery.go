package Ping

import (
	"os/exec"
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

	defer func() {
		if r := recover(); r != nil {
		}
	}()

	for _, ip := range ip {

		cmd := exec.Command("fping", "-c", "3", ip)

		_, err := cmd.CombinedOutput()

		if err != nil {
			return "failed"
		}

	}

	return "success"
}
