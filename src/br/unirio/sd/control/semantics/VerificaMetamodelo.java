package br.unirio.sd.control.semantics;

import br.unirio.sd.model.common.TipoOperacao;
import br.unirio.sd.model.meta.Classe;
import br.unirio.sd.model.meta.Comportamento;
import br.unirio.sd.model.meta.Metamodelo;
import br.unirio.sd.model.meta.Relacionamento;
import br.unirio.sd.model.meta.TipoComportamento;

/**
 * Verifica a semântica de um metamodelo da Dinâmica de Sistemas
 * 
 * @author Marcio
 */
public class VerificaMetamodelo
{
	/**
	 * Verifica a consistência semântica de um comportamento do tipo taxa
	 */
	private void verificaRepositorioTaxa (Metamodelo metamodelo, Classe classe, Comportamento rate) throws Exception
	{
		// Se o repositório for de outra classe, captura a classe desejada
		if (rate.getTipo() == TipoComportamento.RATE && rate.getRepositorio().getTipo() == TipoOperacao.PONTO)
		{
			Relacionamento relacao = metamodelo.pegaRelacionamentoNome(rate.getExpressao().getEsquerda().getNome());
	
			if (relacao == null)
				throw new Exception("The metamodel " + metamodelo.getId() + " does not have the relation " + rate.getExpressao().getEsquerda().getNome() + ", as required by the rate " + rate.getId());
	
			// Se o identificador for o nome do papel de destino ou nome da relação, retorna a classe destino
			String id = rate.getExpressao().getEsquerda().getNome();
			
			if (relacao.getPapelDestino().compareToIgnoreCase(id) == 0 || relacao.getId().compareToIgnoreCase(id) == 0)
				classe = relacao.getClasseDestino();
			else 
				classe = null;
		}	
	
		// Captura o nome do repositório, de acordo com o tipo de sua descrição
		String nomeRepositorio = (rate.getRepositorio().getTipo() == TipoOperacao.PONTO) ? rate.getRepositorio().getDireita().getNome() : rate.getRepositorio().getNome();
	
		// Pega o comportamento que representa o repositório
		Comportamento repositorio = classe.pegaComportamentoNome(nomeRepositorio);
	
		if (repositorio == null)
			throw new Exception("The class " + classe.getId() + " does not have the stock " + nomeRepositorio + ", as required by the rate " + rate.getId());
	
		// Verifica se o comportamento que descreve o repositório é realmente um repositório
		if (repositorio.getTipo() != TipoComportamento.STOCK)
			throw new Exception("The behavior " + nomeRepositorio + " of the class " + classe.getId() + " is not a stock, as required by the rate " + rate.getId());
	}
	
	/**
	 * Verifica a consistência semântica de uma classe em um metamodelo
	 */
	private void verificaSemanticaClasse (Metamodelo metamodelo, Classe classe) throws Exception
	{
		for (Comportamento comportamento : classe.getComportamentos())
			if (comportamento.getTipo() == TipoComportamento.RATE)
				verificaRepositorioTaxa(metamodelo, classe, comportamento);
	}
	
	/**
	 * Verifica a consistência semântica de um relacionamento em um metamodelo
	 */
	private void verificaSemanticaRelacionamento(Metamodelo metamodelo, Relacionamento relacao) throws Exception
	{
		// Verifica se o papel de destino do relacionamento tem o mesmo nome do relacionamento
		if (relacao.getId().compareToIgnoreCase(relacao.getPapelDestino()) == 0)
			throw new Exception("The name of the relation " + relacao.getId() + " was used as its target class role in the metamodel " + metamodelo.getId());
	
		// Verifica se em um auto-relacionamento define um papel de destino
		if (relacao.getClasseOrigem() == relacao.getClasseDestino()  &&  relacao.getPapelDestino().length() == 0)
			throw new Exception("The auto relation " + relacao.getId() + " must specify a target role in the metamodel " + metamodelo.getId());
	}
	
	/**
	 * Verifica a consistência semântica de um metamodelo
	 */
	public void executa(Metamodelo metamodelo) throws Exception
	{
		for (Relacionamento relacionamento : metamodelo.getRelacionamentos())
			verificaSemanticaRelacionamento(metamodelo, relacionamento);
	
		for (Classe classe : metamodelo.getClasses())
			verificaSemanticaClasse(metamodelo, classe);
	}
}