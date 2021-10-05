package cs1501_p1;

public class BST<T extends Comparable<T>>  implements BST_Inter<T> {

	 private BTNode<T> root = null;

	@Override
	public int height() {
		
        BTNode<T> cur = root;
        return height(cur);
    }
	
	//recursive helper method for height
    public int height(BTNode<T> curr) {
    	
    	int ans;
    	
        if(curr == null)
        {
            return 0;
        }
        
        int leftHeight = height(curr.getLeft());
        int rightHeight = height(curr.getRight());
        
        ans = Math.max(leftHeight, rightHeight) + 1;
        
        return ans;
    }

    

	@Override
	public boolean isBalanced() {
		
        BTNode<T> cur = root;
        return isBalanced(cur);
        
    }
	
	
	//recursive helper method for isBalanced that makes recursive calls to height()
    public boolean isBalanced(BTNode<T> cur) {
    	
        if (cur == null) 
        {
            return true;
        }
        
        isBalanced(cur.getLeft());
        isBalanced(cur.getRight());
        
        int leftSide = height(cur.getLeft());
        int rightSide = height(cur.getRight());

        int balanced = Math.abs(leftSide - rightSide);
        
        if(balanced <= 1)
        {
        	return true;
        }
        else return false;
    }

    
	@Override
	public String inOrderTraversal() {
		
        BTNode<T> cur = root;
        
        String ans = inOrderTraversal(cur);
        ans = shorten(ans);
        return ans;
    }
	
	//recursive helper method for inOrderTraversal
    public String inOrderTraversal(BTNode<T> cur) {
    	
        if (cur == null) 
        {
            return "";
        }
        
        String x = "";
       
        x = x + inOrderTraversal(cur.getLeft());
        x = x + cur.getKey() + ":";
        x = x + inOrderTraversal(cur.getRight());
        
        return x;
    }

	@Override
	public String serialize() {
		
        BTNode<T> cur = root;
        String ans = serialize(cur);
        return "R" + ans.substring(1);
    }
	
	//recursive helper method for serialize()
    public String serialize(BTNode<T> cur) {
    	
        String x = "";
        
        
        if (cur == null) 
        {
            return "";
        }
        
        //if leaf is reached return key
        if (cur.getLeft() == null && cur.getRight() == null) 
        {
            return "L" + "(" + (cur.getKey()) + ")";
        }
        
        //if there is only one null value print non null one
        if (cur.getLeft() == null || cur.getRight() == null) 
        { 
            if (cur.getLeft() == null) 
            { 
                x = x + "I" + "(" + (cur.getKey()) + ")" + "," + "X(NULL)" + "," + serialize(cur.getRight());
            }
            if (cur.getRight() == null) 
            { 
                x = x + "I" + "(" + (cur.getKey()) + ")" + "," + serialize(cur.getLeft()) + "," + "X(NULL)";
            }
        }
        
        //if neither nodes are null print both
        if (cur.getLeft() != null && cur.getRight() != null) 
        { 
            x = x + "I" + "(" + (cur.getKey()) + ")" + "," + serialize(cur.getLeft())+ "," + serialize(cur.getRight());
        }
        
        
        //if both nodes are null do nothing because its a leaf then
        return x;
    }


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BST_Inter reverse(){
		
        BST<T> treeCopy = new BST<>();
        treeCopy.root = deepCopy(root);
        return treeCopy;
    }
	
	
	//recursive helper method for producing a reversed deep copy of the tree
    public BTNode<T> deepCopy(BTNode<T> treeRoot) {
    	
        if(treeRoot == null) 
        {
            return null;
        }
        
        BTNode<T> newNode = new BTNode<>(treeRoot.getKey());
        
        //reversing the nodes
        newNode.setLeft(deepCopy(treeRoot.getRight()));
        newNode.setRight(deepCopy(treeRoot.getLeft()));
        return newNode;
    }

	@Override
	public void put(T key) {
		
		BTNode<T> cur = root;
        put(cur, key);
		
	}
	
