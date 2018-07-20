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

    public void add(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        if (file.isDirectory()) {
            return;
        }
        if (removedFiles.contains(path)) {
            removedFiles.remove(path);
        } else {
            String hashcode = Utils.sha1(Utils.readContents(file));
            String fileID = previousCommit.fileseachCommit.get(path);
            if (previousCommit.fileseachCommit.containsKey(path) && (hashcode.equals(fileID))) {
                return;
            } else {
                new File(".gitlet/stagingArea").mkdir();
                String id = Utils.sha1(Utils.readContents(file));
                File newFile = new File(".gitlet/stagingArea/" + id);
                Utils.writeContents(newFile, Utils.readContents(file));
                stagingArea.put(path, id);
            }
        }
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

    public void branch(String branchName) {
        if (branchMap.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
            return;
        } else {
            branchMap.put(branchName, new ArrayList<>());
            for (Node eachNode : branchMap.get(currentBranch)) {
                branchMap.get(branchName).add(eachNode);
            }
        }
    }

    public void rmbranch(String branchName) {
        if (!branchMap.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
            return;
        } else if (branchName.equals(currentBranch)) {
            System.out.println("Cannot remove the current branch.");
            return;
        } else {
            branchMap.remove(branchName);
        }
    }
}
