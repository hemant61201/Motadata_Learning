package main

import (
	"encoding/json"
	"fmt"
	"os"
	"plugins/Ping"
	"plugins/SSH"
)

type CredentialProfile struct {
	Username string `json:"username"`
	Password string `json:"password"`
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

		ping := &Ping.Ping{}

		discovery := &SSH.Discovery{}

		result := ping.PerformfpingDiscovery(inputData.DiscoveryProfile.IP)

		if result == "success" {

			fmt.Println(discovery.ExecuteDiscovery(inputData.DiscoveryProfile.IP, inputData.CredentialProfile.Username, inputData.CredentialProfile.Password), inputData.DiscoveryProfile.ID)

		} else {
			fmt.Println("failed_", inputData.DiscoveryProfile.ID)
		}

	case "Ping":

		switch inputData.Method {

		case "Discovery":

			ping := &Ping.Ping{}

			result := ping.PerformfpingDiscovery(inputData.DiscoveryProfile.IP)

			fmt.Println(result+"_", inputData.DiscoveryProfile.ID)

		case "Polling":

			ping := &Ping.Ping{}

			results, err := ping.PerformfpingPolling(jsonData)

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
