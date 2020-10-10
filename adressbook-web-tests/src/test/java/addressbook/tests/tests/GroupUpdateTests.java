package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class GroupUpdateTests extends TestBase {

  @BeforeMethod
  public void groupUpdatePreconditions() {
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isGroupPresent()) {
      app.getGroupHelper().createGroup(new GroupData("test1", "test2-header", null));
    }
  }

  @Test
  public void testGroupUpdate() {
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().initGroupUpdate();
    app.getGroupHelper().fillGroupForm(new GroupData("Test", "test2-header", "update-footer"));
    app.getGroupHelper().submitGroupUpdate();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size());
  }
}
