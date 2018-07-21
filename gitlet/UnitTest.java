package gitlet;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/* The suite of all JUnit tests for the gitlet package.
   @author
 */
public class UnitTest {

    public void placeholderTest() {
    }
    
    static boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            String[] files = directory.list();
            for (String s : files) {
                boolean success = deleteDirectory(new File(directory, s));
                if (!success) {
                    return false;
                }
            }
        }
        return directory.delete();
    }
    
    @Test
    public void initTest() {
    	 initCommand CT = new initCommand();
         CT.init();
         CT.serialize(CT, ".gitlet");
         
         File file = new File(".gitlet");
         assertEquals(true, file.exists());
         assertEquals("master", CT.currentBranch);
         assertEquals("initial commit", CT.previousCommit.commitMessage);
         deleteDirectory(file);
    }
    
    @Test
    public void addTest() {
    	  
    	 initCommand CT = new initCommand();
         CT.init();
         //CT.serialize(CT, ".gitlet");

        File added = new File("jUnit.txt");

        try {
            added.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addCommand add = new addCommand();
        add.deserialize(".gitlet");
        add.add(added.getName());
        add.serialize(CT, ".gitlet");
    }
    
    @Test
    public void commitTest() {
    	initCommand CT = new initCommand();
        CT.init();
        File file = new File(".gitlet");
        File added = new File("jUnit.txt");

        try {
            added.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        addCommand add = new addCommand();
        add.add(added.getName());
        commitCommand cc=new commitCommand();
        cc.commit("second commit");

        assertEquals(2, cc.branchMap.get("master").size());
        deleteDirectory(file);
    }
  
    
}


