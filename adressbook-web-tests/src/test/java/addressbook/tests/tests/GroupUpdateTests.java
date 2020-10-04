package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.annotations.Test;

public class GroupUpdateTests extends TestBase {

  @Test
  public void testGroupUpdate(){
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isGroupPresent()){
      app.getGroupHelper().createGroup(new GroupData("test1", "test2-header", null) );
    }
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().initGroupUpdate();
    app.getGroupHelper().fillGroupForm(new GroupData("Test", "test2-header", "update-footer"));
    app.getGroupHelper().submitGroupUpdate();
    app.getGroupHelper().returnToGroupPage();
  }
}
