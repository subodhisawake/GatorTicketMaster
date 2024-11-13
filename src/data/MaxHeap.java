package data;

import models.User;
import java.util.ArrayList;
import java.util.List;

public class MaxHeap<T extends Comparable<T>> {
    private List<T> heap;

    public MaxHeap() {
        this.heap = new ArrayList<>();
    }

    public void insert(T key) {
        heap.add(key);
        heapifyUp(heap.size() - 1);
    }

    public T extractTop() {
        if (heap.isEmpty()) {
            return null;
        }
        if (heap.size() == 1) {
            return heap.remove(0);
        }
        T root = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        heapifyDown(0);
        return root;
    }

    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;
        if (index > 0 && heap.get(index).compareTo(heap.get(parentIndex)) > 0) {
            swap(index, parentIndex);
            heapifyUp(parentIndex);
        }
    }

    private void heapifyDown(int index) {
        int largest = index;
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;

        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).compareTo(heap.get(largest)) > 0) {
            largest = leftChildIndex;
        }

        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).compareTo(heap.get(largest)) > 0) {
            largest = rightChildIndex;
        }

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void remove(T item) {
        int index = findIndex(item);
        if (index == -1) return;  // Item not found in heap

        // Swap with last element and remove it
        swap(index, heap.size() - 1);
        heap.remove(heap.size() - 1);

        // Restore heap property
        if (index < heap.size()) {
            heapifyDown(index);
            heapifyUp(index);
        }
    }

    public void update(T oldItem, T newItem) {
        int index = findIndex(oldItem);
        if (index == -1) return;  // Item not found in heap

        // Update item at index with new value
        heap.set(index, newItem);

        // Restore heap property
        heapifyDown(index);
        heapifyUp(index);
    }

    private int findIndex(T item) {
        for (int i = 0; i < heap.size(); i++) {
            if (item instanceof User && heap.get(i) instanceof User) {
                User searchUser = (User) item;
                User heapUser = (User) heap.get(i);
                if (searchUser.getUserID() == heapUser.getUserID()) {
                    return i;
                }
            } else if (heap.get(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }
}