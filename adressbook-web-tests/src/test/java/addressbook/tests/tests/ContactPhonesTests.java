package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhonesTests extends TestBase{


  // add preconditions - contact is created
  @BeforeMethod
  public  void contactCheckPhonesPreconditions(){
    //check that the contact exists, if not - create the new one
    if (app.contact().all().size()==0){
      app.contact().create(new ContactData()
              .withFirstName("Monica").withLastName("Bing").withAddress("testadd").withGroup("[none]")
              .withHome("+1111").withMobile("+1-333-(22)"));
    }

  }


  @Test
  public void testContactPhones(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    //assertThat(contact.getHome(), equalTo(cleaned(contactInfoFromEditForm.getHome())));
    //assertThat(contact.getMobile(), equalTo(cleaned(contactInfoFromEditForm.getMobile())));
    //assertThat(contact.getWork(), equalTo(cleaned(contactInfoFromEditForm.getWork())));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHome(), contact.getMobile(), contact.getWork())
            .stream().filter((s)-> !s.equals(""))
            .map(ContactPhonesTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone){
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
}




}
