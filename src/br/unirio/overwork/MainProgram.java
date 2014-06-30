package br.unirio.overwork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import unirio.experiments.multiobjective.analyzer.ExperimentAnalyzer;
import unirio.experiments.multiobjective.reader.ExperimentFileReaderException;
import br.unirio.optimization.experiment.ManualExperiment;
import br.unirio.optimization.experiment.NSGAIIExperiment;
import br.unirio.optimization.experiment.RandomSearchExperiment;
import br.unirio.optimization.resultInterpretation.ResultInterpreter;
import br.unirio.overwork.builders.controller.SoftwareSystemProjectBuilder;
import br.unirio.overwork.builders.model.SoftwareSystem;
import br.unirio.overwork.builders.reader.Reader;
import br.unirio.overwork.model.base.Activity;
import br.unirio.overwork.model.base.ActivityDevelopment;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;

public class MainProgram
{
	protected static final int CYCLES = 10;
	protected static final int MAXEVALUATIONS = 5000;

	protected static String[] instanceFiles =
	{
		"data/overworking/ACAD.xml",
//		"data/overworking/BOLS.xml",
		"data/overworking/OPMET.xml",
		"data/overworking/PARM.xml",
		"data/overworking/PSOA.xml",
		"data/overworking/WEBAMHS.xml",
		"data/overworking/WEBMET.xml"
	};
	
	public static final void main(String[] args) throws Exception
	{
		run(instanceFiles);
	}
		
	public static void run(String[] instancesFiles) throws Exception
	{		
		Vector<Project> instances = new Vector<Project>();
		
		for (int i = 0; i < instancesFiles.length; i++)
			if (instancesFiles[i].length() > 0)
			{
				Project project = new Project ();
				project = loadInstance(instancesFiles[i]);
				instances.add(project);
			}
		
//		instances.add(createOneDayProject());
		
		runManualExperiment(instances, 9);
//		runRandomSearchExperiment(instances);
//		runNSGAIIExperiment(instances);
	}
	
	protected static void runManualExperiment(Vector<Project> instances, int value) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) + "_val" + value + "_cycles" + CYCLES + "_manual.txt";

		ManualExperiment m = new ManualExperiment(value);
		m.runCycles(filename, instances, 1);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("manual", filename, instances.size(), 1, 3);

		ResultInterpreter interpreterManual = new ResultInterpreter();
		interpreterManual.analyze(filename, instances, 1, 3);
	}
	

	protected static void runNSGAIIExperiment(Vector<Project> instances) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) + "_eval" + MAXEVALUATIONS + "_cycles"+ CYCLES + "_nsga.txt";
		
		NSGAIIExperiment nsgaii2 = new NSGAIIExperiment(MAXEVALUATIONS);
		nsgaii2 .runCycles(filename, instances, CYCLES);
		
 		ExperimentAnalyzer analyzer2 = new ExperimentAnalyzer();
		analyzer2.analyze("nsgaii",filename, instances.size(), CYCLES, 3);
		
		ResultInterpreter interpreterNSGA = new ResultInterpreter();
		interpreterNSGA.analyze(filename, instances, CYCLES, 3);
	}

	protected static void runRandomSearchExperiment(Vector<Project> instances) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) + "_eval" + MAXEVALUATIONS + "_cycles" + CYCLES + "_rs.txt";

		RandomSearchExperiment rs = new RandomSearchExperiment(MAXEVALUATIONS);
		rs.runCycles(filename, instances, CYCLES);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("rs", filename, instances.size(), CYCLES, 3);

		ResultInterpreter interpreterRS = new ResultInterpreter();
		interpreterRS.analyze(filename, instances, CYCLES, 3);
	}
	
	protected static Project loadInstance(String instancia) throws Exception
	{
		Reader reader = new Reader();
		SoftwareSystem ss = reader.run(instancia);
		SoftwareSystemProjectBuilder builder = new SoftwareSystemProjectBuilder();
		builder.addSoftwareSystem(ss);
		return builder.execute();
	}
	
	protected static Project createOneDayProject() throws Exception
	{
		Project project = new Project();
		Developer developer = new Developer("Fulano", 100.0);
		project.addDeveloper(developer);
		
		Activity activity = new ActivityDevelopment(project, "A1", 1, 0, 0);
		activity.setDeveloper(developer);
		project.addActivity(activity);
		
		return project;
	}
}