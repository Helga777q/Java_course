package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public  void contactDeletionPreconditions(){
    //check that the contact exists, if not - create the new one
    if (!app.getContactHelper().isContactPresent()){
      //check that the group exist, if not - create the new one
      app.getNavigationHelper().goToGroupPage();
      if(!app.getGroupHelper().isGroupPresent()){
        app.getGroupHelper().createGroup(new GroupData()
                .withName("Test").withHeader("test contact deletion"));
      }
      app.getContactHelper().createContactWithGroup(new ContactData(
              "Monica1",
              "Geller",
              "New York, Central Perk 3",
              "+1555567888",
              "monica.geller@friends.com",
              "Test"));
    }

  }


  @Test
  public void testContactDeletionHomePage() throws Exception {
   List<ContactData> before = app.getContactHelper().getContactList();
   int index = before.size()-1;
   app.getContactHelper().deleteContacts(index, "home");
   List<ContactData> after = app.getContactHelper().getContactList();
   Assert.assertEquals(after.size(), before.size()-1);
   before.remove(index);
   Assert.assertEquals(after, before);

  }
  @Test
  public void testContactDeletionEditPage() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    int index = before.size()-1;
    app.getContactHelper().deleteContacts(index, "edit");
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()-1);
    before.remove(index);
    Assert.assertEquals(after, before);
  }

  @Test
  public void testContactDeletionViewEditPage() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    int index = before.size()-1;
    app.getContactHelper().deleteContacts(index, "details");
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()-1);
    before.remove(index);
    Assert.assertEquals(after, before);
  }


  @Test
  public void testAllContactsDeletionHome() throws Exception {
    app.getContactHelper().deleteContacts(0, "homeAll");
    //int after = app.getContactHelper().getContactCount();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), 0);
  }

}
