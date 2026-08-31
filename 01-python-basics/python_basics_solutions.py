"""
Python Basics — Solutions
=========================
Reference answers for python_basics_exercises.py.
Run `python3 python_basics_solutions.py` to see all tests pass.
"""


# ---------- 1. Variables & types ----------
def to_celsius(fahrenheit):
    return (fahrenheit - 32) * 5 / 9


def describe(value):
    return f"{value} is of type {type(value).__name__}"


# ---------- 2. Strings ----------
def initials(full_name):
    return "".join(word[0].upper() + "." for word in full_name.split())


def is_palindrome(s):
    cleaned = "".join(c.lower() for c in s if c.isalnum())
    return cleaned == cleaned[::-1]


def count_vowels(s):
    return sum(1 for c in s.lower() if c in "aeiou")


# ---------- 3. Conditionals ----------
def grade(score):
    if score >= 90:
        return "A"
    elif score >= 80:
        return "B"
    elif score >= 70:
        return "C"
    elif score >= 60:
        return "D"
    return "F"


def fizzbuzz(n):
    if n % 15 == 0:
        return "FizzBuzz"
    if n % 3 == 0:
        return "Fizz"
    if n % 5 == 0:
        return "Buzz"
    return str(n)


# ---------- 4. Loops ----------
def factorial(n):
    result = 1
    for i in range(2, n + 1):
        result *= i
    return result


def fibonacci(n):
    seq = []
    a, b = 0, 1
    for _ in range(n):
        seq.append(a)
        a, b = b, a + b
    return seq


def sum_digits(n):
    return sum(int(d) for d in str(abs(n)))


# ---------- 5. Lists ----------
def second_largest(nums):
    distinct = sorted(set(nums), reverse=True)
    return distinct[1] if len(distinct) > 1 else None


def flatten(nested):
    return [item for sublist in nested for item in sublist]


def evens_squared(nums):
    return [n * n for n in nums if n % 2 == 0]


# ---------- 6. Dicts & sets ----------
def word_count(text):
    counts = {}
    for word in text.split():
        counts[word] = counts.get(word, 0) + 1
    return counts


def invert(d):
    return {value: key for key, value in d.items()}


def common_items(a, b):
    return sorted(set(a) & set(b))


# ---------- 7. Functions ----------
def apply_twice(fn, x):
    return fn(fn(x))


def make_counter():
    count = 0

    def counter():
        nonlocal count
        count += 1
        return count

    return counter


# ---------- 8. Exceptions ----------
def safe_divide(a, b):
    try:
        return a / b
    except ZeroDivisionError:
        return None


# ---------- 9. Classes ----------
class BankAccount:
    def __init__(self, owner, balance=0):
        self.owner = owner
        self.balance = balance

    def deposit(self, amount):
        if amount <= 0:
            raise ValueError("deposit must be positive")
        self.balance += amount

    def withdraw(self, amount):
        if amount > self.balance:
            raise ValueError("insufficient funds")
        self.balance -= amount

    def __str__(self):
        return f"{self.owner}: {self.balance}"


# =====================================================================
# Same test runner as the exercise file.
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

    c = make_counter()
    r(_check("make_counter", [c(), c(), c()], [1, 2, 3]))

    r(_check("safe_divide", (safe_divide(6, 3), safe_divide(1, 0)), (2.0, None)))

    acct = BankAccount("Srikanth", 100)
    acct.deposit(50)
    acct.withdraw(30)
    try:
        acct.withdraw(1000)
        raised = False
    except ValueError:
        raised = True
    r(_check("BankAccount", (acct.balance, str(acct), raised),
             (120, "Srikanth: 120", True)))

    print(f"\n{sum(results)}/{len(results)} passing")


if __name__ == "__main__":
    main()
