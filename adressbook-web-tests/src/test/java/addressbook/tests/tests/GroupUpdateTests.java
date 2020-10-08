package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GroupUpdateTests extends TestBase {

  @BeforeMethod
  public void groupUpdatePreconditions (){
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isGroupPresent()){
      app.getGroupHelper().createGroup(new GroupData("test1", "test2-header", null) );
    }
  }

  @Test
  public void testGroupUpdate(){
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().initGroupUpdate();
    app.getGroupHelper().fillGroupForm(new GroupData("Test", "test2-header", "update-footer"));
    app.getGroupHelper().submitGroupUpdate();
    app.getGroupHelper().returnToGroupPage();
  }
}
