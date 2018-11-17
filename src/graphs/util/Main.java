package graphs.util;

import graphs.util.Exceptions.VertexExistException;

public class Main {

	private GraphAdjacencyList<Integer, String, Integer> graphList;
	private Vertex<Integer, String, Integer> v1;
	private Vertex<Integer, String, Integer> v2;
	private Vertex<Integer, String, Integer> v3;
	private Vertex<Integer, String, Integer> v4;
	private Vertex<Integer, String, Integer> v5;
	private Edge<Integer, String, Integer> e1;
	private Edge<Integer, String, Integer> e2;
	private Edge<Integer, String, Integer> e3;
	private Edge<Integer, String, Integer> e4;
	private Edge<Integer, String, Integer> e5;
	
	public Main() throws VertexExistException
	{
		graphList = new GraphAdjacencyList<>(false);
		v1 = new Vertex<Integer, String, Integer>("1", 1);
		v2 = new Vertex<Integer, String, Integer>("2", 2);
		v3 = new Vertex<Integer, String, Integer>("3", 3);
		v4 = new Vertex<Integer, String, Integer>("4", 4);
		v5 = new Vertex<Integer, String, Integer>("5", 5);
		e1 = new Edge<Integer, String, Integer>(v1, v3, 4);
		e2 = new Edge<Integer, String, Integer>(v3, v4, 1);
		e3 = new Edge<Integer, String, Integer>(v2, v5, 2);
		e4 = new Edge<Integer, String, Integer>(v5, v3, 6);
		e5 = new Edge<Integer, String, Integer>(v2, v1, 4);
		graphList.addVertex("1", 1);
	}
}
