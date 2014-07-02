package br.unirio.overwork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import unirio.experiments.multiobjective.analyzer.ExperimentAnalyzer;
import unirio.experiments.multiobjective.reader.ExperimentFileReaderException;
import br.unirio.dominance.reader.ObjectivesComparator;
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
	protected static final int MAXEVALUATIONS = 50;

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
	
	public static final void main(String[] args) throws Exception
	{
		run(instanceFiles);
	}
		
	public static void run(String[] instancesFiles) throws Exception
	{		
		//Locale locale = new Locale("hr", "HR");
		
		Locale.setDefault(new Locale("en", "US"));
		
		Vector<Project> instances = new Vector<Project>();
		
		for (int i = 0; i < instancesFiles.length; i++)
			if (instancesFiles[i].length() > 0)
			{
				Project project = new Project ();
				project = loadInstance(instancesFiles[i]);
				instances.add(project);
			}
		
//		instances.add(createOneDayProject());
		
//		runManualExperiment(instances, 9);
		String file1 = runRandomSearchExperiment(instances);
		String file2 = runNSGAIIExperiment(instances);
		runDominanceAnalysis(instances, file1, file2);
	}
	
	private static void runDominanceAnalysis(Vector<Project> instances, String f1, String f2) throws Exception {
		
		ObjectivesComparator comparator = new ObjectivesComparator();
		comparator.analyze(f1, f2, instances, CYCLES, 3);
		
		
//		Objective o11 = new Objective("",10);
//		Objective o12 = new Objective("",20);
//		Objective o13 = new Objective("",30);
//		
//		Objective o14 = new Objective("",40);
//		Objective o15 = new Objective("",50);
//		Objective o16 = new Objective("",60);
//		
//		Solution s11 = new Solution();
//		Solution s12 = new Solution();
//		
//		s11.addObjective(o11);
//		s11.addObjective(o12);
//		s11.addObjective(o13);
//		
//		s12.addObjective(o14);
//		s12.addObjective(o15);
//		s12.addObjective(o16);
//		
//		ArrayList<Solution> solutions1 = new ArrayList<Solution>();
//		solutions1.add(s11);
//		solutions1.add(s12);
//		
//		Objective o21 = new Objective("",10);
//		Objective o22 = new Objective("",20);
//		Objective o23 = new Objective("",30);
//		
//		Objective o24 = new Objective("",40);
//		Objective o25 = new Objective("",50);
//		Objective o26 = new Objective("",60);
//		
//		Solution s21 = new Solution();
//		Solution s22 = new Solution();
//		s21.addObjective(o24);
//		s21.addObjective(o25);
//		s21.addObjective(o26);
//		
//		s22.addObjective(o21);
//		s22.addObjective(o22);
//		s22.addObjective(o23);
//		
//		ArrayList<Solution> solutions2 = new ArrayList<Solution>();
//		solutions2.add(s21);
//		solutions2.add(s22);
//		
//		for (Solution sol1 : solutions1)
//		{
//			for (Solution sol2 : solutions2)
//			{
//				for (int i = 0; i < sol1.countObjectives(); i++)
//				{					
//					Objective ob1 = new Objective(sol1.getObjective(i).getName(),sol1.getObjective(i).getValue());
//					Objective ob2 = new Objective(sol2.getObjective(i).getName(),sol2.getObjective(i).getValue());
//				
//					if (sol1.getObjective(i).getValue() > sol2.getObjective(i).getValue()){
//						sol2.getObjective(i).addBigger(ob1);							
//						sol1.getObjective(i).addSmaller(ob2);
//					}
//
//					if (sol2.getObjective(i).getValue() > sol1.getObjective(i).getValue()){
//						sol1.getObjective(i).addBigger(ob2);							
//						sol2.getObjective(i).addSmaller(ob1);
//					}
//					
////					if (ob1.getValue() > ob2.getValue()){
////						ob2.addBigger(ob1);							
////						ob1.addSmaller(ob2);
////					}
////					if (ob1.getValue() < ob2.getValue()){
////						ob1.addBigger(ob2);
////						ob2.addSmaller(ob1);
////					}
//				}
//			}
//		}
//		System.out.println("aaaa");
		
	}

		
	protected static String runManualExperiment(Vector<Project> instances, int value) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) + "_val" + value + "_cycles" + CYCLES + "_manual.txt";

		ManualExperiment m = new ManualExperiment(value);
		m.runCycles(filename, instances, 1);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("manual", filename, instances.size(), 1, 3);

		ResultInterpreter interpreterManual = new ResultInterpreter();
		interpreterManual.analyze(filename, instances, 1, 3);
		
		return filename;
	}
	

	protected static String runNSGAIIExperiment(Vector<Project> instances) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) + "_eval" + MAXEVALUATIONS + "_cycles"+ CYCLES + "_nsga.txt";
		
		NSGAIIExperiment nsgaii2 = new NSGAIIExperiment(MAXEVALUATIONS);
		nsgaii2 .runCycles(filename, instances, CYCLES);
		
 		ExperimentAnalyzer analyzer2 = new ExperimentAnalyzer();
		analyzer2.analyze("nsgaii",filename, instances.size(), CYCLES, 3);
		
		ResultInterpreter interpreterNSGA = new ResultInterpreter();
		interpreterNSGA.analyze(filename, instances, CYCLES, 3);
		
		return filename;
	}

	protected static String runRandomSearchExperiment(Vector<Project> instances) throws Exception, ExperimentFileReaderException 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) + "_eval" + MAXEVALUATIONS + "_cycles" + CYCLES + "_rs.txt";

		RandomSearchExperiment rs = new RandomSearchExperiment(MAXEVALUATIONS);
		rs.runCycles(filename, instances, CYCLES);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("rs", filename, instances.size(), CYCLES, 3);

		ResultInterpreter interpreterRS = new ResultInterpreter();
		interpreterRS.analyze(filename, instances, CYCLES, 3);
		
		return filename;
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