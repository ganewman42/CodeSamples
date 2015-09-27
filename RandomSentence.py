#A program to generate a series of random sentences from a text file using Markov chains
#by Grace Newman, written for CS 111 (Intro to CS), completed March 1, 2013

from random import randrange
class sentenceGenerator:

    def __init__(self): #initialize an instance as empty list of start words and empty dictionary of word connections
        self.startWords=[]
        self.connections={}

    def makeData(self, filename):
        file=open(filename) #open the file and put all the words in a list
        words=file.read().split()
        file.close()
        self.makeStarts(words) #make the list of start words and dictionary of connections using the functions
        self.makeConnect(words)

    def makeStarts(self, wordlist):
        for word in wordlist:
            if 65<=ord(word[0])<=90 and word not in self.startWords: #select all of the words that begin with an uppercase letter and aren't already in the list
                self.startWords.append(word) #append those to the list of start words

    def makeConnect(self, wordlist):
        for x in range(len(wordlist)-1): #for each index in the list, get the word and the following word
            word=wordlist[x]
            entry=wordlist[x+1]
            if self.connections.has_key(word):  #if it's already in the dictionary, just append the next word to the entry list
                self.connections[word].append(entry)
            else:
                self.connections[word]=[entry] #if it's not already there, just make it a list entry containing the entry

    def randomSentence(self):
        punctuation=[".", "!", "?"] #initialize a list of ending punctuation and a sentence list for later
        sentence=[]
        start=self.startWords[randrange(len(self.startWords))] #get a random position in startWords and use that to get the first word
        index=randrange(len(self.connections[start])) #get a random word from the associated words with start
        next=self.connections[start][index] 
        sentence.append(start) #put those puppies in the sentence
        sentence.append(next)
        while next[-1] not in punctuation and len(sentence)<=50 or len(sentence)<=4: #while the sentence is still going (doesn't end in punctuation, has less than four words, or has length of fifty words)
            index=randrange(len(self.connections[next])) #get the next word as above and append it to the sentence
            then=self.connections[next][index] 
            sentence.append(then)
            next=then #set that word to initialize the next one

        text=sentence[0] #now take that list of words and turn it into a string with appropriate spacing
        for word in sentence[1:]:
            text=text+" "+word
        return text

def newCall(sentence): #for each new call, print a sentence and query the user
    print sentence.randomSentence()
    command=raw_input("Type 'g' for another sentence, 'n' to switch to a new file, and 'q' to quit: ")
    return command

def main():
    inFile=raw_input("Please input a .txt file to generate random sentences from: ") #to start off, get necessary info, make a sentence, and then ask for more info
    sentence=sentenceGenerator()
    currentInstance=sentence
    data=sentence.makeData(inFile)
    command=newCall(sentence)
    while command!="q":
        if command=="g":
            command=newCall(currentInstance) #whenever the user asks for a new sentence, give it from the working instance
        if command=="n":
            inFile=raw_input("Please input a .txt file to generate random sentences from: ") #if they ask for a new file, make an instance and generate the data again, then query
            sentencen=sentenceGenerator()
            currentInstance=sentencen
            data=sentencen.makeData(inFile)
            command=newCall(sentencen)

main()
