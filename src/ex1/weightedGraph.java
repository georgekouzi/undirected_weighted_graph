package ex1;

import java.util.Collection;
import java.util.HashMap;



public   class weightedGraph  implements weighted_graph  {


	public class  nodeInfo  implements node_info {

		private int _key;
		private String _info;
		private double _tag;

		public nodeInfo(int key)  {
			_key=key;
			_tag=0.0;
			_info="";

		}

		@Override
		public int getKey() {
			return _key;
		}

		@Override
		public String getInfo() {
			return _info;
		}

		@Override
		public void setInfo(String s) {
			_info=s;
		}

		@Override
		public double getTag() {
			return _tag;
		}

		@Override
		public void setTag(double t) {
			_tag=t;
		}


	}



	private HashMap<node_info,HashMap<node_info,Double>> edge_data;

	private HashMap<Integer,node_info> node_data;
	//count the number of edge
	private int sizeOfEdge;
	//count the changes in the graph
	private int modeCount;

	public weightedGraph() {
		edge_data= new  HashMap<node_info,HashMap<node_info,Double>>();
		node_data= new 	 HashMap<Integer,node_info>();	
		sizeOfEdge=0;
		modeCount=0;


	}

	public static void main(String[] args) {
		weighted_graph g =new weightedGraph();
		System.out.println("node size =4 ///edge Size=0 ");
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		System.out.println("node size: "+g.nodeSize());
		System.out.println("edge Size: "+g.edgeSize());
		System.out.println("connect 1-->2 true ,connect 1-->3 true,connect 2-->0 true, not connect 1-->0, edge Size=3 ");
		g.connect(1, 2, 0);System.out.println(g.hasEdge(1,2));
		g.connect(1, 3, 0);System.out.println(g.hasEdge(1,3));
		g.connect(2, 0, 0);System.out.println(g.hasEdge(2,0));
		System.out.println(g.hasEdge(1,0));
		System.out.println("edge Size: "+g.edgeSize());
		System.out.println("node size: "+g.nodeSize());

		System.out.println("remove 1, not connect 1-->2 false , not connect 1-->3 false,connect 2-->0 true, edge Size=1,size of node=3  ");
		//		g.removeNode(1);
		System.out.println(g.hasEdge(1,2));
		System.out.println(g.hasEdge(1,3));
		System.out.println(g.hasEdge(2,0));
		System.out.println("edge Size: "+g.edgeSize());
		System.out.println("node size: "+g.nodeSize());
		//		g.removeEdge(2, 0);
		System.out.println("edge Size: "+g.edgeSize());
		System.out.println("node size: "+g.nodeSize());
		//		g.removeEdge(2, 0);
		System.out.println("edge Size: "+g.edgeSize());
		System.out.println("print graph______________________________________________________________________________________");
		System.out.println(g);

		//		Collection<node_info> c = g.getV(1);
		//		while (c.iterator().hasNext()) {
		//			System.out.println(c.iterator().next().getKey());
		//		}

		//		HashMap<Integer,HashMap<Integer,Double>> ff=new HashMap<Integer,HashMap<Integer,Double>>(); 
		//		ff.put(1, new HashMap<Integer,Double>());
		//		ff.get(1).put(2, 2.0);
		////		ff.put(1, new HashMap<Integer,Double>());
		//		ff.get(1).put(3, 4.0);
		//		System.out.println(ff.get(1).size());
		//		Collection<Integer> nodes=ff.get(1).keySet();
		//System.out.println(nodes.size());
		//		
		//					for(Integer n:nodes ) {
		//						System.out.println(n);
		//					}

	}


	public node_info getNode(int key) {
		if(node_data.containsKey(key))
			return node_data.get(key) ;
		else 
			return null;
	}

	/**
	 * return true iff (if and only if) there is an edge between node1 and node2
	 * Note: this method should run in O(1) time.
	 * @param node1
	 * @param node2
	 * @return
	 */

