package gitlet;

import java.io.File;

public class rmCommand extends commitTree {
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
            File file = new File(".gitlet/stagedFiles" + stagingArea.get(fileName));
            file.delete();
        }
    }
}
