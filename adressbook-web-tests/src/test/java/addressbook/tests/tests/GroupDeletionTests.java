package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import addressbook.tests.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


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
    Groups before = app.group().all();
    GroupData deletedGroup = before.iterator().next(); //choose any Group from the HashSet
    app.group().delete(deletedGroup);
    assertThat(app.group().count(), equalTo(before.size()-1));// count groups using method count()
    Groups after = app.group().all();
    before.remove(deletedGroup);
    assertThat(after, equalTo(before.without(deletedGroup)));

  }

}


