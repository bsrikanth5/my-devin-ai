# LangChain and LangGraph primer

A short, beginner-friendly introduction to read before (and alongside) the lessons.

## The one-sentence version

- **LangChain** gives you the **building blocks** for LLM apps: models, prompts, output parsers, tools and retrievers, plus a way to connect them **in a straight line** (A → B → C).
- **LangGraph** is built on top of LangChain and adds **control flow**: a shared **state**, **branches**, **loops**, **pauses for a human**, and saved progress. Your steps form a **graph** instead of a line.

A useful analogy: LangChain is the box of LEGO bricks. LangGraph is the instruction booklet that says "if the piece is red go to step 5, otherwise go back to step 2".

## Why do we need either of them?

You *could* call Claude directly with the `anthropic` SDK. LangChain and LangGraph help once your app grows:

| Without them, you hand-write... | With them, you get... |
| --- | --- |
| string formatting for every prompt | reusable `ChatPromptTemplate`s with `{placeholders}` |
| code to pull text out of responses | output parsers (`StrOutputParser`, structured output) |
| different code for each LLM provider | one interface: swap `ChatAnthropic` for `ChatOpenAI` in one line |
| your own while-loops and if-statements to manage multi-step logic | a graph with named steps that you can draw, stream, pause and resume |

## LangChain: the building blocks

| Concept | What it is | Example |
| --- | --- | --- |
| **Chat model** | The LLM itself | `ChatAnthropic(model="claude-haiku-4-5")` |
| **Messages** | The conversation, as typed objects | `SystemMessage`, `HumanMessage`, `AIMessage` |
| **Prompt template** | A message recipe with blanks | `("human", "Explain {topic} simply")` |
| **Output parser** | Turns the reply object into something useful | `StrOutputParser()` gives a plain `str` |
| **Runnable** | Anything with `.invoke()`, `.stream()` and `.batch()` | models, prompts, parsers, chains, graphs |
| **Chain (LCEL)** | Runnables joined with `\|` | `prompt \| llm \| StrOutputParser()` |
| **Tools** | Python functions the model may ask to call | `@tool def add(a, b): ...` |
| **Retrievers** | Look up relevant documents (used for RAG) | a vector store's `.as_retriever()` |

A complete LangChain program:

```python
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import StrOutputParser
from langchain_anthropic import ChatAnthropic

prompt = ChatPromptTemplate.from_template("Explain {topic} to a 10-year-old.")
chain = prompt | ChatAnthropic(model="claude-haiku-4-5") | StrOutputParser()
print(chain.invoke({"topic": "recursion"}))
```

The data flows left to right. The dict fills the prompt, the prompt becomes messages, the model replies, and the parser returns a string.

**Important:** the model has **no memory**. Every call is independent. A chatbot "remembers" only because *you* send the earlier messages again each time. Lesson 1 does this with a plain list.

## LangGraph: adding control flow

A chain is great when the steps are always the same. Real apps often need to:

- **choose** what to do next ("is this even code?"),
- **repeat** a step ("wrong answer, give a hint and ask again"),
- **wait** for a human ("type your answer"),
- **remember** where they are, so they can pause and resume.

LangGraph models your app as a **state machine**:

| Concept | What it is | In code |
| --- | --- | --- |
| **State** | A dict shared by all steps (defined as a `TypedDict`) | `class State(TypedDict): code: str; language: str` |
| **Node** | A function: receives the state, returns the keys it updates | `def explain(state): return {"explanation": ...}` |
| **Edge** | A fixed arrow, "after A, run B" | `builder.add_edge("explain", "suggest")` |
| **Conditional edge** | A function that picks the next node | `builder.add_conditional_edges("grade", after_grade, [...])` |
| **START / END** | The entry and exit points | `builder.add_edge(START, "first_node")` |
| **compile()** | Turns the blueprint into a runnable graph | `graph = builder.compile()` |
| **Checkpointer** | Saves the state after every step | `builder.compile(checkpointer=InMemorySaver())` |
| **interrupt()** | Pauses the graph to wait for a human | `answer = interrupt("Your answer: ")` |
| **Command(resume=...)** | Resumes a paused graph with the human's input | `graph.invoke(Command(resume="append"), config)` |

