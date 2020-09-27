package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {
    app.initContactCreation();
    app.fillContactForm(new ContactData("Monica", "Geller", "New York, Central Perk 3", "+1555567888", "monica.geller@friends.com"));
    app.submitContactForm();
    app.returnToHomePage();
  }


}