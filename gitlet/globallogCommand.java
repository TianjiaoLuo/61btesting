package gitlet;

public class globallogCommand extends commitTree {
    public void globalLog() {
    	
        for (Node commitNode : idtoNode.values()) {
            System.out.println("===");
            System.out.println("Commit " + commitNode.hashcode());
            System.out.println(commitNode.time);
            System.out.println(commitNode.commitMessage);
            System.out.println();
        }
    }
}
