package br.unirio.sd.control.gerador;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import br.unirio.sd.model.common.Expressao;
import br.unirio.sd.model.meta.Classe;
import br.unirio.sd.model.meta.Comportamento;
import br.unirio.sd.model.meta.Metamodelo;
import br.unirio.sd.model.meta.Propriedade;
import br.unirio.sd.model.meta.Relacionamento;
import br.unirio.sd.model.meta.TipoComportamento;
import br.unirio.sd.model.meta.TipoRelacionamento;

public class Gerador
{
	public void executa(String diretorio, Metamodelo metamodelo) throws Exception
	{
        // APLICAR OS CENÁRIOS SOBRE AS CLASSES

		for (Classe classe : metamodelo.getClasses())
			geraClasse(diretorio, metamodelo, classe);
	}

	private void geraClasse(String diretorio, Metamodelo metamodelo, Classe classe) throws Exception
	{
		PrintWriter writer = new PrintWriter(diretorio + classe.getId() + ".txt", "UTF-8");
        writer.println("public class " + classe.getId());
        writer.println("{");
        
        for (Propriedade propriedade : classe.getPropriedades())
        	writer.println("\tprivate double " + propriedade.getId() + " = " + propriedade.getValor() + ";");
        
        for (Comportamento comportamento : classe.getComportamentos())
        	if (comportamento.getTipo() == TipoComportamento.STOCK)
        		writer.println("\tprivate double " + comportamento.getId() + " = 0;");

        for (Relacionamento relacionamento : metamodelo.getRelacionamentos())
        	if (relacionamento.getClasseOrigem() == classe)
        		if (relacionamento.getTipo() == TipoRelacionamento.MULTIPLE)
        			writer.println("\tprivate List<" + relacionamento.getClasseDestino().getId() + "> " + relacionamento.getId() + ";");
        		else
        			writer.println("\tprivate " + relacionamento.getClasseDestino().getId() + " " + relacionamento.getId() + ";");

        writer.println();
        writer.println("\tpublic " + classe.getId() + "()");
        writer.println("\t{");

        for (Relacionamento relacionamento : metamodelo.getRelacionamentos())
        	if (relacionamento.getClasseOrigem() == classe)
        		if (relacionamento.getTipo() == TipoRelacionamento.MULTIPLE)
        			writer.println("\t\tthis." + relacionamento.getId() + " = new ArrayList<" + relacionamento.getClasseDestino().getId() + ">();");

        writer.println("\t}");
        writer.println();
        
        writer.println("\tpublic void init()");
        writer.println("\t{");
        
        for (Comportamento comportamento : classe.getComportamentos())
        	if (comportamento.getTipo() == TipoComportamento.STOCK)
        		writer.println("\t\t" + comportamento.getId() + " = " + imprimeExpressao(comportamento.getExpressao()) + ";");

        writer.println("\t}");
        writer.println();
        
        writer.println("\tpublic void step()");
        writer.println("\t{");
        
        // ORDENAR OS COMPORTAMENTOS TOPOLOGICAMENTE
        List<Comportamento> comportamentos = new ArrayList<Comportamento>();

        for (Comportamento comportamento : classe.getComportamentos())
        	if (comportamento.getTipo() == TipoComportamento.RATE || comportamento.getTipo() == TipoComportamento.PROCESS)
        		comportamentos.add(comportamento);
        
        List<Comportamento> comportamentosOrdenados = ordenaTopologicamente(comportamentos, classe);

        for (Comportamento comportamento : comportamentosOrdenados)
        {
        	if (comportamento.getTipo() != TipoComportamento.STOCK)
        		writer.println("\t\t" + comportamento.getId() + " = " + imprimeExpressao(comportamento.getExpressao()) + ";");
        }
        
//    	private Expressao repositorio;		// somente para RATES
        
        writer.println("\t}");
        writer.println("}");
        writer.close();
	}
	
	private List<Comportamento> ordenaTopologicamente(List<Comportamento> comportamentos, Classe classe)
	{
		return null;
	}

