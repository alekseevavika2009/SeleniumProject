package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class DemoQaTests {
    private WebDriver driver;

    @BeforeMethod
    public void configureDriver() {
        // 1. Указать путь к скаченному chromedriver: можно через PATH или Java Properties
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\сhromedriver.exe");

        // 2. Создание объекта драйвер, который будет общаться с chromedriver
        this.driver = new ChromeDriver();

        // 3. Зададим время неявного ожидания при поиске любого элемента
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // 4. Запускаем браузер в подноэкранном режиме
        driver.manage().window().maximize();
    }

    // Перед выполнением этого теста должен быть создан пользователь в системе
    @Test
    public void loginUser() {
        // блок констант с данными зарегистрированного пользователя
        final String userName = "m5";
        final String password = "P@ssw0rd";

        // переходим на страницу логина
        driver.get("https://demoqa.com/login");

        // находим поле UserName и вводим значение
        WebElement userNameInput = driver.findElement(By.id("userName"));
        userNameInput.sendKeys(userName);

        // находим поле Password и вводим значение
        WebElement passwordInput = driver.findElement(By.cssSelector("#password"));
        passwordInput.sendKeys(password);

        // скролим до конца страницы, чтобы реклама не перекрывала кнопку Login
        scrollToBottomUsing(driver);

        // находим кнопку Login и нажимаем её
        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login']"));
        loginButton.click();

        // проверяем успешность логина;
        // критерий - наличие лэйбла UserName, содержащий значение текущего пользователя, а также кнопки Log out на открывшейся странице
        WebElement userNameValue = driver.findElement(By.id("userName-value"));
        Assert.assertEquals(userNameValue.getText(), userName, "Некорректное значение UserName обнаружено на странице после логина");

        // по id будут найдены несколько элементов, поэтому применяем другой локатор
        List<WebElement> possibleLogOutButtons = driver.findElements(By.xpath("//*[text()='Log out']"));
        Assert.assertFalse(possibleLogOutButtons.isEmpty(), "Log Out button was not found on the page");
    }

    @Test
    public void loginUser_useNonExistentUserName() {
        final String userName = "111m5";
        final String password = "P@ssw0rd";


        driver.get("https://demoqa.com/login");

        WebElement userNameInput = driver.findElement(By.id("userName"));
        userNameInput.sendKeys(userName);
        WebElement passwordInput = driver.findElement(By.cssSelector("#password"));
        passwordInput.sendKeys(password);

        scrollToBottomUsing(driver);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login']"));
        loginButton.click();


        WebElement userNameValue = driver.findElement(By.id("userName-value"));
        Assert.assertEquals(userNameValue.getText(), userName, "Invalid username or password!");

    }

    @Test
    public void loginUser_incorrectPassword() {
        final String userName = "m5";
        final String password = "P@ssw0rd";

        driver.get("https://demoqa.com/login");
        WebElement userNameInput = driver.findElement(By.id("userName"));
        userNameInput.sendKeys(userName);

        WebElement passwordInput = driver.findElement(By.cssSelector("#password"));
        passwordInput.sendKeys(password);

        scrollToBottomUsing(driver);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login']"));
        loginButton.click();

        WebElement userNameValue = driver.findElement(By.id("userName-value"));
        Assert.assertEquals(userNameValue.getText(), userName, "Invalid username or password!");
    }

    // Задание со звёздочкой
    @Test
    public void createNewUser() {
        // блок констант с данными нового пользователя
        final String firstName = UUID.randomUUID().toString();
        final String lastName = UUID.randomUUID().toString();
        final String userName = UUID.randomUUID().toString();
        final String password = "P@ssw0rd";

        // заходим на страницу регистрации
        driver.get("https://demoqa.com/register");

        // проверим, что мы на странице с регистрацией; используем tag name локатор
        WebElement registrationHeader = driver.findElement(By.tagName("h4"));
        Assert.assertEquals(registrationHeader.getText(), "Register to Book Store", "Couldn't access register page");

        // поиск по id и ввод
        WebElement firstNameInput = driver.findElement(By.id("firstname"));
        firstNameInput.sendKeys(firstName);

        // поиск по id, но через css-селектор, и ввод
        WebElement lastNameInput = driver.findElement(By.cssSelector("#lastname"));
        lastNameInput.sendKeys(lastName);

        // поиск по id, но через XPath, и ввод
        WebElement userNameInput = driver.findElement(By.xpath("//input[@id='userName']"));
        userNameInput.sendKeys(userName);

        // поиск по compound class name (через css-селектор) и ввод
        WebElement passwordInput = driver.findElements(By.cssSelector(".mr-sm-2.form-control")).get(3);
        passwordInput.sendKeys(password);

        // скролим до конца страницы, чтобы реклама не перекрывала iframe с чекбоксом для captcha
        scrollToBottomUsing(driver);

        // переключаем контекст браузера на него, чтобы иметь возможность нажать на чекбокс
        WebElement recaptchaIframe = driver.findElement(By.cssSelector("#g-recaptcha iframe"));
        driver.switchTo().frame(recaptchaIframe);

        // поиск по id и чек
        WebElement captchaCheckbox = driver.findElement(By.id("recaptcha-anchor"));
        captchaCheckbox.click();

        // ждём, пока captcha будет пройдена
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeToBe(captchaCheckbox, "aria-checked", "true"));

        // переключаемся с iframe обратно
        driver = driver.switchTo().defaultContent();

        // поиск по id и клик
        WebElement registerButton = driver.findElement(By.id("register"));
        registerButton.click();

        // проверяем наличие алерта с сообщением об успешной регистрации
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // переключаемся на алерт и проверяем наличие сообщения об успешной регистрации
        Assert.assertEquals(alert.getText(), "User Register Successfully.", "Алерт содержит некорректное сообщение об успешной регистрации");

        alert.accept();
    }

    @AfterMethod
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    // скроллим страницу до конца, используя JavaScript
    private static void scrollToBottomUsing(WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
}
