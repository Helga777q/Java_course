package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void contactCreationPreconditions() {
    app.getGroupHelper().createGroupIfNotPresent(new GroupData().withName("Test"));

  }


  @Test
  public void testContactCreationWithGroup() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData()
            .withFirstName("Monica").withLastName("Geller").withAddress("NY, Central Perk 3").withHomePhone("+155566666").withEmail("mgeller@friends.com").withGroup("Test");
    app.getContactHelper().createContactWithGroup(contact);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);
    //search of Id of the newely created contact
    contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    // compare of HashSets
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));

  }

  @Test
  public void testContactCreationWithOutGroup() {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().initContactCreation();
    ContactData contact = new ContactData()
            .withFirstName("Monica1").withLastName("Geller1").withAddress("NY, Central Perk 31").withHomePhone("+15556666622").withEmail("mgeller@friends.com").withGroup("[none]");
    app.getContactHelper().fillContactForm(contact, true);
    app.getContactHelper().submitContactForm();
    app.getNavigationHelper().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    // setting Id for the new added Contact
    contact.withId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
    before.add(contact);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    //compare of sorted Lists
    Assert.assertEquals(before, after);

  }


}