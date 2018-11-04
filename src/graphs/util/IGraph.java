package graphs.util;

import java.util.List;

import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

public interface IGraph<K, V, A> {
	
	/**
	 *  Add a vertex to the graph
	 * @param v the vertex that will be added.
	 * @throws VertexExistException 
	 */
	public void addVertex( V element, K key ) throws VertexExistException;
	
	/**
	 *  Add a edge to the graph
	 * @param v1 the source vertex
	 * @param v2 the destination vertex
	 * @param e the edge that will be added.
	 */
	public void addEdge( K idVertexSource, K idVertexEnd, A infoEdge)throws VertexDoesnotExistException, EdgeExistException;
	
	
	/**
	 * Remove all vertices (and thus, edges) of the graph.
	 */
	public void clear();
	
	/**
	 * Test for vertex membership.
	 * @param v a vertex that will verify
	 * @return if the graph contains that vertex
	 */
	public boolean containsVertex(K idVertex);
	
	/**
	 * Test for edge membership.
	 * @param e a edge that will verify
	 * @return true if the graph contains that edge, otherwise false
	 */
	public boolean containsEdge( K idVertexSource, K idVertexEnd ) throws VertexDoesnotExistException;
	
	/**
	 * Determine if graph is empty.
	 * @return true if the graph is empty, otherwise false
	 */
	public boolean isEmpty();
	
	/**
	 * Determine out degree of vertex.
	 * @param e a edge that will verify
	 * @return a number with the degree of that vertex. 
	 */
	

	
	
	
}
