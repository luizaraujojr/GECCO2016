package br.unirio.overwork.instance;

import br.unirio.overwork.instance.calculator.FunctionPointCalculator;
import br.unirio.overwork.instance.model.FunctionPointSystem;
import br.unirio.overwork.instance.reader.InstanceReader;

public class MainInstanceLoader
{
	private static String[] instanceFiles =
	{
	 	"data/instances/ACAD/functions-point.xml",
//	 	"data/instances/BOLS/functions-point.xml",
//	 	"data/instances/PARM/functions-point.xml",
//	 	"data/instances/PSOA/functions-point.xml"
	};
	
	public static final void main(String[] args) throws Exception
	{
		InstanceReader reader = new InstanceReader();

		for (int i = 0; i < instanceFiles.length; i++)
		{
			FunctionPointSystem fps = reader.run(instanceFiles[i]);
			new FunctionPointCalculator().calculate(fps);
		}
	}
}