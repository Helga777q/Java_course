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
    //check if the group exists, if not created a new group, that will be used for contact creation
    app.getGroupHelper().createGroupIfNotPresent(new GroupData(
            "Test",
            "test2",
            null
    ));
    //check if the contact exists , if not create the new with the group from step above
    app.getContactHelper().contactPreConditions( new ContactData(
            "Monica1",
            "Geller",
            "New York, Central Perk 3",
            "+1555567888",
            "monica.geller@friends.com",
            "Test")
    );

  }


  @Test
  public void testContactDeletionHomePage() throws Exception {
   List<ContactData> before = app.getContactHelper().getContactList();
   app.getContactHelper().selectContact(before.size() - 1);
   app.getContactHelper().deleteSelectedContacts();
   app.getContactHelper().acceptAlertContactsDeletion();
   app.getNavigationHelper().waitForHomePageOpens();
   List<ContactData> after = app.getContactHelper().getContactList();
   Assert.assertEquals(after.size(), before.size()-1);
   before.remove(before.size() - 1);
   Assert.assertEquals(after, before);

  }
  @Test
  public void testContactDeletionEditPage() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().goToContactEditPage(before.size()-1);
    app.getContactHelper().deleteContactFromEditPage();
    app.getNavigationHelper().waitForHomePageOpens();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()-1);
    before.remove(before.size() - 1);
    Assert.assertEquals(after, before);
  }

  @Test
  public void testContactDeletionViewEditPage() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().openContactDetailsPage(before.size()-1);
    app.getContactHelper().clickContactModifyButton();
    app.getContactHelper().deleteContactFromEditPage();
    app.getNavigationHelper().waitForHomePageOpens();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()-1);
    before.remove(before.size() - 1);
    Assert.assertEquals(after, before);
  }


  @Test
  public void testAllContactsDeletionHome() throws Exception {
    app.getContactHelper().selectAllContacts();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptAlertContactsDeletion();
    //int after = app.getContactHelper().getContactCount();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), 0);
  }


}
