package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreationWithGroup() throws Exception {
    app.group().createIfNotPresent(new GroupData().withName("Test"));
    Set<ContactData> before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Monica").withLastName("Geller").withAddress("NY, Central Perk 3").withHomePhone("+155566666").withEmail("mgeller@friends.com").withGroup("Test");
    app.contact().create(contact);
    Set<ContactData> after = app.contact().all();
    assertEquals(after.size(), before.size() + 1); // compare the size of HashSet after the new contact is created
    contact.withId(after.stream().mapToInt((c)-> c.getId()).max().getAsInt()); // finding the id of the contact -> max id value  of the "after" HashSet
    before.add(contact);
    assertEquals(before, after);

  }

  @Test
  public void testContactCreationWithOutGroup() {
    Set<ContactData> before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Monica111").withLastName("Geller1").withAddress("NY, Central Perk 31").withHomePhone("+15556666622").withEmail("mgeller@friends.com").withGroup("[none]");
    app.contact().create(contact);
    Set<ContactData> after = app.contact().all();
    assertEquals(after.size(), before.size() + 1); // compare the size of HashSet after the new contact is created
    contact.withId(after.stream().mapToInt((c)-> c.getId()).max().getAsInt()); //find the id of the new created contact - > max value of the id
    before.add(contact);
    assertEquals(before, after);
  }


}