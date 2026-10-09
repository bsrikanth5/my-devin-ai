"""
Python Basics — 20 Exercises
============================
Fill in each function body (replace `pass` / the TODO).
Run `python3 python_basics_exercises.py` to check your answers — it prints
PASS/FAIL for each exercise. Solutions are in python_basics_solutions.py.
"""


# ---------- 1. Variables & types ----------
def to_celsius(fahrenheit):
    """Convert Fahrenheit to Celsius: (F - 32) * 5/9"""
    return (fahrenheit - 32) * 5/9


def describe(value):
    """Return a string like "42 is of type int"."""
    return f"{value} is of type {type(value).__name__}"

# ---------- 2. Strings ----------
def initials(full_name):
    """"srikanth b reddy" -> "S.B.R." """
    return "".join([name[0].upper() +for name in full_name.split()])


def is_palindrome(s):
    """Ignore case and spaces. "Never odd or even" -> True"""
    s = s.lower().replace(" ", "")
    return s == s[::-1]


def count_vowels(s):
    vowels = "aeiou"
    return sum(1 for char in s.lower() if char in vowels)


# ---------- 3. Conditionals ----------
def grade(score):
    """90+ 'A', 80+ 'B', 70+ 'C', 60+ 'D', else 'F'."""
    if score >= 90:
        return 'A'
    elif score >= 80:
        return 'B'
    elif score >= 70:
        return 'C'
    elif score >= 60:
        return 'D'
    else:
        return 'F'


def fizzbuzz(n):
    """Multiples of 3 -> 'Fizz', 5 -> 'Buzz', both -> 'FizzBuzz', else str(n)."""
    result = []
    for i in range(1, n + 1):
        if i % 15 == 0:
            result.append('FizzBuzz')
        elif i % 3 == 0:
            result.append('Fizz')
        elif i % 5 == 0:
            result.append('Buzz')
        else:
            result.append(str(i))
    return result


# ---------- 4. Loops ----------
def factorial(n):
    """factorial(5) == 120, factorial(0) == 1"""
    result = 1
    for i in range(1, n + 1):
        result *= i
    return result


def fibonacci(n):
    """Return a list of the first n Fibonacci numbers starting 0, 1."""
    if n <= 0:
        return []
    elif n == 1:
        return [0]
    elif n == 2:
        return [0, 1]
    
    fib = [0, 1]
    for i in range(2, n):
        fib.append(fib[i-1] + fib[i-2])
    return fib


def sum_digits(n):
    """sum_digits(1234) == 10"""
    total = 0
    for digit in str(n):
        total += int(digit)
    return total


# ---------- 5. Lists ----------
def second_largest(nums):
    """Return the second largest distinct value, or None if there isn't one."""
    unique_nums = list(set(nums))
    if len(unique_nums) < 2:
        return None
    unique_nums.sort(reverse=True)
    return unique_nums[1]
   
    pass


def flatten(nested):
    """[[1, 2], [3], []] -> [1, 2, 3]  (one level deep)"""
    result = []
    for sublist in nested:
        result.extend(sublist)
    return result


def evens_squared(nums):
    """Use a list comprehension: [1,2,3,4] -> [4, 16]"""
    return [num ** 2 for num in nums if num % 2 == 0]


# ---------- 6. Dicts & sets ----------
def word_count(text):
    """"a b a" -> {"a": 2, "b": 1}"""
    words = text.split()
    count = {}
    for word in words:
        count[word] = count.get(word, 0) + 1
    return count


def invert(d):
    """{"a": 1, "b": 2} -> {1: "a", 2: "b"}"""
    return {v: k for k, v in d.items()}


def common_items(a, b):
    """Return a sorted list of items present in both lists."""
    return sorted(list(set(a) & set(b)))


# ---------- 7. Functions ----------
def apply_twice(fn, x):
    """apply_twice(lambda n: n + 3, 1) == 7"""
    return fn(fn(x))


def make_counter():
    """Return a function that returns 1, 2, 3, ... on successive calls."""
    count = 0
    def counter():
        nonlocal count
        count += 1
        return count
    return counter


# ---------- 8. Exceptions ----------
def safe_divide(a, b):
    """Return a / b, or None if b == 0."""
    try:
        return a / b
    except ZeroDivisionError:
        return None


# ---------- 9. Classes ----------
class BankAccount:
    """
    acct = BankAccount("Srikanth", 100)
    acct.deposit(50); acct.withdraw(30)
    acct.balance == 120
    acct.withdraw(1000) -> raises ValueError("insufficient funds")
    str(acct) == "Srikanth: 120"
    """
    def __init__(self, owner, initial_balance):
        self.owner = owner
        self.balance = initial_balance
    
    def deposit(self, amount):
        self.balance += amount
    
    def withdraw(self, amount):
        if self.balance < amount:
            raise ValueError("insufficient funds")
        self.balance -= amount
    
    def __str__(self):
        return f"{self.owner}: {self.balance}"


# =====================================================================
# Test runner — don't edit below.
# =====================================================================
def _check(name, got, want):
    ok = got == want
    print(f"{'PASS' if ok else 'FAIL'}  {name:<20} got={got!r} want={want!r}")
    return ok


def main():
    results = []
    r = results.append

    r(_check("to_celsius", to_celsius(212), 100.0))
    r(_check("describe", describe(42), "42 is of type int"))
    r(_check("initials", initials("srikanth b reddy"), "S.B.R."))
    r(_check("is_palindrome", is_palindrome("Never odd or even"), True))
    r(_check("count_vowels", count_vowels("hello world"), 3))
    r(_check("grade", grade(85), "B"))
    r(_check("fizzbuzz", [fizzbuzz(i) for i in (3, 5, 15, 7)],
             ["Fizz", "Buzz", "FizzBuzz", "7"]))
    r(_check("factorial", factorial(5), 120))
    r(_check("fibonacci", fibonacci(7), [0, 1, 1, 2, 3, 5, 8]))
    r(_check("sum_digits", sum_digits(1234), 10))
    r(_check("second_largest", second_largest([5, 1, 5, 3]), 3))
    r(_check("flatten", flatten([[1, 2], [3], []]), [1, 2, 3]))
    r(_check("evens_squared", evens_squared([1, 2, 3, 4]), [4, 16]))
    r(_check("word_count", word_count("a b a"), {"a": 2, "b": 1}))
    r(_check("invert", invert({"a": 1, "b": 2}), {1: "a", 2: "b"}))
    r(_check("common_items", common_items([1, 2, 3], [3, 2, 9]), [2, 3]))
    r(_check("apply_twice", apply_twice(lambda n: n + 3, 1), 7))

    try:
        c = make_counter()
        got = [c(), c(), c()]
    except Exception as e:
        got = f"error: {e}"
    r(_check("make_counter", got, [1, 2, 3]))

    r(_check("safe_divide", (safe_divide(6, 3), safe_divide(1, 0)), (2.0, None)))

    try:
        acct = BankAccount("Srikanth", 100)
        acct.deposit(50)
        acct.withdraw(30)
        try:
            acct.withdraw(1000)
            raised = False
        except ValueError:
            raised = True
        got = (acct.balance, str(acct), raised)
    except Exception as e:
        got = f"error: {e}"
    r(_check("BankAccount", got, (120, "Srikanth: 120", True)))

    print(f"\n{sum(results)}/{len(results)} passing")


if __name__ == "__main__":
    main()
