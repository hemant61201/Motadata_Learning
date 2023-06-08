package main

func executeDiscovery(ip string, username string, password string) string {

	_, err := executeSSHCommand(ip, username, password)

	if err != nil {

		//log.Fatal("Failed to execute SSH command:", err)
		return "failed_"
	}

	return "success_"
}
