import sys

to_verify = sorted(open(sys.argv[1]).readlines())
to_verify_against = sorted(open(sys.argv[2]).readlines())

for number, line in enumerate(to_verify):
    if line != to_verify_against[number]: 
        print(f"!\t{line}\n{to_verify_against[number]}")