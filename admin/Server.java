package admin;

import java.io.File;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import etudiant.Client;
import etudiant.Iclient;
import etudiant.Iserver;
import prof.Iprof;
import prof.Prof;


public class Server extends UnicastRemoteObject implements Iserver {
	static private ArrayList<Iclient> clients;
	static private ArrayList<Iprof> profs;
	
	protected Server() throws RemoteException {
		super();
		this.clients = new ArrayList<>();
		this.profs = new ArrayList<>();
	}
	
//pour le login 
	@Override
	public String login(String username,String psswd) throws Exception {
		Connection cnx;
		Statement st;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();
		String sql2="SELECT * FROM `utilisateur` WHERE `username`='"+username+"' and `password`='"+psswd+"'";
		ResultSet result = st.executeQuery(sql2);
//on verifier si le client est un utilisateur dans notre base de données ou non 		
		if(result.next()) {
			System.out.println("welcome ");
//			Client c=  new Client(username,psswd);
//			clients.add(c);
			return result.getString("fonction");
		} else {
			System.out.println(" ******************* vous n'etes pas un utilisateur ******************* ");
		return "vous n'etes pas un utilisateur";}
			
		
	}
	//pour envoyer un message
	@Override
	public String sendMsg(String message,ArrayList<String> list,String username ,String type ) throws Exception {
	
		for (Iclient client : clients) {
            for(int i=0;i<list.size();i++){
                if(client.getUsername().equals(list.get(i))){
                	System.out.println("from the server :"+message);
                    client.receiveMsg(message,username,type);
                }//----------------------------------------------------------------------------------
            }
        }
		return "from the server "+message;
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		LocateRegistry.createRegistry(1099);
		Server od =new Server();
		Naming.rebind("rmi://localhost/irisi", od);//or irisi blasst lurl
	}
//	@Override
//	public String sendmsg(String message) throws RemoteException {
////		
//		return message;
//	}
	//pour la liste des utilisateur connectes
	@Override 
	public  ArrayList<String> listConnectedClient() throws Exception {
		ArrayList<String> list= new ArrayList<String>() ;
		for (Iclient c: Server.clients) { 
			list.add(c.getUsername());
		System.out.println(c.getUsername());}
		return list;
	}
	//pour ajouter un client
	@Override
	public void addclient(Iclient c) throws Exception {
		System.out.println("stocker le client dans la list cote server");

				clients.add(c);
	} 
	//pour ajouter un prof
	@Override
	public void addprof(Iprof prof) throws Exception {
		profs.add(prof);
		
	}
//pour un broadcast  (dans une meet => envoyer les fichiers vers tous les personnes qui participe e cette classe )
	@Override
	public void broadcastMessage(ArrayList<Integer> inc, List<String> list, String filename ,String type) throws Exception {
		for (Iclient client : clients) {
            for(int i=0;i<list.size();i++){
                if(client.getUsername().equals(list.get(i))){
//                	System.out.println("from the server :"+message);
                	System.out.println("retour au clien (avant )");
                    client.receiveFile(inc,filename,type);
                }//----------------------------------------------------------------------------------
            }
        }
		
	}


//	@Override
//	public String signUpStudent(String username, String nom, String prenom, String psswd) throws Exception {
//		String reponse=signUp(username,nom,prenom,psswd,"etudiant");
//		return reponse;
//		
//	}
	
//	public String signUpProf(String username, String nom, String prenom, String psswd) throws Exception {
//		String reponse=signUp(username,nom,prenom,psswd,"prof");
//		return reponse;
//	}
	//pour lenregistrement d'un nouveau utilisateur
	@Override
	public String signUp(String username, String nom, String prenom, String psswd,String fonction ) throws Exception {
		String reponse;
		Connection cnx;
		Statement st,st1;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();st1 = cnx.createStatement();
//		String sql;
		String sql="INSERT INTO `utilisateur`(`nom`, `prenom`, `username`, `fonction`, `password`,`id_class`) VALUES ('"+nom+"','"+prenom+"','"+username+"','"+fonction+"','"+psswd+"',"+0+")";
		String sql2="SELECT * FROM `utilisateur` WHERE `nom`='"+nom+"' and `prenom`='"+prenom+"' and `username`='"+username+"'";
		ResultSet result = st.executeQuery(sql2);
//vérifier si le client ext déja existe , sinon on lui crée		
		if(! result.next()) {
//			buffer=("r "+"user bien inserer ").getBytes();
			reponse="user bien inserer";
			System.out.println("user bien inserer");
				st1.execute(sql);
				cnx.close();
				 
		} else {
//			buffer=("r "+"* cet utilisateur est deja existe *").getBytes();
			System.out.println("*************** cet utilisateur est deja existe ***************");
			reponse="cet utilisateur est deja existe";
		}
		return reponse;
		
	}


	

//pour la creation d'une classe
	@Override
	public String createClass(String className, ArrayList<String> listStudents,String profName) throws Exception {
		String reponse="";
		Connection cnx;
		Statement st,st1,st2;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();st1 = cnx.createStatement();st2 = cnx.createStatement();
		 
		 String sql2="SELECT * FROM `class` WHERE `nom` ='"+className+"'";
			ResultSet result = st.executeQuery(sql2);
	//si le groupe n'existe pas on lui crée 		
			if(! result.next()) {
				System.out.println("en train de creer la classe");
			String sql="INSERT INTO `class` ( `nom`, `prof`) VALUES ('"+className+"','"+profName+"')";
			st1.execute(sql);
		    result = st.executeQuery(sql2);
		    if(result.next()) {
		    	
		    	System.out.println("update clients :"+result.getInt("id"));
		    	for(String username :listStudents ) {
		    		String sql3="SELECT * FROM `utilisateur` WHERE `username`='"+username+"'";
		    		ResultSet result1 = st2.executeQuery(sql3);
		    		result1.next();
		    		if(result1.getInt("id") == 0) {
		    			System.out.println("id 0 = update");
		    			String sql1="UPDATE `utilisateur` SET `id_class`="+result.getInt("id")+" WHERE `username`='"+username+"'";
		    			st1.execute(sql1);}
//		    		System.out.println(username);
		    		else {
		    			System.out.println("ajouter a nouv ");
		    			String sql4="INSERT INTO `utilisateur`(`nom`, `prenom`, `username`, `fonction`, `password`,`id_class`) VALUES ('"+result1.getString("nom")+"','"+result1.getString("prenom")+"','"+result1.getString("username")+"','"+result1.getString("fonction")+"','"+result1.getString("password")+"',"+result.getInt("id")+")";
		    			st1.execute(sql4);
		    		}
		    	reponse="success";
		    	}
		    }

		}else {
			System.out.println("classe deja cree");
			reponse="classe deja cree";
		}
			return reponse;
			
		}

//la liste de tous les etudients
	@Override
	public ArrayList<String> listAllStudents() throws Exception {
		ArrayList<String> list= new ArrayList<String>();
		Connection cnx;
		Statement st,st1;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();
		 String sql="SELECT  distinct `username` FROM `utilisateur` WHERE `fonction`='student'";
		 ResultSet result = st.executeQuery(sql);
		 while(result.next()) {
			 list.add(result.getString("username"));
		 }
		 for(String l:list) System.out.println(l);
		 return list;
	}


//la liste des classes
	@Override
	public ArrayList<String> listClass(String username) throws Exception {
		ArrayList<String> list= new ArrayList<String>();
		Connection cnx;
		Statement st;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();
		 String sql="SELECT  distinct `nom` FROM `class` WHERE `prof`='"+username+"'";
		 ResultSet result = st.executeQuery(sql);
		 while(result.next()) {
			 list.add(result.getString("nom"));
		 }
		 for(String l:list) System.out.println(l);
		 return list;
	}

//pour supprimer une classe
	@Override
	public String deleteClass(ArrayList<String> selected) throws Exception {
		String response="";
		Connection cnx;
		Statement st,st1,st2;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();st1 = cnx.createStatement();st2 = cnx.createStatement();
		 for (String className: selected) { 
		 String sql1="SELECT * FROM `class` WHERE `nom` ='"+className+"'";
		 ResultSet result = st.executeQuery(sql1);
		 if(result.next()) {
		 String sql2="SELECT  * FROM `utilisateur` WHERE `id_class`="+result.getInt("id");
		 ResultSet result1 = st1.executeQuery(sql2);
		 result1.next();
		 String sql3="UPDATE `utilisateur` SET `id_class`="+0+" WHERE `username`='"+result1.getString("username")+"'";
		 st2.execute(sql3);
		 String sql = "DELETE FROM `class` WHERE `nom`='"+className+"'";
		 st.execute(sql);
		 response="success";
		 }
		 }
		return response;
	}

//liste des etudients d'une classe
	@Override
	public ArrayList<String> listStudentsOftheClass(String className) throws Exception {
		ArrayList<String> list= new ArrayList<String>();
		Connection cnx;
		Statement st,st1;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();st1 = cnx.createStatement();
		 String sql1="SELECT * FROM `class` WHERE `nom` ='"+className+"'";
		 ResultSet result = st.executeQuery(sql1);
		 if(result.next()) {
		 String sql2="SELECT distinct `username` FROM `utilisateur` WHERE `id_class`="+result.getInt("id");
		 ResultSet result1 = st1.executeQuery(sql2);
//		 result1.next();
		 while(result1.next()) {
			 list.add(result1.getString("username"));
		 }
		 }
		return list;
	}

// liste des etudients connectes
	@Override
	public ArrayList<String> listConnectedStudent(String username) throws Exception {
		//listStudentsOftheClass
		ArrayList<String> list= new ArrayList<String>();
		ArrayList<String> list1;
		Connection cnx;
		Statement st,st1;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();st1 = cnx.createStatement();
		 String sql1="SELECT * FROM `class` WHERE `prof` ='"+username+"'";
		 ResultSet result = st.executeQuery(sql1);
		 while(result.next()) {
			 list1=listStudentsOftheClass(result.getString("nom"));
			 
			 for (Iclient client : clients) {
		            for(int i=0;i<list1.size();i++){
		                if(client.getUsername().equals(list1.get(i))){
			             list.add(client.getUsername());
		                }
		            }
			 }
		 }
		return list;
	}


//	@Override
//	public void ajouterprof(Iprof prof) throws Exception {
//		System.out.println("prof cote server "+prof);
//		
//	}

//pour dessiner sur le tableau blanc
	@Override
	public void tableaublanc(ArrayList<String> listStudents, int xmousePressed, int ymousePressed, int xmouseReleased,
			int ymouseReleased) throws Exception {
		
		for (Iclient client : clients) {
            for(int i=0;i<listStudents.size();i++){
                if(client.getUsername().equals(listStudents.get(i))){
                	client.tableaublanc(xmousePressed, ymousePressed, xmouseReleased, ymouseReleased);
                }
            }
        }

	}

//pour ouvrir la page du tableau blan
	@Override
	public void openTableaublanc(ArrayList<String> listStudents) throws Exception {
		for (Iclient client : clients) {
            for(int i=0;i<listStudents.size();i++){
                if(client.getUsername().equals(listStudents.get(i))){
                	client.openTableaublanc();
                }
            }
        }
		
	}

//pour supprimer un etudient
	@Override
	public String deleteStudent(String name) throws Exception {
		String response="";
		Connection cnx;
		Statement st;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();
//		 for (String username: selected) { 
			 String sql = "DELETE FROM `utilisateur` WHERE `username`='"+name+"'";
			 st.execute(sql);
			 response="success";
			 
//		 }
		return response;
	}

//pour envoyer un brodcast (dans une meet vers tous les membres de cette classe)
	@Override
	public void sendMsgbrodcast(String text, String username, String className,String type) throws Exception {
		ArrayList<String> list= new ArrayList<String>();
		list=listStudentsOftheClass(className);
		
		for (Iclient client : clients) {
            for(int i=0;i<list.size();i++){
                if(client.getUsername().equals(list.get(i))){
//                	System.out.println("from the server :"+message);
                    client.receiveMsg(text,username,type);
                }//----------------------------------------------------------------------------------
            }
        }
		
	}

//recuperer les classes des utilisateurs
	@Override
	public ArrayList<String> listClassUser(String username) throws Exception {
		ArrayList<String> list= new ArrayList<String>();
		Connection cnx;
		Statement st,st1;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearning", "root", "");
		 st = cnx.createStatement();st1 = cnx.createStatement();
		 String sql = "SELECT * FROM `utilisateur` WHERE `username`='"+username+"'";
		 ResultSet result = st.executeQuery(sql);
		
		 while(result.next()) {
			 System.out.println("get classs :"+username+"id"+result.getInt("id_class"));

			 String sql1="SELECT * FROM `class` WHERE `id`="+result.getInt("id_class");
			 System.out.println();
			 ResultSet result1 = st1.executeQuery(sql1);
			 while(result1.next()) {
				 list.add(result1.getString("nom"));
			 }
		 }
		return list;
	}


	@Override
	public void logout(String username) throws Exception {
		for (int i=0;i<clients.size();i++) {
                if(username.equals(clients.get(i))) {
                	clients.remove(i);
                }
                	
		}
        
		
	}


	
	
	
}
