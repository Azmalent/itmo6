from enum import Enum

import sys
sys.setrecursionlimit(100)

MARKER = '$'
STARTING_SYMBOL = 'S'

class Parser:
    def __init__(self):
        pass


    reductionRules = {
        'ZS':   'S',
        'ZB':   'S',
        'Z':    'S',
        'A':    'Z',
        'aA':   'A',
        'bA':   'A',
        'c':    'A',
        'dB':   'B',
        'a':    'B',
        'd':    'B'
    }


    parsingTable = {
        (MARKER, 'AZabc'):      '<',
        ('SABZacd', MARKER):    '>',
        ('Ac', True):           '>',
        ('Z', 'SB'):            '=',
        ('Z', 'AZabcd'):        '<',
        ('ab', 'A'):            '=',
        ('ab', 'abc'):          '<',
        ('d', 'B'):             '=',
        ('d', 'ad'):            '<'
    }


    def findPivot(self, stack):
        pivot = []
        symbol = stack.pop()
        while symbol != '<':
            pivot.insert(0, symbol)
            symbol = stack.pop()
        return ''.join(pivot)


    def reduce(self, stack):
        pivot = self.findPivot(stack)
        if pivot in self.reductionRules:
            return self.reductionRules[pivot]
        raise ValueError('Invalid pivot: ' + pivot)
        

    def fullReduce(self, stack):
        nonterminal = self.reduce(stack)
        relationship = self.getRelationship(stack[-1], nonterminal)
        if relationship == '>':
            self.fullReduce(stack)
        elif relationship == '<':
            stack.append(relationship)
        stack.append(nonterminal)


    def getRelationship(self, a, b):
        if (a, b) == (MARKER, STARTING_SYMBOL):
            return '='
        for p in self.parsingTable:
            if (p[0] == True or a in p[0]) and (p[1] == True or b in p[1]):
                return self.parsingTable[p]
        raise ValueError('Relationship between ' + a + ' and ' + b + ' is not defined')


    def parse(self, input):
        if input == '':
            return False
        input += MARKER
        stack = [MARKER]
        try:
            while not (input == MARKER and stack == [MARKER, STARTING_SYMBOL]):
                top, nextToken = stack[-1], input[0]
                relationship = self.getRelationship(top, nextToken)
                if relationship == '>':             # Reduce
                    self.fullReduce(stack)
                else:    # Shift
                    stack.append(relationship)
                    stack.append(nextToken)
                    input = input[1:]
        except ValueError as err:
            return False
        return True
        