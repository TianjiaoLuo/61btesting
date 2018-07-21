package gitlet;
import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
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

    public static commitTree deserialize(String path) {
        commitTree object = null;
        File file = new File(".gitlet");
        File inFile = new File(file, path + ".ser");
        try {
            ObjectInputStream inp = new ObjectInputStream(new FileInputStream(
                    inFile));
            object = (commitTree) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            object = null;
        }
        return object;
    }

}
