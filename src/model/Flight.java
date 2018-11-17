package model;

import graphs.util.Edge;
import graphs.util.IEdge;
import graphs.util.IVertex;

public class Flight implements IEdge {

	private double distance;
	private double cost;
	private String name;
	
	public Flight(double distance, double cost, String name) {
		super();
		this.distance = distance;
		this.cost = cost;
		this.name = name;
	}

	@Override
	public int getWeightDistance() {
		return (int) distance;
	}

	@Override
	public int getWeightCost() {
		return (int) cost;
	}

	
}
