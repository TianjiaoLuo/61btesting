package gitlet;

import java.io.File;
import java.io.Serializable;

public class addCommand extends commitTree implements Serializable {
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
                File created = new File(".gitlet/stagingArea/" + id);
                Utils.writeContents(created, Utils.readContents(file));
                stagingArea.put(path, id);
            }
        }
    }
}
