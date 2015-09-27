/** Class to allow user to interact with a priority queue of tasks
* extends TaskPriorityQueue
* @author Grace Newman
* @date 3/7/2015
*/

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.text.SimpleDateFormat;
import java.text.FieldPosition;

public class TaskManager extends TaskPriorityQueue{
    /**
     * Creates a task manager with no tasks.
     */
    public TaskManager(){
        super();
    }

    /**
     * Creates a task manager with the tasks in file.
     * File has one task per line. On each line, the date the task must
     * be completed is first (mm-dd-yyyy), followed by a tab, followed by
     * a description of the task.
     */
    public TaskManager(String file){
        super();
        try {
            Scanner scan = new Scanner(new File(file));
            while (scan.hasNextLine()){
                String line = scan.nextLine();
                //split each line at tab
                String[] splitline= line.split("\t");
                //check for length and get parts to add in
                if (splitline.length==2){
                    addTask(splitline[0], splitline[1]);
                }
            }
        } catch (FileNotFoundException except){
            System.out.println("File not found: empty task manager loaded.");
        }
    }

    /**
     * Adds a new task. (Newer tasks with the same date go first.)
     * @param date the date the new task is due in the format mm-dd-yyyy
     * @param description a description of the new task
     */
    public void addTask(String date, String description){
        //convert to task and add
        Task t=new Task(date, description);
        add(t);
    }

    /**
     * Returns the due date for the task that is due soonest.
     */
    public Date getNextTaskDate(){
        //peek and return date
        Task p=peek();
        return p.getDueDate();
    }

    /**
     * Returns the description for the task that is due soonest.
     */
    public String getNextTaskDescription(){
        //peek and return description
        Task p=peek();
        return p.getDescription();
    }

    /**
     * Removes the task that is due the soonest. Returns true
     * if a task was removed; returns false if there were no tasks
     * to remove.
     */
    public boolean removeNextTask(){
        //test for empty
        if (isEmpty()){
            return false;
        } else {
            poll();
            return true;
        }
    }

    /**
     * Writes out all tasks to a file so that they can be loaded
     * by the TaskManager(String file) constructor. Returns true
     * if the file could be written. The TaskManager should still
     * have all of the same tasks after this method is called as it
     * did before the method was called. However, it may be that tasks
     * with the same due date now are removed in a different order.
     */
    public boolean saveTasks(String outFile){
        //open file for writing
        try {
            File out=new File(outFile);
            PrintWriter writer=new PrintWriter(out);
            //pop and write to file, copying over to array at same time
            Task[] copy=new Task[getLength()+1];
            int i=1;
            while (!isEmpty()){
                Task next=poll();
                //change date format
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                StringBuffer in=new StringBuffer();
                FieldPosition f=new FieldPosition(0);
                StringBuffer date=formatter.format(next.getDueDate(), in, f);
                writer.print(date+"\t"+next.getDescription());
                writer.println();
                copy[i]=next;
                i++;
            }
            writer.flush();
            //and add back in in order
            for (int j=1;j<copy.length;j++){
                add(copy[j]);
                System.out.println("Adding back: "+copy[j]);
            }
            System.out.println("Tasks saved");
            return true;
        } catch (IOException except){
            System.out.println("Exception in out file, could not write tasks to file.");
            return false;
        }
    }
    
    /** Main method for command line interaction */
    public static void main(String[] args){
        //check for arg length
        if (args.length!=1){
            System.out.println("Incorrect command line: please give the name of a file with tasks");
        } else {
            //load the file and make instance
            TaskManager tasks=new TaskManager(args[0]);
            //start prompting
            Scanner scanner=new Scanner(System.in);
            System.out.println("Welcome to the Task Manager!");
            System.out.println("Enter a command: ");
            String mode=scanner.nextLine();
            //strip white space from mode
            mode=mode.trim();
            while (!mode.equals("exit")){
                if (mode.equals("task completed")){
                    if (tasks.isEmpty()){
                        System.out.println("Sorry, nothing else to do.");
                    } else {
                        String desc=tasks.getNextTaskDescription();
                        System.out.println("Good job, you finished: "+desc);
                        tasks.removeNextTask();
                        if (tasks.isEmpty()){
                            System.out.println("Nice, no more to do!");
                        } else {
                            System.out.println("Here's what's next: ");
                            //alter date format
                            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                            StringBuffer in=new StringBuffer();
                            FieldPosition f=new FieldPosition(0);
                            StringBuffer date=formatter.format(tasks.getNextTaskDate(), in, f);
                            System.out.println("   Next task is due "+date);
                            System.out.println("   Task description: "+tasks.getNextTaskDescription());
                        }
                    }
                } else if (mode.equals("show next task")){
                     if (tasks.isEmpty()){
                        System.out.println("Sorry, nothing else to do.");
                    } else {
                        //alter date format
                        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                        StringBuffer in=new StringBuffer();
                        FieldPosition f=new FieldPosition(0);
                        StringBuffer date=formatter.format(tasks.getNextTaskDate(), in, f);
                        System.out.println("Next task is due "+date);
                        System.out.println("Task description: "+tasks.getNextTaskDescription());
                     }
                } else if (mode.equals("save tasks")){
                    System.out.println("Please enter a filename to save tasks to: ");
                    String file=scanner.nextLine();
                    tasks.saveTasks(file);
                } else {
                    System.out.println("Sorry, invalid command.  Try one of the following: exit, task completed, show next task, save tasks.");
                }
                //get next mode
                System.out.println("Enter a command: ");
                mode=scanner.nextLine();
                mode=mode.trim();
            }
        }
    }    
}