package graphs.util;

import java.util.Iterator;
import java.util.List;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

public class Prueba {

	public static void main(String[] args) throws VertexExistException, VertexDoesnotExistException, EdgeExistException {
		
		GraphAdjacencyMatrix<Integer> grafo = new GraphAdjacencyMatrix<>(false);
		for (int i = 0; i < 20; i++) {
			grafo.addVertex(i+1);
		}
		grafo.addEdge(11, 1, 1);
		grafo.addEdge(1, 2, 1);
		grafo.addEdge(13, 3, 1);
		grafo.addEdge(15, 4, 1);
		grafo.addEdge(17, 5, 1);
		grafo.addEdge(11, 6, 1);
		grafo.addEdge(2, 7, 1);
		grafo.addEdge(1, 8, 1);
		grafo.addEdge(15, 9, 1);
		grafo.addEdge(4, 10, 1);
		grafo.addEdge(15, 12, 1);
		grafo.addEdge(5, 13, 1);
		grafo.addEdge(2, 14, 1);
		grafo.addEdge(17, 15, 1);
		grafo.addEdge(15, 16, 1);
		grafo.addEdge(11, 17, 1);
		grafo.addEdge(15, 18, 1);
		grafo.addEdge(9, 19, 1);
		grafo.addEdge(16, 20, 1);
		//grafo.getMatrix().print();
		grafo.BFS(1);
	}

}
