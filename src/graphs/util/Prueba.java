package graphs.util;

import java.util.Iterator;
import java.util.List;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

public class Prueba {

	public static void main(String[] args) throws VertexExistException, VertexDoesnotExistException, EdgeExistException {
		
		GraphAdjacencyMatrix<String> grafo = new GraphAdjacencyMatrix<>(true);
		
		grafo.addVertex("Hola");
		grafo.addVertex("Como estas");
		grafo.addEdge("Hola", "Como estas", 5);
		
//		List<String> a = grafo.getVertexElements();
//		System.out.println(a.get(0));
//		System.out.println(a.get(1));
		
//		List<List<Edge<Integer, String, Integer>>> g = grafo.getAdjacencyList();
//		for (int i = 0; i < g.size(); i++) {
//			for (int j = 0; j < g.get(i).size(); j++) {
//				System.out.println(g.get(i).get(j).getSourceVertex().getId() + " " +g.get(i).get(j).getEndVertex().getId() );
//			}
//		}
		
		System.out.println(grafo.containsVertex("Como estas"));
		System.out.println(grafo.containsVertex("Hola"));
		System.out.println(grafo.containsVertex("Hi"));
		System.out.println(grafo.containsEdge("Hola", "Como estas"));
		System.out.println(grafo.containsEdge("Como estas", "Hola"));
		
//		GraphAdjacencyMatrix<String> graph = new GraphAdjacencyMatrix<>(true);
//		graph.addVertex("Cristian");
//		graph.addVertex("Timmy");
//		graph.addEdge("Cristian", "Timmy", 4);
//		System.out.println(graph.containsEdge("Cristian", "Timmy"));

//		DynamicMatrix matrix = new DynamicMatrix();
//		matrix.addElement();
//		matrix.addElement();
//		matrix.addElement();
//		matrix.addElement();
//		matrix.addElement();
//		//System.out.println(matrix.matrix.get(5).size());
//		matrix.add(5, 4, 4);
//		matrix.add(1, 2, 3);
//		//matrix.removeElement(4);
//		matrix.print();
	}

}
