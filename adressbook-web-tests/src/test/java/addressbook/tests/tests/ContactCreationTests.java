package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import addressbook.tests.model.GroupData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

// Test Data for the tests - from CSV file
  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while(line !=null){
      String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withFirstName(split[0]).withLastName(split[1]).withAddress(split[2]).withEmail(split[3]).withMobile(split[4])
              .withBirthDate(split[5]).withBirthMonth(split[6]).withBirthYear(split[7])});
      line = reader.readLine();
    }
    return list.iterator();
  }


  @Test (dataProvider = "validContacts")
  public void testContactCreationWithOutGroup(ContactData contact) {
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()+1)); 
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }



  
  @Test
  public void testContactCreationWithGroup() throws Exception {
    app.group().createIfNotPresent(new GroupData().withName("Test"));
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Monica with BirthDate").withLastName("Geller").withHome("+155566666").withEmail("mgeller@friends.com").withGroup("Test")
            .withBirthDate("12").withBirthMonth("January").withBirthYear("1990");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()+1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c)-> c.getId()).max().getAsInt()))));

  }




// TestData for negative tests created via DataProvider
  @DataProvider
  public Iterator<Object[]> negativeContactsTest(){
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[]{new ContactData().withFirstName("First Name'").withLastName("LastName").withEmail("test@mail.com")});
    list.add(new Object[]{new ContactData().withFirstName("First Name2'").withLastName("LastName2").withEmail("test2@mail.com")});
    list.add(new Object[]{new ContactData().withFirstName("First Name3'").withLastName("LastName3'").withEmail("test3@mail.com")});
    return list.iterator();
  }


@Test (dataProvider = "negativeContactsTest")
  public void testNegativeContactCreationWithOutGroup(ContactData contact) {
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }





}