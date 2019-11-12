/*
	 * @author: Swetha Kondubhatla
	 * 
	 */

public class Job {

	public int JobId;
	public long remaining_time;
	public long executed_time;
	public long total_time;
	public RedBlackTree rbt;
	public HeapImpl heap;

	// Inserts the job into the heap with the below parameters
	public Job(HeapImpl hp, int newId, long newExecTime, long newremaining) {

		executed_time = 0;
		JobId = newId;
		remaining_time = newExecTime;
		total_time = remaining_time - executed_time;
		heap = hp;
		heap.insert(this);

	}

	// Inserts the job into the rbt with the below parameters
	public Job(RedBlackTree rbtree, int newId, long newExecTime, long newremaining) {

		executed_time = 0;
		JobId = newId;
		remaining_time = newExecTime;
		total_time = remaining_time - executed_time;
		rbt = rbtree;
		rbt.insert(this);

	}

}
