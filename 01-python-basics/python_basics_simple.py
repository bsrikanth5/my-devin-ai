"""
Python Basics — The Simple Version
==================================
Read top to bottom, then run it:

    python3 python_basics_simple.py

Every section prints what it does, so you can match the code to the output.
Try changing a value, re-run, and see what happens. That's the whole lesson.
"""

print("=" * 40)
print("1. PRINTING")
print("=" * 40)

print("Hello, world!")
print("You can print numbers too:", 42)


print()
print("=" * 40)
print("2. VARIABLES — boxes that hold values")
print("=" * 40)

name = "Srikanth"       # text  -> str
age = 30                # whole number -> int
height = 5.9            # decimal -> float
is_learning = True      # yes/no -> bool

print(name, age, height, is_learning)

# f-strings: put variables inside text with {}
print(f"{name} is {age} years old.")


print()
print("=" * 40)
print("3. MATH")
print("=" * 40)

print("2 + 3  =", 2 + 3)
print("10 - 4 =", 10 - 4)
print("3 * 4  =", 3 * 4)
print("10 / 4 =", 10 / 4)      # always a decimal
print("10 // 4 =", 10 // 4)    # whole-number division
print("10 % 4 =", 10 % 4)      # remainder
print("2 ** 3 =", 2 ** 3)      # power


print()
print("=" * 40)
print("4. TEXT (strings)")
print("=" * 40)

word = "python"
print("upper:", word.upper())
print("length:", len(word))
print("first letter:", word[0])
print("joined:", word + " rocks")
print("replaced:", word.replace("p", "P"))


print()
print("=" * 40)
print("5. LISTS — many values in order")
print("=" * 40)

fruits = ["apple", "banana", "cherry"]
print("all:", fruits)
print("first:", fruits[0])
print("last:", fruits[-1])

fruits.append("mango")     # add to the end
print("after append:", fruits)
print("how many:", len(fruits))


print()
print("=" * 40)
print("6. IF / ELSE — making decisions")
print("=" * 40)

temperature = 30

if temperature > 35:
    print("It's hot")
elif temperature > 20:
    print("It's pleasant")
else:
    print("It's cold")

# Note the indentation: the indented lines belong to the if/elif/else above.


print()
print("=" * 40)
print("7. LOOPS — doing something repeatedly")
print("=" * 40)

for fruit in fruits:
    print("I like", fruit)

print("Counting:")
for number in range(1, 6):     # 1, 2, 3, 4, 5
    print(" ", number)

count = 3
while count > 0:
    print("countdown:", count)
    count = count - 1
print("liftoff!")


print()
print("=" * 40)
print("8. FUNCTIONS — reusable blocks of code")
print("=" * 40)


def greet(person):
    return f"Hello, {person}!"


print(greet("Srikanth"))
print(greet("world"))


def add(a, b):
    return a + b


print("add(2, 3) =", add(2, 3))


print()
print("=" * 40)
print("9. DICTIONARIES — labelled values")
print("=" * 40)

person = {"name": "Srikanth", "age": 30, "city": "Hyderabad"}
print("whole dict:", person)
print("name:", person["name"])

person["job"] = "engineer"     # add a new label
print("after adding job:", person)

for key, value in person.items():
    print(f"  {key} -> {value}")


print()
print("=" * 40)
print("10. GETTING INPUT (commented out so this file runs on its own)")
print("=" * 40)

# Uncomment these two lines and re-run to try it:
# your_name = input("What's your name? ")
# print(greet(your_name))
print("See the comments in section 10 to try input().")


print()
print("=" * 40)
print("NOW YOU TRY")
print("=" * 40)
print("""
1. Change `name` at the top and re-run.
2. Add your own fruit to the list.
3. Write a function `multiply(a, b)` and print multiply(4, 5).
4. Make a dictionary about your favourite movie (title, year, rating)
   and print each key/value with a for loop.
5. Print the even numbers from 1 to 20 using a for loop and `if n % 2 == 0`.

When these feel easy, move on to python_basics_exercises.py.
""")
