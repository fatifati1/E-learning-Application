package prof;

import java.util.ArrayList;

import etudiant.registerView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateClassView extends Application{
	private Prof prof;
	public CreateClassView(Prof prof) {
		this.prof=prof;
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
		Text scenetitle = new Text("Create Class");
		scenetitle.setFill(Color.CADETBLUE);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
		grid.add(scenetitle, 1, 0);

		Label nom = new Label("Class name :");
		grid.add(nom, 0, 1);

		TextField className = new TextField();
		className.setPromptText("Class name");
		grid.add(className, 2, 1);
		
		Label Student = new Label("Students :");
		grid.add(Student, 0, 2);

		ListView<String> list= new ListView<>();
		list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		ArrayList<String> listStudents =prof.listAllStudents(prof.getUsername());
		for (String s:listStudents )
		list.getItems().add(s);
		
		ArrayList<String> selected = new ArrayList<String>();
		
		list.getSelectionModel().selectedItemProperty().addListener(observable -> 
		selected.add(list.getSelectionModel().getSelectedItem())
	//System.out.printf("Valeur sélectionnée:"+list.getSelectionModel().getSelectedItem()+" id  :"+list.getSelectionModel().getSelectedIndices()).println()
	);
		grid.add(list, 2, 2);
		
		Button refreshbtn=     new Button("  refresh  ");
		grid.add(refreshbtn,2, 3);
		
		refreshbtn.setOnAction((evt)->{
			list.getItems().clear();
			ArrayList<String> RlistStudents =new ArrayList<String>();
						try {
							RlistStudents =prof.listAllStudents(prof.getUsername());
						} catch (Exception e) {
							e.printStackTrace();
						}
						for (String s:RlistStudents )
							list.getItems().add(s);
	
		 });
		
		Button addStudentbtn = new Button("Add Student");
		grid.add(addStudentbtn, 2, 4);
		
		Button validerbtn = new Button("Valider");

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().addAll(validerbtn);  
		grid.add(hbBtn, 2, 6);
		
		 Text actiontarget = new Text();
	    grid.add(actiontarget, 2, 7);
	  
	    validerbtn.setOnAction((evt)->{
	    	try {
				String reponse=prof.addClass(className.getText(),selected,prof.getUsername());
				if(reponse.equals("success")) {
					System.out.println("success *cote client ");
					showAlertWithoutHeaderText("class has been created successfully ");
					primaryStage.close();
				}
				else System.out.println("classe deja existe");
				actiontarget.setText("classe deja existe");
			} catch (Exception e) {
			
				e.printStackTrace();
			}
//	   	}
//	   	else actiontarget.setText(response);
	    	
	    });
	    addStudentbtn.setOnAction((evt)->{
	    	try {
				new registerStudentView(prof).start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    });
	    
		Scene scene = new Scene(grid, 650, 600);
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
