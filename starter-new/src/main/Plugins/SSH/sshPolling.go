package SSH

import (
	"bytes"
	"encoding/json"
	"fmt"
	"golang.org/x/crypto/ssh"
	"strings"
	"time"
)

type CredentialProfile struct {
	Username []string `json:"username"`
	Password []string `json:"password"`
}

type DiscoveryProfile struct {
	IPs  []string `json:"ip"`
	Port int      `json:"port"`
	IDs  []int    `json:"id"`
}

type DiscoveryRequest struct {
	CredentialProfile CredentialProfile `json:"credentialProfile"`
	DiscoveryProfile  DiscoveryProfile  `json:"discoveryProfile"`
}

type Result struct {
	Loss   string `json:"Loss"`
	Min    string `json:"Min"`
	Avg    string `json:"Avg"`
	Max    string `json:"Max"`
	Status string `json:"Status"`
}

type SSHResult struct {
	IP       string `json:"IP"`
	CPU      string `json:"CPU"`
	Memory   string `json:"Memory"`
	Disk     string `json:"Disk"`
	Uptime   string `json:"Uptime"`
	BpsValue string `json:"BpsValue"`
	Fping    Result `json:"Fping"`
}

type SshPolling struct{}

func (p SshPolling) GetSSHResult(ip string, id int, requestJSON string) (SSHResult, error) {

	defer func() {
		if r := recover(); r != nil {
		}
	}()

	var request DiscoveryRequest

	err := json.Unmarshal([]byte(requestJSON), &request)

	if err != nil {
		return SSHResult{}, fmt.Errorf("failed to unmarshal JSON: %v", err)
	}

	creds := request.CredentialProfile

	username := creds.Username[id]

	password := creds.Password[id]

	config := &ssh.ClientConfig{
		User: username,
		Auth: []ssh.AuthMethod{
			ssh.Password(password),
		},
		Timeout:         10 * time.Second,
		HostKeyCallback: ssh.InsecureIgnoreHostKey(),
	}

	client, err := ssh.Dial("tcp", ip+":22", config)

	if err != nil {
		return SSHResult{}, fmt.Errorf("failed to connect to SSH server: %v", err)
	}

	defer client.Close()

	result := SSHResult{
		IP: ip,
	}

	session, err := client.NewSession()

	if err != nil {
		return result, fmt.Errorf("failed to create SSH session: %v", err)
	}

	defer session.Close()

	combinedCommand := `
		cpu_usage=$(top -bn1 | grep 'Cpu(s)' | awk '{print $2+$4}')
		memory_usage=$(free -m | awk 'NR==2{printf "%.2f%%", ($3/$2)*100 }')
		disk_usage=$(df -h --output=pcent / | awk 'NR==2{print $1}')
		uptime=$(uptime -p)
		bps=$(cat /proc/net/dev | awk 'NR>2 gsub(/:/,"") {printf $1 ":" $2 "," $10 ";"}')
		echo "$cpu_usage|$memory_usage|$disk_usage|$uptime|$bps"
	`

	output, err := executeCommand(session, combinedCommand)

	if err != nil {
		return result, fmt.Errorf("failed to retrieve system information: %v", err)
	}

	// Extract the individual values from the combined output
	fields := strings.Split(output, "|")

	if len(fields) != 5 {
		return result, fmt.Errorf("unexpected output format: %s", output)
	}

	result.CPU = fields[0] + "%"

	result.Memory = fields[1]

	result.Disk = fields[2]

	result.Uptime = fields[3]

	result.BpsValue = fields[4]

	return result, nil
}

func executeCommand(session *ssh.Session, command string) (string, error) {

	var stdoutBuf, stderrBuf bytes.Buffer

	session.Stdout = &stdoutBuf

	session.Stderr = &stderrBuf

	err := session.Run(command)

	if err != nil {
		return "", fmt.Errorf("failed to execute command '%s': %v", command, err)
	}

	output := strings.TrimSpace(stdoutBuf.String())

	if output == "" {
		output = strings.TrimSpace(stderrBuf.String())
	}

	return output, nil
}
