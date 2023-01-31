package etudiant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import javafx.scene.control.TextArea;


public class Client extends UnicastRemoteObject implements Iclient { 
	private String username;
	private String psswd;
	private String nom ;
	private String prenom ;
	private Iserver od;
	private TableauBlancStudent meet;
	private ConversationView cnv;
	private MeetViewStudent meetview;

	public Client(String username,String psswd) throws RemoteException{
        super();
		this.setUsername(username);
		this.psswd=psswd;
	}
	
	public Client() throws Exception{
//		super();
		String url="rmi://localhost/irisi";
		try {
			 od=(Iserver) Naming.lookup(url);
		}catch(Exception e) {
			System.out.println("Serveur introuvable");}
	}
	//envoyer des filchiers
	public void sendFile( File file,ArrayList<String> list, String type) {
		 ArrayList<Integer> inc;
         try (FileInputStream in = new FileInputStream(file)) {
             inc = new ArrayList<>();
             int c=0;
             while((c=in.read()) != -1) {
                 inc.add(c);
             }
             in.close(); 
             od.broadcastMessage(inc, list,file.getName(),type);
       }
         catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	//liste des utilisateurs connectes
	public ArrayList<String> listConnectedClient(String string) throws Exception {
		ArrayList<String> str=(od.listConnectedClient());
		ArrayList<String> list=new ArrayList<String>();
		for(String s:str) {
		if (! s.equals(string))
			list.add(s);}
		return list;
	}

	public String login(String username,String psswd) throws Exception {
		return(od.login(username,psswd));
	}
	
	public String signUp(String username,String nom,String prenom,String psswd,String fonction) throws Exception {
		String response=od.signUp(username, nom, prenom, psswd,fonction);
		return response;
	}
	@Override
	public void receiveMsg(String message,String sender , String type) {
		if(type.equals("meet"))
			meetview.changeArea("("+sender+") :"+message);
		else cnv.changeArea("("+sender+") :"+message);
		
	}

	@Override
	public void sendMsg(String message ,ArrayList<String> list,String username, String type ) throws Exception {
		od.sendMsg(message, list,username,type);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getPsswd() {
		return username;
	}

	public void setPsswd(String psswd) {
		this.psswd = psswd;
	}
	public void addclient(Iclient c) throws Exception {
		od.addclient(c);
		
	}
 
	public ConversationView getCnv() {
		return cnv;
	}
	//modifier le texte dans TextArea apres recevoire les donnees 
	public void setCnv(ConversationView cnv) {
		this.cnv = cnv;
	}
	
	@Override
	public void receiveFile(ArrayList<Integer> inc,  String filename,String type) throws Exception {
		if(type.equals("meet"))
			meetview.uploadfile(filename,inc);
		else cnv.uploadfile(filename,inc);
            		
	}

	@Override
	public void tableaublanc(int xmousePressed, int ymousePressed, int xmouseReleased, int ymouseReleased) throws Exception {
		meet.setArg(xmousePressed, ymousePressed, xmouseReleased, ymouseReleased);	
	}
	@Override
	public void openTableaublanc() throws Exception {
		
		 meet= new TableauBlancStudent(username);
		
	}
	public ArrayList<String> listClass(String username)  throws Exception  {
		ArrayList<String> listClass =od.listClassUser(username);
		for(String l: listClass) System.out.println(l);
		return (od.listClassUser(username));
	}
	public void logout(String username) throws Exception{
		od.logout(username);	
	}
	//liste des studients de la classe
	public ArrayList<String> listStudentsOfClass(String className) throws Exception {
		return (od.listStudentsOftheClass(className));
	}
	public MeetViewStudent getMeetview() {
		return meetview;
	}
	public void setMeetview(MeetViewStudent meetview) {
		this.meetview = meetview;
	}
	
}
