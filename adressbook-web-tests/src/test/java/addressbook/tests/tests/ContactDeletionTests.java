package addressbook.tests.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {


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
