package etudiant;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import javax.management.timer.Timer;
import javax.swing.JLabel;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ConversationView extends Application{
	private Client c;
	private TextArea textArea,textArea2;
	private GridPane grid;
	private Hyperlink hyperlink,hyperlink1,hyperlink2,hyperlink3,hyperlink4;
	private Desktop desktop = Desktop.getDesktop();
	public ConversationView(Client c) {
		this.c=c;
		c.setCnv(this);
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
		
		
		Label scenetitle = new Label("Conversation "+c.getUsername());
		scenetitle.setLayoutX(208.0);
		scenetitle.setLayoutY(14.0);
		scenetitle.setStyle("-fx-text-fill: blue;");
		scenetitle.setFont(Font.font("Arial Black", FontWeight.BOLD, 19.0)); 
		grid.add(scenetitle, 0, 0);
		
		 textArea = new TextArea();
//		 textArea.setWrapText(true);
		textArea.setLayoutX(14.0);textArea.setLayoutY(56.0);textArea.prefHeight(200.0);textArea.prefWidth(388.0);textArea.setEditable(false);
		changeArea("");
		Label sharedFiles = new Label("Shared Files");
		sharedFiles.setStyle("-fx-text-fill: black;");
		sharedFiles.setFont(Font.font("Arial Black", FontWeight.BOLD, 14.0)); 
		
		
		 textArea2 = new TextArea();
		 textArea2.setEditable(false);
	    textArea.setMinHeight(70);
	    
//	    HBox hbBtn = new HBox(10);
//		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//		hbBtn.getChildren().addAll(textArea,textArea2);  
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
//		this.c.receiveMsg(message);
		
		Label labelList = new Label("Connected Clients ");
		labelList.setAlignment(Pos.CENTER);
		labelList.setContentDisplay(ContentDisplay.CENTER);
		labelList.setLayoutX(523.0);
		labelList.setLayoutY(44.0);
		labelList.prefHeight(25.0);
		labelList.prefWidth(153.0);
		labelList.setFont(Font.font(19.0));
		grid.add(labelList, 2, 0);
		
		ListView<String> list= new ListView<>();
		ArrayList<String> selected = new ArrayList<String>();
		list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		list.setLayoutX(500.0);
		list.setLayoutY(69.0);
		list.prefHeight(200.0);
		list.prefWidth(200.0);
		ArrayList<String> ConnectedClient =c.listConnectedClient(c.getUsername());
		for (String c:ConnectedClient )
		list.getItems().add(c);

		Button filebtn=new Button("send file");
		grid.add(filebtn, 1, 4);
		
		final FileChooser fileChooser = new FileChooser();
		//lenvoides fichiers 
		filebtn.setOnAction((evt)->{
//			 File file = fileChooser.showOpenDialog(primaryStage);
//	            if (file != null) {
//	                openFile(file);
//	                List<File> files = Arrays.asList(file);
	                textArea2.clear();
	                List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
	                for(File f: files) {
	                	 textArea2.appendText(f.getName());
//	                	 textArea2.
	                     textArea2.appendText("\n");
	                     System.out.println("avant d;envoyer le file");
	                     c.sendFile(f,selected,"cnv" );
	                     }
	               
	                
	                
//	            }
		});
		
		list.getSelectionModel().selectedItemProperty().addListener(observable -> 
		selected.add(list.getSelectionModel().getSelectedItem())
//System.out.printf("Valeur sélectionnée:"+list.getSelectionModel().getSelectedItem()+" id  :"+list.getSelectionModel().getSelectedIndices()).println()
);
		grid.add(list, 2, 2);
		Button refreshbtn=new Button("refresh");
		grid.add(refreshbtn,2, 3);
		//actualiser la liste
		refreshbtn.setOnAction((evt)->{
			list.getItems().clear();
			ArrayList<String> RConnectedClient =new ArrayList<String>();
						try {
							RConnectedClient = c.listConnectedClient(c.getUsername());
						} catch (Exception e) {
							e.printStackTrace();
						}
						for (String c:RConnectedClient )
							list.getItems().add(c);
	
		 });
		TextField msg= new TextField();
		msg.setLayoutX(21.0);msg.setLayoutY(345.0);msg.prefHeight(62.0);msg.prefWidth(388.0);msg.setPromptText("entrer votre message ");
		grid.add(msg,0,5);
		
		Button sendbtn=new Button("Send");
		sendbtn.setLayoutX(428.0);
		sendbtn.setLayoutY(361.0);
		sendbtn.setMnemonicParsing(false);
		sendbtn.setFont(Font.font(14.0));
		grid.add(sendbtn,1, 5);
		
		  sendbtn.setOnAction((evt)->{
//				list.getSelectionModel().selectedItemProperty().addListener(observable -> selected.add(list.getSelectionModel().getSelectedItem()));

			  for(String l : selected) {System.out.println("selected :"+l);}
			  try {
				c.sendMsg(msg.getText(), selected,c.getUsername(),"cnv");
			} catch (Exception e) {
				e.printStackTrace();
			}
			  selected.removeAll(selected);
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
//			 grid.getChildren().remove(2);
//				grid.getChildren().add(2, hyperlink);
			 
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
	            String separator;
	            if(System.getProperty("os.name").startsWith("Linux") || System.getProperty("os.name").startsWith("MacOS")) separator = "/";
	            else separator = "\\";
	            try {
					out = new FileOutputStream("C:\\Users\\fatimzzahra\\Desktop\\"+ filename);
				//System.getProperty("Users.home") + separator + filename
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

			// Header Text: null
			alert.setHeaderText(null);
			alert.setContentText("File Saved successfully!");

			alert.showAndWait();
		}
}
