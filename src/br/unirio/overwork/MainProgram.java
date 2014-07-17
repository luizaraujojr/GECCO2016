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
import br.unirio.overwork.builders.controller.SoftwareSystemProjectBuilder;
import br.unirio.overwork.builders.model.SoftwareSystem;
import br.unirio.overwork.builders.reader.Reader;
import br.unirio.overwork.model.base.Activity;
import br.unirio.overwork.model.base.ActivityDevelopment;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;

public class MainProgram
{

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
	
	protected static String[] instanceFiles1 =	{"data/overworking/ACAD.xml"};
		
	protected static String[] instanceFiles2 =	{"data/overworking/OPMET.xml"};
	
	protected static String[] instanceFiles3 =	{"data/overworking/PARM.xml"};
	
	protected static String[] instanceFiles4 =	{"data/overworking/PSOA.xml"};
	
	protected static String[] instanceFiles5 =	{"data/overworking/WEBAMHS.xml"};
	
	protected static String[] instanceFiles6 =	{"data/overworking/WEBMET.xml"};
	
	
	public static final void main(String[] args) throws Exception
	{
//		runExperimentThread("1", instanceFiles, 0, 99);
	
		runExperimentAnalysis();
//		runMultiExperimentAnalysis();		
	}

	@SuppressWarnings("unused")
	private static void runExperimentAnalysis()
			throws ExperimentFileReaderException, Exception {
//		new ExperimentAnalyzer().analyze("rs5k","C:/workspace/Hector/data/result/RS/5K/rs5k.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("rs10k","C:/workspace/Hector/data/result/RS/10K/rs10k.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("rs20k","C:/workspace/Hector/data/result/RS/20K/rs20k.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("rs50k","C:/workspace/Hector/data/result/RS/50K/rs50k.txt", instanceFiles.length, 50, 3);
		
//		new ExperimentAnalyzer().analyze("nsga5k2x","C:/workspace/Hector/data/result/NSGA/5K/nsga5k2x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga10k2x","C:/workspace/Hector/data/result/NSGA/10K/nsga10k2x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga20k2x","C:/workspace/Hector/data/result/NSGA/20K/nsga20k2x.txt", instanceFiles.length, 50, 3);
		new ExperimentAnalyzer().analyze("nsga50k2x","C:/workspace/Hector/data/result/NSGA/50K/2x/nsga50k2x.txt", instanceFiles.length, 50, 3);
		
//		new ExperimentAnalyzer().analyze("nsga5k4x","C:/workspace/Hector/data/result/NSGA/5K/4x/nsga5k4x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga10k4x","C:/workspace/Hector/data/result/NSGA/10K/4x/nsga10k4x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga20k4x","C:/workspace/Hector/data/result/NSGA/20K/4x/nsga20k4x.txt", instanceFiles.length, 50, 3);
		new ExperimentAnalyzer().analyze("nsga50k4x","C:/workspace/Hector/data/result/NSGA/50K/4x/nsga50k4x.txt", instanceFiles.length, 50, 3);
		
//		new ExperimentAnalyzer().analyze("nsga5k8x","C:/workspace/Hector/data/result/NSGA/5K/8x/nsga5k8x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga10k8x","C:/workspace/Hector/data/result/NSGA/10K/8x/nsga10k8x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga20k8x","C:/workspace/Hector/data/result/NSGA/20K/8x/nsga20k8x.txt", instanceFiles.length, 50, 3);
		new ExperimentAnalyzer().analyze("nsga50k8x","C:/workspace/Hector/data/result/NSGA/50K/8x/nsga50k8x.txt", instanceFiles.length, 50, 3);
		
		new ExperimentAnalyzer().analyze("nsga50k16x","C:/workspace/Hector/data/result/NSGA/50K/16x/nsga50k16x.txt", instanceFiles.length, 50, 3);		
	}
	
