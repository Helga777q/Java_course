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
    if (app.db().groups().size()==0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Test").withHeader("test2").withFooter("test3"));
    }
  }


  @Test
  public void testGroupDeletionDb() throws Exception {
    Groups before = app.db().groups();
    GroupData deletedGroup = before.iterator().next(); //choose any Group from the list of groups from DB
    app.goTo().groupPage();
    app.group().delete(deletedGroup);
    assertThat(app.group().count(), equalTo(before.size()-1));// count groups using method count()
    Groups after = app.db().groups();
    //before.remove(deletedGroup);
    assertThat(after, equalTo(before.without(deletedGroup)));
    verifyGroupListInUI();

  }

}


