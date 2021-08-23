#!/usr/bin/python3
import sys
pattern = sys.argv[1]

#Methode namens revereseComplement mit String aus Kommandozeile als Input für den Parameter
#Output gibt das revereseComplement zurück

def reverseComplement(pattern):
    output = ""
    #complement = {"A":"T", "T":"A", "C":"G", "G":"C"}
    for letter in pattern:
        if letter == "A":
            output = output + "T"
        elif letter == "T":
            output = output + "A"
        elif letter == "C":
            output = output + "G"
        else:
		#Fehlerbehandlung, wenn ein anderer Buchstabe als erlaubt im String vorkommt.
            if letter == "G":
               output = output + "C"
            else:
                print("This letter is not allowed.")
    return output [::-1]

print(reverseComplement(pattern))
