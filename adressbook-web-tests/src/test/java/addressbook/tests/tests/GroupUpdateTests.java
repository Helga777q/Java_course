package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.annotations.Test;

public class GroupUpdateTests extends TestBase {

  @Test
  public void testGroupUpdate(){
    app.getNavigationHelper().goToGroupPage();
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().initGroupUpdate();
    app.getGroupHelper().fillGroupForm(new GroupData("update-name", "update -header", "update-footer"));
    app.getGroupHelper().submitGroupUpdate();
    app.getGroupHelper().returnToGroupPage();
  }
}
