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
        app.getGroupHelper().createGroup(new GroupData()
                .withName("Test").withHeader("test contact update"));
      }
      app.getContactHelper().createContactWithGroup(new ContactData()
              .withFirstName("Monica").withLastName("Geller").withAddress("NY, Central Perk 3").withHomePhone("+155566666").withEmail("mgeller@friends.com").withGroup("Test")
      );
    }
  }


  @Test
  public void testContactUpdateEditPage() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    int index = before.size() - 1;
    ContactData contact = new ContactData()
            .withId(before.get(index).getId()).withFirstName("New first NAme").withLastName("New LastNAme").withAddress("New address").withHomePhone("+136456634").withEmail("Test@email.com");
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
    ContactData contact = new ContactData()
            .withId(before.get(index).getId()).withFirstName("New first NAme").withLastName("New LastNAme").withAddress("New address").withHomePhone("+136456634").withEmail("Test@email.com");
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
