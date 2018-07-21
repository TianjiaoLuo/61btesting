package gitlet;

import java.io.Serializable;
import java.util.HashMap;

public class findCommand extends commitTree implements Serializable{

	
	public void find(String commitMessage) {
		 Boolean error = true;
		   if(previousCommit !=null) {
			HashMap<String,String> fileMap = previousCommit.fileseachCommit;
			 if(commitMessage.equals(previousCommit.commitMessage)) {
				 String hasCode=previousCommit.hashcode();
				 System.out.println(hasCode);
				 error=false;
			 }
			 
		   }
		   
		   if (error) {
	        	 System.out.println("Found no commit with that message.");
	        }
	       
		
	}
	

	
}
