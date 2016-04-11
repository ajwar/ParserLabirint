package api;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Кирилл on 14.12.2015.
 */
public class Parse {

    static XSSFWorkbook wb = new XSSFWorkbook();
    static XSSFSheet sheet = Utils.initWorkBook(wb);


    public static void doParse(PhantomJSDriver driver) throws IOException, URISyntaxException, InvalidFormatException {

        java.util.List<String> articles = new ArrayList<>();
        initArticles(articles);

        for (int i = 0; i<articles.size(); i++){

            if (articles.get(i).contains("http")) {
                parseByUrl(articles.get(i), i, driver, "");
            } else {
                parseByISBN(articles.get(i), i, driver);
            }

        }
        BTools.printLnWithTime("ПАРСИНГ ЗАКОНЧЕН");
        saveToFile();
    }

    private static void initArticles(List<String> articles) throws InvalidFormatException, IOException {
        Window.fileopen.showDialog(null, "Открыть файл");
        OPCPackage pkg = OPCPackage.open(Window.fileopen.getSelectedFile());
        XSSFWorkbook artBook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = artBook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            XSSFRow row = (XSSFRow) rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        articles.add(cell.getStringCellValue().trim());
                        break;
                }
            }
        }
        rowIterator.remove();
        pkg.close();

    }


    private static void saveToFile() throws IOException {

        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.xlsx", "*.*");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            FileOutputStream fileOut = Utils.createFileOutputStream(fc.getSelectedFile());
            wb.write(fileOut);
            fileOut.close();
        }
    }

    private static void parseByISBN(String art, int n, PhantomJSDriver driver) throws IOException, URISyntaxException {
        driver.get("http://www.labirint.ru/");
        Utils.sleep(500);
        WebElement element = driver.findElement(By.id("search-field"));
        element.sendKeys(art);
        element.sendKeys(Keys.ENTER);
        Utils.sleep(500);
        String currentUrl = driver.getCurrentUrl();
        parseByUrl(currentUrl, n, driver, art);

    }

    public static void parseByUrl(String url, int n, PhantomJSDriver driver, String art) throws IOException, URISyntaxException {
        driver.get(url);
        try {
            driver.findElementByClassName("b-contents-link").click();
            Utils.sleep(500);
        } catch (NoSuchElementException e) {

        }

        Document doc = Jsoup.parse(driver.getPageSource());
        if (doc.text().contains("Страница, которую вы ищете, затерялась в Лабиринте")){
            BTools.printLnWithTime("Страницы "+url+" под номером "+n+" не обнаружено");
            return;
        }
        if (doc.text().contains("у нас ничего нет")){
            BTools.printLnWithTime("ISBN "+art+" под номером "+n+" не обнаружен");
            return;
        }
        XSSFRow rowData = sheet.createRow((short) (n + 1));
        rowData.createCell((short) 0).setCellValue(url);
        String title = doc.select("#product-title").first().select("h1").text().trim();
        BTools.printLnWithTime("ПАРСИТСЯ КНИГА № "+n+" НАЗВАНИЕ " + title);
        rowData.createCell((short) 1).setCellValue(title);
        String author = null;
        String painter = null;
        String interpreter = null;
        try {
            author = doc.select(".authors:contains(Автор)").first().text().replace("Автор:", "").trim();
        } catch (NullPointerException e) {
        }
        try {
            interpreter = doc.select(".authors:contains(Переводчик)").first().text().replace("Переводчик:", "").trim();
        } catch (NullPointerException e1) {
        }
        try {
            painter = doc.select(".authors:contains(Художник)").first().text().replace("Художник:", "").trim();
        } catch (NullPointerException e) {
        }
        rowData.createCell((short) 2).setCellValue(author);
        rowData.createCell((short) 3).setCellValue(painter);
        rowData.createCell((short) 4).setCellValue(interpreter);
        String publishingHouse = doc.select(".publisher").first().text().replace("Издательство:", "").trim();
        String publishYear = StringUtils.substringAfter(publishingHouse, ", ").replace("г.", "").replace("г", "").trim();
        publishingHouse = StringUtils.substringBefore(publishingHouse, ",");
        rowData.createCell((short) 5).setCellValue(publishingHouse);
        rowData.createCell((short) 6).setCellValue(publishYear);
        String series = "";
        try {
            series = doc.select(".series").first().text().replace("Серия:", "").trim();
        } catch (NullPointerException e) {
        }
        rowData.createCell((short) 7).setCellValue(series);
        String age = null;
        try {
            age = doc.select("#age_dopusk").first().text().trim();
        } catch (NullPointerException e) {
        }
        rowData.createCell((short) 8).setCellValue(age);
        String article = doc.select(".articul").first().text().replace("ID товара:", "").trim();
        String isbn = doc.select(".isbn").first().text().replace("ISBN:", "").trim();
        rowData.createCell((short) 9).setCellValue(article);
        rowData.createCell((short) 10).setCellValue(isbn);
        String pages = "";
        try {
            pages = doc.select(".pages2").first().text().replace("Страниц:", "").trim();
        } catch (NullPointerException e) {
        }
        String paperType = null;
        if (pages.contains("(")) {
            paperType = StringUtils.substringBetween(pages, "(", ")");
        }
        pages = StringUtils.substringBefore(pages, "(");
        rowData.createCell((short) 11).setCellValue(pages);
        rowData.createCell((short) 12).setCellValue(paperType);

        String cover = "";
        String illustrations = "";
        try {
            cover = doc.select("span:contains(Тип обложки)").last().nextElementSibling().text().replace("Тип обложки:", "").trim();
        } catch (NullPointerException e) {
            try {
                cover = doc.select("div:contains(Тип обложки)").last().nextElementSibling().text().replace("Тип обложки:", "").trim();
            } catch (NullPointerException e1) {

            }
        }
        try {
            illustrations = doc.select("span:contains(Иллюстрации)").last().nextElementSibling().text().replace("Иллюстрации:", "").trim();
        } catch (NullPointerException e) {
            try {

            } catch (NullPointerException e1) {
                illustrations = doc.select("div:contains(Иллюстрации)").last().nextElementSibling().text().replace("Иллюстрации:", "").trim();
            }
        }
        rowData.createCell((short) 13).setCellValue(cover);
        rowData.createCell((short) 14).setCellValue(illustrations);

        String weight = doc.select(".weight").text().replace("Масса:", "").replace("г.", "").replace("г", "").trim().trim();
        String size = doc.select(".dimensions").text().replace("Размеры:", "").trim();
        rowData.createCell((short) 15).setCellValue(weight);
        rowData.createCell((short) 16).setCellValue(size);
        String content = "";
        try {
            String contentStr = doc.select(".b-contents").first().select("div[style*=margin-top: 10px;]").toString();
            Element contentDoc = Jsoup.parse(contentStr);
            content = TextCleaner.cleanText(TextExtractor.getPlainText(contentDoc));
        } catch (NullPointerException e) {

        }

        rowData.createCell((short) 17).setCellValue(content);
        String about = "";
        Element aboutId = doc.getElementById("product-about");
        if (aboutId != null) {
            aboutId.select("h2").remove();
            about = TextCleaner.cleanText(TextExtractor.getPlainText(aboutId));
        }
        rowData.createCell((short) 18).setCellValue(about);


        Elements genres = doc.getElementById("thermometer-books").select("a");
        int i = 0;
        do {
            String span = genres.get(i).select("span").text();
            rowData.createCell((short) i + 19).setCellValue(span);
            i++;
        } while (i != 10 && i != genres.size());


        Element reviewsEl = doc.select("a:contains(Все отзывы и рецензии)").first();
        if (reviewsEl != null) {
            String reviewUrl = "http://www.labirint.ru" + reviewsEl.attr("href");
            Document page = Utils.getDoc(reviewUrl);
            Elements commentTexts = page.select(".comment-text");
            int j = 0;
            do {
                Element userPic = commentTexts.get(j).select(".comment-user-pic").first();
                if (userPic != null) {
                    Elements select = userPic.select(".comment-pic-container");
                    for (int k = 0; k < select.size(); k++) {
                        String imageUrl = select.get(k).select("a").attr("data-src");
                        String imagePath = System.getProperty("user.home") + File.separatorChar + "img\\" + article + "_" + (j + 1) + "_" + k + ".jpg";
                        saveImage(imageUrl, imagePath);
                    }
                }
                commentTexts.get(j).select(".comment-user-pic").remove();
                String text = commentTexts.get(j).text();
                rowData.createCell((short) j + 29).setCellValue(text);
                j++;
            } while (j != 100 && j != commentTexts.size());

        }
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
    }
}
