package br.unirio.overwork.test.instances;

import junit.framework.TestCase;
import br.unirio.overwork.instance.calculator.FunctionPointCalculator;
import br.unirio.overwork.instance.model.FunctionPointSystem;
import br.unirio.overwork.instance.reader.InstanceReader;
import br.unirio.overwork.instance.report.Report;

public class TestReaderPSOA extends TestCase
{
	public void testAll() throws Exception
	{
		InstanceReader reader = new InstanceReader();
		FunctionPointSystem fps = reader.run("data/instances/PSOA/functions-point.xml");
		Report report = new FunctionPointCalculator().calculate(fps);

		/* Data Functions */

		assertEquals(9, report.getDataFunctionName("Empregado").getRets());
		assertEquals(39, report.getDataFunctionName("Empregado").getDets());

		assertEquals(1, report.getDataFunctionName("MotivoAfastamento").getRets());
		assertEquals(1, report.getDataFunctionName("MotivoAfastamento").getDets());

		assertEquals(2, report.getDataFunctionName("Estabelecimento").getRets());
		assertEquals(7, report.getDataFunctionName("Estabelecimento").getDets());

		assertEquals(1, report.getDataFunctionName("MotivoTransferencia").getRets());
		assertEquals(1, report.getDataFunctionName("MotivoTransferencia").getDets());

		assertEquals(1, report.getDataFunctionName("MotivoFalta").getRets());
		assertEquals(1, report.getDataFunctionName("MotivoFalta").getDets());

		assertEquals(1, report.getDataFunctionName("Verba").getRets());
		assertEquals(1, report.getDataFunctionName("Verba").getDets());

		assertEquals(2, report.getDataFunctionName("Cargo").getRets());
		assertEquals(4, report.getDataFunctionName("Cargo").getDets());

		assertEquals(1, report.getDataFunctionName("Cidade").getRets());
		assertEquals(1, report.getDataFunctionName("Cidade").getDets());

		assertEquals(1, report.getDataFunctionName("Escolaridade").getRets());
		assertEquals(1, report.getDataFunctionName("Escolaridade").getDets());

		/* Transaction Functions */

		assertEquals(3, report.getTransactionFunctionName("IncluirNovoEmpregado").getFtrs());
		assertEquals(19, report.getTransactionFunctionName("IncluirNovoEmpregado").getDets());

		assertEquals(3, report.getTransactionFunctionName("AlterarEmpregado").getFtrs());
		assertEquals(19, report.getTransactionFunctionName("AlterarEmpregado").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverEmpregado").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("RemoverEmpregado").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarEmpregado").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("ConsultarEmpregado").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirAfastamento").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("IncluirAfastamento").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarAfastamento").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("AlterarAfastamento").getDets());

		assertEquals(1, report.getTransactionFunctionName("CancelarAfastamento").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("CancelarAfastamento").getDets());

		assertEquals(2, report.getTransactionFunctionName("ConsultarAfastamentos").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("ConsultarAfastamentos").getDets());

		assertEquals(2, report.getTransactionFunctionName("DesignarGerencia").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("DesignarGerencia").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarDesignacaoDeGerencia").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("AlterarDesignacaoDeGerencia").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverDesignacaoDeGerencia").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("RemoverDesignacaoDeGerencia").getDets());

		assertEquals(2, report.getTransactionFunctionName("ConsultarDesignacaoDeGerencia").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("ConsultarDesignacaoDeGerencia").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirLotacao").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("IncluirLotacao").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverLotacao").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("RemoverLotacao").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarLotacoes").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("ConsultarLotacoes").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirFalta").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("IncluirFalta").getDets());

		assertEquals(2, report.getTransactionFunctionName("AbonarFalta").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("AbonarFalta").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverFalta").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("RemoverFalta").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarFalta").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("AlterarFalta").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarFaltas").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("ConsultarFaltas").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirHoraExtra").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("IncluirHoraExtra").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarHoraExtra").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("AlterarHoraExtra").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverHoraExtra").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("RemoverHoraExtra").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarHorasExtras").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("ConsultarHorasExtras").getDets());

		assertEquals(2, report.getTransactionFunctionName("AssociarCargoAEmpregado").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("AssociarCargoAEmpregado").getDets());

		assertEquals(2, report.getTransactionFunctionName("RemoverCargoDeEmpregado").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("RemoverCargoDeEmpregado").getDets());

		assertEquals(2, report.getTransactionFunctionName("ConsultarCargosDeEmpregado").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("ConsultarCargosDeEmpregado").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirEstabelecimento").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("IncluirEstabelecimento").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverEstabelecimento").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("RemoverEstabelecimento").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarEstabelecimento").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("AlterarEstabelecimento").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarEstabelecimento").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("ConsultarEstabelecimento").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirCentroDeCusto").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("IncluirCentroDeCusto").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarCentroDeCusto").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("AlterarCentroDeCusto").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverCentroDeCusto").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("RemoverCentroDeCusto").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarCentroDeCusto").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("ConsultarCentroDeCusto").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirMotivoDeAfastamento").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("IncluirMotivoDeAfastamento").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverMotivoDeAfastamento").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("RemoverMotivoDeAfastamento").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarMotivoDeAfastamento").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("AlterarMotivoDeAfastamento").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarMotivosDeAfastamento").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("ConsultarMotivosDeAfastamento").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirMotivoDeFalta").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("IncluirMotivoDeFalta").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverMotivoDeFalta").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("RemoverMotivoDeFalta").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarMotivoDeFalta").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("AlterarMotivoDeFalta").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarMotivosDeFalta").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("ConsultarMotivosDeFalta").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirMotivoDeTransferencia").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("IncluirMotivoDeTransferencia").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverMotivoDeTransferencia").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("RemoverMotivoDeTransferencia").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarMotivoDeTransferencia").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("AlterarMotivoDeTransferencia").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarMotivosDeTransferencia").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("ConsultarMotivosDeTransferencia").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirVerba").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("IncluirVerba").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverVerba").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("RemoverVerba").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlteraVerba").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("AlteraVerba").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarVerbas").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("ConsultarVerbas").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirCargo").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("IncluirCargo").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverCargo").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("RemoverCargo").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlteraCargo").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("AlteraCargo").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarCargos").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("ConsultarCargos").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirSalarioDoCargo").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("IncluirSalarioDoCargo").getDets());

		assertEquals(1, report.getTransactionFunctionName("RemoverSalarioDoCargo").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("RemoverSalarioDoCargo").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlteraSalarioDoCargo").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("AlteraSalarioDoCargo").getDets());

		assertEquals(1, report.getTransactionFunctionName("ConsultarSalariosDoCargo").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("ConsultarSalariosDoCargo").getDets());

		assertEquals(3, report.getTransactionFunctionName("ProcessarFolha").getFtrs());
		assertEquals(27, report.getTransactionFunctionName("ProcessarFolha").getDets());

		assertEquals(2, report.getTransactionFunctionName("AdicionarVerbaParaEmpregado").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("AdicionarVerbaParaEmpregado").getDets());

		assertEquals(2, report.getTransactionFunctionName("RemoverVerbaDeEmpregado").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("RemoverVerbaDeEmpregado").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarVerbaDeEmpregado").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("AlterarVerbaDeEmpregado").getDets());

		assertEquals(2, report.getTransactionFunctionName("VisualizarContraCheques").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("VisualizarContraCheques").getDets());

		assertEquals(2, report.getTransactionFunctionName("VisualizarProprioContraCheque").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("VisualizarProprioContraCheque").getDets());
	}
}