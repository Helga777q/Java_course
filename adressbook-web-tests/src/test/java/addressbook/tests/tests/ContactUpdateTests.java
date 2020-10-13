package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;

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
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().goToContactEditPage(before.size() - 1);
    ContactData contact = new ContactData(
            before.get(before.size()-1).getId(),
            "New FirstName",
            "New LastNAme", "new Address: London", "+1888888", "newemail@gmail.com",
            null);
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().submitContactUpdate();
    app.getContactHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size()-1);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }

  @Test
  public void testContactUpdateDetailsPage() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().openContactDetailsPage(before.size()-1);
    app.getContactHelper().clickContactModifyButton();
    ContactData contact = new ContactData(
            before.get(before.size()-1).getId(),
            "Modify4 New FirstName",
            "New LastNAme",
            "new Address: London",
            "+1888886668",
            "newemail2@gmail.com",
            null);
    app.getContactHelper().fillContactForm(contact,false);
    app.getContactHelper().submitContactUpdate();
    app.getContactHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size()-1);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));

  }
}
