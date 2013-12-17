package br.unirio.sd.control.publicador;

import java.io.PrintWriter;

import br.unirio.sd.model.cenario.Ajuste;
import br.unirio.sd.model.cenario.Cenario;
import br.unirio.sd.model.cenario.Conexao;
import br.unirio.sd.model.cenario.Restricao;
import br.unirio.sd.model.common.Expressao;
import br.unirio.sd.model.meta.Classe;
import br.unirio.sd.model.meta.Comportamento;
import br.unirio.sd.model.meta.Metamodelo;
import br.unirio.sd.model.meta.Propriedade;
import br.unirio.sd.model.meta.Relacionamento;
import br.unirio.sd.model.meta.TipoRelacionamento;
import br.unirio.sd.model.meta.Visibilidade;

public class PublicadorMetamodelo
{
	/**
	 * Imprime uma árvore de expressões
	 */
	private void imprimeArvore(PrintWriter out, Expressao no)
	{
		switch (no.getTipoOperacao())
		{
		    case CONSTANTE:
		    	out.print(no.getValor());
				break;
	
		    case VARIAVEL:
				out.print(no.getNome());
				break;
	
		    case SOMA:
				imprimeArvore(out, no.getEsq());
				out.print(" + ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case SUBTRACAO:
				imprimeArvore(out, no.getEsq());
				out.print(" - ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case PRODUTO:
				imprimeArvore(out, no.getEsq());
				out.print(" * ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case DIVISAO:
				imprimeArvore(out, no.getEsq());
				out.print(" / ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case POTENC:
				imprimeArvore(out, no.getEsq());
				out.print(" ^ ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case IGUAL:
				imprimeArvore(out, no.getEsq());
				out.print(" = ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case DIFERENTE:
				imprimeArvore(out, no.getEsq());
				out.print(" <> ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case MAIOR:
				imprimeArvore(out, no.getEsq());
				out.print(" > ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case MAIORIG:
				imprimeArvore(out, no.getEsq());
				out.print(" >= ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case MENOR:
				imprimeArvore(out, no.getEsq());
				out.print(" < ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case MENORIG:
				imprimeArvore(out, no.getEsq());
				out.print(" <= ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case MAX:
				out.print("MAX (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case MIN:
				out.print("MIN (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case IF:
				out.print("IF (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case LOOKUP:
				out.print("LOOKUP (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case AND:
				out.print("AND (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case OR:
				out.print("OR (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case NOT:
				out.print("NOT (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case UMINUS:
				out.print(" -");
				imprimeArvore(out, no.getEsq());
				break;
	
		    case PARENTESES:
				out.print("(");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case NORMAL:
				out.print("NORMAL (");
				imprimeArvore(out, no.getEsq());
				out.print(", ");
				imprimeArvore(out, no.getDir());
				out.print(")");
				break;
	
		    case BETAPERT:
		    	out.print("BETAPERT (");
				imprimeArvore(out, no.getEsq());
				out.print(", ");
				imprimeArvore(out, no.getDir().getEsq());
				out.print(", ");
				imprimeArvore(out, no.getDir().getDir());
				out.print(")");
				break;
	
		    case UNIFORM:
				out.print("UNIFORM (");
				imprimeArvore(out, no.getEsq());
				out.print(", ");
				imprimeArvore(out, no.getDir());
				out.print(")");
				break;
	
		    case ROUND:
				out.print("ROUND (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case EXPN:
				out.print("EXP (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case LOGN:
				out.print("LN (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case GRUPO_SOMA:
				out.print("GROUPSUM (");
				imprimeArvore(out, no.getEsq());
				out.print(", ");
				imprimeArvore(out, no.getDir());
				out.print(")");
				break;
	
		    case GRUPO_MAX:
				out.print("GROUPMAX (");
				imprimeArvore(out, no.getEsq());
				out.print(", ");
				imprimeArvore(out, no.getDir());
				out.print(")");
				break;
	
		    case GRUPO_MIN:
				out.print("GROUPMIN (");
				imprimeArvore(out, no.getEsq());
				out.print(", ");
				imprimeArvore(out, no.getDir());
				out.print(")");
				break;
	
		    case COUNT:
				out.print("COUNT (");
				imprimeArvore(out, no.getEsq());
				out.print(")");
				break;
	
		    case PONTO:
				imprimeArvore(out, no.getEsq());
				out.print(".");
				imprimeArvore(out, no.getDir());
				break;
	
		    case COMMA:
				imprimeArvore(out, no.getEsq());
				out.print(", ");
				imprimeArvore(out, no.getDir());
				break;
	
		    case DT:
				out.print("DT");
				break;
	
		    case TIME:
				out.print("TIME");
				break;
	
		    default:
				out.print("<Expression not found!>");
				break;
		}
	}
	
	/**
	 * Imprime os dados de um comportamento
	 */
	private void imprimeComportamento(PrintWriter out, Comportamento comportamento)
	{
		char c = (comportamento.getVisibilidade() == Visibilidade.PUBLIC) ? '+' : '-';
		out.print("\t\t" + c + " ");
	
		switch (comportamento.getTipo())
		{
		    case STOCK:
				out.print("STOCK " + comportamento.getId() + " = ");
				imprimeArvore(out, comportamento.getExpressao());
				break;
	
		    case RATE:
				out.print("RATE (" + comportamento.getRepositorio());
				out.print(") " + comportamento.getId() + " = ");
				imprimeArvore(out, comportamento.getExpressao());
				break;
	
		    case PROCESS:
				out.print("PROC " + comportamento.getId() + " = ");
				imprimeArvore(out, comportamento.getExpressao());
				break;
	
		    case TABLE:
				out.print("TABLE " + comportamento.getId() + " = ");
				imprimeArvore(out, comportamento.getExpressao());
				break;
	
		    default:
				out.print("???? " + comportamento.getId() + " <Behavior not found!>");
				break;
		}
	
		out.print("\n");
	}
	
	/**
	 * Imprime os dados de uma propriedade
	 */
	private void imprimePropriedade(PrintWriter out, Propriedade propriedade)
	{
		char c = (propriedade.getVisibilidade() == Visibilidade.PUBLIC) ? '+' : '-';
		out.print("\t\t" + c + " ");
		out.print("PROPERTY " + propriedade.getId() + " " + propriedade.getValor() + "\n");
	}
	
	/**
	 * Imprime os dados de uma classe
	 */
	private void imprimeClasse(PrintWriter out, Classe classe)
	{
		out.print("\t" + classe.getId() + "\n");
	
		for (Propriedade propriedade : classe.getPropriedades())
			imprimePropriedade (out, propriedade);
	
		for (Comportamento comportamento : classe.getComportamentos())
			imprimeComportamento(out, comportamento);
	}
	
	/**
	 * Imprime os dados de um relacionamento
	 */
	private void imprimeRelacionamento(PrintWriter out, Relacionamento relacao)
	{
		out.print("\t");
		
		if (relacao.getTipo() == TipoRelacionamento.SINGLE)
			out.print("RELATION ");
		else
			out.print("MULTIRELATION ");
	
		out.print(relacao.getId() + " " + relacao.getIdClasseOrigem() + ", " + relacao.getIdClasseDestino());
	
		if (relacao.getPapelDestino().length() > 0)
			out.print("(" + relacao.getPapelDestino() + ")");
	
		out.print("\n");
	}
	
	/**
	 * Imprime os dados de um ajuste				
	 */
	private void imprimeAjuste(PrintWriter out, Ajuste ajuste)
	{
		out.print("\t\tAFFECT " + ajuste.getId() + " ");
		imprimeArvore(out, ajuste.getExpressao());
		out.print("\n");
	}

	/**
	 * Imprime os dados de uma conexao				
	 */
	private void imprimeConexao (PrintWriter out, Conexao conexao)
	{
		out.print("\t" + conexao.getId() + " (" + conexao.getIdClasse() + ")\n");

		for (Propriedade propriedade : conexao.getPropriedades())
			imprimePropriedade(out, propriedade);

		for (Comportamento comportamento : conexao.getComportamentos())
			imprimeComportamento(out, comportamento);

		for (Ajuste ajuste : conexao.getAjustes())
			imprimeAjuste(out, ajuste);
	}

	/**
	 * Imprime os dados de uma restrição
	 */
	private void imprimeRestricao (PrintWriter out, Restricao restricao)
	{
		out.print("\tCONSTRAINT " + restricao.getIdConexaoOrigem());

		if (restricao.getIdRelacao().length() > 0)
			out.print("." + restricao.getIdRelacao());

		out.print(", " + restricao.getIdCenario() + "." + restricao.getIdConexaoDestino() + "\n");
	}

	/**
	 * Imprime os dados de um cenário
	 */
	private void imprimeCenario(PrintWriter out, Cenario cenario)
	{
		out.println("CENARIO " + cenario.getId());

		for (Conexao conexao : cenario.getConexoes())
			imprimeConexao(out, conexao);

		for (Restricao restricao : cenario.getRestricoes())
			imprimeRestricao(out, restricao);
	}
	
	/**
	 * Imprime os dados de um meta modelo
	 */
	public void publica(PrintWriter out, Metamodelo metamodelo)
	{
		out.println(metamodelo.getId());
	
		for (Classe classe : metamodelo.getClasses())
			imprimeClasse(out, classe);
	
		for (Relacionamento relacao : metamodelo.getRelacionamentos())
			imprimeRelacionamento(out, relacao);
	
		for (Cenario cenario : metamodelo.getCenarios())
			imprimeCenario(out, cenario);
	}
}