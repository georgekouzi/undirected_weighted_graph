package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import ex1.WGraph_Algo;
import ex1.WGraph_DS;
import ex1.node_info;
import ex1.weighted_graph;
import ex1.weighted_graph_algorithms;

class test_WGraph_Algo {

	@Test
	void testEmptyGraph() {
		weighted_graph g = new WGraph_DS();
		weighted_graph_algorithms algo = new WGraph_Algo();
		algo.init(g);
		assertTrue(algo.isConnected());
		assertNull(algo.shortestPath(1, 0));
		assertEquals(algo.shortestPathDist(1, 0),-1);
		assertTrue(algo.save("testEmptyGraph"));

	}

	@Test
	void testOneNodeGraph() {
		weighted_graph g = new WGraph_DS();
		weighted_graph_algorithms algo = new WGraph_Algo();

		algo.init(g);
		algo.getGraph().addNode(0);

		assertTrue(algo.isConnected());
		assertNull(algo.shortestPath(1, 0));
		assertEquals(algo.shortestPathDist(1, 0),-1);
		assertTrue(algo.save("testOneNodeGraph"));

	}
	@Test
	void testGraph() {
		weighted_graph g = new WGraph_DS();
		weighted_graph_algorithms algo = new WGraph_Algo();

		algo.init(g);
		algo.getGraph().addNode(1);
		algo.getGraph().addNode(2);
		assertTrue(algo.save("testGraph"));
		assertFalse(algo.isConnected());
		assertNull(algo.shortestPath(1, 2));
		assertEquals(algo.shortestPathDist(2, 1),-1);
		algo.getGraph().connect(2, 1, 56.876);
		assertTrue(algo.isConnected());
		assertEquals(algo.shortestPathDist(1, 2),56.876);
		assertEquals(algo.shortestPathDist(2, 1),56.876);
		assertNotNull(algo.shortestPath(1, 2));
		assertEquals(algo.shortestPath(1, 2).size(),2);
		assertTrue(algo.save("testGraph_1"));

	}



	@Test
	void testGraph1() {
		weighted_graph g = new WGraph_DS(),g1;
		weighted_graph_algorithms algo = new WGraph_Algo();

		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(5);
		g.addNode(6);

		g.connect(1, 3, 9);
		g.connect(1, 2, 7);
		g.connect(1, 6, 14);
		g.connect(2, 3, 10);
		g.connect(2, 4, 15);
		g.connect(3, 6, 2);
		g.connect(3, 4, 11);
		g.connect(4, 5, 6);
		g.connect(6, 5, 9);

		assertTrue(algo.isConnected());
		algo.init(g);
		assertTrue(algo.isConnected());
		g.addNode(7);
		assertFalse(algo.isConnected());
		g.removeNode(7);
		assertTrue(algo.isConnected());
		g.removeEdge(1, 3);
		g.removeEdge(1, 2);
		g.removeEdge(1, 6);
		assertFalse(algo.isConnected());
		assertNotNull(g.getNode(1));
		assertEquals(g.getEdge(3, 1),-1);
		g.connect(1, 3, 9);
		g.connect(1, 2, 7);
		g.connect(1, 6, 14);		

		g1=algo.copy();
		assertEquals(g1.nodeSize(),g.nodeSize());
		assertEquals(g1.edgeSize(),g.edgeSize());
		g1.removeNode(3);
		assertTrue(algo.isConnected());
		algo.init(g1);
		assertTrue(algo.isConnected());
		assertNotEquals(g1.nodeSize(),g.nodeSize());
		assertNotEquals(g1.edgeSize(),g.edgeSize());
		assertEquals(g1.nodeSize(),5);
		assertEquals(g1.edgeSize(),5);
		assertEquals(g.nodeSize(),6);
		assertEquals(g.edgeSize(),9);
		algo.init(g);
		assertEquals(algo.shortestPathDist(1, 5),20.0);
		assertEquals(algo.shortestPathDist(4, 6),13.0);
		assertEquals(algo.shortestPathDist(0, 10),-1.0);
		int expected []= {4,3,6} ;
		int actual [] = new int [3];
		int i=0;
		for(node_info n :algo.shortestPath(4, 6)) {
			actual[i++]=n.getKey();
		}
		assertArrayEquals(expected,actual);

		int expected2 []= {1,3,6,5};
		int actual2 [] = new int [4];
		i=0;
		for(node_info n :algo.shortestPath(1, 5)) {
			actual2[i++]=n.getKey();
		}
		assertArrayEquals(expected2,actual2);

		assertTrue(algo.save("testGraph1"));
		assertTrue(algo.load("testGraph1"));
		assertEquals(algo.shortestPathDist(1, 5),20.0);
		algo.init(g1);
		assertTrue(algo.save("newfile1"));
		assertEquals(algo.getGraph().nodeSize(),5);
		assertEquals(algo.getGraph().edgeSize(),5);
		assertTrue(algo.load("testGraph1"));
		assertNotEquals(algo.getGraph().nodeSize(),5);
		assertNotEquals(algo.getGraph().edgeSize(),5);
		assertEquals(algo.getGraph().nodeSize(),6);
		assertEquals(algo.getGraph().edgeSize(),9);
		assertTrue(algo.load("testEmptyGraph"));
		assertEquals(algo.getGraph().nodeSize(),0);
		assertEquals(algo.getGraph().edgeSize(),0);
		assertTrue(algo.load("testOneNodeGraph"));
		assertEquals(algo.getGraph().nodeSize(),1);
		assertEquals(algo.getGraph().edgeSize(),0);
		assertTrue(algo.load("testGraph"));
		assertEquals(algo.getGraph().nodeSize(),2);
		assertEquals(algo.getGraph().edgeSize(),0);
		assertTrue(algo.load("testGraph1"));
		assertTimeoutPreemptively(Duration.ofMillis(100), ()-> algo.isConnected());

	}


	@Test
	public void test_Big_Graph() {
		//create graph with 14,499,870 connected and 1,000,000 nodes
		weighted_graph g = new WGraph_DS();
		int size =  1000*1000;
		int ten=1;
		for (int i = 0; i <size; i++) {
			g.addNode(i);
		}

		for (int i = 0; i <size; i++) {
			int dest=i;
			g.connect(size-2, i, 0.23); 

			if(i<size-1){
				g.connect(i,++dest,0.78);
			}
			if(i%2==0&&i<size-2) {
				g.connect(i,2+dest,0.94);
			}	

			if(ten==i&&(i%2==0)) {
				for (int j =0 ; j <size; j++) {
					g.connect(ten, j,0.56);
					g.connect(ten-2, j, 0.4);

				}

				ten=ten*10;
			}


		}


		weighted_graph_algorithms algo = new WGraph_Algo();
		algo.init(g);
		assertTrue(algo.isConnected());
		assertEquals(algo.shortestPathDist(0, 999998),0.23);
		assertEquals(algo.shortestPathDist(0, 8),0.46);
		int expected2 []= {6,999998,8};
		int actual2 [] = new int [3];
		int i=0;
		for(node_info n :algo.shortestPath(6, 8)) {
			actual2[i++]=n.getKey();
		}
		assertArrayEquals(expected2,actual2);

	}






}
