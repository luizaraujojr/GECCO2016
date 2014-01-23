package br.unirio.sd;

import br.unirio.sd.control.gerador.Gerador;
import br.unirio.sd.control.semantics.VerificaMetamodelo;
import br.unirio.sd.model.cenario.Cenario;
import br.unirio.sd.model.cenario.Conexao;
import br.unirio.sd.model.cenario.Restricao;
import br.unirio.sd.model.meta.Classe;
import br.unirio.sd.model.meta.Metamodelo;

public class MainProgram
{
	// DURACAO = FP / 27.80
	// ERROS = 2.4 erros/FP

	public static final void main(String[] args) throws Exception
	{
//		Project project = new ProjectReader().execute("data\\project.xml");
//		System.out.println(project.getFunctionPoints());
		
		MainProgram programa = new MainProgram();
		Metamodelo metamodelo = programa.criaMetamodelo();
		new VerificaMetamodelo().executa(metamodelo);
		new Gerador().executa("src\\br\\unirio\\simulator\\", metamodelo);
		System.out.println("Fim!");
	}
	
	public Metamodelo criaMetamodelo()
	{
		Classe classeDesenvolvedor = criaClasseDesenvolvedor();
		Classe classeAtividade = criaClasseAtividade();
		Classe classeProjeto = criaClasseProjeto();

		Metamodelo metamodelo = new Metamodelo()
			.adicionaClasse(classeDesenvolvedor)
			.adicionaClasse(classeAtividade)
			.adicionaClasse(classeProjeto)
			.adicionaRelacionamentoMultiplo("Precedence", classeAtividade, classeAtividade, "Successor")
			.adicionaRelacionamentoSimples("Team", classeAtividade, classeDesenvolvedor)
			.adicionaRelacionamentoMultiplo("Activity", classeProjeto, classeAtividade, "")
			.adicionaCenario(criaCenarioTrabalhoHorasExtras())
			.adicionaCenario(criaCenarioExaustao());
		
		return metamodelo;
	}

	private Classe criaClasseDesenvolvedor()
	{
		return new Classe("Developer")
			.adicionaProcesso("Productivity", "1")
			.adicionaProcesso("ErrorGenerationRate", "1")
			.adicionaRepositorio("WorkAvailable", "0")
			.adicionaTaxa("RTWorkAvailable", "WorkAvailable", "Productivity");
	}

	private Classe criaClasseAtividade()
	{
		return new Classe("Activity")
			.adicionaPropriedade("TestingTask", 0)
			.adicionaPropriedade("Duration", 0)
			
			// Determine if the activity is concluded
			.adicionaProcesso("Concluded", "IF(RemainingTime < 0.001, 1, 0)")

			// Determine if the activity is ready to run
			.adicionaProcesso("Ready", "AND (GroupMax(Precedence, Concluded) >= 0, NOT(Concluded))")

			// Determine if there are resources available for the activity
			.adicionaProcesso("WorkAvailable", "Team.WorkAvailable")
			.adicionaProcesso("WorkUsed", "MIN(WorkAvailable, RemainingTime)")
			.adicionaTaxa("RTWorkUsed", "Team.WorkAvailable", "-WorkUsed")

			// Determine activity executed time
			.adicionaRepositorio("ExecutedTime", "0")
			.adicionaTaxa("RTExecTime", "ExecutedTime", "IF(Ready, WorkUsed, 0)")
			.adicionaProcesso("RemainingTime", "IF(TestingTask, Errors * 0.13, Duration - ExecutedTime)")

			// Calculates conclusion time for an activity
			.adicionaRepositorio("ConclusionTime", "0")
			.adicionaTaxa("RTConclusionTime", "ConclusionTime", "if(NOT(Concluded), 1, 0)")

			// Errors latent in the activity
			.adicionaRepositorio("Errors", "0")
			.adicionaTaxa("RTErrorsCreated",   "Errors", "IF(TestingTask, 0, Team.ErrorGenerationRate)")
			.adicionaTaxa("RTErrorsInherited", "Errors", "IF(AND(Ready, ExecutedTime), GROUPSUM (Precedence, Errors) / DT, 0)")
			.adicionaTaxa("RTErrorsCorrected", "Errors", "IF(TestingTask, -WorkUsed / 0.13, 0)");
	}
	
	private Classe criaClasseProjeto()
	{
		return new Classe("Project")
			.adicionaProcesso("Concluded", "GroupMin(Activity, Concluded)")	
			.adicionaRepositorio("ProjectTime", "0")
			.adicionaTaxa("RTProjectTime", "ProjectTime", "IF (Concluded, 0, 1)");
	}
	
	private Cenario criaCenarioTrabalhoHorasExtras()
	{
		Cenario cenario = new Cenario("Overworking");
		
		cenario.adicionaConexao(new Conexao("TheDeveloper", "Developer")
			.adicionaPropriedade("WorkHours", 8)
			.adicionaRepositorio("DailyWorkHours", "WorkHours")
			.adicionaProcesso("WHModifier", "1 + (DailyWorkHours - 8) / (12 - 8)")
			.adicionaProcesso("SEModifier", "LOOKUP (SchErrorsTable, WHModifier-1, 0, 1)")
			.adicionaTabela("SchErrorsTable", "VALUES(0.9, 0.94, 1, 1.05, 1.14, 1.24, 1.36, 1.5)")
			/*.adicionaAjuste("Cost", "Cost * DailyWorkHours / 8")*/
			.adicionaAjuste("Productivity", "Productivity * WHModifier")
			.adicionaAjuste("ErrorGenerationRate", "ErrorGenerationRate * SEModifier"));

		return cenario;
	}

	private Cenario criaCenarioExaustao()
	{
		Cenario cenario = new Cenario("Exhaustion");
		
		cenario.adicionaConexao(new Conexao("TheDeveloper", "Developer")
			.adicionaRepositorio("Exhaustion", "0")
			.adicionaProcesso("MaxExhaustion", "50")
			.adicionaTaxa("ExhaustionRate", "Exhaustion", "if(Resting = 1, -MaxExhaustion / 20.0, EXModifier)")
			.adicionaProcesso("EXModifier", "LOOKUP (ExaustionTable, DedicationFactor, 0, 1.5)")
			.adicionaProcesso("DedicationFactor", "1 - (1 - Dedication) / 0.4")
			.adicionaProcesso("Dedication", "0.6 + (WHModifier - 1) * (1.2 - 0.6)")
			.adicionaTabela("ExaustionTable", "VALUES(0.0, 0.0, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.15, 1.3, 1.6, 1.9, 2.2, 2.5)")
			.adicionaRepositorio("Resting", "0")
			.adicionaTaxa("RestingRate1", "Resting", "IF (InitResting, 1 / DT, 0)")
			.adicionaTaxa("RestingRate2", "Resting", "IF (QuitResting, -1 / DT, 0)")
			.adicionaTaxa("DWHRate", "DailyWorkHours", "IF (Resting = 1, (8 - DailyWorkHours) / DT, 0)")
			.adicionaProcesso("InitResting", "AND(Resting = 0, Exhaustion > MaxExhaustion)")
			.adicionaProcesso("QuitResting", "AND(Resting = 1, Exhaustion < 0.1)"));
		
		cenario.adicionaRestricao(new Restricao("TheDeveloper", "Overworking", "TheDeveloper"));
		return cenario;
	}
}