module ErrorShooter {
	requires javafx.controls;
	requires javafx.graphics;
	requires org.junit.jupiter.api;
	
	opens application to javafx.graphics, javafx.fxml;
}
