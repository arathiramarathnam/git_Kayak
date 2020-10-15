package com.kayak.testrunner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/** add junit 4.11 dependency to pom.xml
 * to run testrunner via maven build, add Runner.java to pom.xml
 * via jenkins, provide maven command as mvn test -DCucumber.options="/featurefiles/Kayak.feature"
under available tab , Cucumber , Cucumber Test Result plugin and Cucumber Report plugin, click install without  restart.
or create maven project, add cucumber dependencies, right click on project>> maven build>> Goals >> clean install
create jenkinsjob for cucumber project>>under Build >>path of pom.xml
example: \Users\arath\eclipse\com.kayak.cucumberjunit\pom.xml
run build

**/
@RunWith(Cucumber.class)
@CucumberOptions(features= "featurefiles/Kayak.feature", glue= {"com.kayak.stepdefinition"})
//plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"})
public class TestRunner extends AbstractTestNGCucumberTests{
	
	/*@AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig("src/main/resources/extent-config.xml");
	}*/
}
 	
 

