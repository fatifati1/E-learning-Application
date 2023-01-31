package etudiant;

import java.rmi.Naming;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import prof.Iprof;
import prof.Prof;
import prof.welcomeView;

public class LoginView extends Application {
	
	private Client c;
	private Prof prof;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		c=new Client();
		prof=new Prof();
		primaryStage.setTitle("Elearning");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));
		grid.setStyle("-fx-background-color: BURLYWOOD;");
		Text scenetitle = new Text("Welcome");
//		scenetitle.setStyle("-fx-text-fill: blue;");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		userTextField.setPromptText("your name ..");
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		pwBox.setPromptText("your password ..");
		grid.add(pwBox, 1, 2);
		
		Button btn = new Button("Sign in");
		Button btn2 = new Button("Sign up");
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().addAll(btn,btn2);  
		grid.add(hbBtn, 1, 4);
		
		 Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        // le boutton de login 
        
        btn.setOnAction((evt)->{
        	String username= userTextField.getText();
        	String psswd= pwBox.getText();
        	try {
        		String cli=c.login(username,psswd);
        		System.out.println(cli);
				if(! cli.equals("vous n'etes pas un utilisateur")) {
					if (cli.equals("professor")) {
						prof.setUsername(username);
						prof.setPsswd(psswd);
						primaryStage.hide();
//						prof.addprof((Iprof)prof);
//						prof.ajouterprof((Iprof)this.prof);
						new welcomeView(prof).start(new Stage()); 
						
					}else  {
					c.setUsername(username);
					c.setPsswd(psswd);
					primaryStage.hide();
					c.addclient((Iclient)c);
					new Menu(c).start(new Stage());}}
				else { 
					actiontarget.setText("Please enter your name correctly");
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	
        });
        //l'enregistrement
        btn2.setOnAction((evt)->{
        	try {
        		primaryStage.close();
				new registerView(c).start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
		Scene scene = new Scene(grid, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
		

}


