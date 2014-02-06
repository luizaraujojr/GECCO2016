package br.unirio.overwork.test;

import junit.framework.TestCase;
import br.unirio.overwork.builders.WorkPackageProjectBuilder;
import br.unirio.overwork.model.Developer;
import br.unirio.overwork.model.Project;
import br.unirio.overwork.model.scenarios.ScenarioExhaution;
import br.unirio.overwork.model.scenarios.ScenarioOverworking;
import br.unirio.overwork.simulation.Simulator;

public class TestScenarioExhaustion extends TestCase 
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

	public void testAll() throws Exception
	{
		Project project = createProject();
		
		Simulator simulator = new Simulator();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());
		new ScenarioOverworking(12).connect(project.getActivities());
		new ScenarioExhaution().connect(project.getActivities());
		simulator.init();
		
		while (!project.isConcluded())
			simulator.run();
		
		assertEquals(0.0, project.getActivity("Requisitos Inscrições").getStartExecutionTime(), 0.1);	
		assertEquals(1.0, project.getActivity("Requisitos Inscrições").getFinishingTime(), 0.1);	
		assertEquals(12.9, project.getActivity("Requisitos Inscrições").getErrors(), 0.1);
		
		assertEquals(1.0, project.getActivity("Requisitos Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(2.6, project.getActivity("Requisitos Turmas").getFinishingTime(), 0.1);	
		assertEquals(19.7, project.getActivity("Requisitos Turmas").getErrors(), 0.1);
		
		assertEquals(2.6, project.getActivity("Requisitos Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(3.4, project.getActivity("Requisitos Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(9.6, project.getActivity("Requisitos Disciplinas").getErrors(), 0.1);
		
		assertEquals(3.4, project.getActivity("Requisitos Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(4.3, project.getActivity("Requisitos Aluno").getFinishingTime(), 0.1);	
		assertEquals(11.0, project.getActivity("Requisitos Aluno").getErrors(), 0.1);
		
		assertEquals(4.3, project.getActivity("Requisitos Áreas").getStartExecutionTime(), 0.1);	
		assertEquals(5.2, project.getActivity("Requisitos Áreas").getFinishingTime(), 0.1);	
		assertEquals(11.0, project.getActivity("Requisitos Áreas").getErrors(), 0.1);
		
		assertEquals(5.2, project.getActivity("Requisitos Professores").getStartExecutionTime(), 0.1);	
		assertEquals(6.0, project.getActivity("Requisitos Professores").getFinishingTime(), 0.1);	
		assertEquals(9.6, project.getActivity("Requisitos Professores").getErrors(), 0.1);
		
		assertEquals(6.0, project.getActivity("Requisitos Usuários").getStartExecutionTime(), 0.1);	
		assertEquals(6.7, project.getActivity("Requisitos Usuários").getFinishingTime(), 0.1);	
		assertEquals(8.6, project.getActivity("Requisitos Usuários").getErrors(), 0.1);
		
		assertEquals(6.7, project.getActivity("Projeto Inscrições").getStartExecutionTime(), 0.1);	
		assertEquals(8.1, project.getActivity("Projeto Inscrições").getFinishingTime(), 0.1);	
		assertEquals(29.4, project.getActivity("Projeto Inscrições").getErrors(), 0.1);
		
		assertEquals(8.1, project.getActivity("Projeto Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(10.1, project.getActivity("Projeto Turmas").getFinishingTime(), 0.1);	
		assertEquals(44.6, project.getActivity("Projeto Turmas").getErrors(), 0.1);
		
		assertEquals(10.1, project.getActivity("Projeto Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(11.1, project.getActivity("Projeto Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(21.8, project.getActivity("Projeto Disciplinas").getErrors(), 0.1);
		
		assertEquals(11.1, project.getActivity("Projeto Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(12.2, project.getActivity("Projeto Aluno").getFinishingTime(), 0.1);	
		assertEquals(25.0, project.getActivity("Projeto Aluno").getErrors(), 0.1);
		
		assertEquals(12.2, project.getActivity("Projeto Áreas").getStartExecutionTime(), 0.1);	
		assertEquals(13.4, project.getActivity("Projeto Áreas").getFinishingTime(), 0.1);	
		assertEquals(25.0, project.getActivity("Projeto Áreas").getErrors(), 0.1);
		
		assertEquals(13.4, project.getActivity("Projeto Professores").getStartExecutionTime(), 0.1);	
		assertEquals(14.4, project.getActivity("Projeto Professores").getFinishingTime(), 0.1);	
		assertEquals(21.8, project.getActivity("Projeto Professores").getErrors(), 0.1);
		
		assertEquals(14.4, project.getActivity("Projeto Usuários").getStartExecutionTime(), 0.1);	
		assertEquals(15.3, project.getActivity("Projeto Usuários").getFinishingTime(), 0.1);	
		assertEquals(19.6, project.getActivity("Projeto Usuários").getErrors(), 0.1);
		
		assertEquals(15.3, project.getActivity("Codificacao Inscrições").getStartExecutionTime(), 0.1);	
		assertEquals(22.0, project.getActivity("Codificacao Inscrições").getFinishingTime(), 0.1);	
		assertEquals(86.0, project.getActivity("Codificacao Inscrições").getErrors(), 0.1);
		
		assertEquals(22.0, project.getActivity("Codificacao Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(34.7, project.getActivity("Codificacao Turmas").getFinishingTime(), 0.1);	
		assertEquals(106.4, project.getActivity("Codificacao Turmas").getErrors(), 0.1);
		
		assertEquals(34.7, project.getActivity("Codificacao Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(40.0, project.getActivity("Codificacao Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(60.0, project.getActivity("Codificacao Disciplinas").getErrors(), 0.1);
		
		assertEquals(40.0, project.getActivity("Codificacao Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(44.7, project.getActivity("Codificacao Aluno").getFinishingTime(), 0.1);	
		assertEquals(82.8, project.getActivity("Codificacao Aluno").getErrors(), 0.1);
		
		assertEquals(44.7, project.getActivity("Codificacao Áreas").getStartExecutionTime(), 0.1);	
		assertEquals(49.5, project.getActivity("Codificacao Áreas").getFinishingTime(), 0.1);	
		assertEquals(82.8, project.getActivity("Codificacao Áreas").getErrors(), 0.1);
		
		assertEquals(49.5, project.getActivity("Codificacao Professores").getStartExecutionTime(), 0.1);	
		assertEquals(53.6, project.getActivity("Codificacao Professores").getFinishingTime(), 0.1);	
		assertEquals(72.0, project.getActivity("Codificacao Professores").getErrors(), 0.1);
		
		assertEquals(53.6, project.getActivity("Codificacao Usuários").getStartExecutionTime(), 0.1);	
		assertEquals(57.3, project.getActivity("Codificacao Usuários").getFinishingTime(), 0.1);	
		assertEquals(64.8, project.getActivity("Codificacao Usuários").getErrors(), 0.1);
		
		assertEquals(57.3, project.getActivity("Testes Inscrições").getStartExecutionTime(), 0.1);	
		assertEquals(68.2, project.getActivity("Testes Inscrições").getFinishingTime(), 0.1);	
		assertEquals(0.0, project.getActivity("Testes Inscrições").getErrors(), 0.1);
		
		assertEquals(68.2, project.getActivity("Testes Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(80.6, project.getActivity("Testes Turmas").getFinishingTime(), 0.1);	
		assertEquals(0.0, project.getActivity("Testes Turmas").getErrors(), 0.1);
		
		assertEquals(80.6, project.getActivity("Testes Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(85.8, project.getActivity("Testes Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(0.0, project.getActivity("Testes Disciplinas").getErrors(), 0.1);
		
		assertEquals(85.8, project.getActivity("Testes Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(93.0, project.getActivity("Testes Aluno").getFinishingTime(), 0.1);	
		assertEquals(0.0, project.getActivity("Testes Aluno").getErrors(), 0.1);
		
		assertEquals(93.0, project.getActivity("Testes Áreas").getStartExecutionTime(), 0.1);	
		assertEquals(101.5, project.getActivity("Testes Áreas").getFinishingTime(), 0.1);	
		assertEquals(0.0, project.getActivity("Testes Áreas").getErrors(), 0.1);
		
		assertEquals(101.5, project.getActivity("Testes Professores").getStartExecutionTime(), 0.1);	
		assertEquals(110.9, project.getActivity("Testes Professores").getFinishingTime(), 0.1);	
		assertEquals(0.0, project.getActivity("Testes Professores").getErrors(), 0.1);
		
		assertEquals(110.9, project.getActivity("Testes Usuários").getStartExecutionTime(), 0.1);	
		assertEquals(118.6, project.getActivity("Testes Usuários").getFinishingTime(), 0.1);	
		assertEquals(0.0, project.getActivity("Testes Usuários").getErrors(), 0.1);
	}
}