package test.br.unirio.sd.parser;

import org.junit.Assert;
import org.junit.Test;

import br.unirio.sd.control.parser.ErrorManager;
import br.unirio.sd.control.parser.Parser;
import br.unirio.sd.model.common.Expressao;
import br.unirio.sd.model.common.TipoOperacao;

public class TestParserExpressao
{
	private void verifica(Parser parser, Expressao expressaoEsperado)
	{
//		try
//		{
			Expressao expressaoResultado = parser.execute();
			comparaExpressoes(expressaoResultado, expressaoEsperado);
//		}
//		catch(ParserException e)
//		{
//			Assert.fail(e.getMessage());
//		}
	}
	
	private void comparaExpressoes(Expressao expressaoResultado, Expressao expressaoEsperado) 
	{
		if (expressaoResultado.getTipo() != expressaoEsperado.getTipo())
			Assert.fail("Tipo de operação inválido (esperado: " + expressaoResultado.getTipo().name() + "; recebido: " + expressaoEsperado.getTipo().name() + ")");
		
		if (expressaoResultado.getValor() != expressaoEsperado.getValor())
			Assert.fail("Valor de parâmetro numérico inválido (esperado: " + expressaoResultado.getValor() + "; recebido: " + expressaoEsperado.getValor() + ")");
		
		if (expressaoResultado.getNome() != null && expressaoEsperado.getNome() == null)
			Assert.fail("Valor de parâmetro string inválido (esperado: " + expressaoResultado.getNome() + "; recebido: null)");
		
		if (expressaoResultado.getNome() == null && expressaoEsperado.getNome() != null)
			Assert.fail("Valor de parâmetro string inválido (esperado: null; recebido: " + expressaoEsperado.getNome() + ")");
		
		if (expressaoResultado.getNome() != null && expressaoEsperado.getNome() != null && expressaoResultado.getNome().compareToIgnoreCase(expressaoEsperado.getNome()) != 0)
			Assert.fail("Valor de parâmetro string inválido (esperado: " + expressaoResultado.getNome() + "; recebido: " + expressaoEsperado.getNome() + ")");
		
		if (expressaoResultado.getEsquerda() == null && expressaoEsperado.getEsquerda() != null)
			Assert.fail("O nó da esquerda do resultado é nulo, enquanto o esperado é diferente de nulo");
		
		if (expressaoResultado.getEsquerda() != null && expressaoEsperado.getEsquerda() == null)
			Assert.fail("O nó da esquerda do resultado é diferente de nulo, enquanto o esperado é nulo");
		
		if (expressaoResultado.getDireita() == null && expressaoEsperado.getDireita() != null)
			Assert.fail("O nó da direita do resultado é nulo, enquanto o esperado é diferente de nulo");
		
		if (expressaoResultado.getDireita() != null && expressaoEsperado.getDireita() == null)
			Assert.fail("O nó da direita do resultado é diferente de nulo, enquanto o esperado é nulo");
		
		if (expressaoResultado.getEsquerda() != null && expressaoEsperado.getEsquerda() != null)
			comparaExpressoes(expressaoResultado.getEsquerda(), expressaoEsperado.getEsquerda());
		
		if (expressaoResultado.getDireita() != null && expressaoEsperado.getDireita() != null)
			comparaExpressoes(expressaoResultado.getDireita(), expressaoEsperado.getDireita());
	}

	@Test
	public void testMaisSimples() 
	{
		Parser parser = new Parser("1 + 1");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(1.0), new Expressao(1.0)));
	}

	@Test
	public void testSomaComProduto() 
	{
		Parser parser = new Parser("1 + 3 * 2");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(1.0), new Expressao(TipoOperacao.PRODUTO, new Expressao(3), new Expressao(2))));
	}

	@Test
	public void testProdutoComSoma() 
	{
		Parser parser = new Parser("1 * 3 + 2");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(TipoOperacao.PRODUTO, new Expressao(1), new Expressao(3)), new Expressao(2)));
	}

	@Test
	public void testProdutoComSomaIdentificadorMeio() 
	{
		Parser parser = new Parser("1 * abc + 2");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(TipoOperacao.PRODUTO, new Expressao(1), new Expressao("abc")), new Expressao(2)));
	}

	@Test
	public void testProdutoComSomaIdentificadorFinal() 
	{
		Parser parser = new Parser("1 * 3 + abc");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(TipoOperacao.PRODUTO, new Expressao(1), new Expressao(3)), new Expressao("abc")));
	}

	@Test
	public void testSubtracaoSimples() 
	{
		Parser parser = new Parser("1 - 3");
		verifica(parser, new Expressao(TipoOperacao.SUBTRACAO, new Expressao(1), new Expressao(3)));
	}

	@Test
	public void testMenosUnario() 
	{
		Parser parser = new Parser("-3");
		verifica(parser, new Expressao(TipoOperacao.UMINUS, new Expressao(3), null));
	}

	@Test
	public void testCondicional() 
	{
		Parser parser = new Parser("IF(2 > 1, 2, 1)");
		verifica(parser, new Expressao(TipoOperacao.IF, new Expressao(TipoOperacao.MAIOR, new Expressao(2.0), new Expressao(1.0)), new Expressao(TipoOperacao.COMMA, new Expressao(2.0), new Expressao(1.0))));
	}

	@Test
	public void testMaximo() 
	{
		Parser parser = new Parser("MAX(3, 2, 1)");
		verifica(parser, new Expressao(TipoOperacao.MAX, new Expressao(TipoOperacao.COMMA, new Expressao(TipoOperacao.COMMA, new Expressao(3.0), new Expressao(2.0)), new Expressao(1.0)), null));
	}

	@Test
	public void testMinimo() 
	{
		Parser parser = new Parser("MAX(4, 3, 2, 1)");
		verifica(parser, new Expressao(TipoOperacao.MAX, new Expressao(TipoOperacao.COMMA, new Expressao(TipoOperacao.COMMA, new Expressao(TipoOperacao.COMMA, new Expressao(4.0), new Expressao(3.0)), new Expressao(2.0)), new Expressao(1.0)), null));
	}

	@Test
	public void testExpressaoErrada() 
	{
		ErrorManager.getInstance().setDiscrete();
		Parser parser = new Parser("1 + ");
		Assert.assertNull(parser.execute());
		ErrorManager.getInstance().setVerbose();
	}
}