package etudiant;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class MeetViewStudent extends Application {
	private TextArea textArea,textArea2;
	private GridPane grid;
	private Hyperlink hyperlink,hyperlink1,hyperlink2,hyperlink3,hyperlink4;
	private Stage Stage;
	private String className;
	private Client c;
	
	public MeetViewStudent(Client c, String Class ,Stage primaryStage) {
		this.c=c;
		c.setMeetview(this);
        this.Stage=primaryStage;
        className=Class;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {

		
		primaryStage.setTitle("Elearning");
		 grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		
		grid.setPadding(new Insets(50, 50, 50, 50));
//		grid.setStyle("-fx-background-color: BURLYWOOD;");
		
		
		Label scenetitle = new Label(" Meet : Student "+c.getUsername());
		scenetitle.setLayoutX(208.0);
		scenetitle.setLayoutY(14.0);
		scenetitle.setStyle("-fx-text-fill: CADETBLUE;");
		scenetitle.setFont(Font.font("Arial Black", FontWeight.BOLD, 19.0)); 
		grid.add(scenetitle, 0, 0);
		
		 textArea = new TextArea();
		textArea.setEditable(false);
		changeArea("");
		Label sharedFiles = new Label("Shared Files");
		sharedFiles.setStyle("-fx-text-fill: black;");
		sharedFiles.setFont(Font.font("Arial Black", FontWeight.BOLD, 14.0)); 
		
		
		 textArea2 = new TextArea();
		 textArea2.setEditable(false);
	    textArea.setMinHeight(70);
	     
		grid.add(sharedFiles, 0,3,1,1);
		
		grid.add(textArea, 0, 2); 
		hyperlink = new Hyperlink("files recu");
		hyperlink1 = new Hyperlink("files recu");
		hyperlink2 = new Hyperlink("files recu");
		hyperlink3 = new Hyperlink("files recu");
		hyperlink4 = new Hyperlink("files recu");
		final VBox root = new VBox();
		root.getChildren().addAll(hyperlink,hyperlink1,hyperlink2,hyperlink3,hyperlink4);
		areaOrLabel("");
		grid.add(root, 2, 4);
		
//		Label labelList = new Label("Participants ");
//		labelList.setAlignment(Pos.CENTER);
//		labelList.setContentDisplay(ContentDisplay.CENTER);
//		labelList.setLayoutX(523.0);
//		labelList.setLayoutY(44.0);
//		labelList.prefHeight(25.0);
//		labelList.prefWidth(153.0);
//		labelList.setFont(Font.font(19.0));
//		grid.add(labelList, 2, 0);

		
//		ListView<String> list= new ListView<>();
//		ArrayList<String> selected = new ArrayList<String>();
//		list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
////		list.setLayoutX(500.0);
////		list.setLayoutY(69.0);
////		list.prefHeight(200.0);
////		list.prefWidth(200.0);
//		
//		list.setMouseTransparent( true );
//		list.setFocusTraversable( false );
//		
//		ArrayList<String> ConnectedStudent =c.listConnectedStudent(prof.getUsername());
//		for (String c:ConnectedStudent )
//		list.getItems().add(c);

		Button filebtn=new Button("send file");
		grid.add(filebtn, 1, 4);
		
		 ArrayList <String>   studentsOfClass=c.listStudentsOfClass(className);
		final FileChooser fileChooser = new FileChooser();
		filebtn.setOnAction((evt)->{
	                textArea2.clear();
	                List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
	                for(File f: files) {
	                	 textArea2.appendText(f.getName());
//	                	 textArea2.
	                     textArea2.appendText("\n");
	                     System.out.println("avant d;envoyer le file");
	                    
	                     c.sendFile(f,studentsOfClass,"meet");
	                     }
	               
	                
	                
//	            }
		});
		
//		list.getSelectionModel().selectedItemProperty().addListener(observable -> 
//		selected.add(list.getSelectionModel().getSelectedItem())
//);
//		grid.add(list, 2, 2);
//		Button refreshbtn=new Button("refresh");
//		grid.add(refreshbtn,2, 3);
//		
//		refreshbtn.setOnAction((evt)->{
//			list.getItems().clear();
//			ArrayList<String> RConnectedClient =new ArrayList<String>();
//						try {
//							RConnectedClient = prof.listConnectedStudent(prof.getUsername());
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						for (String c:RConnectedClient )
//							list.getItems().add(c);
//	
//		 });
		TextField msg= new TextField();
		msg.prefHeight(62.0);msg.prefWidth(388.0);
		msg.setPromptText("entrer votre message ");
		grid.add(msg,0,5);
		
		Button sendbtn=new Button("Send");
		sendbtn.setLayoutX(428.0);
		sendbtn.setLayoutY(361.0);
		sendbtn.setMnemonicParsing(false);
		sendbtn.setFont(Font.font(14.0));
		grid.add(sendbtn,1, 5);
		
		Button retourbtn=new Button("Retour ");
		retourbtn.setFont(Font.font(14.0));
		grid.add(retourbtn,0, 6);
		
		
		retourbtn.setOnAction((evt)->{
			 primaryStage.close();
//			 System.exit(0);
			 Stage.show();
		 });
		
		  sendbtn.setOnAction((evt)->{
//			  for(String l : selected) {System.out.println("selected :"+l);}
			  try {
				c.sendMsg(msg.getText(),studentsOfClass,c.getUsername(),"meet");
			} catch (Exception e) {
				e.printStackTrace();
			}
//			  selected.removeAll(selected);
			   msg.setText("");
			   
//			      }
//			  });
			  
			  
		  });
//		  String filename=" ";
//		  filelabel = new Label("<HTML><U><font size=\"4\" color=\"#365899\">" + filename + "</font></U></HTML>");
//		  grid.add(filelabel, 0, 6);
//		  grid.add(hyperlink, 0, 6);
		Scene scene = new Scene(grid, 1100, 700);
		primaryStage.setScene(scene);
		
 
		primaryStage.show();
		
	}
	 public void changeArea(String message) {
//		 if(this.c.isMsgRecu()) {
//			  System.out.println("text recu");
		 if( ! message.equals("")) {
			  textArea.appendText(message);
			  textArea.appendText("\n");
			  }
		 else 
			  textArea.appendText(message);

	 }
	 public void areaOrLabel(String filename) {
		
		 if( ! filename.equals("")) {
			 
			 System.out.println("modifier labels");
			 System.out.println(filename);

		 }else { System.out.println("modifier txtarea");
//		 grid.add(hyperlink, 0, 6); 
		 grid.add(textArea2, 0, 4);}
		 
	 }
	 
	 public void uploadfile(String filename,ArrayList<Integer> inc) {
		 if(hyperlink.getText().equals("files recu")) {
			 hyperlink.setText(filename);
			 hyperlink.setOnAction(evt -> {
			 changeArea2(filename,inc);
			 });
		 }
		 else if(hyperlink1.getText().equals("files recu")) {
			 hyperlink1.setText(filename);
			 hyperlink1.setOnAction(evt -> {
				 changeArea2(filename,inc);
				 });
		 }
		 else if(hyperlink2.getText().equals("files recu")) {
			 hyperlink2.setText(filename);
			 hyperlink2.setOnAction(evt -> {
				 changeArea2(filename,inc);
				 });
		 }
		 else if(hyperlink3.getText().equals("files recu")) {
			 hyperlink3.setText(filename);
			 hyperlink3.setOnAction(evt -> {
				 changeArea2(filename,inc);
				 });
		 }
		 else if(hyperlink4.getText().equals("files recu")) {
			 hyperlink4.setText(filename);
			 hyperlink4.setOnAction(evt -> {
				 changeArea2(filename,inc);
				 });
		 }
		 
	 }
		 
	 public void changeArea2(String filename,ArrayList<Integer> inc) {
		
	 System.out.println("reception graphic");
	 
//	 hyperlink.setOnAction(evt -> {
			   FileOutputStream out;
//	            String separator;
//	            if(System.getProperty("os.name").startsWith("Linux") || System.getProperty("os.name").startsWith("MacOS")) separator = "/";
//	            else separator = "\\";
	            try {
					out = new FileOutputStream("C:\\Users\\fatimzzahra\\Desktop\\"+ filename);
	            String[] extension = filename.split("\\.");
	            for (int i = 0; i<inc.size(); i++) {
	                int cc = inc.get(i);
	                if(extension[extension.length - 1].equals("txt")||
	                        extension[extension.length - 1].equals("java")||
	                        extension[extension.length - 1].equals("php")||
	                        extension[extension.length - 1].equals("c")||
	                        extension[extension.length - 1].equals("cpp")||
	                        extension[extension.length - 1].equals("xml")
	                        )
	                out.write((char)cc);
	                else{
	                    out.write((byte)cc);
	                }
	            }
	            out.flush();
	            out.close();
	            } catch (Exception e) {
					
					e.printStackTrace();
				}
	            showAlertWithoutHeaderText();
//		   });
	 
			  System.out.println("stocker le file ");
//		  areaOrLabel(filename); 
			 
		 
	 }
	 public void showAlertWithoutHeaderText() {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Upload File ");

			
			alert.setHeaderText(null);
			alert.setContentText("File Saved successfully!");

			alert.showAndWait();
		}
}


