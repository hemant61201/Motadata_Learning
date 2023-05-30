package main

import "fmt"

func change(s ...string) {
	s[0] = "Go"
	s = append(s, "playground")
	fmt.Println(s)
}

func main() {
	welcome := []string{"hello", "world"}
	change(welcome...)
	fmt.Println(welcome)
}

//func main() {
//	//s := []int{2, 3, 5, 7, 11, 13}
//	//
//	//s = s[0:6]
//	//fmt.Println(s)
//	//
//	//s = s[:4]
//	//fmt.Println(s)
//	//
//	//s = s[1:]
//	//fmt.Println(s)
//	//
//	//s = s[:]
//	//fmt.Println(s)
//	//finger := 4
//	//fmt.Printf("Finger %d is ", finger)
//	//switch finger {
//	//case 1:
//	//	fmt.Println("Thumb")
//	//case 2:
//	//	fmt.Println("Index")
//	//case 3:
//	//	fmt.Println("Middle")
//	//case 4:
//	//	fmt.Println("Ring")
//	//case 4: //duplicate case
//	//	fmt.Println("Another Ring")
//	//case 5:
//	//	fmt.Println("Pinky")
//
//	}
