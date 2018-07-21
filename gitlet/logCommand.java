package gitlet;

public class logCommand extends commitTree {
    public void log() {
        Node preNode = previousCommit;
        while (preNode != null) {
            System.out.println("===");
            System.out.println("Commit " + preNode.hashcode());
            System.out.println(preNode.hashcode());
            System.out.println(preNode.hashcode());
            System.out.println();
            preNode = previousCommit.parent;
        }

    }
}
