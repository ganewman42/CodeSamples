/**
* Class to create and process a binary search tree of word counts
* @author Grace Newman
* @date February 24, 2015
*/

import java.util.ArrayList;
import java.util.List;

public class WordCountMap{
    //stores a binary tree of words in alphabetical order by root
    private WordNode wordRoot;
    
    /** Constructor for an empty map */
    public WordCountMap(){
        //initialize the root to blank node
        wordRoot=new WordNode();
    }
    
    /**
     * Adds 1 to the existing count for word, or adds word to the WordCountMap
     * with a count of 1 if it was not already present.
     */
    public void incrementCount(String word){
        //check for containment
        if (contains(word)){
            //if it's already there, just change count at the node
            addTo(wordRoot, word);
        } else {
            //otherwise, make a new node and place- initializes count to 1
            WordNode add = new WordNode(word);
            //check for the root being null- if it is just set to root
            if (wordRoot.data.getWord()==null){
                wordRoot=add;
            } 
            //otherwise place
            else {
                addWord(add, wordRoot);
            }
        }       
    }

    /**
     * Returns true if word is stored in this WordCountMap, and false otherwise.
     */
    public boolean contains(String word){
        //use a standard recursive search from the method
        WordNode ret= search(word, wordRoot);
        return ret!=null;
    }

    /**
     * Returns the count of word, or -1 if word is not in the WordCountMap.
     */
    public int getCount(String word){
        //check for containment
        if (!contains(word)){
            return -1;
        } else {
            //search the one ordered by word for speed
            WordNode ret =search(word,wordRoot);
            //return the count
            return ret.data.getCount();
        }
    }

    /** 
     * Returns a list of WordCount objects, one per word stored in this 
     * WordCountMap, sorted in decreasing order by count. 
     */
     public List<WordCount> getWordCountsByCount(){
        //get by word
        List<WordCount>list =new ArrayList<WordCount>();
        List<WordCount> wordOrder=inOrder(wordRoot, list);
         //call helper sort method and return
         List<WordCount> temp = new ArrayList<WordCount>();
         return sortCount(wordOrder);
     }

    /** 
     * Returns a list of WordCount objects, one per word stored in this 
     * WordCountMap, sorted alphabetically by word. 
     */
     public List<WordCount> getWordCountsByWord(){
         //traversal of word tree
         List<WordCount>list =new ArrayList<WordCount>();
         return inOrder(wordRoot, list);
     }
    
    /**Private inner class of necessary nodes.  This is a binary tree.*/
    private class WordNode{
        //instance variables: left and right child
        private WordNode leftChild;
        private WordNode rightChild;
        //also knows its associated data, a WordCount object
        private WordCount data;
        
        /** Blank constructor for root*/
        public WordNode(){
            leftChild=null;
            rightChild=null;
            data=new WordCount();
        }
        
        /** Constructor taking in a word */
        public WordNode(String inword){
            leftChild=null;
            rightChild=null;
            data=new WordCount(inword);
        }
        /** No get and set methods since outer class has access */
    }
    
    /** The following private methods implement binary tree algorithms for easy use */
        
    
    /** Method adding in a node to binary search tree by word order, assuming not already there
    * @param a node to add
    * @return void
    */
    private void addWord(WordNode node, WordNode root){
        int comp = node.data.getWord().compareTo(root.data.getWord());
        //if less than call at left
        if (comp < 0){
            //check for base case and recurse down
            if (root.leftChild!=null){
                addWord(node, root.leftChild);
            } else {
                root.leftChild=node;
            }
        }
        //otherwise call at right
        else {
            if (root.rightChild!=null){
                addWord(node, root.rightChild);
            } else {
                root.rightChild=node;
            }
        }
    }
                 
    /**
    * Recursive method to search a binary tree
    * @param a word for search and the root of tree to search
    * @return the node if found, null if not
    */
    private WordNode search(String word, WordNode node){
        //code adapted from Carrano p.720
        WordNode result=null;
        //base case: the node is null
        if (node!=null){
            String entry=node.data.getWord();
            //check for null entry- happens at root
            if (entry==null){}
            else if (entry.equals(word)){
                result=node;
            }
            else if (word.compareTo(entry)<0){
                result=search(word, node.leftChild);
            } else {
                result=search(word, node.rightChild);
            }
        }
        return result;
    }
        
    /**
    * Recursive method to to an in-order traversal of a binary tree
    * @param root of tree to search, list to update
    * @return an ordered list of WordCount objects
    */
    private List<WordCount> inOrder(WordNode node, List<WordCount> list){
        //algorithm adapted from Carrano p. 24
        //base case: node is null
        if (node !=null){
            //traverse on either side and add in middle
            inOrder(node.leftChild, list);
            list.add(node.data);
            inOrder(node.rightChild, list);
        }
        return list;
    }
    
    /**
    * Recursive method to search for a node and up its count, behaves similar to search
    * @param the root of a tree and a word to search for
    * @return null
    */
    private void addTo(WordNode node, String word){
        //base case: the node is null
        if (node!=null){
            String entry=node.data.getWord();
            if (entry.equals(word)){
                node.data.upCount();
            }
            else if (word.compareTo(entry)<0){
                addTo(node.leftChild, word);
            } else {
                addTo(node.rightChild, word);
            }
        }
    }
    
    /**
    * Insertion sort for sorting by count
    * @param list to sort
    * @return list sorted by count
    */
    private List<WordCount> sortCount(List<WordCount> list){
        //iterate over
        for (int i=0;i<list.size()-1;i++){
            WordCount next=list.get(i+1);
            list.remove(i+1);
            int index=i;
            //while it's not at start and it's less than check move over
            while ( index>=0 && next.getCount()>list.get(index).getCount()){
                index--;
            }
            //insert at index
            list.add((index+1), next);
        }
        return list;
    }
        
    /**main method for testing */
    public static void main(String[] args){
        //test initialization
        WordCountMap map = new WordCountMap();
        System.out.println("New empty map contains hi: "+map.contains("hi"));
        //add three different words and check
        String add="My name Grace";
        String[] split = add.split(" ");
        for (int i=0;i<split.length;i++){
            map.incrementCount(split[i]);
        }
        //test get
        System.out.println("Get count for a new word, should be 1: "+map.getCount("My"));
        //test ordering by word
        System.out.println("Getting by word test, should print Grace My name below:");
        List<WordCount> words = map.getWordCountsByWord();
        for (int i=0;i<words.size();i++){
            System.out.println("  "+words.get(i).getWord());
        }
        //test contains positive and negative
        System.out.println("Map contains added word: "+map.contains("My"));
        System.out.println("Map contains random word: "+map.contains("YO"));
        //test incrementing count
        map.incrementCount("Grace");
        System.out.println("Count increment test, should return 2: "+map.getCount("Grace"));
        //and finally add a few others and test ordering
        map.incrementCount("My");
        map.incrementCount("Hi");
        System.out.println("Getting by count test, should print numbers in descending order: ");
        List<WordCount> countby = map.getWordCountsByCount();
        for (int i=0;i<countby.size();i++){
            System.out.println("  "+countby.get(i).getCount());
        }
        System.out.println("Check of correct word content, should print Grace, Hi, My, name below");
        List<WordCount> words2 = map.getWordCountsByWord();
        for (int i=0;i<words2.size();i++){
            System.out.println("  "+words2.get(i).getWord());
        }
        
    }
        
         
}