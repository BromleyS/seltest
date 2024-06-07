
package com.bromleys.testing.seltest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
    public void before(final TestInfo testInfo )
    {
        this.testInfo = testInfo;
        final String testName = testInfo.getTestMethod().get().getName();
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

    private static final Properties config = new Properties();
    static
    {
        try
        {
            final Properties props = new Properties();

            // properties file is in root of tests path so hudson can pick it up.
            final InputStream stream = AbstractIntegrationTest.class.getResourceAsStream(
                    "/test.properties" );
            if ( stream != null )
            {
                try (stream) {
                    props.load(stream);
                    config.putAll(props);
                }
            }
            else
            {
                LOGGER.warning( "No properties file, using defaults" );
            }
        }
        catch ( final IOException e )
        {
            LOGGER.warning(
                    "attempted to load properties. Failed, using defaults. Exception: " + e );
        }
    }

    /**
     *
     * @param key the key of the property tog get
     * @return the value of the property
     */
    public static String getPropertyValue(final String key){
        return config.getProperty(key);
    }
}
