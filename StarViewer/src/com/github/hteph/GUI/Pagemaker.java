package com.github.hteph.GUI;

import java.awt.Label;
import java.util.Arrays;

import com.github.hteph.Generators.StarGenerator;
import com.github.hteph.Generators.StarSystemGenerator;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.ObjectsOfAllSorts.TemperatureRangeBandHelpClass;

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

		Color ambiColor = new Color(1.0, 1.0, 1.0, 0.5);

		AmbientLight light = new AmbientLight();
		light.setColor(ambiColor);

		PointLight light2 = new PointLight();
		light2.setColor(Color.WHITE);

		BackgroundImage myBI= new BackgroundImage(new Image("/Starfield.png",833,833,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);

		Group displayPlanet = new Group(planetSphere, light, light2);


		HBox pictBox = new HBox(displayPlanet);

		pictBox.setBackground(new Background(myBI));

		HBox introPlanet = new HBox(description, pictBox);
		introPlanet.setPadding(new Insets(15, 12, 15, 12));
		introPlanet.setSpacing(10);

		infoPage.getChildren().add(introPlanet);
		//making the first fact pane
		TitledPane firstTitledPane = new TitledPane();
		firstTitledPane.setText("Basic Facts");

		ListView<String> factList = new ListView<>();

		ObservableList<String> starFacts = FXCollections.observableArrayList("Gravity [Earth-eqv]: "+String.valueOf(planet.getGravity()),"Atmosphere Pressure [Earth-norm]: "
				+String.valueOf(planet.getAtmoPressure()),"Surface temperature [C]: "+String.valueOf(planet.getSurfaceTemp()-274.0),"Lifeform: "
						+String.valueOf(planet.getLifeType()));

		factList.setItems(starFacts);
		factList.maxWidth(50);
		factList.setPrefHeight(100);

		VBox factBox = new VBox();

		factBox.getChildren().add(factList);		
		factBox.setPadding(new Insets(15, 12, 15, 12));

		firstTitledPane.setContent(factBox);

		//making the second fact pane
		TitledPane secondTitledPane = new TitledPane();
		secondTitledPane.setText("Atmospheric Facts");

		ListView<String> factList2 = new ListView<>();

		ObservableList<String> atmoFacts = FXCollections.observableArrayList("Atmosphere Pressure [Earth-norm]: "
				+String.valueOf(planet.getAtmoPressure()), "Atmosphere Composition: "+planet.getAtmoshericCompositionParsed(), "Hydrosphere type: "+planet.getHydrosphereDescription(),
				"Hydrosphere [%]: "+String.valueOf(planet.getHydrosphere()));

		factList2.setItems(atmoFacts);
		factList2.maxWidth(50);
		factList2.setPrefHeight(100);

		VBox atmoFactBox = new VBox();

		atmoFactBox.getChildren().add(factList2);		
		atmoFactBox.setPadding(new Insets(15, 12, 15, 12));

		secondTitledPane.setContent(atmoFactBox);

		//making the third fact pane
		TitledPane thirdTitledPane = new TitledPane();
		thirdTitledPane.setText("Geophysical Facts");

		ListView<String> factList3 = new ListView<>();

		ObservableList<String> geoFacts = FXCollections.observableArrayList("Radius [km]: "
				+String.valueOf(planet.getRadius()), "Density [Earth-norm]: "+planet.getDensity(), "Core type: "+planet.getTectonicCore(),
				"Tectonic Activity: "+planet.getTectonicActivityGroup(), "Magnetic Field: "+planet.getMagneticField());

		factList3.setItems(geoFacts);
		factList3.maxWidth(50);
		factList3.setPrefHeight(100);

		VBox geoFactBox = new VBox();

		geoFactBox.getChildren().add(factList3);		
		geoFactBox.setPadding(new Insets(15, 12, 15, 12));

		thirdTitledPane.setContent(geoFactBox);
		//making the fourth fact pane
		TitledPane fourthTitledPane = new TitledPane();
		fourthTitledPane.setText("Habitational Facts");

		ListView<String> factList4 = new ListView<>();

		ObservableList<String> climateFacts = FXCollections.observableArrayList("Surface temperature [C]: "+String.valueOf(planet.getSurfaceTemp()-274.0),
				"Orbital Period [Earth Years]: "+String.valueOf(planet.getOrbitalPeriod()),"Rotational Period [Earth days]: "+String.valueOf(planet.getRotationalPeriod())+
				"(Tidelocked: "+String.valueOf(planet.isTidelocked())+")", "Axial Tilt [Degrees]: "+String.valueOf(planet.getAxialTilt()), 
				"Orbital Eccentricity: "+String.valueOf(planet.getOrbitaleccentricity()));

		factList4.setItems(climateFacts);
		factList4.maxWidth(50);
		factList4.setPrefHeight(150);

		VBox climateFactBox = new VBox();

		climateFactBox.getChildren().add(factList4);		
		climateFactBox.setPadding(new Insets(15, 12, 15, 12));

		fourthTitledPane.setContent(climateFactBox);
		//Rangeband display		
		TitledPane fifthTitledPane = new TitledPane();
		fifthTitledPane.setText("Temperature Rangebands");

		TableView<TemperatureRangeBandHelpClass> temperatureTable = new TableView<TemperatureRangeBandHelpClass>();
		ObservableList<TemperatureRangeBandHelpClass> temperatures = FXCollections.observableArrayList();



		TemperatureRangeBandHelpClass base = new TemperatureRangeBandHelpClass("base",planet.getRangeBandTemperature());
		TemperatureRangeBandHelpClass summer = new TemperatureRangeBandHelpClass("summer",planet.getRangeBandTempSummer());
		TemperatureRangeBandHelpClass winter = new TemperatureRangeBandHelpClass("winter", planet.getRangeBandTempWinter());

		System.out.println(base);
		temperatures.add(base);
		temperatures.add(summer);
		temperatures.add(winter);

		temperatureTable.itemsProperty().set(temperatures);
		
		TableColumn<TemperatureRangeBandHelpClass, String> name = new TableColumn<>("Type");
		TableColumn<TemperatureRangeBandHelpClass, Integer> one = new TableColumn<>("Equatorial");
		TableColumn<TemperatureRangeBandHelpClass, Double> two = new TableColumn<>("5-15");
		TableColumn<TemperatureRangeBandHelpClass, Double> three = new TableColumn<>("15-25");
		TableColumn<TemperatureRangeBandHelpClass, Double> four = new TableColumn<>("25-35");
		TableColumn<TemperatureRangeBandHelpClass, Double> five = new TableColumn<>("35-45");
		TableColumn<TemperatureRangeBandHelpClass, Double> six = new TableColumn<>("45-55");
		TableColumn<TemperatureRangeBandHelpClass, Double> seven = new TableColumn<>("55-65");
		TableColumn<TemperatureRangeBandHelpClass, Double> eight = new TableColumn<>("65-75");
		TableColumn<TemperatureRangeBandHelpClass, Double> nine = new TableColumn<>("75-85");
		TableColumn<TemperatureRangeBandHelpClass, Double> ten = new TableColumn<>("Polar");
		
		name.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, String>("name"));
		one.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Integer>("one"));
		two.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("two"));

		temperatureTable.getColumns().addAll(name,one,two,three,four,five,six,seven,eight,nine,ten);

		fifthTitledPane.setContent(temperatureTable);

		//Making me page
		Accordion furtherFacts= new Accordion();      
		furtherFacts.getPanes().addAll(firstTitledPane,secondTitledPane,thirdTitledPane,fourthTitledPane,fifthTitledPane);

		infoPage.getChildren().add(furtherFacts);

		return infoPage;
	}



	private static void findStarColour(Planet planet, PhongMaterial planetColour) {
		planetColour.setDiffuseColor(Color.DARKGRAY);

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
		//Making the picture of star
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
		//making the factbox
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

		//Making the other listnings (here the other main objects in the system)
		TitledPane secondTitledPane = new TitledPane();
		secondTitledPane.setText("System Objects");

		TableView<StellarObject> table = new TableView<StellarObject>();
		ObservableList<StellarObject> systemOrbitsObjects = FXCollections.observableArrayList();

		for(int i=1; i<star.getOrbitalObjects().size();i++){
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
