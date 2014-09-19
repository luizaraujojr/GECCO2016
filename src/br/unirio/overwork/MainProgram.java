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
	
	protected static String[] instanceFiles12 =	{"data/overworking/ACAD.xml", "data/overworking/OPMET.xml"};
	
	protected static String[] instanceFiles34 =	{"data/overworking/PARM.xml", "data/overworking/PSOA.xml"};
		
	protected static String[] instanceFiles56 =	{"data/overworking/WEBAMHS.xml", "data/overworking/WEBMET.xml"};
		
	public static final void main(String[] args) throws Exception
	{
		// 0 margarine
		// 99 CPM
		// 50 SH
		
//		runExperimentThread("1", instanceFiles, 0, 0);
//		runExperimentThread("1", instanceFiles12, 50, 150);
//		runExperimentThread("2", instanceFiles34, 50, 15000);
//		runExperimentThread("3", instanceFiles56, 50, 15000);
//		showProjectDetails(instanceFiles);
		runExperimentAnalysis();
//		runMultiExperimentAnalysis();		
	}

	@SuppressWarnings("unused")
	private static void runExperimentAnalysis() throws ExperimentFileReaderException, Exception 
	{	
		String resultPath="C:/Users/luiz/Documents/GitHub/Hector/data/result/dissertacao/BaseData/";
		
//		new ExperimentAnalyzer().analyze("rs5k","C:/workspace/Hector/data/result/RS/5K/rs5k.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("rs10k","C:/workspace/Hector/data/result/RS/10K/rs10k.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("rs20k","C:/workspace/Hector/data/result/RS/20K/rs20k.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("rs50k","C:/workspace/Hector/data/result/RS/50K/rs50k.txt", instanceFiles.length, 50, 3);
		
		
		//Preparing data to stop parameter
		new ExperimentAnalyzer().analyze("nsga5k2x",resultPath + "NSGA/5K/nsga_5k_c50_2x_error1.txt", instanceFiles.length, 50, 3);
		new ExperimentAnalyzer().analyze("nsga10k2x",resultPath + "NSGA/10K/nsga_10k_c50_2x_error1.txt", instanceFiles.length, 50, 3);
		new ExperimentAnalyzer().analyze("nsga20k2x",resultPath + "NSGA/20K/nsga_20k_c50_2x_error1.txt", instanceFiles.length, 50, 3);
		new ExperimentAnalyzer().analyze("nsga50k2x",resultPath + "NSGA/50K/nsga_50k_c50_2x_error1.txt", instanceFiles.length, 50, 3);
		new ExperimentAnalyzer().analyze("nsga100k2x",resultPath + "NSGA/100K/nsga_100k_c50_2x_error1.txt", instanceFiles.length, 50, 3);
		new ExperimentAnalyzer().analyze("nsga150k2x",resultPath + "NSGA/150K/nsga_150k_c50_2x_error1.txt", instanceFiles.length, 50, 3);
//		
		
		
//		new ExperimentAnalyzer().analyze("GA",resultPath + "NSGA/50K/2x/nsga50k2xerror1.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("CPM",resultPath + "OMS/CPM/CPM_error1.txt", instanceFiles.length, 1, 3);
//		new ExperimentAnalyzer().analyze("MAR",resultPath + "OMS/Margarine/Margarine_error1.txt", instanceFiles.length, 1, 3);
//		new ExperimentAnalyzer().analyze("SH",resultPath + "OMS/SecondHalf/SecondHalf_error1.txt", instanceFiles.length, 1, 3);
	
//		new ExperimentAnalyzer().analyze("GA",resultPath + "NSGA/50K/2x/nsga50k2xerror1.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("CPM",resultPath + "OMS/CPM/CPM.txt", instanceFiles.length, 1, 3);
//		new ExperimentAnalyzer().analyze("MAR",resultPath + "OMS/Margarine/Margarine.txt", instanceFiles.length, 1, 3);
//		new ExperimentAnalyzer().analyze("SH",resultPath + "OMS/SecondHalf/SecondHalf.txt", instanceFiles.length, 1, 3);
		
//		new ExperimentAnalyzer().analyze("nsga5k4x","C:/workspace/Hector/data/result/NSGA/5K/4x/nsga5k4x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga10k4x","C:/workspace/Hector/data/result/NSGA/10K/4x/nsga10k4x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga20k4x","C:/workspace/Hector/data/result/NSGA/20K/4x/nsga20k4x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga50k4x","C:/workspace/Hector/data/result/NSGA/50K/4x/nsga50k4x.txt", instanceFiles.length, 50, 3);
		
//		new ExperimentAnalyzer().analyze("nsga5k8x","C:/workspace/Hector/data/result/NSGA/5K/8x/nsga5k8x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga10k8x","C:/workspace/Hector/data/result/NSGA/10K/8x/nsga10k8x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga20k8x","C:/workspace/Hector/data/result/NSGA/20K/8x/nsga20k8x.txt", instanceFiles.length, 50, 3);
//		new ExperimentAnalyzer().analyze("nsga50k8x","C:/workspace/Hector/data/result/NSGA/50K/8x/nsga50k8x.txt", instanceFiles.length, 50, 3);
		
//		new ExperimentAnalyzer().analyze("nsga50k16x","C:/workspace/Hector/data/result/NSGA/50K/16x/nsga50k16x.txt", instanceFiles.length, 50, 3);		
	}
	
	@SuppressWarnings("unused")

	private static void runMultiExperimentAnalysis()
			throws ExperimentFileReaderException, Exception {
		String resultPath="C:/Users/luiz/Documents/GitHub/Hector/data/result/BaseData/";
	
		ExperimentFileReader reader = new ExperimentFileReader();
//		ExperimentResult configNSGA05k2x = reader.execute("nsga05k2x", path + "nsga/5k/nsga5k2x.txt", 6, 50, 3);
//		ExperimentResult configNSGA10k2x = reader.execute("nsga10k2x", path + "nsga/10k/nsga10k2x.txt", 6, 50, 3);
//		ExperimentResult configNSGA20k2x = reader.execute("nsga20k2x", path + "nsga/20k/nsga20k2x.txt", 6, 50, 3);
//		ExperimentResult configNSGA50k2x = reader.execute("nsga50k2x", path + "nsga/50k/2x/nsga50k2x.txt", 6, 50, 3);
		
//		ExperimentResult configNSGA50k4x = reader.execute("nsga50k4x", path + "nsga/50k/4x/nsga50k4x.txt", 6, 50, 3);
//		
//		ExperimentResult configNSGA50k8x = reader.execute("nsga50k8x", path + "nsga/50k/8x/nsga50k8x.txt", 6, 50, 3);

//		ExperimentResult configRS5 = reader.execute("rs5k", path + "rs/5k/rs5k.txt", 6, 50, 3);
//		ExperimentResult configRS10 = reader.execute("rs10k", path + "rs/10k/rs10k.txt", 6, 50, 3);
//		ExperimentResult configRS20 = reader.execute("rs20k", path + "rs/20k/rs20k.txt", 6, 50, 3);
//		ExperimentResult configRS50 = reader.execute("rs50k", path + "rs/50k/rs50k.txt", 6, 50, 3);
			

		MultiExperimentAnalyzer analyzer = new MultiExperimentAnalyzer();
		analyzer.addExperimentResult(reader.execute("GA",resultPath + "NSGA/50K/2x/nsga50k2xerror1.txt", instanceFiles.length, 50, 3));
		analyzer.addExperimentResult(reader.execute("CPM",resultPath + "OMS/CPM/CPM_error1.txt", instanceFiles.length, 1, 3));
		analyzer.addExperimentResult(reader.execute("MAR",resultPath + "OMS/Margarine/Margarine_error1.txt", instanceFiles.length, 1, 3));
		analyzer.addExperimentResult(reader.execute("SH",resultPath + "OMS/SecondHalf/SecondHalf_error1.txt", instanceFiles.length, 1, 3));
		
//		analyzer.addExperimentResult(configNSGA50k4x);
//		analyzer.addExperimentResult(configNSGA50k8x);
//		analyzer.addExperimentResult(configRS5);
//		analyzer.addExperimentResult(configRS10);
//		analyzer.addExperimentResult(configRS20);
//		analyzer.addExperimentResult(configRS50);
		analyzer.analyzeInstanceFrontiers();
	}

	private static void runExperimentThread(String name, final String[] ifile, final int cycles, final int eval) 
	{
		new Thread(name)
		{
	        public void run()
	        {
	        	try 
	        	{
					run1(ifile, cycles, eval);
				} 
	        	catch (Exception e) 
	        	{
					e.printStackTrace();
				}
	        }
	    }.start();
	}
		
	public static void run1(String[] instancesFiles, final int cycles, final int maxevaluations) throws Exception
	{		
		Locale.setDefault(new Locale("pt", "BR"));
		final Vector<Project> instances1 = loadInstances(instancesFiles);
//		final Vector<Project> instances2 = loadInstances(instancesFiles);
		
		runManualExperiment(instances1, maxevaluations, cycles);
		
//		runRandomSearchExperimentThread(cycles, maxevaluations, instances1);
//		runNSGAIIExperimentThread(cycles, maxevaluations, instances2);

//		runManualExperiment(instances1, maxevaluations, cycles);		
//		runRandomSearchExperimentThread(cycles, maxevaluations, instances1);
//		runNSGAIIExperimentThread(cycles, maxevaluations, instances2);
	}
	
	public static void showProjectDetails(String[] instancesFiles) throws Exception
	{
		final Vector<Project> instances1 = loadInstances(instancesFiles);
		
		for (Project instance : instances1)
			System.out.println("ACT: " + instance.getActivitiesCount() + "; DEP: " + instance.getDependencyCount());
	}

	private static void runNSGAIIExperimentThread(final int cycles, final int maxevaluations, final Vector<Project> instances2) 
	{
		new Thread("b")
		{
		    public void run()
		    {
		    	try 
		    	{
		    		runNSGAIIExperiment(instances2, cycles, maxevaluations);
				} 
		    	catch (Exception e) 
		    	{
					e.printStackTrace();
				}
		    }
		}.start();
	}

	private static void runRandomSearchExperimentThread(final int cycles, final int maxevaluations, final Vector<Project> instances1) 
	{
		new Thread("a")
		{
	        public void run()
	        {
	        	try 
	        	{
	        		runRandomSearchExperiment(instances1, cycles, maxevaluations);
				} 
	        	catch (Exception e) 
	        	{
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
		String filename = "data/result/" + sdf1.format(new Date()) + "_v" + maxevaluations + "_c" + cycles +  "_nsga_" + instances.get(0).getName()+ "_pop2x_error090.txt";
		
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
		String filename = "data/result/" + sdf1.format(new Date()) + "_v" + maxevaluations + "_c" + cycles + "_rs_" + instances.get(0).getName()+ "_error090.txt";

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
}