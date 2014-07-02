package br.unirio.overwork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import unirio.experiments.multiobjective.analyzer.ExperimentAnalyzer;
import unirio.experiments.multiobjective.analyzer.MultiExperimentAnalyzer;
import unirio.experiments.multiobjective.model.ExperimentResult;
import unirio.experiments.multiobjective.reader.ExperimentFileReader;
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
//	protected static final int CYCLES = 30;
//	protected static final int MAXEVALUATIONS = 5000;

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
		ExperimentFileReader reader = new ExperimentFileReader();
		ExperimentResult configNSGA = reader.execute("nsga20k", "data/result/20k/ouput_datetime28062014_114012_eval20000_cycles30_nsga.txt", 6, 30, 3);
		ExperimentResult configRS = reader.execute("rs20k", "data/result/20k/ouput_datetime27062014_093455_eval20000_cycles30_rs.txt", 6, 30, 3);

		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(configNSGA);
		analyzer.addExperimentResult(configRS);
		analyzer.analyzeInstanceFrontiers();

		
//		run(instanceFiles, 30, 5000);
//		run(instanceFiles, 30, 10000);
//		run(instanceFiles, 30, 20000);
		//run(instanceFiles, 3, 50);
	}
		
	public static void run(String[] instancesFiles, int cycles, int maxevaluations) throws Exception
	{		
		//Locale locale = new Locale("hr", "HR");
		Locale.setDefault(new Locale("en", "US"));
		Vector<Project> instances = loadInstances(instancesFiles);
		
//		instances.add(createOneDayProject());
		
//		runManualExperiment(instances, 9, cycles, maxevaluations);
		String file1 = runRandomSearchExperiment(instances, cycles, maxevaluations);
		String file2 = runNSGAIIExperiment(instances, cycles, maxevaluations);
//		runDominanceAnalysis(instances, file1, file2);
	}

	private static Vector<Project> loadInstances(String[] instancesFiles) throws Exception
	{
		Vector<Project> instances = new Vector<Project>();
		
		for (int i = 0; i < instancesFiles.length; i++)
			if (instancesFiles[i].length() > 0)
			{
				Project project = new Project ();
				project = loadInstance(instancesFiles[i]);
				instances.add(project);
			}

		return instances;
	}
	
//	private static void runDominanceAnalysis(Vector<Project> instances, String f1, String f2) throws Exception {
//		
//		ObjectivesComparator comparator = new ObjectivesComparator();
//		comparator.analyze(f1, f2, instances, CYCLES, 3);		
//	}

		
	protected static String runManualExperiment(Vector<Project> instances, int value, int cycles, int maxevaluations) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) + "_val" + value + "_cycles" + cycles + "_manual.txt";

		ManualExperiment m = new ManualExperiment(value);
		m.runCycles(filename, instances, 1);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("manual", filename, instances.size(), 1, 3);

		ResultInterpreter interpreterManual = new ResultInterpreter();
		interpreterManual.analyze(filename, instances, 1, 3);
		
		return filename;
	}
	

	protected static String runNSGAIIExperiment(Vector<Project> instances, int cycles, int maxevaluations) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) + "_eval" + maxevaluations + "_cycles"+ cycles + "_nsga.txt";
		
		NSGAIIExperiment nsgaii2 = new NSGAIIExperiment(maxevaluations);
		nsgaii2 .runCycles(filename, instances, cycles);
		
 		ExperimentAnalyzer analyzer2 = new ExperimentAnalyzer();
		analyzer2.analyze("nsgaii",filename, instances.size(), cycles, 3);
		
		ResultInterpreter interpreterNSGA = new ResultInterpreter();
		interpreterNSGA.analyze(filename, instances, cycles, 3);
		
		return filename;
	}

	protected static String runRandomSearchExperiment(Vector<Project> instances, int cycles, int maxevaluations) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) + "_eval" + maxevaluations + "_cycles" + cycles + "_rs.txt";

		RandomSearchExperiment rs = new RandomSearchExperiment(maxevaluations);
		rs.runCycles(filename, instances, cycles);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("rs", filename, instances.size(), cycles, 3);

		ResultInterpreter interpreterRS = new ResultInterpreter();
		interpreterRS.analyze(filename, instances, cycles, 3);
		
		return filename;
	}
	
	protected static Project loadInstance(String instancia) throws Exception
	{
		Reader reader = new Reader();
		SoftwareSystem ss = reader.run(instancia);
		SoftwareSystemProjectBuilder builder = new SoftwareSystemProjectBuilder();
		//builder.addSoftwareSystem(ss);
		return builder.execute(ss);
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