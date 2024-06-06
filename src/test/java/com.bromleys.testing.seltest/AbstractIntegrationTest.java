
package com.bromleys.testing.seltest;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

/**
 *
 */
public abstract class AbstractIntegrationTest
{
    private static final Logger LOGGER =
            Logger.getLogger( AbstractIntegrationTest.class.getName() );

    private TestInfo testInfo;

    @BeforeEach
    public void before( TestInfo testInfo )
    {
        this.testInfo = testInfo;
        String testName = testInfo.getTestMethod().get().getName();
        LOGGER.log( Level.INFO, "  ===== {0} =====", testName );
        beforeTime = System.currentTimeMillis();
    }

    private long beforeTime;

    @AfterEach
    public void after()
    {
        final String testName = testInfo.getTestMethod().get().getName();
        final long timeTaken = System.currentTimeMillis() - beforeTime;
        LOGGER.log( Level.INFO, "Test ''{0}'' took {1}ms", new Object[]{ testName, timeTaken } );
    }


}
