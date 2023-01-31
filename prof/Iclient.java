package prof;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import etudiant.Client;

public interface Iclient extends Remote {
	public void sendMsg(String message ,ArrayList<String> list,String username) throws Exception;
	public void receiveMsg(String message) throws RemoteException;
	public String login(String username,String psswd) throws Exception;
	public void setUsername(String username)throws Exception;
	public String getUsername()throws Exception;
}
