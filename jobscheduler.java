
/*
	 * @author: Swetha Kondubhatla
	 * 
	 */

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class jobscheduler {

	public static RedBlackTree rbt;
	public static HeapImpl hp;
	public static int n;
	public static int z;
	public static int y;
	public static int t;
	static File file = null;

	static ArrayList<Job> jobs = new ArrayList<Job>();
	static List<String> list = new ArrayList<String>();
	static List<Integer> timer = new ArrayList<Integer>();

	public static void main(String[] args) throws FileNotFoundException {
		// rbt first node
		rbt = new RedBlackTree();
		hp = new HeapImpl();

		/*File file = new File("input2");
		Scanner scan = new Scanner(file);

		/*
		 * Scanner user = new Scanner( System.in ); String inputFileName,
		 * outputFileName;
		 * 
		 * // prepare the input file // System.out.print("Input File Name: ");
		 * inputFileName = user.nextLine().trim(); File file = new File(
		 * inputFileName ); Scanner scan = new Scanner( file );
		 */

		
		  file = new File(args[0]); 
		  Scanner scan = new Scanner(file);
		 

		File file1 = new File("output_file.txt");
		FileOutputStream fos = new FileOutputStream(file1);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);

		while (scan.hasNextLine()) {
			list.add(scan.nextLine());
		}

		/*
		 * All the values in front of colon are the arrival times of the jobs.
		 * These are put into the timer list
		 */
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			String t = str.substring(0, str.indexOf(":"));
			int p = Integer.parseInt(t);
			timer.add(p);
		}
		timer.add(0);

		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			y = timer.get(i);
			z = timer.get(i + 1);
			// Run till t secs in Scheduler.java
			t = z - y;
			/*
			 * If arrival time = global timer, new operation should be performed
			 * based on strings present in the input file.
			 */

			if (y == Scheduler.global_timer) {

				/*
				 * If contains Insert, Insert the job in both the heap and Red
				 * Black Tree and call the Scheduler from the Scheduler.java
				 * class.
				 */
				if (str.contains("Insert")) {
					String ans1 = str.substring(str.indexOf("(") + 1, str.indexOf(","));
					int l = Integer.parseInt(ans1);
					String ans2 = str.substring(str.indexOf(",") + 1, str.indexOf(")"));
					int m = Integer.parseInt(ans2);
					jobs.add(new Job(hp, l, m, m));
					jobs.add(new Job(rbt, l, m, m));
					Scheduler sc = new Scheduler();
					sc.scheduleHeap(hp, rbt);

				}
				/*
				 * if String contains Print Job. 2 cases arise. 1: print job in
				 * the range and 2: print job with the given jobId While the job
				 * is printing, Schedule the job thereby running operations in
				 * parallel The print functions are in RedBlackTree.java
				 */
				else if (str.contains("PrintJob")) {
					if (str.contains(",")) {
						String answer1 = str.substring(str.indexOf("(") + 1, str.indexOf(","));
						String answer2 = str.substring(str.indexOf(",") + 1, str.indexOf(")"));
						int k1 = Integer.parseInt(answer1);
						int k2 = Integer.parseInt(answer2);
						rbt.printJob(k1, k2);
						System.out.println("");
						Scheduler sc = new Scheduler();
						sc.scheduleHeap(hp, rbt);
					} else {
						String answer = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
						int k = Integer.parseInt(answer);
						rbt.printJob(k);
						Scheduler sc = new Scheduler();
						sc.scheduleHeap(hp, rbt);
					}
				}

				/*
				 * If contains NextJob, print the NextJob and call the Scheduler
				 * from the Scheduler.java class. The NextJob is invoked from
				 * RedBlackTree.java
				 */

				else if (str.contains("NextJob")) {
					String answer = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
					int k = Integer.parseInt(answer);
					rbt.NextJob(k);
					Scheduler sc = new Scheduler();
					sc.scheduleHeap(hp, rbt);
				}

				/*
				 * If contains PreviousJob, print the PreviousJob and call the
				 * Scheduler from the Scheduler.java class. The PreviousJob is
				 * invoked from RedBlackTree.java
				 */
				else if (str.contains("PreviousJob")) {
					String answer = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
					int k = Integer.parseInt(answer);
					rbt.PreviousJob(k);
					Scheduler sc = new Scheduler();
					sc.scheduleHeap(hp, rbt);
				}
			}

		}
		// Keep running for the remaining amount of time till next operation
		// comes in
		Scheduler sc = new Scheduler();
		sc.scheduleHeap_remaining(hp, rbt);

	}

	/*
	 * Given a job-id value, return the corresponding Job value If there is no
	 * such job, return a job with (0,0,0) as its value.
	 */
	public static Job b;

	public static Job get_job(int j_id) {
		int i = 0;
		int k = 0;
		for (i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).JobId == j_id) {
				k = i;
				break;
			} else {
				k = -1;
			}
		}
		// set all the values of job b to (0,0,0)
		if (k > 0)
			return jobs.get(k);
		else {
			b = jobs.get(0);
			b.remaining_time = 0;
			b.total_time = 0;
			b.executed_time = 0;
			b.JobId = 0;
			return b;
		}

	}

}
