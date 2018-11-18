package graphs.util;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import graphs.util.Exceptions.EdgeDoesnotExistException;
import graphs.util.Exceptions.EdgeExistException;

/**
* Represents a Vertex of the graph
* @param <K> identifier type of the vertex
* @param <V> type of the vertex element
* @param <A> type of the edge element.
*/
public class Vertex<K, V extends IVertex<K> , A extends IEdge>{
	
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	* Vertex's element
	*/
	private V infoVertex;

	/**
	* Vertex's key
	*/
	private K id;
	

	/**
	* List of edges that are successors
	*/
	private ArrayList<Edge<K, V, A>> successors;

	/**
	* Centinel
	*/
	private boolean checked;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	* Vertex's Constructor
	* @param pInfoVertex Vertex element
	*/
	public Vertex( V pInfoVertex, K key)
	{
		id = key;
		infoVertex = pInfoVertex;
		successors = new ArrayList<Edge<K, V, A>>( );
		checked = false;
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	
	/**
	* Determine the vertex element
	* @return the vertex element
	*/
	public V getInfoVertex( )
	{
		return infoVertex;
	}

	/**
	* Determine the vetex successors
	* @return List of vertex successors
	*/
	public ArrayList<Edge<K, V, A>> getSucesores( )
	{
		return successors;
	}

	

	public K getId() {
		return id;
	}

	/**
	* Determine the edge (if exist) to another vertex. Null if doesn't exist
	* @param idDestino Identifier of the end Vertex
	* @return Edge of this vertex to end vertex, null otherwise
	*/
	public Edge<K, V, A> getEdge( K idEnd )
	{
		for( int i = 0; i < successors.size( ); i++ )
		{
			Edge<K, V, A> e = successors.get( i );
			if( idEnd.equals( e.getEndVertex( ).getId( ) ) )
			{
			return e;
			}
		}
		return null;
	}

	/**
	* Determine the value of the centinel
	* @return determine the value of the centinel
	*/
	public boolean isChecked( )
	{
		return checked;
	}

	/**
	* True to the check centinel
	*/
	public void check( )
	{
		checked = true;
	}

	/**
	* False to the check centinel
	*/
	public void uncheck( )
	{
		checked = false;
	}

	/**
	* Delete a vertex edge
	* @param idEnd Identifier of the end vertex of the edge that want to be deleted
	* @throws EdgeDoesnotExistsException Generates if the edge does not exists
	*/
	public void deleteEdge( K idEnd ) throws EdgeDoesnotExistException
	{
		Edge<K, V, A> edge = getEdge( idEnd );
		if( edge == null )
		{
			throw new EdgeDoesnotExistException( "The edge doesn't exist", getId( ), idEnd );
		}
		successors.remove( edge );
	}


	/**
	* Add a new edge to the vertex
	* @param Edge to add
	* @throws EdgeExistsException Generates if the edge already exists
	*/
	public void addEdge( Edge<K, V, A> edge ) throws EdgeExistException
	{
		K idEnd = edge.getEndVertex( ).getId( );
		if( isSuccessor( idEnd ) )
		{
			 new EdgeExistException( "The edge is already created", getId( ), idEnd );
		}
			successors.add( edge );
	}

	

	/**
	* Delete all the edges
	*/
	public void deleteEdges( )
	{
		successors.clear( );
	}

	/**
	* Determine if the edge is a successor of the vertex
	* @param idEnd Identifier of the end vertex
	* @return True if is a successor, otherwise false
	*/
	public boolean isSuccessor( K idEnd )
	{
		return getEdge( idEnd ) != null;
	}

	/**
	* Determine the number of successors of this vertex
	* @return number of successor of this vertex
	*/
	public int getNumberSuccessors( )
	{
		return successors.size( );
	}


	
	public void dfs( ArrayList<V> l )
	{
		check();
		
		for( Edge<K, V, A> arco : successors )
		{
			Vertex<K, V, A> vert = arco.getEndVertex();
			if( !vert.isChecked() )
			{
				vert.dfs( l );
			}
		}
		
		l.add(this.getInfoVertex());
	}
	

	
	
}
