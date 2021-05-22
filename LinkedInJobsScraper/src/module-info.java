module LinkedInJobsScraper {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires org.jsoup;
	
	opens com.chrismcodes.scraper to javafx.graphics, javafx.fxml;
}
