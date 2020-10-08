package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactUpdateTests extends TestBase {

  @BeforeMethod
  public void contactUpdatePreconditions() {
    if (!app.getContactHelper().isContactPresent()) {
      app.getNavigationHelper().goToGroupPage();
      if (!app.getGroupHelper().isGroupPresent()) {
        app.getGroupHelper().createGroup(new GroupData(
                "Test",
                "test contact update",
                null
        ));
      }
      app.getContactHelper().createContactWithGroup((new ContactData(
              "Monica1",
              "Geller",
              "New York, Central Perk 3",
              "+1555567888",
              "monica.geller@friends.com",
              "Test")
      ));
    }
  }


  @Test
  public void testContactUpdate() throws Exception {
    app.getContactHelper().goToContactEditPage();
    app.getContactHelper().fillContactForm(new ContactData(
            "New FirstName",
            "New LastNAme",
            "new Address: London",
            "+1888888",
            "newemail@gmail.com",
            null), false);
    app.getContactHelper().submitContactUpdate();
    app.getContactHelper().returnToHomePage();
  }

  @Test
  public void testContactUpdateDetailsPage() throws Exception {
    app.getContactHelper().openContactDetailsPage();
    app.getContactHelper().clickContactModifyButton();
    app.getContactHelper().fillContactForm(new ContactData(
            "Modify4 New FirstName",
            "New LastNAme",
            "new Address: London",
            "+1888886668",
            "newemail2@gmail.com",
            null), false);
    app.getContactHelper().submitContactUpdate();
    app.getContactHelper().returnToHomePage();
  }
}
