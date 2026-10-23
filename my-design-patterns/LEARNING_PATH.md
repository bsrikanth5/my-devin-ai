# Learning path

A reference for *what* the Gang of Four patterns are and *in what order* to
study them. Tick the boxes as you go.

For each lesson:

1. Read the header comment (problem, idea, analogy).
2. Run the file and match each line of output to `main`.
3. Do the "Try it" exercise at the bottom.

```bash
cd my-design-patterns
java behavioral/Strategy.java
```

---

## Gang of Four: synopsis

**The book.** *Design Patterns: Elements of Reusable Object-Oriented Software*
(1994) by Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides. The
four authors are known as the "Gang of Four", or GoF. The book catalogued 23
patterns that experienced developers kept reinventing, and gave each one a name.

**What a pattern is.** A pattern is a proven way to arrange classes and objects
to solve a recurring design problem. It isn't code you copy: it's a template
you adapt. The shared names give developers a vocabulary, so saying "that's a
Decorator" explains a whole design in one word.

**The two big principles** behind almost every pattern:

1. **Program to an interface, not an implementation.** Code depends on
   `PaymentProcessor`, not on `CardProcessor`, so implementations can be
   swapped.
2. **Favor object composition over class inheritance.** Build behaviour by
   combining objects (an object *has a* strategy, *wraps* another object)
   rather than through deep subclass trees.

A third idea underlies many of them: **encapsulate what varies**. Find the
part that keeps changing and put it behind an interface.

**The three families:**

| Family | Question it answers | Patterns |
| --- | --- | --- |
| **Creational** (5) | How do objects get created, without hard-coding `new ConcreteClass()` everywhere? | Singleton, Factory Method, Abstract Factory, Builder, Prototype |
| **Structural** (7) | How do classes and objects fit together into larger structures? | Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy |
| **Behavioral** (11) | How do objects communicate and divide up responsibilities? | Chain of Responsibility, Command, Interpreter, Iterator, Mediator, Memento, Observer, State, Strategy, Template Method, Visitor |

**How each pattern is described in the book:** intent, motivation (an example
problem), applicability (when to use it), structure (a class diagram),
participants, collaborations, consequences (trade-offs), implementation
notes, sample code, known uses, and related patterns. The lesson headers here
are a condensed version of that format.

**In modern Java:**

- Some patterns are built into the language or standard library:
  - Iterator: `Iterable` and for-each loops
  - Observer: event listeners
  - Decorator: `java.io` streams
  - Builder: `StringBuilder` and `HttpRequest.newBuilder()`
  - Flyweight: `Integer.valueOf` caching
- Lambdas make Strategy and Command much lighter. Often a strategy is just
  `kg -> 7.99`, and a command is just a `Runnable`.
- Singleton is now often avoided in favour of dependency injection, because
  global state makes code hard to test.
- Patterns are tools, not goals. Use one when you can feel the problem it
  solves, not just because it exists.

---

## Overall order (all 23)

### Phase 1: the essentials

These are the patterns you'll see most in real code and interviews.

- [x] 1. [Strategy](behavioral/Strategy.java): swap algorithms behind an interface
- [x] 2. [Observer](behavioral/Observer.java): notify subscribers of changes
- [x] 3. [Factory Method](creational/FactoryMethod.java): subclasses decide what to create
- [x] 4. [Builder](creational/Builder.java): build complex objects step by step
- [ ] 5. [Singleton](creational/Singleton.java): one shared instance
- [ ] 6. [Decorator](structural/Decorator.java): wrap an object to add behaviour
- [ ] 7. [Adapter](structural/Adapter.java): make an incompatible interface fit
- [ ] 8. [Facade](structural/Facade.java): a simple front door to a complex system
- [ ] 9. [Template Method](behavioral/TemplateMethod.java): fixed steps, customisable parts
- [ ] 10. [Command](behavioral/Command.java): actions as objects, which enables undo

### Phase 2: very common

- [ ] 11. [Iterator](behavioral/Iterator.java): walk a collection without knowing its insides
- [ ] 12. [Composite](structural/Composite.java): treat items and groups the same way
- [ ] 13. [State](behavioral/State.java): behaviour changes with internal state
- [ ] 14. [Proxy](structural/Proxy.java): a stand-in that controls access
- [ ] 15. [Abstract Factory](creational/AbstractFactory.java): families of matching objects
- [ ] 16. [Chain of Responsibility](behavioral/ChainOfResponsibility.java): pass a request along a chain
- [ ] 17. [Prototype](creational/Prototype.java): create by copying a template

### Phase 3: specialised

- [ ] 18. [Memento](behavioral/Memento.java): save and restore snapshots
- [ ] 19. [Mediator](behavioral/Mediator.java): objects talk through a hub
- [ ] 20. [Bridge](structural/Bridge.java): split two dimensions of variation
- [ ] 21. [Flyweight](structural/Flyweight.java): share common data across many objects
- [ ] 22. [Visitor](behavioral/Visitor.java): add operations without changing classes
- [ ] 23. [Interpreter](behavioral/Interpreter.java): evaluate a small language

---

## Order within each family

Use this if you'd rather finish one folder at a time.

### Behavioral (`behavioral/`)

**Core: you'll see these everywhere**

1. [Strategy](behavioral/Strategy.java): swap one algorithm for another behind an interface.
2. [Observer](behavioral/Observer.java): notify a list of subscribers when something changes.
3. [Template Method](behavioral/TemplateMethod.java): a fixed sequence of steps, where subclasses fill in some of them. Compare it with Strategy.
4. [Command](behavioral/Command.java): turn an action into an object, which makes undo possible.
5. [Iterator](behavioral/Iterator.java): how `for (x : list)` works behind the scenes.

**Next: state and coordination**

6. [State](behavioral/State.java): built like Strategy, but the object switches states by itself.
7. [Chain of Responsibility](behavioral/ChainOfResponsibility.java): pass a request along a chain until someone handles it.
8. [Memento](behavioral/Memento.java): save and restore snapshots. Compare its undo with Command's.
9. [Mediator](behavioral/Mediator.java): objects talk through a central hub instead of to each other.

**Advanced: used less often**

10. [Visitor](behavioral/Visitor.java): add new operations without changing the classes they work on.
11. [Interpreter](behavioral/Interpreter.java): evaluate a small language.

### Creational (`creational/`)

1. [Factory Method](creational/FactoryMethod.java): the basic "hide the `new`" pattern.
2. [Builder](creational/Builder.java): readable construction with many optional parts.
3. [Singleton](creational/Singleton.java): easy to learn, and useful to know when *not* to use it.
4. [Abstract Factory](creational/AbstractFactory.java): Factory Method scaled up to families of objects.
5. [Prototype](creational/Prototype.java): copying objects, plus the shallow vs deep copy gotcha.

### Structural (`structural/`)

1. [Adapter](structural/Adapter.java): the simplest wrapper, which translates one interface to another.
2. [Decorator](structural/Decorator.java): a wrapper that adds behaviour, and can be stacked.
3. [Facade](structural/Facade.java): a wrapper around a whole subsystem.
4. [Proxy](structural/Proxy.java): a wrapper that controls access. Compare it with Decorator.
5. [Composite](structural/Composite.java): trees where leaves and groups share one interface.
6. [Bridge](structural/Bridge.java): fixes a "class explosion" from combining two hierarchies.
7. [Flyweight](structural/Flyweight.java): a memory optimisation that shares common state.

The first four structural patterns are all wrappers. Learning them together
makes the difference in *intent* between them clear.
