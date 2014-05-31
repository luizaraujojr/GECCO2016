package br.unirio.overwork.test.scenario;

import junit.framework.TestCase;
import br.unirio.overwork.builders.controller.SoftwareSystemProjectBuilder;
import br.unirio.overwork.builders.model.WorkPackage;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;
import br.unirio.overwork.model.scenarios.ScenarioOverworking;
import br.unirio.simulation.SimulationEngine;

public class TestScenarioOverworking extends TestCase 
{
	private static Project createProject()
	{
		SoftwareSystemProjectBuilder builder = new SoftwareSystemProjectBuilder();
		
		WorkPackage wp1 = builder.addWorkPackage("Usuários");
		wp1.addRequirement("usuario", 7);
		wp1.addRequirement("Cadastro de usuário", 3);
		wp1.addRequirement("Lista de usuários ", 4);
		wp1.addRequirement("Consulta de usuário", 4);

		WorkPackage wp2 = builder.addWorkPackage("Professores");
		wp2.addRequirement("professor", 7);
		wp2.addRequirement("Cadastro de professor", 4);
		wp2.addRequirement("Lista de professores", 4);
		wp2.addRequirement("Consulta de professor", 5);
		
		WorkPackage wp3 = builder.addWorkPackage("Áreas");
		wp3.addRequirement("area", 7);
		wp3.addRequirement("Cadastro de área", 4);
		wp3.addRequirement("Lista de áreas", 4);
		wp3.addRequirement("Lista de sub-áreas", 4);
		wp3.addRequirement("Consulta de área", 4);
		
		WorkPackage wp4 = builder.addWorkPackage("Aluno");
		wp4.addRequirement("aluno", 10);
		wp4.addRequirement("Cadastro de aluno", 6);
		wp4.addRequirement("Consulta de aluno", 7);
		
		WorkPackage wp5 = builder.addWorkPackage("Disciplinas");
		wp5.addRequirement("disciplina", 7);
		wp5.addRequirement("Cadastro de disciplina", 4);
		wp5.addRequirement("Lista de disciplinas", 4);
		wp5.addRequirement("Consulta de disciplina", 5);
		
		WorkPackage wp6 = builder.addWorkPackage("Turmas");
		wp6.addRequirement("turma", 7);
		wp6.addRequirement("turmasolicitada", 7);
		wp6.addRequirement("Cadastro de turma", 6);
		wp6.addRequirement("Cadastro de turma solicitada", 6);
		wp6.addRequirement("Lista de turmas", 4);
		wp6.addRequirement("Consulta de turma", 7);
		wp6.addRequirement("Lista de turmas solicitadas", 4);
		
		WorkPackage wp7 = builder.addWorkPackage("Inscrições");
		wp7.addRequirement("inscricao", 7);
		wp7.addRequirement("Cadastro de inscrição", 6);
		wp7.addRequirement("Consulta de inscrição", 7);
		wp7.addRequirement("Geração de comprovante", 7);
		
		return builder.execute();
	}

	public void testAll() throws Exception
	{
		Project project = createProject();
		
		SimulationEngine simulator = new SimulationEngine();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());
		new ScenarioOverworking(12).connect(project.getActivities());
		simulator.init();
		
		while (!project.isConcluded())
			simulator.run();
		
		assertEquals(0, project.getActivity("Requisitos Inscrições").getStartExecutionTime(), 0.1);	
		assertEquals(0.83, project.getActivity("Requisitos Inscrições").getFinishingTime(), 0.1);	
		assertEquals(40.5, project.getActivity("Requisitos Inscrições").getErrors(), 0.1);
		
