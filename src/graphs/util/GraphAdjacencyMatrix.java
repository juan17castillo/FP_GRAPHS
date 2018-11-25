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
import graphs.util.Pair;

public class GraphAdjacencyMatrix<K, V extends IVertex<K>, A extends IEdge> implements IGraph<K, V, A>{

	private Hashtable<K, Vertex<K, V, A>> hashVertex;
	private Hashtable<K, Integer> hashKeys;
	private Hashtable<Integer, K> hashIndex;
	private DynamicMatrix<K, V, A> matrix;
	private boolean isDirected;
	private int[] levels, lessDistance;
	
	public GraphAdjacencyMatrix(boolean is) {
		isDirected = is;
		matrix = new DynamicMatrix<>();
		hashVertex = new Hashtable<>();
		hashKeys = new Hashtable<>();
		hashIndex = new Hashtable<>();
	}
	@Override
	public void addVertex(V element, K key) throws VertexExistException {
		if(hashVertex.get(key)!=null)
		{
			throw new VertexExistException("Vertex already exist", element);
		}
		else
		{
			Vertex<K, V, A> v= new Vertex<>(element, key);
			hashVertex.put(v.getId(), v);
			hashKeys.put(key, matrix.addElement());
			hashIndex.put(hashKeys.get(key), key);
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
		if(matrix.get(hashKeys.get(IdVertexSource), hashKeys.get(IdVertexEnd))!=null)
		{
			throw new EdgeExistException("Edge already exist", IdVertexEnd, IdVertexEnd);
		}
		matrix.add(infoEdge, hashKeys.get(IdVertexSource), hashKeys.get(IdVertexEnd));
		if(!isDirected)
		{
			matrix.add(infoEdge, hashKeys.get(IdVertexEnd), hashKeys.get(IdVertexSource));
		}
		
	}

	@Override
	public void clear() {
		hashVertex.clear();
		hashKeys.clear();
		matrix = new DynamicMatrix<>();
		
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
		return matrix.get(hashKeys.get(VertexSource), hashKeys.get(VertexEnd))!=null;
		
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
				if(matrix.get(i, j)!=null)
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
	
	public Hashtable<K, Vertex<K, V, A>> Dijkstra(K k)
	{
		ArrayList<ArrayList<Pair<Integer, Integer>>> list = new ArrayList<>();
		for (int i = 0; i < matrix.getDimension(); i++) {
			list.add(new ArrayList<>());
		}
		for (int i = 0; i < matrix.getDimension(); i++) {
			for (int j = 0; j < matrix.getDimension(); j++) {
				if(matrix.get(i, j)!=null)
				{
					list.get(i).add(new Pair<Integer, Integer>(matrix.get(i, j).getWeight(), j));
				}
			}
		}
		lessDistance = new int[matrix.getDimension()];
		for (int i = 0; i < lessDistance.length; i++) {
			lessDistance[i] = (int)Math.pow(10, 9);
		}
		lessDistance[hashKeys.get(k)] = 0;
		PriorityQueue<Pair<Integer, K>> cola = new PriorityQueue<>();
		cola.add(new Pair<Integer, K>(0, k));
		while(!cola.isEmpty())
		{
			Pair<Integer, K> pair = cola.poll();
			int x = hashKeys.get(pair.getValue());
			if(!hashVertex.get(pair.getValue()).isChecked())
			{
				hashVertex.get(pair.getValue()).check();
				for (int i = 0; i < list.get(x).size(); i++) {
					int e = list.get(x).get(i).getValue();
					int w = list.get(x).get(i).getKey();
					if(lessDistance[x] + w < lessDistance[e])
					{
						
						hashVertex.get(hashIndex.get(e)).setPred(hashVertex.get(hashIndex.get(x)));
						lessDistance[e] = lessDistance[x] + w;
						cola.add(new Pair<Integer, K>(lessDistance[e], hashIndex.get(e)));
					}
				}
			}
		}
		uncheckAll();
		return hashVertex;
	}
	
	public ArrayList<Edge<K, V, A>> kruskalMST(){
		ArrayList<Edge<K, V, A>> graphEdges = new ArrayList<>();
		if(isDirected)
		{
			for (int i = 0; i < matrix.getDimension(); i++) {
				for (int j = 0; j < matrix.getDimension(); j++) {
					if(matrix.get(i, j)!=null)
					{
						
						graphEdges.add(new Edge<K, V, A>(hashVertex.get(hashIndex.get(i)), hashVertex.get(hashIndex.get(j)), matrix.get(i, j)));
					}
				}
			}
		}
		else
		{
			for (int i = 0; i < matrix.getDimension(); i++) {
				
				for (int j = i; j < matrix.getDimension(); j++) {
					
					if(matrix.get(i, j)!=null)
					{
						
						graphEdges.add(new Edge<K, V, A>(hashVertex.get(hashIndex.get(i)), hashVertex.get(hashIndex.get(j)), matrix.get(i, j)));
					}
				}
			}
		}
		Collections.sort(graphEdges);
		ArrayList<Edge<K, V, A>> mstEdges = new ArrayList<>();
		DisjointSet nodeSet = new DisjointSet(matrix.getDimension()+1);
		for(int i=0; i<graphEdges.size() && mstEdges.size()<(matrix.getDimension()-1); i++){
			Edge<K, V, A> currentEdge = graphEdges.get(i);
			int root1 = nodeSet.find(hashKeys.get(currentEdge.getSourceVertex().getId()));
			int root2 = nodeSet.find(hashKeys.get(currentEdge.getEndVertex().getId()));
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

	public DynamicMatrix<K, V, A> getMatrix()
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
	
	private void uncheckAll( )
	{
		for( Vertex<K, V, A> vertex : hashVertex.values( ) )
		{
			vertex.uncheck( );
		}
	}
}
