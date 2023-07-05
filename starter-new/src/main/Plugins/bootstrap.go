package main

import (
	"Plugins/Ping"
	"Plugins/SNMP"
	"Plugins/SSH"
	"encoding/json"
	"fmt"
	"os"
)

type CredentialProfile struct {
	Username []string `json:"username"`
	Password []string `json:"password"`
}

type DiscoveryProfile struct {
	IP   []string `json:"ip"`
	Port int      `json:"port"`
	ID   []int    `json:"id"`
}

type InputData struct {
	Method            string            `json:"Method"`
	Operation         string            `json:"Operation"`
	CredentialProfile CredentialProfile `json:"credentialProfile"`
	DiscoveryProfile  DiscoveryProfile  `json:"discoveryProfile"`
}

func main() {

	defer func() {
		if r := recover(); r != nil {
		}
	}()

	if len(os.Args) < 2 {

		fmt.Println("Please provide the JSON data as a command-line argument")

		return
	}

	input := os.Args[1]

	var inputData InputData

	err := json.Unmarshal([]byte(input), &inputData)

	if err != nil {

		fmt.Println("Failed to parse JSON data:", err)

		return
	}

	switch inputData.Operation {

	case "SSH":

		switch inputData.Method {

		case "Discovery":

			ping := &Ping.Ping{}

			discovery := &SSH.Discovery{}

			discoveryResult := ping.PingDiscovery(inputData.DiscoveryProfile.IP)

			if discoveryResult == "success" {

				fmt.Println(discovery.ExecuteDiscovery(inputData.DiscoveryProfile.IP, inputData.CredentialProfile.Username[0], inputData.CredentialProfile.Password[0]), inputData.DiscoveryProfile.ID)

			} else {
				fmt.Println("failed_", inputData.DiscoveryProfile.ID)
			}

		case "Polling":

			sshPing := &Ping.SshPing{}

			resultMap, err := sshPing.PingPolling(input)

			if err != nil {
				fmt.Printf("Error performing fping polling: %v\n", err)
				return
			}

			size := len(resultMap.IPs)

			resultsChanel := make(chan SSH.SSHResult, size)

			completed := 0

			for i, ip := range resultMap.IPs {

				go func(ip string, i int) {

					sshPolling := &SSH.SshPolling{}

					sshResult, err := sshPolling.GetSSHResult(ip, i, input)

					if err != nil {
						fmt.Printf("Error retrieving SSH result for IP %s: %v\n", ip, err)
						resultsChanel <- SSH.SSHResult{} // Send an empty result to indicate failure
						return
					}

					result := resultMap.Results[ip]

					sshResult.Fping = SSH.Result{
						Loss:   result.Loss,
						Min:    result.Min,
						Avg:    result.Avg,
						Max:    result.Max,
						Status: result.Status,
					}

					resultsChanel <- sshResult

				}(ip, i)
			}

			for sshResult := range resultsChanel {

				if sshResult.IP != "" {

					pollingResult, err := json.Marshal(sshResult)

					if err != nil {
						fmt.Printf("Error marshaling JSON: %v\n", err)
						return
					}

					fmt.Println(string(pollingResult))
				}

				completed++

				if completed == size {
					close(resultsChanel)
				}

			}

		}

	case "Ping":

		switch inputData.Method {

		case "Discovery":

			ping := &Ping.Ping{}

			discoveryResult := ping.PingDiscovery(inputData.DiscoveryProfile.IP)

			fmt.Println(discoveryResult+"_", inputData.DiscoveryProfile.ID)

		case "Polling":

			sshPing := &Ping.SshPing{}

			resultMap, err := sshPing.PingPolling(input)

			if err != nil {
				fmt.Printf("Error performing fping polling: %v\n", err)
				return
			}

			fmt.Println(resultMap)

		}

	case "SNMP":

		switch inputData.Method {

		case "Discovery":

			ping := &Ping.Ping{}

			discovery := &SNMP.Discovery{}

			result := ping.PingDiscovery(inputData.DiscoveryProfile.IP)

			if result == "success" {

				fmt.Println(discovery.ExecuteDiscovery(inputData.DiscoveryProfile.IP, inputData.DiscoveryProfile.Port), inputData.DiscoveryProfile.ID)

			} else {
				fmt.Println("failed_", inputData.DiscoveryProfile.ID)
			}

		case "Polling":

			snmpPing := &Ping.SshPing{}

			resultMap, err := snmpPing.PingPolling(input)

			if err != nil {
				fmt.Printf("Error performing fping polling: %v\n", err)
				return
			}

			size := len(resultMap.IPs)

			resultsCh := make(chan SNMP.SNMPResult, size)

			completed := 0

			for i, ip := range resultMap.IPs {

				go func(ip string, i int) {

					snmpPolling := &SNMP.SNMPPolling{}

					snmpResult, err := snmpPolling.GetSNMPResult(ip, inputData.DiscoveryProfile.Port)

					if err != nil {
						fmt.Printf("Error retrieving SNMP result for IP %s: %v\n", ip, err)
						resultsCh <- SNMP.SNMPResult{} // Send an empty result to indicate failure
						return
					}

					result := resultMap.Results[ip]

					snmpResult.Fping = SNMP.Result{
						Loss:   result.Loss,
						Min:    result.Min,
						Avg:    result.Avg,
						Max:    result.Max,
						Status: result.Status,
					}

					resultsCh <- snmpResult
				}(ip, i)
			}

			for snmpResult := range resultsCh {

				if snmpResult.IP != "" {

					pollingData, err := json.Marshal(snmpResult)

					if err != nil {
						fmt.Printf("Error marshaling JSON: %v\n", err)
						return
					}

					fmt.Println(string(pollingData))
				}

				completed++

				if completed == size {
					close(resultsCh)
				}

			}

		}

	default:

		fmt.Println("Invalid operation:", inputData.Operation)
	}
}
