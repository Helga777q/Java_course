package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
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
      app.getContactHelper().createContactWithGroup(new ContactData(
              "Monica1",
              "Geller",
              "New York, Central Perk 3",
              "+1555567888",
              "monica.geller@friends.com",
              "Test")
      );
    }
  }


  @Test
  public void testContactUpdateEditPage() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    int index = before.size() - 1;
    ContactData contact = new ContactData(
            before.get(index).getId(),
            "New FirstName22222",
            "New LastNAme", "new Address: London", "+1888888", "newemail@gmail.com",
            null);
    app.getContactHelper().modifyContact(index, contact, "edit");
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
    //compare of HashSets
    before.remove(index);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }

  @Test
  public void testContactUpdateDetailsPage() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    int index = before.size() - 1;
    ContactData contact = new ContactData(
            before.get(index).getId(),
            "Modify4 New FirstName222222",
            "New LastNAme",
            "new Address: London",
            "+1888886668",
            "newemail2@gmail.com",
            null);
    app.getContactHelper().modifyContact(index, contact, "details");
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
    before.remove(index);
    before.add(contact);
    //sort lists and compare sorted lists
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }
}
