package SSH

type Discovery struct{}

func (d Discovery) ExecuteDiscovery(ip []string, username string, password string) string {

	for _, ip := range ip {

		_, err := ExecuteSSHCommand(ip, username, password)

		if err != nil {

			//log.Fatal("Failed to execute SSH command:", err)
			return "failed_"
		}

	}

	return "success_"
}
