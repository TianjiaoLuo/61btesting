package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public class commitCommand extends commitTree implements Serializable {
	
	
    public  void commit(String commitMessage) {
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
        
       Set<String> stagSet= stagingArea.keySet();
        Iterator stagIt= stagSet.iterator();
        while(stagIt.hasNext()) {
          String fileName=(String)stagIt.next();
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
       Set<String> preSet= previousCommit.parent.fileseachCommit.keySet();
       Iterator preIt=preSet.iterator(); 
       while(preIt.hasNext()) {
    	   String fName= (String)preIt.next();
    	   if (!removedFiles.contains(fName) && !stagingArea.containsKey(fName)) {
               previousCommit.fileseachCommit.put(fName, previousCommit.parent.fileseachCommit.get(fName));
           }
       }
       
        branchMap.get(currentBranch).add(previousCommit);
        stagingArea.clear();
        removedFiles.clear();
        idtoNode.put(previousCommit.hashcode(), previousCommit);
    }
}
