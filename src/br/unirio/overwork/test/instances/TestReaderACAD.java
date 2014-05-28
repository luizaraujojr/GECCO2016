package br.unirio.overwork.test.instances;

import junit.framework.TestCase;
import br.unirio.overwork.instance.calculator.FunctionPointCalculator;
import br.unirio.overwork.instance.model.FunctionPointSystem;
import br.unirio.overwork.instance.reader.InstanceReader;
import br.unirio.overwork.instance.report.Report;

public class TestReaderACAD extends TestCase
{
	public void testAll() throws Exception
	{
		InstanceReader reader = new InstanceReader();
		FunctionPointSystem fps = reader.run("data/instances/ACAD/functions-point.xml");
		Report report = new FunctionPointCalculator().calculate(fps);

		/* Data Functions */

		assertEquals(5, report.getDataFunctionName("Aluno").getRets());
		assertEquals(37, report.getDataFunctionName("Aluno").getDets());

		assertEquals(1, report.getDataFunctionName("Professor").getRets());
		assertEquals(7, report.getDataFunctionName("Professor").getDets());

		assertEquals(2, report.getDataFunctionName("Area").getRets());
		assertEquals(5, report.getDataFunctionName("Area").getDets());

		assertEquals(1, report.getDataFunctionName("Usuario").getRets());
		assertEquals(4, report.getDataFunctionName("Usuario").getDets());

		assertEquals(3, report.getDataFunctionName("Disciplina").getRets());
		assertEquals(13, report.getDataFunctionName("Disciplina").getDets());

		assertEquals(1, report.getDataFunctionName("Turma").getRets());
		assertEquals(7, report.getDataFunctionName("Turma").getDets());

		assertEquals(1, report.getDataFunctionName("Inscricao").getRets());
		assertEquals(4, report.getDataFunctionName("Inscricao").getDets());

		/* Transaction Functions */

		assertEquals(1, report.getTransactionFunctionName("IncluirNovoAluno").getFtrs());
		assertEquals(30, report.getTransactionFunctionName("IncluirNovoAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarAluno").getFtrs());
		assertEquals(32, report.getTransactionFunctionName("AlterarAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarAluno").getFtrs());
		assertEquals(32, report.getTransactionFunctionName("ConsultarAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverAluno").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("RemoverAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("InformarTrancamentoDeAluno").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("InformarTrancamentoDeAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("CancelarTrancamentoDeAluno").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("CancelarTrancamentoDeAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverTrancamentoDeAluno").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("RemoverTrancamentoDeAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirBolsaParaAluno").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("IncluirBolsaParaAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarBolsaDeAluno").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("AlterarBolsaDeAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarBolsasDeAluno").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("ConsultarBolsasDeAluno").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverBolsaParaAluno").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("RemoverBolsaParaAluno").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirNovoProfessor").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("IncluirNovoProfessor").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarProfessor").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("AlterarProfessor").getDets());

		assertEquals(2, report.getTransactionFunctionName("ConsultarProfessor").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("ConsultarProfessor").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverProfessor").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("RemoverProfessor").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirNovaArea").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("IncluirNovaArea").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarArea").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("AlterarArea").getDets());

		assertEquals(2, report.getTransactionFunctionName("ConsultarArea").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("ConsultarArea").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverArea").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("RemoverArea").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirNovaLinha").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("IncluirNovaLinha").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarLinha").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("AlterarLinha").getDets());

		assertEquals(2, report.getTransactionFunctionName("ConsultarLinha").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("ConsultarLinha").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverLinha").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("RemoverLinha").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirUsuario").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("IncluirUsuario").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarUsuario").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("AlterarUsuario").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarUsuario").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("ConsultarUsuario").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverUsuario").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("RemoverUsuario").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirNovaDisciplina").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("IncluirNovaDisciplina").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarDisciplina").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("AlterarDisciplina").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarDisciplina").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("ConsultarDisciplina").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverDisciplina").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("RemoverDisciplina").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirNovaTurma").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("IncluirNovaTurma").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarTurma").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("AlterarTurma").getDets());

		assertEquals(2, report.getTransactionFunctionName("ConsultarTurma").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("ConsultarTurma").getDets());

		assertEquals(2, report.getTransactionFunctionName("RemoverTurma").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("RemoverTurma").getDets());

		assertEquals(2, report.getTransactionFunctionName("RealizarInscricaoEmTurma").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("RealizarInscricaoEmTurma").getDets());

		assertEquals(2, report.getTransactionFunctionName("ConsultarInscricaoEmTurma").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("ConsultarInscricaoEmTurma").getDets());

		assertEquals(2, report.getTransactionFunctionName("CancelarInscricaoEmTurma").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("CancelarInscricaoEmTurma").getDets());

		assertEquals(4, report.getTransactionFunctionName("GeracaoDeComprovanteDeInscricao").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("GeracaoDeComprovanteDeInscricao").getDets());
	}
}