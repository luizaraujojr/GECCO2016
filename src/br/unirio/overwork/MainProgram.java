package br.unirio.overwork;

import java.io.OutputStreamWriter;
import java.util.Vector;

import unirio.experiments.monoobjective.execution.FileMonoExperimentListener;
import unirio.experiments.monoobjective.execution.StreamMonoExperimentListener;
import br.unirio.optimization.RandomSearchExperiment;
import br.unirio.overwork.builders.WorkPackageProjectBuilder;
import br.unirio.overwork.instance.model.FunctionPointSystem;
import br.unirio.overwork.instance.reader.InstanceReader;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;
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
				
		String overtime = "";
		
		System.out.println("Makespan\tCost\tOverworking");
	}
		
		public static void run(String instancia) throws Exception
		{
			//loading the project information
			Project project = new Project ();
			project = loadInstance(instancia);
			
			// creating the instance vector
			Vector<Project> instances = new Vector<Project>();
			instances.add(project);
			
			// run the Random Search experiment 
	       	RandomSearchExperiment rs = new RandomSearchExperiment();
	       	rs.addListerner(new FileMonoExperimentListener("saida rs.txt", true));
	       	rs.addListerner(new StreamMonoExperimentListener(new OutputStreamWriter(System.out), true));
	       	rs.run(instances, CYCLES);
		}
		
		public static Project loadInstance(String instancia) throws Exception
		{
			//loading the project information
			InstanceReader reader = new InstanceReader();
			FunctionPointSystem fps = new FunctionPointSystem("aaa");
			fps = reader.run(instancia);
				
			//Temporary
			return createProject();
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