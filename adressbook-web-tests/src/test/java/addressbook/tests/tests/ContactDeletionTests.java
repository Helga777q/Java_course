package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().selectContact(before -1);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptAlertContactsDeletion();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before-1);
  }

  @Test
  public void testAllContactsDeletionHome() throws Exception {
    app.getContactHelper().selectAllContacts();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptAlertContactsDeletion();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, 0);
  }

  @Test
  public void testContactDeletionEditPage() throws Exception {
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().goToContactEditPage(before-1);
    app.getContactHelper().deleteContactFromEditPage();
    app.getNavigationHelper().waitForHomePageOpens();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before-1);
  }

  @Test
  public void testContactDeletionViewEditPage() throws Exception {
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().openContactDetailsPage(before-1);
    app.getContactHelper().clickContactModifyButton();
    app.getContactHelper().deleteContactFromEditPage();
    app.getNavigationHelper().waitForHomePageOpens();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before-1);
  }

}
