package com.nifty50.testscases;
import Utilities.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.nifty50.pages.NSEnifty50Page;
import java.util.Set;

public class NSEIndiaStock extends BaseClass {

   NSEnifty50Page NSEnifty50Page ;
    @Test
    public void stockInformationVerifyRCOM() throws InterruptedException {


        Log.info("stockInformationVerifyRCOM started ");

        NSEnifty50Page = new NSEnifty50Page(driver);
        String stockName = "RCOM";
        NSEnifty50Page.searchStock(stockName);
        Log.info("Verify Stock information");
        Assert.assertTrue(NSEnifty50Page.stockCurrentValue().equals("1.75"));
        Assert.assertTrue(NSEnifty50Page.stockPreviousValue().equals("1.80"));
        Assert.assertTrue(NSEnifty50Page.stockOpenValue().equals("1.75"));
        Assert.assertTrue(NSEnifty50Page.stockHighValue().equals("1.80"));
        Assert.assertTrue(NSEnifty50Page.stockLowValue().equals("1.75"));

        Log.info("Printing 52 week High and Low values ");
        System.out.println("52 week High Value is ----  "+NSEnifty50Page.get52WeekHighPrice() );
        System.out.println("52 week High Value is ----  "+NSEnifty50Page.get52WeekLowPrice() );
        Log.info("End of executing test");
    }

    @Test
    public void selectAnyStockNIFTY50() throws InterruptedException{

        Log.info("Starting Test named selectAnyStockNIFTY50");

        WebElement stock = driver.findElement(By.xpath("//table[@id='tab1Ganier']/tbody/tr/td/a"));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", stock);
        stock.click();
        Thread.sleep(3000);
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);
        }

        System.out.println("52 week High Value is ----  "+NSEnifty50Page.get52WeekHighPrice() );
        System.out.println("52 week High Value is ----  "+NSEnifty50Page.get52WeekLowPrice() );
        Log.info("End of executing test");
    }
}
