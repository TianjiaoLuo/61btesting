package gitlet;

import java.io.Serializable;
import java.util.ArrayList;

public class branchCommand extends commitTree implements Serializable {
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
}
