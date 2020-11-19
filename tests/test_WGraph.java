package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;

class test_WGraph {
	@Test
	void testAddNode() {
		weighted_graph g =new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		//		System.out.println(g);
		assertNotNull(g.getNode(0));
		assertNotNull(g.getNode(1));
		assertNotNull(g.getNode(2));
		assertNotNull(g.getNode(3));
		assertNull(g.getNode(-4));
		assertEquals(g.nodeSize(),4);
		assertEquals(g.edgeSize(),0);

	}


	@Test
	void testConnect() {
		weighted_graph g =new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(1, 2, 95.3);
		//		System.out.println(g);
		assertEquals(g.getEdge(1, 2),95.3);		
		g.connect(2, 1, 45.6);
		//		System.out.println(g);
		assertEquals(g.getEdge(2, 1),45.6);
		assertEquals(g.edgeSize(),1);
		assertEquals(g.getEdge(0,2 ),-1);
		assertTrue(g.hasEdge(1, 2));
		assertTrue(g.hasEdge(2, 1));
		assertFalse(g.hasEdge(1, 0));

	}
	@Test
	void testRemove() {
		weighted_graph g =new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(1, 2, 95.3);
		g.connect(1, 3, 20.4);
		g.connect(3, 2, 9);
		g.connect(0, 1, 100.543);
		g.connect(0, 2, 10.7);
		//System.out.println(g);
		assertEquals(g.getEdge(1, 2),95.3);		
		g.connect(2, 1, 45.6);
		//System.out.println(g);
		assertEquals(g.getEdge(1, 2),45.6);
		assertEquals(g.edgeSize(),5);
		assertEquals(g.nodeSize(),4);
		assertTrue(g.hasEdge(1, 2));
		assertTrue(g.hasEdge(2, 1));
		assertTrue(g.hasEdge(2, 3));
		assertTrue(g.hasEdge(3, 2));
		assertEquals(g.getNode(2),g.removeNode(2));
		assertEquals(g.edgeSize(),2);
		assertEquals(g.nodeSize(),3);
		assertFalse(g.hasEdge(1, 2));
		assertFalse(g.hasEdge(2, 1));
		assertFalse(g.hasEdge(2, 3));
		assertFalse(g.hasEdge(3, 2));
		g.addNode(2);
		assertFalse(g.hasEdge(1, 2));
		g.connect(1, 2, 95.3);
		assertTrue(g.hasEdge(2, 1));
		assertTrue(g.hasEdge(1, 2));
		assertEquals(g.edgeSize(),3);
		assertEquals(g.nodeSize(),4);
		g.removeEdge(2, 1);
		assertEquals(g.edgeSize(),2);
		assertEquals(g.nodeSize(),4);
		assertFalse(g.hasEdge(1, 2));
		assertFalse(g.hasEdge(2, 1));

	}

	@Test
	void testGetV() {
		weighted_graph g =new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(1, 2, 95.3);
		g.connect(1, 3, 20.4);
		g.connect(3, 2, 9);
		g.connect(0, 1, 100.543);
		g.connect(0, 2, 10.7);
		int i=0;
		for(node_info n: g.getV()) {
			assertEquals(n.getKey(),i++);

		}
		 i=1;
		for(node_info n: g.getV(0)) {
			assertEquals(n.getKey(),i++);

		}
	
	
	
	
	}
}
