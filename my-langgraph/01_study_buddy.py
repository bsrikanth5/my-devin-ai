"""
LESSON 1: STUDY BUDDY  (LangChain basics)

What it does:  A command-line tutor that explains any topic in simple terms
               and remembers what you've already talked about.

New ideas:
  1. Chat model     - the LLM itself (Claude), from common.get_llm().
  2. Prompt template - a reusable message with {placeholders}.
  3. Output parser  - turns the model's reply object into a plain string.
  4. Chain (LCEL)   - `prompt | llm | parser` pipes the output of one step
                      into the next, like a Unix pipe.
  5. Chat history   - a plain Python list of messages we pass in every time.
                      The model itself has NO memory; we resend the history.

Run:  python 01_study_buddy.py      (type 'quit' to exit)
"""
from langchain_core.messages import AIMessage, HumanMessage
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

from common import get_llm

# --- 1. The prompt: a system message, the history so far, then the new question.
prompt = ChatPromptTemplate.from_messages([
    ("system",
     "You are a friendly study buddy for a beginner programmer. "
     "Explain things in plain language, use a small example, "
     "and keep answers under 150 words. Level: {level}."),
    MessagesPlaceholder("history"),  # earlier messages get inserted here
    ("human", "{question}"),
])

# --- 2. The chain: prompt -> model -> plain string.
chain = prompt | get_llm() | StrOutputParser()


def main():
    print("Study Buddy - ask me anything. Type 'quit' to exit.\n")
    history = []  # grows with every turn: [HumanMessage, AIMessage, ...]

    while True:
        question = input("You: ").strip()
        if question.lower() in {"quit", "exit", "q"}:
            break
        if not question:
            continue

        # --- 3. Run the chain. The dict fills the {placeholders} in the prompt.
        answer = chain.invoke({
            "level": "beginner",
            "history": history,
            "question": question,
        })
        print(f"\nBuddy: {answer}\n")

        # --- 4. Remember this turn so follow-up questions make sense.
        history.append(HumanMessage(question))
        history.append(AIMessage(answer))

    print("Bye! Happy studying.")


if __name__ == "__main__":
    main()

# Try it:
#  1. Ask "what is a list?", then "how do I add to one?" - the second question
#     only makes sense because of the history. Now comment out the two
#     history.append lines and ask again: the buddy forgets.
#  2. Change "beginner" to "expert" and compare the answers.
#  3. Replace chain.invoke(...) with a loop over chain.stream(...) and
#     print(chunk, end="", flush=True) to see the answer appear word by word.
