This project allows us to create a undirected weighted graph.
We create the nodes (with inner class node_info) 
And each node can be linked to another node (i.e. we will create ribs). In addition you can calculate the distance between nodes and find the shortest distance between nodes and more.

inner class nodeInfo:
This class builds a node with unique  key for the new  node.
For the dijkstra algorithm we need to keep the shortest distance between the source node and the destination node (use tag) .in addition we want to keep the node with the minimum distance on the next minimum tag node(use Parent).
And to know if we visited at this node we use info.
 

class WGraph_DS :
This department has two data structures:
1)node_data(HashMap) stores all the node we add to the graph by the key node. 
2)edge_data(HashMap in HashMap) store the rib between two node With weight .
This data structure allows us to delete, Search ,Add more: Usually in O(1)

class Graph_Algo:
This class allows you to see data on the graph: 

1) The shortest distance between two nodes-shortestPathDist(use dijkstra algorithm)

2) A list of the nodes with the shortest route between two nodes-shortestPath(use dijkstra algorithm)

3) A test of whether it is possible to get from any node to any other node-isConnected(use BFS algorithm)

4) Deep copy for graph -copy()

5) Init function gets a graph that allows you to point to new graph.

6) We can Saves this undirected weighted graph to the file name

7) We can Load file- undirected weighted graph and this graph point to file graph.

6) BFS- An algorithm used to move or search on graph- in run time o(n+v) when n is the number of nodes and v is the number of edge .

7) Dijkstra- This algorithm makes it possible to go over a weighted undirected graph
	 And find the cheapest ways from the source node to the rest of the graph nodes.
	 The weights in the graph symbolize distance. 
	 The shortest route between two points means the route with the lowest amount of weights between the two vertices.
	 Ran time- O(E*log(V)) because we create PriorityQueue and compare the node by the minimum distance .


How to create the graph:
1) We will create a graph  ---> graph g1 = new Graph_DS() .
2) We will add the node to the graph -->g1.addNode(n->int number);
3) start creating edges -->	g1.connect(int node_src , int node_dest,double weighte).
4) afther that you can remove node ,remove edge and more.

How to create the graph_algo:
1) first you need to  create graph
2) We will create a graph_algo -->graph_algorithms g=new Graph_Algo().
but afther that you need to init to same graph -->graph_algorithms g1=new Graph_Algo(), and init (graph g). 
3) afther that you can use shortestPathDist,isConnected,shortestPath,save and load file functions.

