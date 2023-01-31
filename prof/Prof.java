package prof;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import etudiant.Client;

public class Prof  extends UnicastRemoteObject implements Iprof{
	private etudiant.Iserver od;
	private String username;
	private String psswd;
	
	public Prof() throws Exception {
		super();
		String url="rmi://localhost/irisi";
		try {
			 od=(etudiant.Iserver) Naming.lookup(url);
		}catch(Exception e) {
			System.out.println("Serveur introuvable");
		}
	}
	
	public String login(String username,String psswd) throws Exception {
		return(od.login(username,psswd));
	}
	
	public String signUp(String username,String nom,String prenom,String psswd,String fonction) throws Exception {
		String response=od.signUp(username, nom, prenom, psswd,fonction);
		return response;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPsswd() {
		return psswd;
	}

	public void setPsswd(String psswd) {
		this.psswd = psswd;
	}

	public void addprof(Iprof prof) throws Exception {
		 od.addprof(prof);
	}

	public String addClass(String className, ArrayList<String> listStudents, String profName) throws Exception {
		
		return (od.createClass(className,listStudents,profName));
		
	}
//liste des etudients
	public ArrayList<String> listAllStudents(String username) throws Exception {

		return (od.listAllStudents());
	}

	public ArrayList<String> listClass(String username) throws Exception {
		return (od.listClass(username));
	}

	public String deleteClass(ArrayList<String> selected)  throws  Exception{
		
		return (od.deleteClass(selected));
	}
//liste des etudients de la classe 
	public ArrayList<String> listStudentsOftheClass(String className) throws  Exception {
		
		return (od.listStudentsOftheClass(className));
	}

//liste des etudients connectes	
	public ArrayList<String> listConnectedStudent(String username) throws  Exception {
		ArrayList<String> listConnectedStudent=od.listConnectedStudent(username);
				for(String l: listConnectedStudent) System.out.println(l);
		return (od.listConnectedStudent(username));
	}

	public void sendFile(File file, ArrayList<String> list, String type) {
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

	public void sendMsg(String message, ArrayList<String> selected, String username, String type) throws Exception {
		od.sendMsg(message, selected,username,type);
	}

//	public void ajouterprof(Iprof p) throws  Exception {
//		System.out.println("prof  :"+ p);
//		od.ajouterprof(p);
//		
//	}

	//pour dessiner dans le tableau blanc
	public void tableaublanc(ArrayList<String> listStudents, int xmousePressed, int ymousePressed, int xmouseReleased, int ymouseReleased) throws Exception {
		od.tableaublanc(listStudents,xmousePressed, ymousePressed, xmouseReleased, ymouseReleased);
		
	}
//ouvrir le tableau
	public void openTableaublanc(ArrayList<String> listStudents) throws Exception {
		od.openTableaublanc(listStudents);
		
	}

	public String deleteStudent(String name) throws Exception {
		
		return (od.deleteStudent(name));
		
	}

	public void sendMsgbrodcast(String text, String username, String className,String type) throws Exception {
		od.sendMsgbrodcast(text,username,className,type);
		
	}


}

