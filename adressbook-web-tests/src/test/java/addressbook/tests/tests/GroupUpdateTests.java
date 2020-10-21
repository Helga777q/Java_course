package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import addressbook.tests.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupUpdateTests extends TestBase {

  @BeforeMethod
  public void groupUpdatePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size()==0) {
      app.group().create(new GroupData().withName("Test"));
    }
  }

  @Test
  public void testGroupUpdate() {
    Groups before = app.group().all();
    GroupData updatedGroup = before.iterator().next(); // choose any group for Update from the HashSet
    GroupData group = new GroupData()
            .withId(updatedGroup.getId()).withName("Test-update").withHeader("test1").withFooter("test4");
    app.group().modify(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before.without(updatedGroup).withAdded(group)));
  }

  @Test
  public void testNegativeGroupUpdate() {
    Groups before = app.group().all();
    GroupData updatedGroup = before.iterator().next(); // choose any group for Update from the HashSet
    GroupData group = new GroupData()
            .withId(updatedGroup.getId()).withName("Test'").withHeader("test1").withFooter("test4");
    app.group().modify(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }








}
