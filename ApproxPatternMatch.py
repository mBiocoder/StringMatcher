import sys
#pat = sys.argv[1]
#txt = sys.argv[2]

# Python3 program for Naive Pattern
# Searching algorithm
#def search(pat, txt, d):
    #M = len(pat)
   # N = len(txt)

    #mismatch_count = []
    # A loop to slide pat[] one by one */
    #for i in range(N - M + 1):
        #j = 0

        # For current index i, check
        # for pattern match */

        #while (j < M):
           # if (txt[i + j] != pat[j]):
               # mismatch_count = mismatch_count +1
                #break
           # j += 1

        #if (mismatch_count <= d):
            #print(i)
        #elif(j == M):
           # print(i)


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
    #txt = "AABAACAADAABAAABAA"
    ##txt = text.lower()
    #pat = "AABA"
    #d = 2



    #ATGATCAAG
    pat = sys.argv[1]
    txt = "atcaatgatcaacgtaagcttctaagcatgatcaaggtgctcacacagtttatccacaacgctgagtggatgacatcaagataggtcgttgtatctccttcctctcgtactctcatgaccagcggaaagatgatcaagagaggatgatttcttggccatatcgcaatgaatacttgtgacttggtgcttccaattgacatcttcagcgccatattgcgctggccaaggtgacggagcgggattgacgaaagcatgatcatggctgttgttctgtttatcttgttttgactgagacttgttaggagtagacggtttttcatcactgactagccaaagccttactctgcctgacatcgaccgtaaatgtgataatgaatttacatgcttccgcgacgatttacctcttgatcatcgatccgattgaaggatcttcaattgttaattctcttgcctcgactcatagccatgatgagctcttgatcatgttgtccttaaccctctattttttacggaagaatgatcaagctgctgctcttgatcatcgtttcg"
    #txt = sys.argv[2]
    d = int(sys.argv[2])

    #file_path = txt
    #with open(file_path) as f_obj:
        #contents = f_obj.read().rstrip()
    #print(contents)

    #search(pat, contents)
    search(pat, txt, d)