import junit.framework.TestCase;

import java.io.File;
import java.util.Scanner;

public class Tree2BinaryTreeTest extends TestCase {

  public void testLoadFile() throws Exception {
    new Tree2BinaryTree()
            .loadFile(new Scanner(new File("testData.txt")))
            .output();

  }
}