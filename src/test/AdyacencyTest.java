package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import graphs.util.Edge;
import graphs.util.GraphAdjacencyList;
import graphs.util.GraphAdjacencyMatrix;
import graphs.util.Vertex;
import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;
import model.City;
import model.Flight;

class AdyacencyTest {
	GraphAdjacencyList<Integer, City, Flight> graph;
	
	public void scene1() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		graph = new GraphAdjacencyList<>(true);
		graph.addVertex(new City("Cali", 1,  1, 2), 1);
		graph.addVertex(new City("Medellin", 2, 3, 2), 2);
		graph.addEdge(1, 2, new Flight(620, 1000000, "Cali-Medellin"));
	}
	
	public void scene2() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		graph = new GraphAdjacencyList<>(true);
		scene1();
		graph.addVertex(new City("Cartagena", 3,  1, 2), 3);
		graph.addEdge(3, 2, new Flight(760, 3000000, "Cartagena-Medellin"));
	}
	
	public void scene3() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene2();
		graph.addVertex(new City("Pereira", 4,  1, 2), 4);
	}
	@Test
	void testAddVertex() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		graph.addVertex(new City("Bogota", 5,  1, 2), 5);
		assertEquals(3, graph.getVertexList().size());
		
		scene2();
		graph.addVertex(new City( "Palmira", 6, 1, 2), 6);
		assertTrue(graph.containsVertex(6));
		
		scene3();
		boolean b = graph.containsVertex(1);
		try {
			graph.addVertex(new City("Cali", 1,  1, 2), 1);
		} catch (Exception e) {
			b = true;
		}
		assertTrue(b);
	}
	@Test
	public void testAddEdge() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		graph.addVertex(new City("Bogota", 5,  1, 2), 5);
		graph.addEdge(5, 1, new Flight(760, 3000000, "Bogota-Cali"));
		assertTrue(graph.containsEdge(5,1));
		
		scene2();
		graph.addEdge(3, 1, new Flight(760, 3000000, "Cartagena-Cali"));
		assertFalse(graph.containsEdge(1, 3));
		
		scene3();
		boolean b = false;
		try {
			graph.addEdge(1, 2, new Flight(760, 3000000, "Cali-Medellin"));
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
		assertEquals(0, graph.getVertexList().size());
	}
	
	@Test
	public void containsEdgeTest() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		assertTrue(graph.containsEdge(1, 2));
		
		scene2();
		assertFalse(graph.containsEdge(2, 1));
		
		scene3();
		assertFalse(graph.containsEdge(4, 1)&&graph.containsEdge(2, 1));
	}
	
	@Test
	public void containsVertexTest() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		assertTrue(graph.containsVertex(2));
		
		scene2();
		assertFalse(graph.containsVertex(5));
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
	public void DFSTest() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		List<City> e = graph.dfs(1);
		assertEquals("Medellin", e.get(0).getName()) ;
		
		scene2();
		graph.addEdge(2, 3, new Flight(760, 3000000, "Medellin-Cartagena"));
		e = graph.dfs(1);
		assertEquals("Cartagena", e.get(0).getName()) ;
		
	}
	
	@Test
	public void testDijkstra() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		GraphAdjacencyList<Character, Vert, Edg> g = new GraphAdjacencyList<>(false);
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
		GraphAdjacencyList<Character, Vert, Edg> g = new GraphAdjacencyList<>(false);
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