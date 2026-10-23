"""
LESSON 2: CODE EXPLAINER  (your first LangGraph graph)

What it does:  Paste a code snippet. A small graph works out the language,
               explains the code, then suggests improvements. If the text
               isn't code at all, the graph takes a shortcut and stops early.

    START -> detect_language --(is code?)--> explain -> suggest -> END
                                  `--(not code)--> not_code -> END

New ideas:
  1. State      - one shared dict (a TypedDict) that every node can read.
  2. Nodes      - plain Python functions: take the state, return ONLY the
                  keys they want to update.
  3. Edges      - fixed arrows: "after explain, always run suggest".
  4. Conditional edge - a function looks at the state and picks the next node.
  5. compile() + invoke() - turn the blueprint into a runnable app.

Run:  python 02_code_explainer.py
"""
from typing import TypedDict

from langgraph.graph import END, START, StateGraph

from common import get_llm

llm = get_llm(temperature=0)

SAMPLE_CODE = """def avg(nums):
    total = 0
    for n in nums:
        total = total + n
    return total / len(nums)"""


# --- 1. State: everything the graph knows, in one place.
class State(TypedDict, total=False):
    code: str
    language: str
    explanation: str
    suggestions: str


# --- 2. Nodes: each does one job and returns a partial update.
def detect_language(state: State) -> dict:
    reply = llm.invoke(
        "What programming language is this? Reply with ONE word only, "
        f"or NONE if it is not code.\n\n{state['code']}"
    )
    return {"language": reply.content.strip().strip(".")}


def explain(state: State) -> dict:
    reply = llm.invoke(
        f"Explain this {state['language']} code line by line for a beginner. "
        f"Keep it short.\n\n{state['code']}"
    )
    return {"explanation": reply.content}


def suggest(state: State) -> dict:
    reply = llm.invoke(
        f"Suggest up to 3 small improvements to this {state['language']} code "
        f"(bugs, edge cases, readability). Bullet points only.\n\n{state['code']}"
    )
    return {"suggestions": reply.content}


def not_code(state: State) -> dict:
    return {"explanation": "That doesn't look like code, so there's nothing to explain."}


# --- 3. The router for the conditional edge: returns the NAME of the next node.
def route_after_detect(state: State) -> str:
    return "not_code" if state["language"].upper() == "NONE" else "explain"


# --- 4. Wire it up.
builder = StateGraph(State)
builder.add_node("detect_language", detect_language)
builder.add_node("explain", explain)
builder.add_node("suggest", suggest)
builder.add_node("not_code", not_code)

builder.add_edge(START, "detect_language")
builder.add_conditional_edges("detect_language", route_after_detect, ["explain", "not_code"])
builder.add_edge("explain", "suggest")
builder.add_edge("suggest", END)
builder.add_edge("not_code", END)

graph = builder.compile()


def read_code() -> str:
    print("Paste your code, then type END on its own line.")
    print("(Or just type END to use a sample snippet.)\n")
    lines = []
    while (line := input()) != "END":
        lines.append(line)
    return "\n".join(lines) or SAMPLE_CODE


def main():
    # Mermaid text of the graph you built: paste it into https://mermaid.live
    print(graph.get_graph().draw_mermaid())

    code = read_code()
    # stream() yields after each node, so you can watch the graph move.
    for step in graph.stream({"code": code}):
        for node_name, update in step.items():
            print(f"\n=== {node_name} ===")
            for key, value in update.items():
                print(f"[{key}]\n{value}")


if __name__ == "__main__":
    main()

# Try it:
#  1. Type some plain English instead of code and watch it take the
#     not_code path.
#  2. Add a "complexity" node between explain and suggest that rates the
#     code easy / medium / hard and stores it in a new state key.
#  3. Paste the printed Mermaid text into https://mermaid.live to see a
#     picture of your graph.
#  4. Replace graph.stream(...) with result = graph.invoke({"code": code})
#     and print(result): invoke returns only the final state.
