package main

import (
	"encoding/json"
	"fmt"
	"os"
)

type CredentialProfile struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

type DiscoveryProfile struct {
	IP   string `json:"ip"`
	Port int    `json:"port"`
	ID   int    `json:"id"`
}

type InputData struct {
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

		result := performFping(inputData.DiscoveryProfile.IP)

		if result == "success" {

			fmt.Println(executeDiscovery(inputData.DiscoveryProfile.IP, inputData.CredentialProfile.Username, inputData.CredentialProfile.Password), inputData.DiscoveryProfile.ID)

		} else {
			fmt.Println("failed_", inputData.DiscoveryProfile.ID)
		}

	case "Ping":

		result := performFping(inputData.DiscoveryProfile.IP)

		fmt.Println(result+"_", inputData.DiscoveryProfile.ID)

	default:

		fmt.Println("Invalid operation:", inputData.Operation)
	}
}
