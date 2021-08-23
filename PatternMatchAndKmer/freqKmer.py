#!/usr/bin/python3
import sys
Text=sys.argv[1]
kvar=int(sys.argv[2])

#Dictionary kFreq
kFreq = {}
#Methode kMers, das einen String Tstring und einen Integerweet für k nimmt und die Anzahl aller k-mers,
# die von Tstring gebildet werden können, zurückgibt.
def kMers(Tstring, k):

#For-Schleife von 0 bis Anzahl aller k-mere, die für Tstring gebildet werden können
    for i in range(0, len(Tstring) - k + 1):
        #Alle k-mers im Tstring
        kmer = Tstring[i:i + k]
        #Falls kmer in kFreq schon vorkam, so erhöhe die Zahl um 1
        if kmer in kFreq:
            kFreq[kmer] += 1
            #Ansonsten setzte den Wert vom kmer auf 1
        else:
            kFreq[kmer] = 1

    return kFreq

#kMers("ACAACTATGCATACTATCGGGAACTATCCT", 5)
kMers(Text,kvar)

#Aufruf der Methode kMers
#kMers("ACAACTATGCATACTATCGGGAACTATCCT",5)
#kMers(Tstring, k)

#Finden des k-mers mit dem maximalen Wert
maximum = max(kFreq, key=kFreq.get)
#Ausgabe des Wertes
print(maximum)
