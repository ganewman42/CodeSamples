#Simple program to convert a DFA (deterministic finite automaton) to an NFA (nondeterministic finite automatom)
#by Grace Newman, 4/15/2014 for a complexity course

def main():

    #power set function courtesy of Josh Davis, each subset is ordered to match the order of X
    def powerSet(X):
        if len(X) == 0:
            return ((),)
        else:
            withoutFirst = powerSet(X[1:])
            withFirst = [(X[0],) + Y for Y in withoutFirst]
            return tuple(withFirst) + withoutFirst
        
    #intersetion function courtesy of Josh Davis
    def intersection(X, Y):
        res = []
        for x in X:
            if x in Y:
                res.append(x)
        return tuple(res)

    def dfaFromNFA(NFA):
        #define variables for ease of use
        nstates=NFA[0]
        alphabet=NFA[1]
        nstart=NFA[2]
        naccept=NFA[3]
        ndelta=NFA[4]
        #build the states of the dfa using the power set function
        dstates=powerSet(nstates)
        #define the accept states as anything including an element from naccept
        daccept=()
        for i in dstates:
            for j in naccept:
                if j in i:
                    daccept=daccept+(i,)
        #define the start state as the singleton of nstart          
        dstart=tuple(nstart)
        #now define the new transition function
        ddelta=dict()
        for state in dstates: #look at each state
            for a in alphabet: #for that state, look at each possible transition character
                outstates=()
                for n in state: #for each of the nstates in the tuple of dstate
                    if (n, a) in ndelta: #add the state that the nstate would go to if it's in the keys
                        outstates=outstates+(ndelta[(n,a)],)
                        #now merge those into a single tuple
                        ddelta[(state, a)]=outstates #put that tuple in the dictionary indexed with (state, input)
        return (dstates, alphabet, dstart, daccept, ddelta)

    #run a simple test
    delta = {("1", "a"):("3","1"), ("2", "a"):("1", "2"), ("3", "a"):("2",), ("3", "b"):("3", "2")}
    n = (("1", "2", "3"), ("a", "b"), "1", ("2",), delta)
    DFA=dfaFromNFA(n)
    print DFA[4]

main()
    
    
            
