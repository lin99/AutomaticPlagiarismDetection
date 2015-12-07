import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class ProblemC {

	public static class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
		/**
		 * Construct the tree.
		 */
		public BinarySearchTree() {
			root = null;
			cont = 0;
		}

		/**
		 * Insert into the tree; duplicates are ignored.
		 * 
		 * @param x
		 *            the item to insert.
		 */
		public void insert(AnyType x) {
			root = insert(x, root);
		}

		/**
		 * Remove from the tree. Nothing is done if x is not found.
		 * 
		 * @param x
		 *            the item to remove.
		 */
		public void remove(AnyType x) {
			root = remove(x, root);
		}

		/**
		 * Find the smallest item in the tree.
		 * 
		 * @return smallest item or null if empty.
		 * @throws Exception
		 */
		public AnyType findMin() throws Exception {
			if (isEmpty())
				throw new Exception();
			return findMin(root).element;
		}

		/**
		 * Find the largest item in the tree.
		 * 
		 * @return the largest item of null if empty.
		 * @throws Exception
		 */
		public AnyType findMax() throws Exception {
			if (isEmpty())
				throw new Exception();
			return findMax(root).element;
		}

		/**
		 * Find an item in the tree.
		 * 
		 * @param x
		 *            the item to search for.
		 * @return true if not found.
		 */
		public boolean contains(AnyType x) {
			return contains(x, root);
		}

		/**
		 * Make the tree logically empty.
		 */
		public void makeEmpty() {
			root = null;
		}

		/**
		 * Test if the tree is logically empty.
		 * 
		 * @return true if empty, false otherwise.
		 */
		public boolean isEmpty() {
			return root == null;
		}

		/**
		 * Print the tree contents in sorted order.
		 */
		public void printTree() {
			if (isEmpty())
				System.out.println("Empty tree");
			else
				printTree(root);
		}

		/**
		 * Internal method to insert into a subtree.
		 * 
		 * @param x
		 *            the item to insert.
		 * @param t
		 *            the node that roots the subtree.
		 * @return the new root of the subtree.
		 */
		private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
			if (t == null)
				return new BinaryNode<>(x, null, null);

			int compareResult = x.compareTo(t.element);
			cont++;
			if (compareResult < 0)
				t.left = insert(x, t.left);
			else if (compareResult > 0)
				t.right = insert(x, t.right);
			else
				; // Duplicate; do nothing
			return t;
		}

		/**
		 * Internal method to remove from a subtree.
		 * 
		 * @param x
		 *            the item to remove.
		 * @param t
		 *            the node that roots the subtree.
		 * @return the new root of the subtree.
		 */
		private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
			if (t == null)
				return t; // Item not found; do nothing

			int compareResult = x.compareTo(t.element);

			if (compareResult < 0)
				t.left = remove(x, t.left);
			else if (compareResult > 0)
				t.right = remove(x, t.right);
			else if (t.left != null && t.right != null) // Two children
			{
				t.element = findMin(t.right).element;
				t.right = remove(t.element, t.right);
			} else
				t = (t.left != null) ? t.left : t.right;
			return t;
		}

		/**
		 * Internal method to find the smallest item in a subtree.
		 * 
		 * @param t
		 *            the node that roots the subtree.
		 * @return node containing the smallest item.
		 */
		private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
			if (t == null)
				return null;
			else if (t.left == null)
				return t;
			return findMin(t.left);
		}

		/**
		 * Internal method to find the largest item in a subtree.
		 * 
		 * @param t
		 *            the node that roots the subtree.
		 * @return node containing the largest item.
		 */
		private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
			if (t != null)
				while (t.right != null)
					t = t.right;

			return t;
		}

		/**
		 * Internal method to find an item in a subtree.
		 * 
		 * @param x
		 *            is item to search for.
		 * @param t
		 *            the node that roots the subtree.
		 * @return node containing the matched item.
		 */
		private boolean contains(AnyType x, BinaryNode<AnyType> t) {
			if (t == null)
				return false;

			int compareResult = x.compareTo(t.element);

			if (compareResult < 0)
				return contains(x, t.left);
			else if (compareResult > 0)
				return contains(x, t.right);
			else
				return true; // Match
		}

		/**
		 * Internal method to print a subtree in sorted order.
		 * 
		 * @param t
		 *            the node that roots the subtree.
		 */
		private void printTree(BinaryNode<AnyType> t) {
			if (t != null) {
				printTree(t.left);
				System.out.println(t.element);
				printTree(t.right);
			}
		}

		/**
		 * Internal method to compute height of a subtree.
		 * 
		 * @param t
		 *            the node that roots the subtree.
		 */
		private int height(BinaryNode<AnyType> t) {
			if (t == null)
				return -1;
			else
				return 1 + Math.max(height(t.left), height(t.right));
		}

		// Basic node stored in unbalanced binary search trees
		private static class BinaryNode<AnyType> {
			// Constructors
			BinaryNode(AnyType theElement) {
				this(theElement, null, null);
			}

			BinaryNode(AnyType theElement, BinaryNode<AnyType> lt,
					BinaryNode<AnyType> rt) {
				element = theElement;
				left = lt;
				right = rt;
			}

			AnyType element; // The data in the node
			BinaryNode<AnyType> left; // Left child
			BinaryNode<AnyType> right; // Right child
		}

		/** The tree root. */
		private BinaryNode<AnyType> root;
		private int cont;

	}

	public static int vis[];

	public static void optimal(BinarySearchTree<Integer> bst, int l, int r,
			int arr[]) {
		if (l == r)
			return;
		int mid = (l + r) / 2;
		vis[mid]++;
		bst.insert(arr[mid]);
		optimal(bst, mid + 1, r, arr);
		optimal(bst, l, mid, arr);

	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan;
		File f = new File("C_1.txt");
		if (f.exists()) {
			scan = new Scanner(f);
			System.setOut( new PrintStream( "C_1Out.txt" ) );
		} else {
			scan = new Scanner(System.in);
		}
		int test = scan.nextInt();
		for (int t = 0; t < test; t++) {
			System.out.println("Case #" +(t+1)+":");
			int n = scan.nextInt();
			int arr[] = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = scan.nextInt();
			}
			BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
			BinarySearchTree<Integer> optimalBst = new BinarySearchTree<Integer>();
			for (int i = 0; i < arr.length; i++) {
				bst.insert(arr[i]);
			}
			vis = new int[n];
			Arrays.sort(arr);
			optimal(optimalBst, 0, n, arr);
			System.out.println(bst.cont + " " + optimalBst.cont);
		}
	}
}
