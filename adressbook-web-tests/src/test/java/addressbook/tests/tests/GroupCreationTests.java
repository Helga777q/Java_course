package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import addressbook.tests.model.Groups;
import org.testng.annotations.Test;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests  extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("Test");
    app.group().create(group);
    Groups after = app.group().all();
    assertThat(after.size(), equalTo(before.size()+1));
    //group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt());
    assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));

  }

}
