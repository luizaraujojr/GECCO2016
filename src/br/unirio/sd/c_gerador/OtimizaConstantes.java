
/*
 ************************************************************************
 *									*
 * Substitui uma variável por um valor em uma expressão			*
 *									*
 ************************************************************************
 */

static void substitui_variavel_valor (EXPRESSAO *no, char *id, double valor)
{
	if (no == NULL)
		return;

	if (no -> operacao == OP_VARIAVEL)
	{
		if (stricmp (no -> nome, id) == 0)
		{
			no -> operacao = OP_CONSTANTE;
			no -> valor = valor;
			no -> dir = no -> esq = NULL;
			free (no -> nome);
			no -> nome = NULL;
			no -> dados = NULL;
		}
	}

	if (no -> esq != NULL)
		substitui_variavel_valor (no -> esq, id, valor);

	if (no -> dir != NULL)
		substitui_variavel_valor (no -> dir, id, valor);
}


/*
 ************************************************************************
 *									*
 * Substitui uma variável por outra variável em uma expressão		*
 *									*
 ************************************************************************
 */

static void substitui_variavel_variavel (EXPRESSAO *no, char *id, char *novo)
{
	if (no == NULL)
		return;

	if (no -> operacao == OP_VARIAVEL)
		if (stricmp (no -> nome, id) == 0)
		{
			free (no -> nome);
			no -> nome = strdup (novo);
			no -> valor = 0.0;
			no -> dir = no -> esq = NULL;
			no -> dados = NULL;
		}

	if (no -> esq != NULL)
		substitui_variavel_variavel (no -> esq, id, novo);

	if (no -> dir != NULL)
		substitui_variavel_variavel (no -> dir, id, novo);
}


/*
 ************************************************************************
 *									*
 * Imprime as características de uma equação				*
 *									*
 ************************************************************************
 */

static int otimiza_equacao_constante (EQUACAO *eqbase)
{
	EQUACAO		*equacao;
	int		i;


	/*
	 * Apenas processos podem ser constantes
	 */

	if (eqbase -> tipo != TP_PROC)
		return (FALSE);


	/*
	 * Se o processo for uma constante, substitui a constante nas expressões
	 */

	if (eqbase -> expressao -> operacao == OP_CONSTANTE)
	{
		for (i = 0; i < pega_numero_equacoes(); i++)
		{
			equacao = pega_equacao_indice (i);
			substitui_variavel_valor (equacao -> expressao, eqbase -> id, eqbase -> expressao -> valor);
		}

		return (TRUE);
	}


	/*
	 * Se o processo for uma variável, substitui a variável nas expressões
	 */

	if (eqbase -> expressao -> operacao == OP_VARIAVEL)
	{
		for (i = 0; i < pega_numero_equacoes(); i++)
		{
			equacao = pega_equacao_indice (i);
			substitui_variavel_variavel (equacao -> expressao, eqbase -> id, eqbase -> expressao -> nome);
		}

		return (TRUE);
	}

	return (FALSE);
}


/*
 ************************************************************************
 *									*
 * Remove do modelo as equações constantes				*
 *									*
 ************************************************************************
 */

int otimiza_equacoes_constantes (void)
{
	EQUACAO		*equacao;
	int		i, contador;


	contador = 0;

	for (i = pega_numero_equacoes()-1; i >= 0; i--)
	{
		equacao = pega_equacao_indice (i);
		
		if (otimiza_equacao_constante (equacao))
		{
			remove_equacao_indice (i);
			contador++;
		}
	}

	return (contador);
}