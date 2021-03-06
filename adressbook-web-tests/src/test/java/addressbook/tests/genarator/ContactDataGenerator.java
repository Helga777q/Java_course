package addressbook.tests.genarator;

import addressbook.tests.model.ContactData;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Group count")
  public int count;


  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data Format")
  public String format;



  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex){
      jCommander.usage();
      return;
    }
    generator.run();

  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")){
      saveAsCsv(contacts, new File(file));
    } else if (format.equals("xml")){
      saveAsXml(contacts, new File(file));
   } else if (format.equals("json")){
      saveAsJson(contacts, new File(file));
    } else{
      System.out.println("Wrong file format "+format);
    }

  }

  //create file in Json format
  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try(Writer writer =new FileWriter(file)) {
      writer.write(json);
    }
  }


  //create file in XML format
  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try(Writer writer =new FileWriter(file)){
      writer.write(xml);
    }

  }


  //create file in Csv format
  private  void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
   try( Writer writer = new FileWriter(file)) {
     for (ContactData contact : contacts) {
       writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
               contact.getFirstName(), contact.getLastName(), contact.getAddress(),
               contact.getEmail(), contact.getEmail2(), contact.getEmail3(),
               contact.getMobile(),contact.getHome(),contact.getWork(),
               contact.getBirthDate(), contact.getBirthMonth(), contact.getBirthYear()
       ));
     }
   }
  }


  //generate data for the file
  private  List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    String[] months= new String[] {"January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "December" };
    for (int i=0; i<count; i++){
      contacts.add(new ContactData()
              .withFirstName(String.format("First Name %s", i))
              .withLastName(String.format("Last Name %s", i))
              .withAddress(String.format("Some new address, str %s", i))
              .withEmail(String.format("testemail"+i+"@gmail.com"))
              .withEmailSecond(String.format("test2"+i+"@gmail.com"))
              .withEmailThird(String.format("test3"+i+"@gmail.com"))
              .withMobile(String.format("+1-5555-%s", i))
              .withHome(String.format("+1-666-%s", i))
              .withWork(String.format("+1-5777-%s", i))
              .withBirthDate((String.valueOf((int)(Math.random()*27)+1)))
              .withBirthMonth(months[(int)(Math.random()* months.length)])
              .withBirthYear(String.valueOf((int)(Math.random() * (2000 - 1960 + 1)+1960)))
      );
    }
    return contacts;

  }

}
