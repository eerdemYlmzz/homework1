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
