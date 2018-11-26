package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.jupiter.api.Test;

import graphs.util.Edge;
import graphs.util.GraphAdjacencyMatrix;
import graphs.util.Vertex;
import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;
import model.City;
import model.Flight;

class MatrixTest {
	GraphAdjacencyMatrix<Integer, City, Flight> graph;
	
	public void scene1() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		graph = new GraphAdjacencyMatrix<>(true);
		graph.addVertex(new City("Cali", 1002, 1356.0, 1342.1), 1002);
		graph.addVertex(new City("Medellin", 204, 12312.2, 12312.1), 204);
		graph.addEdge(1002, 204, new Flight(102, 419, "Avianca"));
	}
	
	public void scene2() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		graph = new GraphAdjacencyMatrix<>(true);
		scene1();
		graph.addVertex(new City("Cartagena", 340, 12312.1, 6545.2), 340);
		graph.addEdge(340, 204, new Flight(43242, 629, "Avianca") );
	}
	
	public void scene3() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene2();
		graph.addVertex(new City("Pereira", 237, 12312.8, 43534.3), 237);
	}
	@Test
	void testAddVertex() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		graph.addVertex(new City("Bogota", 178, 43543.1, 56445.3), 178);
		assertEquals(3, graph.getMatrix().getDimension());
		
		scene2();
		graph.addVertex(new City("Palmira", 145, 543534.3, 436544.4), 145);
		assertTrue(graph.containsVertex(145));
		
		scene3();
		boolean b = graph.containsVertex(1002);
		try {
			graph.addVertex(new City("Cali", 1002, 5436354.3, 32432.2), 1002);
		} catch (Exception e) {
			b = true;
		}
		assertTrue(b);
	}
	@Test
	public void testAddEdge() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		graph.addVertex(new City("Bogota", 178, 5425345.4, 43634.2), 178);
		graph.addEdge(178, 1002, new Flight(432342,  900, "Avianca"));
		assertTrue(graph.containsEdge(178, 1002));
		
		scene2();
		graph.addEdge(340, 1002, new Flight(213312, 198, "Avianca"));
		assertFalse(graph.containsEdge(1002, 340));
		
		scene3();
		boolean b = false;
		try {
			graph.addEdge(1002, 204, new Flight(34234, 143, "Avianca"));
		} catch (Exception e) {
			b = true;
		}
		assertTrue(b);
	}

	@Test
	public void clearTest() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene3();
		graph.clear();
		assertEquals(0, graph.getMatrix().getDimension());
	}
	
	@Test
	public void containsEdgeTest() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		assertTrue(graph.containsEdge(1002, 204));
		
		scene2();
		assertFalse(graph.containsEdge(204, 1002));
		
		scene3();
		assertFalse(graph.containsEdge(237, 1002)&&graph.containsEdge(237, 204));
	}
	
	@Test
	public void containsVertexTest() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		assertTrue(graph.containsVertex(204));
		
		scene2();
		assertFalse(graph.containsVertex(178));
	}
	
	@Test
	public void isEmptyTest() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		assertFalse(graph.isEmpty());
		
		scene2();
		graph.clear();
		assertTrue(graph.isEmpty());
	}
	
	@Test
	public void BFSTest() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		graph.BFS(1002);
		int[] ar = graph.getLevels();
		assertEquals(1, ar[1]);
		
		scene2();
		graph.addEdge(204, 340, new Flight(423423.32, 245, "Avianca"));
		graph.BFS(1002);
		
		int[] arr = graph.getLevels();
		assertEquals(2, arr[2]);
		
		scene3();
		graph.BFS(1002);
		int[] arrr = graph.getLevels();
		assertEquals(0, arrr[3]);
	}
	
	@Test
	public void testDijkstra() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		GraphAdjacencyMatrix<Character, Vert, Edg> g = new GraphAdjacencyMatrix<>(false);
		g.addVertex(new Vert('A'), 'A');
		g.addVertex(new Vert('B'), 'B');
		g.addVertex(new Vert('C'), 'C');
		g.addVertex(new Vert('D'), 'D');
		g.addVertex(new Vert('E'), 'E');
		g.addVertex(new Vert('F'), 'F');
		g.addVertex(new Vert('G'), 'G');
		g.addVertex(new Vert('H'), 'H');
		g.addEdge('A', 'B', new Edg(3));
		g.addEdge('A', 'C', new Edg(1));
		g.addEdge('C', 'F', new Edg(5));
		g.addEdge('C', 'D', new Edg(2));
		g.addEdge('F', 'H', new Edg(3));
		g.addEdge('F', 'D', new Edg(2));
		g.addEdge('H', 'E', new Edg(1));
		g.addEdge('D', 'E', new Edg(4));
		g.addEdge('D', 'B', new Edg(1));
		g.addEdge('B', 'G', new Edg(5));
		g.addEdge('G', 'E', new Edg(2));
		g.Dijkstra('A');
		int[] less = g.getLessDistance();
		int[] l = {0,3,1,3,7,5,8,8};
		for (int i = 0; i < less.length; i++) {
			assertEquals(less[i], l[i]);
		}
	}
	
	@Test
	public void testKruskal() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		GraphAdjacencyMatrix<Character, Vert, Edg> g = new GraphAdjacencyMatrix<>(false);
		g.addVertex(new Vert('A'), 'A');
		g.addVertex(new Vert('B'), 'B');
		g.addVertex(new Vert('C'), 'C');
		g.addVertex(new Vert('D'), 'D');
		g.addVertex(new Vert('E'), 'E');
		g.addVertex(new Vert('F'), 'F');
		g.addVertex(new Vert('G'), 'G');
		g.addVertex(new Vert('H'), 'H');
		g.addVertex(new Vert('I'), 'I');
		g.addVertex(new Vert('J'), 'J');
		g.addEdge('D', 'E', new Edg(64));
		g.addEdge('A', 'B', new Edg(84));
		g.addEdge('I', 'G', new Edg(85));
		g.addEdge('G', 'E', new Edg(92));
		g.addEdge('C', 'D', new Edg(95));
		g.addEdge('H', 'I', new Edg(98));
		g.addEdge('B', 'C', new Edg(130));
		g.addEdge('F', 'C', new Edg(176));
		g.addEdge('F', 'G', new Edg(224));
		g.addEdge('H', 'F', new Edg(245));
		g.addEdge('I', 'J', new Edg(257));
		g.addEdge('B', 'G', new Edg(268));
		g.addEdge('J', 'E', new Edg(399));
		ArrayList<Edge<Character, Vert, Edg>> l = g.kruskalMST();
		int s = 0;
		for (int i = 0; i < l.size(); i++) {
			s+=l.get(i).getWeight();
		}
		assertEquals(1081, s);
	}
}

//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.