package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

// Test Data for the tests - from CSV file
  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")))) {
      String line = reader.readLine();
      while (line != null) {
        String[] split = line.split(";");
        list.add(new Object[]{new ContactData().withFirstName(split[0]).withLastName(split[1]).withAddress(split[2]).withEmail(split[3]).withMobile(split[4])
                .withBirthDate(split[5]).withBirthMonth(split[6]).withBirthYear(split[7])});
        line = reader.readLine();
      }
      return list.iterator();
    }
  }


  @Test (dataProvider = "validContacts")
  public void testContactCreationWithOutGroup(ContactData contact) {
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()+1)); 
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }



  @DataProvider
  public Iterator<Object[]> contactsFromXmlFile() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }
  }



  @Test(dataProvider = "contactsFromXmlFile")
  public void testContactCreationXMmlFile(ContactData contact)  {
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()+1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c)-> c.getId()).max().getAsInt()))));
  }


  @DataProvider
  public Iterator<Object[]> contactsFromJsonFile() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }
  }


  @Test(dataProvider = "contactsFromJsonFile")
  public void testContactCreationJsonFile(ContactData contact)  {
    Contacts before = app.contact().all();
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