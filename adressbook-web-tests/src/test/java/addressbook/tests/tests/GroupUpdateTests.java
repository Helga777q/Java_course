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
    if (app.group().all().size()==0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Test"));
    }
  }


  @Test
  public void testGroupUpdateDb() {
    Groups before = app.db().groups();
    GroupData updatedGroup = before.iterator().next(); // choose any group for Update from the DB list of groups
    GroupData group = new GroupData()
            .withId(updatedGroup.getId()).withName("Test-update").withHeader("test1").withFooter("test4");
    app.goTo().groupPage();
    app.group().modify(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(updatedGroup).withAdded(group)));
  }

@Test
  public void testNegativeGroupUpdateDb() {
    Groups before = app.db().groups();
    GroupData updatedGroup = before.iterator().next(); // choose any group for Update from the DB list
    GroupData group = new GroupData()
            .withId(updatedGroup.getId()).withName("Test'").withHeader("test1").withFooter("test4");
    app.goTo().groupPage();
    app.group().modify(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }







  @Test(enabled = false)
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

  @Test(enabled = false)
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
