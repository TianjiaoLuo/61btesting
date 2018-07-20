package gitlet;


import java.io.Serializable;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;




public class Node implements Serializable{
    public Node parent;
    public String currentBranch;
    public String time;
    public String commitMessage;
    public HashMap<String, String> fileseachCommit;


    public Node() {
        this.commitMessage = "";
        date();
        this.currentBranch = "";
        this.parent = null;
        this.fileseachCommit = new HashMap<>();
    }

    public Node(String currentBranch, String message) {
        date();
        this.commitMessage = message;
        this.currentBranch = currentBranch;
        this.parent = null;
        this.fileseachCommit = new HashMap<>();
    }


    public void date() {
        TimeZone timeStandard = TimeZone.getDefault();
        DateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        form.setTimeZone(timeStandard);
        Date date = new Date();
        this.time = form.format(date);
    }

    public String hashcode() {
        return Utils.sha1(time, commitMessage, currentBranch);
    }

}
