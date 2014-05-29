package br.unirio.overwork;

import java.util.Vector;

import unirio.experiments.multiobjective.analyzer.ExperimentAnalyzer;
import br.unirio.optimization.experiment.HillClimbingExperiment;
import br.unirio.optimization.experiment.NSGAIIExperiment;
import br.unirio.optimization.experiment.RandomSearchExperiment;
import br.unirio.overwork.builders.WorkPackage;
import br.unirio.overwork.builders.WorkPackageProjectBuilder;
import br.unirio.overwork.instance.calculator.FunctionPointCalculator;
import br.unirio.overwork.instance.model.FunctionPointSystem;
import br.unirio.overwork.instance.model.transaction.FileReference;
import br.unirio.overwork.instance.model.transaction.Transaction;
import br.unirio.overwork.instance.reader.InstanceReader;
import br.unirio.overwork.instance.report.Report;
import br.unirio.overwork.instance.report.ReportDataFunction;
import br.unirio.overwork.instance.report.ReportTransactionFunction;
import br.unirio.overwork.model.base.Project;

public class MainProgram
{
	protected static final int CYCLES = 5;
	protected static final int MAXEVALUATIONS = 2000;
	protected static String[] instanceFiles =
	{
//	 		"data/instances/ACAD/functions-point.xml",
//	 		"data/instances/BOLS/functions-point.xml",
//	 		"data/instances/PARM/functions-point.xml",
//	 		"data/instances/PSOA/functions-point.xml",
		"data/instances/WEBMET/functions-point.xml",
		"data/instances/OPMET/functions-point.xml"
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
		
		ExperimentAnalyzer analyzer2 = new ExperimentAnalyzer();
		analyzer2.analyze("hc", "saida hc.txt", instances.size(), CYCLES, 3);
		
		RandomSearchExperiment rs = new RandomSearchExperiment();
		rs.runCycles("saida rs.txt", instances, CYCLES);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("rs", "saida rs.txt", instances.size(), CYCLES, 3);
		
		NSGAIIExperiment hsgaii2 = new NSGAIIExperiment();
		hsgaii2.runCycles("saida nsgaii2.txt", instances, CYCLES);
		
		ExperimentAnalyzer analyzer3 = new ExperimentAnalyzer();
		analyzer3.analyze("NSGAII", "saida nsgaii2.txt", instances.size(), CYCLES, 3);	       	
	}
	
	public static Project loadInstance(String instancia) throws Exception
	{
		//loading the project information
		InstanceReader reader = new InstanceReader();
		FunctionPointSystem fps = new FunctionPointSystem(instancia.split("/")[2]);
		
		fps = reader.run(instancia);
	
		FunctionPointCalculator fpc = new FunctionPointCalculator();
		
		Report report = new Report();
		report = fpc.calculate(fps);
		
		WorkPackageProjectBuilder builder = new WorkPackageProjectBuilder();
		 
		
		for  (ReportDataFunction df: report.getDataFunctions())
		{
			WorkPackage workPackage = builder.addWorkPackage(df.getName()).addDataFunction(df.getName(), df.getFunctionPoints());
		}

		
		for  (ReportTransactionFunction tr: report.getTransactionFunctions())
		{	
			for (Transaction _transaction : fps.getTransactions())
			{
				if (_transaction.getName() == tr.getName())
				{
					for (FileReference fr: _transaction.getFileReferences())
					{
						WorkPackage workPackage = builder.getWorkPackagebyName(fr.getName());
						workPackage.addTransactionalFunction(tr.getName(), tr.getFunctionPoints());
					}	
				}		
			}
				
			
			//System.out.println(tr.getName());
			//System.out.println(tr.getFileReference(0).getName());
			
		//	
		}
					
		return builder.execute(); //createProject();  
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