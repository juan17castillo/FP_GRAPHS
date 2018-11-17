package model;

import java.util.HashMap;

import graphs.util.GraphAdjacencyList;
import graphs.util.GraphAdjacencyMatrix;

public class Aeroline {
	
	private HashMap<Integer, City> cities;
	private HashMap<String, Flight > flights;
	private GraphAdjacencyList<Integer, City, Flight> tourList;
	private GraphAdjacencyMatrix<City> tourMatrix;
	
}
