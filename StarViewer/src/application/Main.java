package application;
	
import java.util.ArrayList;
import java.util.Objects;

import com.github.hteph.Generators.StarGenerator;
import com.github.hteph.Generators.StarSystemGenerator;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		ArrayList<ArrayList> testSystem= new ArrayList<>();
		
		StellarObject testStar = StarGenerator.Generator();
		ArrayList<StellarObject> Test = StarSystemGenerator.Generator((Star) testStar);
		testSystem.add(Test);
		StellarObject testStar2 = StarGenerator.Generator();
		ArrayList<StellarObject> Test2 = StarSystemGenerator.Generator((Star) testStar2);
		testSystem.add(Test2);
		
		try {
			Group root = new Group();
			Scene scene = new Scene(root,800,1000);
			primaryStage.setTitle("StarView");
			BorderPane borderPane = new BorderPane();
			borderPane.prefHeightProperty().bind(scene.heightProperty());
	        borderPane.prefWidthProperty().bind(scene.widthProperty());	        	        
			
			TabPane tabPane = new TabPane();
			tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			borderPane.setCenter(tabPane);

			
			for (int i = 0; i < testSystem.size(); i++) {
	            Tab tab = new Tab();
	            
	            String text = testSystem.get(i).get(0).toString();
	            
	            tab.setText(text);            
	            VBox starBox = new VBox();
	            // Setting the page text
	            
	            
	            
	            starBox.getChildren().add(new Label(text ));
	            starBox.setAlignment(Pos.CENTER);            
	            
				TabPane orbitTabs = new TabPane();
				orbitTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
				orbitTabs.setSide(Side.RIGHT);
	            orbitTabs.prefHeightProperty().bind(scene.heightProperty());
	            orbitTabs.prefWidthProperty().bind(scene.widthProperty());            
	            
	            for (int n = 0; n < testSystem.get(i).size(); n++) {
		            Tab orbit = new Tab();
		            
		            
		            String text2 = ((StellarObject) testSystem.get(i).get(n)).getName();
		            
		            orbit.setText(text2);
		            VBox orbitbox = new VBox();
		            //orbitbox.getChildren().add(new Label(text2));
		            
		            TabPane moonTabs = new TabPane();
					moonTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		            moonTabs.setSide(Side.BOTTOM);
		            moonTabs.prefHeightProperty().bind(scene.heightProperty());
		            moonTabs.prefWidthProperty().bind(scene.widthProperty());
		            for (int j = 0; j < 1; j++) {
		            	Tab moon = new Tab();
		            	String text3 =  testSystem.get(i).get(n).toString();
		            	String objectName = ((StellarObject) testSystem.get(i).get(n)).getName();
			            moon.setText(objectName);
			            VBox moonbox = new VBox();
			            moonbox.getChildren().add(new Label(text3));
			            moon.setContent(moonbox);
			            moonTabs.getTabs().add(moon);
		            }
		            orbitbox.getChildren().add(moonTabs);	            
		            orbit.setContent(orbitbox);
		            orbitTabs.getTabs().add(orbit);
	            }
	            starBox.getChildren().add(orbitTabs);	            
	            tab.setContent(starBox);
	            tabPane.getTabs().add(tab);	            
	        }
			
			
	        root.getChildren().add(borderPane);	        
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
