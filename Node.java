import java.util.ArrayList;
import java.util.Collections;

public class Node implements Comparable<Node> {

	private ArrayList<Integer> keys;
	private ArrayList<Node> children;

	public Node(int key) {
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
	public boolean addKeyMajor(Node root, int n) {
		boolean result = true;
		if(getKeyList().indexOf(n) != -1)
		{
			return false;
		}

		
		if (isLeaf()) 
		{
			this.keys.add(n);
			Collections.sort(getKeyList());
		} 
		else 
		{
			//route the recursive call to the right child
			int i = 0;
			while(i < numOfKeys() && n > getKeyList().get(i))
				i++;
			
			result = getChildrenList().get(i).addKeyMajor(root, n);
		}
		//this part of the code will start running when the recursion unfolds.
		//Here we start making and resolving splits

		// check if any of the children has three keys.
		int i = 0;
		while (i < numOfChildren() && getChildrenList().get(i).numOfKeys() != 3)
			i++;
		Node threeKeysNode = i < numOfChildren() ? this.children.get(i) : null;


		//split the child with the three keys.
		if(threeKeysNode != null)
			internalSplit(threeKeysNode);


		//if we are at the root with three key, take care of a split.
		if (this == root && numOfKeys() == 3) 
			rootSplit();
		
		return result;
	}

	/**
	 * does the split for root and takes care of the children.
	 */
	public void rootSplit() 
	{
		//take care of the for children
		//if the root is a leaf the loops wont run.
		Node left = new Node(getSmallestKey());
		for(int i = 0; i < numOfChildren()/2 ; i++)
			left.getChildrenList().add(this.getChildrenList().get(i));
		
		Node right = new Node(getBiggestKey());
		for(int f = numOfChildren()/2 ; f < numOfChildren() ; f++)
			right.getChildrenList().add(this.getChildrenList().get(f));
		
		ArrayList<Node> newChildrenList = new ArrayList<>(4);
		newChildrenList.add(left);
		newChildrenList.add(right);


		removeSmallestKey();
		removeBiggestKey();

		this.children = newChildrenList;
	}

	/**
	 * its called when a non root node need split.
	 */
	public void internalSplit(Node thKNode) 
	{
		getKeyList().add(thKNode.getKeyList().get(1));
		Collections.sort(this.keys);

		Node left = new Node(thKNode.getSmallestKey());
		for(int i = 0 ; i < thKNode.numOfChildren()/2 ; i++)
			left.getChildrenList().add( thKNode.getChildrenList().get(i) );


		Node right = new Node(thKNode.getBiggestKey());
		for(int i = thKNode.numOfChildren()/2 ; i < thKNode.numOfChildren() ; i++)
			right.getChildrenList().add( thKNode.getChildrenList().get(i) );


		getChildrenList().add(left);
		getChildrenList().add(right);
		getChildrenList().remove(thKNode);
		Collections.sort(getChildrenList());
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