import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends action {

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    Locator mainButton(){
        return new Locator(By.xpath("//a[@href='/movies-in-theaters/?ref_=nv_tp_inth_1'][text()='Movies']"),"Main Button");
    }

    Locator topRated(){
        return new Locator(By.xpath("//a[@href='/chart/top?ref_=nv_mv_250_6'][text()='Top Rated Movies']"),"Top Rated");
    }

    Locator moviesName(){
        return new Locator(By.xpath("//td[@class='titleColumn']//a"),"Movies Names");
    }

    Locator year(){
        return new Locator(By.xpath("//td[@class='titleColumn']//span"),"Year");
    }

    Locator rating(){
        return new Locator(By.xpath("//td[@class='ratingColumn imdbRating']//strong"),"Rating");
    }

    public WebDriver navigateToWebsite() throws InterruptedException, IllegalAccessException, InstantiationException, IOException {
        File file;
        if(System.getProperty("os.name").contains("Mac")) {
            file = new File(new File("src/test/resources/chromedriver").getCanonicalPath());
        }else if(System.getProperty("os.name").contains("Windows")){
            file = new File(new File("src/test/resources/chromedriver.exe").getCanonicalPath());
        }else {
            file = new File(new File("src/test/resources/chromedriver2").getCanonicalPath());
        }
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath().replace("\\","\\\\"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://www.imdb.com");
        return driver;
    }

    public void navigateToTopRatedMovies() throws InterruptedException, IllegalAccessException, InstantiationException {
        mouseOver(mainButton());
        click(topRated());
        waitforPageReady();
    }

    public ArrayList<panda1> getMoviesName() throws IllegalAccessException {
        List<WebElement> MovieName = getWebElements(moviesName());
        List<WebElement> Year = getWebElements(year());
        List<WebElement> Rating = getWebElements(rating());
        ArrayList<panda1> trial = new ArrayList<panda1>();
        for (int i=0;i<MovieName.size();i++){
            String name = getText(MovieName, i);
            int year = Integer.parseInt(getText(Year,i).replace("(","").replace(")","").trim());
            double rating = Double.parseDouble(getText(Rating,i));
            panda1 p = new panda1();
            p.setMovieName(name);
            p.setRating(rating);
            p.setYear(year);
            trial.add(p);
        }
        return trial;
    }
}
