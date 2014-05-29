package br.unirio.overwork.test.instances;

import junit.framework.TestCase;
import br.unirio.overwork.instance.calculator.FunctionPointCalculator;
import br.unirio.overwork.instance.model.FunctionPointSystem;
import br.unirio.overwork.instance.reader.InstanceReader;
import br.unirio.overwork.instance.report.Report;

public class TestReaderPARM extends TestCase
{
	public void testAll() throws Exception
	{
		InstanceReader reader = new InstanceReader();
		FunctionPointSystem fps = reader.run("data/instances/PARM/functions-point.xml");
		Report report = new FunctionPointCalculator().calculate(fps);

		/* Data Functions */

		assertEquals(1, report.getDataFunctionName("ParametrosLegais").getRets());
		assertEquals(9, report.getDataFunctionName("ParametrosLegais").getDets());

		assertEquals(1, report.getDataFunctionName("ImpostoDeRenda").getRets());
		assertEquals(11, report.getDataFunctionName("ImpostoDeRenda").getDets());

		assertEquals(1, report.getDataFunctionName("ImpostoDeRendaExterior").getRets());
		assertEquals(4, report.getDataFunctionName("ImpostoDeRendaExterior").getDets());

		assertEquals(4, report.getDataFunctionName("FundamentacaoLegal").getRets());
		assertEquals(14, report.getDataFunctionName("FundamentacaoLegal").getDets());

		assertEquals(1, report.getDataFunctionName("PrevalenciaConsignacao").getRets());
		assertEquals(5, report.getDataFunctionName("PrevalenciaConsignacao").getDets());

		assertEquals(1, report.getDataFunctionName("SalarioFamilia").getRets());
		assertEquals(6, report.getDataFunctionName("SalarioFamilia").getDets());

		assertEquals(1, report.getDataFunctionName("IndiceDeReajusteRGPS").getRets());
		assertEquals(8, report.getDataFunctionName("IndiceDeReajusteRGPS").getDets());

		assertEquals(1, report.getDataFunctionName("Teto").getRets());
		assertEquals(9, report.getDataFunctionName("Teto").getDets());

		assertEquals(2, report.getDataFunctionName("SalarioMinimoGrupoBeneficio").getRets());
		assertEquals(6, report.getDataFunctionName("SalarioMinimoGrupoBeneficio").getDets());

		assertEquals(1, report.getDataFunctionName("CoeficienteReajustamentoBaseSM").getRets());
		assertEquals(5, report.getDataFunctionName("CoeficienteReajustamentoBaseSM").getDets());

		assertEquals(1, report.getDataFunctionName("IndiceReajusteBuracoNegro").getRets());
		assertEquals(5, report.getDataFunctionName("IndiceReajusteBuracoNegro").getDets());

		assertEquals(1, report.getDataFunctionName("SalarioContribuicao").getRets());
		assertEquals(11, report.getDataFunctionName("SalarioContribuicao").getDets());

		assertEquals(1, report.getDataFunctionName("UF").getRets());
		assertEquals(1, report.getDataFunctionName("UF").getDets());

		assertEquals(1, report.getDataFunctionName("Rubrica").getRets());
		assertEquals(3, report.getDataFunctionName("Rubrica").getDets());

		assertEquals(1, report.getDataFunctionName("SalarioMinimoRegionalizado").getRets());
		assertEquals(6, report.getDataFunctionName("SalarioMinimoRegionalizado").getDets());

		assertEquals(1, report.getDataFunctionName("LocalDeTrabalho").getRets());
		assertEquals(2, report.getDataFunctionName("LocalDeTrabalho").getDets());

		assertEquals(1, report.getDataFunctionName("EspecieBeneficio").getRets());
		assertEquals(10, report.getDataFunctionName("EspecieBeneficio").getDets());

		assertEquals(1, report.getDataFunctionName("Tratamento").getRets());
		assertEquals(3, report.getDataFunctionName("Tratamento").getDets());

		assertEquals(1, report.getDataFunctionName("FatorDeAtualizacao").getRets());
		assertEquals(6, report.getDataFunctionName("FatorDeAtualizacao").getDets());

		assertEquals(1, report.getDataFunctionName("TaxaConversaoMoeda").getRets());
		assertEquals(5, report.getDataFunctionName("TaxaConversaoMoeda").getDets());

		assertEquals(1, report.getDataFunctionName("CotacaoValorAtualizacao").getRets());
		assertEquals(5, report.getDataFunctionName("CotacaoValorAtualizacao").getDets());

		/* Transaction Functions */

		assertEquals(1, report.getTransactionFunctionName("IncluirTipoFundamentacaoLegal").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("IncluirTipoFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarTipoFundamentacaoLegal").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("AlterarTipoFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirTipoFundamentacaoLegal").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("ExcluirTipoFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarTipoFundamentacaoLegal").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("PesquisarTipoFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharTipoFundamentacaoLegal").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("DetalharTipoFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirReferenciaFundamentacaoLegal").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("IncluirReferenciaFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarReferenciaFundamentacaoLegal").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("AlterarReferenciaFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirReferenciaFundamentacaoLegal").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("ExcluirReferenciaFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarReferenciaFundamentacaoLegal").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("PesquisarReferenciaFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharReferenciaFundamentacaoLegal").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("DetalharReferenciaFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirAutoridadeEmissora").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("IncluirAutoridadeEmissora").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarAutoridadeEmissora").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("AlterarAutoridadeEmissora").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirAutoridadeEmissora").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("ExcluirAutoridadeEmissora").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarAutoridadeEmissora").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("PesquisarAutoridadeEmissora").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharAutoridadeEmissora").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("DetalharAutoridadeEmissora").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirFundamentacaoLegal").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("IncluirFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarFundamentacaoLegal").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("AlterarFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirFundamentacaoLegal").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("ExcluirFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarFundamentacaoLegal").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("PesquisarFundamentacaoLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharFundamentacaoLegal").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("DetalharFundamentacaoLegal").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirImpostoDeRenda").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("IncluirImpostoDeRenda").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarImpostoDeRenda").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("AlterarImpostoDeRenda").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirImpostoDeRenda").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("ExcluirImpostoDeRenda").getDets());

		assertEquals(2, report.getTransactionFunctionName("PesquisarImpostoDeRenda").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("PesquisarImpostoDeRenda").getDets());

		assertEquals(2, report.getTransactionFunctionName("DetalharImpostoDeRenda").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("DetalharImpostoDeRenda").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirAliquotaImpostoDeRenda").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("IncluirAliquotaImpostoDeRenda").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarAliquotaImpostoDeRenda").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("AlterarAliquotaImpostoDeRenda").getDets());

		assertEquals(2, report.getTransactionFunctionName("ExcluirAliquotaImpostoDeRenda").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("ExcluirAliquotaImpostoDeRenda").getDets());

		assertEquals(2, report.getTransactionFunctionName("DetalharAliquotaImpostoDeRenda").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("DetalharAliquotaImpostoDeRenda").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirImpostoDeRendaExterior").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("IncluirImpostoDeRendaExterior").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarImpostoDeRendaExterior").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("AlterarImpostoDeRendaExterior").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirImpostoDeRendaExterior").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("ExcluirImpostoDeRendaExterior").getDets());

		assertEquals(2, report.getTransactionFunctionName("PesquisarImpostoDeRendaExterior").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("PesquisarImpostoDeRendaExterior").getDets());

		assertEquals(2, report.getTransactionFunctionName("DetalharImpostoDeRendaExterior").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("DetalharImpostoDeRendaExterior").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirSalarioFamilia").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("IncluirSalarioFamilia").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarSalarioFamilia").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("AlterarSalarioFamilia").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirSalarioFamilia").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("ExcluirSalarioFamilia").getDets());

		assertEquals(2, report.getTransactionFunctionName("PesquisarSalarioFamilia").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("PesquisarSalarioFamilia").getDets());

		assertEquals(2, report.getTransactionFunctionName("DetalharSalarioFamilia").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("DetalharSalarioFamilia").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirFaixaSalarioFamilia").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("IncluirFaixaSalarioFamilia").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarFaixaSalarioFamilia").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("AlterarFaixaSalarioFamilia").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirFaixaSalarioFamilia").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("ExcluirFaixaSalarioFamilia").getDets());

		assertEquals(2, report.getTransactionFunctionName("DetalharFaixaSalarioFamilia").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("DetalharFaixaSalarioFamilia").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirParametroLegal").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("IncluirParametroLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarParametroLegal").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("AlterarParametroLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirParametroLegal").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("ExcluirParametroLegal").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarParametrosLegais").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("PesquisarParametrosLegais").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharParametrosLegais").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("DetalharParametrosLegais").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirPrevalenciaConsignacao").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("IncluirPrevalenciaConsignacao").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarPrevalenciaConsginacao").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("AlterarPrevalenciaConsginacao").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirPrevalenciaConsignacao").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("ExcluirPrevalenciaConsignacao").getDets());

		assertEquals(2, report.getTransactionFunctionName("PesquisarPrevalenciaConsignacao").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("PesquisarPrevalenciaConsignacao").getDets());

		assertEquals(2, report.getTransactionFunctionName("DetalharPrevalenciaConsignacao").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("DetalharPrevalenciaConsignacao").getDets());

		assertEquals(2, report.getTransactionFunctionName("DetalharVigenciaPrevalenciaConsignacao").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("DetalharVigenciaPrevalenciaConsignacao").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirIndiceReajusteRGPS").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("IncluirIndiceReajusteRGPS").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarIndiceReajusteRGPS").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("AlterarIndiceReajusteRGPS").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirIndiceReajusteRGPS").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("ExcluirIndiceReajusteRGPS").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarIndiceReajusteRGPS").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("PesquisarIndiceReajusteRGPS").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharIndiceReajusteRGPS").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("DetalharIndiceReajusteRGPS").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirTeto").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("IncluirTeto").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarTeto").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("AlterarTeto").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirTeto").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("ExcluirTeto").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarTeto").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("PesquisarTeto").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharTeto").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("DetalharTeto").getDets());

		assertEquals(1, report.getTransactionFunctionName("IncluirTipoTeto").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("IncluirTipoTeto").getDets());

		assertEquals(1, report.getTransactionFunctionName("AlterarTipoTeto").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("AlterarTipoTeto").getDets());

		assertEquals(1, report.getTransactionFunctionName("ExcluirTipoTeto").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("ExcluirTipoTeto").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarTipoTeto").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("PesquisarTipoTeto").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharTipoTeto").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("DetalharTipoTeto").getDets());

		assertEquals(2, report.getTransactionFunctionName("IncluirSalarioContribuicao").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("IncluirSalarioContribuicao").getDets());

		assertEquals(2, report.getTransactionFunctionName("AlterarSalarioContribuicao").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("AlterarSalarioContribuicao").getDets());

		assertEquals(2, report.getTransactionFunctionName("ExcluirSalarioContribuicao").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("ExcluirSalarioContribuicao").getDets());

		assertEquals(2, report.getTransactionFunctionName("PesquisarSalarioContribuicao").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("PesquisarSalarioContribuicao").getDets());

		assertEquals(2, report.getTransactionFunctionName("DetalharSalarioContribuicao").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("DetalharSalarioContribuicao").getDets());

		assertEquals(2, report.getTransactionFunctionName("PesquisarSalarioMinimoRegionalizado").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("PesquisarSalarioMinimoRegionalizado").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharSalarioMinimoRegionalizado").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("DetalharSalarioMinimoRegionalizado").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarLocaisdeTrabalho").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("PesquisarLocaisdeTrabalho").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharLocaisdeTrabalho").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("DetalharLocaisdeTrabalho").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarEspecieBeneficio").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("PesquisarEspecieBeneficio").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharEspecieBeneficio").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("DetalharEspecieBeneficio").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarTratamento").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("PesquisarTratamento").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharTratamento").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("DetalharTratamento").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarFatorAtualizacao").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("PesquisarFatorAtualizacao").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharFatorAtualizacao").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("DetalharFatorAtualizacao").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarRubrica").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("PesquisarRubrica").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharRubrica").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("DetalharRubrica").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarTaxaConversaoMoeda").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("PesquisarTaxaConversaoMoeda").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharTaxaConversaoMoeda").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("DetalharTaxaConversaoMoeda").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarCotacaoFatorAtualizacao").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("PesquisarCotacaoFatorAtualizacao").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharCotacaoFatorAtualizacao").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("DetalharCotacaoFatorAtualizacao").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarGrupoBeneficio").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("PesquisarGrupoBeneficio").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharGrupoBeneficio").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("DetalharGrupoBeneficio").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarCoeficienteReajustamentoBaseSM").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("PesquisarCoeficienteReajustamentoBaseSM").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharCoeficienteReajustamentoBaseSM").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("DetalharCoeficienteReajustamentoBaseSM").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarIndiceReajusteBuracoNegro").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("PesquisarIndiceReajusteBuracoNegro").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharIndiceReajusteBuracoNegro").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("DetalharIndiceReajusteBuracoNegro").getDets());

		assertEquals(1, report.getTransactionFunctionName("PesquisarSalarioMinimoGrupoBenef").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("PesquisarSalarioMinimoGrupoBenef").getDets());

		assertEquals(1, report.getTransactionFunctionName("DetalharSalarioMinimoGrupoBenef").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("DetalharSalarioMinimoGrupoBenef").getDets());
	}
}