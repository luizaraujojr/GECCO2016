%{
package br.unirio.sd.control.parser;

import br.unirio.sd.control.lex.Lexico;
%}

/*
 * "Start Symbol" da linguagem
 */
%start expr

/*
 * Tokens retornados pelo analisador lexico
 */
%token MIN
%token MAX
%token IF
%token AND
%token OR
%token NOT
%token ID
%token CONST
%token LOOKUP
%token L_PARENT
%token R_PARENT
%token ASTER
%token PLUS
%token COMMA
%token MINUS
%token DIV
%token POTENC
%token LE
%token LT
%token DIF
%token GT
%token GE
%token EQUAL
%token SEMICOLON
%token BOUNDED
%token ROUND
%token LN
%token EXP
%token DT
%token TIME
%token POINT
%token GROUPSUM
%token GROUPMAX
%token GROUPMIN
%token L_COLCH
%token R_COLCH
%token COUNT
%token BOUND
%token SELECT

/*
 * Informacoes de precedencia
 */
%left	EQUAL DIF GT GE LT LE
%left	PLUS MINUS
%left	ASTER DIV
%left	POTENC
%left	UMINUS   	/* menos unario */

%%
expr	: CONST
		{
			$$.setExpressao(new Expressao($1.valor));
		}
		| ID
		{
			$$.setExpressao(new Expressao($1.id));
		}
		| expr PLUS expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_SOMA, $1.getExpressao(), $3.getExpressao()));
		}
		| expr MINUS expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_SUBTRACAO, $1.getExpressao(), $3.getExpressao()));
		}
		| expr ASTER expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_PRODUTO, $1.getExpressao(), $3.getExpressao()));
		}
		| expr DIV expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_DIVISAO, $1.getExpressao(), $3.getExpressao()));
		}
		| MINUS expr	%prec UMINUS
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_UMINUS, $2.getExpressao(), null));
		}
		| L_PARENT expr R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_PARENTESES, $2.getExpressao(), null));
		}
		| expr EQUAL expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_IGUAL, $1.getExpressao(), $3.getExpressao()));
		}
		| expr DIF expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_DIFERENTE, $1.getExpressao(), $3.getExpressao()));
		}
		| expr GT expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_MAIOR, $1.getExpressao(), $3.getExpressao()));
		}
		| expr GE expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_MAIORIG, $1.getExpressao(), $3.getExpressao()));
		}
		| expr LT expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_MENOR, $1.getExpressao(), $3.getExpressao()));
		}
		| expr LE expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_MENORIG, $1.getExpressao(), $3.getExpressao()));
		}
		| NOT L_PARENT expr R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_NOT, $3.getExpressao(), null));
		}
		| AND L_PARENT params R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_AND, $3.getExpressao(), null));
		}
		| OR L_PARENT params R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_OR, $3.getExpressao(), null));
		}
		| IF L_PARENT expr COMMA expr COMMA expr R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_COMMA, $5.getExpressao(), $7.getExpressao()));
			$$.setExpressao(new Expressao(TipoOperacao.OP_COMMA, $3.getExpressao(), $$.getExpressao()));
			$$.setExpressao(new Expressao(TipoOperacao.OP_IF, $$.getExpressao(), null));
		}
		| MAX L_PARENT params R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_MAX, $3.getExpressao(), null));
		}
		| MIN L_PARENT params R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_MIN, $3.getExpressao(), null));
		}
		| ROUND L_PARENT expr R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_ROUND, $3.getExpressao(), null));
		}
		| LOOKUP L_PARENT ID COMMA expr COMMA expr COMMA expr R_PARENT
		{
			Expressao e = new Expressao($3.id);
			$$.setExpressao(new Expressao(TipoOperacao.OP_COMMA, $7.getExpressao(), $9.getExpressao()));
			$$.setExpressao(new Expressao(TipoOperacao.OP_COMMA, $5.getExpressao(), $$.getExpressao()));
			$$.setExpressao(new Expressao(TipoOperacao.OP_COMMA, e, $$.getExpressao()));
			$$.setExpressao(new Expressao(TipoOperacao.OP_LOOKUP, $$.getExpressao(), null));
		}
		| expr POTENC expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_POTENC, $1.getExpressao(), $3.getExpressao()));
		}
		| LN L_PARENT expr R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_LOGN, $3.getExpressao(), null));
		}
		| EXP L_PARENT expr R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_EXPN, $3.getExpressao(), null));
		}
		| DT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_DT, null, null));
		}
		| TIME
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_TIME, null, null));
		}
		| COUNT L_PARENT objset R_PARENT
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_COUNT, $3.getExpressao(), null));
		}
		| GROUPSUM L_PARENT objset COMMA ID R_PARENT
		{
			Expressao e1 = new Expressao($5.id);
			$$.setExpressao(new Expressao(TipoOperacao.OP_GRUPO_SOMA, $3.getExpressao(), e1));
		}
		| GROUPMAX L_PARENT objset COMMA ID R_PARENT
		{
			Expressao e1 = new Expressao($5.id);
			$$.setExpressao(new Expressao(TipoOperacao.OP_GRUPO_MAX, $3.getExpressao(), e1));
		}
		| GROUPMIN L_PARENT objset COMMA ID R_PARENT
		{
			Expressao e1 = new Expressao($5.id);
			$$.setExpressao(new Expressao(TipoOperacao.OP_GRUPO_MIN, $3.getExpressao(), e1));
		}
		| objset POINT ID
		{
			Expressao e1 = new Expressao($1.id);
			Expressao e2 = new Expressao($3.id);
			$$.setExpressao(new Expressao(TipoOperacao.OP_PONTO, e1, e2));
		}
		;

params	: params COMMA expr
		{
			$$.setExpressao(new Expressao(TipoOperacao.OP_COMMA, $1.getExpressao(), $3.getExpressao()));
		}
		| expr
		{
			$$.setExpressao($1.getExpressao());
		}
		;

objset	: ID
		{
			$$.setExpressao(new Expressao($1.id);
		}
		| objset POINT ID
		{
			Expressao e1 = new Expressao($3.id);
			$$.setExpressao(new Expressao(OP_PONTO, $1.getExpressao(), e1));
		}
		| L_COLCH ID R_COLCH
		{
			Expressao e1 = new Expressao($2.id);
			$$.setExpressao(new Expressao(OP_CLSSET, e1, null));
		}
		| BOUND L_PARENT objset COMMA ID R_PARENT
		{
			Expressao e1 = new Expressao($5.id);
			$$.setExpressao(new Expressao(OP_BOUND, $3.getExpressao(), e1));
		}
		| SELECT L_PARENT objset COMMA expr R_PARENT
		{
			$$.setExpressao(new Expressao(OP_SELECT, $3.getExpressao(), $5.getExpressao()));
		}
		;
%%

private Lexico lexico;

public Parser(String s)
{
	this.lexico = new Lexico(s);
}

private int yylex()
{
	return 0;
}

private void yyerror(String message) throws ParserException
{
	throw new ParserException(message);
}
