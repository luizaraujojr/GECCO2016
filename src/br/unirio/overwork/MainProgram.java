package br.unirio.overwork;

import br.unirio.overwork.builders.WorkPackageProjectBuilder;
import br.unirio.overwork.model.Activity;
import br.unirio.overwork.model.Developer;
import br.unirio.overwork.model.Project;
import br.unirio.overwork.model.scenarios.ScenarioErrorRegeneration;
import br.unirio.overwork.simulation.Simulator;

public class MainProgram
{
	private static Project createProject()
	{
		WorkPackageProjectBuilder builder = new WorkPackageProjectBuilder();
		
		builder.addWorkPackage("Usu�rios")
			.addDataFunction("usuario", 7)
			.addTransactionalFunction("Cadastro de usu�rio", 3)
			.addTransactionalFunction("Lista de usu�rios ", 4)
			.addTransactionalFunction("Consulta de usu�rio", 4);

		builder.addWorkPackage("Professores")
			.addDataFunction("professor", 7)
			.addTransactionalFunction("Cadastro de professor", 4)
			.addTransactionalFunction("Lista de professores", 4)
			.addTransactionalFunction("Consulta de professor", 5);
		
		builder.addWorkPackage("�reas")
			.addDataFunction("area", 7)
			.addTransactionalFunction("Cadastro de �rea", 4)
			.addTransactionalFunction("Lista de �reas", 4)
			.addTransactionalFunction("Lista de sub-�reas", 4)
			.addTransactionalFunction("Consulta de �rea", 4);
		
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
		
		builder.addWorkPackage("Inscri��es")
			.addDataFunction("inscricao", 7)
			.addTransactionalFunction("Cadastro de inscri��o", 6)
			.addTransactionalFunction("Consulta de inscri��o", 7)
			.addTransactionalFunction("Gera��o de comprovante", 7);
		
		return builder.execute();
	}
	
	public static final void main(String[] args) throws Exception
	{
		Project project = createProject();

		Simulator simulator = new Simulator();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());
		
//		ScenarioOverworking scenarioOverworking = new ScenarioOverworking(12);
//		scenarioOverworking.connect(project.getActivities());
//		
//		ScenarioExhaution scenarioExhaustion = new ScenarioExhaution();
//		scenarioExhaustion.connect(project.getActivities());

		ScenarioErrorRegeneration scenarioEGIniciais = new ScenarioErrorRegeneration(0.85);
		scenarioEGIniciais.connect(project.getActivitiesByPrefix("Requisitos"));
		scenarioEGIniciais.connect(project.getActivitiesByPrefix("Projeto"));

		ScenarioErrorRegeneration scenarioEGCodificacao = new ScenarioErrorRegeneration(0.326);
		scenarioEGCodificacao.connect(project.getActivitiesByPrefix("Codificacao"));

		simulator.init();
		
		while (!project.isConcluded())
			simulator.run();
		
		for (Activity a : project.getActivities())
			System.out.println(a.toString());
	}
}