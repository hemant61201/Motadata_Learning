package main

import (
	"fmt"
)

func main() {

	i := 1

	for i <= 3 {
		fmt.Println(i)

		i += 1
	}

	for j := 4; j <= 7; j++ {
		fmt.Println(j)
	}

	for {
		fmt.Println("Loop")
		break
	}

	for j := 8; j <= 10; j++ {

		if j%2 == 0 {
			fmt.Println(j)
		}

		continue
	}

}
