package test.br.unirio.sd.parser;

import org.junit.Assert;
import org.junit.Test;

import br.unirio.sd.control.parser.Lexico;
import br.unirio.sd.control.parser.Parser;
import br.unirio.sd.control.parser.Token;

public class TestLexicoExpressao 
{
	private void verifica(Lexico lexico, Token... tokensEsperados)
	{
		int contador = 0;
		
		for (Token t : tokensEsperados)
		{
			Token proximo = lexico.proximo();
			
			if (proximo.getTipo() != t.getTipo())
				Assert.fail("Tipo de token inválido na posição " + contador + " (esperado: " + t.getTipo() + "; recebido: " + proximo.getTipo() + ")");
			
			if (proximo.getValor() != t.getValor())
				Assert.fail("Valor de token inválido na posição " + contador + " (esperado: " + t.getValor() + "; recebido: " + proximo.getValor() + ")");
			
			if (proximo.getTipo() == Parser.ID && proximo.getNome().compareToIgnoreCase(t.getNome()) != 0)
				Assert.fail("Nome de identificador inválido na posição " + contador + " (esperado: " + t.getNome() + "; recebido: " + proximo.getNome() + ")");
			
			contador++;
		}
	}
	
	@Test
	public void testMaisSimples() 
	{
		verifica(new Lexico("1 + 1"), new Token(1.0), new Token(Parser.PLUS), new Token(1.0));
	}
	
	@Test
	public void testExpressaoDupla() 
	{
		verifica(new Lexico("1 + a * 3"), new Token(1.0), new Token(Parser.PLUS), new Token("a"), new Token(Parser.ASTER), new Token(3.0));
	}
	
	@Test
	public void testExpressaoTripla() 
	{
		verifica(new Lexico("1 + a * 3 / 10"), new Token(1.0), new Token(Parser.PLUS), new Token("a"), new Token(Parser.ASTER), new Token(3.0), new Token(Parser.DIV), new Token(10.0));
	}
	
	@Test
	public void testExpressaoSubtracao() 
	{
		verifica(new Lexico("10 - abc"), new Token(10.0), new Token(Parser.MINUS), new Token("abc"));
	}
	
	@Test
	public void testExpressaoPotencia() 
	{
		verifica(new Lexico("x ^ y"), new Token("x"), new Token(Parser.POTENC), new Token("y"));
	}
	
	@Test
	public void testExpressaoMenosUnario() 
	{
		verifica(new Lexico("- abc"), new Token(Parser.MINUS), new Token("abc"));
	}
	
	@Test
	public void testExpressaoParenteses() 
	{
		verifica(new Lexico("(success)"), new Token(Parser.L_PARENT), new Token("success"), new Token(Parser.R_PARENT));
	}
	
	@Test
	public void testExpressaoColchetes() 
	{
		verifica(new Lexico("array[1]"), new Token("array"), new Token(Parser.L_COLCH), new Token(1.0), new Token(Parser.R_COLCH));
	}
	
	@Test
	public void testExpressaoMaior() 
	{
		verifica(new Lexico("a > b"), new Token("a"), new Token(Parser.GT), new Token("b"));
	}
	
	@Test
	public void testExpressaoMaiorIgual() 
	{
		verifica(new Lexico("a >= b"), new Token("a"), new Token(Parser.GE), new Token("b"));
	}
	
	@Test
	public void testExpressaoMenor() 
	{
		verifica(new Lexico("a < b"), new Token("a"), new Token(Parser.LT), new Token("b"));
	}
	
	@Test
	public void testExpressaoMenorIgual() 
	{
		verifica(new Lexico("a <= b"), new Token("a"), new Token(Parser.LE), new Token("b"));
	}
	
	@Test
	public void testExpressaoIgual() 
	{
		verifica(new Lexico("a = b"), new Token("a"), new Token(Parser.EQUAL), new Token("b"));
	}
	
	@Test
	public void testExpressaoDiferente() 
	{
		verifica(new Lexico("a <> b"), new Token("a"), new Token(Parser.DIF), new Token("b"));
	}
	
	@Test
	public void testExpressaoNumeroFracionado() 
	{
		verifica(new Lexico("1.5 <> .5"), new Token(1.5), new Token(Parser.DIF), new Token(0.5));
	}
	
