package com.xm.selenium.pageobjects;

import com.xm.selenium.view.HomePageElements;
import com.xm.selenium.view.TradingConditionsElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

  private WebDriver driver;

  public HomePage(WebDriver driver) {
    this.driver = driver;
  }

  public void openHomePage() {
    driver.get("https://www.xm.com/");
  }

  public void acceptCookies() {
    driver.findElement(By.className(HomePageElements.ACCEPT_COOKIES_BUTTON)).click();
  }

  public void clickMobileMenuButton() {
    driver.findElement(By.className(HomePageElements.MOBILE_MENU_BUTTON)).click();
  }

  public void clickTradingButton() {
    driver.findElement(By.className(HomePageElements.TRADING_BUTTON)).click();
  }

  public void clickMobileTradingButton() {
    driver.findElement(By.className(HomePageElements.MOBILE_TRADING_BUTTON)).click();
  }

  public void clickStocksButton() {
    driver.findElement(By.xpath(HomePageElements.STOCKS_BUTTON)).click();
  }

  public void clickMobileStocksButton() {
    driver.findElement(By.xpath(HomePageElements.MOBILE_STOCKS_BUTTON)).click();
  }

}
