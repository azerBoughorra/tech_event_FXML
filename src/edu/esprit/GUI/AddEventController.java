/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.GUI;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.elevation.ElevationResult;
import com.lynden.gmapsfx.service.elevation.ElevationStatus;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEvent;
import netscape.javascript.JSObject;

/**
 * FXML Controller class
 *
 * @author azer
 */
public class AddEventController implements Initializable, MapComponentInitializedListener {

    @FXML
    private AnchorPane mapCont;
    GoogleMapView mapComponent;
    protected GoogleMap map;
    private MarkerOptions markerOptions2;
    private Marker myMarker2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mapComponent = new GoogleMapView();
        mapComponent.addMapInializedListener(this);
        mapComponent.setDisableDoubleClick(false);
        mapComponent.getWebview().getEngine().setOnAlert((WebEvent<String> event) -> {
            System.out.println("Event event: " + event);
        });
        mapComponent.setMaxWidth(200);
        mapComponent.setMaxHeight(200);

        this.mapCont.getChildren().add(mapComponent);
    }

    private void OnClickMap(LatLong cord) {
        System.out.println(cord);
        markerOptions2=new MarkerOptions();
        markerOptions2.position(cord);
        markerOptions2.title("qsdqsd");
        myMarker2=new Marker(markerOptions2);
        
        myMarker2=new Marker(markerOptions2);
        this.map.addMarker(myMarker2);
        myMarker2.setVisible(true);
    }

    @Override
    public void mapInitialized() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("Calling showDirections from Java");
                Platform.runLater(() -> mapComponent.getMap().hideDirectionsPane());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        t.start();
        //Once the map has been loaded by the Webview, initialize the map details.
        LatLong center = new LatLong(47.606189, -122.335842);
        mapComponent.addMapReadyListener(() -> {
            // This call will fail unless the map is completely ready.
            checkCenter(center);
        });

        MapOptions options = new MapOptions();
        options.center(center)
                .mapMarker(true)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);

        map = mapComponent.createMap(options);

        map.addUIEventHandler(UIEventType.click, e -> {
            OnClickMap(new LatLong((JSObject)e.getMember("latLng")));
        });
        map.setHeading(123.2);
//        System.out.println("Heading is: " + map.getHeading() );

        //map.showDirectionPane();
    }

    private void hideMarker() {
//		System.out.println("deleteMarker");

        boolean visible = myMarker2.getVisible();

        //System.out.println("Marker was visible? " + visible);
        myMarker2.setVisible(!visible);

//				markerOptions2.visible(Boolean.FALSE);
//				myMarker2.setOptions(markerOptions2);
//		System.out.println("deleteMarker - made invisible?");
    }

    private void deleteMarker() {
        //System.out.println("Marker was removed?");
        map.removeMarker(myMarker2);
    }

    private void checkCenter(LatLong center) {
//        System.out.println("Testing fromLatLngToPoint using: " + center);
//        Point2D p = map.fromLatLngToPoint(center);
//        System.out.println("Testing fromLatLngToPoint result: " + p);
//        System.out.println("Testing fromLatLngToPoint expected: " + mapComponent.getWidth()/2 + ", " + mapComponent.getHeight()/2);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }

    public void elevationsReceived(ElevationResult[] results, ElevationStatus status) {
        if (status.equals(ElevationStatus.OK)) {
            for (ElevationResult e : results) {
                System.out.println(" Elevation on " + e.getLocation().toString() + " is " + e.getElevation());
            }
        }
    }

    public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
        if (status.equals(GeocoderStatus.OK)) {
            for (GeocodingResult e : results) {
                System.out.println(e.getVariableName());
                System.out.println("GEOCODE: " + e.getFormattedAddress() + "\n" + e.toString());
            }

        }

    }

    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        if (status.equals(DirectionStatus.OK)) {

            System.out.println("OK");

            DirectionsResult e = results;
            GeocodingService gs = new GeocodingService();

            System.out.println("SIZE ROUTES: " + e.getRoutes().size() + "\n" + "ORIGIN: " + e.getRoutes().get(0).getLegs().get(0).getStartLocation());
            //gs.reverseGeocode(e.getRoutes().get(0).getLegs().get(0).getStartLocation().getLatitude(), e.getRoutes().get(0).getLegs().get(0).getStartLocation().getLongitude(), this);
            System.out.println("LEGS SIZE: " + e.getRoutes().get(0).getLegs().size());
            System.out.println("WAYPOINTS " + e.getGeocodedWaypoints().size());
            /*double d = 0;
             for(DirectionsLeg g : e.getRoutes().get(0).getLegs()){
             d += g.getDistance().getValue();
             System.out.println("DISTANCE " + g.getDistance().getValue());
             }*/
            try {
                System.out.println("Distancia total = " + e.getRoutes().get(0).getLegs().get(0).getDistance().getText());
            } catch (Exception ex) {
                System.out.println("ERRO: " + ex.getMessage());
            }
            System.out.println("LEG(0)");
            System.out.println(e.getRoutes().get(0).getLegs().get(0).getSteps().size());
            /*for(DirectionsSteps ds : e.getRoutes().get(0).getLegs().get(0).getSteps()){
             System.out.println(ds.getStartLocation().toString() + " x " + ds.getEndLocation().toString());
             MarkerOptions markerOptions = new MarkerOptions();
             markerOptions.position(ds.getStartLocation())
             .title(ds.getInstructions())
             .animation(Animation.DROP)
             .visible(true);
             Marker myMarker = new Marker(markerOptions);
             map.addMarker(myMarker);
             }
             */
        }
    }

}
