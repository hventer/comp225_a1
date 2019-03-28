
import java.security.MessageDigest;

class DNode {
// Node for building a Doubly-linked list
	String contents;
	DNode next;
	DNode prev;

	DNode (String k) {  // Constructor: builds a node which can be searched forwards and backwards
		next= null; prev= null;
		contents= k;
	}

	DNode (String k, DNode prev, DNode next){ // Constructor: builds a node with given references
		this.prev = prev;
		this.next = next;
		this.contents = k;
	}

	DNode searchForwards(DNode curr, String key) { //TODO
		// Post: DNode is an address of a doubly-linked list.
		// returns the address of key if it occurs in the portion of the list
		// beginning at curr and ending at the last node. Returns null if key does not occur in that portion,
		// or if DNode is null.
		while(curr != null) {
			if(curr.contents == key)
				return curr;
			else
				curr = curr.next;
		}
		return null;
	}

	DNode searchBackwards(DNode curr, String key) { //TODO
		// Post: DNode is an address of a doubly-linked list.
		// returns the address of key if it occurs in the portion of the list
		// beginning at the head and ending at curr. Returns null if key does not occur in that portion,
		// or if DNode is null.
		while(curr != null) {
			if(curr.contents == key)
				return curr;
			else
				curr = curr.prev;
		}
		return null;
	}

	void insertAfter(DNode curr, DNode newNode) { //TODO
		// Pre: curr and newNode are addresses for DNodes
		// Post: newNode is inserted between curr and its next neighbour, i.e.
		// let N be newNode's next neighbour, then: curr.next points to newNode, newNode.next points to N
		// N.prev points to newNode and newNode.prev points to curr.
		// If curr has no next neighbour, then newNode is inserted as the last node after curr.
		if(curr.next == null) {
			curr.next = newNode;
			newNode.prev = curr;
		}

		DNode N = curr.next;
		curr.next = newNode;
		newNode.next = N;
		newNode.prev = curr;
		N.prev = newNode;
	}

	void insertBefore(DNode curr, DNode newNode) { //TODO
		// Pre: curr and newNode are addresses for DNodes
		// Post: newNode is inserted between curr and its previous neighbour, i.e.
		// let P be newNode's previous neighbour, then: P.next points to newNode, newNode.next points to curr
		// curr.prev points to newNode and newNode.next points to curr.
		// If curr has no previous neighbour, then newNode is inserted as the first node before curr.
		if(curr.prev == null) {
			curr.prev = newNode;
			newNode.next = curr;
		}

		DNode P = curr.prev;
		curr.prev = newNode;
		newNode.prev = P;
		newNode.next = curr;
		P.next = newNode;
	}
}

class DLSList {
	// Class invariant: The nodes in the list are sorted (ascending) according to the contents
	// AND numNodes == the number of nodes in the list
	// AND (lastVisited points to the node which was last valid access after method visit is called
	//     OR is set to head (in case removeNode demands it) or it is initialised)
	DNode head;  // The first node in the list
	DNode lastVisited; // The address of the node last visited
	int numNodes; // The number of nodes in the list

	DLSList (){
		head= null;
		lastVisited= null;
		numNodes= 0;
	}

	void addNewNode(DNode newNode) { //TODO
		// Post: newNode is inserted into the current list in correct sorted order
		// numNodes is adjusted to be equal to the number of nodes in the list
		DNode temp;
		if(head == null) {
			head = newNode;
			lastVisited = head;
		}

		else {
			if(head.contents.compareTo(newNode.contents) >= 0) {
				head.insertBefore(head, newNode);
				lastVisited = head;
				temp = head;
				head = head.prev;
				head.next = temp;
			}

			else {
				temp = head;

				while(temp != null && temp.contents.compareTo(newNode.contents) <= 0) {
					lastVisited = temp;
					temp = temp.next;
				}
				head.insertAfter(lastVisited, newNode);
			}

			numNodes++;
		}
	}

