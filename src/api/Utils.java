package api;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class Utils {
    public static Document getDoc(String url) throws IOException {
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.52 Safari/537.17")
                .timeout(600000).get();
        return document;
    }

    public static void sleep(int mil){
        try {
            Thread.sleep(mil);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static FileOutputStream createFileOutputStream(File file) throws FileNotFoundException {
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOut = new FileOutputStream(file);
        return fileOut;
    }

    public static Capabilities getCapabilities() throws URISyntaxException {
        DesiredCapabilities cap = new DesiredCapabilities();
        String[] phantomArgs = new String[]{
                "--webdriver-loglevel=NONE"
        };

        String unpack = Phanbedder.unpack("D:\\phantomjs-2.1.1-windows\\bin");
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, unpack);
        return cap;
    }

    public static XSSFSheet initWorkBook(XSSFWorkbook wb){
        XSSFSheet sheet = wb.createSheet();
        XSSFRow row = sheet.createRow((short) 0);
        row.createCell((short) 0).setCellValue("Ссылка");
        row.createCell((short) 1).setCellValue("Название книги");
        row.createCell((short) 2).setCellValue("Автор");
        row.createCell((short) 3).setCellValue("Художник");
        row.createCell((short) 4).setCellValue("Переводчик");
        row.createCell((short) 5).setCellValue("Издательство");
        row.createCell((short) 6).setCellValue("Год");
        row.createCell((short) 7).setCellValue("Серия");
        row.createCell((short) 8).setCellValue("Рекомендуемый возраст");
        row.createCell((short) 9).setCellValue("ID товара");
        row.createCell((short) 10).setCellValue("ISBN");
        row.createCell((short) 11).setCellValue("Страниц");
        row.createCell((short) 12).setCellValue("Качество страниц");
        row.createCell((short) 13).setCellValue("Тип обложки");
        row.createCell((short) 14).setCellValue("Иллюстрации");
        row.createCell((short) 15).setCellValue("Масса, г");
        row.createCell((short) 16).setCellValue("Размеры, мм");
        row.createCell((short) 17).setCellValue("Содержание");
        row.createCell((short) 18).setCellValue("Аннотация");
        row.createCell((short) 19).setCellValue("Раздел 1");
        row.createCell((short) 20).setCellValue("Раздел 2");
        row.createCell((short) 21).setCellValue("Раздел 3");
        row.createCell((short) 22).setCellValue("Раздел 4");
        row.createCell((short) 23).setCellValue("Раздел 5");
        row.createCell((short) 24).setCellValue("Раздел 6");
        row.createCell((short) 25).setCellValue("Раздел 7");
        row.createCell((short) 26).setCellValue("Раздел 8");
        row.createCell((short) 27).setCellValue("Раздел 9");
        row.createCell((short) 28).setCellValue("Раздел 10");
        row.createCell((short) 29).setCellValue("Рецензия 1");
        row.createCell((short) 30).setCellValue("Рецензия 2");
        row.createCell((short) 31).setCellValue("Рецензия 3");
        row.createCell((short) 32).setCellValue("Рецензия 4");
        row.createCell((short) 33).setCellValue("Рецензия 5");
        row.createCell((short) 34).setCellValue("Рецензия 6");
        row.createCell((short) 35).setCellValue("Рецензия 7");
        row.createCell((short) 36).setCellValue("Рецензия 8");
        row.createCell((short) 37).setCellValue("Рецензия 9");
        row.createCell((short) 38).setCellValue("Рецензия 10");
        row.createCell((short) 39).setCellValue("Рецензия 11");
        row.createCell((short) 40).setCellValue("Рецензия 12");
        row.createCell((short) 41).setCellValue("Рецензия 13");
        row.createCell((short) 42).setCellValue("Рецензия 14");
        row.createCell((short) 43).setCellValue("Рецензия 15");
        row.createCell((short) 44).setCellValue("Рецензия 16");
        row.createCell((short) 45).setCellValue("Рецензия 17");
        row.createCell((short) 46).setCellValue("Рецензия 18");
        row.createCell((short) 47).setCellValue("Рецензия 19");
        row.createCell((short) 48).setCellValue("Рецензия 20");
        row.createCell((short) 49).setCellValue("Рецензия 21");
        row.createCell((short) 50).setCellValue("Рецензия 22");
        row.createCell((short) 51).setCellValue("Рецензия 23");
        row.createCell((short) 52).setCellValue("Рецензия 24");
        row.createCell((short) 53).setCellValue("Рецензия 25");
        row.createCell((short) 54).setCellValue("Рецензия 26");
        row.createCell((short) 55).setCellValue("Рецензия 27");
        row.createCell((short) 56).setCellValue("Рецензия 28");
        row.createCell((short) 57).setCellValue("Рецензия 29");
        row.createCell((short) 58).setCellValue("Рецензия 30");
        row.createCell((short) 59).setCellValue("Рецензия 31");
        row.createCell((short) 60).setCellValue("Рецензия 32");
        row.createCell((short) 61).setCellValue("Рецензия 33");
        row.createCell((short) 62).setCellValue("Рецензия 34");
        row.createCell((short) 63).setCellValue("Рецензия 35");
        row.createCell((short) 64).setCellValue("Рецензия 36");
        row.createCell((short) 65).setCellValue("Рецензия 37");
        row.createCell((short) 66).setCellValue("Рецензия 38");
        row.createCell((short) 67).setCellValue("Рецензия 39");
        row.createCell((short) 68).setCellValue("Рецензия 40");
        row.createCell((short) 69).setCellValue("Рецензия 41");
        row.createCell((short) 70).setCellValue("Рецензия 42");
        row.createCell((short) 71).setCellValue("Рецензия 43");
        row.createCell((short) 72).setCellValue("Рецензия 44");
        row.createCell((short) 73).setCellValue("Рецензия 45");
        row.createCell((short) 74).setCellValue("Рецензия 46");
        row.createCell((short) 75).setCellValue("Рецензия 47");
        row.createCell((short) 76).setCellValue("Рецензия 48");
        row.createCell((short) 77).setCellValue("Рецензия 49");
        row.createCell((short) 78).setCellValue("Рецензия 50");
        row.createCell((short) 79).setCellValue("Рецензия 51");
        row.createCell((short) 80).setCellValue("Рецензия 52");
        row.createCell((short) 81).setCellValue("Рецензия 53");
        row.createCell((short) 82).setCellValue("Рецензия 54");
        row.createCell((short) 83).setCellValue("Рецензия 55");
        row.createCell((short) 84).setCellValue("Рецензия 56");
        row.createCell((short) 85).setCellValue("Рецензия 57");
        row.createCell((short) 86).setCellValue("Рецензия 58");
        row.createCell((short) 87).setCellValue("Рецензия 59");
        row.createCell((short) 88).setCellValue("Рецензия 60");
        row.createCell((short) 89).setCellValue("Рецензия 61");
        row.createCell((short) 90).setCellValue("Рецензия 62");
        row.createCell((short) 91).setCellValue("Рецензия 63");
        row.createCell((short) 92).setCellValue("Рецензия 64");
        row.createCell((short) 93).setCellValue("Рецензия 65");
        row.createCell((short) 94).setCellValue("Рецензия 66");
        row.createCell((short) 95).setCellValue("Рецензия 67");
        row.createCell((short) 96).setCellValue("Рецензия 68");
        row.createCell((short) 97).setCellValue("Рецензия 69");
        row.createCell((short) 98).setCellValue("Рецензия 70");
        row.createCell((short) 99).setCellValue("Рецензия 71");
        row.createCell((short) 100).setCellValue("Рецензия 72");
        row.createCell((short) 102).setCellValue("Рецензия 73");
        row.createCell((short) 103).setCellValue("Рецензия 74");
        row.createCell((short) 104).setCellValue("Рецензия 75");
        row.createCell((short) 105).setCellValue("Рецензия 76");
        row.createCell((short) 106).setCellValue("Рецензия 77");
        row.createCell((short) 107).setCellValue("Рецензия 78");
        row.createCell((short) 108).setCellValue("Рецензия 79");
        row.createCell((short) 109).setCellValue("Рецензия 80");
        row.createCell((short) 110).setCellValue("Рецензия 81");
        row.createCell((short) 111).setCellValue("Рецензия 82");
        row.createCell((short) 112).setCellValue("Рецензия 83");
        row.createCell((short) 113).setCellValue("Рецензия 84");
        row.createCell((short) 114).setCellValue("Рецензия 85");
        row.createCell((short) 115).setCellValue("Рецензия 86");
        row.createCell((short) 116).setCellValue("Рецензия 87");
        row.createCell((short) 117).setCellValue("Рецензия 88");
        row.createCell((short) 118).setCellValue("Рецензия 89");
        row.createCell((short) 119).setCellValue("Рецензия 90");
        row.createCell((short) 120).setCellValue("Рецензия 91");
        row.createCell((short) 121).setCellValue("Рецензия 92");
        row.createCell((short) 122).setCellValue("Рецензия 93");
        row.createCell((short) 123).setCellValue("Рецензия 94");
        row.createCell((short) 124).setCellValue("Рецензия 95");
        row.createCell((short) 125).setCellValue("Рецензия 96");
        row.createCell((short) 126).setCellValue("Рецензия 97");
        row.createCell((short) 127).setCellValue("Рецензия 98");
        row.createCell((short) 128).setCellValue("Рецензия 99");
        row.createCell((short) 129).setCellValue("Рецензия 100");
        return sheet;
    }
}
