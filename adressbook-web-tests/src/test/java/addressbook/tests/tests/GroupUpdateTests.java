package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class GroupUpdateTests extends TestBase {

  @BeforeMethod
  public void groupUpdatePreconditions() {
    app.goTo().groupPage();
    if (app.group().list().size()==0) {
      app.group().create(new GroupData().withName("Test"));
    }
  }

  @Test
  public void testGroupUpdate() {
    Set<GroupData> before = app.group().all();
    GroupData updatedGroup = before.iterator().next(); // choose any group for Update from the HashSet
    GroupData group = new GroupData()
            .withId(updatedGroup.getId()).withName("Test-update").withHeader("test1").withFooter("test4");
    app.group().modify(group);
    Set<GroupData> after = app.group().all();
    assertEquals(after.size(), before.size());
    before.remove(updatedGroup);
    before.add(group);
    assertEquals(before, (after));

  }





}
