package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Comparator;
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
    int index= before.size()-1;
    GroupData group = new GroupData(before.get(index).getId(), "Test", "test2-header", "update-footer");
    app.getGroupHelper().modifyGroup(index, group);
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size());
    before.remove(index);
    before.add(group);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, (after));

  }





}
