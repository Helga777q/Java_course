package addressbook.tests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class BaseHelper {


  protected WebDriver wd;

  public BaseHelper(WebDriver wd) {
    this.wd = wd;
  }

  public void click(By locator) {
    wd.findElement(locator).click();
  }

  public void type(By locator, String text) {
    click(locator);
    if (text != null) {
      String existingText = wd.findElement(locator).getAttribute("value");
      if (!text.equals(existingText)){
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
      }
    }
  }


  public void selectFromDropDown(By locator, String text){
    if (text !=null){
      new Select(wd.findElement(locator)).selectByVisibleText(text);
    }
  }



  public void attach(By locator, File file) {
    if (file != null) {
        wd.findElement(locator).sendKeys(file.getAbsolutePath());
      }
    }



  public boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  public boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }


  public void waitForPresenceOfElement(int waitingTime, By locator) {
    WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(waitingTime));
    wait.until(ExpectedConditions.presenceOfElementLocated(locator));

    //wd.manage().timeouts().implicitlyWait(waitingTime, TimeUnit.SECONDS);  // Implicit wait
  }


}