package br.unirio.overwork;

import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

import unirio.experiments.monoobjective.execution.FileMonoExperimentListener;
import unirio.experiments.monoobjective.execution.StreamMonoExperimentListener;
import br.unirio.optimization.ProjectSolution;
import br.unirio.optimization.RandomSearchExperiment;
import br.unirio.overwork.builders.WorkPackageProjectBuilder;
<<<<<<< HEAD
import br.unirio.overwork.model.Developer;
import br.unirio.overwork.model.Project;
=======
import br.unirio.overwork.instance.reader.InstanceReader;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;
import br.unirio.overwork.model.scenarios.ScenarioExhaution;
>>>>>>> 01b098677229606f1983375d5beed6ee3ca623ee
import br.unirio.overwork.model.scenarios.ScenarioOverworking;
import br.unirio.reader.InstanceReader;
import br.unirio.simulation.SimulationEngine;

public class MainProgram
{
	protected static final int CYCLES = 10;
	protected static String[] instanciasAleatorias =
		{
	 		"C:\\workspace\\OptimizationAlgorithms\\trunk\\data\\Instance.txt",
		};
	

	
	public static final void main(String[] args) throws Exception
	{
<<<<<<< HEAD
		for (int i = 0; i < instanciasAleatorias.length; i++)
			if (instanciasAleatorias[i].length() > 0)
				run(instanciasAleatorias[i]);
=======
		InstanceReader reader = new InstanceReader();
		reader.run("data/instances/ACAD/functions-point.xml");
		reader.run("data/instances/BOLS/functions-point.xml");
		reader.run("data/instances/PARM/functions-point.xml");
		reader.run("data/instances/PSOA/functions-point.xml");
		
		Project project = createProject();
		
		SimulationEngine simulator = new SimulationEngine();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
>>>>>>> 01b098677229606f1983375d5beed6ee3ca623ee
		
		
		
		String overtime = "";
		//instance loaded and project created
		System.out.println("Makespan\tCost\tOverworking");
//		for (int j = 0; j < 1; j++)
//		{
//			overtime = "";
//			
//			Solution solution = new Solution (project);
//			solution.randomize();
//					
//			SimulationEngine simulator = new SimulationEngine();
//	
//			for (Developer developer : project.getDevelopers())
//				simulator.addResource(developer.getEffort());
//			
//			simulator.addSimulationObjects(project.getActivities());
//			
//			for (int i = 0; i < project.countActivities(); i++)
//			{
//				ScenarioOverworking scenarioOverworking = new ScenarioOverworking(solution.getActivitiesOverworkAllocation(i));
//				scenarioOverworking.connect(project.getActivity(i));
//				
//				overtime = overtime + solution.getActivitiesOverworkAllocation(i)  + "\t";
//			}
//					
//	//		ScenarioExhaution scenarioExhaustion = new ScenarioExhaution();
//	//		scenarioExhaustion.connect(project.getActivities());
//	
//			simulator.init();
//			
//			while (!project.isConcluded())
//				simulator.run();
//			
//			NumberFormat nf2 = new DecimalFormat("0.00");
//			
//			
//			System.out.println(nf2.format(project.getMakespan()) + "\t" + nf2.format(project.getCost()) + "\t" + nf2.format(project.getOverworking()) + "\t" + overtime);
		}
		
		public static void run(String instancia) throws Exception
		{
			//loading the project information
			Project project = new Project ();
			project = loadInstance(instancia);
			
			// Cria o vetor de instâncias
			Vector<Project> instances = new Vector<Project>();
			instances.add(project);
			
			// Executa os experimentos com Random Search
	       	RandomSearchExperiment rs = new RandomSearchExperiment();
//	       	rs.addListerner(new FileMonoExperimentListener("saida rs.txt", true));
//	       	rs.addListerner(new StreamMonoExperimentListener(new OutputStreamWriter(System.out), true));
	       	rs.run(instances, CYCLES);
			
			
		}
		public static Project loadInstance(String instancia) throws Exception
		{
			//loading the project information
			InstanceReader reader = new InstanceReader();
			return reader.execute(instancia);
		}
		
}