package ex1.src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import ex1.src.WGraph_DS.nodeInfo;
/**
 *This class knows how to do operations on graphs.
 *We can check if the graph is Connected, check the cheap path between two nodes and the cheap distance between two nodes.
 *We can save the graph on text file and load the graph from text file.
 *This class uses a BFS algorithm that allows running on all nodes in the graph in run time O(n+m).
 *This class uses a Dijkstra algorithm that allows running on all nodes in the graph and find the cheap path two nodes in run time O(E*log(V)).
 *https://en.wikipedia.org/wiki/Breadth-first_search.
 *https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm.
 * @author george kouzy
 *
 */


public class WGraph_Algo implements weighted_graph_algorithms {

	private weighted_graph _wGraph;
	// count the number of visited
	private static int _count_vis;

	//////Constructor///////////////
	public WGraph_Algo(){
		_wGraph=new WGraph_DS();	
	}


	/**
	 * This function allows you to point this graph to new graph.
	 * @param g
	 */
	@Override
	public void init(weighted_graph g) {
		_wGraph=g;
	}
	/**
	 * This function return this graph.
	 * @return -this graph
	 */
	@Override
	public weighted_graph getGraph() {

		return _wGraph;
	}
	/**
	 * This function returns a deep copy of the graph
	 * run time o(m+n)
	 * @return - new graph.
	 */
	@Override
	public weighted_graph copy() {
		weighted_graph g =new WGraph_DS();

		for ( node_info i: _wGraph.getV()) {
			g.addNode(i.getKey());
			for ( node_info j:_wGraph.getV(i.getKey())) {			
				if(g.getNode(j.getKey())!=null)
					g.connect(i.getKey(), g.getNode(j.getKey()).getKey(),_wGraph.getEdge(i.getKey(), j.getKey()));
				else {
					g.addNode(j.getKey());
					g.connect(i.getKey(), g.getNode(j.getKey()).getKey(),_wGraph.getEdge(i.getKey(), j.getKey()));

				}
			}

		}

		return g;
	}

	/**
	 *   This function checks if there is a Path from each node to all the nodes With BFS Algorithm
	 *   after BFSConnected  we checking the number of visited in the BFS function.
	 *   if we visit in all the node on the graph the graph is connected
	 *   uses function shortestPath
	 *   @return -True if there is at least one Path, otherwise it will return a false
	 */

	@Override
	public boolean isConnected() {
		if(_wGraph.nodeSize()==0) {
			return true;
		}
		//send the first node in the graph to BFS
		BFSConnected(_wGraph.getV().iterator().next().getKey());
		if(_count_vis==_wGraph.nodeSize())  
			return true;
		else
			return false;
	}

	/**
	 * returns the length of the shortest path between src to dest
	 * if src==dest we return 0 or if no path between the node return -1.
	 * In order to find the shortest distance from source node to destination node  we will use the algorithm of dijkstra.
	 * @param src - source node.
	 * @param dest -  destination node.
	 * @return -shortest distance.
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		if(_wGraph.getNode(src)==null||_wGraph.getNode(dest)==null) 
			return-1;	
		if(src==dest) { 
			return 0.0;
		}	
		ClearEverything();
		dijkstraAlgo(src);
		//if there no path we return -1 like Infinite
		if(((nodeInfo)_wGraph.getNode(dest)).getParent()==null)
			return -1;
		//the distance from the source to the destination save in destination tag
		return _wGraph.getNode(dest).getTag();
	}
	/**
	 * returns the the shortest path between src to dest - as an ordered List of nodes:
	 * source--> n1-->n2-->...destination
	 * if no such path --> returns null;
	 * In order to find the shortest path from source node to destination node we will use the algorithm of dijkstra.
	 * @param src - source node
	 * @param dest - destination  node
	 * @return - list of the shortest path between source to destination
	 */
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		if(_wGraph.getNode(src)==null||_wGraph.getNode(dest)==null) 
			return null;
		List<node_info>  c = new ArrayList<node_info>();

		if(src==dest) {
			c.add(_wGraph.getNode(src));
			return c;
		}

		ClearEverything();
		dijkstraAlgo(src);
		//if the parent of node dest equal to null we returns null.
		if(((nodeInfo)_wGraph.getNode(dest)).getParent()==null) return null;
		//Create a list that will store the cheapest list from the destination node to the source node.

		node_info n = _wGraph.getNode(dest);
		//run from destination node to the source node.
		while(n!=_wGraph.getNode(src)) {
			//add destination--> add parent destination-->add parent of parent destination-->add...Until n is equal to the source node
			c.add(n);
			//Parent update of node
			n=((nodeInfo) n).getParent();
		}
		//Adding the source node to the list
		c.add(_wGraph.getNode(src));
		//Arrange a list in reverse order
		Collections.reverse(c);

