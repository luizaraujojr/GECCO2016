
/*
 ************************************************************************
 *									*
 * Constrói as relações inversas de uma relação bidirecional		*
 *									*
 ************************************************************************
 */

static void processa_relacao_objeto (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, CLASSE *classe, RELMODELO *relmodelo)
{
	EXPRESSAO	*walker;
	EXPRESSAO	*elemento;
	RELACAO		*relacao;
	OBJETO		*relac;
	RELMODELO	*reldestino;
	char		*nome_objeto;


	relacao = pega_relacionamento_nome_origem (metamodelo, relmodelo -> id);

	if (relacao == NULL)
		return;

	if (strlen(relacao -> papel_destino) == 0)
		return;

	for (walker = relmodelo -> elementos; walker != NULL; walker = walker -> esq)
	{
		nome_objeto = (walker -> operacao == OP_VARIAVEL) ? walker -> nome : walker -> dir -> nome;

		relac = pega_objeto_nome (modelo, nome_objeto);

		reldestino = pega_relacao_modelo_nome (relac, relacao -> papel_destino);

		if (reldestino == NULL)
		{
			reldestino = (RELMODELO *) calloc (1, sizeof(RELMODELO));
			strcpy (reldestino -> id, relacao -> papel_destino);
			reldestino -> elementos = constroi_no_variavel (objeto -> id);
			insere_item (&relac -> relmodelo, reldestino);
		}
		else
		{
			elemento = constroi_no_variavel (objeto -> id);
			reldestino -> elementos = constroi_no (OP_COMMA, elemento, reldestino -> elementos);
		}
	}
}


/*
 ************************************************************************
 *									*
 * Processa as relações de um objeto do modelo				*
 *									*
 ************************************************************************
 */

static void processa_relacoes_objeto (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto)
{
	CLASSE		*classe;
	RELMODELO	*relmodelo;
	int		i;


	/*
	 * Pega a classe do objeto no metamodelo
	 */

	classe = pega_classe_nome (metamodelo, objeto -> idclasse);


	/*
	 * Processa as relações do objeto do modelo
	 */

	for (i = 0; i < pega_numero_itens (&objeto -> relmodelo); i++)
	{
		relmodelo = pega_item (&objeto -> relmodelo, i);
		processa_relacao_objeto (metamodelo, modelo, objeto, classe, relmodelo);
	}
}


/*
 ************************************************************************
 *									*
 * Processa as relações dos objetos do modelo				*
 *									*
 ************************************************************************
 */

void processa_relacoes_modelo (METAMODELO *metamodelo, MODELO *modelo)
{
	OBJETO		*objeto;
	int		i;


	for (i = 0; i < pega_numero_itens (&modelo -> objetos); i++)
	{
		objeto = pega_item (&modelo -> objetos, i);
		processa_relacoes_objeto (metamodelo, modelo, objeto);
	}
}