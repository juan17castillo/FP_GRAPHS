package test;

import graphs.util.IEdge;

public class Edg implements IEdge{

	private int weight;
	public Edg(int cost)
	{
		weight = cost;
	}
	
	@Override
	public int compareTo(Object o) {
		Edg g = (Edg) o;
		return weight-g.getWeight();
	}
	@Override
	public int compareTo(IEdge a) {
		Edg g = (Edg) a;
		return weight-g.getWeight();
	}

	@Override
	public int getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}

}
