package br.unirio.sd.control.lex;

import lombok.Getter;

/**
 * Tipo de token gerado pelo analisador léxico de expressões
 * 
 * @author Marcio
 */
public enum TipoToken
{
	CONST, 
	ID,
	LT, 
	LE, 
	DIF, 
	EQ,
	GE, 
	GT, 
	ASTER,
	PLUS,
	MINUS,
	DIV,
	POWER,
	OPEN_PARENTHESIS,
	CLOSE_PARENTHESIS,
	OPEN_INDEX,
	CLOSE_INDEX,
	POINT, 
	COMMA,
	MAX("max"),
	MIN("min"),
	IF("if"),
	AND("and"),
	OR("or"),
	NOT("not"),
	LOOKUP("lookup"),
	NORMAL("normal"),
	UNIFORM("uniform"),
	ROUND("round"),
	LOGN("ln"),
	EXPN("exp"),
	SMOOTH("smooth"),
	DELAY3("delay3"),
	DT("dt"),
	TIME("time"),
	GRUPO_SOMA("groupsum"),
	GRUPO_MAX("groupmax"),
	GRUPO_MIN("groupmin"),
	BETAPERT("betapert"),
	COUNT("count"),
	BOUND("bound"),
	SELECT("select");
	
	private @Getter String nome;

	private TipoToken(String nome)
	{
		this.nome = nome;
	}

	private TipoToken()
	{
		this("");
	}
}