package br.unirio.overwork;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.util.Vector;

import unirio.experiments.monoobjective.execution.FileMonoExperimentListener;
import unirio.experiments.monoobjective.execution.StreamMonoExperimentListener;
import unirio.experiments.multiobjective.analyzer.ExperimentAnalyzer;
import br.unirio.analyzer.ProjectExperimentFileReader;
import br.unirio.optimization.experiment.HillClimbingExperiment;
import br.unirio.optimization.experiment.NSGAIIExperiment;
import br.unirio.optimization.experiment.RandomSearchExperiment;
import br.unirio.overwork.builders.WorkPackage;
import br.unirio.overwork.builders.WorkPackageProjectBuilder;
import br.unirio.overwork.instance.calculator.FunctionPointCalculator;
import br.unirio.overwork.instance.model.FunctionPointSystem;
import br.unirio.overwork.instance.model.data.DataFunction;
import br.unirio.overwork.instance.model.transaction.Transaction;
import br.unirio.overwork.instance.reader.InstanceReader;
import br.unirio.overwork.model.base.Project;

@SuppressWarnings("unused")
public class MainProgram
{
	private static final int CYCLES = 10;
	
	private static final int MAXEVALUATIONS = 250;
	
	private static String[] instanceFiles =
	{
	 	"data/instances/ACAD/functions-point.xml",
//	 	"data/instances/BOLS/functions-point.xml",
//	 	"data/instances/PARM/functions-point.xml",
//	 	"data/instances/PSOA/functions-point.xml"
	};
	
	public static final void main(String[] args) throws Exception
	{
//		for (int i = 0; i < instanceFiles.length; i++)
//			if (instanceFiles[i].length() > 0)
				run(instanceFiles);
	}
	
	
	public static void run(String[] instancesFiles) throws Exception
	{
		//creating the instance vector
		
		Vector<Project> instances = new Vector<Project>();
		for (int i = 0; i < instancesFiles.length; i++)
			if (instancesFiles[i].length() > 0)
			{
				//loading the project information
				Project project = new Project ();
				project = loadInstance(instancesFiles[i]);
				
				//population the project vector
				instances.add(project);
			}			
		
		
		HillClimbingExperiment hc = new HillClimbingExperiment();
		hc.runCycles("saida hc.txt", instances, CYCLES);
		
		ExperimentAnalyzer analyzer2 = new ExperimentAnalyzer(new ProjectExperimentFileReader());
		analyzer2.analyze("hc", "saida hc.txt", instances.size(), CYCLES, 3);
		
		RandomSearchExperiment rs = new RandomSearchExperiment();
		rs.runCycles("saida rs.txt", instances, CYCLES);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer(new ProjectExperimentFileReader());
		analyzer1.analyze("rs", "saida rs.txt", instances.size(), CYCLES, 3);
		
		NSGAIIExperiment hsgaii2 = new NSGAIIExperiment();
		hsgaii2.runCycles("saida nsgaii2.txt", instances, CYCLES);
		
		ExperimentAnalyzer analyzer3 = new ExperimentAnalyzer(new ProjectExperimentFileReader());
		analyzer3.analyze("NSGAII", "saida nsgaii2.txt", instances.size(), CYCLES, 3);	       	
	}
	
	
//	private static void optimize(Project project) throws Exception, FileNotFoundException
//	{
//		// creating the instance vector
//		Vector<Project> instances = new Vector<Project>();
//		instances.add(project);
//		
//		// run the Random Search experiment
//		System.out.println("RS");
//       	RandomSearchExperiment rse = new RandomSearchExperiment();
//       	rse.run(project, CYCLES, MAXEVALUATIONS);
//       	
//       	// run the Hill Climbing experiment
//       	System.out.println("HC");
//       	HillClimbingExperiment hce = new HillClimbingExperiment();
//       	hce.run(project, CYCLES, MAXEVALUATIONS);
//       	
//       	// run the Genetic algorithm experiment 
//       	System.out.println("GA");
//       	GeneticAlgorithmExperiment gae = new GeneticAlgorithmExperiment();
//       	gae.addListerner(new FileMonoExperimentListener("saida ga.txt", true));
//       	gae.addListerner(new StreamMonoExperimentListener(new OutputStreamWriter(System.out), true));
//       	gae.run(instances, CYCLES);
//	}
	
