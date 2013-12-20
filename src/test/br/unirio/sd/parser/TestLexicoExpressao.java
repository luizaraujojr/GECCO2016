package test.br.unirio.sd.parser;

import org.junit.Assert;
import org.junit.Test;

import br.unirio.sd.control.lex.Lexico;
import br.unirio.sd.control.lex.LexicoException;
import br.unirio.sd.control.lex.TipoToken;
import br.unirio.sd.control.lex.Token;

public class TestLexicoExpressao 
{
	private Token proximoToken(Lexico lexico)
	{
		try
		{
			return lexico.proximo();
		}
		catch(LexicoException e)
		{
			Assert.fail("Ocorreu uma exceção ao pegar o próximo token.");
			return null;
		}
	}
	
	private void verifica(Lexico lexico, Token... tokensEsperados)
	{
		int contador = 0;
		
		for (Token t : tokensEsperados)
		{
			Token proximo = proximoToken(lexico);
			
			if (proximo.getTipo() != t.getTipo())
				Assert.fail("Tipo de token inválido na posição " + contador + " (esperado: " + t.getTipo().name() + "; recebido: " + proximo.getTipo().name() + ")");
			
			if (proximo.getValor() != t.getValor())
				Assert.fail("Valor de token inválido na posição " + contador + " (esperado: " + t.getValor() + "; recebido: " + proximo.getValor() + ")");
			
			if (proximo.getTipo() == TipoToken.ID && proximo.getNome().compareToIgnoreCase(t.getNome()) != 0)
				Assert.fail("Nome de identificador inválido na posição " + contador + " (esperado: " + t.getNome() + "; recebido: " + proximo.getNome() + ")");
			
			contador++;
		}
	}
	
	@Test
	public void testMaisSimples() 
	{
		verifica(new Lexico("1 + 1"), new Token(1.0), new Token(TipoToken.PLUS), new Token(1.0));
	}
	
	@Test
	public void testExpressaoDupla() 
	{
		verifica(new Lexico("1 + a * 3"), new Token(1.0), new Token(TipoToken.PLUS), new Token("a"), new Token(TipoToken.ASTER), new Token(3));
	}
	
	@Test
	public void testExpressaoTripla() 
	{
		verifica(new Lexico("1 + a * 3 / 10"), new Token(1.0), new Token(TipoToken.PLUS), new Token("a"), new Token(TipoToken.ASTER), new Token(3), new Token(TipoToken.DIV), new Token(10));
	}
	
	@Test
	public void testExpressaoSubtracao() 
	{
		verifica(new Lexico("10 - abc"), new Token(10), new Token(TipoToken.MINUS), new Token("abc"));
	}
	
	@Test
	public void testExpressaoPotencia() 
	{
		verifica(new Lexico("x ^ y"), new Token("x"), new Token(TipoToken.POWER), new Token("y"));
	}
	
	@Test
	public void testExpressaoMenosUnario() 
	{
		verifica(new Lexico("- abc"), new Token(TipoToken.MINUS), new Token("abc"));
	}
	
	@Test
	public void testExpressaoParenteses() 
	{
		verifica(new Lexico("(success)"), new Token(TipoToken.OPEN_PARENTHESIS), new Token("success"), new Token(TipoToken.CLOSE_PARENTHESIS));
	}
	
	@Test
	public void testExpressaoColchetes() 
	{
		verifica(new Lexico("array[1]"), new Token("array"), new Token(TipoToken.OPEN_INDEX), new Token(1), new Token(TipoToken.CLOSE_INDEX));
	}
	
	@Test
	public void testExpressaoMaior() 
	{
		verifica(new Lexico("a > b"), new Token("a"), new Token(TipoToken.GT), new Token("b"));
	}
	
	@Test
	public void testExpressaoMaiorIgual() 
	{
		verifica(new Lexico("a >= b"), new Token("a"), new Token(TipoToken.GE), new Token("b"));
	}
	
	@Test
	public void testExpressaoMenor() 
	{
		verifica(new Lexico("a < b"), new Token("a"), new Token(TipoToken.LT), new Token("b"));
	}
	
	@Test
	public void testExpressaoMenorIgual() 
	{
		verifica(new Lexico("a <= b"), new Token("a"), new Token(TipoToken.LE), new Token("b"));
	}
	
	@Test
	public void testExpressaoIgual() 
	{
		verifica(new Lexico("a = b"), new Token("a"), new Token(TipoToken.EQ), new Token("b"));
	}
	
