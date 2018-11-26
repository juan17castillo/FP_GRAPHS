package graphs.util;

	/**
	* Represents an edge of the graph
	* @param <K> identifier type of the vertex key
	* @param <V> identifier type of the vertex element
	* @param <A> identifier type of the edge element.
	*/
public class Edge<K, V extends IVertex<K> , A extends IEdge > implements Comparable<Edge<K, V, A>>{
	// -----------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------
	
	/**
	* Source vertex
	*/
	private Vertex<K, V, A> source;
	
	/**
	* End vertex of the edge
	*/
	private Vertex<K, V, A> end;
	
	/**
	* Element of the edge
	*/
	private A infoEdge;
	
	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------
	
	/**
	* Constructor method of the edge
	* @param pSource Source vertex
	* @param pEnd End vertex
	* @param pInfoEnd Edge element
	*/
	public Edge( Vertex<K, V, A> pSource, Vertex<K, V, A> pEnd, A pInfoEdge )
	{
		source = pSource;
		end = pEnd;
		infoEdge = pInfoEdge;
	}
	
	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------
	
	/**
	* Returns the edge element
	* @return Edge element
	*/
	public A getInfoEdge( )
	{
		return infoEdge;
	}
	
	/**
	* Return the end vertex
	* @return end vertex of the edge
	*/
	public Vertex<K, V, A> getEndVertex( )
	{
		return end;
	}
	
	/**
	* Return the source vertex
	* @return source vertex of the edge
	*/
	public Vertex<K, V, A> getSourceVertex( )
	{
		return source;
	}

	public int getWeight() {
		return infoEdge.getWeight();
	}

	
	

	@Override
	public String toString()
	{
		return source.getId()+"->"+end.getId()+": "+getWeight();
	}

	@Override
	public int compareTo(Edge<K, V, A> arg0) {
		return infoEdge.compareTo(arg0.getInfoEdge());
	}
}






//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
//.
