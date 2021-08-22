#!/usr/bin/env python3
import sys

def naive_approx_pattern_match(p, t, d):
    #List calles occurences
    occurrences = []
    for i in range(0, len(t) - len(p) + 1):
        mismatch = 0
        for j in range(0, len(p)):          # for all characters
            #Check if match or mismatch
            if t[i+j] != p[j]:
                mismatch += 1                     # mismatch, so add 1 to mismatch count
                if mismatch > d:
                    break                    # exceeded maximum distance, thus the threshold and will be appended to list
        if mismatch <= d:
            occurrences.append(i)
    return str(occurrences)[1:-1]

#Main method
if __name__ == '__main__':

    #Commandline arguments
    p = sys.argv[1].lower()
    txt = sys.argv[2]
    d = int(sys.argv[3])

    #Read the file
    file_path = txt
    with open(file_path) as f_obj:
        t= f_obj.read()
    #print(t)


    #Call the method and print it in Stdout
    print(naive_approx_pattern_match(p,t,d))

