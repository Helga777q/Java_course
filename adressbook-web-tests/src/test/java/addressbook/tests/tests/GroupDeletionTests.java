package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void groupDeletionPreconditions(){
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isGroupPresent()){
      app.getGroupHelper().createGroup(new GroupData("test1", "test2-header", null) );
    }

  }


  @Test
  public void testGroupDeletion() throws Exception {
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
  }

}


