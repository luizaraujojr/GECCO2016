package br.unirio.overwork;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import br.unirio.overwork.builders.WorkPackageProjectBuilder;
import br.unirio.overwork.instance.reader.InstanceReader;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;
import br.unirio.overwork.model.scenarios.ScenarioExhaution;
import br.unirio.overwork.model.scenarios.ScenarioOverworking;
import br.unirio.simulation.SimulationEngine;

public class MainProgram
{
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
	
	public static final void main(String[] args) throws Exception
	{
		InstanceReader reader = new InstanceReader();
		reader.run("data/instances/ACAD/functions-point.xml");
		reader.run("data/instances/BOLS/functions-point.xml");
		reader.run("data/instances/PARM/functions-point.xml");
		reader.run("data/instances/PSOA/functions-point.xml");
		
		Project project = createProject();
		
		SimulationEngine simulator = new SimulationEngine();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());
		
		ScenarioOverworking scenarioOverworking = new ScenarioOverworking(12);
		scenarioOverworking.connect(project.getActivities());
		
		ScenarioExhaution scenarioExhaustion = new ScenarioExhaution();
		scenarioExhaustion.connect(project.getActivities());

		simulator.init();
		
		while (!project.isConcluded())
			simulator.run();
		
		NumberFormat nf2 = new DecimalFormat("0.00");
		System.out.println("Makespan\tCost\tOverworking");
		System.out.println(nf2.format(project.getMakespan()) + "\t" + nf2.format(project.getCost()) + "\t" + nf2.format(project.getOverworking()));
	}
}