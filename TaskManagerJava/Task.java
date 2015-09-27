/** Class to store a task object (a date and description)
* @author Grace Newman
* @date 3/7/2015
*/

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParsePosition;

public class Task implements Comparable<Task>{
    //instance variables
    private String desc;
    private Date date;
    
    /**
     * Constructor: Creates a new task with the given dueDate and description.
     * dueDate must be in the format mm-dd-yyyy (e.g., 07-01-2014 for 1 July 2014); it's
     * okay if this method crashes if the date format is incorrect.
     */
    public Task(String dueDate, String description){
        desc=description;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        date=formatter.parse(dueDate, pos);
    }

    /**
     * Gets the description of this task.
     */
    public String getDescription(){
        return desc;
    }

    /**
     * Gets the due date for this task.
     */
    public Date getDueDate(){
        return date;
    }
    
    /**
    * Compare method 
    */
    public int compareTo(Task comp){
        //compare by due date
        return date.compareTo(comp.getDueDate());
    }
}