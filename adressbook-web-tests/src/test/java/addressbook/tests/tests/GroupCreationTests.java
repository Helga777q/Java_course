package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class GroupCreationTests  extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().goToGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    //int before = app.getGroupHelper().getGroupCount();
    app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    List<GroupData> after = app.getGroupHelper().getGroupList();
   // int after = app.getGroupHelper().getGroupCount();
    // Assert.assertEquals(after,  before+1);
    Assert.assertEquals(after.size(), before.size()+1);

  }

}
