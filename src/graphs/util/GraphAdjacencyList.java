package graphs.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

/**
* Represents the graph
* @param <K> identifier type of the vertex
* @param <V> type of the vertex element
* @param <A> type of the edge element.
*/
public class GraphAdjacencyList<K, V extends IVertex<K>, A extends IEdge> implements IGraph<K, V , A>  {
	
	private boolean isDirected;
	private int[] lessDistance;
	private HashMap<K, Vertex<K, V, A>> vertexList;
	private Hashtable<K, Integer> hash;
	public GraphAdjacencyList(boolean isDirected) {
		vertexList = new HashMap<K, Vertex<K, V, A>>( );
		this.isDirected = isDirected;
		hash = new Hashtable<>();
	}
	
	
	public boolean containsVertex( K idVertex )
	{
		return vertexList.get( idVertex ) != null;
	}
	
	
	public boolean isEmpty() {
		return vertexList.size()==0;
	}
	
	public boolean containsEdge( K idVertexSource, K idVertexEnd ) throws VertexDoesnotExistException
	{
		Vertex<K, V, A> v = getVertex( idVertexSource );
		if( containsVertex( idVertexEnd) )
		return v.getEdge( idVertexEnd ) != null;
		else
		throw new VertexDoesnotExistException( "End vertex does not Exist", idVertexEnd );
	}
	
	public void addVertex( V element, K key ) throws VertexExistException
	{
		if( containsVertex( key )){
			throw new VertexExistException( "Elemento already exist", key );}
		else
		{
			Vertex<K, V, A> vertex = new Vertex<K, V, A>( element, key );
			vertexList.put( vertex.getId(), vertex );
			hash.put(vertex.getId(), vertexList.size()-1);
		}
	}
	
	
	public void addEdge( K idVertexSource, K idVertexEnd, A infoEdge)throws VertexDoesnotExistException, EdgeExistException
	{
		Vertex<K, V, A> vertexSource = getVertex( idVertexSource );
		Vertex<K, V, A> vertexEnd = getVertex( idVertexEnd );	
		if( containsEdge(idVertexSource, idVertexEnd))
		{
			throw new EdgeExistException( "That edge already Exist", idVertexSource, idVertexEnd );
		}
		
		if(isDirected) {
		Edge<K, V, A> edge = new Edge<K, V, A>( vertexSource, vertexEnd, infoEdge );
		vertexSource.addEdge( edge );}
		else {
			Edge<K, V, A> edge = new Edge<K, V, A>( vertexSource, vertexEnd, infoEdge );
			Edge<K, V, A> edge1 = new Edge<K, V, A>( vertexEnd, vertexSource, infoEdge );
			vertexSource.addEdge( edge );
			vertexEnd.addEdge(edge1);
		}
	}
	
	private Vertex<K, V, A> getVertex( K idVert ) throws VertexDoesnotExistException{
		Vertex<K, V, A> vert = vertexList.get( idVert );
		if( vert == null )
		{
			throw new VertexDoesnotExistException( "That vertex doesnot Exist", idVert );
		}
		return vert;
	}
	
	public V getVertexElement( K idVertex ) throws VertexDoesnotExistException
	{
		return getVertex( idVertex ).getInfoVertex();
	}
	
	public void clear() {
		vertexList.clear();
	}
	
	private void uncheckAll( )
	{
		for( Vertex<K, V, A> vertex : vertexList.values( ) )
		{
			vertex.uncheck( );
		}
	}
	
	public List<V> getVertexElements( )	{
		List<V> vs = new ArrayList<V>( );
		
		for( Vertex<K, V, A> v : vertexList.values( ) )
		{
		vs.add( v.getInfoVertex() );
		}
	
		return vs;
	}
	
	public Collection<Vertex<K, V, A>> getVertexList( )
	{
		return vertexList.values( );
	}
	
	public List<List<Edge<K, V, A>>> getAdjacencyList( ){
		List<List<Edge<K, V, A>>> vs = new ArrayList<List<Edge<K, V, A>>>( );
	
		for( Vertex<K, V, A> v : vertexList.values( ) )
		{
		vs.add( v.getSucesores() );
		}
	
		return vs;
	}
	
	
	public List<V> dfs(K idv) throws VertexDoesnotExistException{
		
		Vertex<K, V, A> vert = vertexList.get( idv );
		if( vert == null )
		{
			throw new VertexDoesnotExistException( "That vertex doesnot Exist", idv );
		}
		
		ArrayList<V> l= new ArrayList<V>();
		
		vert.dfs(l);
		
		return l;
	}
	
	public void Dijkstra(K k)
	{
		ArrayList<ArrayList<Pair<Integer, Integer>>> list = new ArrayList<>();
		for (int i = 0; i < vertexList.size();i++) {
			list.add(new ArrayList<>());
		}
		List<List<Edge<K, V, A>>> lis = getAdjacencyList();
		for (int i = 0; i < vertexList.size(); i++) {
			for (int j = 0; j < lis.get(i).size(); j++) {
				list.get(hash.get(lis.get(i).get(0).getSourceVertex().getId()))
				.add(new Pair<Integer, Integer>(lis.get(i).get(j).getInfoEdge().getWeight(), 
				hash.get(lis.get(i).get(j).getEndVertex().getId())));
			}
		}
		lessDistance = new int[vertexList.size()];
		for (int i = 0; i < lessDistance.length; i++) {
			lessDistance[i] = (int)Math.pow(10, 9);
		}
		boolean[] vis = new boolean[vertexList.size()];
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
	}
	
	public ArrayList<Edge<K, V, A>> kruskalMST(){
		Hashtable<Integer, Edge<K, V, A>> o = new Hashtable<>();
		List<List<Edge<K, V, A>>> l = getAdjacencyList();
		int p = 0;
		boolean[][] f = new boolean[vertexList.size()][vertexList.size()];
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f.length; j++) {
				f[i][j] = false;
			}
		}
		for (int i = 0; i < l.size(); i++) {
			for (int j = 0; j < l.get(i).size(); j++) {
				if(!f[hash.get(l.get(i).get(j).getSourceVertex().getId())][hash.get(l.get(i).get(j).getEndVertex().getId())]){
					f[hash.get(l.get(i).get(j).getSourceVertex().getId())][hash.get(l.get(i).get(j).getEndVertex().getId())] = true;
					f[hash.get(l.get(i).get(j).getEndVertex().getId())][hash.get(l.get(i).get(j).getSourceVertex().getId())] = true;
					o.put(p, l.get(i).get(j));
					p++;
				}
			}
		}
		ArrayList<Edge<K, V, A>> graphEdges = new ArrayList<>();
		for (int i = 0; i < o.size(); i++) {
			graphEdges.add(o.get(i));
		}		
		Collections.sort(graphEdges);
		ArrayList<Edge<K, V, A>> mstEdges = new ArrayList<>();
		DisjointSet nodeSet = new DisjointSet(vertexList.size()+1);
		for(int i=0; i<graphEdges.size() && mstEdges.size()<(vertexList.size()-1); i++){
			Edge<K, V, A> currentEdge = graphEdges.get(i);
			int root1 = nodeSet.find(hash.get(currentEdge.getSourceVertex().getId()));
			int root2 = nodeSet.find(hash.get(currentEdge.getEndVertex().getId()));
			if(root1 != root2){
				mstEdges.add(currentEdge);
				nodeSet.union(root1, root2);
			}
		}
		return mstEdges;
	}
	
	public int[] getLessDistance()
	{
		return lessDistance;
	}
	
	
	
}
