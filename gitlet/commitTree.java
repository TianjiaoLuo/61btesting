package gitlet;
import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class commitTree implements Serializable {
    public HashMap<String, ArrayList<Node>> branchMap;
    public HashMap<String, String> stagingArea;
    public HashMap<String, Node> bracntoLastNode;
    public HashSet<String> removedFiles;
    public HashSet<String> modifiednotaddFiles;
    public Node previousCommit;
    public String currentBranch;
    public HashMap<String, Node> idtoNode;

    public commitTree() {
        branchMap = new HashMap<>();
        stagingArea = new HashMap<>();
        removedFiles = new HashSet<>();
        modifiednotaddFiles = new HashSet<>();
        bracntoLastNode = new HashMap<>();
        previousCommit = null;
        currentBranch = "";
        idtoNode = new HashMap<>();
    }


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
            (new File(".gitlet/commits")).mkdir();
            serialize(previousCommit, ".gitlet/commits/" + previousCommit.hashcode());
        }
    }

    /* We searched for the serialize method online */
    public void serialize(Object object, String path) {
        File file = new File(".gitlet");
        File outFile = new File(file, path + ".ser");
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(outFile));
            out.writeObject(object);
            out.close();
        } catch (IOException e) {
            return;
        }
    }
}
