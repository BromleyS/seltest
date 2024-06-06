/*
 * Copyright 2024 Applied Card Technologies Ltd
 */
package com.bromleys.testing.seltest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Simon.Bromley
 */
public class BingTestIT extends SeleniumUnitTest
{
    public static final String home = "https://bing.com";
    public static final String existingElementXPath = "//h1[@class='logo_cont']";
    public static final String nonExistingElementXPath = "//h1[@class='logo_cont!']";

    public static final String cookieRejectButtonXPath = "//button[@id='bnp_btn_reject']";

    @ParameterizedTest
    @CsvSource( { home + "," + existingElementXPath + ",true",
            home + "," + nonExistingElementXPath + ",false" } )
    public void testGoogleExistingElementParam( String url, String xpath, String present ) throws InterruptedException
    {
        navTo( url );
        findAndClick( cookieRejectButtonXPath );
        assertTrue( Boolean.parseBoolean( present ) == isElementPresent( xpath ) );
    }

}
