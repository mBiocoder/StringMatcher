import sys

mismatch_count = {}
list= []

def search(pat, txt, d):
    M = len(pat)
    N = len(txt)

    for i in range(N - M + 1):

        j= 0
        while (j < M):
            if (txt[i + j] != pat[j]):
                mismatch_count[j] = 1
                break

            if (txt[i + j] != pat[j]):
                mismatch_count[j] = 0
                break
            j += 1
            
            for l in mismatch_count:
                if mismatch_count[l] <= d:
                    list.append(i)
                if mismatch_count[l] > d:
                    list.clear()
    for x in list:
        print(x)


if __name__ == '__main__':

    #ATGATCAAG
    pat = sys.argv[1]
    txt = "atcaatgatcaacgtaagcttctaagcatgatcaaggtgctcacacagtttatccacaacgctgagtggatgacatcaagataggtcgttgtatctccttcctctcgtactctcatgaccagcggaaagatgatcaagagaggatgatttcttggccatatcgcaatgaatacttgtgacttggtgcttccaattgacatcttcagcgccatattgcgctggccaaggtgacggagcgggattgacgaaagcatgatcatggctgttgttctgtttatcttgttttgactgagacttgttaggagtagacggtttttcatcactgactagccaaagccttactctgcctgacatcgaccgtaaatgtgataatgaatttacatgcttccgcgacgatttacctcttgatcatcgatccgattgaaggatcttcaattgttaattctcttgcctcgactcatagccatgatgagctcttgatcatgttgtccttaaccctctattttttacggaagaatgatcaagctgctgctcttgatcatcgtttcg"
    #txt = sys.argv[2]
    d = int(sys.argv[2])

    search(pat, txt, d)
