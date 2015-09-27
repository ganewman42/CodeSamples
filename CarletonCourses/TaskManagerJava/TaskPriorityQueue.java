/** Class to build a ternary priority queue of date and description task objects
* @author Grace Newman
* @date 3/7/2015
*/

public class TaskPriorityQueue implements PriorityQueue<Task>{
    //instance variables: knows the array of tasks 
    private Task[] heap;
    private int lastIndex;
    
    /** Creates an empty priority queue of initial size 10*/
    public TaskPriorityQueue(){
        heap = new Task[10];
        lastIndex=0;
    }
    
     /** Adds the given item to the queue. */
    public void add(Task item){
        int newIndex=lastIndex+1;
        int parentIndex=newIndex/3;
        //while it's less than the parent, bubble up
        while ((parentIndex >0) && (item.compareTo(heap[parentIndex]) < 0)){
            //move parent down
            heap[newIndex]=heap[parentIndex];
            //update positions
            newIndex=parentIndex;
            parentIndex=newIndex/3;
        }
        //set new entry
        heap[newIndex]=item;
        //don't forget to increment lastIndex
        lastIndex++;
        //ensure capacity
        ensureCapacity();
    }
    
    private void ensureCapacity(){
        //check capacity
        if (lastIndex==heap.length-1){
            //double size and copy over as needed
            Task[] newheap = new Task[2*heap.length];
            System.arraycopy(heap, 0, newheap, 0, heap.length);
            heap=newheap;
        }
    }
        
    
    /** Removes the first item according to compareTo from the queue, and returns it.
     * @return null if the queue is empty.
     */
    public Task poll(){
        //set root to null
        Task root = null;
        //check for empty
        if (!isEmpty()){
            //get return value
            root=heap[1];
            //swap up the last item, set that spot to null
            heap[1]=heap[lastIndex];
            heap[lastIndex]=null;
            lastIndex--;
            //and reheap
            reheap(1);
        }
        return root;
    }
    
    /** Reheap to make polling easier */
    private void reheap(int rootIndex){
        //initialize
        boolean done=false;
        Task orphan = heap[rootIndex];
        int leftIndex = 2*rootIndex;
        //while not at end and not done
        while (!done && (leftIndex <=lastIndex)){
            //get the smaller child
            //assume it's the left
            int smallIndex=leftIndex;
            int midIndex=leftIndex+1;
            int rightIndex=leftIndex+2;
            //change the smaller child as needed
            if (rightIndex<=lastIndex && heap[rightIndex].compareTo(heap[smallIndex])<0){
                smallIndex=rightIndex;
            } else if (rightIndex<=lastIndex && heap[midIndex].compareTo(heap[smallIndex])<0){
                smallIndex=midIndex;
            }
            //compare to the smallest child- if it's larger, move it down and continue
            if (orphan.compareTo(heap[smallIndex])>0){
                heap[rootIndex]=heap[smallIndex];
                rootIndex=smallIndex;
                leftIndex=2*rootIndex;
            } else {
                //if it's smaller, we're done
                done=true;
            }
        }
        heap[rootIndex]=orphan;
    }
                
    /** Returns the first item according to compareTo in the queue, without removing it.
     * @return null if the queue is empty.
     */
    public Task peek(){
        return heap[1];
    }
    
    /** Returns true if the queue is empty, or lastIndex is one. */
    public boolean isEmpty(){
        return lastIndex<1;
    }
    
    /** Removes all items from the queue. */
    public void clear(){
        //set every item to null and adjust lastIndex
        for (int i=1;i<heap.length;i++){
            heap[i]=null;
        }
        lastIndex=0;
    }
    
    /** Returns the length of the current queue (number of elements) */
    public int getLength(){
        return lastIndex;
    }
    
    /** Main method for testing */
    public static void main(String[] args){
        //test adding in proper order
        Task t1=new Task("10-22-2015","T1-last item");
        Task t2=new Task("10-21-2015","T2- second item");
        Task t3=new Task("9-20-2015","T3- first item");
        //create heap and add to
        TaskPriorityQueue tq=new TaskPriorityQueue();
        tq.add(t1);
        tq.add(t2);
        tq.add(t3);
        //check order
        System.out.println("Peek at irst item, should be T3: "+tq.peek().getDescription());
        tq.poll();
        System.out.println("Second item, should be T2: "+tq.poll().getDescription());
        System.out.println("Third item, should be T1: "+tq.poll().getDescription());
        //add 12 items to check resize works
        Task tm=new Task("10-22-2015","T1-last item");
        for (int i=0;i<13;i++){
            tq.add(tm);
        }
        System.out.println("Resize worked");
        //clear and check for empty
        tq.clear();
        System.out.println("Cleared queue, should be empty: "+tq.isEmpty());   
    }
        
}