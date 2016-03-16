import junit.framework.Test;
import junit.framework.TestSuite;

public class Suite {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(Login.class);
    suite.addTestSuite(Invalid.class);
    suite.addTestSuite(Add.class);
    suite.addTestSuite(Share.class);
    suite.addTestSuite(Delete.class);
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
