package test.br.unirio.sd.parser;

import org.junit.Assert;
import org.junit.Test;

import br.unirio.sd.control.parser.ParserException;
import br.unirio.sd.control.parser.ParserExpressao;
import br.unirio.sd.model.common.Expressao;
import br.unirio.sd.model.common.TipoOperacao;

public class TestParserExpressao 
{
	private void verifica(ParserExpressao parser, Expressao expressaoEsperado)
	{
		try
		{
			Expressao expressaoResultado = parser.executa();
			comparaExpressoes(expressaoResultado, expressaoEsperado);
		}
		catch(ParserException e)
		{
			Assert.fail(e.getMessage());
		}
	}
	
	private void comparaExpressoes(Expressao expressaoResultado, Expressao expressaoEsperado) 
	{
		if (expressaoResultado.getTipo() != expressaoEsperado.getTipo())
			Assert.fail("Tipo de operação inválido (esperado: " + expressaoResultado.getTipo().name() + "; recebido: " + expressaoEsperado.getTipo().name() + ")");
		
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
		ParserExpressao parser = new ParserExpressao("1 + 1");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(1.0), new Expressao(1.0)));
	}

	@Test
	public void testSomaComProduto() 
	{
		ParserExpressao parser = new ParserExpressao("1 + 3 * 2");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(1.0), new Expressao(TipoOperacao.PRODUTO, new Expressao(3), new Expressao(2))));
	}

	@Test
	public void testProdutoComSoma() 
	{
		ParserExpressao parser = new ParserExpressao("1 * 3 + 2");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(TipoOperacao.PRODUTO, new Expressao(1), new Expressao(3)), new Expressao(2)));
	}

	@Test
	public void testProdutoComSomaIdentificadorMeio() 
	{
		ParserExpressao parser = new ParserExpressao("1 * abc + 2");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(TipoOperacao.PRODUTO, new Expressao(1), new Expressao("abc")), new Expressao(2)));
	}

	@Test
	public void testProdutoComSomaIdentificadorFinal() 
	{
		ParserExpressao parser = new ParserExpressao("1 * 3 + abc");
		verifica(parser, new Expressao(TipoOperacao.SOMA, new Expressao(TipoOperacao.PRODUTO, new Expressao(1), new Expressao(3)), new Expressao("abc")));
	}

	@Test
	public void testSubtracaoSimples() 
	{
		ParserExpressao parser = new ParserExpressao("1 - 3");
		verifica(parser, new Expressao(TipoOperacao.SUBTRACAO, new Expressao(1), new Expressao(3)));
	}

	@Test
	public void testMenosUnario() 
	{
		ParserExpressao parser = new ParserExpressao("-3");
		verifica(parser, new Expressao(TipoOperacao.SUBTRACAO, new Expressao(1), new Expressao(3)));
	}
}