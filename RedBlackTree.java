/*
	 * @author: Swetha Kondubhatla
	 * 
	 */
public class RedBlackTree {

	private final int RED = 0;
	private final int BLACK = 1;
	public static int count = 0;
	public static int NodeCount = 0;

	public class Node {

		public Job job = null;
		int color = BLACK;
		Node left = nil, right = nil, parent = nil;

		Node(Job p) {
			this.job = p;
			NodeCount++;
		}
	}

	private final Node nil = new Node(null);
	private Node root = nil;

	/*
	 * This function Prints the values of the jobs in the range [low,high]. This
	 * makes use of the helper function Print which returns the node. The
	 * variable count keeps track of number of nodes in the helper function. If
	 * the number of nodes are 0, then it prints (0,0,0)
	 */
	public void printJob(int low, int high) {
		count = 0;
		Print(root, low, high);
		if (count == 0)
			System.out.print("(0,0,0)");
	}

	/*
	 * Prints the node in the given range in O(log(n) + S) complexity
	 */
	void Print(Node mynode, int low, int high) {
		// if node doesn't exist return null
		if (mynode == nil) {
			return;
		}
		// go this loop only if low < mynode.job.JobId
		if (low < mynode.job.JobId)
			Print(mynode.left, low, high);

		if (low <= mynode.job.JobId && high >= mynode.job.JobId) {
			count++;
			if (count == 1)
				System.out.print(
						"(" + mynode.job.JobId + "," + mynode.job.executed_time + "," + mynode.job.total_time + ")");
			else
				System.out.print(
						",(" + mynode.job.JobId + "," + mynode.job.executed_time + "," + mynode.job.total_time + ")");
		}
		// go this loop only if low < mynode.job.JobId
		if (high > mynode.job.JobId)
			Print(mynode.right, low, high);
	}

	/*
	 * Print the job for a given j job_id. if no such job exists print (0,0,0)
	 */
	public void printJob(int j_id) {
		J = jobscheduler.get_job(j_id);
		Node n = new Node(J);
		if (n == nil) {
			return;
		}
		if (n.job.executed_time == n.job.total_time) {
			System.out.println("(0,0,0)");
		} else
			System.out.println("(" + n.job.JobId + "," + n.job.executed_time + "," + n.job.total_time + ")");
	}

	public Job J = null;
	jobscheduler inp = new jobscheduler();
	Node goal, temp, temp2;
	double min = Double.MAX_VALUE;

	/*
	 * Prints the triplet for the job with smallest ID greater than a given
	 * jobID. Print (0,0,0) if there is no such job. It uses the helper,
	 * closestValue functions to calculate the next_job
	 */

	public void NextJob(int j_id) {
		J = jobscheduler.get_job(j_id);
		Node n = root;
		if (n == nil) {
			System.out.println("(0,0,0)");
			return;
		}
		temp = closestValue(n, j_id);
		if (temp == null) {
			System.out.println("(0,0,0)");
		} else
			System.out.println("(" + temp.job.JobId + "," + temp.job.executed_time + "," + temp.job.total_time + ")");

	}

	public Node closestValue(Node root, double target) {
		helper(root, target);
		return goal;
	}

	public void helper(Node root, double target) {
		if (root.job == null) {
			return;
		}
		if ((root.job.JobId - target) < min && (root.job.JobId) > target && (root.job.JobId - target) != 0) {
			min = (root.job.JobId - target);
			goal = root;
		} else if (root.job.JobId == target) {
			goal = null;
		}

		if (target < root.job.JobId && root.left != null) {
			helper(root.left, target);
		} else if (root.right != null) {
			helper(root.right, target);
		}
	}

	public Job J1 = null;
	jobscheduler inp1 = new jobscheduler();
	Node goal_prev, temp1;
	double min1 = Double.MAX_VALUE;

	/*
	 * Prints the triplet for the job with greatest ID smaller than a given
	 * jobID. Print (0,0,0) if there is no such job. It uses the helper_prev,
	 * closestValue_Prev functions to calculate the Previous_job
	 */

	public void PreviousJob(int j_id) {
		J1 = jobscheduler.get_job(j_id);
		// System.out.println(J1.JobId);
		Node n = root;
		// double t = n.job.JobId;
		if (n == nil) {
			System.out.println("(0,0,0)");
			return;
		}
		temp1 = closestValue_Prev(n, j_id);
		if (temp1 == null) {
			System.out.println("(0,0,0)");
		} else
			System.out
					.println("(" + temp1.job.JobId + "," + temp1.job.executed_time + "," + temp1.job.total_time + ")");

	}

	public Node closestValue_Prev(Node root, double target) {
		helper_prev(root, target);
		return goal_prev;
	}

	public void helper_prev(Node root, double target) {
		if (root.job == null) {
			return;
		}
		// System.out.println("target"+target+"actual"+root.job.JobId);
		if ((target - root.job.JobId) < min1 && (root.job.JobId) < target) {
			min1 = (target - root.job.JobId);
			goal_prev = root;
		} else if (goal_prev.job.JobId == target) {
			goal_prev = null;
		}

		if (target <= root.job.JobId && root.left != null) {
			helper_prev(root.left, target);
		} else if (root.right != null) {
			helper_prev(root.right, target);
		}
	}

