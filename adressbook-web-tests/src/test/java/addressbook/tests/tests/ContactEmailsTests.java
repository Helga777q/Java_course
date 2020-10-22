package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailsTests extends TestBase {

  @BeforeMethod
  public void contactCheckEmailsPreconditions() {
    //check that the contact exists, if not - create the new one
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Monica").withLastName("Bing").withGroup("[none]")
              .withAddress("Test Address")
              .withEmail("test@mail.com").withEmailThird("test3@com"));
    }

  }

  @Test
  public void testContactEmails() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditPage = app.contact().infoFromEditForm(contact);
    //String forDebug = mergeEmails(contactInfoFromEditPage);
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditPage)));
  }

  public String mergeEmails(ContactData contact){
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s)->!s.equals("")).collect(Collectors.joining("\n"));
  }

}
