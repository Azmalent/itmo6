import pytest
from lab1 import Parser

parser = Parser()

def test_invalid_char():
    assert not parser.parse('This sentence contains invalid characters')

def test_empty():
    assert not parser.parse('')

def test_c():
    assert parser.parse('c')

def test_aacda():
    assert parser.parse('aacda')