	@Override
	public boolean hasEdge(int node1, int node2) {
		if(node_data.containsKey(node1)&&node_data.containsKey(node2) &&edge_data.containsKey(node_data.get(node1))&&edge_data.get(node_data.get(node1)).containsKey(node_data.get(node2))) {
			return true;
		}
		else
			return false;
	}
	/**
	 * return the weight if the edge (node1, node1). In case
	 * there is no such edge - should return -1
	 * Note: this method should run in O(1) time.
	 * @param node1
	 * @param node2
	 * @return
	 */
	@Override
	public double getEdge(int node1, int node2) {

		if(hasEdge( node1, node2)) {
			return edge_data.get(node_data.get(node1)).get(node_data.get(node2));
		}
		else
			return -1;
	}
	/**
	 * add a new node to the graph with the given key.
	 * Note: this method should run in O(1) time.
	 * Note2: if there is already a node with such a key -> no action should be performed.
	 * @param key
	 */
	@Override
	public void addNode(int key) {		
		if(!(node_data.containsKey(key))) {
			node_data.put(key,new nodeInfo(key));		
			modeCount++;
		}
		else return;
	}
	/**
	 * Connect an edge between node1 and node2, with an edge with weight >=0.
	 * Note: this method should run in O(1) time.
	 * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
	 */
	@Override
	public void connect(int node1, int node2, double w) {

		try {
			if(w<0)
				throw new Exception("The weight must be positive");
		} catch (Exception e) {e.printStackTrace();	}	

		if(node_data.containsKey(node1)&&node_data.containsKey(node2)) {

			if(edge_data.containsKey(node_data.get(node1))&&edge_data.get(node_data.get(node1)).containsKey(node_data.get(node2)))
				return;

			else
				if(!edge_data.containsKey(node_data.get(node1)))	
					edge_data.put(node_data.get(node1), new HashMap<node_info,Double>());
			if(!edge_data.containsKey(node_data.get(node2)))	
				edge_data.put(node_data.get(node2), new HashMap<node_info,Double>());

			edge_data.get(node_data.get(node1)).put(node_data.get(node2), w);
			edge_data.get(node_data.get(node2)).put(node_data.get(node1), w);
			sizeOfEdge++;
			modeCount++;

		}
		else 
			return;



	}
	/**
	 * This method return a pointer (shallow copy) for a
	 * Collection representing all the nodes in the graph.
	 * Note: this method should run in O(1) time
	 * @return Collection<node_data>
	 */
	@Override
	public Collection<node_info> getV() {
		return node_data.values();
	}
	/**
	 * This method returns a Collection containing all the
	 * nodes connected to node_id
	 * Note: this method should run in O(1) time.
	 * @return Collection<node_data>
	 */
	@Override
	public Collection<node_info> getV(int node_id) {
		if(node_data.containsKey(node_id)&&edge_data.containsKey(node_data.get(node_id))) {

			return edge_data.get(node_data.get(node_id)).keySet() ;
		}
		else 
			return null;
	}
	/**
	 * Delete the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 * This method should run in O(n), |V|=n, as all the edges should be removed.
	 * @return the data of the removed node (null if none).
	 * @param key
	 */
	@Override
	public node_info removeNode(int key) {
		if(node_data.containsKey(key)&&edge_data.containsKey(node_data.get(key))) {
			for(node_info n: getV(key)) {
				edge_data.get(n).remove(node_data.get(key));		
				sizeOfEdge--;
				modeCount++;

			}
			modeCount++;
			node_info removeNode=node_data.get(key);
			edge_data.remove(removeNode);
			node_data.remove(key);
			return removeNode; 
		}
		else
			return null;
	}
	/**
	 * Delete the edge from the graph,
	 * Note: this method should run in O(1) time.
	 * @param node1
	 * @param node2
	 */
	@Override
	public void removeEdge(int node1, int node2) {

		if(node_data.containsKey(node1)&&node_data.containsKey(node2) &&edge_data.containsKey(node_data.get(node1))&&edge_data.get(node_data.get(node1)).containsKey(node_data.get(node2))) {
			edge_data.get(node_data.get(node1)).remove(node_data.get(node2));
			edge_data.get(node_data.get(node2)).remove(node_data.get(node1));	
			//count the edge size and mode count.
			sizeOfEdge--;
			modeCount++;

		}
		else return;

	}

	@Override
	public int nodeSize() {

		return node_data.size();
	}

	@Override
	public int edgeSize() {
		return sizeOfEdge;
	}

	@Override
	public int getMC() {
		return modeCount;
	}

	public String toString() {

		String s="";
		for(node_info i : getV()) {
			s+="\n"+"Node= "+ i.getKey()+"\n";
			if(!edge_data.containsKey(i))
				s+="The node has no connections"+"\n";
			else {
				for(node_info j : getV(i.getKey())) {
					s+="connect to node: "+j.getKey()+" , the edge is: < "+i.getKey()+","+j.getKey()+" >"+"and the weight between them is: "+getEdge(i.getKey(),j.getKey()) +"\n";
				}	
			}
		}
		return s; 

	}


}
