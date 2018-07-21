package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class initCommand extends commitTree implements Serializable {
    public void init() {
        File file = new File(".gitlet");
        if (file.exists()) {
            System.out.println("A gitlet version-control system already exists in the current directory.");
            return;
        } else {
            file.mkdir();
            previousCommit = new Node("master", "initial commit");
            currentBranch = previousCommit.currentBranch;
            branchMap.put("master", new ArrayList<>());
            idtoNode.put(previousCommit.hashcode(), previousCommit);
            branchMap.get("master").add(previousCommit);
            (new File(".gitlet")).mkdir();
            (new File(".gitlet/commitFiles")).mkdir();
            serialize(previousCommit, ".gitlet/commitFiles/" + previousCommit.hashcode());
        }
    }
}
