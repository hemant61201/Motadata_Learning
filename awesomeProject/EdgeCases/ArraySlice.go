package main

import (
	"fmt"
)

func changeLocal(num [5]int) {
	num[0] = 55
	fmt.Println("inside function ", num)

}

func printarray(a [3][2]string) {
	for _, v1 := range a {
		for _, v2 := range v1 {
			fmt.Printf("%s ", v2)
		}
		fmt.Printf("\n")
	}
}

func subtactOne(numbers []int) {
	for i := range numbers {
		numbers[i] -= 2
	}

}

func countries() []string {
	countries := []string{"USA", "Singapore", "Germany", "India", "Australia"}
	neededCountries := countries[:len(countries)-2]
	countriesCpy := make([]string, len(neededCountries))
	copy(countriesCpy, neededCountries)
	return countriesCpy
}

func main() {

	fmt.Println("array declaration")
	var a [3]int
	fmt.Println(a)

	var b [3]int
	b[0] = 12
	b[1] = 78
	b[2] = 50
	fmt.Println(b)
	fmt.Println("\nshort hand declaration")

	c := [3]int{12, 78, 50}
	fmt.Println(c)

	fmt.Println("\nsyntactic sugar to determine length")
	d := [...]int{12, 78, 50}
	fmt.Println(d)

	fmt.Println("\nArrays are value types")
	g := [...]string{"USA", "China", "India", "Germany", "France"}
	h := g
	g[0] = "Singapore"
	fmt.Println("g is ", g)
	fmt.Println("h is ", h)

	fmt.Println("\nChanges made to an array inside a function are not visible to the caller")
	num := [...]int{5, 6, 7, 8, 8}
	fmt.Println("before passing to function ", num)
	changeLocal(num)
	fmt.Println("after passing to function ", num)

	fmt.Println("\nlength of an array")
	i := [...]float64{67.7, 89.8, 21, 78}
	fmt.Println("length of a is", len(i))

	fmt.Println("\niterating array using for loop")
	farray := [...]float64{67.7, 89.8, 21, 78}
	for i := 0; i < len(a); i++ {
		fmt.Printf("%d th element of a is %.2f\n", i, farray[i])
	}

	fmt.Println("\niterating array using for range loop")
	for i, v := range farray { //range returns both the index and value
		fmt.Printf("%d the element of a is %.2f\n", i, v)
	}

	fmt.Println("\ndeclaring 2d arrays")
	animals := [3][2]string{
		{"lion", "tiger"},
		{"cat", "dog"},
		{"pigeon", "peacock"},
	}
	printarray(animals)
	var company [3][2]string
	company[0][0] = "apple"
	company[0][1] = "samsung"
	company[1][0] = "microsoft"
	company[1][1] = "google"
	company[2][0] = "AT&T"
	company[2][1] = "T-Mobile"
	fmt.Printf("\n")
	printarray(company)

	fmt.Println("\nSlice declaration")
	as := [5]int{76, 77, 78, 79, 80}
	var slice1 []int = as[1:4]
	fmt.Println(slice1)

	slice2 := []int{6, 7, 8}
	fmt.Println(slice2)

	fmt.Println("\nmodifying a slice, modifies the underlying array")
	darr := [...]int{57, 89, 90, 82, 100, 78, 67, 69, 59}
	dslice := darr[2:5]
	fmt.Println("array before", darr)
	for i := range dslice {
		dslice[i]++
	}
	fmt.Println("array after", darr)

	fmt.Println("\nWhen slices share the same underlying array, changes each one makes is reflected in the array")
	numa := [3]int{78, 79, 80}
	nums1 := numa[:]
	nums2 := numa[:]
	fmt.Println("array before change", numa)
	nums1[0] = 100
	fmt.Println("array after modification to slice nums1", numa)
	nums2[1] = 101
	fmt.Println("array after modification to slice nums2", numa)

	fmt.Println("\nlength and capacity of slice")
	fruitarray := [...]string{"apple", "orange", "grape", "mango", "water melon", "pine apple", "chikoo"}
	fruitslice := fruitarray[1:3]
	fmt.Printf("length of slice %d capacity %d", len(fruitslice), cap(fruitslice))

	fmt.Println("\n\nreslicing a slice")
	vegarray := [...]string{"apple", "orange", "grape", "mango", "water melon", "pine apple", "chikoo"}
	vegslice := vegarray[1:3]
	fmt.Printf("length of slice %d capacity %d\n", len(vegslice), cap(vegslice))
	vegslice = vegslice[:cap(fruitslice)]
	fmt.Println("After re-slicing length is", len(vegslice), "and capacity is", cap(vegslice))

	fmt.Println("\ndeclaring a slice using make")
	mkslice := make([]int, 5, 5)
	fmt.Println(mkslice)

	fmt.Println("\nappending to a slice")
	cars := []string{"Ferrari", "Honda", "Ford"}
	fmt.Println("cars:", cars, "has old length", len(cars), "and capacity", cap(cars))
	cars = append(cars, "Toyota")
	fmt.Println("cars:", cars, "has new length", len(cars), "and capacity", cap(cars)) //capacity of cars is doubled to 6

	fmt.Println("\nappending to a nil slice")
	var names []string //zero value of a slice is nil
	if names == nil {
		fmt.Println("slice is nil going to append")
		names = append(names, "John", "Sebastian", "Vinay")
		fmt.Println("names contents:", names)
	}

	fmt.Println("\nappending one slice to another")
	veggies := []string{"potatoes", "tomatoes", "brinjal"}
	fruits := []string{"oranges", "apples"}
	food := append(veggies, fruits...)
	fmt.Println("food:", food)

	fmt.Println("\nchanges made to a slice inside a function are visible to the caller")
	nos := []int{8, 7, 6}
	fmt.Println("slice before function call", nos)
	subtactOne(nos)
	fmt.Println("slice after function call", nos)

	fmt.Println("\nMultidimensional slices")
	pls := [][]string{
		{"C", "C++"},
		{"JavaScript"},
		{"Go", "Rust"},
	}
	for _, v1 := range pls {
		for _, v2 := range v1 {
			fmt.Printf("%s ", v2)
		}
		fmt.Printf("\n")
	}

	fmt.Println("\nMemory optimization using copy")
	countriesNeeded := countries()
	fmt.Println(countriesNeeded)

}
