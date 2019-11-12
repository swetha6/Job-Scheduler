/*
	 * @author: Swetha Kondubhatla
	 * 
	 */

public class Scheduler {

	public static Job J1;
	public static RedBlackTree rbt;
	public static HeapImpl hp;
	public static Integer global_timer = 0;
	public static Integer jobrun_timer = 0;
	public static Job J;

	// Impl: Scheduler in Heap
	public void scheduleHeap(HeapImpl hp, RedBlackTree rbt) {
		/*
		 * keep scheduling the job with the conditions until next operation
		 * arrives in. input.t value gives the time till which the next
		 * operation comes in.
		 */
		for (int u = 0; u < jobscheduler.t; u++) {
			/*
			 * If the local job timer Jobrun_timer variable is a multiple of 5
			 * implies, the job has run for 5 ms. If the job J has run for 5
			 * seconds, take the new job from the heap. J1 is the current active
			 * job in the Scheduler
			 */
			if (jobrun_timer % 5 == 0) {
				J = hp.remove();
				rbt.delete(J);
				J1 = J;
			}
			/*
			 * If the job has not run for 5 ms and, there is still some
			 * execution time left, Schedule the same job and remove its entry
			 * from both the trees as new entry will be inserted.
			 */
			else if (J1.remaining_time > 0) {
				hp.remove_job(J1);
				rbt.delete(J1);
			}
			/*
			 * If the job has not run for 5 ms and, there is no execution time
			 * left, remove it from both the trees as the job has been
			 * completed. The local Jobrun_timer value will be set to 0 as new
			 * job has to be taken.
			 */
			else if (J1.remaining_time <= 0) {
				J = hp.remove();
				rbt.delete(J);
				J1 = J;
				jobrun_timer = 0;
			}

			// System.out.println("k is key "+J1.JobId);
			if (J1 == null) {
				global_timer++;
			}
			/*
			 * Schedule the job and re insert the job into both the trees
			 */
			else if (J1.executed_time >= 0) {
				J1.executed_time = J1.executed_time + 1;
				J1.remaining_time = J1.remaining_time - 1;
				jobrun_timer++;
				global_timer++;
				if (J1.remaining_time > 0) {
					hp.insert(J1);
					rbt.insert(J1);
				}

			}
			// hp.print();
		}
	}

	/*
	 * If we reach the End of the file, all the remaining jobs in the CPU must
	 * be scheduled for the remaining time until they are completed. This method
	 * handles that case.
	 */
	public void scheduleHeap_remaining(HeapImpl hp, RedBlackTree rbt) {
		while (hp.getSize() >= 1) {
			// System.out.println("::"+seconds);
			if (jobrun_timer % 5 == 0) {
				J = hp.remove();
				J1 = J;
			} else if (J1.remaining_time > 0) {
				hp.remove_job(J1);
				rbt.delete(J1);
			} else if (J1.remaining_time <= 0) {
				J = hp.remove();
				J1 = J;
				jobrun_timer = 0;
			}
			global_timer++;

			if (J1.executed_time >= 0) {
				J1.executed_time = J1.executed_time + 1;
				J1.remaining_time = J1.remaining_time - 1;
				jobrun_timer++;
				if (J1.remaining_time > 0) {
					hp.insert(J1);
					rbt.insert(J1);
				}
			}
			// hp.print();
		}
	}
}
