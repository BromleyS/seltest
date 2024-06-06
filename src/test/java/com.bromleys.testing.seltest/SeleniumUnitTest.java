/*
 * Copyright 2024 Applied Card Technologies Ltd
 */
package com.bromleys.testing.seltest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Simon.Bromley
 */
public abstract class SeleniumUnitTest extends AbstractIntegrationTest
{
    private static final Logger LOGGER =
            Logger.getLogger( SeleniumUnitTest.class.getName() );

    private static WebDriver driver;

    /**
     * @return The driver.
     */
    public static WebDriver getWebDriver()
    {
        return driver;
    }

    /**
     * @param driver The driver to set.
     */
    public static void setWebDriver( final WebDriver driver )
    {
        SeleniumUnitTest.driver = driver;
    }


    /**
     * @param by Selection criteria
     * @return Matched WebElement
     * <p>
     * Will throw NoSuchElementException if element can't be located.
     */
    public WebElement getElement( final By by )
    {
        return driver.findElement( by );
    }

    /**
     * @param xpath
     * @return
     */
    public List<WebElement> getElements( String xpath )
    {
        return driver.findElements( By.ByCssSelector.xpath( xpath ) );
    }

    /**
     * @param xpath
     * @return True if found.
     */
    public boolean isElementPresent( String xpath )
    {
        return isElementPresent( By.ByCssSelector.xpath( xpath ) );
    }

    /**
     * @param by Selection criteria
     * @return True if found.
     */
    public boolean isElementPresent( final By by )
    {
        boolean elementFound = false;
        try
        {
            List<WebElement> list = driver.findElements( by );
            elementFound = !list.isEmpty();
        }
        catch ( final NoSuchElementException e )
        {
            LOGGER.log( Level.INFO, "Element not matched", e );
        }

        return elementFound;
    }

    @BeforeAll
    public static void beforeAbstractIntegrationTest() throws SQLException, IOException
    {
        if ( getWebDriver() == null )
        {
            setWebDriver( new ChromeDriver() );

            getWebDriver().manage().timeouts().implicitlyWait( 1, TimeUnit.SECONDS );
            getWebDriver().manage().window().setPosition( new Point( 0, 0 ) );
            getWebDriver().manage().window().setSize( new Dimension( 1440, 768 ) );

            Runtime.getRuntime().addShutdownHook( new Thread()
            {
                @Override
                public void run()
                {
                    LOGGER.info( "Web driver was closed, opening a new one" );
                    if ( getWebDriver() != null )
                    {
                        try
                        {
                            getWebDriver().quit();
                        }
                        catch ( final org.openqa.selenium.remote.UnreachableBrowserException e )
                        {
                            LOGGER.info( "Web driver closed" );
                        }
                        finally
                        {
                            setWebDriver( null );
                        }
                    }
                }
            } );
        }

        LOGGER.info( "Web driver is open" );
    }

    /**
     * @param url
     */
    public void navTo( String url )
    {
        driver.navigate().to( url );
    }

    /**
     * @param xpath
     */
    public void findAndClick( String xpath )
    {
        List<WebElement> els = getElements( xpath );
        if ( !els.isEmpty() )
        {
            els.get( 0 ).click();
        }
    }
}
