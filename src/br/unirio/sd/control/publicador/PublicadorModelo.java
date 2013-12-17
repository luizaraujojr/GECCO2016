package br.unirio.sd.control.publicador;

import java.io.PrintWriter;

import br.unirio.sd.model.common.Expressao;
import br.unirio.sd.model.common.TipoOperacao;
import br.unirio.sd.model.modelo.Ativacao;
import br.unirio.sd.model.modelo.Conexao;
import br.unirio.sd.model.modelo.Modelo;
import br.unirio.sd.model.modelo.Objeto;
import br.unirio.sd.model.modelo.Propriedade;
import br.unirio.sd.model.modelo.Relacionamento;

public class PublicadorModelo
{
	/**
	 * Imprime uma árvore de expressões
	 */
	private void imprimeRelacoes (PrintWriter out, Expressao no)
	{
		if (no.getTipoOperacao() == TipoOperacao.COMMA)
		{
			imprimeRelacoes(out, no.getDir());
			out.print(", ");
			imprimeRelacoes(out, no.getEsq());
		}
		else if (no.getTipoOperacao() == TipoOperacao.VARIAVEL)
		{
			out.print(no.getNome());
		}
	}
	
	/**
	 * Imprime os dados de um objeto
	 */
	private void imprimeObjeto (PrintWriter out, Objeto objeto)
	{
		out.print("\tOBJECT " + objeto.getId() + " FROM CLASS " + objeto.getIdClasse() + "\n");
	
		for (Propriedade propriedade : objeto.getPropriedades())
			out.print("\t\tPROPERTY " + propriedade.getId() + " = " + propriedade.getValor() + "\n");
	
		for (Relacionamento relacionamento : objeto.getRelacionamentos())
		{
			out.print("\t\tRELATION " + relacionamento.getId() + " = ");
			imprimeRelacoes(out, relacionamento.getElementos());
			out.print("\n");
		}
	}
	
	/**
	 * Imprime os dados de uma ativação de cenário
	 */
	private void imprimeAtivacao(PrintWriter out, Ativacao ativacao)
	{
		out.print("\tATIVATE " + ativacao.getIdCenario() + " OVER\n");
	
		for (Conexao conexao : ativacao.getConexoes())
			out.print("\t\t" + conexao.getIdObjeto() + " AS " + conexao.getIdConexao() + "\n");
	}
	
	/**
	 * Imprime os dados de um meta modelo
	 */
	public void imprimeModelo (PrintWriter out, Modelo modelo)
	{
		out.print("MODEL " + modelo.getId() + " FROM METAMODEL " + modelo.getIdMetaModelo() + "\n");
	
		for (Objeto objeto : modelo.getObjetos())
			imprimeObjeto(out, objeto);
	
		for (Ativacao ativacao : modelo.getAtivacoes())
			imprimeAtivacao(out, ativacao);
	}
}