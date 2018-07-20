package gitlet;

import java.io.Serializable;

/* Driver class for Gitlet, the tiny stupid version-control system.
   @author
*/
public class Main implements Serializable {

    /* Usage: java gitlet.Main ARGS, where ARGS contains
       <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        commitTree CT = new commitTree();
        if (args[0].equals("init")) {
            if (args.length != 1) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            } else {
                CT.init();
                CT.serialize(CT, ".gitlet");
                return;
            }
        } else {
            System.out.println("No command with that name exists.");
            return;
        }

    }

}
