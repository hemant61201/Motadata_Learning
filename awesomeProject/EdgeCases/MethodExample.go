package main

import (
	"fmt"
	"math"
)

type Employee struct {
	name     string
	salary   int
	currency string
	age      int
}

func (e Employee) displaySalary() {
	fmt.Printf("Salary of %s is %s%d", e.name, e.currency, e.salary)
}

func displaySalary(e Employee) {
	fmt.Printf("\nSalary of %s is %s%d", e.name, e.currency, e.salary)
}

type Rectangle struct {
	length int
	width  int
}

type Circle struct {
	radius float64
}

func (r Rectangle) Area() int {
	return r.length * r.width
}

func perimeter(r *Rectangle) {
	fmt.Println("perimeter function output:", 2*(r.length+r.width))

}

func (r *Rectangle) perimeter() {
	fmt.Println("perimeter method output:", 2*(r.length+r.width))
}

func (c Circle) Area() float64 {
	return math.Pi * c.radius * c.radius
}

func area(r Rectangle) {
	fmt.Printf("\nArea Function result: %d\n", (r.length * r.width))
}

func (e Employee) changeName(newName string) {
	e.name = newName
}

func (e *Employee) changeAge(newAge int) {
	e.age = newAge
}

type address struct {
	city  string
	state string
}

func (a address) fullAddress() {
	fmt.Printf("\nFull address: %s, %s", a.city, a.state)
}

type person struct {
	firstName string
	lastName  string
	address
}

type myInt int

func (a myInt) add(b myInt) myInt {
	return a + b
}

func main() {
	emp1 := Employee{
		name:     "Sam Adolf",
		salary:   5000,
		currency: "$",
	}
	emp1.displaySalary()

	displaySalary(emp1)

	r := Rectangle{
		length: 10,
		width:  5,
	}
	fmt.Printf("\nArea of rectangle %d\n", r.Area())
	c := Circle{
		radius: 12,
	}
	fmt.Printf("Area of circle %f", c.Area())

	e := Employee{
		name: "Mark Andrew",
		age:  50,
	}
	fmt.Printf("\nEmployee name before change: %s", e.name)
	e.changeName("Michael Andrew")
	fmt.Printf("\nEmployee name after change: %s", e.name)

	fmt.Printf("\n\nEmployee age before change: %d", e.age)
	e.changeAge(51) //calling method with pointer receiver
	fmt.Printf("\nEmployee age after change: %d", e.age)

	p := person{
		firstName: "Elon",
		lastName:  "Musk",
		address: address{
			city:  "Los Angeles",
			state: "California",
		},
	}

	p.fullAddress()

	rect1 := Rectangle{
		length: 10,
		width:  5,
	}
	area(rect1)
	fmt.Println("Area Method Result:", rect1.Area())

	ptr1 := &rect1

	fmt.Println("Area Method Result:", ptr1.Area())

	rect2 := Rectangle{
		length: 10,
		width:  5,
	}
	ptr2 := &r
	perimeter(ptr2)
	ptr2.perimeter()

	rect2.perimeter()

	num1 := myInt(5)
	num2 := myInt(10)
	sum := num1.add(num2)
	fmt.Println("Sum is", sum)
}
