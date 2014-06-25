package br.unirio.overwork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import unirio.experiments.multiobjective.analyzer.ExperimentAnalyzer;
import unirio.experiments.multiobjective.reader.ExperimentFileReaderException;
import br.unirio.optimization.experiment.NSGAIIExperiment;
import br.unirio.optimization.experiment.RandomSearchExperiment;
import br.unirio.optimization.resultInterpretation.ResultInterpreter;
import br.unirio.overwork.builders.controller.SoftwareSystemProjectBuilder;
import br.unirio.overwork.builders.model.SoftwareSystem;
import br.unirio.overwork.builders.model.WorkPackage;
import br.unirio.overwork.builders.reader.Reader;
import br.unirio.overwork.model.base.Project;

public class MainProgram
{
	
	protected static final int CYCLES = 1;
	protected static final int MAXEVALUATIONS = 30;
	protected static String[] instanceFiles =
	{
//		"data/overworking/ACAD.xml",
//		"data/overworking/BOLS.xml",
//		"data/overworking/OPMET.xml",
//		"data/overworking/PARM.xml",
		"data/overworking/PSOA.xml"
//		"data/overworking/WEBAMHS.xml",
//		"data/overworking/WEBMET.xml"
	};
	
	public static final void main(String[] args) throws Exception
	{
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
				//project = createProject();
				
				//population the project vector
				instances.add(project);
			}			
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_hhmmss");
		
		runRandomSearchExperiment(instances, sdf1);
		
		//runNSGAIIExperiment(instances, sdf1);
	}

	private static void runNSGAIIExperiment(Vector<Project> instances,
			SimpleDateFormat sdf1) throws Exception,
			ExperimentFileReaderException {
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) +"_eval"+ MAXEVALUATIONS + "_cycles"+ CYCLES + "_nsga.txt";
		NSGAIIExperiment hsgaii2 = new NSGAIIExperiment();
		hsgaii2.setMaxEvaluations(MAXEVALUATIONS);
		hsgaii2.runCycles(filename, instances, CYCLES);
		
 		ExperimentAnalyzer analyzer2 = new ExperimentAnalyzer();
		analyzer2.analyze("nsgaii",filename, instances.size(), CYCLES, 3);
		
		ResultInterpreter interpreterNSGA = new ResultInterpreter();
		interpreterNSGA.analyze(filename, instances, CYCLES, 3);
	}

	private static void runRandomSearchExperiment(Vector<Project> instances,
			SimpleDateFormat sdf1) throws Exception,
			ExperimentFileReaderException {
		String filename = "data/result/ouput_datetime" + sdf1.format(new Date()) +"_eval"+ MAXEVALUATIONS + "_cycles"+ CYCLES + "_rs.txt";
		RandomSearchExperiment rs = new RandomSearchExperiment();
		rs.setMaxEvaluations(MAXEVALUATIONS);
		rs.runCycles(filename, instances, CYCLES);
		
		ExperimentAnalyzer analyzer1 = new ExperimentAnalyzer();
		analyzer1.analyze("rs",filename, instances.size(), CYCLES, 3);

		ResultInterpreter interpreterRS = new ResultInterpreter();
		interpreterRS.analyze(filename, instances, CYCLES, 3);
	}
	
	public static Project loadInstance(String instancia) throws Exception
	{
		Reader reader = new Reader();
		SoftwareSystem ss = reader.run(instancia);
		SoftwareSystemProjectBuilder builder = new SoftwareSystemProjectBuilder();
		builder.addSoftwareSystem(ss);
		return builder.execute();   
	}
	
	protected static Project createProject()
	{
		SoftwareSystem system = new SoftwareSystem("Projeto de teste");
		
		WorkPackage wp1 = system.addWorkPackage("Usuários");

		wp1.addComponent("usuario", 7);
		wp1.addComponent("Cadastro de usuário", 3);
		wp1.addComponent("Lista de usuários ", 4);
		wp1.addComponent("Consulta de usuário", 4);

		WorkPackage wp2 = system.addWorkPackage("Professores");

		wp2.addComponent("professor", 7);
		wp2.addComponent("Cadastro de professor", 4);
		wp2.addComponent("Lista de professores", 4);
		wp2.addComponent("Consulta de professor", 5);
		
		WorkPackage wp3 = system.addWorkPackage("Áreas");

		wp3.addComponent("area", 7);
		wp3.addComponent("Cadastro de área", 4);
		wp3.addComponent("Lista de áreas", 4);
		wp3.addComponent("Lista de sub-áreas", 4);
		wp3.addComponent("Consulta de área", 4);
		
		WorkPackage wp4 = system.addWorkPackage("Aluno");

		wp4.addComponent("aluno", 10);
		wp4.addComponent("Cadastro de aluno", 6);
		wp4.addComponent("Consulta de aluno", 7);	

		WorkPackage wp5 = system.addWorkPackage("Disciplinas");

		wp5.addComponent("disciplina", 7);
		wp5.addComponent("Cadastro de disciplina", 4);
		wp5.addComponent("Lista de disciplinas", 4);
		wp5.addComponent("Consulta de disciplina", 5);
		
		WorkPackage wp6 = system.addWorkPackage("Turmas");

		wp6.addComponent("turma", 7);
		wp6.addComponent("turmasolicitada", 7);
		wp6.addComponent("Cadastro de turma", 6);
		wp6.addComponent("Cadastro de turma solicitada", 6);
		wp6.addComponent("Lista de turmas", 4);
		wp6.addComponent("Consulta de turma", 7);
		wp6.addComponent("Lista de turmas solicitadas", 4);
		
		WorkPackage wp7 = system.addWorkPackage("Inscrições");

		wp7.addComponent("inscricao", 7);
		wp7.addComponent("Cadastro de inscrição", 6);
		wp7.addComponent("Consulta de inscrição", 7);
		wp7.addComponent("Geração de comprovante", 7);
			
		SoftwareSystemProjectBuilder builder = new SoftwareSystemProjectBuilder();
		
		builder.addSoftwareSystem(system);
		
		return builder.execute();

	}
}