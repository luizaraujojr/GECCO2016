

/*
 ************************************************************************
 *									*
 * Gera um clone de um comportamento					*
 *									*
 ************************************************************************
 */

static COMPORTAMENTO *clona_comportamento (COMPORTAMENTO *original)
{
	COMPORTAMENTO		*clone;


	if ((clone = (COMPORTAMENTO *) malloc (sizeof(COMPORTAMENTO))) == NULL)
	{
		yyerror ("Out of memory");
		return (NULL);
	}

	clone -> tipo = original -> tipo;
	clone -> visibilidade = original -> visibilidade;
	strcpy (clone -> id, original -> id);
	clone -> expressao = clona_expressao (original -> expressao);
	clone -> repositorio = clona_expressao (original -> repositorio);
	return (clone);
}


/*
 ************************************************************************
 *									*
 * Copia os comportamentos de uma classe para um objeto			*
 *									*
 ************************************************************************
 */

static int copia_comportamentos_classe (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto)
{
	COMPORTAMENTO	*comp, *clone;
	CLASSE		*classe;
	int		i;


	classe = pega_classe_nome (metamodelo, objeto -> idclasse);

	for (i = 0; i < pega_numero_itens (&classe -> comportamentos); i++)
	{
		comp = pega_item (&classe -> comportamentos, i);

		if ((clone = clona_comportamento (comp)) == NULL)
			return (FALSE);

		insere_item (&objeto -> comportamentos, clone);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Copia os comportamentos de uma conexão para um objeto		*
 *									*
 ************************************************************************
 */

static int copia_comportamentos_conexao (METAMODELO *metamodelo, MODELO *modelo, CONMODELO *conmodelo, CENARIO *cenario)
{
	COMPORTAMENTO	*comp, *clone;
	CONEXAO		*conexao;
	OBJETO		*objeto;
	char		s[200];
	int		i;


	objeto = pega_objeto_nome (modelo, conmodelo -> idobjeto);

	conexao = pega_conexao_nome (cenario, conmodelo -> idconexao);

	for (i = 0; i < pega_numero_itens (&conexao -> comportamentos); i++)
	{
		comp = pega_item (&conexao -> comportamentos, i);

		if (pega_propriedade_modelo_nome (objeto, comp -> id) != NULL)
		{
			sprintf (s, "Scenario %s: the object %s used in the connection %s already has a property named %s", cenario -> id, objeto -> id, conexao -> id, comp -> id);
			return (yyerror (s));
		}

		if (pega_comportamento_compilacao_nome (objeto, comp -> id) != NULL)
		{
			sprintf (s, "Scenario %s: the object %s used in the connection %s already has a behavior named %s", cenario -> id, objeto -> id, conexao -> id, comp -> id);
			return (yyerror (s));
		}

		if ((clone = clona_comportamento (comp)) == NULL)
			return (FALSE);

		insere_item (&objeto -> comportamentos, clone);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Copia os comportamentos de uma ativação para seus objetos		*
 *									*
 ************************************************************************
 */

static int copia_comportamentos_ativacao (METAMODELO *metamodelo, MODELO *modelo, ATIVACAO *ativacao)
{
	CONMODELO	*conmodelo;
	CENARIO		*cenario;
	int		i;


	cenario = pega_cenario_nome (metamodelo, ativacao -> idcenario);

	for (i = 0; i < pega_numero_itens (&ativacao -> conexoes); i++)
	{
		conmodelo = pega_item (&ativacao -> conexoes, i);
		
		if (!copia_comportamentos_conexao (metamodelo, modelo, conmodelo, cenario))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Copia os comportamentos das classes e conexões para os objetos	*
 *									*
 ************************************************************************
 */

int copia_comportamentos_objetos (METAMODELO *metamodelo, MODELO *modelo)
{
	OBJETO		*objeto;
	ATIVACAO	*ativacao;
	int		i;


	/*
	 * Libera os comportamentos copiados em uma compilação anterior
	 */

	for (i = 0; i < pega_numero_itens (&modelo -> objetos); i++)
	{
		objeto = pega_item (&modelo -> objetos, i);
		libera_comportamentos (&objeto -> comportamentos);
	}


	/*
	 * Copia os comportamentos da classe
	 */

	for (i = 0; i < pega_numero_itens (&modelo -> objetos); i++)
	{
		objeto = pega_item (&modelo -> objetos, i);
		
		if (!copia_comportamentos_classe (metamodelo, modelo, objeto))
			return (FALSE);
	}


	/*
	 * Copia os comportamentos das ativações de cenários
	 */

	for (i = 0; i < pega_numero_itens (&modelo -> ativacoes); i++)
	{
		ativacao = pega_item (&modelo -> ativacoes, i);
		
		if (!copia_comportamentos_ativacao (metamodelo, modelo, ativacao))
			return (FALSE);
	}

	return (TRUE);
}