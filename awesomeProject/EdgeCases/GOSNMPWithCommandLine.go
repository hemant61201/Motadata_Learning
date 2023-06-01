package main

import (
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
	"time"

	g "github.com/gosnmp/gosnmp"
)

func getOid(Target string, port uint64, done chan bool) {

	params := &g.GoSNMP{
		Target:    Target,
		Port:      uint16(port),
		Community: "public",
		Version:   g.Version2c,
		Timeout:   time.Duration(2) * time.Second,
	}

	err := params.Connect()

	if err != nil {
		log.Fatalf("Connect() err: %v", err)
	}

	defer params.Conn.Close()

	oids := []string{"1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.1.3.0", "1.3.6.1.2.1.1.2.0", "1.3.6.1.2.1.1.1.0"}

	result, err2 := params.Get(oids)

	if err2 != nil {
		log.Fatalf("Get() err: %v", err2)
	}

	fmt.Println("Target : ", Target)

	for i, variable := range result.Variables {

		fmt.Printf("%d: oid: %s ", i, variable.Name)

		switch variable.Type {

		case g.OctetString:
			fmt.Printf("string: %s\n", string(variable.Value.([]byte)))

		default:
			fmt.Printf("number: %d\n", g.ToBigInt(variable.Value))
		}
	}

	done <- true
}

func main() {

	done := make(chan bool)

	envTarget := os.Getenv("GOSNMP_TARGET")

	envPort := os.Getenv("GOSNMP_PORT")

	if len(envTarget) <= 0 {
		log.Fatalf("environment variable not set: GOSNMP_TARGET")
	}

	if len(envPort) <= 0 {
		log.Fatalf("environment variable not set: GOSNMP_PORT")
	}

	port, _ := strconv.ParseUint(envPort, 10, 16)

	envTargets := strings.Split(envTarget, ",")

	for _, v := range envTargets {
		go getOid(v, port, done)

		<-done
	}

}
