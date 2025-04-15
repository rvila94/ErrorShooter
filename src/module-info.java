module ErrorShooter {
	requires javafx.controls;
	requires javafx.graphics;
	// DECOMENTE SI TU VEUX LES TESTS JUNIT
	//requires org.junit.jupiter.api;
	
	opens application to javafx.graphics, javafx.fxml;
}
