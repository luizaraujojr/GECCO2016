package br.unirio.nrpapf;

import br.unirio.nrpapf.calculator.FunctionPointCalculator;
import br.unirio.nrpapf.model.FunctionPointSystem;
import br.unirio.nrpapf.reader.InstanceReader;
import br.unirio.nrpapf.report.Report;
import br.unirio.nrpapf.report.TestCasePublisher;

public class MainInstanceLoader
{
	private static String[] instanceFiles =
	{
//	 	"data/instances/ACAD/functions-point.xml",
//	 	"data/instances/BOLS/functions-point.xml",
//	 	"data/instances/PARM/functions-point.xml",
	 	"data/instances/PSOA/functions-point.xml"
	};
	
	public static final void main(String[] args) throws Exception
	{
		InstanceReader reader = new InstanceReader();

		for (int i = 0; i < instanceFiles.length; i++)
		{
			String instanceFilename = instanceFiles[i];
			FunctionPointSystem fps = reader.run(instanceFilename);
			Report report = new FunctionPointCalculator().calculate(fps);

			String instanceName = instanceFilename.substring(15, instanceFilename.lastIndexOf('/'));
			new TestCasePublisher().run(report, instanceFilename, "TestReader" + instanceName);
		}
	}
}