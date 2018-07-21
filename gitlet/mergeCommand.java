package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class mergeCommand extends commitTree implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	public void merge(String branchName) {
		
		 if (!stagingArea.isEmpty() || !removedFiles.isEmpty()) {
	            System.out.println("You have uncommitted changes.");
	            return;
	        }
	        if (!branchMap.containsKey(branchName)) {
	            System.out.println("A branch with that name does not exist.");
	            return;
	        }
	        if (branchName.equals(currentBranch)) {
	            System.out.println("Cannot merge a branch with itself");
	            return;
	        }
	        Node sPoint = getSplitPoint(branchName, currentBranch);
	        Node lastCurrentBranchCommit = getLastNode(currentBranch);
	        Node lastGivenBranchCommit = getLastNode(branchName);
	        boolean b = false;
	        for (String s : lastGivenBranchCommit.fileseachCommit.keySet()) {
	        	boolean cMF=checkModiFun(lastGivenBranchCommit,s,lastGivenBranchCommit.hashcode());
	            if (!lastCurrentBranchCommit.fileseachCommit.containsKey(s) && Utils.plainFilenamesIn(".").contains(s) && cMF) {
	                System.out.println("There is an untracked] file in the way; delete it or add it first.");
	                return;
	            }
	        }
	        if (sPoint.equals(lastGivenBranchCommit)) {
	            System.out.println("Given branch is an ancestor of the current branch.");
	            return;
	        }
	        if (sPoint.equals(lastCurrentBranchCommit)) {
	            for (String fileName : lastGivenBranchCommit.fileseachCommit.keySet()) {
	            	String hCode=lastGivenBranchCommit.fileseachCommit.get(fileName);
	            	
	                File file = new File(".gitlet/blobs/" + hCode);
	                byte[] content = Utils.readContents(file);
	                File newFile = new File(fileName);
	                Utils.writeContents(newFile, content);
	            }
	            int s1 = branchMap.get(currentBranch).size();
	            int s2 = branchMap.get(branchName).size();
	            for (int i = s1; i < s2; i++) {
	            	branchMap.get(currentBranch).add(branchMap.get(branchName).get(i));
	            }
	            previousCommit = lastGivenBranchCommit;
	            System.out.println("Current branch fast-forwarded.");
	            return;
	        }
	        Boolean noConflict = false;
	        HashSet<Node> commitSet = new HashSet<>();
	        commitSet.add(sPoint);
	        commitSet.add(lastCurrentBranchCommit);
	        commitSet.add(lastGivenBranchCommit);
	        HashSet<String> nameSet = new HashSet<>();
	        for (Node c : commitSet) {
	            for (String s : c.fileseachCommit.keySet()) {
	                if (!nameSet.contains(s)) {
	                    nameSet.add(s);
	                }
	            }
	        }

	        for (String fileName : nameSet) {
	            noConflict = false;
	            String sID =  sPoint.hashcode();
	            String branchID = lastGivenBranchCommit.hashcode();
	            String currentID = sPoint.hashcode();
	            
	            if (sPoint.fileseachCommit.containsKey(fileName)  && branchID != null && currentID != null && !sID
	                                            .equals(branchID) && sID.equals(currentID)) {
	                File file = new File(".gitlet/blobs/" + branchID);
	                byte[] content = Utils.readContents(file);
	                File newFile = new File(fileName);
	                Utils.writeContents(newFile, content);
	                stage(lastGivenBranchCommit, fileName);
	                noConflict = true;
	            }
	            if (sID != null && branchID != null && currentID != null
	                            && !sID.equals(currentID) && sID.equals(branchID)) {
	                noConflict = true;
	            }
	            if (!sPoint.fileseachCommit.containsKey(fileName)
	                            && !lastGivenBranchCommit.fileseachCommit.containsKey(fileName)
	                            && lastCurrentBranchCommit.fileseachCommit.containsKey(fileName)) {
	                        noConflict = true;
	            }
	            if (!lastGivenBranchCommit.fileseachCommit.containsKey(fileName)
	                            && lastCurrentBranchCommit.fileseachCommit.containsKey(fileName)) {
	                noConflict = true;
	            }
	            if (!sPoint.fileseachCommit.containsKey(fileName)
	                            && !lastCurrentBranchCommit.fileseachCommit.containsKey(fileName)
	                            && lastGivenBranchCommit.fileseachCommit.containsKey(fileName)) {
	            	
	                File file = new File(".gitlet/blobs/" +lastGivenBranchCommit.hashcode());
	                byte[] content = Utils.readContents(file);
	                File newFile = new File(fileName);
	                Utils.writeContents(newFile, content);
	                stage(lastGivenBranchCommit, fileName);
	                noConflict = true;
	            }
	            if (sPoint.fileseachCommit.containsKey(fileName)) {
	                if (lastCurrentBranchCommit.fileseachCommit.containsKey(
	                                fileName) && !lastGivenBranchCommit.fileseachCommit.containsKey(fileName)) {
	                    rm(fileName);

	                    noConflict = true;
	                }
	            }
	            if (sPoint.fileseachCommit.containsKey(fileName)
	                            && currentID != null && sID.equals(currentID)
	                            && !lastGivenBranchCommit.fileseachCommit.containsKey(fileName)) {
	                rm(fileName);
	                noConflict = true;
	            }
	            if (sPoint.fileseachCommit.containsKey(fileName)
	                            && branchID != null && sID.equals(branchID)
	                            && !lastCurrentBranchCommit.fileseachCommit.containsKey(fileName)) {
	                noConflict = true;
	            }
	            if (!noConflict) {

	                b = true;
	                File file1 = new File(".gitlet/blobs/" + lastCurrentBranchCommit.hashcode());
	                byte[] content1 = Utils.readContents(file1);
	                File file2 = new File(".gitlet/blobs/" +lastGivenBranchCommit.hashcode());
	                byte[] content2 = Utils.readContents(file2);
	                File newFile = new File(fileName);

	                byte[] b1 = ("<<<<<<< HEAD" + "\n").getBytes();
	                byte[] b2 = ("=======" + "\n").getBytes();
	                byte[] b3 = (">>>>>>>" + "\n").getBytes();

	                byte[] content = new byte[b1.length + content1.length + b2.length + content2.length + b3.length];

	                System.arraycopy(b1, 0, content, 0, b1.length);
	                System.arraycopy(content1, 0, content, b1.length,
	                                content1.length);
	                System.arraycopy(b2, 0, content, b1.length + content1.length,b2.length);
	                System.arraycopy(content2, 0, content, b1.length
	                                + content1.length + b2.length, content2.length);
	                System.arraycopy(b3, 0, content, b1.length + content1.length + b2.length + content2.length, b3.length);
	                Utils.writeContents(newFile, content);

	            }
	        }
	        mergeCommit(b, lastCurrentBranchCommit, lastGivenBranchCommit);
		
	}
	
	
	 public void mergeCommit(boolean b, Node lastCurrentBranchCommit, Node lastGivenBranchCommit) {
		 String currentBranchName = lastCurrentBranchCommit.currentBranch;
		 String givenBranchName = lastGivenBranchCommit.currentBranch;
		 if (!b) {
		     commit("Merged " + currentBranchName + " with " + givenBranchName+ ".");
		 } else {
			 System.out.println("Encountered a merge conflict.");
		 }
    }
	 
	 private  void commit(String commitMessage) {
	        if (commitMessage == null) {
	            System.out.println("Please enter a commit message");
	            return;
	        }
	        if (stagingArea == null && removedFiles.isEmpty()) {
	            System.out.println("No changes added to the commit.");
	            return;
	        }
	        Node newNode = new Node(currentBranch, commitMessage);
	        newNode.parent = previousCommit;
	        previousCommit = newNode;
	        serialize(previousCommit, ".gitlet/commits/" + previousCommit.hashcode());
	        new File(".gitlet/blobs/").mkdir();
	        for (String fileName : stagingArea.keySet()) {
	            File newFile = new File(".gitlet/blobs/" + stagingArea.get(fileName));
	            if (!newFile.exists()) {
	                File file = new File(".gitlet/stagingArea/" + stagingArea.get(fileName));
	                byte[] content = Utils.readContents(file);
	                Utils.writeContents(newFile, content);
	                previousCommit.fileseachCommit.put(fileName, stagingArea.get(fileName));
	                (new File(".gitlet/stagingArea/" + stagingArea.get(fileName))).delete();
	            } else {
	                previousCommit.fileseachCommit.put(fileName, stagingArea.get(fileName));
	                (new File(".gitlet/stagingArea/" + stagingArea.get(fileName))).delete();
	            }
	        }
	        for (String fileName : previousCommit.parent.fileseachCommit.keySet()) {
	            if (!removedFiles.contains(fileName) && !stagingArea.containsKey(fileName)) {
	                previousCommit.fileseachCommit.put(fileName, previousCommit.parent.fileseachCommit.get(fileName));
	            }
	        }
	        branchMap.get(currentBranch).add(previousCommit);
	        stagingArea.clear();
	        removedFiles.clear();
	        idtoNode.put(previousCommit.hashcode(), previousCommit);
	    }

	 
	    public Node getSplitPoint(String branchName1, String branchName2) {
	        ArrayList<Node> lst1 = branchMap.get(branchName1);
	        ArrayList<Node> lst2 = branchMap.get(branchName2);

	        HashSet<String> set = new HashSet<>();
	        for (int i = 0; i < lst1.size(); i++) {
	            set.add(lst1.get(i).hashcode());
	        }

	        for (int j = lst2.size() - 1; j >= 0; j--) {
	            if (set.contains(lst2.get(j).hashcode())) {
	                return lst2.get(j);
	            }
	        }
	        return null;
	    }
	    
	    public Node getLastNode(String branchName) {
	        ArrayList<Node> arr = branchMap.get(branchName);
	        Node c = arr.get(arr.size() - 1);
	        return c;
	    }
	
	    public void stage(Node node, String fileName) {
	        String hashCode = node.hashcode();
	        stagingArea.put(fileName, hashCode);
	        File file = new File(".gitlet/blobs/" + hashCode);
	        byte[] content = Utils.readContents(file);
	        File newFile = new File(".gitlet/stagingArea/" +hashCode);
	        Utils.writeContents(newFile, content);
	    }
	    
	    public void rm(String fileName) {
	        if (!stagingArea.containsKey(fileName) && !previousCommit.fileseachCommit.containsKey(fileName)) {
	            System.out.println("No reason to remove the file.");
	            return;
	        }
	        if (previousCommit.fileseachCommit.containsKey(fileName)) {
	            File f = new File(fileName);
	            f.delete();
	            removedFiles.add(fileName);
	        } else {
	            stagingArea.remove(fileName);
	            File file = new File(".gitlet/stagingArea" + stagingArea.get(fileName));
	            file.delete();
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
