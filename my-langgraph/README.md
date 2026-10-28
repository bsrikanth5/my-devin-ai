# my-langgraph

Learn **LangChain** and **LangGraph** by building one small project that grows step by step: a learning tutor powered by Claude.

New to both? Read **[PRIMER.md](PRIMER.md)** first. It explains what each library is, how they differ, and the key vocabulary.

## Lessons

| # | File | What you build | Main ideas |
| --- | --- | --- | --- |
| 1 | [`01_study_buddy.py`](01_study_buddy.py) | A chatbot that explains topics simply and remembers the conversation | LangChain: prompt → model → parser chain, chat history |
| 2 | [`02_code_explainer.py`](02_code_explainer.py) | Paste code, get the language, an explanation and suggestions | LangGraph: state, nodes, edges, conditional branching |
| 3 | [`03_quiz_master.py`](03_quiz_master.py) | A quiz that gives hints and lets you retry wrong answers | LangGraph: loops, human-in-the-loop `interrupt()`, checkpointing |

Each file starts with a comment explaining the new ideas and ends with **Try it** exercises.

## Setup (once)

You need Python 3.10+ and an [Anthropic API key](https://console.anthropic.com/settings/keys).

```bash
cd my-langgraph
python3 -m venv .venv
source .venv/bin/activate          # do this in every new terminal
pip install -r requirements.txt

cp .env.example .env               # then open .env and paste your key
```

`.env` is git-ignored, so your key never gets committed.

### Optional: keep the key in the macOS Keychain instead of `.env`

If `ANTHROPIC_API_KEY` is already set in your shell, the lessons use it and you can skip `.env`. To store it in the Keychain rather than in a plain-text file:

```bash
# 1. Store the key once (you'll be prompted to paste it)
security add-generic-password -a "$USER" -s anthropic -w

# 2. Add this line to ~/.zshrc, then open a new terminal
export ANTHROPIC_API_KEY=$(security find-generic-password -s anthropic -w)

# 3. Check it's set (prints only the first 7 characters)
echo ${ANTHROPIC_API_KEY:0:7}
```

If your key is already in an environment variable with a different name, `.env` can point to it: `ANTHROPIC_API_KEY=${MY_CLAUDE_KEY}`.

## Run a lesson

```bash
python 01_study_buddy.py
python 02_code_explainer.py
python 03_quiz_master.py
```

Every lesson uses `common.py` to create the Claude model. The default is `claude-haiku-4-5` (fast and cheap); set `CLAUDE_MODEL` in `.env` to use a different one.

## Troubleshooting

- `ANTHROPIC_API_KEY is not set`: create `.env` from `.env.example` and add your key.
- `ModuleNotFoundError: langchain_anthropic`: activate the virtual environment (`source .venv/bin/activate`) and run `pip install -r requirements.txt`.
- `model not found` errors: the model name may have been retired, so set `CLAUDE_MODEL` in `.env` to a current one from Anthropic's model list.
