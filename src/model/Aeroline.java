package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


import graphs.util.GraphAdjacencyList;
import graphs.util.GraphAdjacencyMatrix;
import graphs.util.Exceptions.EdgeExistException;
import graphs.util.Exceptions.VertexDoesnotExistException;
import graphs.util.Exceptions.VertexExistException;

public class Aeroline {
	
	private HashMap<Integer, City> cities;
	private HashMap<String, Flight > flights;
	private GraphAdjacencyList<Integer, City, Flight> tourList;
	private GraphAdjacencyMatrix<Integer, City, Flight> tourMatrix;
	
	public Aeroline() {
		cities = new HashMap<Integer, City>();
		flights = new HashMap<String, Flight>();
		tourList = new GraphAdjacencyList<>(false);
		tourMatrix = new GraphAdjacencyMatrix<>(false);
		loadTourData();
	}
	
	
	public void loadTourData() {
		
		File file = new File ("data/cities.txt");		
		try {
			FileReader reader = new FileReader(file); 
			BufferedReader br = new BufferedReader(reader); 
			String line = "";
			String[] s;
			while((line = br.readLine()) != null){
				s = line.split(",");
				
				City c = new City(s[0], Integer.parseInt(s[1])
						, Double.parseDouble(s[2]), Double.parseDouble(s[3]));
				
				cities.put(c.getId(), c);
				tourList.addVertex(c, Integer.parseInt(s[1]));
				tourMatrix.addVertex(c, Integer.parseInt(s[1]));
				
				System.out.println("Agregado "+c.getName());
			}
    		
			br.close();
			
			
			file = new File ("data/edges.txt");		
			
				reader = new FileReader(file); 
				br = new BufferedReader(reader); 
				line = "";
				while((line = br.readLine()) != null){
					s = line.split(",");
					
					Flight f = new Flight(Double.parseDouble(s[3]),Double.parseDouble(s[4])
							, s[0]);
					
					flights.put(f.getName(), f);
					tourList.addEdge(Integer.parseInt(s[1]), Integer.parseInt(s[2]), 
							f);
					f.setIdFrom(Integer.parseInt(s[1])); f.setIdTo(Integer.parseInt(s[2]));
					System.out.println("Agregado "+f.getName());

				}
	    		
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VertexExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VertexDoesnotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EdgeExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	public void setCostMode() {
		for (Flight f : flights.values()) {
			f.setCostMode(true);
			f.setDistanceMode(false);
		}
	}
	
	public void setDistanceMode() {
		for (Flight f : flights.values()) {
			f.setCostMode(false);
			f.setDistanceMode(true);
		}
	}


	public HashMap<Integer, City> getCities() {
		return cities;
	}


	public HashMap<String, Flight> getFlights() {
		return flights;
	}


	public GraphAdjacencyList<Integer, City, Flight> getTourList() {
		return tourList;
	}


	public GraphAdjacencyMatrix<Integer, City, Flight> getTourMatrix() {
		return tourMatrix;
	}
	
	
}
