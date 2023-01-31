package prof;

import java.io.File;
import java.util.ArrayList;

public interface Iprof {
	public void addprof(Iprof prof) throws Exception;
	public String signUp(String username,String nom,String prenom,String psswd,String fonction) throws Exception;
	public String login(String username,String psswd) throws Exception;
	public String addClass(String string, ArrayList<String> selected,String profName) throws Exception;
	public ArrayList<String> listAllStudents(String username2)throws Exception;
	public ArrayList<String> listClass(String username) throws Exception;
	public String deleteClass(ArrayList<String> selected)  throws  Exception;
	public ArrayList<String> listStudentsOftheClass(String className) throws  Exception;
	public ArrayList<String> listConnectedStudent(String username) throws  Exception;
	public void sendFile(File file, ArrayList<String> list, String type);
	public void sendMsg(String message, ArrayList<String> selected, String username, String type) throws Exception;
	public void tableaublanc(ArrayList<String> listStudents, int xmousePressed, int ymousePressed, int xmouseReleased, int ymouseReleased) throws Exception;
	public void openTableaublanc(ArrayList<String> listStudents) throws Exception;
	public String deleteStudent(String name) throws Exception;
	public void sendMsgbrodcast(String text, String username, String className,String type) throws Exception;
	
}
