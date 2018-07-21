package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class checkoutCommand extends commitTree implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	public void checkoutCommit(String hoshCode, String fileName) {
		  if (idtoNode.containsKey(hoshCode)) {
	            if (!idtoNode.get(hoshCode).fileseachCommit.containsKey(fileName)) {
	            	 System.out.println("File does not exist in that commit.");
	                return;
	            } else {
	                File file = new File(".gitlet/blobs/" + idtoNode.get(hoshCode).fileseachCommit.get(fileName));
	                byte[] content = Utils.readContents(file);
	                File newFile = new File(fileName);
	                Utils.writeContents(newFile, content);
	            }
	        } else {
	        	 System.out.println("No commit with that id exists.");
	        }
	}
	
	public void  checkoutBranch(String branchName) {
		if (!bracntoLastNode.containsKey(branchName)) {
			 System.out.println("No such branch exists.");
            return;
        }

        if (currentBranch.equals(branchName)) {
        	 System.out.println("No need to checkout the current branch.");
            return;
        }

        Node lastCurrentBranchNode = getLastNode(currentBranch);
        Node lastGivenBranchNode = getLastNode(branchName);
        
        Set<String> lasGB =lastGivenBranchNode.fileseachCommit.keySet();
          Iterator lasGBIt=lasGB.iterator();
          while(lasGBIt.hasNext()) {
        	 String s= (String)lasGBIt.next();
        	 boolean  cMf= checkModiFun(lastGivenBranchNode,s,lastGivenBranchNode.hashcode());
             if (!lastCurrentBranchNode.fileseachCommit.containsKey(s)&& Utils.plainFilenamesIn(".").contains(s) && cMf) {
            	 System.out.println("There is an untracked file in the way; delete it or add it first.");
                 return;
             }
          }
        
        Set<String> lastNodeSet= getLastNode(branchName).fileseachCommit.keySet();
           Iterator lasNodeIt= lastNodeSet.iterator();
             while(lasNodeIt.hasNext()) {
            	 String s=(String)lasNodeIt.next();
            	 File file = new File(".gitlet/blobs/" + getLastNode(branchName).fileseachCommit.get(s));
                 byte[] content = Utils.readContents(file);
                 File newFile = new File(s);
                 Utils.writeContents(newFile, content);
            }
        Set<String> lastBranchNodeSet=getLastNode(currentBranch).fileseachCommit.keySet();
        Iterator  lastBranchNodeIt=lastBranchNodeSet.iterator();
              while(lastBranchNodeIt.hasNext()) {
            	  String s=(String)lastBranchNodeIt.next();
            	  if (!getLastNode(branchName).fileseachCommit.containsKey(s)) {
                      Utils.restrictedDelete(new File(s));
                  }
              }
        
        
        if (!bracntoLastNode.containsKey(branchName)) {
        	previousCommit = getLastNode(branchName);
        } else {
        	previousCommit = bracntoLastNode.get(branchName);
        }

        currentBranch = branchName;
        stagingArea.clear();
        for (String hashCode : stagingArea.values()) {
            File file = new File(".gitlet/stagingArea/" + hashCode);
            file.delete();
        }
        removedFiles.clear();
	}
	
	private Node getLastNode(String branchName) {
	        ArrayList<Node> arr = branchMap.get(branchName);
	        Node node = arr.get(arr.size() - 1);
	        return node;
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
	
	public void checkoutFile(String fileName) {
	        if (!previousCommit.fileseachCommit.containsKey(fileName)) {
	            System.out.println("File does not exist in that commit.");
	            return;
	        }
	        File file = new File(".gitlet/blobs/" + previousCommit.fileseachCommit.get(fileName));
	        byte[] content = Utils.readContents(file);
	        File newFile = new File(fileName);
	        Utils.writeContents(newFile, content);
	    }

}
