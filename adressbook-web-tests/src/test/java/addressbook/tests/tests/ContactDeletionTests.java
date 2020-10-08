package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void checkIfContactExists(){
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
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptAlertContactsDeletion();
  }

  @Test
  public void testAllContactsDeletionHome() throws Exception {
    app.getContactHelper().selectAllContacts();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptAlertContactsDeletion();
  }

  @Test
  public void testContactDeletionEditPage() throws Exception {
    app.getContactHelper().goToContactEditPage();
    app.getContactHelper().deleteContactFromEditPage();
  }

  @Test
  public void testContactDeletionViewEditPage() throws Exception {
    app.getContactHelper().openContactDetailsPage();
    app.getContactHelper().clickContactModifyButton();
    app.getContactHelper().deleteContactFromEditPage();
  }

}
