package prof;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import etudiant.Client;
import etudiant.Iclient;
import prof.Iprof;

public interface Iserver extends Remote {
	public String login(String username,String psswd)  throws  Exception ;
	public String sendMsg(String message,ArrayList<String> list,String username, String type ) throws  Exception;
	public ArrayList<String> listConnectedClient() throws  Exception;
	public void addclient(Iclient c) throws Exception ;
	public void broadcastMessage(ArrayList<Integer> inc, List<String> list,String filename, String type)throws  Exception;
	public String signUp(String username, String nom, String prenom, String psswd, String fonction) throws Exception;
	public void addprof(Iprof prof)throws Exception;
	public String createClass(String className, ArrayList<String> listStudents, String profName) throws Exception;
	public ArrayList<String> listAllStudents() throws Exception ;
	public ArrayList<String> listClass(String username) throws  Exception;
	public String deleteClass(ArrayList<String> selected)  throws  Exception;
	public ArrayList<String> listStudentsOftheClass(String className) throws  Exception;
	public ArrayList<String> listConnectedStudent(String username) throws  Exception;
//	public void ajouterprof(Iprof prof) throws  Exception;
	public void tableaublanc(ArrayList<String> listStudents, int xmousePressed, int ymousePressed, int xmouseReleased, int ymouseReleased) throws  Exception;
	public void openTableaublanc(ArrayList<String> listStudents) throws Exception;
	public String deleteStudent(String name) throws Exception;
	public void sendMsgbrodcast(String text, String username, String className,String type) throws Exception;
	public ArrayList<String> listClassUser(String username)  throws Exception;
	public void logout(String username) throws Exception;
}
