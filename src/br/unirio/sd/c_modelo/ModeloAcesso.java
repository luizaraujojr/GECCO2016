
/*
 ************************************************************************
 *									*
 * Conta o número de nós de variável em uma árvore			*
 *									*
 ************************************************************************
 */

static int conta_nos_variavel_arvore (EXPRESSAO *no)
{
	if (no == NULL)
		return (0);

	if (no -> operacao == OP_VARIAVEL) 
		return (1);

	return (conta_nos_variavel_arvore (no -> dir) + conta_nos_variavel_arvore (no -> esq));
}


/*
 ************************************************************************
 *									*
 * Conta o número de participantes de uma relação em um modelo		*
 *									*
 ************************************************************************
 */

int conta_participantes_relacao_modelo_nome (RELMODELO *relacao)
{
	return (conta_nos_variavel_arvore (relacao -> elementos));
}


/*
 ************************************************************************
 *									*
 * Retorna o n-ésimo nó variável de uma árvore				*
 *									*
 ************************************************************************
 */

static EXPRESSAO *pega_no_variavel_arvore (EXPRESSAO *no, int indice)
{
	if (no == NULL)
		return (NULL);

	if (indice == 0)
		return ((no -> operacao == OP_VARIAVEL) ? no : no -> dir);

	return (pega_no_variavel_arvore (no -> esq, indice-1));
}


/*
 ************************************************************************
 *									*
 * Retorna a expressão do n-ésimo participante de uma relação		*
 *									*
 ************************************************************************
 */

EXPRESSAO *pega_participante_relacao_modelo_nome (RELMODELO *relacao, int indice)
{
	return (pega_no_variavel_arvore (relacao -> elementos, indice));
}


/*
 ************************************************************************
 *									*
 * Pega uma propriedade com um nome em um objeto			*
 *									*
 ************************************************************************
 */

PROPMODELO *pega_propriedade_modelo_nome (OBJETO *objeto, char *id)
{
	PROPMODELO	*propriedade;
	int		i;


	for (i = 0; i < pega_numero_itens (&objeto -> propmodelo); i++)
	{
		propriedade = pega_item (&objeto -> propmodelo, i);

		if (stricmp (propriedade -> id, id) == 0)
			return (propriedade);
	}

	return (NULL);
}


/*
 ************************************************************************
 *									*
 * Pega um comportamento com um nome em um objeto			*
 *									*
 ************************************************************************
 */

COMPORTAMENTO *pega_comportamento_compilacao_nome (OBJETO *objeto, char *id)
{
	COMPORTAMENTO	*comp;
	int		i;


	for (i = 0; i < pega_numero_itens (&objeto -> comportamentos); i++)
	{
		comp = pega_item (&objeto -> comportamentos, i);

		if (stricmp (comp -> id, id) == 0)
			return (comp);
	}

	return (NULL);
}


/*
 ************************************************************************
 *									*
 * Pega uma propriedade com um nome em um objeto			*
 *									*
 ************************************************************************
 */

PROPRIEDADE *pega_propriedade_compilacao_nome (OBJETO *objeto, char *id)
{
	PROPRIEDADE	*prop;
	int		i;


	for (i = 0; i < pega_numero_itens (&objeto -> propriedades); i++)
	{
		prop = pega_item (&objeto -> propriedades, i);

		if (stricmp (prop -> id, id) == 0)
			return (prop);
	}

	return (NULL);
}
