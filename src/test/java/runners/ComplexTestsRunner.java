package runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps,hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "pretty," +
                "html:target/cucumber-reports/complex-tests.html," +
                "json:target/cucumber-reports/complex-tests.json")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@complex")
@ConfigurationParameter(key = EXECUTION_DRY_RUN_PROPERTY_NAME, value = "false")
public class ComplexTestsRunner {
}