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
    if (app.contact().list().size()==0){
      app.contact().create(new ContactData()
              .withFirstName("Monica").withLastName("Bing").withAddress("testadd").withEmail("mon@friends.com").withHomePhone("+1111").withGroup("[none]")
      );
    }
  }


  @Test
  public void testContactUpdateEditPage() throws Exception {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData contact = new ContactData()
            .withId(before.get(index).getId()).withFirstName("New first NAme333").withLastName("New LastNAme").withAddress("New address").withHomePhone("+136456634").withEmail("Test@email.com");
    app.contact().modify(index, contact, "edit");
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());
    //compare of HashSets
    before.remove(index);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }

  @Test
  public void testContactUpdateDetailsPage() throws Exception {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData contact = new ContactData()
            .withId(before.get(index).getId()).withFirstName("New first NAme111").withLastName("New LastNAme").withAddress("New address").withHomePhone("+136456634").withEmail("Test@email.com");
    app.contact().modify(index, contact, "details");
    List<ContactData> after = app.contact().list();
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
