package graphs.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

/**
* Represents the graph
* @param <K> identifier type of the vertex
* @param <V> type of the vertex element
* @param <A> type of the edge element.
*/
public class GraphAdjacencyList<K, V, A> implements IGraph<K, V, A>  {
	
	private boolean isDirected;
	
	private HashMap<K, Vertex<K, V, A>> vertexList;

	public GraphAdjacencyList(boolean isDirected) {
		vertexList = new HashMap<K, Vertex<K, V, A>>( );
		this.isDirected = isDirected;
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
		}
	}
	
	
	public void addEdge( K idVertexSource, K idVertexEnd, A infoEdge)throws VertexDoesnotExistException, EdgeExistException
	{
		Vertex<K, V, A> vertexSource = getVertex( idVertexSource );
		Vertex<K, V, A> vertexEnd = getVertex( idVertexEnd );		
		
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
}
