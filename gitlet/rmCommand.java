package gitlet;

import java.io.File;
import java.io.Serializable;

public class rmCommand extends commitTree implements Serializable{
	
    public void rm(String fileName) {
        if (!stagingArea.containsKey(fileName) && !previousCommit.fileseachCommit.containsKey(fileName)) {
            System.out.println("No reason to remove the file.");
            return;
        }
        if (previousCommit.fileseachCommit.containsKey(fileName)) {
            File f = new File(fileName);
            f.delete();
            removedFiles.add(fileName);
        } else {
            stagingArea.remove(fileName);
            File file = new File(".gitlet/stagingArea" + stagingArea.get(fileName));
            file.delete();
        }
    }
}
