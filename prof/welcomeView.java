package prof;

import java.util.ArrayList;

import etudiant.LoginView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class welcomeView extends Application{
	private Prof prof;
	public welcomeView(Prof prof) {
		this.prof=prof;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Elearning");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setStyle("-fx-background-color: BURLYWOOD;");
		
		Label scenetitle = new Label("Professor :"+prof.getUsername());
		scenetitle.setStyle("-fx-text-fill: CADETBLUE;");
		scenetitle.setFont(Font.font("Arial Black", FontWeight.BOLD, 25.0)); 
		grid.add(scenetitle, 1, 0);
		
		Label labelList = new Label("liste classes");
		labelList.setAlignment(Pos.TOP_LEFT);
		labelList.setFont(Font.font(19.0));
		grid.add(labelList, 0, 1);
		
		
		ListView<String> list= new ListView<>();
		
		ArrayList<String> listClass =prof.listClass(prof.getUsername());
		for (String c:listClass )
		list.getItems().add(c);
		grid.add(list, 0, 2);
		
		ArrayList<String> selected = new ArrayList<String>();
		list.getSelectionModel().selectedItemProperty().addListener(observable -> 
		selected.add(list.getSelectionModel().getSelectedItem())
);
		
		
		Button refreshbtn=   new Button("  refresh   ");
		VBox box=new VBox();
		box.setAlignment(Pos.BOTTOM_RIGHT);
		box.getChildren().add(refreshbtn);
		
		grid.add(refreshbtn,0, 3);
		
		Button addClassBtn=  new Button("Add class   ");
		
		Button deletClassBtn=new Button("Delete class");
		
		Button detailBtn=    new Button("  Details   ");
		
		Text actiontarget = new Text();
//	        grid.add(actiontarget, 1, 6);
	        
		VBox v= new VBox(40);
		v.getChildren().addAll(addClassBtn,deletClassBtn,detailBtn,actiontarget);
		grid.add(v,2, 2);
		
		Button btnChat=      new Button("    Chat    ");
		btnChat.prefHeight(40);
		btnChat.prefWidth(90);
		grid.add(btnChat,3, 3);
		
		Button logoutbtn=    new Button("   Logout    ");
		grid.add(logoutbtn,4, 0);
		
		Button meetbtn=    new Button("   Meet    ");
		grid.add(meetbtn,1, 3);
		
		
		meetbtn.setOnAction((evt)->{
			if(selected.size() != 0) {
			try {
				int i= selected.size() -1;
				ArrayList<String> studentOfTheClass=prof.listStudentsOftheClass(selected.get(i));
				actiontarget.setText("");
				new MeetView(prof,primaryStage,selected.get(i)).start(new Stage()); 
				
				prof.openTableaublanc(selected);
				
				primaryStage.hide();
				TableauBlanc c= new TableauBlanc(prof,studentOfTheClass);
//				new ChatView(prof,primaryStage).start(new Stage()); 
				
			} catch (Exception e) {e.printStackTrace();}
			}
			else actiontarget.setText("Choisir une classe tout d'abord !");
		});
		
		refreshbtn.setOnAction((evt)->{
			list.getItems().clear();
			ArrayList<String> RlistClass =new ArrayList<String>();
						try {
							RlistClass =prof.listClass(prof.getUsername());
						} catch (Exception e) {
							e.printStackTrace();
						}
						for (String c:RlistClass )
							list.getItems().add(c);
		});
		
		deletClassBtn.setOnAction((evt)->{
			if(selected.size() != 0) {
			try { 	actiontarget.setText("");
				String response =prof.deleteClass(selected);
				if (response.equals("success")) {
					for(String c : selected)
					showAlertWithoutHeaderText("class ("+c+") deleted ");
					}
			} catch (Exception e) {e.printStackTrace();}
			}else actiontarget.setText("Choisir une classe tout d'abord !");
		});
		logoutbtn.setOnAction((evt)->{
			System.out.println("bye");
			try {
				primaryStage.close();
				new LoginView().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		detailBtn.setOnAction((evt)->{
			if(selected.size() != 0) {
			try {	actiontarget.setText("");
				int i =selected.size() -1;
				new DetailView(prof,selected.get(i)).start(new Stage());
			} catch (Exception e) {
				
				e.printStackTrace();
			}}else actiontarget.setText("Choisir une classe tout d'abord !");
		});
		btnChat.setOnAction((evt)->{
		
			try {
				primaryStage.hide();
				
				new ChatView(prof,primaryStage).start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// conversation 
			//meet =choisir les personnes dans la meme class = projeter le tableau 
		});
		addClassBtn.setOnAction((evt)->{
			try {
				new CreateClassView(prof).start(new Stage());
			} catch (Exception e) {
				
				e.printStackTrace();
			}	
	    });
	    
		Scene scene = new Scene(grid, 850, 600);
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
