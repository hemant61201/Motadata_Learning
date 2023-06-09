package main

import (
	"encoding/json"
	"fmt"
	"os"
	"plugins/functions"
	"runtime"
)

type CredentialProfile struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

type DiscoveryProfile struct {
	IP   string `json:"ip"`
	Port int    `json:"port"`
	ID   []int  `json:"id"`
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

	runtime.GOMAXPROCS(runtime.NumCPU())

	var inputData InputData

	err := json.Unmarshal([]byte(jsonData), &inputData)

	if err != nil {

		fmt.Println("Failed to parse JSON data:", err)

		return
	}

	switch inputData.Operation {

	case "SSH":

		result := functions.PerformfpingDiscovery(inputData.DiscoveryProfile.IP)

		if result == "success" {

			fmt.Println(functions.ExecuteDiscovery(inputData.DiscoveryProfile.IP, inputData.CredentialProfile.Username, inputData.CredentialProfile.Password), inputData.DiscoveryProfile.ID)

		} else {
			fmt.Println("failed_", inputData.DiscoveryProfile.ID)
		}

	case "Ping":

		switch inputData.Method {

		case "Discovery":

			result := functions.PerformfpingDiscovery(inputData.DiscoveryProfile.IP)

			fmt.Println(result+"_", inputData.DiscoveryProfile.ID)

		case "Polling":

			results, err := functions.PerformfpingPolling(jsonData)

			if err != nil {
				fmt.Println("Error:", err)
				return
			}

			for id, result := range results {
				fmt.Printf("%d: %+v\n", id, result)
			}

		}

	default:

		fmt.Println("Invalid operation:", inputData.Operation)
	}
}
