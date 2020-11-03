package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactUpdateTests extends TestBase {

  @BeforeMethod
  public void contactUpdatePreconditions() {
    //check if the contacts exist in DB, if not the new contact is created
    if (app.db().contacts().size()==0){
      app.contact().create(new ContactData()
              .withFirstName("Monica").withLastName("Bing").withAddress("testadd some address")
              .withEmail("mon@friends.com").withEmailSecond("email2@test").withEmailThird("email@test.com")
              .withHome("+1111").withMobile("+222-333").withWork("+1-0000")
              .withBirthDate("11").withBirthMonth("October").withBirthYear("2001"));
    }
  }


  @Test
  public void testContactUpdateEditPage() throws Exception {
    Contacts before = app.db().contacts();
    ContactData updatedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(updatedContact.getId())
            .withFirstName("Monica").withLastName("Geller").withAddress("NY Central perk")
            .withEmail("mon@friends").withEmailSecond("email22@test").withEmailThird("email@test.com")
            .withHome("+1111").withMobile("+222-333").withWork("+1-0000")
            .withBirthDate("11").withBirthMonth("October").withBirthYear("2001");
    app.contact().modify(contact, "edit");
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(updatedContact).withAdded(contact)));
  }

  @Test
  public void testContactUpdateDetailsPage() throws Exception {
    Contacts before = app.db().contacts();
    ContactData updatedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(updatedContact.getId())
            .withFirstName("Monica").withLastName("Bing-Geller").withAddress("NY")
            .withEmail("mon@friends.com").withEmailSecond("email28@test").withEmailThird("email@test.com")
            .withHome("+1111").withMobile("+222-333").withWork("+1-0000")
            .withBirthDate("18").withBirthMonth("October").withBirthYear("1991");
    app.contact().modify(contact, "details");
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(updatedContact).withAdded(contact)));

  }

  @Test
  public void testNegativeContactUpdateEditPage() throws Exception {
    Contacts before = app.db().contacts();
    ContactData updatedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(updatedContact.getId())
            .withFirstName("Monica'").withLastName("Bing").withAddress("testadd some address")
            .withEmail("mon@friends.com").withEmailSecond("email2@test").withEmailThird("email@test.com")
            .withHome("+1111").withMobile("+222-333").withWork("+1-0000")
            .withBirthDate("11").withBirthMonth("October").withBirthYear("2001");
    app.contact().modify(contact, "edit");
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
  }

}
