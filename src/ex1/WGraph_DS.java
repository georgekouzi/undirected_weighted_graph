package ex1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class creates an undirected weighted graph that holds the addition of nodes
 * You can add new nodes, connect two nodes by ribs, delete nodes, delete ribs between two nodes, 
 * you can check if a rib exists, you can get a particular node, 
 * you can get the list of all the neighbors of a particular node and the list of nodes.
 * In addition, you can know the number of nodes in the graph, the number of ribs 
 * and the number of actions performed in the graph (such as deleting the addition of a node)
 * This class have Inner class (node_info) ,And with this class we built the vertices of the graph.
 * Each node has the option to keep its neighbors in HashMap of HashMap- (edge_data) and all the node that we add to the graph save in HashMap- (node_data).   
 * @author george kouzy
 *
 */

public class WGraph_DS implements weighted_graph,Serializable  {

	private static final long serialVersionUID = 1L;

	// HashMap in HashMap hold all the edges and the weights in the graph: first key node_info(src), next key node_info(dest) and the value is double (weight).
	private HashMap<node_info,HashMap<node_info,Double>> edge_data;
	// This HashMap hold all the nodes in the graph.  
	private HashMap<Integer,node_info> node_data;
	//count the number of edge
	private int sizeOfEdge;
	//count the changes in the graph
	private int modeCount;

	///Constructor///
	public WGraph_DS() {
		edge_data= new  HashMap<node_info,HashMap<node_info,Double>>();
		node_data= new 	 HashMap<Integer,node_info>();	
		sizeOfEdge=0;
		modeCount=0;
	}

	/**
	 * return the node_info by the node id if key node exist.
	 * run time- O(1).
	 * @param key - the node id
	 * @return the node_data by the node_id,return null if the node didn't create .
	 */
	public node_info getNode(int key) {
		if(node_data.containsKey(key))
			return node_data.get(key) ;
		else 
			return null;
	}

