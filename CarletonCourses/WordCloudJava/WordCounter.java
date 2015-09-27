/* 
* Class to count words from a file and display, depends on instructor written WordClouseMaker
* @author Grace Newman
* @date 2/26/2015
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WordCounter{
    //instance variables: a word count map and the stopwords
    private WordCountMap map;
    private List<String> stopwords;
    
    /** Blank constructor */
    public WordCounter(){
        map= new WordCountMap();
        stopwords = new ArrayList<String>();
    }
    
    /** Method to load stop words.  Assumes presence of StopWords.txt in directory */
    public void loadStop(){
        //initialize list
        List<String> stops = new ArrayList<String>();
        try{
            Scanner scanner = new Scanner(new File("StopWords.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //strip white space
                stops.add(line.trim());
            }
        }
        //catch if the file is not found
        catch(FileNotFoundException except){
            //make an empty animals list
            System.out.println("Stop Word file not found- no stop words loaded.");
        }
        stopwords=stops;
    }
    
    /** Method to load a text file to display */
    public void loadText(String filename){
        try{
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //remove punctuation and double quotes
                String nopunc=line.replaceAll("\\.|,|\\?|!|;|\"|\\(|\\)","");
                //special cases: remove dashes surrounded by spaces and single quotes within doubles
                String nopunc2=nopunc.replaceAll(" - ","");
                String nopunc3=nopunc2.replaceAll("\"'","");
                //put in lower case
                String lower=nopunc3.toLowerCase();
                //and finally split at any whitespace
                String[] splitline=lower.split("\\s");
                //add all these to the map if they're not a stopword and not blank
                for (int i=0;i<splitline.length;i++){
                    if (!stopwords.contains(splitline[i]) && !splitline[i].equals("")){
                        map.incrementCount(splitline[i]);
                    }
                }
            }
        }
        //catch if the file is not found
        catch(FileNotFoundException except){
            //make an empty animals list
            System.out.println("Text file not found- no text loaded.");
        }
    }
    
    /** Method to print in alphabetical order */
    public void printAlpha(){
        List<WordCount> list = map.getWordCountsByWord();
        for (int i=0;i<list.size();i++){
            WordCount item=list.get(i);
            System.out.println(item.getWord()+": "+item.getCount());
        }
    }
    
    /**Method to print in count order */
    public void printCount(){
        List<WordCount> list = map.getWordCountsByCount();
        for (int i=0;i<list.size();i++){
            WordCount item=list.get(i);
            System.out.println(item.getWord()+": "+item.getCount());
        }
    }
    
    /** Method to make a word cloud */
    public void wordCloud(int max, String output){
        //generate list in count order
        List<WordCount> list = map.getWordCountsByCount();
        List<WordCount> shortlist=list;
        //get those of highest count
        if (list.size()>max){
            shortlist=list.subList(0,max-1);
        }
        //call to WordCloudMaker
        String html=WordCloudMaker.getWordCloudHTML(output, shortlist);
        //write output
        try {
            PrintWriter toFile=new PrintWriter(output);
            toFile.println(html);
            toFile.close();
        } catch (FileNotFoundException e){
            System.out.println("Error writing to html file, could not open output file.");
        }
    }
        
        
    
    
    public static void main(String[] args){
        //construct instance
        WordCounter counter = new WordCounter();
        //load stop words
        counter.loadStop();
        //check for the proper args length
        if (args.length<2){
            System.out.println("Incorrect usage: give a usage type and a file name");
        } else{
            //load the text file as a WordCountMap
            counter.loadText(args[1]);
            //now check for display type
            if (args[0].equals("alphabetical")){
                counter.printAlpha();
            }
            else if (args[0].equals("byCount")){
                counter.printCount();
            }
            else if (args[0].equals("cloud")){
                //check for additional args
                if (args.length<4){
                    System.out.println("Incorrect usage: if exporting cloud, give cloud, a name of a text file, the number of words to include, and the name of the outfile.");
                } else {
                    counter.wordCloud(Integer.parseInt(args[2]),args[3]);
                }
            }
            else {
                System.out.println("Incorrect output type: select alphabetical, byCount, or cloud.");
            }
        }  
    }
        
}