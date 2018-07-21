package gitlet;

import java.io.Serializable;
import java.io.File;

public class commitCommand extends commitTree implements Serializable {
    public void commit(String commitMessage) {
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
                File file = new File(".gitlet/stagedFiles/" + stagingArea.get(fileName));
                byte[] content = Utils.readContents(file);
                Utils.writeContents(newFile, content);
                previousCommit.fileseachCommit.put(fileName, stagingArea.get(fileName));
                (new File(".gitlet/stagedFiles/" + stagingArea.get(fileName))).delete();
            } else {
                previousCommit.fileseachCommit.put(fileName, stagingArea.get(fileName));
                (new File(".gitlet/stagedFiles/" + stagingArea.get(fileName))).delete();
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
}
