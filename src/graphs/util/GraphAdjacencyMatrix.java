package graphs.util;

import java.util.Hashtable;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

public class GraphAdjacencyMatrix<V>{

	private Hashtable<V, Integer> hash;
	private DynamicMatrix matrix;
	private boolean isDirected;
	
	public GraphAdjacencyMatrix(boolean is) {
		isDirected = is;
		matrix = new DynamicMatrix();
		hash = new Hashtable<>();
	}
	
	public void addVertex(V element) throws VertexExistException {
		if(hash.contains(element))
		{
			throw new VertexExistException("Vertex already exist", element);
		}
		else
		{
			hash.put(element, matrix.addElement());
			
		}
		
	}

	
	public void addEdge(V VertexSource, V VertexEnd, int infoEdge)
			throws VertexDoesnotExistException, EdgeExistException {
		if(!hash.containsKey(VertexSource))
		{
			throw new VertexDoesnotExistException("Vertex doesn't exist", VertexSource);
		}
		if(!hash.containsKey(VertexEnd))
		{
			throw new VertexDoesnotExistException("Vertex doesn't exist", VertexEnd);
		}
		if(matrix.get(hash.get(VertexSource), hash.get(VertexEnd))!=0)
		{
			throw new EdgeExistException("Edge already exist", VertexEnd, VertexEnd);
		}
		matrix.add(infoEdge, hash.get(VertexSource), hash.get(VertexEnd));
		if(!isDirected)
		{
			matrix.add(infoEdge, hash.get(VertexEnd), hash.get(VertexSource));
		}
		
	}

	
	public void clear() {
		hash.clear();
		matrix = new DynamicMatrix();
		
	}

	
	public boolean containsVertex(V Vertex) {
		return hash.containsKey(Vertex);
		
	}

	
	public boolean containsEdge(V VertexSource, V VertexEnd) throws VertexDoesnotExistException {
		if(!hash.containsKey(VertexSource))
		{
			throw new VertexDoesnotExistException("Vertex doesn't exist", VertexSource);
		}
		if(!hash.containsKey(VertexEnd))
		{
			throw new VertexDoesnotExistException("Vertex doesn't exist", VertexEnd);
		}
		return matrix.get(hash.get(VertexSource), hash.get(VertexEnd))!=0;
		
	}

	
	public boolean isEmpty() {
		return hash.isEmpty();
	}

}
