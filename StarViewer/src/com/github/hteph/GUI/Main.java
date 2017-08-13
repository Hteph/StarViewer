package com.github.hteph.GUI;
	
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


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
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

			
			for (int i = 0; i < 5; i++) {
	            Tab tab = new Tab();
	            tab.setText("Tab" + i);            
	            HBox hbox = new HBox();
	            hbox.getChildren().add(new Label("Tab" + i));
	            hbox.setAlignment(Pos.CENTER);            
	            
				TabPane orbitTabs = new TabPane();
				orbitTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
				orbitTabs.setSide(Side.RIGHT);
	            orbitTabs.prefHeightProperty().bind(scene.heightProperty());
	            orbitTabs.prefWidthProperty().bind(scene.widthProperty());            
	            
	            for (int n = 0; n < 5; n++) {
		            Tab orbit = new Tab();
		            orbit.setText("Orbit" + n);
		            HBox orbitbox = new HBox();
		            orbitbox.getChildren().add(new Label("Orbit" + n));
		            
		            TabPane moonTabs = new TabPane();
					moonTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		            moonTabs.setSide(Side.BOTTOM);
		            moonTabs.prefHeightProperty().bind(scene.heightProperty());
		            moonTabs.prefWidthProperty().bind(scene.widthProperty());
		            for (int j = 0; j < 5; j++) {
		            	Tab moon = new Tab();
			            moon.setText("Moon" + j);
			            HBox moonbox = new HBox();
			            moonbox.getChildren().add(new Label("Moon" + n));
			            moon.setContent(moonbox);
			            moonTabs.getTabs().add(moon);
		            }
		            orbitbox.getChildren().add(moonTabs);	            
		            orbit.setContent(orbitbox);
		            orbitTabs.getTabs().add(orbit);
	            }
	            hbox.getChildren().add(orbitTabs);	            
	            tab.setContent(hbox);
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
