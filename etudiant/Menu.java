package etudiant;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {
	private Client c;
	
	public Menu(Client c) {
		this.c=c;
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
			
//			Text scenetitle = new Text("Welcome");
//			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			
			Label scenetitle = new Label("WELCOME "+c.getUsername());//
			scenetitle.setLayoutX(226.0);
			scenetitle.setLayoutY(59.0);
			scenetitle.prefHeight(21.0);
			scenetitle.prefWidth(116.0);
			scenetitle.setFont(Font.font("Arial Black", FontWeight.NORMAL, 15.0));
			grid.add(scenetitle, 0, 0, 2, 1);
			
			Button Meetbtn = new Button(" Meet ");
			grid.add(Meetbtn,2,4);
			
			
			Label labelList = new Label("liste classes");
			labelList.setAlignment(Pos.TOP_LEFT);
			labelList.setFont(Font.font(19.0));
			grid.add(labelList, 1, 1);
			ListView<String> list= new ListView<>();
			
			
			ArrayList<String> selected =new ArrayList<String>();
			list.getSelectionModel().selectedItemProperty().addListener(observable -> 
			selected .add(list.getSelectionModel().getSelectedItem())
	);
			
			ArrayList<String> listClass =c.listClass(c.getUsername());
			for (String c:listClass )
			list.getItems().add(c);
			
			
			grid.add(list, 1, 2);
			
			 
			 Button refreshbtn=   new Button("  refresh   ");
			 grid.add(refreshbtn,1, 3);
			 
			 //reinitisalisation de la liste 
			 refreshbtn.setOnAction((evt)->{
					list.getItems().clear();
					ArrayList<String> RlistClass =new ArrayList<String>();
								try {
								 RlistClass =c.listClass(c.getUsername());		
								 } catch (Exception e) {
									e.printStackTrace();
								}
								for (String c:RlistClass )
									list.getItems().add(c);
				});
			
			Button Chatbtn = new Button("  Chat  ");
			grid.add(Chatbtn, 0, 4);
			
			Button logoutbtn=    new Button("  Logout  ");
			grid.add(logoutbtn,3, 0);
			
			
			Text actiontarget = new Text();
	        grid.add(actiontarget, 1, 6);
			
			logoutbtn.setOnAction((evt)->{
				System.out.println("bye");
				try {
					primaryStage.close();
					c.logout(c.getUsername());
					//retirer le la liste
					new LoginView().start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			Meetbtn.setOnAction((evt)->{
				if(selected.size() != 0) {
					int i= selected.size() -1;
					actiontarget.setText("");
					try {
						new MeetViewStudent(c, selected.get(i), primaryStage).start(new Stage());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
					primaryStage.hide();
				
				} else actiontarget.setText("Choisir une classe tout d'abord !");
				
	         });
			
			//pour le chat
			Chatbtn.setOnAction((evt)->{
				primaryStage.close();
				try {
					new ConversationView(c).start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
	         });
			Scene scene = new Scene(grid, 650, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}

	}