A minimal graph:

```python
from typing import TypedDict
from langgraph.graph import StateGraph, START, END

class State(TypedDict):
    n: int

def double(state):
    return {"n": state["n"] * 2}

def keep_going(state):
    return "double" if state["n"] < 100 else END   # a loop!

builder = StateGraph(State)
builder.add_node("double", double)
builder.add_edge(START, "double")
builder.add_conditional_edges("double", keep_going, ["double", END])
graph = builder.compile()

print(graph.invoke({"n": 3}))   # {'n': 192}
```

There's no LLM in this example. That's the point: LangGraph is about **flow**. Nodes can call Claude, call an API, or run plain Python.

## Side by side

| | LangChain (chain) | LangGraph (graph) |
| --- | --- | --- |
| Shape | Straight line A → B → C | Any graph: branches and loops |
| Data passed along | Output of one step = input of the next | One shared state; each node updates part of it |
| Branching | Limited (`RunnableBranch`) | First-class (conditional edges) |
| Loops | No | Yes |
| Pause for a human | No | Yes (`interrupt` + checkpointer) |
| Save and resume | No | Yes (checkpointers, `thread_id`) |
| Best for | Prompt → answer, simple pipelines, RAG lookups | Multi-step workflows, agents, retries, chatbots with memory |

**They're not competitors.** You use LangChain pieces (models, prompts, tools) *inside* LangGraph nodes. Most real projects use both.

## What about "agents"?

An **agent** is an LLM that decides its own next step, usually "call this tool" or "I'm done". In LangGraph an agent is just a graph with a loop:

```
START -> model --(wants a tool?)--> tools -> model -> ... --(done)--> END
```

You've built every piece of that in these lessons: a node that calls the model (lesson 2), a conditional edge (lesson 2) and a loop (lesson 3). Agents with tools are the natural next step after this project.

## How the lessons map to these ideas

| Lesson | Library | You learn |
| --- | --- | --- |
| [`01_study_buddy.py`](01_study_buddy.py) | LangChain | chat model, prompt template, `\|` chains, output parser, chat history |
| [`02_code_explainer.py`](02_code_explainer.py) | LangGraph | state, nodes, edges, a conditional edge, `stream()` |
| [`03_quiz_master.py`](03_quiz_master.py) | LangGraph | loops, counters in state, `interrupt()`, checkpointer, `Command(resume=...)` |

## Common beginner pitfalls

- **Forgetting that nodes return *updates*, not the whole state.** Return `{"language": "Python"}`, not `state`. LangGraph merges your update into the state for you.
- **Infinite loops.** Every loop needs a counter or condition that eventually routes to `END`. LangGraph stops runaway graphs with a `GraphRecursionError` (the default limit is 25 steps, or set `recursion_limit` in the config).
- **`interrupt()` without a checkpointer.** Pausing needs somewhere to save the state, so compile with `checkpointer=InMemorySaver()` and pass a `thread_id`.
- **Code before `interrupt()` runs again on resume.** When a paused node resumes, it restarts from the top of that node. Keep side effects, like printing or calling the LLM, out of the node that interrupts.
- **Trusting LLM output formats.** Ask for a specific format ("reply with ONE word") and parse defensively, for example with `.strip().upper().startswith(...)`.
- **Committing your API key.** Keep it in `.env`, which is git-ignored, never in code.

## Where to go next

- LangChain docs: https://docs.langchain.com/oss/python/langchain/overview
- LangGraph docs: https://docs.langchain.com/oss/python/langgraph/overview
- Free LangChain Academy course, "Introduction to LangGraph": https://academy.langchain.com/
