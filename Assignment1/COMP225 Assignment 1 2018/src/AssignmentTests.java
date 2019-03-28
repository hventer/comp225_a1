import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

// These tests illustrate some basic properties of the methods you have been asked
// to implement. They are not exhaustive.
// For assessment there will also be cases where various combinations of
// the methods will be tested. You are strongly recommended to create some tests for
// yourself to ensure that your implementations satisfy all the specifications.


public class AssignmentTests {
	@Test
	public void testSearchForwards() {

		String[] items = {"a","b","c","d","e","f"};
		ArrayList<DNode> list = createList(items);
		DNode head = list.get(0);
		DNode target = null;

		target = head.searchForwards(list.get(3), "a");
		assertNull(target);

		target = head.searchForwards(list.get(3), "e");
		assertSame(target, list.get(4));  // The two references should be the same
		assertSame(target.next, list.get(5));

		target = head.searchForwards(list.get(3), "f");
		assertSame(target, list.get(5));
		assertSame(target.next, null);
	}


	@Test
	public void testSearchBackwards() {

		String[] items = {"a","b","c","d","e","f"};
		ArrayList<DNode> list = createList(items);

		DNode head = list.get(0);
		DNode curr = list.get(3);
		DNode target = null;

		target = head.searchBackwards(curr, "f");
		assertNull(target);

		target = head.searchBackwards(curr, "a");
		assertSame(target, list.get(0));
		assertSame(target.prev,null);

		target = head.searchBackwards(curr, "b");
		assertSame(target, list.get(1));
		assertSame(target.prev,list.get(0));

	}

	@Test
	public void testInsertAfter() {

		String[] items = {"a","b","c","d","e","f"};
		ArrayList<DNode> list = createList(items);

		String[] testArray = {"a","b","z","c","d","e","f"};

		DNode head = list.get(0);
		DNode curr = list.get(1);
		DNode newNode = new DNode("z");

		head.insertAfter(curr, newNode);

		DNode temp = head;
		for (int i = 0; i < testArray.length-1; i++){
			assertEquals(testArray[i], temp.contents);
			temp = temp.next;
		}
		assertEquals(testArray[testArray.length-1], temp.contents);

		for (int i = testArray.length-1; i >= 0; i--){
			assertEquals(testArray[i], temp.contents);
			temp = temp.prev;
		}
	}

	@Test
	public void testInsertBefore() {

		String[] items = {"a","b","c","d","e","f"};
		ArrayList<DNode> list = createList(items);

		String[] testArray = {"a","z","b","c","d","e","f"};

		DNode head = list.get(0);
		DNode curr = list.get(1);
		DNode newNode = new DNode("z");

		head.insertBefore(curr, newNode);

		DNode temp = head;
		for (int i = 0; i < testArray.length-1; i++){
			assertEquals(testArray[i], temp.contents);
			temp = temp.next;
		}
		assertEquals(testArray[testArray.length-1], temp.contents);

		for (int i = testArray.length-1; i >= 0; i--){
			assertEquals(testArray[i], temp.contents);
			temp = temp.prev;
		}
	}


	// The following tests concern the class DLSList

	@Test
	public void testAddNewNode() {
		String[] items = {"b","c","e"};
		ArrayList<DNode> list = createList(items);
		DNode head = list.get(0);

		DLSList testList= new DLSList();
		testList.head= head;
		testList.numNodes= items.length;
		testList.lastVisited= testList.head; // Initialise testList to satisfy the class invariant

		// now prepare and add a node
		DNode nodeToAdd= new DNode("d");
		testList.addNewNode(nodeToAdd);

		String testArray1[]= {"b", "c", "d", "e" };

		DNode temp= testList.head;
		for(int i=0; i< testArray1.length; i++){ // Checks the order of nodes starting from the first node
			assertEquals(testArray1[i], temp.contents);
			temp=temp.next;
		}
		assertEquals(testArray1.length, testList.numNodes);

		nodeToAdd= new DNode("f");
		testList.addNewNode(nodeToAdd);

		String testArray2[]= {"b", "c", "d", "e", "f" };
		temp = testList.head;
		for(int i=0; i< testArray2.length; i++){
			assertEquals(testArray2[i], temp.contents);
			temp=temp.next;
		}
		assertEquals(testArray2.length, testList.numNodes);

		nodeToAdd= new DNode("a");
		testList.addNewNode(nodeToAdd);

		String testArray3[]= {"a", "b", "c", "d", "e", "f" };
		temp = testList.head;
		for(int i=0; i< testArray3.length; i++){
			assertEquals(testArray3[i], temp.contents);
			temp=temp.next;
		}
		assertEquals(testArray3.length, testList.numNodes);
	}

