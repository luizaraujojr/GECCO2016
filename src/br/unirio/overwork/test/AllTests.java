package br.unirio.overwork.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.unirio.overwork.test.instances.TestReaderACAD;
import br.unirio.overwork.test.instances.TestReaderBOLS;
import br.unirio.overwork.test.instances.TestReaderPARM;
import br.unirio.overwork.test.instances.TestReaderPSOA;
import br.unirio.overwork.test.scenario.TestScenarioExhaustion;
import br.unirio.overwork.test.scenario.TestScenarioOverworking;

public class AllTests extends TestCase
{
	  public static Test suite() 
	  {
		  TestSuite allTests = new TestSuite();
		  
		  allTests.addTestSuite(TestReaderACAD.class);
		  allTests.addTestSuite(TestReaderBOLS.class);
		  allTests.addTestSuite(TestReaderPARM.class);
		  allTests.addTestSuite(TestReaderPSOA.class);
		  
		  allTests.addTestSuite(TestScenarioOverworking.class);
		  allTests.addTestSuite(TestScenarioExhaustion.class);
		  
		  return allTests;
	  }
}