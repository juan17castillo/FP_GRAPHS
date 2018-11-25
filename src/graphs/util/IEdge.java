package graphs.util;

import model.Flight;

public interface IEdge<K, V extends IVertex<K> , A extends IEdge > extends Comparable<A>{

	public int getWeight( );
	
	int compareTo(A a);

	

}
