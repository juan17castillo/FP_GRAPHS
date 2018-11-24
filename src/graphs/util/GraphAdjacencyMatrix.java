package graphs.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;
import test.Edge1;
import graphs.util.Pair;

public class GraphAdjacencyMatrix<K, V extends IVertex<K>, A extends IEdge> implements IGraph<K, V, A>{

	private Hashtable<K, V> hashVertex;
	private Hashtable<K, Integer> hashKeys;
	private DynamicMatrix matrix;
	private boolean isDirected;
	private int[] levels, lessDistance;
	
	public GraphAdjacencyMatrix(boolean is) {
		isDirected = is;
		matrix = new DynamicMatrix();
		hashVertex = new Hashtable<>();
		hashKeys = new Hashtable<>();
	}
	@Override
	public void addVertex(V element, K key) throws VertexExistException {
		if(hashVertex.containsKey(key))
		{
			throw new VertexExistException("Vertex already exist", element);
		}
		else
		{
			hashVertex.put(element.getId(), element);
			hashKeys.put(key, matrix.addElement());
		}
		
	}

	@Override
	public void addEdge(K IdVertexSource, K IdVertexEnd, A infoEdge)
			throws VertexDoesnotExistException, EdgeExistException {
		if(!hashVertex.containsKey(IdVertexSource))
		{
			throw new VertexDoesnotExistException("Vertex doesn't exist", IdVertexSource);
		}
		if(!hashVertex.containsKey(IdVertexEnd))
		{
			throw new VertexDoesnotExistException("Vertex doesn't exist", IdVertexEnd);
		}
		if(matrix.get(hashKeys.get(IdVertexSource), hashKeys.get(IdVertexEnd))!=0)
		{
			throw new EdgeExistException("Edge already exist", IdVertexEnd, IdVertexEnd);
		}
		matrix.add(infoEdge.getWeightCost(), hashKeys.get(IdVertexSource), hashKeys.get(IdVertexEnd));
		if(!isDirected)
		{
			matrix.add(infoEdge.getWeightCost(), hashKeys.get(IdVertexEnd), hashKeys.get(IdVertexSource));
		}
		
	}

	@Override
	public void clear() {
		hashVertex.clear();
		hashKeys.clear();
		matrix = new DynamicMatrix();
		
	}

	@Override
	public boolean containsVertex(K Vertex) {
		return hashVertex.containsKey(Vertex);
		
	}

	@Override
	public boolean containsEdge(K VertexSource, K VertexEnd) throws VertexDoesnotExistException {
		if(!hashVertex.containsKey(VertexSource))
		{
			throw new VertexDoesnotExistException("Vertex doesn't exist", VertexSource);
		}
		if(!hashVertex.containsKey(VertexEnd))
		{
			throw new VertexDoesnotExistException("Vertex doesn't exist", VertexEnd);
		}
		return matrix.get(hashKeys.get(VertexSource), hashKeys.get(VertexEnd))!=0;
		
	}

	public void BFS(K s)
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
        levels[hashKeys.get(s)] = 0;
        vis[hashKeys.get(s)] = true;
        q.add(hashKeys.get(s));
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
	
	public void Dijkstra(K k)
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
		lessDistance[hashKeys.get(k)] = 0;
		PriorityQueue<Pair<Integer, Integer>> cola = new PriorityQueue<>();
		cola.add(new Pair<Integer, Integer>(0, hashKeys.get(k)));
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
	}
	
	public ArrayList<Edge1> kruskalMST(){
		ArrayList<Edge1> graphEdges = new ArrayList<>();
		if(isDirected)
		{
			for (int i = 0; i < matrix.getDimension(); i++) {
				for (int j = 0; j < matrix.getDimension(); j++) {
					if(matrix.get(i, j)!=0)
					{
						graphEdges.add(new Edge1(i, j, matrix.get(i, j)));
					}
				}
			}
		}
		else
		{
			for (int i = 0; i < matrix.getDimension(); i++) {
				for (int j = i; j < matrix.getDimension(); j++) {
					if(matrix.get(i, j)!=0)
					{
						graphEdges.add(new Edge1(i, j, matrix.get(i, j)));
					}
				}
			}
		}
		
		Collections.sort(graphEdges);
		ArrayList<Edge1> mstEdges = new ArrayList<>();
		DisjointSet nodeSet = new DisjointSet(matrix.getDimension()+1);
		for(int i=0; i<graphEdges.size() && mstEdges.size()<(matrix.getDimension()-1); i++){
			Edge1 currentEdge = graphEdges.get(i);
			int root1 = nodeSet.find(currentEdge.getVertex1());
			int root2 = nodeSet.find(currentEdge.getVertex2());
			if(root1 != root2){
				mstEdges.add(currentEdge);
				nodeSet.union(root1, root2);
			}
		}
		return mstEdges;
	}
	@Override
	public boolean isEmpty() {
		return hashVertex.isEmpty();
	}

	public DynamicMatrix getMatrix()
	{
		return matrix;
	}
	
	public int[] getLevels()
	{
		return levels;
	}
	
	public int[] getLessDistance()
	{
		return lessDistance;
	}
}
