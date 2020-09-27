package addressbook.tests.tests;

import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase {


  @Test
  public void testGroupDeletionTests() throws Exception {
    applicationManager.goToGroupPage();
    applicationManager.selectGroup();
    applicationManager.deleteSelectedGroups();
    applicationManager.returnToGroupPage();
  }


}


