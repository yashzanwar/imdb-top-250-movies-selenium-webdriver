import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.util.ArrayList;

public class TestSuite extends action{


    public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
        WebDriver driver = null;
        try {
            HomePage homepage = new HomePage(driver);
            driver = homepage.navigateToWebsite();
            homepage.navigateToTopRatedMovies();
            ArrayList<panda1> list = homepage.getMoviesName();
            DataBaseActivity dataBaseActivity = new DataBaseActivity();
            dataBaseActivity.dataBase(list);
        }catch (Exception e) {
            Reporter.log(e.toString());
        }finally {
            driver.quit();
        }
    }
}
