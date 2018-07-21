package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class resetCommand extends commitTree implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	public void reset(String hashCode) {
		if (!idtoNode.containsKey(hashCode)) {
            System.out.println("No commit with that id exists.");
            return;
        }

        for (String s : Utils.plainFilenamesIn(".")) {

        	String hCode=previousCommit.fileseachCommit.get(s);
            if (checkModiFun(previousCommit,s,hCode) && (checkModiFun(previousCommit,s, stagingArea.get(s)))) {
            	 System.out.println("There is an untracked file in the way; delete it or add it first.");
                return;
            }
        }
        Set<String> idtoNodeSet=  idtoNode.get(hashCode).fileseachCommit.keySet();
       Iterator it=idtoNodeSet.iterator();
        while(it.hasNext()) {
            String fileName=(String)it.next();
            File file = new File(".gitlet/blobs/" + idtoNode.get(hashCode).fileseachCommit.get(fileName));
            byte[] content = Utils.readContents(file);
            File newFile = new File(fileName);
            Utils.writeContents(newFile, content);
        }
        
        Set<String> preSet= previousCommit.fileseachCommit.keySet();
        Iterator preIt=preSet.iterator();
        while(preIt.hasNext()) {
        	String s=(String)preIt.next();
        	 if (previousCommit.fileseachCommit.containsKey(s) && !idtoNode.get(hashCode).fileseachCommit.containsKey(s)) {
                 File file = new File(s);
                 Utils.restrictedDelete(file);
             }
        }
        

        previousCommit = idtoNode.get(hashCode);
        currentBranch = previousCommit.currentBranch;
        bracntoLastNode.put(currentBranch, previousCommit);

        stagingArea.clear();
        for (String id : stagingArea.values()) {
            File file = new File(".gitlet/stagingArea/" + id);
            file.delete();
        }
        removedFiles.clear();

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
