package SNMP

import (
	"Plugins/Constants"
	"encoding/hex"
	"fmt"
	"github.com/gosnmp/gosnmp"
	"strings"
)

// SnmpTypeConversion converts the snmp data types to go data types
func SnmpTypeConversion(pdu gosnmp.SnmpPDU) (result string) {

	switch pdu.Type {

	case gosnmp.OctetString:

		//checks for physical address oid prefix
		if strings.HasPrefix(pdu.Name, MetricToInstanceOid["interface.physical.address"]) {

			result = hex.EncodeToString(pdu.Value.([]byte))

		} else {

			result = fmt.Sprintf("%s", string(pdu.Value.([]byte)))

		}

	default:

		result = fmt.Sprintf("%v", pdu.Value)

	}

	return result

}

type resultMap struct{}

// GetDefaultResultMap returns default error map with status and err which is passed
func (r resultMap) GetDefaultResultMap(status string, err error) map[string]interface{} {

	result := make(map[string]interface{})

	result[Constants.STATUS] = status

	result[Constants.MESSAGE] = fmt.Sprintf("%v", err)

	return result

}
