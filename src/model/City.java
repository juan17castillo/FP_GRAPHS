package model;

import graphs.util.IVertex;

public class City implements IVertex<Integer>{

	private Integer id;
	private String name;
	private double latitude;
	private double longitude;
	
	
	public City(Integer id, String name, double latitude, double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	@Override
	public Integer getId() {
		return id;
	}

}
