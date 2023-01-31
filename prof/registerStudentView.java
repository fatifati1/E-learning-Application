package prof;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class registerStudentView extends Application {
	private Prof prof;
	public registerStudentView(Prof prof) {
		this.prof=prof;
	}
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Elearning");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));
		grid.setStyle("-fx-background-color: BURLYWOOD;");
		
		Text scenetitle = new Text("Sign Up");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
		scenetitle.setFill(Color.CADETBLUE);
		grid.add(scenetitle, 1, 0);

		Label nom = new Label("Last name :");
		grid.add(nom, 0, 1);

		TextField nomTextField = new TextField();
		nomTextField.setPromptText("Last name ");
		grid.add(nomTextField, 2, 1);
		
		Label prenom = new Label("First name :");
		grid.add(prenom, 0, 2);

		TextField prenomTextField = new TextField();
		prenomTextField.setPromptText("First name ");
		grid.add(prenomTextField, 2, 2);
		
		Label username = new Label("Username :");
		grid.add(username, 0, 3);

		TextField usernameTextField = new TextField();
		usernameTextField.setPromptText("Username");
		grid.add(usernameTextField, 2, 3);
		
		Label pw = new Label("Password :");
		grid.add(pw, 0, 4);

		PasswordField pwBox = new PasswordField();
		pwBox.setPromptText("Your password ");
		grid.add(pwBox, 2, 4);
		
		
		Button validerbtn = new Button("Valider");
		

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().addAll(validerbtn);  
		grid.add(hbBtn, 2, 5);
		
		 Text actiontarget = new Text();
	    grid.add(actiontarget, 2, 7);
	  
	    validerbtn.setOnAction((evt)->{
	    	String response="";
	    	try {
				response=prof.signUp(usernameTextField.getText(), nomTextField.getText(), prenomTextField.getText(), pwBox.getText(),"student");
			} catch (Exception e) {e.printStackTrace();}
	   	if(response.equals("user bien inserer")) {
	   		System.out.println("done");
	   		showAlertWithoutHeaderText("user has been created successfully !");
	   			primaryStage.close();
	   	}
	   	else actiontarget.setText(response);
	    	
	    });
	    
		Scene scene = new Scene(grid, 600, 400);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}	
	 public void showAlertWithoutHeaderText(String message) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Upload File ");

			// Header Text: null
			alert.setHeaderText(null);
			alert.setContentText(message);

			alert.showAndWait();
		}

}
