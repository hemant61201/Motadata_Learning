package SNMP

import (
	"Plugins/Constants"
	"fmt"
	g "github.com/gosnmp/gosnmp"
	"net"
	"strings"
	"time"
)

const (
	INTERFACE = "interface"
	SYSTEM    = "system"
)

type DiscoveryProfile struct {
	IPs  []string `json:"ip"`
	Port int      `json:"port"`
	IDs  []int    `json:"id"`
}

type Result struct {
	Loss   string `json:"Loss"`
	Min    string `json:"Min"`
	Avg    string `json:"Avg"`
	Max    string `json:"Max"`
	Status string `json:"Status"`
}

type SNMPResult struct {
	IP       string                 `json:"IP"`
	SNMPData map[string]interface{} `json:"SNMPData"`
	Fping    Result                 `json:"Fping"`
}

type SNMPPolling struct{}

func (p SNMPPolling) GetSNMPResult(ip string, port int) (SNMPResult, error) {

	params := &g.GoSNMP{
		Target:    ip,
		Port:      uint16(port),
		Community: "public",
		Version:   g.Version2c,
		Timeout:   time.Duration(2) * time.Second,
	}

	snmpCollect := &Collect{}

	result := SNMPResult{

		IP: ip,

		SNMPData: snmpCollect.Collect(params),
	}

	if message, ok := result.SNMPData["message"].(string); ok && message != "" {
		return SNMPResult{}, fmt.Errorf("%s", message)
	}

	return result, nil
}

type Collect struct{}

func (c Collect) Collect(snmp *g.GoSNMP) map[string]interface{} {

	result := make(map[string]interface{})

	result[Constants.RESULT] = make(map[string]interface{})

	err := snmp.Connect()

	snmpResult := &resultMap{}

	if err != nil {

		return snmpResult.GetDefaultResultMap(Constants.FAILED, fmt.Errorf("error in collect() method: %v", err))

	}

	defer func(Conn net.Conn) {

		tempErr := Conn.Close()

		if tempErr != nil {

			err = fmt.Errorf("close() in Collect function failed: %v", tempErr)

		}

	}(snmp.Conn)

	result[Constants.RESULT].(map[string]interface{})[SYSTEM] = make(map[string]interface{})

	var walkOrBulkWalk = snmp.BulkWalk

	tempMap := make(map[string]interface{})

	errors := make([]string, 0)

	for rootOid := range InstanceOidToMetric {

		err = walkOrBulkWalk(rootOid, func(pdu g.SnmpPDU) error {

			tempArr := strings.Split(pdu.Name, ".")

			interfaceIndex := tempArr[len(tempArr)-1]

			//if not any data is inserted for that interface
			//then make map to store data. If we don't do this
			//and directly try to access by index we get nil map
			_, ok := tempMap[interfaceIndex]

			if !ok {

				tempMap[interfaceIndex] = make(map[string]interface{})

			}

			tempMap[interfaceIndex].(map[string]interface{})[InstanceOidToMetric[rootOid]] = SnmpTypeConversion(pdu)

			return nil

		})

		//store err for single rootOid(since we are in loop)
		if err != nil {

			errors = append(errors, fmt.Sprintf("%v", err))

		}

	}

	//if all rootOid fetch have some errors then its failed
	if len(errors) >= len(InstanceOidToMetric) {

		result[Constants.STATUS] = Constants.FAILED

	} else {

		result[Constants.STATUS] = Constants.SUCCESS

	}

	//store errors (if any)
	result[Constants.MESSAGE] = strings.Join(errors, "\n") //join() converts array of errors into string

	// as java side "message" is string and not json array
	result[Constants.RESULT].(map[string]interface{})[INTERFACE] = make([]interface{}, len(tempMap))

	i := 0

	for _, data := range tempMap {

		result[Constants.RESULT].(map[string]interface{})[INTERFACE].([]interface{})[i] = data

		i++

	}

	scalarOIDS := make([]string, len(ScalarOidToMetric))

	j := 0

	for oid := range ScalarOidToMetric {

		scalarOIDS[j] = oid

		j++

	}

	data, err := snmp.Get(scalarOIDS)

	if err != nil {

		return snmpResult.GetDefaultResultMap(Constants.FAILED, fmt.Errorf("getScalarOID function failed: %v", err))

	}

	for _, val := range data.Variables {

		result[Constants.RESULT].(map[string]interface{})[SYSTEM].(map[string]interface{})[ScalarOidToMetric[val.Name]] = SnmpTypeConversion(val)

	}

	result[Constants.STATUS] = Constants.SUCCESS

	return result

}