	// This function finds the node in a given RedBlackTree
	private Node findNode(Node findNode, Node node) {

		if (root == nil) {
			return null;
		}

		if (findNode.job.JobId < node.job.JobId) {
			if (node.left != nil) {
				return findNode(findNode, node.left);
			}
		} else if (findNode.job.JobId > node.job.JobId) {
			if (node.right != nil) {
				return findNode(findNode, node.right);
			}
		} else if (findNode.job.JobId == node.job.JobId) {
			return node;
		}
		return null;
	}

	// Inserts a new job key in the tree
	public void insert(Job key) {

		Node node = new Node(key);
		Node temp = root;
		if (root == nil) {
			root = node;
			node.color = BLACK;
			node.parent = nil;
		} else {
			node.color = RED;
			while (true) {
				if (node.job.JobId < temp.job.JobId) {
					if (temp.left == nil) {
						temp.left = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.left;
					}
				} else if (node.job.JobId >= temp.job.JobId) {
					if (temp.right == nil) {
						temp.right = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.right;
					}
				}
			}
			fixTree(node);
		}

	}

	// Takes as argument the newly inserted node
	private void fixTree(Node node) {
		while (node.parent.color == RED) {
			Node uncle = nil;
			// If parent is left child
			if (node.parent == node.parent.parent.left) {
				uncle = node.parent.parent.right;

				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.right) {
					// Double rotation needed
					node = node.parent;
					rotateLeft(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;

				rotateRight(node.parent.parent);
			} else {

				uncle = node.parent.parent.left;
				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.left) {
					// Double rotation needed
					node = node.parent;
					rotateRight(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				rotateLeft(node.parent.parent);
			}
		}
		root.color = BLACK;
	}

	void rotateLeft(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				// node is left child
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
			node.right.parent = node.parent;
			node.parent = node.right;
			if (node.right.left != nil) {
				node.right.left.parent = node;
			}
			node.right = node.right.left;
			node.parent.left = node;
		} else {
			// Need to rotate root
			Node right = root.right;
			root.right = right.left;
			right.left.parent = root;
			root.parent = right;
			right.left = root;
			right.parent = nil;
			root = right;
		}
	}

	void rotateRight(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}

			node.left.parent = node.parent;
			node.parent = node.left;
			if (node.left.right != nil) {
				node.left.right.parent = node;
			}
			node.left = node.left.right;
			node.parent.right = node;
		} else {// Need to rotate root
			Node left = root.left;
			root.left = root.left.right;
			left.right.parent = root;
			root.parent = left;
			left.right = root;
			left.parent = nil;
			root = left;
		}
	}

	// Deletes whole tree
	void deleteTree() {
		root = nil;
	}

	void transplant(Node target, Node with) {
		if (target.parent == nil) {
			root = with;
		} else if (target == target.parent.left) {
			target.parent.left = with;
		} else
			target.parent.right = with;
		with.parent = target.parent;
	}

	// delete the job key in the tree
	boolean delete(Job key) {
		Node z = new Node(key);
		if ((z = findNode(z, root)) == null)
			return false;
		Node x;
		Node y = z; // temporary reference y
		int y_original_color = y.color;

		if (z.left == nil) {
			x = z.right;
			transplant(z, z.right);
		} else if (z.right == nil) {
			x = z.left;
			transplant(z, z.left);
		} else {
			y = treeMinimum(z.right);
			y_original_color = y.color;
			x = y.right;
			if (y.parent == z)
				x.parent = y;
			else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (y_original_color == BLACK)
			deleteFixup(x);
		return true;
	}

	// handles all the 6 delete cases
	void deleteFixup(Node x) {
		while (x != root && x.color == BLACK) {
			if (x == x.parent.left) {
				Node w = x.parent.right;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					rotateLeft(x.parent);
					w = x.parent.right;
				}
				if (w.left.color == BLACK && w.right.color == BLACK) {
					w.color = RED;
					x = x.parent;
					continue;
				} else if (w.right.color == BLACK) {
					w.left.color = BLACK;
					w.color = RED;
					rotateRight(w);
					w = x.parent.right;
				}
				if (w.right.color == RED) {
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.right.color = BLACK;
					rotateLeft(x.parent);
					x = root;
				}
			} else {
				Node w = x.parent.left;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					rotateRight(x.parent);
					w = x.parent.left;
				}
				if (w.right.color == BLACK && w.left.color == BLACK) {
					w.color = RED;
					x = x.parent;
					continue;
				} else if (w.left.color == BLACK) {
					w.right.color = BLACK;
					w.color = RED;
					rotateLeft(w);
					w = x.parent.left;
				}
				if (w.left.color == RED) {
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.left.color = BLACK;
					rotateRight(x.parent);
					x = root;
				}
			}
		}
		x.color = BLACK;
	}

	// calculates tree minimum. This method is uses in delete operation
	Node treeMinimum(Node subTreeRoot) {
		while (subTreeRoot.left != nil) {
			subTreeRoot = subTreeRoot.left;
		}
		return subTreeRoot;
	}

}