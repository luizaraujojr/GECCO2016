package br.unirio.overwork;

import br.unirio.overwork.builders.WorkPackageProjectBuilder;
import br.unirio.overwork.model.Activity;
import br.unirio.overwork.model.Developer;
import br.unirio.overwork.model.Project;
import br.unirio.overwork.model.scenarios.ScenarioCommunicationOverhead;
import br.unirio.overwork.simulation.Simulator;

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
		Project project = createProject();

		Simulator simulator = new Simulator();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());
		
		ScenarioCommunicationOverhead scenarioCommunicationOverhead = new ScenarioCommunicationOverhead();
		scenarioCommunicationOverhead.connect(project.getActivities());
		
//		ScenarioOverworking scenarioOverworking = new ScenarioOverworking(12);
//		scenarioOverworking.connect(project.getActivities());
		
//		ScenarioExhaution scenarioExhaustion = new ScenarioExhaution();
//		scenarioExhaustion.connect(project.getActivities());

//		ScenarioErrorRegeneration scenarioEGIniciais = new ScenarioErrorRegeneration(0.85);
//		ScenarioErrorRegeneration scenarioEGCodificacao = new ScenarioErrorRegeneration(0.326);
//		
//		for (Activity activity : project.getActivitiesByPrefix("Requisitos"))
//			scenarioEGIniciais.connect((ActivityDevelopment) activity);
//		
//		for (Activity activity : project.getActivitiesByPrefix("Projeto"))
//			scenarioEGIniciais.connect((ActivityDevelopment) activity);
//
//		for (Activity activity : project.getActivitiesByPrefix("Codificacao"))
//			scenarioEGCodificacao.connect((ActivityDevelopment) activity);

		simulator.init();
		
		while (!project.isConcluded())
			simulator.run();
		
		for (Activity a : project.getActivities())
			System.out.println(a.toString());
	}
}