	/**
	 * return true if node1 exist and node2 exist and if there is an edge between node1 and node2
	 * run time- O(1).
	 * @param node1
	 * @param node2
	 * @return-true or false
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
	 * check if node1 exist and node2 exist and if there is an edge between node1 and node2 
	 * In case there is no such edge - should return -1
	 * run time- O(1).
	 * @param node1
	 * @param node2
	 * @return- The weight if the edge (node1, node1) exist else return -1.
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
	 * key must be positive.
	 * run time- O(1).
	 * if there is already a node with such a key no action be performed.
	 * This is an action that is done on the graph, so mode count need to be updated(+1).
	 * @param key -node_data. 
	 */
	@Override
	public void addNode(int key) {		
		try {
			if(key<0)
				throw new Exception("The key must be positive");
		} catch (Exception e) {e.printStackTrace(); return;	}	

		if(!(node_data.containsKey(key))) {
			node_data.put(key,new nodeInfo(key));		
			modeCount++;
		}
		else return;
	}
	/**
	 * Connect an edge between node1 and node2, with an edge with weight >=0.
	 * check if node1 exist and node2 exist and if there is an edge between node1 and node2 
	 *  this method should run in O(1) time.
	 *  if the edge node1-node2 already exists - the method simply updates the weight of the edge.
	 *  @param node1
	 *  @param node2
	 *  @param w - The weight between node1 and node2.
	 */
	@Override
	public void connect(int node1, int node2, double w) {
		if(node1==node2) {
			return;
		}
		//weight < 0 throw exception
		try {
			if(w<0)
				throw new Exception("The weight must be positive");
		} catch (Exception e) {e.printStackTrace(); return;	}	


		if(node_data.containsKey(node1)&&node_data.containsKey(node2)) {
			// if the edge  already exists - the method simply updates the weight of the edge.
			if(edge_data.containsKey(node_data.get(node1))&&edge_data.get(node_data.get(node1)).containsKey(node_data.get(node2))) {
				//if this  weight not equal to the new equal we update mc.
				if(edge_data.get(node_data.get(node1)).get(node_data.get(node2))!=w) {
					modeCount++;
				}
				edge_data.get(node_data.get(node1)).put(node_data.get(node2), w);
				edge_data.get(node_data.get(node2)).put(node_data.get(node1), w);

				return;
			}
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
	 * This method return a pointer (shallow copy) for the
	 * Collection representing all the nodes in the graph.
	 * run time O(1).
	 * @return Collection of all the node in this graph.
	 */
	@Override
	public Collection<node_info> getV() {
		return node_data.values();
	}
	/**
	 *
	 * This method returns a Collection containing all the
	 * nodes connected to node_id
	 * run time O(1).
	 * @return Collection<node_info> else return empty Collection.
	 */
	@Override
	public Collection<node_info> getV(int node_id) {
		if(node_data.containsKey(node_id)&&edge_data.containsKey(node_data.get(node_id))) {

			return edge_data.get(node_data.get(node_id)).keySet() ;
		}
		else {
			Collection<node_info> nib =new ArrayList<node_info>();
			//return empty Collection
			return nib;
		}
	}
	/**
	 * Removes all edges which starts or ends at this node.
	 * Delete the node (with the given ID) from the graph.
	 * update the number of rib and mode count. 
	 * Run time  O(n), |V|=n, as all the edges should be removed.
	 * @return the data of the removed node (null if none).
	 * @param key
	 */
	@Override
	public node_info removeNode(int key) {
		if(node_data.containsKey(key)) {
			//if node(key) have neighbors.
			if(edge_data.containsKey(node_data.get(key))) {
				for(node_info n: getV(key)) {
					edge_data.get(n).remove(node_data.get(key));		
					sizeOfEdge--;
					modeCount++;
				}
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
	 * Delete the edge from the graph if exist,
	 * run time O(1).
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
	/** return the number of vertices (nodes) in the graph.
	 * run time O(1).
	 * @return - int number of node in the graph.
	 */
	@Override
	public int nodeSize() {

		return node_data.size();
	}
	/**
	 * return the number of edges (unidirectional graph).
	 * run time O(1).
	 * @return
	 */
	@Override
	public int edgeSize() {
		return sizeOfEdge;
	}/**
	 * return the Mode Count - for testing changes in the graph.
	 * Any change(remove node and edge,add node, connect between two nodes and update weight between two nodes that already exist)
	 * in the inner state of the graph should cause an increment in the ModeCount
	 * @return - int mode count.
	 */
	@Override
	public int getMC() {
		return modeCount;
	}
	/**
	 * string function return all the data of nodes and edge on the graph
	 * @return s
	 */
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
	/**
	 * This inner class allows you to create nodes with unique key and do various actions like delete creation and more.
	 * Each node can hold another node (parent).
	 * tag- holds the cheapest weight .
	 * and info - indicates if we visited this node.
	 * @author george kouzy
	 *
	 */
	protected class nodeInfo implements node_info,Serializable {
		private static final long serialVersionUID = 1L;
		private int _key;
		private String _info;
		private double _tag;
		private node_info parent;


		public nodeInfo(int key)  {
			_key=key;
			_tag=0.0;
			_info="";
			parent=null;
		}

		public node_info getParent() {
			return parent;
		}
		public void setParent(node_info p) {
			parent =p;
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
		public String toString() {
			return "Node = "+ _key;
		}


		/**
		 * hash code by key node
		 * @return - obj hash by key
		 */

		public int hashCode() {
			return Objects.hash(_key);
		}

		/**
		 * this function equal between two node if they have same key return true else false.
		 * @return true or false. 
		 */

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}
			if (!(object instanceof nodeInfo)) { 
				return false;
			}
			nodeInfo otherObject = (nodeInfo) object;
			if (_key != otherObject._key) {
				return false;
			}
			else
				return true;
		}



	}

	/**
	 * equals function -  if two graph are equal return true
	 * @return true if this edge_data equal to other edge_data and this node_data equal to other node_data
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof WGraph_DS)) {
			return false;
		}
		WGraph_DS otherObject =  (WGraph_DS) obj;
		if (!node_data.equals(otherObject.node_data)||!(edge_data.equals(otherObject.edge_data))) {
			return false;
		}
		else
			return true;

	}
	//Maybe use in the future
	public int hashCode() {
		return Objects.hash(node_data,edge_data);
	}


}
