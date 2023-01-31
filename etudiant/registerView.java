package etudiant;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import prof.Prof;

public class registerView extends Application{
	private Client c;
	private String choixFonction;
	
	public registerView(Client c) {
		this.c=c;
	}
	public registerView(String name) {
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
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
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
		
		Label fonction = new Label("Fonction :");
		grid.add(fonction, 0, 5);
		 
		ComboBox<String> cb = new ComboBox<String>();
		 cb.getItems().add("professor");
		 cb.getItems().add("student");
		grid.add(cb, 2, 5);
		
		
		cb.getSelectionModel().selectedItemProperty().addListener(observable -> {
		  choixFonction=cb.getSelectionModel().getSelectedItem().toString();
		  System.out.println(choixFonction);});
		
//		 String choix ="";
//		  choix = cb.getSelectionModel().getSelectedItem().toString();
//		 System.out.println(choix);
//		ComboBoxChoice c = colDomain.getValue();
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().addAll(validerbtn);  
		grid.add(hbBtn, 2, 6);
		
		 Text actiontarget = new Text();
	    grid.add(actiontarget, 2, 7);
	  //valider l'enregistrement
	    validerbtn.setOnAction((evt)->{
	    	String response="";
	    	try {
				response=c.signUp(usernameTextField.getText(), nomTextField.getText(), prenomTextField.getText(), pwBox.getText(),choixFonction);
			} catch (Exception e) {e.printStackTrace();}
	   	if(response.equals("user bien inserer")) {
	   		try {
	   			primaryStage.close();
				new LoginView().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}

	   	}
	   	else actiontarget.setText(response);
	    	
	    });
	    
		Scene scene = new Scene(grid, 600, 400);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}		
}
