package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import org.testng.annotations.Test;

public class ContactUpdateTests extends TestBase {


  @Test
  public void testContactUpdate() throws Exception {
    app.getContactHelper().goToContactEditPage();
    app.getContactHelper().fillContactForm(new ContactData("New FirstName", "New LastNAme", "new Address: London", "+1888888", "newemail@gmail.com") );
    app.getContactHelper().submitContactUpdate();
    app.getContactHelper().returnToHomePage();
  }

  @Test
  public void testContactUpdateDetailsPage() throws Exception {
    app.getContactHelper().openContactDetailsPage();
    app.getContactHelper().clickContactModifyButton();
    app.getContactHelper().fillContactForm(new ContactData("Modify New FirstName", "New LastNAme", "new Address: London", "+1888888", "newemail@gmail.com") );
    app.getContactHelper().submitContactUpdate();
    app.getContactHelper().returnToHomePage();

  }
}