	void removeNode(String key) { //TODO
		// Post: All occurrences of nodes with contents field equal to key are removed.
		// If lastVisited points to one of the removed nodes, then lastVisited is set to head
		// numNodes is adjusted to be equal to the number of nodes in the list
		DNode temp;
		if(head == null)
			return;

		if(head.contents.compareTo(key) == 0) { //head contains the key (first node)
			head = head.next;
			head.prev = head.prev.prev;
			lastVisited = head;
			numNodes--;
		}

		else { //not the first node
			temp = head;
			while(temp != null) {
				if(temp.contents.compareTo(key) == 0) { //check if temp contains key
					temp = temp.next;
					lastVisited.next = temp;
					if(temp != null) //!(last node was removed and temp became null)
						temp.prev = lastVisited;
					numNodes--;
				}
				else {
						lastVisited = temp;
						temp = temp.next;
				}
			}
		}
	}


	DNode visit(String key) { //TODO
		// Post: Returns the address of the first node (in ascending order) whose contents equal key, and null if there is no such node;
		// lastVisited is set to the address returned if it is not null, otherwise lastVisited remains unchanged
		DNode matchingNode = head.searchForwards(head, key);
		if(matchingNode != null)
			lastVisited = matchingNode;

		return matchingNode;
	}
}

class BlockchainNode { // The basic BlockchainNode...
	String currHash;  // To store the hash
	String contents;  // To store the data
	int blockNumber;  // The number of the node in the block after the Genesis Node
	BlockchainNode next; // The next BlockchainNode
	BlockchainNode prev; // The previous BlockchainNode

	BlockchainNode (String s) {
		// Creates a Genesis node
		currHash= StringUtil.applySha256(Integer.toString(0)+"AllZeros:This is the Genesis String!"+s);
		contents= s;
		blockNumber= 0;
		next= null;
		prev= null;
	}

	BlockchainNode (String s, String pH, int bN) {
	// Creates a new blocknumbered bN with contents s
		currHash= StringUtil.applySha256(Integer.toString(bN)+pH+s);
		contents= s;
		blockNumber= bN;
		next= null;
		prev= null;
	}
}

class SimpleBlockchain {
	// Class invariant: all nodes in the Blockchain satisfy "Blockchain Validity"
	// i.e.  Blockchain Validity holds of all nodes (except the Genesis node):
	// StringUtil.applySha256(this.blockNumber+ prev.currHash+this.contents == this.currHash)
	// AND the Genesis node has been correctly initialised
	BlockchainNode genesisNode; // Created by calling BlockchainNode (String s)
	BlockchainNode lastNode;   // The last node in the Blockchain

	SimpleBlockchain(String s){
		genesisNode= new BlockchainNode(s);
		lastNode= genesisNode;
	}


	void addBlock(String s) { //TODO
		// Post: Creates a new valid block with contents s, and adds it
		// to the current SimpleBlockchain so that the result satisfies the blockchain condition.
		BlockchainNode temp = lastNode;

		lastNode = new BlockchainNode(s, lastNode.currHash, lastNode.blockNumber+1);
		temp.next = lastNode;
		lastNode.prev = temp;
	}

	boolean validate () { //TODO
		// Post: Returns true if the SimpleBlockchain is valid, i.e. satisfies the blockChain condition
		// null SimplBlockchains are valid
		if(lastNode == genesisNode || genesisNode == null)
			return true;

		else {
			BlockchainNode temp = genesisNode.next;

			while(temp != null) {
				if(!temp.currHash.equals(StringUtil.applySha256(Integer.toString(temp.blockNumber)+temp.prev.currHash+temp.contents)))
					return false;
				else
					temp = temp.next;
			}
			return true;
		}
	}

	BlockchainNode findTamperedNode() { //TODO
		// Post: If validate returns false, returns the address of the first node which fails to validate
		// If validate returns true, then returns null
		if(validate())
			return null;

		else {
			BlockchainNode temp = genesisNode.next;

			while(temp != null) {
				if(!temp.currHash.equals(StringUtil.applySha256(Integer.toString(temp.blockNumber)+temp.prev.currHash+temp.contents)))
					return temp;
				else
					temp = temp.next;
			}
			return null;
		}
	}
}


/// This is what we will use for the hash function in making a blockchain
/// Do not change or remove this code
class StringUtil {
	//Applies Sha256 to a string and returns the result.
	public static String applySha256(String input){
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			//Applies sha256 to our input,
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
