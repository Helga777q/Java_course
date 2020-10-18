package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void groupDeletionPreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size()==0) {
      app.group().create(new GroupData().withName("Test"));
    }

  }


  @Test
  public void testGroupDeletion() throws Exception {
    Set<GroupData> before = app.group().all();
    GroupData deletedGroup = before.iterator().next(); //choose any Group from the HashSet
    app.group().delete(deletedGroup);
    Set<GroupData> after = app.group().all();
    assertEquals(after.size(), before.size() - 1); // check size of the List before test and after test
    before.remove(deletedGroup);
    assertEquals(before, after);
  }

}


