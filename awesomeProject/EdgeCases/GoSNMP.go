// Copyright 2012 The GoSNMP Authors. All rights reserved.  Use of this
// source code is governed by a BSD-style license that can be found in the
// LICENSE file.

package main

import (
	"fmt"
	"log"

	g "github.com/gosnmp/gosnmp"
)

func main() {

	g.Default.Target = "172.16.8.2"

	err := g.Default.Connect()

	if err != nil {
		log.Fatalf("Connect() err: %v", err)
	}

	defer g.Default.Conn.Close()

	oids := []string{"1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.1.3.0", "1.3.6.1.2.1.1.2.0", "1.3.6.1.2.1.1.1.0"}

	result, err2 := g.Default.Get(oids)

	if err2 != nil {
		log.Fatalf("Get() err: %v", err2)
	}

	for i, variable := range result.Variables {

		fmt.Printf("%d: oid: %s ", i, variable.Name)

		switch variable.Type {

		case g.OctetString:
			fmt.Printf("string: %s\n", string(variable.Value.([]byte)))

		default:
			fmt.Printf("number: %d\n", g.ToBigInt(variable.Value))
		}
	}
}
