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
public class GoogleTestIT extends SeleniumUnitTest
{
    public static final String home = "https://google.com";
    public static final String existingElementXPath = "//img[@alt='Google']";
    public static final String nonExistingElementXPath = "//img[@alt='Google!']";

    public static final String cookieRejectButtonXPath = "//button/div[contains(.,'Reject all')]";

    @ParameterizedTest
    @CsvSource( { home + "," + existingElementXPath + ",true",
            home + "," + nonExistingElementXPath + ",false" } )
    public void test(final String url, final String xpath, final String present ) throws InterruptedException
    {
        navTo( url );
        findAndClick( cookieRejectButtonXPath );
        assertEquals(Boolean.parseBoolean(present), isElementPresent(xpath));
    }

}
