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
		"data/overworking/ACAD.xml"
//		"data/overworking/BOLS.xml",
//		"data/overworking/OPMET.xml",
//		"data/overworking/PARM.xml",
//		"data/overworking/PSOA.xml",
//		"data/overworking/WEBAMHS.xml",
//		"data/overworking/WEBMET.xml"
	};
	
	protected static String[] instanceFiles1 =	{"data/overworking/ACAD.xml"};
		
	protected static String[] instanceFiles2 =	{"data/overworking/OPMET.xml"};
	
	protected static String[] instanceFiles3 =	{"data/overworking/PARM.xml"};
	
	protected static String[] instanceFiles4 =	{"data/overworking/PSOA.xml"};
	
	protected static String[] instanceFiles5 =	{"data/overworking/WEBAMHS.xml"};
	
	protected static String[] instanceFiles6 =	{"data/overworking/WEBMET.xml"};
	
	
	public static final void main(String[] args) throws Exception
	{
		runExperimentThread("1", instanceFiles1, 50, 50000);
		runExperimentThread("2", instanceFiles2, 50, 50000);
		runExperimentThread("3", instanceFiles3, 50, 50000);
		runExperimentThread("4", instanceFiles4, 50, 50000);
		
		//runExperimentAnalysis();
	}

	@SuppressWarnings("unused")
	private static void runExperimentAnalysis()
			throws ExperimentFileReaderException, Exception {
		String path = "C:/Users/luizaraujo/Downloads/resultados 01245/";
		ExperimentFileReader reader = new ExperimentFileReader();
		ExperimentResult configNSGA5 = reader.execute("nsga5k", path + "03072014_121632_v5000_c50_nsga.txt", 5, 50, 3);
		ExperimentResult configNSGA10 = reader.execute("nsga10k", path + "03072014_121632_v10000_c50_nsga.txt", 5, 50, 3);
		ExperimentResult configNSGA20 = reader.execute("nsga20k", path + "03072014_121632_v20000_c50_nsga.txt", 5, 50, 3);
		ExperimentResult configNSGA50 = reader.execute("nsga50k", path + "09072014_112842_v50000_c50_nsga.txt", 5, 50, 3);
//		ExperimentResult configNSGA100 = reader.execute("nsga100k", "data/result/04072014_085441_v100000_c50_nsga - Copy - Copy.txt", 1, 50, 3);
		
		ExperimentResult configRS5 = reader.execute("rs5k", path + "03072014_121632_v5000_c50_rs.txt", 5, 50, 3);
		ExperimentResult configRS10 = reader.execute("rs10k", path + "03072014_121632_v10000_c50_rs.txt", 5, 50, 3);
		ExperimentResult configRS20 = reader.execute("rs20k", path + "03072014_121632_v20000_c50_rs.txt", 5, 50, 3);
		ExperimentResult configRS50 = reader.execute("RS50k", path + "09072014_112842_v50000_c50_rs.txt", 5, 50, 3);
//		ExperimentResult configRS100 = reader.execute("RS100k", "data/result/04072014_085441_v100000_c50_RS - Copy - Copy.txt", 1, 50, 3);
		
		ExperimentResult configRS = reader.execute("rs20k", "data/result/20k/ouput_datetime27062014_093455_eval20000_cycles30_rs.txt", 6, 30, 3);

		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(configNSGA5);
		analyzer.addExperimentResult(configNSGA10);
		analyzer.addExperimentResult(configNSGA20);
		analyzer.addExperimentResult(configNSGA50);
//		analyzer.addExperimentResult(configNSGA100);
		analyzer.addExperimentResult(configRS5);
		analyzer.addExperimentResult(configRS10);
		analyzer.addExperimentResult(configRS20);
		analyzer.addExperimentResult(configRS50);
//		analyzer.addExperimentResult(configRS100);
		
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
		Locale.setDefault(new Locale("en", "US"));
		final Vector<Project> instances1 = loadInstances(instancesFiles);
		final Vector<Project> instances2 = loadInstances(instancesFiles);		
		
//		runManualExperiment(instances, 9, cycles, maxevaluations);
		
		runRandomSearchExperimentThread(cycles, maxevaluations, instances1);
		
		runNSGAIIExperimentThread(cycles, maxevaluations, instances2);
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
		
	protected static String runManualExperiment(Vector<Project> instances, int value, int cycles, int maxevaluations) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_HHmmss");
		String filename = "data/result/" + sdf1.format(new Date()) + "_v" + maxevaluations + "_c" + cycles +  "_manual.txt";

		ManualExperiment m = new ManualExperiment(value);
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