package org.csc301;

public class Heap<T extends HeapItem> {
	
	// Note the T is a parameter representing a type that extends the HeapItem interface
	// This a new way to use inheritance!

	protected T[] items; // Array that is used to store heap items. items[0] is the highest priority element.
	protected int maxHeapSize; // The capacity of the heap
	protected int currentItemCount; // How many elements we have currently on the heap

	public Heap(int maxHeapSize) {
		this.maxHeapSize = maxHeapSize;
		items = (T[]) new HeapItem[maxHeapSize];
		currentItemCount = 0; // heap is empty!
	}

	public boolean isEmpty() {
		return currentItemCount == 0;
	}

	public boolean isFull() {
		return currentItemCount == maxHeapSize;
	}

	public void add(T item) throws HeapFullException
	// Adds item T to its correct position on the heap
	{
		if (isFull())
			throw new HeapFullException();
		else {
			item.setHeapIndex(currentItemCount);
			items[currentItemCount] = item;  // the element is added to the bottom
			sortUp(item); // Move the element up to its legitimate place. Check the diagram on the handout!
			currentItemCount++;
		}
	}

	public boolean contains(T item)
	// Returns true if item is on the heap
	// Otherwise returns false
	{
		return items[item.getHeapIndex()].equals(item);
	}

	public int count() {
		return currentItemCount;
	}

	public void updateItem(T item) {
		sortUp(item);
	}

	public T removeFirst() throws HeapEmptyException
	// Removes and returns the element sitting on top of the heap
	{
		if (isEmpty())
			throw new HeapEmptyException();
		else {
			T firstItem = items[0]; // element of top of the heap is stored in firstItem variable
			currentItemCount--;
			items[0] = items[currentItemCount]; //last element moves on top
			items[0].setHeapIndex(0);
			sortDown(items[0]); // move the element to its legitimate position. Please check the diagram on the handout.
			return firstItem;
		}
	}
	
	private void sortUp(T item) {
		// Implement this method according to the diagram on the handout.
		// Also: the indices of children and parent elements satisfy some relationships.
		// The formulas are on the handout.
		if (!contains(item))
			return;
		if (item.getHeapIndex() == 0)
			return;
		int i = item.getHeapIndex();
		int parent = (i - 1)/2;
		if (items[i].compareTo(items[parent]) > 0)
			return;
		
		while (i > 0 && items[i].compareTo(items[parent]) < 0){
	         items[i] = items[parent]; 
	         i = parent;
	         parent = (parent-1) / 2;
		}
		items[i] = item;
	}

	private void sortDown(T item) {
		// Implement this method according to the diagram on the handout.
				// Also: the indices of children and parent elements satisfy some relationships.
				// The formulas are on the handout.
		// if item not in the heap, do nothing
		if (!contains(item)) 
			return;
				
		int i = item.getHeapIndex();
		int smaller;
	
        while ((i * 2 + 1) <= currentItemCount) {
    		int left = i * 2 + 1;
    		int right = i * 2 + 2;
            if(right <= currentItemCount && items[left].compareTo(items[right]) > 0) 
            	smaller = right;
            else
            	smaller = left;
            
            if (items[i].compareTo(items[smaller]) > 0) {
                T swap = items[i];
                items[i] = items[smaller];
                items[i].setHeapIndex(i);
                items[smaller] = swap;  
                items[smaller].setHeapIndex(smaller);
            } else {
            	break;
            }
            i = smaller;
		}
	}
}
	// You may implement additional helper methods if desired. Make sure to make them private!