	@Test
	public void testRemoveNode() {
		//TEST to remove first node
		String[] items = {"a","b","c","d"};
		ArrayList<DNode> list = createList(items);
		DNode head = list.get(0);

		DLSList testList= new DLSList();
		testList.head= head;
		testList.numNodes= items.length;
		testList.lastVisited= testList.head; // Initialise testList to satisfy the class invariant

		// now remove a node
		testList.removeNode("a");

		String testArray[]= {"b", "c", "d"};

		DNode temp= testList.head;
		for(int i=0; i< testArray.length; i++){ // Checks the order of nodes starting from the first node
			assertEquals(testArray[i], temp.contents);
			temp=temp.next;
		}
		assertEquals(testArray.length, testList.numNodes);


		//TEST to remove not last or first (somewhere in the middle)
		String[] items1 = {"a","b","c","d","e","f"};
		ArrayList<DNode> list1 = createList(items1);
		DNode head1 = list1.get(0);

		DLSList testList1= new DLSList();
		testList1.head= head1;
		testList1.numNodes= items1.length;
		testList1.lastVisited= testList1.head; // Initialise testList to satisfy the class invariant

		// now remove a node
		testList1.removeNode("c");

		String testArray1[]= {"a", "b", "d","e","f"};

		DNode temp1= testList1.head;
		for(int i=0; i< testArray1.length; i++){ // Checks the order of nodes starting from the first node
			assertEquals(testArray1[i], temp1.contents);
			temp1=temp1.next;
		}
		assertEquals(testArray1.length, testList1.numNodes);


		//TEST to remove last node
		String[] items2 = {"a","b","c","d","e","f"};
		ArrayList<DNode> list2 = createList(items2);
		DNode head2 = list2.get(0);

		DLSList testList2= new DLSList();
		testList2.head= head2;
		testList2.numNodes= items2.length;
		testList2.lastVisited= testList2.head; // Initialise testList to satisfy the class invariant

		// now remove a node
		testList2.removeNode("f");

		String testArray2[]= {"a","b","c","d","e"};

		DNode temp2= testList2.head;
		for(int i=0; i< testArray2.length; i++){ // Checks the order of nodes starting from the first node
			assertEquals(testArray2[i], temp2.contents);
			temp2=temp2.next;
		}
		assertEquals(testArray2.length, testList2.numNodes);


		//TEST to remove more than 1 node
		String[] items3 = {"a","b","b","b","c","d","e","f"};
		ArrayList<DNode> list3 = createList(items3);
		DNode head3 = list3.get(0);

		DLSList testList3= new DLSList();
		testList3.head= head3;
		testList3.numNodes= items3.length;
		testList3.lastVisited= testList3.head; // Initialise testList to satisfy the class invariant

		// now remove a node
		testList3.removeNode("b");

		String testArray3[]= {"a","c","d","e","f"};

		DNode temp3= testList3.head;
		for(int i=0; i< testArray3.length; i++){ // Checks the order of nodes starting from the first node
			assertEquals(testArray3[i], temp3.contents);
			temp3=temp3.next;
		}
		assertEquals(testArray3.length, testList3.numNodes);
	}

	@Test
	public void testVisit() {

		String[] items = {"a","b","c","d"};
		ArrayList<DNode> list = createList(items);
		DNode head = list.get(0);

		DLSList testList= new DLSList();
		testList.head= head;
		testList.numNodes= items.length;
		testList.lastVisited= testList.head; // Initialise testList to satisfy the class invariant

		DNode temp= testList.visit("b");
		DNode curr= list.get(1);

		assertSame(curr, temp);
		assertSame(curr, testList.lastVisited);


		DNode temp1= testList.visit("f");

		assertSame(null, temp1);
	}


	// The following tests concern the class SimpleBlockchain

