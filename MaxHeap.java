import java.util.Arrays;

public final class MaxHeap<T extends Comparable<? super T>> implements MaxHeapInterface<T> {
    private T[] heap;
    private int lastIndex;
    private boolean integrityOk = false;
    private static final int DEFAULT_CAPACITY = 5;
    private static final int MAX_CAPACITY = 10000;

    public MaxHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MaxHeap(int initialCapacity) {
        integrityOk = false;
        checkCapacity(initialCapacity);

        T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
        integrityOk = true;
    }

    public MaxHeap(T[] entries) {
        this(entries.length);
        lastIndex = entries.length;

        for (int index = 1; index <= lastIndex; index++)
            heap[index] = entries[index - 1];

        for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--)
            reheap(rootIndex);
    }

    public void add(T newEntry) {
        checkIntegrity();
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;

        while (parentIndex > 0 && newEntry.compareTo(heap[parentIndex]) > 0) {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        }

        heap[newIndex] = newEntry;
        lastIndex++;
        ensureCapacity();
    }

    public T removeMax() {
        checkIntegrity();
        T root = null;

        if (!isEmpty()) {
            root = heap[1];
            heap[1] = heap[lastIndex];
            lastIndex--;
            reheap(1);
        }

        return root;
    }

    public T getMax() {
        checkIntegrity();
        if (isEmpty()) return null;
        return heap[1];
    }

    public boolean isEmpty() {
        return lastIndex < 1;
    }

    public int getSize() {
        return lastIndex;
    }

    public void clear() {
        while (lastIndex > -1) {
            heap[lastIndex] = null;
            lastIndex--;
        }
        lastIndex = 0;
    }

    private void reheap(int rootIndex) {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;

        while (!done && leftChildIndex <= lastIndex) {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;

            if (rightChildIndex <= lastIndex &&
                    heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
                largerChildIndex = rightChildIndex;
            }

            if (orphan.compareTo(heap[largerChildIndex]) < 0) {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex;
            } else {
                done = true;
            }
        }

        heap[rootIndex] = orphan;
    }

    private void ensureCapacity() {
        if (lastIndex >= heap.length - 1) {
            int newCapacity = heap.length * 2;
            checkCapacity(newCapacity);
            heap = Arrays.copyOf(heap, newCapacity);
        }
    }

    private void checkIntegrity() {
        if (!integrityOk)
            throw new SecurityException("MaxHeap is corrupt!");
    }

    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to exceed max capacity!");
    }
}
