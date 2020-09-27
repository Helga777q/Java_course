package addressbook.tests;

import org.testng.annotations.Test;

public class GroupCreationTests  extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    goToGroupPage();
    initGroupCreation();
    fillGroupForm(new GroupData("Test", "test1", "test2"));
    submitGroupCreation();
    returnToGroupPage();
  }

}
