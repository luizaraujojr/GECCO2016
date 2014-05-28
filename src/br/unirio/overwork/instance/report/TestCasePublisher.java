package br.unirio.overwork.instance.report;

public class TestCasePublisher
{
	public void run(Report report, String instanceFileName, String testClassName)
	{
		System.out.println("package br.unirio.overwork.test.instances;");
		System.out.println("");
		System.out.println("import junit.framework.TestCase;");
		System.out.println("import br.unirio.overwork.instance.calculator.FunctionPointCalculator;");
		System.out.println("import br.unirio.overwork.instance.model.FunctionPointSystem;");
		System.out.println("import br.unirio.overwork.instance.reader.InstanceReader;");
		System.out.println("import br.unirio.overwork.instance.report.Report;");
		
		System.out.println("");
		System.out.println("public class " + testClassName + " extends TestCase");
		System.out.println("{");
		System.out.println("\tpublic void testAll() throws Exception");
		System.out.println("\t{");
		System.out.println("\t\tInstanceReader reader = new InstanceReader();");
		System.out.println("\t\tFunctionPointSystem fps = reader.run(\"" + instanceFileName + "\");");
		System.out.println("\t\tReport report = new FunctionPointCalculator().calculate(fps);");
		
		System.out.println("");
		System.out.println("\t\t/* Data Functions */");

		for (ReportDataFunction df : report.getDataFunctions())
		{
			System.out.println("");
			System.out.println("\t\tassertEquals(" + df.getRets() + ", report.getDataFunctionName(\"" + df.getName() + "\").getRets());");
			System.out.println("\t\tassertEquals(" + df.getDets() + ", report.getDataFunctionName(\"" + df.getName() + "\").getDets());");
		}
		
		System.out.println("");
		System.out.println("\t\t/* Transaction Functions */");

		for (ReportTransactionFunction tf : report.getTransactionFunctions())
		{
			System.out.println("");
			System.out.println("\t\tassertEquals(" + tf.getFtrs() + ", report.getTransactionFunctionName(\"" + tf.getName() + "\").getFtrs());");
			System.out.println("\t\tassertEquals(" + tf.getDets() + ", report.getTransactionFunctionName(\"" + tf.getName() + "\").getDets());");
		}

		System.out.println("\t}");
		System.out.print("}");
	}
}