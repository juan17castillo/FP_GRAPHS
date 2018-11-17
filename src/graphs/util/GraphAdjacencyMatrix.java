package graphs.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

public class GraphAdjacencyMatrix<V>{

	private Hashtable<V, Integer> hash;
	private DynamicMatrix matrix;
	private boolean isDirected;
	private int[] levels;
	
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

	public void BFS(V s)
	{
		levels = new int[matrix.getDimension()];
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int i = 0; i < matrix.getDimension(); i++) {
			list.add(new ArrayList<Integer>());
			
		}
        for (int i = 0; i < matrix.getDimension(); i++) {
			for (int j = 0; j < matrix.getDimension(); j++) {
				if(matrix.get(i, j)!=0)
				{
					list.get(i).add(j);
				}
			}
		}
		Queue<Integer> q = new LinkedList<Integer>();
        boolean[] vis = new boolean[matrix.getDimension()];
        levels[hash.get(s)] = 0;
        vis[hash.get(s)] = true;
        q.add(hash.get(s));
        while(!q.isEmpty()) {
        	int p = q.poll();
        	for (int j = 0; j < list.get(p).size(); j++) {
				if(!vis[list.get(p).get(j)])
				{
					levels[list.get(p).get(j)] = levels[p]+1;
					q.add(list.get(p).get(j));
					vis[list.get(p).get(j)] = true;
				}
			}	
		}
	}
	
	public boolean isEmpty() {
		return hash.isEmpty();
	}

	public DynamicMatrix getMatrix()
	{
		return matrix;
	}
}
