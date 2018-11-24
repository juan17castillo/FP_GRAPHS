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

import graphs.util.Vertex;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Aeroline;
import model.City;
import model.Flight;


public class MainViewController implements Initializable, MapComponentInitializedListener{

	
	private Aeroline a;
	
    @FXML
    private GoogleMapView mapView;
    
    private GoogleMap map;
    
	private ObservableList<String> data;
    
    private ArrayList<Marker> markers;

    @FXML
    private AnchorPane anchorInit;

    @FXML
    private AnchorPane anchorApp;

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
    private TableView<String> tableCities;

    @FXML
    private TableColumn<String, String> columnCities;

    @FXML
    private Button btnAddCityTable;

    @FXML
    private Button btnRemoveCityOnTable;

    @FXML
    private Button btnGenerateTour;
    
    @FXML
    private ComboBox<String> comboFrom;

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
     		        "Shangai",
     		        "Moscu",
     		        "Amsterdan",
     		        "Cairo",
     		        "Otawa"
     		    );
     	comboFrom.setItems(options);
     	

     	ObservableList<String> data = FXCollections.observableArrayList(
     			 "Cali",
  		        "Barcelona",
  		        "New York",
  		        "Paris",
  		        "London",
  		        "Shangai",
  		        "Moscu",
  		        "Amsterdan",
  		        "Cairo",
  		        "Otawa");
     	columnCities.setCellValueFactory(new PropertyValueFactory<String, String>("City"));
     	columnCities.setCellFactory(ComboBoxTableCell.forTableColumn(data));
     	tableCities.getItems().add("Cali");
     	
     	
    }

    @FXML
    void generateRamdonToyr(ActionEvent event) {

    }

    @FXML
    void generateTour(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void removeCityOnTable(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        markers = new ArrayList<Marker>();
        mapView.setKey("AIzaSyBRKd40k8kiVyiLs5EVjl_yUYFmIt-2CHI");

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
        	System.out.println("Aristica "+from.getName()+" a "+to.getName());
        	addLine(new LatLong(from.getLatitude(),from.getLongitude()), 
        			new LatLong(to.getLatitude(), to.getLongitude()));
		}
        
        addLine(new LatLong(3.42158, -76.5205), (new LatLong(41.3922500, 2.1648800)));
   
    }

    
}