	@Test
	public void testExpressaoDiferente() 
	{
		verifica(new Lexico("a <> b"), new Token("a"), new Token(TipoToken.DIF), new Token("b"));
	}
	
	@Test
	public void testExpressaoNumeroFracionado() 
	{
		verifica(new Lexico("1.5 <> .5"), new Token(1.5), new Token(TipoToken.DIF), new Token(0.5));
	}
	
	@Test
	public void testExpressaoPonto() 
	{
		verifica(new Lexico("x.y"), new Token("x"), new Token(TipoToken.POINT), new Token("y"));
	}
	
	@Test
	public void testExpressaoVirgula() 
	{
		verifica(new Lexico("1, 2, 3, 4"), new Token(1), new Token(TipoToken.COMMA), new Token(2), new Token(TipoToken.COMMA), new Token(3), new Token(TipoToken.COMMA), new Token(4));
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
		verifica(new Lexico("max(1, 2)"), new Token(TipoToken.MAX), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.COMMA), new Token(2.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}
	
	@Test
	public void testFuncaoMinimo() 
	{
		verifica(new Lexico("min(1, 2)"), new Token(TipoToken.MIN), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.COMMA), new Token(2.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}
	
	@Test
	public void testFuncaoIF() 
	{
		verifica(new Lexico("if(1, 2, 3)"), new Token(TipoToken.IF), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.COMMA), new Token(2.0), new Token(TipoToken.COMMA), new Token(3.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoAnd() 
	{
		verifica(new Lexico("and(1, 2)"), new Token(TipoToken.AND), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.COMMA), new Token(2.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoOr() 
	{
		verifica(new Lexico("or(1, 2)"), new Token(TipoToken.OR), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.COMMA), new Token(2.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoNot() 
	{
		verifica(new Lexico("not(1)"), new Token(TipoToken.NOT), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoLookup() 
	{
		verifica(new Lexico("lookup(1)"), new Token(TipoToken.LOOKUP), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoArredondamento() 
	{
		verifica(new Lexico("round(1)"), new Token(TipoToken.ROUND), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoLogaritmo() 
	{
		verifica(new Lexico("ln(1)"), new Token(TipoToken.LOGN), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoExponencial() 
	{
		verifica(new Lexico("exp(1)"), new Token(TipoToken.EXPN), new Token(TipoToken.OPEN_PARENTHESIS), new Token(1.0), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoDelta() 
	{
		verifica(new Lexico("DT"), new Token(TipoToken.DT));
	}

	@Test
	public void testFuncaoTempo() 
	{
		verifica(new Lexico("TIME"), new Token(TipoToken.TIME));
	}

	@Test
	public void testFuncaoGrupoSoma() 
	{
		verifica(new Lexico("GROUPSUM(10)"), new Token(TipoToken.GRUPO_SOMA), new Token(TipoToken.OPEN_PARENTHESIS), new Token(10), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoGrupoMaximo() 
	{
		verifica(new Lexico("GROUPMAX(10)"), new Token(TipoToken.GRUPO_MAX), new Token(TipoToken.OPEN_PARENTHESIS), new Token(10), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoGrupoMinimo() 
	{
		verifica(new Lexico("GROUPMIN(10)"), new Token(TipoToken.GRUPO_MIN), new Token(TipoToken.OPEN_PARENTHESIS), new Token(10), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoCount() 
	{
		verifica(new Lexico("COUNT(10)"), new Token(TipoToken.COUNT), new Token(TipoToken.OPEN_PARENTHESIS), new Token(10), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoBound() 
	{
		verifica(new Lexico("BOUND(10)"), new Token(TipoToken.BOUND), new Token(TipoToken.OPEN_PARENTHESIS), new Token(10), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoSelect() 
	{
		verifica(new Lexico("SELECT(10)"), new Token(TipoToken.SELECT), new Token(TipoToken.OPEN_PARENTHESIS), new Token(10), new Token(TipoToken.CLOSE_PARENTHESIS));
	}

	@Test
	public void testFuncaoSelectCaseInsensitive() 
	{
		verifica(new Lexico("SeLeCt(10)"), new Token(TipoToken.SELECT), new Token(TipoToken.OPEN_PARENTHESIS), new Token(10), new Token(TipoToken.CLOSE_PARENTHESIS));
	}
}