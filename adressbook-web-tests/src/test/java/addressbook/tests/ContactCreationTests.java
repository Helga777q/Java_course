package addressbook.tests;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("Monica", "Geller", "New York, Central Perk 3", "+1555567888", "monica.geller@friends.com"));
    submitContactForm();
    returnToHomePage();
  }


}