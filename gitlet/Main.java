package gitlet;

import java.io.Serializable;
import java.io.File;

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
        if (args[0].toLowerCase().equals("init")) {
            if (args.length != 1) {
                System.out.println("Incorrect operands.");
                System.exit(0);
            } else {
                initCommand CT = new initCommand();
                CT.init();
                CT.serialize(CT, ".gitlet");
                return;
            }
        } else {
            System.out.println("No command with that name exists.");
            return;
        }
        File gitletFolder = new File(".gitlet");
        if (!gitletFolder.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }
        if (args[0].toLowerCase().equals("add")) {
            if (args.length != 2) {
                System.out.println("Incorrect operands.");
                return;
            } else {
                addCommand CT = new addCommand();
                CT = (addCommand) addCommand.deserialize(".gitlet");
                CT.add(args[1]);
                CT.serialize(CT, ".gitlet");
                return;
            }
        } else if (args[0].toLowerCase().equals("branch")) {
            if (args.length != 2) {
                System.out.println("Incorrect operands.");
                return;
            } else {
                branchCommand CT = new branchCommand();
                CT = (branchCommand) CT.deserialize(".gitlet");
                CT.branch(args[1]);
                CT.serialize(CT, ".gitlet");
                return;
            }
        } else if (args[0].toLowerCase().equals("rm-branch")) {
            if (args.length != 2) {
                System.out.println("Incorrect operands.");
                return;
            } else {
                rmbranch CT = new rmbranch();
                CT = (rmbranch) CT.deserialize(".gitlet");
                CT.rmbranch(args[1]);
                CT.serialize(CT, ".gitlet");
                return;
            }
        } else if (args[0].toLowerCase().equals("commit")) {
            if (args.length != 2) {
                System.out.println("Incorrect operands.");
                return;
            }
            commitCommand CT = new commitCommand();
            CT = (commitCommand) CT.deserialize(".gitlet");
            CT.commit(args[1]);
            CT.serialize(CT, ".gitlet");
            return;
        } else if (args[0].toLowerCase().equals("rm")) {
            if (args.length != 2) {
                System.out.println("Incorrect operands.");
                return;
            } else {
                rmCommand CT = new rmCommand();
                CT = (rmCommand) CT.deserialize(".gitlet");
                CT.rm(args[1]);
                CT.serialize(CT, ".gitlet");
                return;
            }
        } else if (args[0].toLowerCase().equals("log")) {
            if (args.length != 1) {
                System.out.println("Incorrect operands.");
                return;
            } else {
                logCommand CT = new logCommand();
                CT = (logCommand) CT.deserialize(".gitlet");
                CT.log();
                CT.serialize(CT, ".gitlet");
                return;
            }
        } else if (args[0].toLowerCase().equals("global-log")) {
            if (args.length != 1) {
                System.out.println("Incorrect operands.");
                return;
            } else {
                globallogCommand CT = new globallogCommand();
                CT = (globallogCommand) CT.deserialize(".gitlet");
                CT.globalLog();
                CT.serialize(CT, ".gitlet");
                return;
            }
        }else if(args[0].toLowerCase().equals("find")) {// find 
        	  if (!checkExist()) {
                  System.out.println("Not in an initialized gitlet directory.");
                  return;
              }
        	  if (args.length != 2) {
                  System.out.println("Incorrect operands.");
                  return;
              }
        	  findCommand findC=new findCommand();
        	  findC.deserialize(".gitlet");
        	  findC.find(args[1]);
        	  return ;
        }else if(args[0].toLowerCase().equals("status")) { // status 
        	if (!checkExist()) {
                System.out.println("Not in an initialized gitlet directory.");
                return;
            }
            if (args.length != 1) {
                System.out.println("Incorrect operands.");
                return;
            }
            statusCommand sc=new statusCommand();
            sc.deserialize(".gitlet");
            sc.status();
            return ;
        }else if(args[0].toLowerCase().equals("checkout")) { //checkout 
        	if (!checkExist()) {
                System.out.println("Not in an initialized gitlet directory.");
                return;
            }
            if (args.length < 2 || args.length > 4) {
                System.out.print("Incorrect operands.");
                return;
            }
            checkoutCommand cc=new checkoutCommand();
                cc.deserialize(".gitlet");
                if (args.length == 3) {
                    if (!args[1].equals("--")) {
                        System.out.println("Incorrect operands.");
                        return;
                    }
                    cc.checkoutFile(args[2]);
                } else if (args.length == 4) {
                    if (!args[2].equals("--")) {
                        System.out.println("Incorrect operands.");
                        return;
                    }
                    cc.checkoutCommit(args[1], args[3]);
                } else {
                    cc.checkoutBranch(args[1]);
                }
                cc.serialize(cc, ".gitlet");
               return ;
        }else if(args[0].toLowerCase().equals("reset")) { //reset 
        	 if (!checkExist()) {
                 System.out.println("Not in an initialized gitlet directory.");
                 return;
             }
             if (args.length != 2) {
                 System.out.println("Incorrect operands.");
                 return;
             }
             resetCommand rc=new resetCommand();
              rc.deserialize(".gitlet");
             rc.reset(args[1]);
             rc.serialize(rc, ".gitlet");
             return ;
        }else if(args[0].toLowerCase().equals("merge")) { //merge 
        	if (!checkExist()) {
                System.out.println("Not in an initialized gitlet directory.");
                return;
            }
            if (args.length != 2) {
                System.out.println("Incorrect operands.");
                return;
            }
            mergeCommand mc=new mergeCommand();
            mc.deserialize(".gitlet");
            mc.merge(args[1]);
            mc.serialize(mc,".gitlet");
        }else {
        	  System.out.println("No command with that name exists.");
        	  System.exit(0);
        }
    }
    
    public static boolean checkExist() {
        File file = new File(".gitlet");
        return file.exists();
    }
}