	@Test
	public void testAddBlock() {
		SimpleBlockchain testBc= new SimpleBlockchain("hello");

		BlockchainNode newBc= new BlockchainNode ("number two", testBc.genesisNode.currHash, 1);

		testBc.addBlock("number two");

		assertEquals("number two", testBc.lastNode.contents);
		assertEquals(1, testBc.lastNode.blockNumber);
		assertEquals(newBc.currHash, testBc.lastNode.currHash);
		assertSame(testBc.genesisNode.next, testBc.lastNode);



		BlockchainNode newBc2= new BlockchainNode ("number three", newBc.currHash, 2);

		testBc.addBlock("number three");

		assertEquals("number three", testBc.lastNode.contents);
		assertEquals(2, testBc.lastNode.blockNumber);
		assertEquals(newBc2.currHash, testBc.lastNode.currHash);
		assertSame(testBc.genesisNode.next.next, testBc.lastNode);



		BlockchainNode newBc3= new BlockchainNode ("number four", newBc2.currHash, 3);

		testBc.addBlock("number four");

		assertEquals("number four", testBc.lastNode.contents);
		assertEquals(3, testBc.lastNode.blockNumber);
		assertEquals(newBc3.currHash, testBc.lastNode.currHash);
		assertSame(testBc.genesisNode.next.next.next, testBc.lastNode);
	}


	@Test
	public void testValidate() {
		SimpleBlockchain testBc = new SimpleBlockchain("hello");
		testBc.addBlock("number two");
		assertTrue(testBc.validate());

		SimpleBlockchain testBc2 = new SimpleBlockchain("hello");
		assertTrue(testBc2.validate());

		SimpleBlockchain testBc3 = new SimpleBlockchain("hello");
		testBc3.genesisNode = null;
		assertTrue(testBc3.validate());

		SimpleBlockchain testBc4 = new SimpleBlockchain("hello");
		testBc4.addBlock("number two");
		testBc4.addBlock("number three");
		assertTrue(testBc4.validate());

		SimpleBlockchain testBc5 = new SimpleBlockchain("hello");
		testBc5.addBlock("number two");
		testBc5.addBlock("number three");
		testBc5.addBlock("number four");
		testBc5.lastNode.prev.blockNumber = 7;
		testBc5.genesisNode.next.contents = "not two anymore";
		assertFalse(testBc5.validate());
	}


	@Test
	public void findTamperedNode() {
		SimpleBlockchain testBc= new SimpleBlockchain("hello");
		testBc.addBlock("number two"); // assumes addBlock is correctly implemented
		testBc.lastNode.contents= "something else"; // A simple attack: Change the block chain contents
		assertFalse(testBc.validate()); // No longer valid
		assertSame(testBc.lastNode, testBc.findTamperedNode()); // Last node has been altered

		SimpleBlockchain testBc2 = new SimpleBlockchain("hello");
		assertTrue(testBc2.validate());
		assertSame(null, testBc2.findTamperedNode());

		SimpleBlockchain testBc3 = new SimpleBlockchain("hello");
		testBc3.genesisNode = null;
		assertTrue(testBc3.validate());
		assertSame(null, testBc3.findTamperedNode());

		SimpleBlockchain testBc4 = new SimpleBlockchain("hello");
		testBc4.addBlock("number two");
		testBc4.addBlock("number three");
		assertTrue(testBc4.validate());
		assertSame(null, testBc4.findTamperedNode());

		SimpleBlockchain testBc5= new SimpleBlockchain("hello");
		testBc5.addBlock("number two");
		testBc5.addBlock("number three");
		testBc5.addBlock("number four");
		testBc5.lastNode.prev.contents= "something else";
		assertFalse(testBc5.validate());
		assertSame(testBc5.lastNode.prev, testBc5.findTamperedNode());

		SimpleBlockchain testBc6= new SimpleBlockchain("hello");
		testBc6.addBlock("number two");
		testBc6.addBlock("number three");
		testBc6.addBlock("number four");
		testBc6.genesisNode.next.contents= "something else";
		assertFalse(testBc6.validate());
		assertSame(testBc6.genesisNode.next, testBc6.findTamperedNode());
	}


	public ArrayList<DNode> createList(String[] items){
		ArrayList<DNode> list = new ArrayList<>();
		if (items.length > 0){
			list.add(new DNode(items[0],null,null));
			for (int i = 1; i < items.length; i++){
				DNode toAdd = new DNode(items[i],list.get(i-1),null);
				list.add(toAdd);
				list.get(i-1).next = toAdd;
			}
		}
		return list;
	}

}
