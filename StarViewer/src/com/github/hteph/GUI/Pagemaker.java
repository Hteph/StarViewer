package com.github.hteph.GUI;

import java.awt.Label;

import com.github.hteph.Generators.StarGenerator;
import com.github.hteph.Generators.StarSystemGenerator;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;

import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public final class Pagemaker {

	private Pagemaker(){
		//No instances of this class please!

	}

	//Methods------------------------------------------------
	public static VBox generator(StellarObject target) {
		VBox page =new VBox();
		if(target instanceof Star) page = StarPageGenerator((Star) target);
		if(target instanceof Planet) page = PlanetPageGenerator((Planet) target);
		return page;
	}
	
//Inner Methods -----------------------------------------------
	
	private static VBox PlanetPageGenerator(Planet planet) {
		
		VBox infoPage = new VBox();

		Text titelInfo = new Text(planet.getName() + " ("+planet.getClassificationName() + ")" );
		
		titelInfo.setFont(Font.font ("Verdana", 20));
		HBox topBox = new HBox();
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(titelInfo);
		topBox.setPadding(new Insets(15, 12, 15, 12));

		infoPage.getChildren().add(topBox);
		
		TextArea description = new TextArea(planet.getDescription());
		description.setPrefColumnCount(60);
		description.setWrapText(true);
		
		Sphere planetSphere = new Sphere(100);
		planetSphere.setTranslateX(200); 
		planetSphere.setTranslateY(150);

		final PhongMaterial planetColour = new PhongMaterial();

		findStarColour(planet, planetColour);
		
		planetSphere.setMaterial(planetColour);

		Image normalMap = new Image("/normalmap.png");

		planetColour.setBumpMap(normalMap);

		Color ambiColor = new Color(1.0, 1.0, 1.0, 0.1);

		AmbientLight light = new AmbientLight();
		light.setColor(ambiColor);

		PointLight light2 = new PointLight();
		light2.setColor(Color.WHITE);
		
		
		return null;
	}

	

	private static void findStarColour(Planet planet, PhongMaterial planetColour) {
		planetColour.setDiffuseColor(Color.BROWN);
	
}

	private static VBox StarPageGenerator(Star star) {

		VBox infoPage = new VBox();

		Text titelInfo = new Text(star.getName() + " ("+star.getClassification() + ")" );

		titelInfo.setFont(Font.font ("Verdana", 20));

		HBox topBox = new HBox();
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(titelInfo);
		topBox.setPadding(new Insets(15, 12, 15, 12));

		infoPage.getChildren().add(topBox);


		TextArea description = new TextArea(star.getDescription());
		description.setPrefColumnCount(60);
		description.setWrapText(true);

		Sphere starSphere = new Sphere(100);
		starSphere.setTranslateX(200); 
		starSphere.setTranslateY(150);

		final PhongMaterial starColour = new PhongMaterial();

		findStarColour(star, starColour);

		starSphere.setMaterial(starColour);

		Image normalMap = new Image("/normalmap.png");

		starColour.setBumpMap(normalMap);

		Color ambiColor = new Color(1.0, 1.0, 1.0, 0.1);

		AmbientLight light = new AmbientLight();
		light.setColor(ambiColor);

		PointLight light2 = new PointLight();
		light2.setColor(Color.WHITE);



		BackgroundImage myBI= new BackgroundImage(new Image("/Starfield.png",833,833,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);

		Group displayStar = new Group(starSphere, light, light2);

		HBox test = new HBox(displayStar);
		test.setBackground(new Background(myBI));

		HBox intro = new HBox(description, test);
		intro.setPadding(new Insets(15, 12, 15, 12));
		intro.setSpacing(10);

		infoPage.getChildren().add(intro);

		TitledPane firstTitledPane = new TitledPane();
		firstTitledPane.setText("Facts");

		ListView<String> factList = new ListView<>();

		ObservableList<String> starFacts = FXCollections.observableArrayList("Lumosity [Sol-eqv]: "+String.valueOf(star.getLumosity()),"Mass [Sol-eqv]: "
				+String.valueOf(star.getMass()),"Diameter [Sol-eqv]: "+String.valueOf(star.getRadius()),"Age [Gy]: "
						+String.valueOf(star.getAge()));

		factList.setItems(starFacts);
		factList.maxWidth(50);
		factList.setPrefHeight(100);

		VBox factBox = new VBox();

		factBox.getChildren().add(factList);		
		factBox.setPadding(new Insets(15, 12, 15, 12));

		firstTitledPane.setContent(factBox);

		TitledPane secondTitledPane = new TitledPane();
		secondTitledPane.setText("System Objects");

		TableView<StellarObject> table = new TableView<StellarObject>();
		ObservableList<StellarObject> systemOrbitsObjects = FXCollections.observableArrayList();

		for(int i=0; i<star.getOrbitalObjects().size();i++){
			systemOrbitsObjects.add(star.getOrbitalObjects().get(i));
		}
		table.itemsProperty().set(systemOrbitsObjects);
		TableColumn<StellarObject, Double> orbit = new TableColumn<>("Orbit Distance [Au]");
		TableColumn<StellarObject, String> name = new TableColumn<>("Object Name");
		TableColumn<StellarObject, String> type = new TableColumn<>("Object Type");
		TableColumn<StellarObject, String> life = new TableColumn<>("Native Life");

		orbit.setCellValueFactory(new PropertyValueFactory<StellarObject, Double>("orbitDistance"));
		name.setCellValueFactory(new PropertyValueFactory<StellarObject, String>("name"));
		type.setCellValueFactory(new PropertyValueFactory<StellarObject, String>("classificationName"));
		life.setCellValueFactory(new PropertyValueFactory<StellarObject, String>("lifeType"));

		table.getColumns().addAll(orbit, name, type, life);

		secondTitledPane.setContent(table);	

		Accordion furtherFacts= new Accordion();      
		furtherFacts.getPanes().addAll(firstTitledPane, secondTitledPane);

		infoPage.getChildren().add(furtherFacts);

		return infoPage;
	}

	private static void findStarColour(Star star, PhongMaterial starColour) {

		switch (star.getClassification().charAt(0)) {
		case 'Y':
			starColour.setDiffuseColor(Color.BROWN);

			break;
		case 'T':
			starColour.setDiffuseColor(Color.DARKRED);
			break;
		case 'M':
			starColour.setDiffuseColor(Color.CRIMSON);
			break;
		case 'K':
			starColour.setDiffuseColor(Color.ORANGE);

			break;
		case 'G':
			starColour.setDiffuseColor(Color.YELLOW);
			break;
		case 'F':
			starColour.setDiffuseColor(Color.LIGHTYELLOW);
			break;
		case 'A':
			starColour.setDiffuseColor(Color.BLUE);
			break;
		case 'B':
			starColour.setDiffuseColor(Color.LIGHTBLUE);	       
			break;
		case 'O':
			starColour.setDiffuseColor(Color.WHITE);
			break;
		default:
			starColour.setDiffuseColor(Color.DARKORCHID);
			break;
		}
	}
}
