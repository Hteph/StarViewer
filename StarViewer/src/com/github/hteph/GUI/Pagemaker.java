package com.github.hteph.GUI;

import java.awt.Label;

import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;

import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public final class Pagemaker {

	private Pagemaker(){
		//No instances of thic class please!

	}

	//Methods------------------------------------------------


	//Inner Methods -----------------------------------------------

	private VBox StarPageGenerator(Star star) {

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
		
//	     RotateTransition rt = new RotateTransition(Duration.millis(3000), starSphere);
//	     rt.setByAngle(180);
//	     rt.setCycleCount(4);
//	     rt.setAutoReverse(true);
//	     rt.play();
		Group displayStar = new Group(starSphere);

		HBox intro = new HBox(description, displayStar);
		intro.setPadding(new Insets(15, 12, 15, 12));
		intro.setSpacing(10);

		infoPage.getChildren().add(intro);
		
	      TitledPane firstTitledPane = new TitledPane();
	      firstTitledPane.setText("Facts");

		
		Text factDump = new Text("Lumosity [Sol-eqv]: "+String.valueOf(star.getLumosity())+" Mass [Sol-eqv]: "
		+String.valueOf(star.getMass())+"\nDiameter [Sol-eqv]:"+String.valueOf(star.getRadius())+" Age [Gy]: "
				+String.valueOf(star.getAge()));
		
		VBox factBox = new VBox();
		
		factBox.getChildren().add(factDump);		
		factBox.setPadding(new Insets(15, 12, 15, 12));
		
		firstTitledPane.setContent(factBox);

	      TitledPane secondTitledPane = new TitledPane();
	      secondTitledPane.setText("System Objects");

	      VBox systemsObjects = new VBox();
	      
	      systemsObjects.getChildren().add(new Text("Here there will be planets"));
	      systemsObjects.getChildren().add(new Text("Here there will be planets"));
	      systemsObjects.getChildren().add(new Text("Here there will be planets"));
	      systemsObjects.getChildren().add(new Text("Here there will be planets"));
	      systemsObjects.getChildren().add(new Text("Here there will be planets"));
	      
	      secondTitledPane.setContent(systemsObjects);
	      
	      Accordion furtherFacts= new Accordion();      
	      furtherFacts.getPanes().addAll(firstTitledPane, secondTitledPane);
	    
	      infoPage.getChildren().add(furtherFacts);
		
		
		return infoPage;
	}


}
