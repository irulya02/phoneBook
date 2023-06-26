package e2e;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
public class TestBase {

    protected static ApplicationManager2 app = new ApplicationManager2();

    @BeforeMethod
   public void setupTest(){
       app.init();
   }

    @AfterMethod
  public void tearDown() {
       app.stop();
    }
}
