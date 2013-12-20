package br.unirio.sd.control.semantics;

import br.unirio.sd.model.common.TipoOperacao;
import br.unirio.sd.model.meta.Classe;
import br.unirio.sd.model.meta.Comportamento;
import br.unirio.sd.model.meta.Metamodelo;
import br.unirio.sd.model.meta.Relacionamento;
import br.unirio.sd.model.meta.TipoComportamento;

/**
 * Verifica a sem�ntica de um metamodelo da Din�mica de Sistemas
 * 
 * @author Marcio
 */
public class VerificaMetamodelo
{
	/**
	 * Verifica se uma taxa � m�ltipla
	 */
	private boolean verificaMultirate (Comportamento rate)
	{
		return ((rate.getTipo() == TipoComportamento.RATE) && (rate.getRepositorio().getTipo() == TipoOperacao.PONTO));
	}
	
	/**
	 * Pega a classe relacionada a uma taxa
	 */
	private Classe pegaClasseRelacionada(Metamodelo metamodelo, Relacionamento relacao, Classe classe, String id)
	{
		// Se o identificador for o nome do papel de destino, retorna a classe destino
		if (relacao.getPapelDestino().compareToIgnoreCase(id) == 0)
			return metamodelo.pegaClasseNome(relacao.getIdClasseDestino());
	
		// Se o identificador for o nome da rela��o, retorna a classe destino
		if (relacao.getId().compareToIgnoreCase(id) == 0)
			return metamodelo.pegaClasseNome(relacao.getIdClasseDestino());
	
		return null;
	}
	
	/**
	 * Verifica a consist�ncia sem�ntica de um comportamento do tipo taxa
	 */
	private void verificaRepositorioTaxa (Metamodelo metamodelo, Classe classe, Comportamento rate) throws Exception
	{
		// Se o reposit�rio for de outra classe, captura a classe desejada
		if (verificaMultirate(rate))
		{
			Relacionamento relacao = metamodelo.pegaRelacionamentoNome(rate.getExpressao().getEsquerda().getNome());
	
			if (relacao == null)
				throw new Exception("The metamodel " + metamodelo.getId() + " does not have the relation " + rate.getExpressao().getEsquerda().getNome() + ", as required by the rate " + rate.getId());
	
			classe = pegaClasseRelacionada(metamodelo, relacao, classe, rate.getExpressao().getEsquerda().getNome());
		}	
	
		// Captura o nome do reposit�rio, de acordo com o tipo de sua descri��o
		String nomeRepositorio = (rate.getExpressao().getTipo() == TipoOperacao.PONTO) ? rate.getExpressao().getDireita().getNome() : rate.getExpressao().getNome();
	
		// Pega o comportamento que representa o reposit�rio
		Comportamento repositorio = classe.pegaComportamentoNome(nomeRepositorio);
	
		if (repositorio == null)
			throw new Exception("The class " + classe.getId() + " does not have the stock " + nomeRepositorio + ", as required by the rate " + rate.getId());
	
		// Verifica se o comportamento que descreve o reposit�rio � realmente um reposit�rio
		if (repositorio.getTipo() != TipoComportamento.STOCK)
			throw new Exception("The behavior " + nomeRepositorio + " of the class " + classe.getId() + " is not a stock, as required by the rate " + rate.getId());
	}
	
	/**
	 * Verifica a consist�ncia sem�ntica de um comportamento em uma classe
	 */
	private void verificaSemanticaComportamento (Metamodelo metamodelo, Classe classe, Comportamento comportamento) throws Exception
	{
		// A sem�ntica das tabelas � garantida por constru��o. N�o pode ter erros.
		if (comportamento.getTipo() == TipoComportamento.TABLE)
			return;
	
		// Nas taxas, devemos verificar a consist�ncia de seu reposit�rio
		if (comportamento.getTipo() == TipoComportamento.RATE)
			verificaRepositorioTaxa(metamodelo, classe, comportamento);
	}
	
	/**
	 * Verifica a consist�ncia sem�ntica de uma classe em um metamodelo
	 */
	private void verificaSemanticaClasse (Metamodelo metamodelo, Classe classe) throws Exception
	{
		for (Comportamento comportamento : classe.getComportamentos())
			verificaSemanticaComportamento(metamodelo, classe, comportamento);
	}
	
	/**
	 * Verifica a consist�ncia sem�ntica de um relacionamento em um metamodelo
	 */
	private void verificaSemanticaRelacionamento(Metamodelo metamodelo, Relacionamento relacao) throws Exception
	{
		// Verifica se a classe origem do relacionamento existe no meta modelo
		if (metamodelo.pegaClasseNome (relacao.getIdClasseOrigem()) == null)
			throw new Exception("The relation " + relacao.getId() + " is associated with the class " + relacao.getIdClasseOrigem() + " which is not in the metamodel " + metamodelo.getId());
	
		// Verifica se a classe destino do relacionamento existe no meta modelo
		if (metamodelo.pegaClasseNome(relacao.getIdClasseDestino()) == null)
			throw new Exception("The relation " + relacao.getId() + " is associated with the class " + relacao.getIdClasseDestino() + " which is not in the metamodel " + metamodelo.getId());
	
		// Verifica se o papel de destino do relacionamento tem o mesmo nome do relacionamento
		if (relacao.getId().compareToIgnoreCase(relacao.getPapelDestino()) == 0)
			throw new Exception("The name of the relation " + relacao.getId() + " was used as its target class role in the metamodel " + metamodelo.getId());
	
		// Verifica se em um auto-relacionamento define um papel de destino
		if ((relacao.getIdClasseOrigem().compareToIgnoreCase(relacao.getIdClasseDestino()) == 0)  &&  (relacao.getPapelDestino().length() == 0))
			throw new Exception("The auto relation " + relacao.getId() + " must specify a target role in the metamodel " + metamodelo.getId());
	}
	
	/**
	 * Verifica a consist�ncia sem�ntica de um metamodelo
	 */
	public void executa(Metamodelo metamodelo) throws Exception
	{
		for (Relacionamento relacionamento : metamodelo.getRelacionamentos())
			verificaSemanticaRelacionamento(metamodelo, relacionamento);
	
		for (Classe classe : metamodelo.getClasses())
			verificaSemanticaClasse(metamodelo, classe);
	}
}