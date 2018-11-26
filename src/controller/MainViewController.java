package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import model.Aeroline;
import model.City;
import model.Flight;


public class MainViewController implements Initializable, MapComponentInitializedListener{

	private Aeroline a;
	

    @FXML
    private GoogleMapView mapView;
    
    private GoogleMap map;
    
    
    private ArrayList<Marker> markers;

    @FXML
    private AnchorPane anchorButtons;

    @FXML
    private Button btnCreateTour;

    @FXML
    private Button btnRamdonTour;

    @FXML
    private Button btnBack;

    @FXML
    private AnchorPane anchorOptions;

    @FXML
    private ComboBox<String> comboFrom;

    @FXML
    private Button btnGenerateTour;

    @FXML
    private ComboBox<String> comboTo;

    @FXML
    private Button buttonAddCity;

    @FXML
    private TextArea txtAreaTo;
        
    private HashMap<String, String> to;

    @FXML
    void addCityOnTable(ActionEvent event) {

    }

    @FXML
    void createTour(ActionEvent event) {
    	anchorButtons.setVisible(false);
    	anchorOptions.setVisible(true);
    	
    	 ObservableList<String> options = 
     		    FXCollections.observableArrayList(
     		        "Cali",
     		        "Barcelona",
     		        "New York",
     		        "Paris",
     		        "London",
     		        "Shanghai",
     		        "Moscu",
     		        "Amsterdam",
     		        "Cairo",
     		        "Ottawa"
     		    );
     	comboFrom.setItems(options);
     	comboTo.setItems(options);
     	comboFrom.setPromptText("Seleccione");
     	comboTo.setPromptText("Seleccione");


     	
    }
    
    @FXML
    void addCityTo(ActionEvent event) {
    	if(!comboTo.getValue().equals(comboTo.getPromptText())){
    		
    		if(!to.containsKey(comboTo.getValue())) {
    		if(txtAreaTo.getText().equals(txtAreaTo.getPromptText())) {
    			txtAreaTo.setText(comboTo.getValue());
    		}else {
    			txtAreaTo.setText(txtAreaTo.getText()+","+comboTo.getValue());}
    		
    		to.put(comboTo.getValue(), "Agregado");
    		}else {
    			
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Warning Dialog");
    			alert.setHeaderText("Look");
    			alert.setContentText("Ya ha agregado tal ciudad");

    			alert.showAndWait();
    		}
    	}
    	
    }

