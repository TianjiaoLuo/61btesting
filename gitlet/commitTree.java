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
	
    public static HashMap<String, ArrayList<Node>> branchMap=new HashMap<>();
    public static HashMap<String, String> stagingArea= new HashMap<>();
    public static HashMap<String, Node> bracntoLastNode= new HashMap<>();
    public static HashSet<String> removedFiles= new HashSet<>();
    public static HashSet<String> modifiednotaddFiles= new HashSet<>();
    public static Node previousCommit;
    public static String currentBranch="";
    public static HashMap<String, Node> idtoNode=new HashMap<>();;

    public commitTree() {
    }


    public void serialize(Object object, String path) {
        File file = new File(".gitlet");
        File outFile = new File(file, path + ".ser");
        try {
        	if(!outFile.exists()) {
        		outFile.createNewFile();
        	}
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
        	if(!inFile.exists()) {
        		inFile.createNewFile();
        	}
            ObjectInputStream inp = new ObjectInputStream(new FileInputStream(inFile));
            object = (commitTree) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            object = null;
        }
        return object;
    }

    public Node PreviousNode(String branchName) {
        ArrayList<Node> NodeList = branchMap.get(branchName);
        Node previous = NodeList.get(NodeList.size() - 1);
        return previous;
    }

}
