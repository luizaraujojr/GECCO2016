

static VETOR_DINAMICO	equacoes;

/*
 ************************************************************************
 *									*
 * Adiciona uma equa��o							*
 *									*
 ************************************************************************
 */

int adiciona_equacao (int tipo, char *id, EXPRESSAO *repositorio, EXPRESSAO *expressao)
{
	EQUACAO		*equacao;


	if ((equacao = (EQUACAO *) calloc (1, sizeof (EQUACAO))) == NULL)
		return (yyerror ("Out of memory"));

	equacao -> tipo = tipo;
	strcpy (equacao -> id, id);
	equacao -> repositorio = repositorio;
	equacao -> expressao = expressao;
	equacao -> comentario = NULL;

	insere_item (&equacoes, equacao);
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Adiciona uma equa��o	representando um coment�rio			*
 *									*
 ************************************************************************
 */

int adiciona_comentario (char *msg)
{
	EQUACAO		*equacao;


	if ((equacao = (EQUACAO *) malloc (sizeof (EQUACAO))) == NULL)
		return (yyerror ("Out of memory"));

	equacao -> tipo = TP_COMMENT;
	equacao -> repositorio = NULL;
	equacao -> expressao = NULL;
	equacao -> comentario = strdup (msg);

	insere_item (&equacoes, equacao);
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Retorna o n�mero de equa��es						*
 *									*
 ************************************************************************
 */

int pega_numero_equacoes (void)
{
	return (pega_numero_itens (&equacoes));
}


/*
 ************************************************************************
 *									*
 * Retorna uma equa��o, dado seu �ndice no vetor			*
 *									*
 ************************************************************************
 */

EQUACAO *pega_equacao_indice (int indice)
{
	return (pega_item (&equacoes, indice));
}


/*
 ************************************************************************
 *									*
 * Remove uma equa��o, dado seu �ndice no vetor				*
 *									*
 ************************************************************************
 */

void remove_equacao_indice (int indice)
{
	EQUACAO		*equacao;

	equacao = pega_item (&equacoes, indice);
	libera_equacao (equacao);
	remove_item_posicao (&equacoes, indice);
}