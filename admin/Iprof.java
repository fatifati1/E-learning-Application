package admin;

import java.util.ArrayList;

public interface Iprof {
	public void addprof(Iprof prof) throws Exception;
	public String signUp(String username,String nom,String prenom,String psswd,String fonction) throws Exception;
	public String login(String username,String psswd) throws Exception;
	public String addClass(String string, ArrayList<String> selected,String profName) throws Exception;
	public ArrayList<String> listAllStudents(String username2)throws Exception;
	
}
