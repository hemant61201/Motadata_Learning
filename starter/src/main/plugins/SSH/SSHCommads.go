package SSH

import (
	"fmt"
	"golang.org/x/crypto/ssh"
	"time"
)

func ExecuteSSHCommand(ip string, username string, password string) (string, error) {

	config := &ssh.ClientConfig{

		User: username,

		Auth: []ssh.AuthMethod{
			ssh.Password(password),
		},

		Timeout: 2 * time.Second,

		HostKeyCallback: ssh.InsecureIgnoreHostKey(),
	}

	client, err := ssh.Dial("tcp", ip+":22", config)

	if err != nil {

		return "", fmt.Errorf("failed to connect to SSH server: %v", err)

	}

	defer client.Close()

	session, err := client.NewSession()

	if err != nil {

		return "", fmt.Errorf("failed to create SSH session: %v", err)

	}

	defer session.Close()

	output, err := session.CombinedOutput("ifconfig")

	if err != nil {

		return "", fmt.Errorf("failed to execute command: %v", err)

	}

	return string(output), nil
}
