package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.annotations.Test;

public class GroupCreationTests  extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    applicationManager.goToGroupPage();
    applicationManager.initGroupCreation();
    applicationManager.fillGroupForm(new GroupData("Test", "test1", "test2"));
    applicationManager.submitGroupCreation();
    applicationManager.returnToGroupPage();
  }

}
