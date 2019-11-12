
/*
	 * @author: Swetha Kondubhatla
	 * 
	 */

import java.util.ArrayList;

public class HeapImpl {
	private static ArrayList<Job> Heap;
	private static final int FRONT = 1;

	public HeapImpl() {

		Heap = new ArrayList<Job>();
		Heap.add(null);

	}

	private int parent(int pos) {
		return pos / 2;
	}

	private int leftChild(int pos) {
		return (2 * pos);
	}

	private int rightChild(int pos) {
		return (2 * pos) + 1;
	}

	private boolean isLeaf(int pos) {
		if (pos >= (Heap.size() / 2) && pos <= Heap.size()) {
			return true;
		}
		return false;
	}

	private void swap(int fpos, int spos) {
		Job tmp;
		tmp = Heap.get(fpos);
		Heap.set(fpos, Heap.get(spos));
		Heap.set(spos, tmp);
	}

	public void minHeapify(int pos) {
		if (!isLeaf(pos)) {
			if ((Heap.get(pos)).executed_time > Heap.get(leftChild(pos)).executed_time
					|| (Heap.get(pos)).executed_time > Heap.get(rightChild(pos)).executed_time) {
				if (Heap.get(leftChild(pos)).executed_time < Heap.get(rightChild(pos)).executed_time) {
					swap(pos, leftChild(pos));
					minHeapify(leftChild(pos));
				} else {
					swap(pos, rightChild(pos));
					minHeapify(rightChild(pos));
				}
			}
		}
	}

	// Insert a new job into the heap. Swap with the child pointers to maintain
	// the minheap property
	public void insert(Job job) {
		Heap.add(job);
		int current = Heap.size() - 1;

		while (current != 1 && (Heap.get(current).executed_time < Heap.get(parent(current)).executed_time)) {
			swap(current, parent(current));
			current = parent(current);
		}
		// printHeap();
	}

	// Prints the heap
	public void print() {
		for (int i = 1; i <= Heap.size() - 1; i++) {
			System.out.print(
					Heap.get(i).JobId + " " + Heap.get(i).executed_time + " " + Heap.get(i).remaining_time + "\t");
		}
		System.out.println("\n");
	}

	// Maintains the heap property
	public void minHeap() {
		for (int pos = (Heap.size() - 1 / 2); pos >= 1; pos--) {
			minHeapify(pos);
		}
	}

	// Removes the root value from the heap
	public Job remove() {
		Job pr = null;
		if (Heap.size() > 1) {
			pr = Heap.get(FRONT);
			Heap.set(FRONT, Heap.get(Heap.size() - 1));
			minHeapify(FRONT);
			Heap.remove(Heap.size() - 1);
		}

		return pr;
	}

	// Remove a job J from the heap
	public int remove_job(Job J) {
		int g = 0;
		if (Heap.size() > 1) {
			g = Heap.indexOf(J);
			Heap.remove(g);
		}
		return g;
	}

	// returns the size of the heap
	public int getSize() {
		return Heap.size() - 1;
	}

}