"""
LESSON 3: QUIZ MASTER  (loops, human-in-the-loop, checkpointing)

What it does:  Quizzes you on a topic. If your answer is wrong, it gives a
               hint and lets you try again (up to 3 tries). Then it moves on.

    START -> ask_question -> get_answer -> grade --(correct / out of tries)--> next
                                ^             |
                                |-- give_hint <-(wrong, tries left)
    next --(more questions)--> ask_question
         `--(done)--> END

New ideas:
  1. Cycles     - edges can point backwards, so the graph can loop.
                  (A plain LangChain chain can't do this.)
  2. Counters in state - `attempts` and `question_num` stop the loops.
  3. interrupt() - pause the graph and wait for a human (you!).
  4. Checkpointer - saves the state at every step, which is what lets the
                  graph pause and later resume exactly where it stopped.
  5. Command(resume=...) - how we send your answer back into the paused graph.

Run:  python 03_quiz_master.py
"""
from typing import TypedDict

from langgraph.checkpoint.memory import InMemorySaver
from langgraph.graph import END, START, StateGraph
from langgraph.types import Command, interrupt

from common import get_llm

llm = get_llm(temperature=0.7)

MAX_ATTEMPTS = 3
NUM_QUESTIONS = 3


class State(TypedDict, total=False):
    topic: str
    question_num: int   # which question we're on (1, 2, 3)
    question: str
    asked: list[str]    # every question so far, so none get repeated
    answer: str
    attempts: int       # tries used on the current question
    correct: bool
    score: int


def ask_question(state: State) -> dict:
    num = state.get("question_num", 0) + 1
    asked = state.get("asked", [])
    reply = llm.invoke(
        f"Write quiz question #{num} about {state['topic']} for a beginner. "
        "It must have a short answer (a word or a phrase). "
        "Reply with the question only. Don't repeat any of these earlier "
        f"questions: {asked or 'none yet'}"
    )
    print(f"\nQuestion {num}: {reply.content}")
    return {
        "question_num": num,
        "question": reply.content,
        "asked": asked + [reply.content],  # a new list, not .append()
        "attempts": 0,
    }


def get_answer(state: State) -> dict:
    # Pauses the whole graph here. main() shows the prompt, reads your input
    # and resumes the graph with Command(resume=your_answer).
    answer = interrupt("Your answer: ")
    return {"answer": answer, "attempts": state["attempts"] + 1}


def grade(state: State) -> dict:
    reply = llm.invoke(
        f"Question: {state['question']}\nStudent answer: {state['answer']}\n"
        "Is the answer correct? Be lenient about spelling and wording. "
        "Reply with exactly one word: CORRECT or WRONG."
    )
    correct = reply.content.strip().upper().startswith("CORRECT")
    if correct:
        print("Correct!")
        return {"correct": True, "score": state.get("score", 0) + 1}
    return {"correct": False}


def give_hint(state: State) -> dict:
    reply = llm.invoke(
        f"Question: {state['question']}\nThe student answered: {state['answer']}\n"
        "Give a one-sentence hint WITHOUT revealing the answer."
    )
    tries_left = MAX_ATTEMPTS - state["attempts"]
    print(f"Not quite. Hint: {reply.content}  (tries left: {tries_left})")
    return {}


def reveal(state: State) -> dict:
    reply = llm.invoke(f"Give the short correct answer to: {state['question']}")
    print(f"Out of tries. The answer was: {reply.content}")
    return {}


# --- Routers: look at the state, return the name of the next node.
def after_grade(state: State) -> str:
    if state["correct"]:
        return "next"
    if state["attempts"] >= MAX_ATTEMPTS:
        return "reveal"
    return "give_hint"


def after_question_done(state: State) -> str:
    return "ask_question" if state["question_num"] < NUM_QUESTIONS else END


def next_step(state: State) -> dict:
    return {}  # a tiny "junction" node, so both paths can share one router


builder = StateGraph(State)
builder.add_node("ask_question", ask_question)
builder.add_node("get_answer", get_answer)
builder.add_node("grade", grade)
builder.add_node("give_hint", give_hint)
builder.add_node("reveal", reveal)
builder.add_node("next", next_step)

builder.add_edge(START, "ask_question")
builder.add_edge("ask_question", "get_answer")
builder.add_edge("get_answer", "grade")
builder.add_conditional_edges("grade", after_grade, ["next", "give_hint", "reveal"])
builder.add_edge("give_hint", "get_answer")  # <- the loop back!
builder.add_edge("reveal", "next")
builder.add_conditional_edges("next", after_question_done, ["ask_question", END])

# The checkpointer is required for interrupt(): it stores the paused state.
graph = builder.compile(checkpointer=InMemorySaver())


def main():
    topic = input("Quiz topic (e.g. Python lists): ").strip() or "Python lists"
    # thread_id names this conversation; the checkpointer saves state under it.
    config = {"configurable": {"thread_id": "quiz-1"}}

    result = graph.invoke({"topic": topic, "score": 0}, config)
    # While the graph is paused at interrupt(), result contains "__interrupt__".
    while "__interrupt__" in result:
        prompt = result["__interrupt__"][0].value
        answer = input(prompt)
        result = graph.invoke(Command(resume=answer), config)

    print(f"\nFinished! Score: {result['score']}/{NUM_QUESTIONS}")


if __name__ == "__main__":
    main()

# Try it:
#  1. Change MAX_ATTEMPTS to 1 and see the hint loop disappear.
#  2. After the quiz, print graph.get_state(config).values to see the
#     final saved state, and list(graph.get_state_history(config)) to see
#     every checkpoint the graph saved along the way.
#  3. Add a "difficulty" key that goes up after each correct answer and
#     use it in the ask_question prompt.
