package test;

import graphs.util.IEdge;
import graphs.util.IVertex;

public class Edg implements IEdge{

	private int weight;
	public Edg(int cost)
	{
		weight = cost;
	}
	@Override
	public int compareTo(IEdge a) {
		return weight-a.getWeight();
	}

	@Override
	public int getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}

}
