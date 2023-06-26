package e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager2 {
    public WebDriver driver;

    protected void init(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://phonebook.telran-edu.de:8080/user/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    protected  void stop(){
        driver.quit();
    }
}
