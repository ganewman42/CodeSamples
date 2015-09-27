/** 
* Small class to store a word and its count
* @author Grace Newman
* @date 2/24/2015
*/

public class WordCount{
    //stores a word and its count
    private String word=null;
    private Integer count=null;
    
    /** Blank constructor */
    public WordCount(){
        //set word to blank and count to 0
        word=null;
        count=0;
    }
    
    /** Constructor taking a word and initializing its count to 1 */
    public WordCount(String w){
        word=w;
        count=1;
    }
    
    /**
     * Gets the word stored by this WordCount
     */
    public String getWord(){
        return word;
    }

    /** 
     * Gets the count stored by this WordCount
     */
     public int getCount(){
         return count;
     }
    
    /** Add one to the count */
    public void upCount(){
        count++;
    }
}