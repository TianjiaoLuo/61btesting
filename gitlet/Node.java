package gitlet;


import java.io.Serializable;
import java.util.HashMap;


public class Node implements Serializable{
    public Node parent;
    public String currentBranch;
    public String time;
    public String commitMessage;
    public HashMap<String, String> fileseachCommit;


    public Node() {
        this.commitMessage = "";
        this.time = Utils.date();
        this.currentBranch = "";
        this.parent = null;
        this.fileseachCommit = new HashMap<>();
    }

    public Node(String currentBranch, String message) {
        this.time = Utils.date();
        this.commitMessage = message;
        this.currentBranch = currentBranch;
        this.parent = null;
        this.fileseachCommit = new HashMap<>();
    }

    public String hashcode() {
        return Utils.sha1(time, commitMessage, currentBranch);
    }
}
