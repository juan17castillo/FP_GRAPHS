package test;

import graphs.util.IEdge;

public class Edg implements IEdge{

	private int weight;
	public Edg(int cost)
	{
		weight = cost;
	}
	@Override
	public int getWeightDistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWeightCost() {
		// TODO Auto-generated method stub
		return weight;
	}

}
