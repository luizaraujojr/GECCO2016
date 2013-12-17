/*
 * "Start Symbol" da linguagem
 */

%start expr


/*
 * Definicao da estrutura YYSTYPE
 */

%union
{
	char 		id[MAXID + 1];
	double		valor;
	EXPRESSAO	*expressao;
	void		*papel;
}


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
%token NORMAL
%token UNIFORM
%token BOUNDED
%token ROUND
%token LN
%token EXP
%token SMOOTH
%token DELAY3
%token DT
%token TIME
%token SPEC
%token L_CHAVE
%token R_CHAVE
%token POINT
%token GROUPSUM
%token GROUPMAX
%token GROUPMIN
%token BETAPERT
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
		$$.expressao = constroi_no_constante ($1.valor);
	}
	| ID
	{
		$$.expressao = constroi_no_variavel ($1.id);
	}
	| expr PLUS expr
	{
		$$.expressao = constroi_no (OP_SOMA, $1.expressao, $3.expressao);
	}
	| expr MINUS expr
	{
		$$.expressao = constroi_no (OP_SUBTRACAO, $1.expressao, $3.expressao);
	}
	| expr ASTER expr
	{
		$$.expressao = constroi_no (OP_PRODUTO, $1.expressao, $3.expressao);
	}
	| expr DIV expr
	{
		$$.expressao = constroi_no (OP_DIVISAO, $1.expressao, $3.expressao);
	}
	| MINUS expr	%prec UMINUS
	{
		$$.expressao = constroi_no (OP_UMINUS, $2.expressao, NULL);
	}
	| L_PARENT expr R_PARENT
	{
		$$.expressao = constroi_no (OP_PARENTESES, $2.expressao, NULL);
	}
	| expr EQUAL expr
	{
		$$.expressao = constroi_no (OP_IGUAL, $1.expressao, $3.expressao);
	}
	| expr DIF expr
	{
		$$.expressao = constroi_no (OP_DIFERENTE, $1.expressao, $3.expressao);
	}
	| expr GT expr
	{
		$$.expressao = constroi_no (OP_MAIOR, $1.expressao, $3.expressao);
	}
	| expr GE expr
	{
		$$.expressao = constroi_no (OP_MAIORIG, $1.expressao, $3.expressao);
	}
	| expr LT expr
	{
		$$.expressao = constroi_no (OP_MENOR, $1.expressao, $3.expressao);
	}
	| expr LE expr
	{
		$$.expressao = constroi_no (OP_MENORIG, $1.expressao, $3.expressao);
	}
	| NOT L_PARENT expr R_PARENT
	{
		$$.expressao = constroi_no (OP_NOT, $3.expressao, NULL);
	}
	| AND L_PARENT params R_PARENT
	{
		$$.expressao = constroi_no (OP_AND, $3.expressao, NULL);
	}
	| OR L_PARENT params R_PARENT
	{
		$$.expressao = constroi_no (OP_OR, $3.expressao, NULL);
	}
	| IF L_PARENT expr COMMA expr COMMA expr R_PARENT
	{
		$$.expressao = constroi_no (OP_COMMA, $5.expressao, $7.expressao);
		$$.expressao = constroi_no (OP_COMMA, $3.expressao, $$.expressao);
		$$.expressao = constroi_no (OP_IF, $$.expressao, NULL);
	}
	| MAX L_PARENT params R_PARENT
	{
		$$.expressao = constroi_no (OP_MAX, $3.expressao, NULL);
	}
	| MIN L_PARENT params R_PARENT
	{
		$$.expressao = constroi_no (OP_MIN, $3.expressao, NULL);
	}
	| ROUND L_PARENT expr R_PARENT
	{
		$$.expressao = constroi_no (OP_ROUND, $3.expressao, NULL);
	}
	| BETAPERT L_PARENT expr COMMA expr COMMA expr R_PARENT
	{
		$$.expressao = constroi_no (OP_COMMA, $5.expressao, $7.expressao);
		$$.expressao = constroi_no (OP_BETAPERT, $3.expressao, $$.expressao);
	}
	| NORMAL L_PARENT expr COMMA expr R_PARENT
	{
		$$.expressao = constroi_no (OP_NORMAL, $3.expressao, $5.expressao);
	}
	| UNIFORM L_PARENT expr COMMA expr R_PARENT
	{
		$$.expressao = constroi_no (OP_UNIFORM, $3.expressao, $5.expressao);
	}
	| LOOKUP L_PARENT ID COMMA expr COMMA expr COMMA expr R_PARENT
	{
		EXPRESSAO *e;
		e = constroi_no_variavel ($3.id);
		$$.expressao = constroi_no (OP_COMMA, $7.expressao, $9.expressao);
		$$.expressao = constroi_no (OP_COMMA, $5.expressao, $$.expressao);
		$$.expressao = constroi_no (OP_COMMA, e, $$.expressao);
		$$.expressao = constroi_no (OP_LOOKUP, $$.expressao, NULL);
	}
	| expr POTENC expr
	{
		$$.expressao = constroi_no (OP_POTENC, $1.expressao, $3.expressao);
	}
	| LN L_PARENT expr R_PARENT
	{
		$$.expressao = constroi_no (OP_LOGN, $3.expressao, NULL);
	}
	| EXP L_PARENT expr R_PARENT
	{
		$$.expressao = constroi_no (OP_EXPN, $3.expressao, NULL);
	}
	| SMOOTH L_PARENT expr COMMA expr R_PARENT
	{
		$$.expressao = constroi_no_complemento (OP_SMOOTH, $3.expressao, $5.expressao);
	}
	| DELAY3 L_PARENT expr COMMA expr R_PARENT
	{
		$$.expressao = constroi_no_complemento (OP_DELAY3, $3.expressao, $5.expressao);
	}
	| DT
	{
		$$.expressao = constroi_no (OP_DT, NULL, NULL);
	}
	| TIME
	{
		$$.expressao = constroi_no (OP_TIME, NULL, NULL);
	}
	| COUNT L_PARENT objset R_PARENT
	{
		$$.expressao = constroi_no (OP_COUNT, $3.expressao, NULL);
	}
	| GROUPSUM L_PARENT objset COMMA ID R_PARENT
	{
		EXPRESSAO *e1 = constroi_no_variavel ($5.id);
		$$.expressao = constroi_no (OP_GRUPO_SOMA, $3.expressao, e1);
	}
	| GROUPMAX L_PARENT objset COMMA ID R_PARENT
	{
		EXPRESSAO *e1 = constroi_no_variavel ($5.id);
		$$.expressao = constroi_no (OP_GRUPO_MAX, $3.expressao, e1);
	}
	| GROUPMIN L_PARENT objset COMMA ID R_PARENT
	{
		EXPRESSAO *e1;
		e1 = constroi_no_variavel ($5.id);
		$$.expressao = constroi_no (OP_GRUPO_MIN, $3.expressao, e1);
	}
	| objset POINT ID
	{
		EXPRESSAO *e1 = constroi_no_variavel ($1.id);
		EXPRESSAO *e2 = constroi_no_variavel ($3.id);
		$$.expressao = constroi_no (OP_PONTO, e1, e2);
	}
	;

params	: params COMMA expr
	{
		$$.expressao = constroi_no (OP_COMMA, $1.expressao, $3.expressao);
	}
	| expr
	{
		$$.expressao = $1.expressao;
	}
	;

objset	: ID
	{
		$$.expressao = constroi_no_variavel ($1.id);
	}
	| objset POINT ID
	{
		EXPRESSAO *e1 = constroi_no_variavel ($3.id);
		$$.expressao = constroi_no (OP_PONTO, $1.expressao, e1);
	}
	| L_COLCH ID R_COLCH
	{
		EXPRESSAO *e1 = constroi_no_variavel ($2.id);
		$$.expressao = constroi_no (OP_CLSSET, e1, NULL);
	}
	| BOUND L_PARENT objset COMMA ID R_PARENT
	{
		EXPRESSAO *e1 = constroi_no_variavel ($5.id);
		$$.expressao = constroi_no (OP_BOUND, $3.expressao, e1);
	}
	| SELECT L_PARENT objset COMMA expr R_PARENT
	{
		$$.expressao = constroi_no (OP_SELECT, $3.expressao, $5.expressao);
	}
	;

%%