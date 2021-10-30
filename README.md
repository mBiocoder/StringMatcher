# StringMatcher
String matching algorithms and Benchmarking

This repository contains the implementation of following algorithms:
1. Naive string matching algorithm
2. Z-box algorithm
3. Knuth-Morris-Pratt (KMP)
4. Boyer-Moore with next table (BMN)
5. Boyer-Moore with bad character rule (BMB)
6. Approx. Pattern Matching with python
7. PatternMatchAndKmer with python

##############################################

8. Alignment and Benchmarking with Python

Additionally benchmarking is performed comparing the runtime (symbol comparisons) between two algorithms and plotting this in a line chart using JavaFX.

The package sample contains code specifically for the naive algorithm. It has the naive algorithm and the modified naive algorithm implemented, where each character in the pattern is different and can then plot the runtime for various alphabets (DNA, binary and alphanumerical).

There are two types of the naive algorithm implemented inside the folder sample. The classical version and a modified naive algorithm, where we assume that all characters in a given pattern are different and thus never the same.
