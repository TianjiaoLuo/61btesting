package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public class statusCommand extends commitTree implements Serializable{

	
	public void status() {
		 System.out.println("=== Branches ===");
        
        Set<String> set1=bracntoLastNode.keySet();
        Iterator it1= set1.iterator();
         	while(it1.hasNext()) {
         		String s=(String)it1.next();
         		if (!currentBranch.equals(s)) {
         			 System.out.println("*" + s);
                 }
         	}
        
       Set<String> set=bracntoLastNode.keySet();
       Iterator it= set.iterator();
        	while(it.hasNext()) {
        		String s=(String)it.next();
        		if (!currentBranch.equals(s)) {
        			 System.out.println(s);
                }
        	}

        System.out.println();
        System.out.println("=== Staged Files ===");
        
        Set<String> stagSet=stagingArea.keySet();
        Iterator stagIt= set.iterator();
    	while(stagIt.hasNext()) {
    		String s=(String)stagIt.next();
    		 System.out.println(s);
    	}
        
        System.out.println();
        System.out.println("=== Removed Files ===");
        Set<String> remSet=stagingArea.keySet();
        Iterator remIt= set.iterator();
    	while(remIt.hasNext()) {
    		String s=(String)remIt.next();
    		 System.out.println(s);
    	}
        
    	 System.out.println();
        updateModifForNode();
        System.out.println("=== Modifications Not Staged For Commit ===");
        Set<String> modifSet=stagingArea.keySet();
        Iterator modifIt=modifSet.iterator();
        while(modifIt.hasNext()) {
        	String s=(String)remIt.next();
        	File f=new File(s);
        	if(f.exists()) {
        		 System.out.println(s + " (modified)");
        	}else {
        		 System.out.println(s + " (deleted)");
        	}
        }
        
        System.out.println();
        System.out.println("=== Untracked Files ===");
        for (String s : Utils.plainFilenamesIn(".")) {
        	String hashCode=previousCommit.hashcode();
        	boolean cMf=checkModiFun(previousCommit,s,hashCode);
            if (!stagingArea.containsKey(s) && !removedFiles.contains(s) && !modifiednotaddFiles.contains(s) && cMf) {
            	 System.out.println(s);
            }
        }
        
        System.out.println();
    }
	
	private void updateModifForNode() {
		for (String s : Utils.plainFilenamesIn(".")) {
			String hashCode = Utils.sha1(previousCommit.time, previousCommit.commitMessage, currentBranch);
            if (previousCommit.fileseachCommit.containsKey(s) && checkModiFun(previousCommit,s,hashCode) && !stagingArea.containsKey(s)) {
            	modifiednotaddFiles.add(s);
            }
        }

      Set<String> stagSet=  stagingArea.keySet();
      Iterator stagIt=stagSet.iterator();
      while(stagIt.hasNext()) {
      	String s=(String)stagIt.next();
      	String hashCode = Utils.sha1(previousCommit.time, previousCommit.commitMessage, currentBranch);
      	if (checkModiFun(previousCommit,s, hashCode) || !Utils.plainFilenamesIn(".").contains(s)) {
        	modifiednotaddFiles.add(s);
        }
      }
      
      
      Set<String> fileSet=  previousCommit.fileseachCommit.keySet();
      Iterator fileIt=fileSet.iterator();
      while(fileIt.hasNext()) {
      	String s=(String)fileIt.next();
      	if (!removedFiles.contains(s) && (!Utils.plainFilenamesIn(".").contains(s))) {
        	modifiednotaddFiles.add(s);
        }
      }
	}
	
	
	private boolean checkModiFun(Node node,String name, String hashCode) {
	        if (hashCode == null) {
	            return true;
	        }
	        File file = new File(name);
	        byte[] arr = Utils.readContents(file);
	        String s = Utils.sha1(node.time, node.commitMessage, currentBranch);
	        return (!s.equals(hashCode));
	    }
}
