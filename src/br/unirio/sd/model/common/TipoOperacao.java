package br.unirio.sd.model.common;

import lombok.Getter;

public enum TipoOperacao
{
	CONSTANTE,
	VARIAVEL,
	SOMA,
	SUBTRACAO,
	PRODUTO,
	DIVISAO,
	IGUAL,
	DIFERENTE,
	MAIOR,
	MAIORIG,
	MENOR,
	MENORIG,
	MAX("max"),
	MIN("min"),
	ID,
	IF("if"),
	AND("and"),
	OR("or"),
	NOT("not"),
	UMINUS,
	PARENTESES,
	COMMA,
	LOOKUP("lookup"),
	NORMAL("normal"),
	UNIFORM("uniform"),
	ROUND("round"),
	POTENC,
	LOGN("ln"),
	EXPN("exp"),
	SMOOTH("smooth"),
	DELAY3("delay3"),
	DT("dt"),
	TIME("time"),
	GRUPO_SOMA("groupsum"),
	GRUPO_MAX("groupmax"),
	GRUPO_MIN("groupmin"),
	PONTO,
	BETAPERT("betapert"),
	COUNT("count"),
	BOUND("bound"),
	CLSSET,
	INDEX,
	OBJSET,
	SELECT("select");
	
	private @Getter String nome;
	
	private TipoOperacao()
	{
		this.nome = "";
	}
	
	private TipoOperacao(String nome)
	{
		this.nome = nome;
	}
}