package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.stream.Collectors;

 public class Main {
  //  public static void main(String[] args) {
//        // 1. Указать путь к скаченному chromedriver: можно через PATH или Java Properties
//        System.setProperty("webdriver.chrome.driver", "D:\\Soft\\chromedriver-win64\\chromedriver.exe");
 //       System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\сhromedriver.exe");
//
//        // 2. Создание объекта драйвер, который будет общаться с chromedriver
 //       WebDriver driver = new ChromeDriver();
//        // 3. Выполнение команд
 //       driver.get("https://demoqa.com/books");
//    }
//
//        // находим строки таблицы с информацией по книгам (включая строку с названиями столбцов - table header);
//        // исключаем из них пустые строки
//        List<WebElement> tableRowsIncludingHeader = driver.findElements(By.className("rt-tr"));
 //       List<WebElement> filteredTableRows = tableRowsIncludingHeader.stream()
//               .filter(row -> !row.getAttribute("class").contains("-padRow"))
//               .toList();
//
//        // выводим на экран содержание строк таблицы;
//        // начнём с table header
//        final String infoDelimiter = " - ";
//
  //       String headerValues = filteredTableRows.get(0)
 //               .findElements(By.className("rt-resizable-header-content"))
  //              .stream()
  //              .map(WebElement::getText)
  //              .collect(Collectors.joining(infoDelimiter));
//
  //       System.out.println(headerValues);
//
//        // перейдём к информации по книгам
 //       filteredTableRows.stream().skip(1)
  //              .map(tableRow -> {
  //                  String imgSrc = tableRow.findElement(By.tagName("img")).getAttribute("src");
//
  //                   WebElement bookCell = tableRow.findElement(By.tagName("a"));
 //                   String bookLink = bookCell.getAttribute("href");
 //                   String bookTitle = bookCell.getText();
//
//                    // ячейки с автором и издателем мы можем идентифицировать только по имени тега и индексу
//                    final int authorCellIndex = 2;
//                    String bookAuthor = tableRow.findElements(By.className("rt-td")).get(authorCellIndex).getText();
//
   //                  final int publisherCellIndex = 3;
//
//                    // более хитрый способ найти по индексу; важно указывать КОНТЕКСТ!
 //                  String publisherCellXpath = String.format("(.//div)[%d]", publisherCellIndex + 1);
  //                  String bookPublisher = tableRow.findElement(By.xpath(publisherCellXpath)).getText();
//
//                    // соединяем всю полученную информацию в одну строку
  //                  String tableRowInfo = String.join(infoDelimiter, imgSrc, "(" + bookLink, bookTitle + ")", bookAuthor, bookPublisher);
  //                  return tableRowInfo;
   //             })
    //            .forEach(System.out::println);
//
//        // 4. Окончание сессии
//       driver.quit();
        //driver.close() - закрывает только текущее окно; если оно единственно открытое, то заканчивается и сессия
//    }


        // тут идёт вся логика вашего скрипта; закомментруйте скрипт рассмотренный на уроке перед тем, как начнёте разработку;
        // сама логика основного скрипта уже описана, вам же нужно только реализовать сами методы
        public static void main(String[] args) {
            WebDriver driver = configureAndCreateDriver();
            printAllFullBookInfoToTerminal(driver);
            tearDownDriver(driver);
        }

        public static WebDriver configureAndCreateDriver() {
                System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\сhromedriver.exe");

                WebDriver driver = new ChromeDriver();
                driver.get("https://demoqa.com/books");
            return null;
        }

        public static void tearDownDriver(WebDriver driver) {
            driver.quit();
        }

        public static void printAllFullBookInfoToTerminal(WebDriver driver) {
            List<WebElement> tableRowsIncludingHeader = driver.findElements(By.className("rt-tr"));
            List<WebElement> getAllBookUrls =driver.findElements(By.tagName("span a"));
            System.out.println(printFullBookInfoToTerminal);

            // 2. Для каждого элемента полученного листа используйте метод printFullBookInfoToTerminal, чтобы вывести информацию по всем книгам на экран
        }

        public static List<String> getAllBookUrls(WebDriver driver) {

            driver.get("https://demoqa.com/books");
            System.out.println("https://demoqa.com/" + bookUrl );

            // 2. Извлечь url каждой книги и соединить его с основным URL "https://demoqa.com/"
            // Например, если у книги в таблице url равен "/books?book=9781449325862", то полный url будет "https://demoqa.com/books?book=9781449325862"

            // 3. Исправить строку ниже, чтобы она возвращала не null, а List URL-строк всех книг
            return getAllBookUrls;
        }

        public static void printFullBookInfoToTerminal(WebDriver driver, String bookUrl) {
            driver.get("https://demoqa.com/books" + bookUrl);

            // 2. извлечь всю информацию о книге и вывести на экран построчно в формате "<Характеристика>: <Значение>"
            // Например,
            // ISBN: 9781449325862
            // Title: Git Pocket Guide
            // и т.д.
        }
    }