package graphs.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;
import graphs.util.Pair;

public class GraphAdjacencyMatrix<V>{

	private Hashtable<V, Integer> hash;
	private DynamicMatrix matrix;
	private boolean isDirected;
	private int[] levels, lessDistance;
	
	public GraphAdjacencyMatrix(boolean is) {
		isDirected = is;
		matrix = new DynamicMatrix();
		hash = new Hashtable<>();
	}
	
	public void addVertex(V element) throws VertexExistException {
		if(hash.containsKey(element))
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
	
	public void Dijkstra(V k)
	{
		ArrayList<ArrayList<Pair<Integer, Integer>>> list = new ArrayList<>();
		for (int i = 0; i < matrix.getDimension(); i++) {
			list.add(new ArrayList<>());
		}
		for (int i = 0; i < matrix.getDimension(); i++) {
			for (int j = 0; j < matrix.getDimension(); j++) {
				if(matrix.get(i, j)!=0)
				{
					list.get(i).add(new Pair<Integer, Integer>(matrix.get(i, j), j));
				}
			}
		}
		lessDistance = new int[matrix.getDimension()];
		for (int i = 0; i < lessDistance.length; i++) {
			lessDistance[i] = (int)Math.pow(10, 9);
		}
		boolean[] vis = new boolean[matrix.getDimension()];
		lessDistance[hash.get(k)] = 0;
		PriorityQueue<Pair<Integer, Integer>> cola = new PriorityQueue<>();
		cola.add(new Pair<Integer, Integer>(0, hash.get(k)));
		while(!cola.isEmpty())
		{
			Pair<Integer, Integer> pair = cola.poll();
			int x = pair.getValue();
			if(!vis[x])
			{
				vis[x] = true;
				for (int i = 0; i < list.get(x).size(); i++) {
					int e = list.get(x).get(i).getValue();
					int w = list.get(x).get(i).getKey();
					if(lessDistance[x] + w < lessDistance[e])
					{
						lessDistance[e] = lessDistance[x] + w;
						cola.add(new Pair<Integer, Integer>(lessDistance[e], e));
					}
				}
			}
		}
//		for (int i = 0; i < vis.length; i++) {
//			System.out.println(lessDistance[i]);
//		}
	}
	
	public boolean isEmpty() {
		return hash.isEmpty();
	}

	public DynamicMatrix getMatrix()
	{
		return matrix;
	}
	
	public int[] getLevels()
	{
		return levels;
	}
}