	@SuppressWarnings("unused")
	private static void runMultiExperimentAnalysis()
			throws ExperimentFileReaderException, Exception {
		String path = "C:/workspace/Hector/data/result/";
		ExperimentFileReader reader = new ExperimentFileReader();
		ExperimentResult configNSGA05k2x = reader.execute("nsga05k2x", path + "nsga/5k/nsga5k2x.txt", 6, 50, 3);
		ExperimentResult configNSGA10k2x = reader.execute("nsga10k2x", path + "nsga/10k/nsga10k2x.txt", 6, 50, 3);
		ExperimentResult configNSGA20k2x = reader.execute("nsga20k2x", path + "nsga/20k/nsga20k2x.txt", 6, 50, 3);
		ExperimentResult configNSGA50k2x = reader.execute("nsga50k2x", path + "nsga/50k/2x/nsga50k2x.txt", 6, 50, 3);
		
//		ExperimentResult configNSGA50k4x = reader.execute("nsga50k4x", path + "nsga/50k/4x/nsga50k4x.txt", 6, 50, 3);
//		
//		ExperimentResult configNSGA50k8x = reader.execute("nsga50k8x", path + "nsga/50k/8x/nsga50k8x.txt", 6, 50, 3);

		ExperimentResult configRS5 = reader.execute("rs5k", path + "rs/5k/rs5k.txt", 6, 50, 3);
		ExperimentResult configRS10 = reader.execute("rs10k", path + "rs/10k/rs10k.txt", 6, 50, 3);
		ExperimentResult configRS20 = reader.execute("rs20k", path + "rs/20k/rs20k.txt", 6, 50, 3);
		ExperimentResult configRS50 = reader.execute("rs50k", path + "rs/50k/rs50k.txt", 6, 50, 3);

		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(configNSGA05k2x);
		analyzer.addExperimentResult(configNSGA10k2x);
		analyzer.addExperimentResult(configNSGA20k2x);
		analyzer.addExperimentResult(configNSGA50k2x);
		
//		analyzer.addExperimentResult(configNSGA50k4x);
//		
//		analyzer.addExperimentResult(configNSGA50k8x);
//				
		analyzer.addExperimentResult(configRS5);
		analyzer.addExperimentResult(configRS10);
		analyzer.addExperimentResult(configRS20);
		analyzer.addExperimentResult(configRS50);
//		
		analyzer.analyzeInstanceFrontiers();
	}

	private static void runExperimentThread(String name, final String[] ifile, final int cycles, final int eval) {
		new Thread(name)
		{
	        public void run(){
	        	try {
					run1(ifile, cycles, eval);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }.start();
	}
		
	public static void run1(String[] instancesFiles, final int cycles, final int maxevaluations) throws Exception
	{		
		Locale.setDefault(new Locale("pt", "BR"));
		final Vector<Project> instances1 = loadInstances(instancesFiles);
		final Vector<Project> instances2 = loadInstances(instancesFiles);		
		
//		runManualExperiment(instances1, maxevaluations, cycles);
		
//		runRandomSearchExperimentThread(cycles, maxevaluations, instances1);
		
//		runNSGAIIExperimentThread(cycles, maxevaluations, instances2);
	}

	private static void runNSGAIIExperimentThread(final int cycles,
			final int maxevaluations, final Vector<Project> instances2) {
		new Thread("b"){
		    public void run(){
		    	try {
		    		String file2 = runNSGAIIExperiment(instances2, cycles, maxevaluations);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		  }.start();
	}

	private static void runRandomSearchExperimentThread(final int cycles,
			final int maxevaluations, final Vector<Project> instances1) {
		new Thread("a")
		{
	        public void run(){
	        	try {
	        		String file1 = runRandomSearchExperiment(instances1, cycles, maxevaluations);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }.start();
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
		
//		instances.add(createOneDayProject());

		return instances;
	}
		
	/*
	 * Remember to change the file, coping the values from the cycle to the pareto front.
	 */
	protected static String runManualExperiment(Vector<Project> instances, int type, int cycles) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_HHmmss");
		String filename = "data/result/" + sdf1.format(new Date()) + "_v" + type + "_c" + cycles +  "_manual.txt";

		ManualExperiment m = new ManualExperiment(type);
		m.runCycles(filename, instances, 1);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("manual", filename, instances.size(), 1, 3);

//		ResultInterpreter interpreterManual = new ResultInterpreter();
//		interpreterManual.analyze(filename, instances, 1, 3);
		
		return filename;
	}
	

	protected static String runNSGAIIExperiment(Vector<Project> instances, int cycles, int maxevaluations) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_HHmmss");
		String filename = "data/result/" + sdf1.format(new Date()) + "_v" + maxevaluations + "_c" + cycles +  "_nsga_" + instances.get(0).getName()+ "_pop8x.txt";
		
		NSGAIIExperiment nsgaii2 = new NSGAIIExperiment(maxevaluations);
		nsgaii2.runCycles(filename, instances, cycles);
		
 		ExperimentAnalyzer analyzer2 = new ExperimentAnalyzer();
		analyzer2.analyze("nsgaii",filename, instances.size(), cycles, 3);
		
//		ResultInterpreter interpreterNSGA = new ResultInterpreter();
//		interpreterNSGA.analyze(filename, instances, cycles, 3);
		
		return filename;
	}

	protected static String runRandomSearchExperiment(Vector<Project> instances, int cycles, int maxevaluations) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_HHmmss");
		String filename = "data/result/" + sdf1.format(new Date()) + "_v" + maxevaluations + "_c" + cycles + "_rs.txt";

		RandomSearchExperiment rs = new RandomSearchExperiment(maxevaluations);
		rs.runCycles(filename, instances, cycles);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("rs", filename, instances.size(), cycles, 3);

//		ResultInterpreter interpreterRS = new ResultInterpreter();
//		interpreterRS.analyze(filename, instances, cycles, 3);
		
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