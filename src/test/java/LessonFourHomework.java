import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URI;
import java.time.Duration;
import java.util.List;

public class LessonFourHomework {
    private AndroidDriver driver;
    String appPath = new File("apks/org.wikipedia.apk").getAbsolutePath();

    @Before
    public void setUp() throws Exception{
        //Использовал Capabilities вместо DesiredCapabilities
        Capabilities options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:deviceName", "emulator-5554")
                .amend("appium:appPackage", "org.wikipedia")
                .amend("appium:appActivity", ".main.MainActivity")
                .amend("appium:automationName", "UiAutomator2")
                .amend ("appium:app",appPath);

        driver = new AndroidDriver(
                new URI("http://localhost:4723/").toURL(),
                options
        );

    }
    // Добавил что бы приложение закрывалось после теста
    @After
    public void tearDown() {
        if (driver != null) {
            driver.terminateApp("org.wikipedia");
            driver.quit();
        }
    }

    @Test
    public void saveTwoArticlesToMyListAndDeleteOneOfTheArticles(){
        //пропускаем онбординг
        waitForElementAndClick(
                AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/fragment_onboarding_skip_button\")"),
                "Элемент search_element_init не найден по xpath",
                10
        );
        //Кликаем на поиск
        waitForElementAndClick(By.xpath("//*[@text='Поиск по Википедии']"),
                "Элемент search_element_init не найден по xpath",
                10
        );
        //Вводим искомый текст
        waitForElementAndSendKeys(By.xpath("//*[@text = 'Поиск по Википедии']"),"Java",
                "Элемент search не найден по xpath",
                15
        );
        //Нажимаем на пункт меню
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='язык программирования']"),
                "Не найдено на странице",
                15
        );
        //Закрываем предложение поиграть в игры википедии
        waitForElementAndClick(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/closeButton\")"),
                "Не найдено на странице",
                15
        );
        //Проверяем заголовок статьи
        waitForElementPresent(AppiumBy.androidUIAutomator("new UiSelector().text(\"Java\").instance(0)"),
                "Не могу найти заголовок",
                15
        );
        //Нажимаем сохранить статью
        waitForElementAndClick(AppiumBy.accessibilityId("Сохранить"),
                "Элемент 'Сохранить в список' не найден",
                10
        );
        //Нажимаем "Добавить в список"
        waitForElementAndClick(AppiumBy.id("org.wikipedia:id/snackbar_action"),
                "Тост не найден",
                15
        );
        //Переменная с именем папки для сохраненияя
        String nameOfFolder = "Всякое разное";
        //Вводим название папки для сохранения статьи
        waitForElementAndSendKeys(
                AppiumBy.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Поле ввода не найдено" ,
                10
        );
        //Нажимаем "Ок"
        waitForElementAndClick(AppiumBy.id("android:id/button1"),
                "Элемент 'Ок' не найден",
                10
        );
        //Нажимаем на поиск
        waitForElementAndClick(By.xpath("//*[@text='Поиск по Википедии']"),
                "Элемент search_element_init не найден по xpath",
                10
        );
        //Вводим искомый текст
        waitForElementAndSendKeys(By.xpath("//*[@text = 'Поиск по Википедии']"),"APK",
                "Элемент search не найден по xpath",
                15
        );
        //Кликаем на найденную статью
        waitForElementAndClick(By.xpath("//*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and @text=\"APK\"]"),
                "Не найдено на странице",
                15
        );
        //Проверяем заголовок второй статьи
        waitForElementPresent(AppiumBy.androidUIAutomator("new UiSelector().text(\"APK\").instance(0)"),
                "Не могу найти заголовок",
                15
        );
        //Сохраняем название статьи
        String titleBeforeDeletion = waitForElementAndGetAttribute(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"APK\").instance(0)"),
                "text",
                "Cannot find title of article",
                15
        );
        //Нажимаем сохранить статью
        waitForElementAndClick(AppiumBy.accessibilityId("Сохранить"),
                "Элемент 'Сохранить в список' не найден",
                10
        );
        //Нажимаем "Добавить в список"
        waitForElementAndClick(AppiumBy.id("org.wikipedia:id/snackbar_action"),
                "Тост при повторном сохранении не найден",
                10
        );
        //выбираем добавить в тот же список
        waitForElementAndClick(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/item_title\" and @text=\""+nameOfFolder+"\"]"),
                "Тост не найден",
                10
        );
        //Нажимаем "Назад"
        waitForElementAndClick(AppiumBy.accessibilityId("Перейти вверх"),
                "Элемент 'Перейти вверх' не найден",
                10
        );
        //Нажимаем "Назад"
        waitForElementAndClick(AppiumBy.accessibilityId("Перейти вверх"),
                "Элемент 'Перейти вверх' не найден",
                10
        );
        //Нажимаем "Назад"
        waitForElementAndClick(AppiumBy.accessibilityId("Перейти вверх"),
                "Элемент 'Перейти вверх' не найден",
                10
        );
        //Нажимаем на сохраненные статьи
        waitForElementAndClick(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/navigation_bar_item_icon_view\").instance(1)"),
                "Элемент 'Сохраненные' не найден",
                10
        );
        //Нажимаем на папку в которой сохранены статьи
        waitForElementAndClick(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"" + nameOfFolder + "\")"),
                "Список 'Test' не найден",
                10
        );
        //Нажимаем на элемент "Понятно"
        waitForElementAndClick(AppiumBy.androidUIAutomator("new UiSelector().text(\"Понятно\")"),
                "Элемент 'Понятно' не найден",
                10);
        //Свайп влево статьи Java
        swipeElementToLeft(AppiumBy.androidUIAutomator("new UiSelector().text(\"Java\")"),
                "Не найден список"
        );
        //Проверяем что элемента с именем Java нет на странице
        waitForElementNotPresent(AppiumBy.androidUIAutomator("new UiSelector().text(\"Java\")"),
                "Элемент Java на странице",
                10);
        //Проверяем что элемент APK на странице
        waitForElementPresent(AppiumBy.androidUIAutomator("new UiSelector().text(\"APK\")"),
                "Элемент APK не найден на странице",
                10);
        //Заходим в статью APK
        waitForElementAndClick(By.xpath("//*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and @text=\"APK\"]"),
                "Не найдено на странице",
                15
        );
        //Смотрим на заголовок статьи
        String titleAfterDeletion = waitForElementAndGetAttribute(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"APK\").instance(0)"),
                "text",
                "Cannot find title of article",
                15
        );
        //Сравниваем название статей про APK
        Assert.assertEquals(
                "Название статьи изменилось",
                titleBeforeDeletion,
                titleAfterDeletion
        );
    }
    @Test
    public void articleHasTitleWithoutWait() {
        // Пропускаем онбординг
        waitForElementAndClick(
                AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/fragment_onboarding_skip_button\")"),
                "Кнопка пропуска онбординга не найдена",
                10
        );

        // Открываем поиск
        waitForElementAndClick(
                By.xpath("//*[@text='Поиск по Википедии']"),
                "Поле поиска не найдено",
                10
        );

        // Вводим запрос
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Поиск по Википедии']"),
                "Java",
                "Поле ввода поиска не найдено",
                10
        );

        // Открываем статью по результату
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='язык программирования']"),
                "Результат поиска 'Java (язык программирования)' не найден",
                10
        );
        //Закрываем предложение поиграть в игры википедии
        waitForElementAndClick(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/closeButton\")"),
                "Не найдено на странице",
                15
        );
        //Проверяем заголовок сразу, после закрытия предложения поиграть в игры на Википедии
        assertElementPresent(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Java\").instance(0)"),
                "У статьи нет элемента title"
        );
    }

    protected void swipeElementToLeft(By by, String errorMessage){
        WebElement element = waitForElementPresent(by, errorMessage, 15);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y+lower_y)/2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Point start = new Point(right_x,middle_y);
        Point end = new Point(left_x, middle_y);
        Sequence swipe = new Sequence( finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));


    }

    private WebElement waitForElementPresent(By by, String error_message, int timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String error_message, int timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message,timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, int timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }
    private boolean waitForElementNotPresent(By by, String error_message, int timeoutInSeconds){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(error_message +"\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, int timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
    private void assertElementPresent(By by, String errorMessage) {
        // Временно устанавливаем неявное ожидание в 0
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        try {
            List<WebElement> elements = driver.findElements(by);
            Assert.assertFalse(errorMessage, elements.isEmpty());
        } finally {
            // Восстанавливаем исходное значение неявного ожидания (10 секунд)
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }


}