	public static Project loadInstance(String instancia) throws Exception
	{
		//loading the project information
		InstanceReader reader = new InstanceReader();
		FunctionPointSystem fps = new FunctionPointSystem(instancia.split("/")[2]);
		fps = reader.run(instancia);
	
		FunctionPointCalculator fpc = new FunctionPointCalculator();
		
		fpc.calculate(fps);
		
		WorkPackageProjectBuilder builder = new WorkPackageProjectBuilder();
		 
		
		for  (DataFunction df: fps.getDataFunctions())
		{
	//		WorkPackage workPackage = builder.addWorkPackage(df.getName()).addDataFunction(df.getName(), df.getFp());
		}
		
		for  (Transaction tr: fps.getTransactions())
		{				
		//	WorkPackage workPackage = builder.getWorkPackagebyName(tr.getFileReference(0).getName());
			//System.out.println(tr.getName());
			//System.out.println(tr.getFileReference(0).getName());
			
			//workPackage.addTransactionalFunction(tr.getName(), tr.getFp());
		}
					
		return createProject(); //builder.execute();

	}
	
	private static Project createProject()
	{
		WorkPackageProjectBuilder builder = new WorkPackageProjectBuilder();
		
		builder.addWorkPackage("Usuários")
			.addDataFunction("usuario", 7)
			.addTransactionalFunction("Cadastro de usuário", 3)
			.addTransactionalFunction("Lista de usuários ", 4)
			.addTransactionalFunction("Consulta de usuário", 4);

		builder.addWorkPackage("Professores")
			.addDataFunction("professor", 7)
			.addTransactionalFunction("Cadastro de professor", 4)
			.addTransactionalFunction("Lista de professores", 4)
			.addTransactionalFunction("Consulta de professor", 5);
		
		builder.addWorkPackage("Áreas")
			.addDataFunction("area", 7)
			.addTransactionalFunction("Cadastro de área", 4)
			.addTransactionalFunction("Lista de áreas", 4)
			.addTransactionalFunction("Lista de sub-áreas", 4)
			.addTransactionalFunction("Consulta de área", 4);
		
		builder.addWorkPackage("Aluno")
			.addDataFunction("aluno", 10)
			.addTransactionalFunction("Cadastro de aluno", 6)
			.addTransactionalFunction("Consulta de aluno", 7);
		
		builder.addWorkPackage("Disciplinas")
			.addDataFunction("disciplina", 7)
			.addTransactionalFunction("Cadastro de disciplina", 4)
			.addTransactionalFunction("Lista de disciplinas", 4)
			.addTransactionalFunction("Consulta de disciplina", 5);
		
		builder.addWorkPackage("Turmas")
			.addDataFunction("turma", 7)
			.addDataFunction("turmasolicitada", 7)
			.addTransactionalFunction("Cadastro de turma", 6)
			.addTransactionalFunction("Cadastro de turma solicitada", 6)
			.addTransactionalFunction("Lista de turmas", 4)
			.addTransactionalFunction("Consulta de turma", 7)
			.addTransactionalFunction("Lista de turmas solicitadas", 4);
		
		builder.addWorkPackage("Inscrições")
			.addDataFunction("inscricao", 7)
			.addTransactionalFunction("Cadastro de inscrição", 6)
			.addTransactionalFunction("Consulta de inscrição", 7)
			.addTransactionalFunction("Geração de comprovante", 7);
		
		return builder.execute();
	}
}