package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void groupDeletionPreconditions(){
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isGroupPresent()){
      app.getGroupHelper().createGroup(new GroupData("test1", "test2-header", null) );
    }

  }


  @Test
  public void testGroupDeletion() throws Exception {
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectGroup(before.size()-1);
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size()-1);
  }

}


