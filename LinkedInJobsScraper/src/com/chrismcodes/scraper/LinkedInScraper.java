/*
 * @author: ChrisMCodes
 * 
 * Description: GUI app for finding jobs on LinkedIn Jobs
 * 
 * Last updated 2021-21-05
 * 
 */


package com.chrismcodes.scraper;


import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LinkedInScraper extends Application {
	
	public List<Job> jobs = new ArrayList<Job>();
	
	/**************************************************************
	 * 
	 * Method: start
	 * @param primaryStage: Stage
	 * @return void
	 * 
	 * Description: launches GUI window
	 * to prompt for user input.
	 * 
	 **************************************************************/
	@Override
	public void start(Stage primaryStage) {
		VBox vb = new VBox();
		Button button = new Button("Submit");
		Label promptJobTitle = new Label();
		Label promptJobLocation = new Label();
		TextField jobTitle = new TextField();
		TextField jobLocation = new TextField();
		promptJobTitle.setText("Job Title:\n");
		promptJobLocation.setText("Location of Job\n");
		button.setOnAction(e -> {
			jobs = getResults(jobTitle.getText(), jobLocation.getText());
			showSecondStage(jobs);
		});
		vb.setPadding(new Insets(15));
		vb.getChildren().addAll(promptJobTitle, jobTitle, 
				promptJobLocation, jobLocation, button);
		Scene scene = new Scene(vb, 400, 250);
		primaryStage.setTitle("Search for Jobs");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**************************************************************
	 * 
	 * Method: showSecondStage
	 * 
	 * @return void
	 * 
	 * Description: launches second GUI window
	 * if results are found from job query
	 * 
	 **************************************************************/

	private void showSecondStage(List<Job> jobs) {
		Stage secondStage = new Stage();
		VBox results = new VBox();
		ScrollBar scrollbar = new ScrollBar();
		scrollbar.setOrientation(Orientation.VERTICAL);
		String message = getOutputLabel(jobs);
		Label firstFiveResults = new Label(message);
		Button close = new Button("Exit");
		close.setOnAction(h -> {
			System.exit(0);
		});
		results.setPadding(new Insets(15));
		results.getChildren().addAll(firstFiveResults, close);
		Scene resultWindow = new Scene(results, 800, 600);
		secondStage.setTitle("Search Results");
		secondStage.setScene(resultWindow);
		secondStage.show();
	}

	
	
	/**************************************************************
	 * 
	 * Method: getOutputLabel
	 * 
	 * @return output (String)
	 * 
	 * Description: Compiles results into one
	 * long string.
	 * 
	 **************************************************************/

	private String getOutputLabel(List<Job> jobs) {
		String output = "";
		if (jobs.size() == 0) { return "No results found"; }
		for (Job job : jobs) {
			output += job.getTitle().text() + "\n" +
			job.getLocation().text() + "\n" +
			"===========================================\n\n";
		}
		return output;
	}
	
	/**************************************************************
	 * 
	 * Method: getResults
	 * @param title: String
	 * @param locale: String
	 * @return jobs: List<Job>
	 * 
	 * Description: scrapes for results
	 * and returns them as an ArrayList
	 * of Job objects
	 * 
	 **************************************************************/
	
	private static List<Job> getResults(String title, String locale) {
 		try {

 			String searchUrl = "https://www.linkedin.com/jobs/search/?keywords=" +
 					title + "&location=" + locale;
 			Document doc = Jsoup.connect(searchUrl).get();
 			Elements jobTitles = doc.getElementsByClass("result-card__title job-result-card__title");
 			Elements jobLocations = doc.getElementsByClass("job-result-card__location");
 			System.out.println(jobTitles.size());
 			List<Job> jobs = new ArrayList<Job>();
 			for (int i = 0; i < jobTitles.size(); i++) {
 				Job current = new Job(jobTitles.get(i), jobLocations.get(i));
 				jobs.add(current);
 			}
 			if (jobs.isEmpty()) {
 				noResultsAlert();
 			} else {
 				return jobs;
 			} 
		} catch (Exception f) {
			exceptionGui();
			f.printStackTrace();
		}
 		return null;
	}
	
	

/**************************************************************
 * 
 * Method: exceptionGui
 * 
 * @return void
 * 
 * Description: launches GUI window
 * to display exception message.
 * 
 **************************************************************/
private static void exceptionGui() {
	Stage exceptionStage = new Stage();
					VBox exception = new VBox();
					Button exitException = new Button("Exit");
					exitException.setOnAction(g -> {
						System.exit(1);
					});
					Label exceptionMessage = new Label("An error occurred");
					exception.getChildren().addAll(exceptionMessage, exitException);
					Scene exceptionScene = new Scene(exception, 250, 150);
					exceptionStage.setScene(exceptionScene);
					exceptionStage.show();
}

/**************************************************************
 * 
 * Method: noResultsAlert
 * 
 * @return void
 * 
 * Description: launches GUI window
 * to inform that no results were found
 * 
 **************************************************************/
private static void noResultsAlert() {
	Stage alertStage = new Stage();
	 				VBox alert = new VBox();
	 				Button exitButton = new Button("Exit");
	 				exitButton.setOnAction(h -> {
						System.exit(0);
					});
	 				Label alertMessage = new Label("No results were found.");
	 				alert.getChildren().addAll(alertMessage, exitButton);
	 				Scene alertScene = new Scene(alert, 250, 150);
	 				alertStage.setScene(alertScene);
	 				alertStage.show();
}

/**************************************************************
 * 
 * Method: main
 * @param args[]: String[]
 * @return void
 * 
 * Description: driver class
 * 
 **************************************************************/
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
