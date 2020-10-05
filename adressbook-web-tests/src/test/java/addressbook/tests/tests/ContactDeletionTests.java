package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {


  


  @Test
  public void testContactDeletionHomePage() throws Exception {
    app.getContactHelper().contactPreConditions( new ContactData(
            "Monica1",
            "Geller",
            "New York, Central Perk 3",
            "+1555567888",
            "monica.geller@friends.com",
            "Test")
    );

    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptAlertContactsDeletion();
  }

  @Test
  public void testAllContactsDeletionHome() throws Exception {
    app.getContactHelper().contactPreConditions( new ContactData(
            "Monica1",
            "Geller",
            "New York, Central Perk 3",
            "+1555567888",
            "monica.geller@friends.com",
            "Test")
    );
    app.getContactHelper().selectAllContacts();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptAlertContactsDeletion();
  }

  @Test
  public void testContactDeletionEditPage() throws Exception {
    app.getContactHelper().contactPreConditions( new ContactData(
            "Monica1",
            "Geller",
            "New York, Central Perk 3",
            "+1555567888",
            "monica.geller@friends.com",
            "Test")
    );
    app.getContactHelper().goToContactEditPage();
    app.getContactHelper().deleteContactFromEditPage();
  }

  @Test
  public void testContactDeletionViewEditPage() throws Exception {
    app.getContactHelper().contactPreConditions( new ContactData(
            "Monica1",
            "Geller",
            "New York, Central Perk 3",
            "+1555567888",
            "monica.geller@friends.com",
            "Test")
    );
    app.getContactHelper().openContactDetailsPage();
    app.getContactHelper().clickContactModifyButton();
    app.getContactHelper().deleteContactFromEditPage();
  }

}