    @FXML
    void generateRamdonTour(ActionEvent event) {
    	
    	List<String> choices = new ArrayList<>();
    	choices.add("Tour mas corto");
    	choices.add("Tour mas economico");

    	ChoiceDialog<String> dialog = new ChoiceDialog<>("Tour mas corto", choices);
    	dialog.setTitle("Choice Dialog");
    	dialog.setHeaderText("Look");
    	dialog.setContentText("Elige que tipo de tour quieres: ");

    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    		
    		MapOptions options = new MapOptions();

            options.center(new LatLong(3.0974571279868752, -76.25453940624999))
                    .zoomControl(true)
                    .zoom(2)
                    .overviewMapControl(false)
                    .mapTypeControl(false);
            map = mapView.createMap(options);

    			String mode;
    		if(result.get().equalsIgnoreCase("Tour mas corto")) {
    			mode = "distancia en km = ";
    			a.setDistanceMode(); 
    		}else {
    			mode = " costo en usd = ";
    			a.setCostMode();
    		}
    		
    		String cs = "Se ha agregado un tour compuesto de los siguientes viajes: \n";
    		ArrayList<Flight> e = a.mst();
    		for (int i = 0; i < e.size(); i++) {
    			City from = a.getCities().get(e.get(i).getIdFrom());
            	City to = a.getCities().get(e.get(i).getIdTo());
            	addLine(new LatLong(from.getLatitude(),from.getLongitude()), 
            			new LatLong(to.getLatitude(), to.getLongitude()));
                addMarker(new LatLong(from.getLatitude(), from.getLongitude()), from.getName());
                addMarker(new LatLong(to.getLatitude(), to.getLongitude()), to.getName());
    			cs+="De "+from.getName()+" a "+to.getName()+". "+mode+e.get(i).getWeight()+"\n";

			}
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Information Dialog");
    		alert.setHeaderText(null);
    		alert.setContentText(cs);

    		alert.show();
    	}
    	
    	
    }

    @FXML
    void generateTour(ActionEvent event) {
    	if(comboTo.getValue()!=null&&comboFrom.getValue()!=null && !comboTo.getValue().equalsIgnoreCase(comboTo.getPromptText()) && 
    			!comboFrom.getValue().equalsIgnoreCase(comboFrom.getPromptText())){
    		
    		
    		List<String> choices = new ArrayList<>();
        	choices.add("Camino mas corto entre ciudades");
        	choices.add("Camino mas economico entre ciudades");

        	ChoiceDialog<String> dialog = new ChoiceDialog<>("Camino mas corto entre ciudades", choices);
        	dialog.setTitle("Choice Dialog");
        	dialog.setHeaderText("Look");
        	dialog.setContentText("Elige que tipo de camino quieres: ");

        	Optional<String> result = dialog.showAndWait();
        	if (result.isPresent() ){
        		
        		MapOptions options = new MapOptions();

                options.center(new LatLong(3.0974571279868752, -76.25453940624999))
                        .zoomControl(true)
                        .zoom(2)
                        .overviewMapControl(false)
                        .mapTypeControl(false);
                map = mapView.createMap(options);

        			String mode;
        		if(result.get().equalsIgnoreCase("Camino mas corto entre ciudades")) {
        			mode = "distancia en km = ";
        			a.setDistanceMode(); 
        		}else {
        			mode = " costo en usd = ";
        			a.setCostMode();
        		}
        		
        		String cs = "Se ha agregado un camino compuesto de los siguientes viajes: \n";
        		
       
        		ArrayList<Flight> e = a.dijkstra(a.getKey(comboFrom.getValue()), 
        				a.getKey(comboTo.getValue()));
        		
        		

        		for (int i = e.size()-1; i > -1; i--) {
        			City from = a.getCities().get(e.get(i).getIdFrom());
                	City to = a.getCities().get(e.get(i).getIdTo());
                	addLine(new LatLong(from.getLatitude(),from.getLongitude()), 
                			new LatLong(to.getLatitude(), to.getLongitude()));
                    addMarker(new LatLong(from.getLatitude(), from.getLongitude()), from.getName());
                    addMarker(new LatLong(to.getLatitude(), to.getLongitude()), to.getName());
        			cs+="De "+from.getName()+" a "+to.getName()+". "+mode+e.get(i).getWeight()+"\n";

    			}
        		
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Information Dialog");
        		alert.setHeaderText(null);
        		alert.setContentText(cs);

        		alert.show();
        	}
  
        	}else {
        		Alert alert = new Alert(AlertType.WARNING);
    	 		alert.setTitle("Warning Dialog");
    	 		alert.setHeaderText("Look");
    	 		alert.setContentText("Deber seleccionar una ciudad en cada campo");
    	 		alert.show();
        		
        	}
    		

    }

    @FXML
    void goBack(ActionEvent event) {
    	System.exit(0);
    }

   


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	
 		
        mapView.addMapInializedListener(this);
        markers = new ArrayList<Marker>();
        mapView.setKey("AIzaSyBRKd40k8kiVyiLs5EVjl_yUYFmIt-2CHI");
        to = new HashMap<>();
    	a = new Aeroline();
    	
    	
    	 
    	
    }
    
    
    public void deleteMarker(String t) {
    	boolean done = false;
    	for (int i = 0; i < markers.size() && !done; i++) {
			if(markers.get(i).getTitle().equalsIgnoreCase(t)) {
				markers.remove(i); done = true;
			}
		}
    }
    
    
    public void addMarker(LatLong m, String name) {	
        MarkerOptions markerOptions = new MarkerOptions();
    	markerOptions.position( m )
        .visible(Boolean.TRUE)
        .title(name);
    	Marker marker = new Marker( markerOptions );
    	map.addMarker(marker);
    	markers.add(marker);

    }
    
    
    public void addLine(LatLong from,  LatLong to) {
    	LatLong[] ary = new LatLong[]{from, to};
        MVCArray mvc = new MVCArray(ary);
        PolylineOptions polyOpts = new PolylineOptions()
                .path(mvc)

                

                .strokeColor("white")

                .strokeWeight(1);

        Polyline poly = new Polyline(polyOpts);
        map.addMapShape(poly);
    }
    
    
    

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        options.center(new LatLong(3.0974571279868752, -76.25453940624999))
                .zoomControl(true)
                .zoom(2)
                .overviewMapControl(false)
                .mapTypeControl(false);
        map = mapView.createMap(options);
        
    
        //Add a marker to the map
        MarkerOptions markerOptions = new MarkerOptions();

        
        for( City c : a.getCities().values( ) )
        {
            addMarker(new LatLong(c.getLatitude(), c.getLongitude()), c.getName());
        }

        
        
        for( Flight f : a.getFlights().values( ) )
		{
        	City from = a.getCities().get(f.getIdFrom());
        	City to = a.getCities().get(f.getIdTo());
        	addLine(new LatLong(from.getLatitude(),from.getLongitude()), 
        			new LatLong(to.getLatitude(), to.getLongitude()));
		}
        addLine(new LatLong(3.42158, -76.5205), (new LatLong(41.3922500, 2.1648800)));
    	
        Alert alert = new Alert(AlertType.WARNING);
 		alert.setTitle("Warning Dialog");
 		alert.setHeaderText("Look");
 		alert.setContentText("Asegurate de tener actualizado tu Java Runtime Environment"
 				+ " para que la interfaz grafica se pueda cargar");

 		alert.showAndWait();
        
        
    	List<String> choices = new ArrayList<>();
    	choices.add("Lista de adyacencia");
    	choices.add("Matriz de adyacencia");
    

    	ChoiceDialog<String> dialog = new ChoiceDialog<>("Lista de adyacencia", choices);
    	dialog.setTitle("Choice Dialog");
    	dialog.setHeaderText("Look");
    	dialog.setContentText("Elige que tipo de implementacion de grafo quieres: ");

    	
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    		if(result.get().equalsIgnoreCase("Lista de adyacencia")) {
    			a.setAdyacencyListMode(true);
    		}else {
    			a.setAdyacencyListMode(false);
    		}
    	}
    	
    }
    
    @FXML
    void backToMenu(ActionEvent event) {
    	anchorButtons.setVisible(true);
    	anchorOptions.setVisible(false);
    }

    
}
