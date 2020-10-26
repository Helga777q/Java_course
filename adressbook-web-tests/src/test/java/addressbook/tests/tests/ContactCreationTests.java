package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import addressbook.tests.model.GroupData;
import org.testng.annotations.Test;

import java.io.File;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreationWithGroup() throws Exception {
    app.group().createIfNotPresent(new GroupData().withName("Test"));
    Contacts before = app.contact().all();
    File photo= new File("src/test/resources/test_photo.bmp");
    ContactData contact = new ContactData()
            .withFirstName("Monica with Photo 1").withLastName("Geller").withHome("+155566666").withEmail("mgeller@friends.com").withGroup("Test")
            .withPhoto(photo);
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()+1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c)-> c.getId()).max().getAsInt()))));

  }



  @Test
  public void testCurDir(){
    File  currDir = new File(".");
    File photo = new File("src/test/resources/test_photo.bmp");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());

  }


  @Test
  public void testContactCreationWithOutGroup() {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Monica111 without group").withLastName("Geller1").withAddress("NY, Central Perk 31").withAddress("NY, Central Perk 3").withHome("+155566666").withGroup("Test");
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
            .withFirstName("Monica'").withLastName("Geller1").withAddress("NY, Central Perk 31").withHome("+15556666622").withEmail("mgeller@friends.com").withGroup("[none]");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }





}