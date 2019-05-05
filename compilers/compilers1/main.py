import sys
from lab1 import Parser


def main(argv=None):
    if argv is None:
        argv = sys.argv
    if len(argv) == 2:
        parser = Parser()
        result = 'accept' if parser.parse(argv[1]) else 'reject'
        print(result)
    else:
        print('Invalid number of arguments')


if __name__ == "__main__":
    sys.exit(main())