	//recursive helper method for put(), like shown in the book
	public void put(BTNode<T> cur, T key) {
		 
	        BTNode<T> newNode = new BTNode<>(key);
	        
	        if (cur == null) 
	        {
	            root = newNode; 
	            return;
	        }
	        
	        //look for key in right subtree
	        if (key.compareTo(cur.getKey()) > 0) 
	        { 
	            if (cur.getRight() != null) 
	            {
	                cur = cur.getRight();
	                put(cur, key); 
	            } 
	            else 
	            {
	                cur.setRight(newNode); 
	            }
	        } 
	        
	        //look for key in left subtree
	        else if (key.compareTo(cur.getKey()) < 0) 
	        { 
	            if (cur.getLeft() != null) 
	            {
	                cur = cur.getLeft();
	                put(cur, key);
	            } 
	            else 
	            {
	                cur.setLeft(newNode); 
	            }
	        }
	    }


	@Override
	public boolean contains(T key) {
		
		 BTNode<T> cur = root;
		 
	        while (cur != null) {
	            if (key.compareTo(cur.getKey()) > 0) 
	            {
	                cur = cur.getRight();
	            } 
	            else if (key.compareTo(cur.getKey()) < 0) 
	            {
	                cur = cur.getLeft();
	            }
	            else 
	            {
	                return true;
	            }
	        }
	        return false;
	}

	
	
	@Override
	public void delete(T key) {
		
        if (!contains(key)) 
        {
            System.out.println("The key " + key + " is not in the tree.");
            return;
        }
        
        BTNode<T> cur = root;
        BTNode<T> parentNode = null;
        
        
        //traverse to node
        while (true) 
        {
            if (key.compareTo(cur.getKey()) > 0) 
            {
                parentNode = cur;
                cur = cur.getRight();
            } 
            else if (key.compareTo(cur.getKey()) < 0) 
            {
                parentNode = cur;
                cur = cur.getLeft();
            } 
            else if (key.compareTo(cur.getKey()) == 0) 
            {
                break;
            }
        }

        //if left and right are both null
        if (cur.getRight() == null && cur.getLeft() == null) 
        {
            if (cur == root) 
            {
                root = null;
            } 
            else if (parentNode.getLeft() == cur) 
            {
                parentNode.setLeft(null);
            } 
            else if (parentNode.getRight() == cur) 
            {
                parentNode.setRight(null);
            }
            
        }

        //if only one child is null
        else if (cur.getRight() == null || cur.getLeft() == null) 
        {
            if (cur == root && cur.getLeft() != null) 
            {
                root = cur.getLeft();
            } 
            else if (cur == root && cur.getRight() != null) 
            {
                root = cur.getRight();
            } 
            else if (cur != root && cur.getLeft() != null && parentNode.getLeft() == cur) 
            {
                parentNode.setLeft(cur.getLeft());
            } 
            else if (cur != root && cur.getRight() != null && parentNode.getLeft() == cur) 
            {
                parentNode.setLeft(cur.getRight());
            } 
            else if (cur != root && cur.getLeft() != null && parentNode.getRight() == cur) 
            {
                parentNode.setRight(cur.getLeft());
            } 
            else if (cur != root && cur.getRight() != null && parentNode.getRight() == cur) 
            {
                parentNode.setRight(cur.getRight());
            }
            
        }

        //if neither are null
        else if (cur.getLeft() != null && cur.getRight() != null) 
        {
            BTNode<T> successor = getNewLeaf(cur);
            
            if (cur == root) 
            {
                root = successor;
            } 
            else if (cur != root && parentNode.getLeft() == cur) 
            {
                parentNode.setLeft(successor);
            } 
            else if (cur != root && parentNode.getRight() == cur) 
            {
                parentNode.setRight(successor);
            }
            
        }
    }
	
    //helper method to restructure tree
    public BTNode<T> getNewLeaf(BTNode<T> nodeToDelete) {
    	
        BTNode<T> newLeaf = nodeToDelete;
        BTNode<T> newLeafParent = null;
        BTNode<T> current = nodeToDelete.getRight();

        while (current != null) 
        {
            newLeafParent = newLeaf;
            newLeaf = current;
            current = current.getLeft();
        }

        
        if (newLeaf != nodeToDelete.getRight()) 
        {
            newLeafParent.setLeft(newLeaf.getRight());
            newLeaf.setRight(nodeToDelete.getRight());
        }
        
        newLeaf.setLeft(nodeToDelete.getLeft());

        return newLeaf;
    }
	
    //helper method to shorten the string for in order traversal
	public String shorten(String text) {
	
		 text = text.substring(0,text.length()-1);
		 return text;
		
	}

}
