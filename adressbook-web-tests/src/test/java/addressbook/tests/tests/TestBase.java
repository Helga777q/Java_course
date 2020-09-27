package addressbook.tests.tests;

import addressbook.tests.appmanager.ApplicationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {

  protected final ApplicationManager applicationManager = new ApplicationManager();

  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception {
    applicationManager.init();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
    applicationManager.stop();
  }

}
