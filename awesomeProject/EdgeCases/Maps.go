package main

import (
	"fmt"
)

func main() {
	var personSalary1 map[string]int //zero value of map is nil
	if personSalary1 == nil {
		fmt.Println("map is nil. Going to make personSalary1 map.")
		personSalary1 = make(map[string]int)
	}

	fmt.Println("\nShort hand declaration:-")
	personSalary2 := make(map[string]int)
	personSalary2["steve"] = 12000
	personSalary2["jamie"] = 15000
	personSalary2["mike"] = 9000
	fmt.Println("personSalary2 map contents:", personSalary2)

	fmt.Println("\nMap initialization and declaration:-")
	personSalary3 := map[string]int{
		"steve": 12000,
		"jamie": 15000,
	}
	personSalary3["mike"] = 9000
	fmt.Println("personSalary map contents:", personSalary3)

	fmt.Println("\nAccessing items of a map:-")
	employee := "jamie"
	fmt.Println("Salary of", employee, "is", personSalary3[employee])

	fmt.Println("\nAccess an element which is not present returns the zero value:-")
	fmt.Println("Salary of joe is", personSalary3["joe"])

	fmt.Println("\nChecking whether a key is present or not:-")
	newEmp := "joe"
	value, ok := personSalary3[newEmp]
	if ok == true {
		fmt.Println("Salary of", newEmp, "is", value)
	} else {
		fmt.Println(newEmp, "not found")
	}

	fmt.Println("\niterating over all elements using for range:-")
	for key, value := range personSalary3 {
		fmt.Printf("personSalary3[%s] = %d\n", key, value)
	}

	fmt.Println("\nDeleting items:-")
	fmt.Println("map before deletion", personSalary3)
	delete(personSalary3, "steve")
	fmt.Println("map after deletion", personSalary3)

	fmt.Println("\nLength of map:-")
	fmt.Println("length of personSalary3 is", len(personSalary3))

	fmt.Println("\nMaps are reference type:-")
	map1 := map[string]int{
		"steve": 12000,
		"jamie": 15000,
	}
	fmt.Println("Original map1", map1)
	map2 := map1
	map2["mike"] = 18000
	fmt.Println("map1 changed", map2)

}
