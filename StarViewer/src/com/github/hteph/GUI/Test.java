package com.github.hteph.GUI;

import java.awt.Label;

import com.github.hteph.Generators.StarGenerator;
import com.github.hteph.Generators.StarSystemGenerator;
import com.github.hteph.ObjectsOfAllSorts.Star;

import javafx.application.Application;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Test extends Application{



	@Override 
	public void start(Stage stage) {


		Star star = (Star) StarGenerator.Generator();
		StarSystemGenerator.Generator(star);

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

		VBox systemsObjects = new VBox();

		for(int i=0; i<star.getOrbitalObjects().size();i++){
			systemsObjects.getChildren().add(new Text(star.getOrbitalObjects().get(i).getName()));
		}
		TableView table = new TableView();

		secondTitledPane.setContent(systemsObjects);

		Accordion furtherFacts= new Accordion();      
		furtherFacts.getPanes().addAll(firstTitledPane, secondTitledPane);

		infoPage.getChildren().add(furtherFacts);



		//Creating a scene object 
		Scene scene = new Scene(infoPage, 600, 1000);  

		//Setting title to the Stage 
		stage.setTitle("Drawing a Sphere - draw fill");

		//Adding scene to the stage 
		stage.setScene(scene); 

		//Displaying the contents of the stage 
		stage.show(); 
	}      
	private void findStarColour(Star star, PhongMaterial starColour) {

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
	public static void main(String args[]){ 
		launch(args); 
	} 
}

