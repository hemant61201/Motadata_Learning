package main

import (
	"awesomeProject2/Ping"
	"awesomeProject2/SSH"
	"encoding/json"
	"fmt"
	"os"
	"sync"
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

	if len(os.Args) < 2 {

		fmt.Println("Please provide the JSON data as a command-line argument")

		return
	}

	jsonData := os.Args[1]

	var inputData InputData

	err := json.Unmarshal([]byte(jsonData), &inputData)

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

			result := ping.PingDiscovery(inputData.DiscoveryProfile.IP)

			if result == "success" {

				fmt.Println(discovery.ExecuteDiscovery(inputData.DiscoveryProfile.IP, inputData.CredentialProfile.Username[0], inputData.CredentialProfile.Password[0]), inputData.DiscoveryProfile.ID)

			} else {
				fmt.Println("failed_", inputData.DiscoveryProfile.ID)
			}

		case "Polling":

			sshPing := &Ping.SshPing{}

			resultMap, err := sshPing.SshPingPolling(jsonData)
			if err != nil {
				fmt.Printf("Error performing fping polling: %v\n", err)
				return
			}

			resultsCh := make(chan SSH.SSHResult)

			var wg sync.WaitGroup
			for i, ip := range resultMap.IPs {
				//id := resultMap.IDs[i]
				wg.Add(1)
				go func(ip string, i int) {
					defer wg.Done()
					sshPolling := &SSH.SshPolling{}
					sshResult, err := sshPolling.GetSSHResult(ip, i, jsonData)
					if err != nil {
						fmt.Printf("Error retrieving SSH result for IP %s: %v\n", ip, err)
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

					resultsCh <- sshResult
				}(ip, i)
			}

			go func() {
				wg.Wait()
				close(resultsCh)
			}()

			results := make([]SSH.SSHResult, 0)
			for sshResult := range resultsCh {
				results = append(results, sshResult)
			}

			jsonData, err := json.Marshal(results)
			if err != nil {
				fmt.Printf("Error marshaling JSON: %v\n", err)
				return
			}

			fmt.Println(string(jsonData))
		}

	case "Ping":

		switch inputData.Method {

		case "Discovery":

			ping := &Ping.Ping{}

			result := ping.PingDiscovery(inputData.DiscoveryProfile.IP)

			fmt.Println(result+"_", inputData.DiscoveryProfile.ID)

		case "Polling":

			ping := &Ping.Ping{}

			results, err := ping.PingPolling(jsonData)

			if err != nil {
				fmt.Println("Error:", err)
				return
			}

			for _, ip := range inputData.DiscoveryProfile.IP {
				result := results[ip]
				fmt.Printf("{Loss:%s Min:%s Avg:%s Max:%s Status:%s IP:%s ID:%d}\n", result.Loss, result.Min, result.Avg, result.Max, result.Status, result.IP, result.ID)
			}

		}

	default:

		fmt.Println("Invalid operation:", inputData.Operation)
	}
}
