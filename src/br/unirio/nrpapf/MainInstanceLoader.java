package br.unirio.nrpapf;

import br.unirio.nrpapf.calculator.FunctionPointCalculator;
import br.unirio.nrpapf.model.FunctionPointSystem;
import br.unirio.nrpapf.reader.InstanceReader;
import br.unirio.nrpapf.report.OverworkPublisher;
import br.unirio.nrpapf.report.Report;

public class MainInstanceLoader
{
	private static String[] instanceFiles =
	{
//	 	"data/nrp-apf/ACAD/functions-point.xml",
//	 	"data/nrp-apf/BOLS/functions-point.xml",
//	 	"data/nrp-apf/PARM/functions-point.xml",
	 	"data/nrp-apf/PSOA/functions-point.xml"
	};
	
	public static final void main(String[] args) throws Exception
	{
		InstanceReader reader = new InstanceReader();

		for (int i = 0; i < instanceFiles.length; i++)
		{
			String instanceFilename = instanceFiles[i];
			FunctionPointSystem fps = reader.run(instanceFilename);
			Report report = new FunctionPointCalculator().calculate(fps);

//			String instanceName = instanceFilename.substring(15, instanceFilename.lastIndexOf('/'));
//			new TestCasePublisher().run(report, instanceFilename, "TestReader" + instanceName);

			new OverworkPublisher().run(fps, report);
		}
	}
}