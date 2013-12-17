package br.unirio.sd.c_modelo;
/*
 ************************************************************************
 *									*
 * Verifica a sem�ntica de uma conex�o em um cen�rio			*
 *									*
 ************************************************************************
 */

static int verifica_semantica_conexao (CENARIO *cenario, CONEXAO *conexao, METAMODELO *metamodelo)
{
	char		s[2049];

	if (pega_classe_nome (metamodelo, conexao -> idclasse) == NULL)
	{
		sprintf (s, "Scenario %s: the class %s used in the connection %s is not defined in the metamodel", cenario -> id, conexao -> idclasse, conexao -> id);
		return (yyerror (s));
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica a sem�ntica de uma restri��o em um cen�rio			*
 *									*
 ************************************************************************
 */

static int verifica_semantica_restricao (CENARIO *cenario, RESTRICAO *restricao, METAMODELO *metamodelo)
{
	CONEXAO		*conexao;
	RELACAO		*relacao;
	char		s[2049];


	if ((conexao = pega_conexao_nome (cenario, restricao -> idconexao_origem)) == NULL)
	{
		sprintf (s, "Scenario %s: the role %s used in a constraint is not defined in the scenario", cenario -> id, restricao -> idconexao_origem);
		return (yyerror (s));
	}

	if (*restricao -> idrelacao == 0)
		return (TRUE);

	relacao = pega_relacionamento_nome (metamodelo, restricao -> idrelacao);

	if (relacao == NULL)
	{
		sprintf (s, "Scenario %s: the relationship %s used in a constraint is not defined in the metamodel", cenario -> id, restricao -> idrelacao);
		return (yyerror (s));
	}

	if (relacao == NULL)
	{
		sprintf (s, "Scenario %s: the relationship %s used in a constraint is not defined in the metamodel", cenario -> id, restricao -> idrelacao);
		return (yyerror (s));
	}

	if (stricmp (relacao -> idclasse_origem, conexao -> idclasse) != 0  &&  stricmp (relacao -> idclasse_destino, conexao -> idclasse) != 0)
	{
		sprintf (s, "Scenario %s: the relationship %s used in a constraint relates to another class", cenario -> id, restricao -> idrelacao);
		return (yyerror (s));
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica a sem�ntica de um cen�rio de um metamodelo			*
 *									*
 ************************************************************************
 */

static int verifica_semantica_cenario_metamodelo (CENARIO *cenario, METAMODELO *metamodelo)
{
	RESTRICAO	*restricao;
	CONEXAO		*conexao;
	int		i;


	for (i = 0; i < pega_numero_itens (&cenario -> conexoes); i++)
	{
		conexao = pega_item (&cenario -> conexoes, i);

		if (!verifica_semantica_conexao (cenario, conexao, metamodelo))
			return (FALSE);
	}

	for (i = 0; i < pega_numero_itens (&cenario -> restricoes); i++)
	{
		restricao = pega_item (&cenario -> restricoes, i);

		if (!verifica_semantica_restricao (cenario, restricao, metamodelo))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica a sem�ntica dos cen�rios em rela��o aos metamodelos		*
 *									*
 ************************************************************************
 */

int verifica_semantica_cenarios (METAMODELO *metamodelo)
{
	CENARIO		*cenario;
	int		i;

	for (i = 0; i < pega_numero_itens (&metamodelo -> cenarios); i++)
	{
		cenario = pega_item (&metamodelo -> cenarios, i);
		
		if (!verifica_semantica_cenario_metamodelo (cenario, metamodelo))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica se o nome de um cen�rio � v�lido				*
 *									*
 ************************************************************************
 */

int nome_valido_cenario (METAMODELO *metamodelo, char *id)
{
	if (pega_cenario_nome (metamodelo, id) != NULL)
		return (yyerror ("The metamodel has a scenario with the same name"));

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica se o nome de um elemento � v�lido para um cen�rio		*
 *									*
 ************************************************************************
 */

int nome_valido_elemento_cenario (CENARIO *cenario, char *id)
{
	if (pega_conexao_nome (cenario, id) != NULL)
		return (yyerror ("The scenario has a connection with the same name"));

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Determina se o nome de um novo elemento � v�lido para a conex�o	*
 *									*
 ************************************************************************
 */

int nome_valido_elemento_conexao (CONEXAO *conexao, char *id)
{
	if (pega_propriedade_cenario_nome (conexao, id) != NULL)
		return (yyerror ("The connection has a property with the same name"));

	if (pega_comportamento_cenario_nome (conexao, id) != NULL)
		return (yyerror ("The connection has a behavior with the same name"));

	return (TRUE);
}
