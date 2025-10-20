import java.util.Arrays;

public final class MinHeap<T extends Comparable<? super T>> implements MinHeapInterface<T> {
    private T[] heap;
    private int lastIndex;
    private boolean IntegrityOk = false;
    private static final int DEFAULT_CAPACITY = 5;
    private static final int MAX_CAPACITY = 10000;

    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MinHeap(int initialCapacity) {
        IntegrityOk = false;
        checkCapacity(initialCapacity);

        T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
        IntegrityOk = true;
    }

    public MinHeap(T[] entries) {
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

        while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0) {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        }

        heap[newIndex] = newEntry;
        lastIndex++;
        ensureCapacity();
    }

    public T removeMin() {
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

    public T getMin() {
        checkIntegrity();
        T root = null;

        if (!isEmpty())
            root = heap[1];

        return root;
    }

    public boolean isEmpty() {
        return lastIndex < 1;
    }

    public int getSize() {
        return lastIndex;
    }

    public void clear() {
        checkIntegrity();
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

        while (!done && (leftChildIndex <= lastIndex)) {
            int smallerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildInde
