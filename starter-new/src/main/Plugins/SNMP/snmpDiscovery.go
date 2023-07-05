package SNMP

import (
	"time"

	g "github.com/gosnmp/gosnmp"
)

type Discovery struct{}

func (d Discovery) ExecuteDiscovery(ip []string, port int) string {

	params := &g.GoSNMP{
		Target:    ip[0],
		Port:      uint16(port),
		Community: "public",
		Version:   g.Version2c,
		Timeout:   time.Duration(2) * time.Second,
	}

	err := params.Connect()

	if err != nil {
		return "failed_"
	}

	defer params.Conn.Close()

	oid := []string{"1.3.6.1.2.1.1.5.0"}

	result, err2 := params.Get(oid)

	if err2 != nil {
		return "failed_"
	}

	for _, variable := range result.Variables {

		switch variable.Type {

		case g.OctetString:
			name := string(variable.Value.([]byte))

			if name != "" {
				return "success_"
			}
		}
	}

	return "failed_"
}