	@Test
	public void testExpressaoPonto() 
	{
		verifica(new Lexico("x.y"), new Token("x"), new Token(Parser.POINT), new Token("y"));
	}
	
	@Test
	public void testExpressaoVirgula() 
	{
		verifica(new Lexico("1, 2, 3, 4"), new Token(1.0), new Token(Parser.COMMA), new Token(2.0), new Token(Parser.COMMA), new Token(3.0), new Token(Parser.COMMA), new Token(4.0));
	}
	
	@Test
	public void testIdentificadorComNumero() 
	{
		verifica(new Lexico("abc12"), new Token("abc12"));
	}
	
	@Test
	public void testIdentificadorComUnderscore() 
	{
		verifica(new Lexico("ab_c"), new Token("ab_c"));
	}
	
	@Test
	public void testIdentificadorComecandoUnderscore() 
	{
		verifica(new Lexico("_abc"), new Token("_abc"));
	}
	
	@Test
	public void testFuncaoMaximo() 
	{
		verifica(new Lexico("max(1, 2)"), new Token(Parser.MAX), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.COMMA), new Token(2.0), new Token(Parser.R_PARENT));
	}
	
	@Test
	public void testFuncaoMinimo() 
	{
		verifica(new Lexico("min(1, 2)"), new Token(Parser.MIN), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.COMMA), new Token(2.0), new Token(Parser.R_PARENT));
	}
	
	@Test
	public void testFuncaoIF() 
	{
		verifica(new Lexico("if(1, 2, 3)"), new Token(Parser.IF), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.COMMA), new Token(2.0), new Token(Parser.COMMA), new Token(3.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoAnd() 
	{
		verifica(new Lexico("and(1, 2)"), new Token(Parser.AND), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.COMMA), new Token(2.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoOr() 
	{
		verifica(new Lexico("or(1, 2)"), new Token(Parser.OR), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.COMMA), new Token(2.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoNot() 
	{
		verifica(new Lexico("not(1)"), new Token(Parser.NOT), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoLookup() 
	{
		verifica(new Lexico("lookup(1)"), new Token(Parser.LOOKUP), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoArredondamento() 
	{
		verifica(new Lexico("round(1)"), new Token(Parser.ROUND), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoLogaritmo() 
	{
		verifica(new Lexico("ln(1)"), new Token(Parser.LN), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoExponencial() 
	{
		verifica(new Lexico("exp(1)"), new Token(Parser.EXP), new Token(Parser.L_PARENT), new Token(1.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoDelta() 
	{
		verifica(new Lexico("DT"), new Token(Parser.DT));
	}

	@Test
	public void testFuncaoTempo() 
	{
		verifica(new Lexico("TIME"), new Token(Parser.TIME));
	}

	@Test
	public void testFuncaoGrupoSoma() 
	{
		verifica(new Lexico("GROUPSUM(10)"), new Token(Parser.GROUPSUM), new Token(Parser.L_PARENT), new Token(10.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoGrupoMaximo() 
	{
		verifica(new Lexico("GROUPMAX(10)"), new Token(Parser.GROUPMAX), new Token(Parser.L_PARENT), new Token(10.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoGrupoMinimo() 
	{
		verifica(new Lexico("GROUPMIN(10)"), new Token(Parser.GROUPMIN), new Token(Parser.L_PARENT), new Token(10.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoCount() 
	{
		verifica(new Lexico("COUNT(10)"), new Token(Parser.COUNT), new Token(Parser.L_PARENT), new Token(10.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoBound() 
	{
		verifica(new Lexico("BOUND(10)"), new Token(Parser.BOUND), new Token(Parser.L_PARENT), new Token(10.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoSelect() 
	{
		verifica(new Lexico("SELECT(10)"), new Token(Parser.SELECT), new Token(Parser.L_PARENT), new Token(10.0), new Token(Parser.R_PARENT));
	}

	@Test
	public void testFuncaoSelectCaseInsensitive() 
	{
		verifica(new Lexico("SeLeCt(10)"), new Token(Parser.SELECT), new Token(Parser.L_PARENT), new Token(10.0), new Token(Parser.R_PARENT));
	}
}