		assertEquals(0.83, project.getActivity("Requisitos Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(2.11, project.getActivity("Requisitos Turmas").getFinishingTime(), 0.1);	
		assertEquals(61.5, project.getActivity("Requisitos Turmas").getErrors(), 0.1);
		
		assertEquals(2.11, project.getActivity("Requisitos Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(2.73, project.getActivity("Requisitos Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(30, project.getActivity("Requisitos Disciplinas").getErrors(), 0.1);
		
		assertEquals(2.73, project.getActivity("Requisitos Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(3.44, project.getActivity("Requisitos Aluno").getFinishingTime(), 0.1);	
		assertEquals(34.47, project.getActivity("Requisitos Aluno").getErrors(), 0.1);
		
		assertEquals(3.45, project.getActivity("Requisitos Áreas").getStartExecutionTime(), 0.1);	
		assertEquals(4.16, project.getActivity("Requisitos Áreas").getFinishingTime(), 0.1);	
		assertEquals(34.5, project.getActivity("Requisitos Áreas").getErrors(), 0.1);
		
		assertEquals(4.16, project.getActivity("Requisitos Professores").getStartExecutionTime(), 0.1);	
		assertEquals(4.78, project.getActivity("Requisitos Professores").getFinishingTime(), 0.1);	
		assertEquals(30, project.getActivity("Requisitos Professores").getErrors(), 0.1);
		
		assertEquals(4.78, project.getActivity("Requisitos Usuários").getStartExecutionTime(), 0.1);	
		assertEquals(5.34, project.getActivity("Requisitos Usuários").getFinishingTime(), 0.1);	
		assertEquals(27, project.getActivity("Requisitos Usuários").getErrors(), 0.1);
		
		assertEquals(5.34, project.getActivity("Projeto Inscrições").getStartExecutionTime(), 0.1);	
		assertEquals(6.35, project.getActivity("Projeto Inscrições").getFinishingTime(), 0.1);	
		assertEquals(96.19, project.getActivity("Projeto Inscrições").getErrors(), 0.1);
		
		assertEquals(6.35, project.getActivity("Projeto Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(7.88, project.getActivity("Projeto Turmas").getFinishingTime(), 0.1);	
		assertEquals(146.06, project.getActivity("Projeto Turmas").getErrors(), 0.1);
		
		assertEquals(7.88, project.getActivity("Projeto Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(8.62, project.getActivity("Projeto Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(71.25, project.getActivity("Projeto Disciplinas").getErrors(), 0.1);
		
		assertEquals(8.62, project.getActivity("Projeto Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(9.48, project.getActivity("Projeto Aluno").getFinishingTime(), 0.1);	
		assertEquals(81.9, project.getActivity("Projeto Aluno").getErrors(), 0.1);
		
		assertEquals(9.48, project.getActivity("Projeto Áreas").getStartExecutionTime(), 0.1);	
		assertEquals(10.34, project.getActivity("Projeto Áreas").getFinishingTime(), 0.1);	
		assertEquals(81.94, project.getActivity("Projeto Áreas").getErrors(), 0.1);
		
		assertEquals(10.34, project.getActivity("Projeto Professores").getStartExecutionTime(), 0.1);	
		assertEquals(11.09, project.getActivity("Projeto Professores").getFinishingTime(), 0.1);	
		assertEquals(71.25, project.getActivity("Projeto Professores").getErrors(), 0.1);
		
		assertEquals(11.09, project.getActivity("Projeto Usuários").getStartExecutionTime(), 0.1);	
		assertEquals(11.76, project.getActivity("Projeto Usuários").getFinishingTime(), 0.1);	
		assertEquals(64.13, project.getActivity("Projeto Usuários").getErrors(), 0.1);
		
		assertEquals(11.76, project.getActivity("Codificacao Inscrições").getStartExecutionTime(), 0.1);	
		assertEquals(18.47, project.getActivity("Codificacao Inscrições").getFinishingTime(), 0.1);	
		assertEquals(184.3, project.getActivity("Codificacao Inscrições").getErrors(), 0.1);
		
		assertEquals(18.47, project.getActivity("Codificacao Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(28.67, project.getActivity("Codificacao Turmas").getFinishingTime(), 0.1);	
		assertEquals(279.86, project.getActivity("Codificacao Turmas").getErrors(), 0.1);
		assertEquals(28.67, project.getActivity("Codificacao Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(33.64, project.getActivity("Codificacao Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(136.52, project.getActivity("Codificacao Disciplinas").getErrors(), 0.1);
		
		assertEquals(33.64, project.getActivity("Codificacao Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(39.36, project.getActivity("Codificacao Aluno").getFinishingTime(), 0.1);	
		assertEquals(156.94, project.getActivity("Codificacao Aluno").getErrors(), 0.1);
		
		assertEquals(39.36, project.getActivity("Codificacao Áreas").getStartExecutionTime(), 0.1);	
		assertEquals(45.08, project.getActivity("Codificacao Áreas").getFinishingTime(), 0.1);	
		assertEquals(157, project.getActivity("Codificacao Áreas").getErrors(), 0.1);
		
		assertEquals(45.08, project.getActivity("Codificacao Professores").getStartExecutionTime(), 0.1);	
		assertEquals(50.06, project.getActivity("Codificacao Professores").getFinishingTime(), 0.1);	
		assertEquals(136.52, project.getActivity("Codificacao Professores").getErrors(), 0.1);
		
		assertEquals(50.06, project.getActivity("Codificacao Usuários").getStartExecutionTime(), 0.1);	
		assertEquals(54.53, project.getActivity("Codificacao Usuários").getFinishingTime(), 0.1);	
		assertEquals(122.87, project.getActivity("Codificacao Usuários").getErrors(), 0.1);
		
		assertEquals(54.53, project.getActivity("Testes Inscrições").getStartExecutionTime(), 0.1);	
		assertEquals(63.13, project.getActivity("Testes Inscrições").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Inscrições").getErrors(), 0.1);
		
		assertEquals(63.13, project.getActivity("Testes Turmas").getStartExecutionTime(), 0.1);	
		assertEquals(76.18, project.getActivity("Testes Turmas").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Turmas").getErrors(), 0.1);
		
		assertEquals(76.18, project.getActivity("Testes Disciplinas").getStartExecutionTime(), 0.1);	
		assertEquals(82.54, project.getActivity("Testes Disciplinas").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Disciplinas").getErrors(), 0.1);
		
		assertEquals(82.54, project.getActivity("Testes Aluno").getStartExecutionTime(), 0.1);	
		assertEquals(89.86, project.getActivity("Testes Aluno").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Aluno").getErrors(), 0.1);
		
		assertEquals(89.86, project.getActivity("Testes Áreas").getStartExecutionTime(), 0.1);	
		assertEquals(97.18, project.getActivity("Testes Áreas").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Áreas").getErrors(), 0.1);
		
		assertEquals(97.18, project.getActivity("Testes Professores").getStartExecutionTime(), 0.1);	
		assertEquals(103.55, project.getActivity("Testes Professores").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Professores").getErrors(), 0.1);
		
		assertEquals(103.55, project.getActivity("Testes Usuários").getStartExecutionTime(), 0.1);	
		assertEquals(109.27, project.getActivity("Testes Usuários").getFinishingTime(), 0.1);	
		assertEquals(0, project.getActivity("Testes Usuários").getErrors(), 0.1);
	}
}