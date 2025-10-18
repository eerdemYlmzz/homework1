
 //EX-1
//OUTPUT
------------------------------------------
The heap is not empty; it contains 6 entries.
The largest entry is 21


Removing entries in descending order:
Removing 21
Removing 20
Removing 15
Removing 14
Removing 10
Removing 2

-------------------------------------------------------------------------

public class Test {
	public static void main(String[] args) {
		Integer[] A = {14, 20, 2, 15, 10, 21};
		
		MaxHeapInterface<Integer> maxHeap = new MaxHeap<>(A); 
		
		if (maxHeap.isEmpty())
			System.out.println("The heap is empty!!");
		else
			System.out.println("The heap is not empty, it contains: " +
					maxHeap.getSize() + " entries");
		
		System.out.println("Largest entry is " + maxHeap.getMax());
		
		System.out.println("Removing entries in descending order:");
		while (!maxHeap.isEmpty())
			System.out.println("Removing " + maxHeap.removeMax());
	}
}


----------------------------------------------

-------------------------------------------------------------------------------------------------------------
//EXERCISE-3

import java.util.Arrays;
public final class MinHeap<T extends Comparable<? super T>>
implements MinHeapInterface<T>
{
   private T[] heap;      
   private int lastIndex; 
   private boolean IntegrityOk = false;
   private static final int DEFAULT_CAPACITY = 5; 
   private static final int MAX_CAPACITY = 10000;
   
   public MinHeap()
   {
      this(DEFAULT_CAPACITY); 
   } 

	public MinHeap(int initialCapacity)
	{
      IntegrityOk = false;
      checkCapacity(initialCapacity);

		T[] tempHeap = (T[])new Comparable[initialCapacity + 1]; 
		heap = tempHeap;
		lastIndex = 0;
		IntegrityOk = true;
	} 
   
   public MinHeap(T[] entries)
   {
      this(entries.length); 
      lastIndex = entries.length;

      for (int index = 1; index <= lastIndex; index++)
         heap[index] = entries[index - 1];
      
      for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--)
         reheap(rootIndex);
   } 

   public void add(T newEntry)
   {
      checkIntegrity();        
      int newIndex = lastIndex + 1;
      int parentIndex = newIndex / 2;
      
      while ( (parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0) 
      {
         heap[newIndex] = heap[parentIndex];
         newIndex = parentIndex;
         parentIndex = newIndex / 2;
      } 
      
      heap[newIndex] = newEntry;
      lastIndex++;
      ensureCapacity();
   } 

   public T removeMin()
   {
      checkIntegrity();             
      T root = null;
      
      if (!isEmpty())
      {
         root = heap[1];            
         heap[1] = heap[lastIndex]; 
         lastIndex--;               
         reheap(1);                 
      } 
      
      return root;
   } 

	public T getMin()
	{
		checkIntegrity();
		T root = null;

		if (!isEmpty())
			root = heap[1];

		return root;
	} 

	public boolean isEmpty()
	{
		return lastIndex < 1;
	} 

	public int getSize()
	{
		return lastIndex;
	} 

	public void clear()
	{
		checkIntegrity();
		while (lastIndex > -1)
		{
			heap[lastIndex] = null;
			lastIndex--;
		} 

		lastIndex = 0;
	} 

   private void reheap(int rootIndex)
   {
      boolean done = false;
      T orphan = heap[rootIndex];
      int leftChildIndex = 2 * rootIndex;
      
      while (!done && (leftChildIndex <= lastIndex) )
      {
         int smallerChildIndex = leftChildIndex; 
         int rightChildIndex = leftChildIndex + 1;
         
         if ( (rightChildIndex <= lastIndex) &&
             heap[rightChildIndex].compareTo(heap[smallerChildIndex]) < 0)
         {
            smallerChildIndex = rightChildIndex;
         } 
         
         if (orphan.compareTo(heap[smallerChildIndex]) > 0)
         {
            heap[rootIndex] = heap[smallerChildIndex];
            rootIndex = smallerChildIndex;
            leftChildIndex = 2 * rootIndex;
         }
         else
            done = true;
      } 
      
      heap[rootIndex] = orphan;
   } 

	private void ensureCapacity()
	{
      int numberOfEntries = lastIndex;
      int capacity = heap.length - 1;
      if (numberOfEntries >= capacity)
      {
         int newCapacity = 2 * capacity;
         checkCapacity(newCapacity); 
         heap = Arrays.copyOf(heap, newCapacity + 1);
      } 
   } 

	private void checkIntegrity()
	{
		if (!IntegrityOk)
			throw new SecurityException ("MinHeap object check is failed!");
	} 

	private void checkCapacity(int capacity)
	{
		if (capacity < DEFAULT_CAPACITY)
         throw new IllegalStateException("Attempt to create a heap " +
                                         "whose capacity is smaller than " +
                                         DEFAULT_CAPACITY);
		else if (capacity > MAX_CAPACITY)
			throw new IllegalStateException("Attempt to create a heap " +
											        "whose capacity is larger than " +
											        MAX_CAPACITY);
	} 
}
//END