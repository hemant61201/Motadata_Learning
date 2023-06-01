package main

import "awesomeProject/EdgeCases/employee"

func main() {
	e := employee.New("Hemant", "Vadhaiya", 30, 20)
	e.LeavesRemaining()
}