		return c;

	}

	/**
	 * Saves this undirected weighted graph to the file name
	 * if the save successfully The default path to this file is in the project folder.
	 * @param  String file - The file name.
	 * @return  - if the file was successfully saved return true else false
	 */
	@Override
	public boolean save(String file) {
		try {
			FileOutputStream File = new FileOutputStream(file);
			ObjectOutputStream obj = new ObjectOutputStream(File);
			obj.writeObject(_wGraph);
			obj.close();
			File.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	/**
	 * Load this file (undirected weighted graph) and this graph point to file graph.
	 * file name on the computer 
	 * The default path to this file is in the project folder.
	 * graph and nodeInfo class need to implements from Serializable interface and after a serialized object has been written into a file
	 * it can be read from the file and deserialized that is the type information and bytes that represent the object 
	 * and its data can be used to recreate the object in memory.
	 * @param  String file - The file name.
	 * @return  - if the file was successfully load return true else false
	 */
	@Override
	public boolean load(String file) {

		try {
			FileInputStream File = new FileInputStream(file);
			ObjectInputStream obj = new ObjectInputStream(File);
			init((weighted_graph) obj.readObject()); 
			obj.close();
			File.close();
			return true;

		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		

	}

	/**
	 * This function use BFS algorithm  
	 * run time O(N+M) when n is the number of node and M is number of edge
	 * run over all the node and check if src have a path from all node to each node on the graph    
	 * @param src
	 */
	private void BFSConnected  (int src) {
		ClearEverything();

		//hold the neighbors
		LinkedList<node_info> neighbors = new LinkedList<node_info>(); 
		_count_vis=1;
		//Marking the first node
		_wGraph.getNode(src).setInfo("true");
		neighbors.add(_wGraph.getNode(src)); 
		// check all the  neighbor and finish the loop when the neighbors LinkedList is empty 
		while(!neighbors.isEmpty()) { 
			//remove the first node in  neighbors list and n equal to this node 
			node_info n =neighbors.remove(); 
			//loop for find the neighbor of n 
			for (node_info i :_wGraph.getV(n.getKey())) { 
				// if we dont visit in this node get in
				if(i.getInfo()=="false") {
					//after visit in this node n we change tag to true/1
					i.setInfo("true");
					//count the visited
					_count_vis++;
					//if the number of visited equal to number of node finish and return
					if(_count_vis==_wGraph.nodeSize())return;
					//add neighbor
					neighbors.add(i); 
				}
			}
		}
	}


	/**
	 * This function cleans all the node tag,info and Parent
	 */
	private void ClearEverything() {
		for (node_info i : _wGraph.getV()) { 
			i.setTag(Double.MAX_VALUE);
			i.setInfo("false");
			((nodeInfo) i).setParent(null);

		}
	}
/**
 * inner class allows as to compare between two node distance.
 */
	private class minDistanse implements Comparator<node_info> {
		public int compare(node_info dist1, node_info dist2) {
			return  (int) (dist1.getTag()-dist2.getTag());

		}

	}

	/**
	 * This algorithm makes it possible to go over a weighted undirected graph
	 * And find the cheapest ways from the source node to the rest of the graph nodes.
	 * The weights in the graph symbolize distance. 
	 * The shortest route between two points means the route with the lowest amount of weights between the two vertices.
	 * Ran time- O(E*log(V)) because we create PriorityQueue and compare the node by the minimum distance .
	 * @param src - the source node
	 */
	private void  dijkstraAlgo(int src) {

		PriorityQueue<node_info> p = new PriorityQueue<node_info>(new minDistanse());


		_wGraph.getNode(src).setTag(0.0);
		p.add(_wGraph.getNode(src));

		while(!p.isEmpty()) {

			//update the n node at the cheapest node and delete it from the Priority Queue p 
			node_info n = p.poll();
			//running on the neighbors of n node
			for(node_info neighbor: _wGraph.getV(n.getKey())) {
				//If we did not visited in this node we will enter to the if
				if(neighbor.getInfo()=="false") {
					//Calculate the updated price(Tag) of n node + the price of the side between n and its neighbor	(neighbor)				
					double total =n.getTag()+_wGraph.getEdge(n.getKey(), neighbor.getKey());
					if(neighbor.getTag()>total) {
						//System.out.println(total+"-->"+neighbor.getKey());

						//updated tag,PriorityQueue p and parent of node neighbor.
						neighbor.setTag(total);
						p.add(neighbor);
						((nodeInfo) neighbor).setParent(n);

					}

				}

				//when we finish to visit in the node n we don't wont to visit in this node again 
				n.setInfo("true");

			}

		}

	}
	



}
