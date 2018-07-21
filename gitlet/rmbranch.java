package gitlet;

import java.io.Serializable;

public class rmbranch extends commitTree implements Serializable{
	
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
