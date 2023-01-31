package etudiant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface Iclient extends Remote {
//	public void sendMsg(String message ,ArrayList<String> list,String username) throws  Exception;
	public void receiveMsg(String message, String username, String type) throws RemoteException;
	public String login(String username,String psswd) throws Exception;
	public void setUsername(String username)throws Exception;
	public String getUsername()throws Exception;
	public void receiveFile(ArrayList<Integer> inc, String filename,String type) throws Exception;
	public void tableaublanc(int xmousePressed, int ymousePressed, int xmouseReleased, int ymouseReleased) throws Exception;
	public void openTableaublanc() throws Exception;
	void sendMsg(String message, ArrayList<String> list, String username, String type) throws Exception;
}
