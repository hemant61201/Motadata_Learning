package main

import (
	"os/exec"
)

func performFping(ip string) string {

	cmd := exec.Command("fping", "-c", "3", ip)

	_, err := cmd.CombinedOutput()

	if err != nil {
		//log.Fatal("Failed to execute fping command:", err)

		return "failed"
	}

	return "success"
}
