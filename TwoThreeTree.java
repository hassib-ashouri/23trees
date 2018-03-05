import java.util.ArrayList;
import java.util.Collections;

class TwoThreeTree
{
	private Node root;

	public TwoThreeTree()
	{
		super();
	}

	/**
	 * insert a val in the tree. discard dublicats.
	 * @return true for inserting a new value. False for dublicates.
	 * @insertionType top-down insertion
	 */
	public boolean insert(int n)
	{
        if(isEmpty())
        {
            this.root = new Node(n);
            return true;
        }
        return this.root.addKeyRecurs(n, null);
	}
	/**
	 * search the tree structure.
	 */
	public String search(int n)
	{
		return this.root.search(n);
	}


	/**
	 * checks for an empty tree.
	 * @return true for an empty tree.
	 */
	public boolean isEmpty()
	{
		return this.root == null;
	}

	
	/**
	 * the reason this node class was internal is because its methods
	 * hace implementation that is specifi for the 23tree data structure.
	 * Therefore, its internal, not for general use.
	 */
	private class Node implements Comparable<Node> 
	{

		private ArrayList<Integer> keys;
		private ArrayList<Node> children;

		public Node(int key) 
		{
			this.keys = new ArrayList<>(3);
			this.keys.add(key);
			this.children = new ArrayList<>(4);
		}

		/**
		 * seach method returns the keys of the distination node whether key is found or not.
		 */
		public String search(int n)
		{
			//print the keys if its a leaf node or if the key is found
			if(children.size() == 0 || keys.indexOf(n) != -1)
				return getKeysString();

			//find where to make the recursive call.
			int i = 0;
			while(i < keys.size() && n > keys.get(i))
				i++;

			return children.get(i).search(n);
		}

		/**
		 * recursive implementation for adding keys to the tree.
		 */
		public boolean addKeyRecurs(int n, Node currentP )
		{
			boolean result = true;

			//stop if the key exists
			if(keys.contains(n))
				return false;
			
			//if the current node is a leaf, stop and add key.
			if(children.size() == 0)
			{
				keys.add(n);
				Collections.sort(keys);
			}
			else
			{//make the recursive call on the right child
				int i = 0;
				while(i < keys.size() && n > keys.get(i))
					i++;
				
				result = children.get(i).addKeyRecurs(n,this);
			}

			//split when there are three keys.
			if(keys.size() == 3)
				split(currentP);
			
			return result;
		}

		/**
		 * this method splits any node that has three keys, and increases the hieght of the tree
		 * if the root has three keys.
		 * @param parent it could be null to indicate the absence of a parent.
		 */
		public void split( Node parent )
		{
			Node left = new Node(keys.get(0));
			for(int i = 0; i < children.size()/2 ; i++)
				left.children.add( children.get(i) );

			Node right = new Node(keys.get(this.keys.size() - 1));
			for(int i = children.size()/2 ; i < children.size() ; i++)
				right.children.add( children.get(i) );


			if(parent == null)
			{//this is root. we creat a new tree level.
				keys.remove(0);
				keys.remove(keys.size() -1);
				this.children = new ArrayList<>(4);
				children.add(left);
				children.add(right);
			}
			else
			{//insert into the parent. no need for a new tree level yet.
				parent.keys.add( keys.get(1) );
				parent.children.add(left);
				parent.children.add(right);
				parent.children.remove(this);
				Collections.sort(parent.children);
				Collections.sort(parent.keys);
			}
		}


		/**
		 * print the keys of the node.
		 */
		public String getKeysString()
		{
			StringBuilder result = new StringBuilder();
			result.append(keys.get(0));
			for(int i = 1; i < keys.size(); i++)
			{
				result.append(' ').append(keys.get(i));
			}
			return result.toString();
		}

		public int compareTo(Node other) 
		{
			return keys.get(this.keys.size() - 1) - other.keys.get(0);
		}

	}
}