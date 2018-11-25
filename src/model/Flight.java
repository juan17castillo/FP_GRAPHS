package model;

import graphs.util.Edge;
import graphs.util.IEdge;
import graphs.util.IVertex;

public class Flight implements IEdge{

	private double distance;
	private double cost;
	private String name;
	private boolean costMode;
	private boolean distanceMode;
	private int idFrom;
	private int idTo;
	
	
	public Flight(double distance, double cost, String name) {
		super();
		this.distance = distance;
		this.cost = cost;
		this.name = name;
	}

	public int getWeightDistance() {
		return (int) distance;
	}

	public int getWeightCost() {
		return (int) cost;
	}

	public int compare(Flight o) {

		if(costMode) {
			
			if(cost>o.cost) {
				return 1;
			}
			else if(cost<o.cost) {
				return -1;
			}
			else {
				return 0;
			}
		}else {
			if(distance>o.distance) {
				return 1;
			}
			else if(distance<o.distance) {
				return -1;
			}
			else {
				return 0;
			}
			
		}
		
	}

	public void setCostMode(boolean costMode) {
		this.costMode = costMode;
	}

	public void setDistanceMode(boolean distanceMode) {
		this.distanceMode = distanceMode;
	}

	public String getName() {
		return name;
	}

	public int getIdFrom() {
		return idFrom;
	}

	public void setIdFrom(int idFrom) {
		this.idFrom = idFrom;
	}

	public int getIdTo() {
		return idTo;
	}

	public void setIdTo(int idTo) {
		this.idTo = idTo;
	}

	@Override
	public int compareTo(Object o) {
		return compare((Flight)o);
	}

	@Override
	public int compareTo(IEdge a) {
		return compare((Flight) a);
	}
	
	public int getWeight(){
		
		if(costMode) {
			return (int) cost;
		}else {
			return (int) distance;
		}
	}
	

	
}
