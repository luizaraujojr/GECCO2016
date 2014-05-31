package br.unirio.overwork;

import java.util.Vector;

import unirio.experiments.multiobjective.analyzer.ExperimentAnalyzer;
import br.unirio.instance.Reader;
import br.unirio.optimization.experiment.HillClimbingExperiment;
import br.unirio.optimization.experiment.NSGAIIExperiment;
import br.unirio.optimization.experiment.RandomSearchExperiment;
import br.unirio.overwork.builders.controller.SoftwareSystemProjectBuilder;
import br.unirio.overwork.builders.model.SoftwareSystem;
import br.unirio.overwork.builders.model.WorkPackage;
import br.unirio.overwork.model.base.Project;

public class MainProgram
{
	protected static final int CYCLES = 20;
	protected static final int MAXEVALUATIONS = 10000;
	protected static String[] instanceFiles =
	{
//	 		"data/instances/ACAD/functions-point.xml",
//	 		"data/instances/BOLS/functions-point.xml",
//	 		"data/instances/PARM/functions-point.xml",
//	 		"data/instances/PSOA/functions-point.xml",
		"data/overworking/ACAD.xml"
		//"data/instances/OPMET/functions-point.xml"
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
		hc.setMaxEvaluations(MAXEVALUATIONS);
		hc.runCycles("saida hc.txt", instances, CYCLES);
		
		ExperimentAnalyzer analyzer2 = new ExperimentAnalyzer();
		analyzer2.analyze("hc", "saida hc.txt", instances.size(), CYCLES, 3);
		
		RandomSearchExperiment rs = new RandomSearchExperiment();
		rs.setMaxEvaluations(MAXEVALUATIONS);
		rs.runCycles("saida rs.txt", instances, CYCLES);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("rs", "saida rs.txt", instances.size(), CYCLES, 3);
		
		NSGAIIExperiment hsgaii2 = new NSGAIIExperiment();
		hsgaii2.setMaxEvaluations(MAXEVALUATIONS);
		hsgaii2.runCycles("saida nsgaii2.txt", instances, CYCLES);
		
		ExperimentAnalyzer analyzer3 = new ExperimentAnalyzer();
		analyzer3.analyze("NSGAII", "saida nsgaii2.txt", instances.size(), CYCLES, 3);	       	
	}
	
	public static Project loadInstance(String instancia) throws Exception
	{
		//loading the project information
		Reader reader = new Reader();
		SoftwareSystem ss = new SoftwareSystem(instancia.split("/")[2]);
		
		ss = reader.run(instancia);
	
		SoftwareSystemProjectBuilder builder = new SoftwareSystemProjectBuilder();
		
		builder.addSoftwareSystem(ss);

	return builder.execute();   
//	return createProject(); 
	}
	
	protected static Project createProject()
	{
		SoftwareSystem system = new SoftwareSystem("Projeto de teste");
		
		WorkPackage wp1 = system.addWorkPackage("Usuários");

		wp1.addRequirement("usuario", 7);
		wp1.addRequirement("Cadastro de usuário", 3);
		wp1.addRequirement("Lista de usuários ", 4);
		wp1.addRequirement("Consulta de usuário", 4);

		WorkPackage wp2 = system.addWorkPackage("Professores");

		wp2.addRequirement("professor", 7);
		wp2.addRequirement("Cadastro de professor", 4);
		wp2.addRequirement("Lista de professores", 4);
		wp2.addRequirement("Consulta de professor", 5);
		
		WorkPackage wp3 = system.addWorkPackage("Áreas");

		wp3.addRequirement("area", 7);
		wp3.addRequirement("Cadastro de área", 4);
		wp3.addRequirement("Lista de áreas", 4);
		wp3.addRequirement("Lista de sub-áreas", 4);
		wp3.addRequirement("Consulta de área", 4);
		
		WorkPackage wp4 = system.addWorkPackage("Aluno");

		wp4.addRequirement("aluno", 10);
		wp4.addRequirement("Cadastro de aluno", 6);
		wp4.addRequirement("Consulta de aluno", 7);	

		WorkPackage wp5 = system.addWorkPackage("Disciplinas");

		wp5.addRequirement("disciplina", 7);
		wp5.addRequirement("Cadastro de disciplina", 4);
		wp5.addRequirement("Lista de disciplinas", 4);
		wp5.addRequirement("Consulta de disciplina", 5);
		
		WorkPackage wp6 = system.addWorkPackage("Turmas");

		wp6.addRequirement("turma", 7);
		wp6.addRequirement("turmasolicitada", 7);
		wp6.addRequirement("Cadastro de turma", 6);
		wp6.addRequirement("Cadastro de turma solicitada", 6);
		wp6.addRequirement("Lista de turmas", 4);
		wp6.addRequirement("Consulta de turma", 7);
		wp6.addRequirement("Lista de turmas solicitadas", 4);
		
		WorkPackage wp7 = system.addWorkPackage("Inscrições");

		wp7.addRequirement("inscricao", 7);
		wp7.addRequirement("Cadastro de inscrição", 6);
		wp7.addRequirement("Consulta de inscrição", 7);
		wp7.addRequirement("Geração de comprovante", 7);
			
		SoftwareSystemProjectBuilder builder = new SoftwareSystemProjectBuilder();
		
		builder.addSoftwareSystem(system);
		
		return builder.execute();

	}
}