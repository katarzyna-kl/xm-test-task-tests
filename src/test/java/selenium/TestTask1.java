package selenium;

import com.xm.selenium.pageobjects.HomePage;
import com.xm.selenium.pageobjects.StocksPage;
import java.util.HashMap;
import java.util.Objects;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.xm.selenium.pageobjects.TradingConditionsTable;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestTask1 {

  private HomePage homePage;
  private StocksPage stocksPage;
  private TradingConditionsTable tradingConditionsTable;
  boolean isMobile;
  WebDriver driver;

  @BeforeClass
  public void setUp() {
    driver = new ChromeDriver();
    homePage = new HomePage(driver);
    stocksPage = new StocksPage(driver);
    tradingConditionsTable = new TradingConditionsTable(driver);
  }

  @Test
  @Parameters("resolution")

  public void verifyTradingConditionsDataForOrkla(@Optional String resolution) {

    setupResolution(Objects.requireNonNullElse(resolution, "other"));

    //1. Open Home page
    homePage.openHomePage();
    homePage.acceptCookies();

    //2. Click the “Trading” link located at the top menu
    //3. Click on “Stocks”
    if (isMobile) {
      homePage.clickMobileMenuButton();
      homePage.clickMobileTradingButton();
      homePage.clickMobileStocksButton();
    } else {
      homePage.clickTradingButton();
      homePage.clickStocksButton();
    }
    //4. Choose the "Norway" stock filter
    stocksPage.clickNorwayFilterButton();

    //5. Get data from the table for the "Orkla ASA (ORK.OL)"
    stocksPage.searchInTable("Orkla ASA");
    stocksPage.clickFirstRowInTable();
    HashMap<String, String> stocksTableContent = stocksPage.getStocksTableContent(isMobile);

    //6. Click on the "READ MORE" at this brand
    stocksPage.clickReadMoreButton();

    //7. Compare the data from the previous table with data in "Trading Conditions"
    Assert.assertEquals(stocksPage.getMarginPercentage(stocksTableContent),
        tradingConditionsTable.getMarginRequirementValue(),
        "Margin Percentage values do not match");
    Assert.assertTrue(stocksPage.getDescription(stocksTableContent)
            .contains(tradingConditionsTable.getDescriptionValue()),
        "Description values do not match");
    Assert.assertEquals(stocksPage.getSymbol(stocksTableContent),
        tradingConditionsTable.getSymbolsValue(),
        "Symbol values do not match");
    Assert.assertEquals(stocksPage.getSpreadAsLowAs(stocksTableContent),
        tradingConditionsTable.getSpreadAsLowAsValue(),
        "Spread As Low As values do not match");
    Assert.assertEquals(stocksPage.getMinMaxTrade(stocksTableContent),
        tradingConditionsTable.getMinMaxTradeSizeValue(),
        "Min/Max Trade Size values do not match");
    Assert.assertEquals(stocksPage.getLongSwap(stocksTableContent),
        tradingConditionsTable.getSwapValueLongValue(),
        "Long Swap Value values do not match");
    Assert.assertEquals(stocksPage.getShortSwap(stocksTableContent),
        tradingConditionsTable.getSwapValueShortValue(),
        "Short Swap Value values do not match");
    Assert.assertEquals(stocksPage.getLimitAndStopLevels(stocksTableContent),
        tradingConditionsTable.getLimitAndStopLevelsValue(),
        "Limit and Stop Levels values do not match");

  }

  @AfterTest
  public void tearDown() {
    driver.quit();
  }

  private void setupResolution(String desiredResolution) {
    switch (desiredResolution) {
      case "mobile":
        isMobile = true;
        Dimension dimensionMobile = new Dimension(800, 600);
        driver.manage().window().setSize(dimensionMobile);
      case "low":
        isMobile = false;
        Dimension dimensionLow = new Dimension(1024, 768);
        driver.manage().window().setSize(dimensionLow);
      default:
        isMobile = false;
        driver.manage().window().maximize();
    }
  }

}