	/**
	 * Imprime uma árvore de expressões
	 */
	private String imprimeExpressao(Expressao no)
	{
		switch (no.getTipo())
		{
		    case CONSTANTE:
		    	return no.getValor() + "";
	
		    case VARIAVEL:
				return no.getNome();
	
		    case SOMA:
				return imprimeExpressao(no.getEsquerda()) + " + " + imprimeExpressao(no.getDireita());
	
		    case SUBTRACAO:
				return imprimeExpressao(no.getEsquerda()) + " - " + imprimeExpressao(no.getDireita());
	
		    case PRODUTO:
				return imprimeExpressao(no.getEsquerda()) + " * " + imprimeExpressao(no.getDireita());
	
		    case DIVISAO:
				return imprimeExpressao(no.getEsquerda()) + " / " + imprimeExpressao(no.getDireita());
	
		    case POTENC:
				return imprimeExpressao(no.getEsquerda()) + " ^ " + imprimeExpressao(no.getDireita());
	
		    case IGUAL:
				return imprimeExpressao(no.getEsquerda()) + " == " + imprimeExpressao(no.getDireita());
	
		    case DIFERENTE:
				return imprimeExpressao(no.getEsquerda()) + " != " + imprimeExpressao(no.getDireita());
	
		    case MAIOR:
				return imprimeExpressao(no.getEsquerda()) + " > " + imprimeExpressao(no.getDireita());
	
		    case MAIORIG:
				return imprimeExpressao(no.getEsquerda()) + " >= " + imprimeExpressao(no.getDireita());
	
		    case MENOR:
				return imprimeExpressao(no.getEsquerda()) + " < " + imprimeExpressao(no.getDireita());
	
		    case MENORIG:
				return imprimeExpressao(no.getEsquerda()) + " <= " + imprimeExpressao(no.getDireita());
	
		    case MAX:
				return "MAX (" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case MIN:
				return "MIN (" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case IF:
				return "IF(" + imprimeExpressao(no.getEsquerda()) + ", " + imprimeExpressao(no.getDireita().getEsquerda()) + ", " + imprimeExpressao(no.getDireita().getDireita()) + ")";
	
		    case LOOKUP:
				return "LOOKUP(" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case AND:
				return "AND(" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case OR:
				return "OR(" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case NOT:
				return "NOT(" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case UMINUS:
				return "-" + imprimeExpressao(no.getEsquerda());
	
		    case PARENTESES:
				return "(" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case CLSSET:
				return "[" + imprimeExpressao(no.getEsquerda()) + "]";
	
		    case NORMAL:
				return "NORMAL(" + imprimeExpressao(no.getEsquerda()) + ", " + imprimeExpressao(no.getDireita()) + ")";
	
		    case BETAPERT:
				return "BETAPERT(" + imprimeExpressao(no.getEsquerda()) + ", " + imprimeExpressao(no.getDireita().getEsquerda()) + ", " + imprimeExpressao(no.getDireita().getDireita()) + ")";
	
		    case UNIFORM:
				return "UNIFORM(" + imprimeExpressao(no.getEsquerda()) + ", " + imprimeExpressao(no.getDireita()) + ")";
	
		    case ROUND:
				return "ROUND(" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case EXPN:
				return "EXP(" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case LOGN:
				return "LN(" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case GRUPO_SOMA:
				return "GROUPSUM(" + imprimeExpressao(no.getEsquerda()) + ", " + imprimeExpressao(no.getDireita()) + ")";
	
		    case GRUPO_MAX:
				return "GROUPMAX(" + imprimeExpressao(no.getEsquerda()) + ", " + imprimeExpressao(no.getDireita()) + ")";
	
		    case GRUPO_MIN:
				return "GROUPMIN(" + imprimeExpressao(no.getEsquerda()) + ", " + imprimeExpressao(no.getDireita()) + ")";
	
		    case COUNT:
				return "COUNT(" + imprimeExpressao(no.getEsquerda()) + ")";
	
		    case PONTO:
				return imprimeExpressao(no.getEsquerda()) + "." + imprimeExpressao(no.getDireita());
	
		    case COMMA:
				return imprimeExpressao(no.getEsquerda()) + ", " + imprimeExpressao(no.getDireita());
	
		    case DT:
				return "DT";
	
		    case TIME:
				return "TIME";
				
		    case BOUND:
		    	return "BOUND(" + imprimeExpressao(no.getEsquerda()) + ", " + imprimeExpressao(no.getDireita()) + ")";
	
		    default:
				return "<Expression not found!>";
		}
	}
}