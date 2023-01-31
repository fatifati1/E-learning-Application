package prof;

import java.util.ArrayList;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DetailView extends Application {
	private Prof prof;
	private String className;
	
	public DetailView(Prof prof,String className) {
		this.prof=prof;
		this.className=className;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Elearning");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setStyle("-fx-background-color: BURLYWOOD;");
		grid.setPadding(new Insets(20, 20, 20, 20));

		Label scenetitle = new Label("Classe  : "+className);
		scenetitle.setStyle("-fx-text-fill: DARKCYAN;");

		scenetitle.setFont(Font.font("Arial Black", FontWeight.BOLD, 25.0)); 
		grid.add(scenetitle, 1, 0);
		
		Label labelprofName = new Label("Professor :");
		labelprofName.setStyle("-fx-text-fill: CADETBLUE;");
		labelprofName.setFont(Font.font("Arabic Typesetting", FontWeight.BOLD, 30.0)); 
		grid.add(labelprofName, 0, 1);
		
		Label profName = new Label(prof.getUsername());
		profName.setStyle("-fx-text-fill: CADETBLUE;");
		profName.setFont(Font.font("Arabic Typesetting", FontWeight.BOLD, 30.0)); 
		grid.add(profName, 2 , 1);
		
		Label listStudent = new Label("Students  :");
		listStudent.setStyle("-fx-text-fill: CADETBLUE;");
		listStudent.setFont(Font.font("Arabic Typesetting", FontWeight.BOLD, 30.0)); 
		grid.add(listStudent, 0, 2);
		
		Button addStudentBtn=       new Button(" Add Student  ");
		Button deleteStudentBtn=    new Button("Delete Student");
		Button refreshbtn=     new Button("  refresh  ");
		
		grid.add(refreshbtn,2, 3);
		HBox hbox= new HBox(40);
		hbox.getChildren().addAll(addStudentBtn,deleteStudentBtn);
		grid.add(hbox,2, 4);
		
		ListView<String> list= new ListView<String>();
		ArrayList<String> listStudents =prof.listStudentsOftheClass(className);
		for (String s:listStudents )
		list.getItems().add(s);
		
		ArrayList<String> selected = new ArrayList<String>();
		list.getSelectionModel().selectedItemProperty().addListener(observable -> 
		selected.add(list.getSelectionModel().getSelectedItem())
);
		grid.add(list, 2, 2);
		
		Text actiontarget = new Text();
		  grid.add(actiontarget, 2, 5);
		  
		  refreshbtn.setOnAction((evt)->{
				list.getItems().clear();
				ArrayList<String> RlistClient =new ArrayList<String>();
							try {
								RlistClient = prof.listStudentsOftheClass(className);
							} catch (Exception e) {
								e.printStackTrace();
							}
							for (String c:RlistClient )
								list.getItems().add(c);
			});
		
		addStudentBtn.setOnAction((evt)->{
			try {
				new registerStudentView(prof).start(new Stage());
				//add student to the class
			} catch (Exception e) {e.printStackTrace();}
		});
		
		deleteStudentBtn.setOnAction((evt)->{
			if(selected.size() != 0) {
				actiontarget.setText("");
			
			try {
				for(String name :selected) {
				String response=prof.deleteStudent(name);
				if(response.equals("success")) {
					showAlertWithoutHeaderText("Vous avez supprimer l'utilisateur "+name);
				}}
			} catch (Exception e) {e.printStackTrace();}
			} else actiontarget.setText("Choisir un étudiant tout d'abord !");
	
		});
		
		Scene scene = new Scene(grid, 750, 400);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	public void showAlertWithoutHeaderText(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Upload File ");

		
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

}
