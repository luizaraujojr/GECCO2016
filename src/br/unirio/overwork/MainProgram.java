package br.unirio.overwork;

import java.util.Vector;

import br.unirio.optimization.RandomSearchExperiment;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;
import br.unirio.reader.InstanceReader;
import br.unirio.simulation.SimulationEngine;

public class MainProgram
{
	protected static final int CYCLES = 10;
	protected static String[] instanceFiles =
		{
	 		"data/instances/ACAD/functions-point.xml",
	 		"data/instances/BOLS/functions-point.xml",
	 		"data/instances/PARM/functions-point.xml",
	 		"data/instances/PSOA/functions-point.xml"
		};
	

	
	public static final void main(String[] args) throws Exception
	{
		for (int i = 0; i < instanceFiles.length; i++)
			if (instanceFiles[i].length() > 0)
				run(instanceFiles[i]);
		
		Project project = createProject();
		
		SimulationEngine simulator = new SimulationEngine();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());		
		
		
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