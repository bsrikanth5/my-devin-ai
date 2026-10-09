# my-design-patterns

The 23 classic "Gang of Four" design patterns in Java. Each pattern has one
small, runnable lesson file with a real-world example.

## What's a design pattern?

A design pattern is a named, reusable solution to a problem that keeps coming
up when designing object-oriented code. It isn't a library you install. It's a
way of arranging classes and interfaces that experienced developers recognise
on sight. Knowing the names lets you say "let's use a Strategy here" instead of
describing the whole design.

There are three families:

- **Creational**: how objects get *created*.
- **Structural**: how objects are *composed* into bigger structures.
- **Behavioral**: how objects *communicate* and share responsibilities.

## How to run a lesson

You need JDK 11 or newer (`java -version`). Java 11+ can run a single source
file directly, so there's no compile step and no build tool:

```bash
cd my-design-patterns
java creational/Singleton.java
```

Run every lesson in one go:

```bash
./run_all.sh
```

## How each lesson is laid out

Each file reads top to bottom:

1. **Header comment**: the problem, the idea, a real-world analogy, and when to
   use the pattern (plus how it differs from similar patterns).
2. **The pattern**: interfaces and classes, with short comments on the parts
   that matter.
3. **`main`**: a demo that prints what's happening, so you can match each
   line of output to the code.
4. **Try it**: one or two small changes to make yourself.

To keep each lesson to one runnable file, every class is nested inside the
file's public class. In a real project each class would live in its own file.

## Suggested order

If you're new to patterns, start with the ones you'll meet most often in real
code, then go through the rest:

1. [Strategy](behavioral/Strategy.java)
2. [Observer](behavioral/Observer.java)
3. [Factory Method](creational/FactoryMethod.java)
4. [Builder](creational/Builder.java)
5. [Singleton](creational/Singleton.java)
6. [Decorator](structural/Decorator.java)
7. [Adapter](structural/Adapter.java)
8. [Facade](structural/Facade.java)
9. [Template Method](behavioral/TemplateMethod.java)
10. [Command](behavioral/Command.java)

## All 23 patterns

### Creational

| Pattern | One-line idea | Example |
| --- | --- | --- |
| [Singleton](creational/Singleton.java) | Exactly one instance, shared globally | App config, logger |
| [Factory Method](creational/FactoryMethod.java) | Subclasses decide which class to create | Road vs sea logistics |
| [Abstract Factory](creational/AbstractFactory.java) | Create families of matching objects | Light/dark UI themes |
| [Builder](creational/Builder.java) | Build complex objects step by step | Pizza order |
| [Prototype](creational/Prototype.java) | Create objects by copying a template | Game enemy spawner |

### Structural

| Pattern | One-line idea | Example |
| --- | --- | --- |
| [Adapter](structural/Adapter.java) | Make an incompatible interface fit | Legacy payment gateway |
| [Bridge](structural/Bridge.java) | Split two dimensions of variation | Remotes x devices |
| [Composite](structural/Composite.java) | Treat single items and groups the same | Files and folders |
| [Decorator](structural/Decorator.java) | Wrap an object to add behaviour | Coffee add-ons |
| [Facade](structural/Facade.java) | One simple front door to a complex system | Home theater |
| [Flyweight](structural/Flyweight.java) | Share common data across many objects | Forest of trees |
| [Proxy](structural/Proxy.java) | A stand-in that controls access | Lazy-loading images |

### Behavioral

| Pattern | One-line idea | Example |
| --- | --- | --- |
| [Chain of Responsibility](behavioral/ChainOfResponsibility.java) | Pass a request along until someone handles it | Expense approvals |
| [Command](behavioral/Command.java) | Turn an action into an object | Editor with undo |
| [Interpreter](behavioral/Interpreter.java) | Evaluate sentences of a small language | Math expressions |
| [Iterator](behavioral/Iterator.java) | Walk a collection without knowing its insides | Playlist |
| [Mediator](behavioral/Mediator.java) | Objects talk through a central hub | Chat room |
| [Memento](behavioral/Memento.java) | Save and restore an object's state | Document snapshots |
| [Observer](behavioral/Observer.java) | Notify subscribers when something changes | Channel subscriptions |
| [State](behavioral/State.java) | Behaviour changes with internal state | Order lifecycle |
| [Strategy](behavioral/Strategy.java) | Swap interchangeable algorithms | Shipping options |
| [Template Method](behavioral/TemplateMethod.java) | Fixed skeleton, customisable steps | Making tea vs coffee |
| [Visitor](behavioral/Visitor.java) | Add operations without changing classes | Shape area and JSON export |

## Patterns that look alike

| These two... | ...differ in |
| --- | --- |
| Strategy vs State | Strategy: the caller picks the algorithm. State: the object switches itself as events happen. |
| Decorator vs Proxy | Decorator *adds* features. Proxy *controls access* to the same features. |
| Factory Method vs Abstract Factory | One product via subclassing vs a family of products via one factory object. |
| Command vs Memento (for undo) | Command runs the reverse action. Memento restores a saved snapshot. |
| Template Method vs Strategy | Inheritance (override steps) vs composition (swap the whole algorithm). |
| Adapter vs Facade | Adapter makes one interface fit another. Facade simplifies a whole subsystem. |
