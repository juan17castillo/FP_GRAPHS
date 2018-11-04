package graphs.util;

import java.util.Iterator;
import java.util.List;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

public class Prueba {

	public static void main(String[] args) throws VertexExistException, VertexDoesnotExistException, EdgeExistException {
		
		GraphAdjacencyList<Integer, String, Integer> grafo = new GraphAdjacencyList<Integer, String, Integer>(false);
		
		grafo.addVertex("Hola", 1);
		grafo.addVertex("Como estas", 2);
		grafo.addEdge(1, 2, 5);
		
		List<String> a = grafo.getVertexElements();
		System.out.println(a.get(0));
		System.out.println(a.get(1));
		
		List<List<Edge<Integer, String, Integer>>> g = grafo.getAdjacencyList();
		for (int i = 0; i < g.size(); i++) {
			for (int j = 0; j < g.get(i).size(); j++) {
				System.out.println(g.get(i).get(j).getSourceVertex().getId() + " " +g.get(i).get(j).getEndVertex().getId() );
			}
		}
		
		System.out.println(grafo.containsVertex(2));
		System.out.println(grafo.containsVertex(1));
		System.out.println(grafo.containsVertex(4));
		System.out.println(grafo.containsEdge(1, 2));
		System.out.println(grafo.containsEdge(2, 1));

	}

}
