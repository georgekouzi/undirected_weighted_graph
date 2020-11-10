package ex1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;



public class weightedGraphAlgorithms implements weighted_graph_algorithms,Serializable {
	
	private static final long serialVersionUID = 1L;
	private weighted_graph _wGraph;
	// count the number of visited
	private static int _count_vis;

	weightedGraphAlgorithms(){
		_wGraph=new weightedGraph();	
	}


	public static void main(String[] args) {
		weighted_graph g = new weightedGraph() ;
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		weighted_graph_algorithms g123 = new weightedGraphAlgorithms();
		g123.init(g);
		g123.save("graph.txt");
		System.out.println(g123.save("graph.txt"));
		//		System.out.println(g);
		//		g123.load("C:\\Users\\georg\\eclipse-workspace\\EX_1\\src\\ex1\\tt.txt");	
		g123.load("graph.txt");

	}

	@Override
	public void init(weighted_graph g) {
		_wGraph=g;
	}

	@Override
	public weighted_graph getGraph() {

		return _wGraph;
	}

	@Override
	public weighted_graph copy() {
		weighted_graph g =new weightedGraph();

		for ( node_info i: _wGraph.getV()) {
			g.addNode(i.getKey());
			for ( node_info j:_wGraph.getV(i.getKey())) {			
				if(!g.hasEdge(i.getKey(), j.getKey())) {

					if(g.getNode(j.getKey())!=null)
						g.connect(i.getKey(), g.getNode(j.getKey()).getKey(),_wGraph.getEdge(i.getKey(), j.getKey()));
					else {
						g.addNode(j.getKey());
						g.connect(i.getKey(), g.getNode(j.getKey()).getKey(),_wGraph.getEdge(i.getKey(), j.getKey()));
					}
				}

			}

		}

		return g;
	}


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
	 * this function use BFS algorithm  
	 * run time O(N+M) when n is the number of node and M is number of edge
	 * run over all the node and check if src have a path from all node to each node on the graph    
	 * @param src
	 */
	private void BFSConnected  (int src) {
		clearDatatag();

		//hold the neighbors
		LinkedList<node_info> neighbors = new LinkedList<node_info>(); 
		_count_vis=1;
		//Marking the first node
		_wGraph.getNode(src).setTag(1);
		neighbors.add(_wGraph.getNode(src)); 
		// check all the  neighbor and finish the loop when the neighbors LinkedList is empty 
		while(!neighbors.isEmpty()) { 
			//remove the first node in  neighbors list and n equal to this node 
			node_info n =neighbors.remove(); 
			//loop for find the neighbor of n 
			for (node_info i :_wGraph.getV(n.getKey())) { 
				// if we dont visit in this node get in
				if (i.getTag()==0) {
					//after visit in this node n we change tag to true/1
					i.setTag(1);
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
	 * This function cleans all the node tag
	 */
	private void clearDatatag() {
		for (node_info i : _wGraph.getV()) { 
			i.setTag(0);
		}
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Saves this weighted (undirected) graph to the given
	 * file name
	 * @param file - the file name (may include a relative path).
	 * @return true - iff the file was successfully saved
	 */
	@Override
	public boolean save(String file) {
		System.out.println(file);

		try {
			FileWriter fr = new FileWriter(file);
			PrintWriter buf = new PrintWriter(fr);
			buf.println(_wGraph);
			buf.close();
			fr.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
		return false;
	}

	@Override
	public boolean load(String file) {
		BufferedInputStream _BufferedInputStream =null;

		try {

			_BufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			ObjectInputStream  object = new ObjectInputStream ( _BufferedInputStream );
			_wGraph = (weighted_graph) object.readObject();
			_BufferedInputStream.close();
			object.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
