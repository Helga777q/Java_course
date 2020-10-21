package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import addressbook.tests.model.GroupData;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreationWithGroup() throws Exception {
    app.group().createIfNotPresent(new GroupData().withName("Test"));
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Monica").withLastName("Geller").withAddress("NY, Central Perk 3").withHomePhone("+155566666").withEmail("mgeller@friends.com").withGroup("Test");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()+1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c)-> c.getId()).max().getAsInt()))));

  }

  @Test
  public void testContactCreationWithOutGroup() {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Monica111").withLastName("Geller1").withAddress("NY, Central Perk 31").withHomePhone("+15556666622").withEmail("mgeller@friends.com").withGroup("[none]");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()+1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }

@Test
  public void testNegativeContactCreationWithOutGroup() {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Monica'").withLastName("Geller1").withAddress("NY, Central Perk 31").withHomePhone("+15556666622").withEmail("mgeller@friends.com").withGroup("[none]");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

}