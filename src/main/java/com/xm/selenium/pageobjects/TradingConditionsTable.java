package com.xm.selenium.pageobjects;

import com.xm.selenium.view.TradingConditionsElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TradingConditionsTable {

  private WebDriver driver;

  public TradingConditionsTable(WebDriver driver) {
    this.driver = driver;
  }

  public String getMarginRequirementValue() {
    return driver.findElement(By.xpath(TradingConditionsElements.MARGIN_REQUIREMENT)).getText();
  }

  public String getSymbolsValue() {
    return driver.findElement(By.xpath(TradingConditionsElements.SYMBOLS)).getText();
  }

  public String getDescriptionValue() {
    return driver.findElement(By.xpath(TradingConditionsElements.DESCRIPTION)).getText();
  }

  public String getSpreadAsLowAsValue() {
    return driver.findElement(By.xpath(TradingConditionsElements.SPREAD_AS_LOW_AS)).getText();
  }

  public String getMinMaxTradeSizeValue() {
    return driver.findElement(By.xpath(TradingConditionsElements.MIN_MAX_TRADE_SIZE)).getText();
  }

  public String getSwapValueLongValue() {
    return driver.findElement(By.xpath(TradingConditionsElements.SWAP_VALUE_LONG)).getText();
  }

  public String getSwapValueShortValue() {
    return driver.findElement(By.xpath(TradingConditionsElements.SWAP_VALUE_SHORT)).getText();
  }

  public String getLimitAndStopLevelsValue() {
    return driver.findElement(By.xpath(TradingConditionsElements.LIMIT_AND_STOP_LEVELS)).getText();
  }
}
