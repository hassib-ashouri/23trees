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
        return root.addKey(n);
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

	

	public class Node implements Comparable<Node> 
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
		 * get children's list.
		 */
		public ArrayList<Node> getChildrenList() 
		{
			return this.children;
		}

		/**
		 * get the keys list
		 */
		public ArrayList<Integer> getKeyList() 
		{
			return this.keys;
		}

		/**
		 * removes the smallest key in the list.
		 */
		public int removeSmallestKey()
		{
			return getKeyList().remove(0);
		}

		/**
		 * removes the biggest key in the list.
		 */
		public int removeBiggestKey()
		{
			return getKeyList().remove(numOfKeys() -1);
		}

		/**
		 * seach method returns the keys of the distination node whether key is found or not.
		 */
		public String search(int n)
		{
			//print the keys if its a leaf node or if the key is found
			if(isLeaf() || getKeyList().indexOf(n) != -1)
				return getKeysString();

			//find where to make the recursive call.
			int i = 0;
			while(i < numOfKeys() && n > this.keys.get(i))
				i++;

			return this.children.get(i).search(n);
		}

		/**
		 * This method adds a key to the proper place in the tree. IT handles the splits
		 * and increasing the height of the tree.
		 */
		public boolean addKey(int n)
		{
			return root.addKeyRecurs(n, null);
	
		}


		public boolean addKeyRecurs(int n, Node currentP )
		{
			boolean result = true;

			if(getKeyList().contains(n))
				return false;
			
			if(isLeaf())
			{
				getKeyList().add(n);
				Collections.sort(getKeyList());
			}
			else
			{
				int i = 0;
				while(i < numOfKeys() && n > getKeyList().get(i))
					i++;
				
				result = getChildrenList().get(i).addKeyRecurs(n,this);
			}

			if(numOfKeys() == 3)
				split(currentP);
			
			return result;
		}


		public void split( Node parent )
		{
			Node left = new Node(getSmallestKey());
			for(int i = 0; i < numOfChildren()/2 ; i++)
				left.getChildrenList().add( getChildrenList().get(i) );

			Node right = new Node(getBiggestKey());
			for(int i = numOfChildren()/2 ; i < numOfChildren() ; i++)
				right.getChildrenList().add( getChildrenList().get(i) );


			if(parent == null)
			{//this is root
				removeSmallestKey();
				removeBiggestKey();
				this.children = new ArrayList<>(4);
				getChildrenList().add(left);
				getChildrenList().add(right);
			}
			else
			{
				parent.getKeyList().add( getKeyList().get(1) );
				parent.getChildrenList().add(left);
				parent.getChildrenList().add(right);
				parent.getChildrenList().remove(this);
				Collections.sort(parent.getChildrenList());
				Collections.sort(parent.getKeyList());
			}
		}

		/**
		 * count the non null keys in the list.
		 * @return num of keys.
		 */
		public int numOfKeys() 
		{
			return this.keys.size();
		}

		/**
		 * counts the number of non null children in the list
		 * @return number of children.
		 */
		public int numOfChildren() 
		{
			return this.children.size();
		}

		/**
		 * return the small key.
		 * @test check what get returned when a small does not exist.
		 * @return
		 */
		public Integer getSmallestKey() 
		{
			return this.keys.get(0);
		}

		/**
		 * returns the big key.
		 * @test check what get returned when a big does not exist.
		 * @return the big key.
		 */
		public Integer getBiggestKey() 
		{
			return this.keys.get(this.keys.size() - 1);
		}

		/**
		 * checks if the node has any children.
		 * @return true for no children.
		 */
		public boolean isLeaf() 
		{
			return numOfChildren() == 0;
		}

		/**
		 * print the keys of the node.
		 */
		public String getKeysString()
		{
			StringBuilder result = new StringBuilder();
			result.append(getKeyList().get(0));
			for(int i = 1; i < numOfKeys(); i++)
			{
				result.append(' ').append(getKeyList().get(i));
			}
			return result.toString();
		}

		public int compareTo(Node other) 
		{
			return getBiggestKey() - other.getSmallestKey();
		}

	}
}