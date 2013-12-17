
/*
 * Determina se uma expressão é constante
 */

#define ECONSTANTE(no)		(no -> operacao == OP_CONSTANTE)


/*
 ************************************************************************
 *									*
 * Transforma uma expressão em constante				*
 *									*
 ************************************************************************
 */

static int transforma_constante (EXPRESSAO *no, double valor)
{
	no -> operacao = OP_CONSTANTE;
	no -> valor = valor;
	no -> dados = NULL;
	
	if (no -> nome != NULL)
	{
		free (no -> nome);
		no -> nome = NULL;
	}
	
	libera_expressao (no -> esq);
	libera_expressao (no -> dir);
	no -> esq = no -> dir = NULL;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Substitui um nó por outro na árvore de expressões			*
 *									*
 ************************************************************************
 */

static int substitui_no (EXPRESSAO *base, EXPRESSAO *alvo)
{
	base -> operacao = alvo -> operacao;
	base -> valor = alvo -> valor;
	base -> dados = alvo -> dados;
	base -> esq = alvo -> esq;
	base -> dir = alvo -> dir;

	if (base -> nome != NULL)
	{
		free (base -> nome);
		base -> nome = NULL;
	}

	if (alvo -> nome != NULL)
		base -> nome = strdup (alvo -> nome);

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de parênteses					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_parenteses (EXPRESSAO *no)
{
	int	op = no -> esq -> operacao;

	if (ECONSTANTE(no -> esq))
		return (transforma_constante (no, no -> esq -> valor));

	if (op == OP_VARIAVEL  ||  op == OP_PARENTESES  ||  op == OP_MAX  ||  op == OP_MIN  ||  op == OP_IF  ||  op == OP_AND  ||  op == OP_OR  ||  op == OP_NOT  ||  op == OP_LOOKUP  ||  op == OP_ROUND  ||  op == OP_EXPN  ||  op == OP_LOGN  ||  op == OP_SMOOTH  ||  op == OP_DELAY3  ||  op == OP_DT  ||  op == OP_TIME  ||  op == OP_NORMAL  ||  op == OP_UNIFORM  ||  op == OP_BETAPERT)
		return (substitui_no (no, no -> esq));

//	if (op == OP_PARENTESES  ||  op == OP_MAX  ||  op == OP_MIN  ||  op == OP_IF  ||  op == OP_AND  ||  op == OP_OR  ||  op == OP_NOT  ||  op == OP_LOOKUP  ||  op == OP_ROUND  ||  op == OP_EXPN  ||  op == OP_LOGN  ||  op == OP_SMOOTH  ||  op == OP_DELAY3  ||  op == OP_DT  ||  op == OP_TIME  ||  op == OP_NORMAL  ||  op == OP_UNIFORM  ||  op == OP_BETAPERT)
//		return (substitui_no (no, no -> esq));

	return (FALSE);
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de menos unário					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_menos_unario (EXPRESSAO *no)
{
	if (ECONSTANTE(no -> esq))
		return (transforma_constante (no, -no -> esq -> valor));

	if (no -> esq -> operacao == OP_UMINUS)
		return (substitui_no (no, no -> esq -> esq));

	return (FALSE);
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de soma						*
 *									*
 ************************************************************************
 */

static int reduz_expressao_soma (EXPRESSAO *no)
{
	if (ECONSTANTE(no -> esq)  &&  ECONSTANTE(no -> dir))
		return (transforma_constante (no, no -> esq -> valor + no -> dir -> valor));

	if (ECONSTANTE(no -> esq))
		if (no -> esq -> valor == 0.0)
			return (substitui_no (no, no -> dir));

	if (ECONSTANTE(no -> dir))
		if (no -> dir -> valor == 0.0)
			return (substitui_no (no, no -> esq));

	return (FALSE);

}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de subtração					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_subtracao (EXPRESSAO *no)
{
	if (ECONSTANTE(no -> esq)  &&  ECONSTANTE(no -> dir))
		return (transforma_constante (no, no -> esq -> valor - no -> dir -> valor));

	if (ECONSTANTE(no -> dir))
		if (no -> dir -> valor == 0.0)
			return (substitui_no (no, no -> esq));

	return (FALSE);
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de produto					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_produto (EXPRESSAO *no)
{
	if (ECONSTANTE(no -> esq)  &&  ECONSTANTE(no -> dir))
		return (transforma_constante (no, no -> esq -> valor * no -> dir -> valor));

	if (ECONSTANTE(no -> esq))
	{
		if (no -> esq -> valor == 0.0)
			return (transforma_constante (no, 0.0));

		if (no -> esq -> valor == 1.0)
			return (substitui_no (no, no -> dir));
	}

	if (ECONSTANTE(no -> dir))
	{
		if (no -> dir -> valor == 0.0)
			return (transforma_constante (no, 0.0));

		if (no -> dir -> valor == 1.0)
			return (substitui_no (no, no -> esq));
	}

	return (FALSE);
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de divisão					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_divisao (EXPRESSAO *no)
{
	if (ECONSTANTE(no -> esq)  &&  ECONSTANTE(no -> dir))
	{
		if (no -> dir -> valor == 0.0)
			return (FALSE);

		return (transforma_constante (no, no -> esq -> valor / no -> dir -> valor));
	}

	if (ECONSTANTE(no -> esq))
		if (no -> esq -> valor == 0.0)
			return (transforma_constante (no, 0.0));

	if (ECONSTANTE(no -> dir))
		if (no -> dir -> valor == 1.0)
			return (substitui_no (no, no -> esq));

	return (FALSE);
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de potência					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_potencia (EXPRESSAO *no)
{
	if (ECONSTANTE(no -> esq)  &&  ECONSTANTE(no -> dir))
	{
		if (no -> esq -> valor < 0.0  &&  no -> dir -> valor < 1.0)
			return (FALSE);

		return (transforma_constante (no, pow (no -> esq -> valor, no -> dir -> valor)));
	}

	if (ECONSTANTE(no -> esq))
		if (no -> esq -> valor == 1.0)
			return (transforma_constante (no, 1.0));

	if (ECONSTANTE(no -> dir))
	{
		if (no -> dir -> valor == 0.0)
			return (transforma_constante (no, 1.0));

		if (no -> dir -> valor == 1.0)
			return (substitui_no (no, no -> esq));
	}


	return (FALSE);
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de igual						*
 *									*
 ************************************************************************
 */

static int reduz_expressao_igual (EXPRESSAO *no)
{
	double		valor1, valor2, result;


	if (!ECONSTANTE(no -> esq)  ||  !ECONSTANTE(no -> dir))
		return (FALSE);

	valor1 = no -> esq -> valor;
	valor2 = no -> dir -> valor;

	result = (valor1 == valor2) ? 1.0 : -1.0;
	return (transforma_constante (no, result));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de diferente					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_diferente (EXPRESSAO *no)
{
	double		valor1, valor2, result;


	if (!ECONSTANTE(no -> esq)  ||  !ECONSTANTE(no -> dir))
		return (FALSE);

	valor1 = no -> esq -> valor;
	valor2 = no -> dir -> valor;

	result = (valor1 != valor2) ? 1.0 : -1.0;
	return (transforma_constante (no, result));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de maior						*
 *									*
 ************************************************************************
 */

static int reduz_expressao_maior (EXPRESSAO *no)
{
	double		valor1, valor2, result;


	if (!ECONSTANTE(no -> esq)  ||  !ECONSTANTE(no -> dir))
		return (FALSE);

	valor1 = no -> esq -> valor;
	valor2 = no -> dir -> valor;

	result = (valor1 > valor2) ? 1.0 : -1.0;
	return (transforma_constante (no, result));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de maior ou igual				*
 *									*
 ************************************************************************
 */

static int reduz_expressao_maior_igual (EXPRESSAO *no)
{
	double		valor1, valor2, result;


	if (!ECONSTANTE(no -> esq)  ||  !ECONSTANTE(no -> dir))
		return (FALSE);

	valor1 = no -> esq -> valor;
	valor2 = no -> dir -> valor;

	result = (valor1 >= valor2) ? 1.0 : -1.0;
	return (transforma_constante (no, result));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de menor						*
 *									*
 ************************************************************************
 */

static int reduz_expressao_menor (EXPRESSAO *no)
{
	double		valor1, valor2, result;


	if (!ECONSTANTE(no -> esq)  ||  !ECONSTANTE(no -> dir))
		return (FALSE);

	valor1 = no -> esq -> valor;
	valor2 = no -> dir -> valor;

	result = (valor1 < valor2) ? 1.0 : -1.0;
	return (transforma_constante (no, result));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de menor ou igual				*
 *									*
 ************************************************************************
 */

static int reduz_expressao_menor_igual (EXPRESSAO *no)
{
	double		valor1, valor2, result;


	if (!ECONSTANTE(no -> esq)  ||  !ECONSTANTE(no -> dir))
		return (FALSE);

	valor1 = no -> esq -> valor;
	valor2 = no -> dir -> valor;

	result = (valor1 <= valor2) ? 1.0 : -1.0;
	return (transforma_constante (no, result));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de arredondamento				*
 *									*
 ************************************************************************
 */

static int reduz_expressao_arredondamento (EXPRESSAO *no)
{
	double		valor;


	if (!ECONSTANTE(no -> esq))
		return (FALSE);

	valor = no -> esq -> valor;
	return (transforma_constante (no, (int)(valor + 0.5)));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de logaritmo					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_logaritmo (EXPRESSAO *no)
{
	double		valor;


	if (!ECONSTANTE(no -> esq))
		return (FALSE);

	valor = no -> esq -> valor;

	if (valor <= 0.0)
		return (FALSE);

	return (transforma_constante (no, log(valor)));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de exponenciação					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_exponenciacao (EXPRESSAO *no)
{
	double		valor;


	if (!ECONSTANTE(no -> esq))
		return (FALSE);

	valor = no -> esq -> valor;
	return (transforma_constante (no, exp(valor)));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de negação					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_negacao (EXPRESSAO *no)
{
	double		valor, result;


	if (!ECONSTANTE(no -> esq))
		return (FALSE);

	valor = no -> esq -> valor;
	result = (valor >= 0) ? -1 : 1;
	return (transforma_constante (no, result));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de condição					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_condicao (EXPRESSAO *no)
{
	double		valor1, valor2, valor3, result;
	EXPRESSAO	*parametros;


	parametros = no -> esq;

	if (!ECONSTANTE(parametros -> esq)  ||  !ECONSTANTE(parametros -> dir -> esq)  ||  !ECONSTANTE(parametros -> dir -> dir))
		return (FALSE);

	valor1 = parametros -> esq -> valor;
	valor2 = parametros -> dir -> esq -> valor;
	valor3 = parametros -> dir -> dir -> valor;

	result = (valor1 >= 0.0) ? valor2 : valor3;
	return (transforma_constante (no, result));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de máximo					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_maximo (EXPRESSAO *no)
{
	double		max = -9999999.0;
	EXPRESSAO	*base = no;


	for (no = no -> esq; no -> operacao == OP_COMMA; no = no -> esq)
	{
		if (!ECONSTANTE (no -> dir))
			return (FALSE);

		if (no -> dir -> valor > max)
			max = no -> dir -> valor;
	}

	if (!ECONSTANTE(no))
		return (FALSE);

	if (no -> valor > max)
		max = no -> valor;

	return (transforma_constante (base, max));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de mínimo					*
 *									*
 ************************************************************************
 */

static int reduz_expressao_minimo (EXPRESSAO *no)
{
	double		min = +9999999.0;
	EXPRESSAO	*base = no;


	for (no = no -> esq; no -> operacao == OP_COMMA; no = no -> esq)
	{
		if (!ECONSTANTE (no -> dir))
			return (FALSE);

		if (no -> dir -> valor < min)
			min = no -> dir -> valor;
	}

	if (!ECONSTANTE(no))
		return (FALSE);

	if (no -> valor < min)
		min = no -> valor;

	return (transforma_constante (base, min));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de AND						*
 *									*
 ************************************************************************
 */

static int reduz_expressao_elogico (EXPRESSAO *no)
{
	double		result = TRUE;
	EXPRESSAO	*base = no;


	for (no = no -> esq; no -> operacao == OP_COMMA; no = no -> esq)
	{
		if (!ECONSTANTE (no -> dir))
			return (FALSE);

		result = result  &&  (no -> dir -> valor >= 0);
	}

	if (!ECONSTANTE(no))
		return (FALSE);

	result = result  &&  (no -> valor >= 0);
	return (transforma_constante (base, result ? 1.0 : -1.0));
}


/*
 ************************************************************************
 *									*
 * Reduz uma expressão de OR						*
 *									*
 ************************************************************************
 */

static int reduz_expressao_oulogico (EXPRESSAO *no)
{
	double		result = FALSE;
	EXPRESSAO	*base = no;


	for (no = no -> esq; no -> operacao == OP_COMMA; no = no -> esq)
	{
		if (!ECONSTANTE (no -> dir))
			return (FALSE);

		result = result  ||  (no -> dir -> valor >= 0);
	}

	if (!ECONSTANTE(no))
		return (FALSE);

	result = result  ||  (no -> valor >= 0);
	return (transforma_constante (base, result ? 1.0 : -1.0));
}


/*
 ************************************************************************
 *									*
 * Reduz as subexpressões constantes de uma expressão			*
 *									*
 ************************************************************************
 */

static int otimiza_expressao (EXPRESSAO *no)
{
	int	otimizou = FALSE;


	if (no -> esq != NULL)
		if (otimiza_expressao (no -> esq))
			otimizou = TRUE;

	if (no -> dir != NULL)
		if (otimiza_expressao (no -> dir))
			otimizou = TRUE;

	switch (no -> operacao)
	{
	    case OP_VARIAVEL:
	    case OP_CONSTANTE:
	    case OP_NORMAL:
	    case OP_UNIFORM:
	    case OP_BETAPERT:
	    case OP_COMMA:
	    case OP_DT:
	    case OP_TIME:
		break;			// Nada para otimizar !

	    case OP_PARENTESES:
		if (reduz_expressao_parenteses (no)) otimizou = TRUE;
		break;

	    case OP_UMINUS:
		if (reduz_expressao_menos_unario (no)) otimizou = TRUE;
		break;

	    case OP_SOMA:
		if (reduz_expressao_soma (no)) otimizou = TRUE;
		break;

	    case OP_SUBTRACAO:
		if (reduz_expressao_subtracao (no)) otimizou = TRUE;
		break;

	    case OP_PRODUTO:
		if (reduz_expressao_produto (no)) otimizou = TRUE;
		break;

	    case OP_DIVISAO:
		if (reduz_expressao_divisao (no)) otimizou = TRUE;
		break;

	    case OP_POTENC:
		if (reduz_expressao_potencia (no)) otimizou = TRUE;
		break;

	    case OP_IGUAL:
		if (reduz_expressao_igual (no)) otimizou = TRUE;
		break;

	    case OP_DIFERENTE:
		if (reduz_expressao_diferente (no)) otimizou = TRUE;
		break;

	    case OP_MAIOR:
		if (reduz_expressao_maior (no)) otimizou = TRUE;
		break;

	    case OP_MAIORIG:
		if (reduz_expressao_maior_igual (no)) otimizou = TRUE;
		break;

	    case OP_MENOR:
		if (reduz_expressao_menor (no)) otimizou = TRUE;
		break;

	    case OP_MENORIG:
		if (reduz_expressao_menor_igual (no)) otimizou = TRUE;
		break;

	    case OP_ROUND:
		if (reduz_expressao_arredondamento (no)) otimizou = TRUE;
		break;

	    case OP_EXPN:
		if (reduz_expressao_exponenciacao (no)) otimizou = TRUE;
		break;

	    case OP_LOGN:
		if (reduz_expressao_logaritmo (no)) otimizou = TRUE;
		break;

	    case OP_NOT:
		if (reduz_expressao_negacao (no)) otimizou = TRUE;
		break;

	    case OP_IF:
		if (reduz_expressao_condicao (no)) otimizou = TRUE;
		break;

	    case OP_MAX:
		if (reduz_expressao_maximo (no)) otimizou = TRUE;
		break;

	    case OP_MIN:
		if (reduz_expressao_minimo (no)) otimizou = TRUE;
		break;

	    case OP_AND:
		if (reduz_expressao_elogico (no)) otimizou = TRUE;
		break;

	    case OP_OR:
		if (reduz_expressao_oulogico (no)) otimizou = TRUE;
		break;

	    case OP_LOOKUP:
		break;			// Podemos otimizar, mas não hoje !
	}

	return (otimizou);
}


/*
 ************************************************************************
 *									*
 * Otimiza as expressões constantes nas equações			*
 *									*
 ************************************************************************
 */

int otimiza_expressoes_constantes (void)
{
	EQUACAO		*equacao;
	int		i, contador;


	contador = 0;

	for (i = pega_numero_equacoes()-1; i >= 0; i--)
	{
		equacao = pega_equacao_indice (i);
		
		if (equacao -> expressao != NULL)
			if (otimiza_expressao (equacao -> expressao))
				contador++;
	}

	return (contador);
}