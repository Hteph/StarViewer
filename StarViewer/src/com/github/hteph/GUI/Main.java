package com.github.hteph.GUI;

import java.io.IOException;
import java.util.ArrayList;

import com.github.hteph.Generators.StarGenerator;
import com.github.hteph.Generators.StarSystemGenerator;
import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.PrintThisPage;
import com.github.hteph.Utilities.saveThisLocally;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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


		ArrayList<ArrayList<StellarObject>> oldSystems = new ArrayList<>();


		try {
			oldSystems = saveThisLocally.restoreSaved();
		} catch (Exception e2) {
			System.out.println("restore object fail");
			e2.printStackTrace();
		}

		StellarObject star = null;
		star = StarGenerator.Generator();
		ArrayList<StellarObject> systemList = StarSystemGenerator.Generator((Star) star);

		oldSystems.add(systemList);

		final ArrayList<ArrayList<StellarObject>> newSystems = oldSystems;


		//Generate GUI--------------------------------------------------------------------------------------
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

			for (int i = 0; i < newSystems.size(); i++) {
				Tab tab = new Tab();

				String starSystemName = newSystems.get(i).get(0).toString();
				Star centralStar = (Star) newSystems.get(i).get(0);

				tab.setText(starSystemName);          
				VBox starBox = new VBox();

				starBox.getChildren().add(new Label(starSystemName));
				starBox.setAlignment(Pos.CENTER);            

				TabPane orbitTabs = new TabPane();
				orbitTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
				orbitTabs.setSide(Side.RIGHT);
				orbitTabs.prefHeightProperty().bind(scene.heightProperty());
				orbitTabs.prefWidthProperty().bind(scene.widthProperty());            

				for (int n = 0; n < centralStar.getOrbitalObjects().size(); n++) {
					Tab orbit = new Tab();

					// String orbitingStarName = centralStar.getOrbitalObjects().get(n).getName();
					StellarObject thingOrbitingStar = centralStar.getOrbitalObjects().get(n);

					orbit.setText(thingOrbitingStar.getName());
					VBox orbitbox = new VBox();
					//orbitbox.getChildren().add(new Label(text2));

					TabPane moonTabs = new TabPane();
					moonTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
					moonTabs.setSide(Side.BOTTOM);
					moonTabs.prefHeightProperty().bind(scene.heightProperty());
					moonTabs.prefWidthProperty().bind(scene.widthProperty());
					
					int aNumber=1;
					if(thingOrbitingStar instanceof Planet) aNumber +=((Planet) thingOrbitingStar).getLunarObjects().size();

					for (int j = 0; j < aNumber; j++) {
						
						Tab moon = new Tab();
						VBox moonbox = new VBox();
					if(j==0) {	
						String objectName = thingOrbitingStar.getName();
						moon.setText(objectName);
						moonbox.getChildren().add(new Label(objectName));
						moonbox.getChildren().add(Pagemaker.generator(thingOrbitingStar));
					}else {
						String objectName = ((Planet) thingOrbitingStar).getLunarObjects().get(j-1).getName();						
						moon.setText(objectName);
						moonbox.getChildren().add(new Label(objectName));
						
						moonbox.getChildren().add(Pagemaker.generator(((Planet) thingOrbitingStar).getLunarObjects().get(j-1)));
					}
										
						//Utility box

						HBox utility = new HBox();
						utility.setAlignment(Pos.BOTTOM_CENTER);
						utility.setPadding(new Insets(15, 12, 15, 12));
						//Utskrift
						Button printButton = new Button("Print");
						utility.getChildren().add(printButton);
						printButton.setOnAction(new EventHandler <ActionEvent>() {
							public void handle(ActionEvent event) {
								PrintThisPage.print(root);
							}

						});
						//Save
						Button saveButton = new Button("Save");
						utility.getChildren().add(saveButton);
						saveButton.setOnAction(new EventHandler <ActionEvent>() {
							public void handle(ActionEvent event) {
								saveThisLocally.saveThis(newSystems);
							}

						});

						moonbox.getChildren().add(utility);
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
