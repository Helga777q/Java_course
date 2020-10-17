package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.annotations.Test;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class GroupCreationTests  extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData group = new GroupData().withName("Test");
    app.group().create(group);
    Set<GroupData> after = app.group().all();
    assertEquals(after.size(), before.size()+1);
    group.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt());     //get the id of new created group -max id of the group
    before.add(group);
    assertEquals(before, after);

  }

}
