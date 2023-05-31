package main

import (
	"fmt"
)

func assert(i interface{}) {
	s := i.(int) //get the underlying int value from i
	fmt.Println(s)
}
func main() {
	var s interface{} = 56
	assert(s)
}
