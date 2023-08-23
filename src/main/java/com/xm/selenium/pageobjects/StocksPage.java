package com.xm.selenium.pageobjects;

import com.xm.selenium.view.StocksPageElements;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StocksPage {

  private WebDriver driver;

  public StocksPage(WebDriver driver) {
    this.driver = driver;
  }

  public void clickNorwayFilterButton() {
    WebElement norwayFilterButton = driver.findElement(
        By.xpath(StocksPageElements.NORWAY_FILTER_BUTTON));

    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    jsExecutor.executeScript("arguments[0].scrollIntoView();", norwayFilterButton);

    WebElement stickyBar = driver.findElement(By.xpath(StocksPageElements.STICKY_BAR));

    if (stickyBar.isDisplayed()) {
      stickyBar.click();
    }

    norwayFilterButton.click();
  }

  public void searchInTable(String searchText) {
    WebElement tableSearchField = driver.findElement(
        By.xpath(StocksPageElements.TABLE_SEARCH_FIELD));
    tableSearchField.click();
    tableSearchField.sendKeys(searchText);
  }

  public void clickFirstRowInTable() {
    driver.findElement(By.xpath(StocksPageElements.TABLE_FIRST_ROW)).click();
  }

  public HashMap<String, String> getStocksTableContent(boolean isMobile) {
    HashMap<String, String> tableContent = new HashMap<>();

    for (int i = 1; i <= 8; i++) {
      String headerContent = driver.findElement
              (By.xpath("//*[@id='DataTables_Table_0']/thead/tr/th[" + i + "]"))
          .getText().replace("\n", " ").replace("*", "");

      String cellContent = driver.findElement
          (By.xpath("//*[@id='DataTables_Table_0']/tbody/tr/td[" + i + "]")).getText();

      tableContent.put(headerContent, cellContent);
    }

    //Finding headers and cells values for child elements in the table in mobile view
    if (isMobile) {
      String header1Content = driver.findElement
              (By.xpath("//*[@id='DataTables_Table_0']/tbody/tr[2]/td/ul/li[1]/span[1]"))
          .getText().replace("\n", " ").replace("*", "");
      String cell1Content = driver.findElement
              (By.xpath("//*[@id='DataTables_Table_0']/tbody/tr[2]/td/ul/li[1]/span[2]"))
          .getText();

      String header2Content = driver.findElement
              (By.xpath("//*[@id='DataTables_Table_0']/tbody/tr[2]/td/ul/li[2]/span[1]"))
          .getText().replace("\n", " ").replace("*", "");
      String cell2Content = driver.findElement
              (By.xpath("//*[@id='DataTables_Table_0']/tbody/tr[2]/td/ul/li[2]/span[2]"))
          .getText();

      tableContent.put(header1Content, cell1Content);
      tableContent.put(header2Content, cell2Content);
    }
    return tableContent;
  }

  public void clickReadMoreButton() {
    WebElement readMoreButton = driver.findElement(
        By.className(StocksPageElements.READ_MORE_BUTTON));
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    jsExecutor.executeScript("arguments[0].scrollIntoView();", readMoreButton);
    jsExecutor.executeScript("arguments[0].click();", readMoreButton);
  }

  public String getMarginPercentage(HashMap<String, String> stocksTableContent) {
    return stocksTableContent.get(StocksPageElements.MARGIN_PERCENTAGE_LABEL);
  }

  public String getDescription(HashMap<String, String> stocksTableContent) {
    return stocksTableContent.get(StocksPageElements.DESCRIPTION_LABEL);
  }

  public String getSymbol(HashMap<String, String> stocksTableContent) {
    return stocksTableContent.get(StocksPageElements.SYMBOL_LABEL);
  }

  public String getSpreadAsLowAs(HashMap<String, String> stocksTableContent) {
    return stocksTableContent.get(StocksPageElements.SPREAD_AS_LOW_AS_LABEL);
  }

  public String getMinMaxTrade(HashMap<String, String> stocksTableContent) {
    return stocksTableContent.get(StocksPageElements.MIN_MAX_TRADE_LABEL);
  }

  public String getLongSwap(HashMap<String, String> stocksTableContent) {
    return stocksTableContent.get(StocksPageElements.LONG_SWAP_LABEL);
  }

  public String getShortSwap(HashMap<String, String> stocksTableContent) {
    return stocksTableContent.get(StocksPageElements.SHORT_SWAP_LABEL);
  }

  public String getLimitAndStopLevels(HashMap<String, String> stocksTableContent) {
    return stocksTableContent.get(StocksPageElements.LIMIT_AND_STOP_LEVELS_LABEL);
  }
}
