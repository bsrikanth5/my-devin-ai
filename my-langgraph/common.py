"""Shared helper: every lesson gets its Claude model from here.

Keeping this in one place means switching model or provider is a one-line
change, and the lessons can focus on LangChain / LangGraph ideas.
"""
import os
import sys

from dotenv import load_dotenv
from langchain_anthropic import ChatAnthropic

DEFAULT_MODEL = "claude-haiku-4-5"


def get_llm(temperature=0.3):
    load_dotenv()  # reads ANTHROPIC_API_KEY (and CLAUDE_MODEL) from .env
    if not os.getenv("ANTHROPIC_API_KEY"):
        sys.exit("ANTHROPIC_API_KEY is not set. Copy .env.example to .env and add your key.")
    return ChatAnthropic(
        model=os.getenv("CLAUDE_MODEL", DEFAULT_MODEL),
        temperature=temperature,
    )
