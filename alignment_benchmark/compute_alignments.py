import argparse as ap
import datetime
import sys
from timeit import default_timer as timer

pars = ap.ArgumentParser()
pars.add_argument('--n', type=int, required=True)
pars.add_argument('--m', type=int, required=True)
compute = pars.add_mutually_exclusive_group(required=True)
compute.add_argument('--recursive', action='store_true')
compute.add_argument('--dp', action='store_true')
pars.add_argument('--all', action='store_true')
args = pars.parse_args()
function_calls = 0
additions = 0


def poss_alignments_recursive(n, m):
    global function_calls, additions
    function_calls += 1
    if n == 0 or m == 0:
        return 1
    additions += 2
    return poss_alignments_recursive(n - 1, m) + poss_alignments_recursive(n, m - 1)


def poss_alignments_recursive_all(n, m):
    global function_calls, additions
    function_calls += 1
    if n == 0 or m == 0:
        return 1
    additions += 3
    return poss_alignments_recursive_all(n - 1, m - 1) + poss_alignments_recursive_all(n - 1,
                                                                                       m) + poss_alignments_recursive_all(
        n, m - 1)


def poss_alignments_dp(n, m):
    global additions, function_calls
    matrix = [[]]
    for i in range(n + 1):
        matrix[0].append(1)
    for j in range(1, m + 1):
        matrix.append([1])
    for i in range(1, n + 1):
        for j in range(1, m + 1):
            additions += 1
            matrix[j].append(matrix[j][i - 1] + matrix[j - 1][i])
    function_calls = 1
    return matrix[m][n]


def poss_alignments_dp_all(n, m):
    global additions, function_calls
    matrix = [[]]
    for i in range(n + 1):
        matrix[0].append(1)
    for j in range(1, m + 1):
        matrix.append([1])
    for i in range(1, n + 1):
        for j in range(1, m + 1):
            matrix[j].append(matrix[j][i - 1] + matrix[j - 1][i - 1] + matrix[j - 1][i])
            additions += 2
    function_calls = 1
    return matrix[m][n]


def print_and_measure(my_function):
    print('res\ttime in micros\tfunction_calls\tadditions')
    start = timer()
    res = str(my_function)
    end = timer()
    time = end - start
    print(str(res) + '\t' + str(time * 1000000) + "\t" + str(
        function_calls) + '\t' + str(additions))


n_arg = args.n
m_arg = args.m
if n_arg < 1 or m_arg < 1:
    print("both ints n and m have to be greater than zero", file=sys.stderr)
elif args.all:
    if args.recursive:
        print_and_measure(poss_alignments_recursive_all(n_arg, m_arg))
    else:
        print_and_measure(poss_alignments_dp_all(n_arg, m_arg))
else:
    if args.recursive:
        print_and_measure(poss_alignments_recursive(n_arg, m_arg))
    else:
        print_and_measure(poss_alignments_dp(n_arg, m_arg))
