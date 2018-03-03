

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
        return root.addKeyMajor(this.root, n);
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
}