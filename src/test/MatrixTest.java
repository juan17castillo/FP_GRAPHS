package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import graphs.util.GraphAdjacencyMatrix;
import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

class MatrixTest {
	GraphAdjacencyMatrix<String> graph;
	
	public void scene1() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		graph = new GraphAdjacencyMatrix<>(true);
		graph.addVertex("Cali");
		graph.addVertex("Medellin");
		graph.addEdge("Cali", "Medellin", 419);
	}
	
	public void scene2() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		graph = new GraphAdjacencyMatrix<>(true);
		scene1();
		graph.addVertex("Cartagena");
		graph.addEdge("Cartagena", "Medellin", 629);
	}
	
	public void scene3() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene2();
		graph.addVertex("Pereira");
	}
	@Test
	void testAddVertex() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		graph.addVertex("Bogota");
		assertEquals(3, graph.getMatrix().getDimension());
		
		scene2();
		graph.addVertex("Palmira");
		assertTrue(graph.containsVertex("Palmira"));
		
		scene3();
		boolean b = graph.containsVertex("Cali");
		try {
			graph.addVertex("Cali");
		} catch (Exception e) {
			b = true;
		}
		assertTrue(b);
	}
	@Test
	public void testAddEdge() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		graph.addVertex("Bogota");
		graph.addEdge("Bogota", "Cali", 900);
		assertTrue(graph.containsEdge("Bogota", "Cali"));
		
		scene2();
		graph.addEdge("Cartagena", "Cali", 198);
		assertFalse(graph.containsEdge("Cali", "Cartagena"));
		
		scene3();
		boolean b = false;
		try {
			graph.addEdge("Cali", "Medellin", 143);
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
		assertTrue(graph.containsEdge("Cali", "Medellin"));
		
		scene2();
		assertFalse(graph.containsEdge("Medellin", "Cali"));
		
		scene3();
		assertFalse(graph.containsEdge("Pereira", "Cali")&&graph.containsEdge("Pereira", "Medellin"));
	}
	
	@Test
	public void containsVertexTest() throws VertexExistException, VertexDoesnotExistException, EdgeExistException
	{
		scene1();
		assertTrue(graph.containsVertex("Medellin"));
		
		scene2();
		assertFalse(graph.containsVertex("Bogota"));
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
		graph.BFS("Cali");
		int[] ar = graph.getLevels();
		assertEquals(1, ar[1]);
		
		scene2();
		graph.addEdge("Medellin", "Cartagena", 425);
		graph.BFS("Cali");
		
		int[] arr = graph.getLevels();
		assertEquals(2, arr[2]);
		
		scene3();
		graph.BFS("Cali");
		int[] arrr = graph.getLevels();
		assertEquals(0, arrr[3]);
	}
}