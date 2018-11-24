package test;

public class Edge1 implements Comparable<Edge1>{
	private int vertex1;	//an edge has 2 vertices & a weight
	private int vertex2;
	private int weight;

	public Edge1(int vertex1, int vertex2, int weight){
		this.vertex1=vertex1;
		this.vertex2=vertex2;
		this.weight=weight;
	}

	public int getVertex1(){
		return vertex1;
	}

	public int getVertex2(){
		return vertex2;
	}

	public int getWeight(){
		return weight;
	}

	@Override
	public int compareTo(Edge1 otherEdge) {				//Compare based on edge weight (for sorting)
		return this.getWeight() - otherEdge.getWeight();
	}

	@Override
	public String toString() {
		return "Edge ("+getVertex1()+", "+getVertex2()+") weight="+getWeight();
	}
}

