%{
package br.unirio.sd.control.parser;

import br.unirio.sd.model.common.Expressao;
import br.unirio.sd.model.common.TipoOperacao;
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
%token VALUES

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
			$$ = new ParserVal(new Expressao($1.dval));
		}
		| ID
		{
			$$ = new ParserVal(new Expressao($1.sval));
		}
		| expr PLUS expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.SOMA, $1.obj, $3.obj));
		}
		| expr MINUS expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.SUBTRACAO, $1.obj, $3.obj));
		}
		| expr ASTER expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.PRODUTO, $1.obj, $3.obj));
		}
		| expr DIV expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.DIVISAO, $1.obj, $3.obj));
		}
		| MINUS expr	%prec UMINUS
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.UMINUS, $2.obj, null));
		}
		| L_PARENT expr R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.PARENTESES, $2.obj, null));
		}
		| expr EQUAL expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.IGUAL, $1.obj, $3.obj));
		}
		| expr DIF expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.DIFERENTE, $1.obj, $3.obj));
		}
		| expr GT expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.MAIOR, $1.obj, $3.obj));
		}
		| expr GE expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.MAIORIG, $1.obj, $3.obj));
		}
		| expr LT expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.MENOR, $1.obj, $3.obj));
		}
		| expr LE expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.MENORIG, $1.obj, $3.obj));
		}
		| NOT L_PARENT expr R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.NOT, $3.obj, null));
		}
		| AND L_PARENT params R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.AND, $3.obj, null));
		}
		| OR L_PARENT params R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.OR, $3.obj, null));
		}
		| IF L_PARENT expr COMMA expr COMMA expr R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.COMMA, $5.obj, $7.obj));
			$$ = new ParserVal(new Expressao(TipoOperacao.IF, $3.obj, $$.obj));
		}
		| MAX L_PARENT params R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.MAX, $3.obj, null));
		}
		| MIN L_PARENT params R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.MIN, $3.obj, null));
		}
		| ROUND L_PARENT expr R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.ROUND, $3.obj, null));
		}
		| LOOKUP L_PARENT ID COMMA expr COMMA expr COMMA expr R_PARENT
		{
			Expressao e = new Expressao($3.sval);
			$$ = new ParserVal(new Expressao(TipoOperacao.COMMA, $7.obj, $9.obj));
			$$ = new ParserVal(new Expressao(TipoOperacao.COMMA, $5.obj, $$.obj));
			$$ = new ParserVal(new Expressao(TipoOperacao.COMMA, e, $$.obj));
			$$ = new ParserVal(new Expressao(TipoOperacao.LOOKUP, $$.obj, null));
		}
		| expr POTENC expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.POTENC, $1.obj, $3.obj));
		}
		| LN L_PARENT expr R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.LOGN, $3.obj, null));
		}
		| EXP L_PARENT expr R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.EXPN, $3.obj, null));
		}
		| DT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.DT, null, null));
		}
		| TIME
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.TIME, null, null));
		}
		| COUNT L_PARENT objset R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.COUNT, $3.obj, null));
		}
		| GROUPSUM L_PARENT objset COMMA ID R_PARENT
		{
			Expressao e1 = new Expressao($5.sval);
			$$ = new ParserVal(new Expressao(TipoOperacao.GRUPO_SOMA, $3.obj, e1));
		}
		| GROUPMAX L_PARENT objset COMMA ID R_PARENT
		{
			Expressao e1 = new Expressao($5.sval);
			$$ = new ParserVal(new Expressao(TipoOperacao.GRUPO_MAX, $3.obj, e1));
		}
		| GROUPMIN L_PARENT objset COMMA ID R_PARENT
		{
			Expressao e1 = new Expressao($5.sval);
			$$ = new ParserVal(new Expressao(TipoOperacao.GRUPO_MIN, $3.obj, e1));
		}
		| objset POINT ID
		{
			Expressao e1 = new Expressao($1.sval);
			Expressao e2 = new Expressao($3.sval);
			$$ = new ParserVal(new Expressao(TipoOperacao.PONTO, e1, e2));
		}
		| VALUES L_PARENT values R_PARENT
		{
			$$ = $3;
		}
		;
		
values	: CONST COMMA values 
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.COMMA, new Expressao($1.dval), $3.obj));
		}
		| CONST
		{
			$$ = new ParserVal(new Expressao($1.dval));
		}
		;
		
params	: params COMMA expr
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.COMMA, $1.obj, $3.obj));
		}
		| expr
		{
			$$ = new ParserVal($1.obj);
		}
		;

objset	: ID
		{
			$$ = new ParserVal(new Expressao($1.sval));
		}
		| objset POINT ID
		{
			Expressao e1 = new Expressao($3.sval);
			$$ = new ParserVal(new Expressao(TipoOperacao.PONTO, $1.obj, e1));
		}
		| L_COLCH ID R_COLCH
		{
			Expressao e1 = new Expressao($2.sval);
			$$ = new ParserVal(new Expressao(TipoOperacao.CLSSET, e1, null));
		}
		| BOUND L_PARENT objset COMMA ID R_PARENT
		{
			Expressao e1 = new Expressao($5.sval);
			$$ = new ParserVal(new Expressao(TipoOperacao.BOUND, $3.obj, e1));
		}
		| SELECT L_PARENT objset COMMA expr R_PARENT
		{
			$$ = new ParserVal(new Expressao(TipoOperacao.SELECT, $3.obj, $5.obj));
		}
		;
%%

private Lexico lexico;
private String equacao;

public Parser(String s)
{
	this.equacao = s;
	this.lexico = new Lexico(s);
}

public Expressao execute()
{
	int result = yyparse();
	return (result != 0) ? null : yyval.obj;
}

private int yylex()
{
	Token token = lexico.proximo();
	
	if (token == null)
		return 0;
	
	switch(token.getTipo())
	{
		case CONST:
			yylval = new ParserVal(token.getValor());
			return CONST;
			
		case ID:
			yylval = new ParserVal(token.getNome());
			return ID;
			
		default:
			return token.getTipo();
	}
}

private void yyerror(String message)
{
	ErrorManager.getInstance().add(message + "(" + equacao + ")");
}
