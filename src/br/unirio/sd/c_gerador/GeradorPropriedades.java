
/*
 ************************************************************************
 *									*
 * Gera um clone de uma propriedade					*
 *									*
 ************************************************************************
 */

static PROPRIEDADE *clona_propriedade (PROPRIEDADE *original)
{
	PROPRIEDADE		*clone;


	if ((clone = (PROPRIEDADE *) calloc (1, sizeof(PROPRIEDADE))) == NULL)
	{
		yyerror ("Out of memory");
		return (NULL);
	}

	strcpy (clone -> id, original -> id);
	clone -> valor = original -> valor;
	clone -> visibilidade = original -> visibilidade;
	return (clone);
}


/*
 ************************************************************************
 *									*
 * Copia as propriedades de uma classe para um objeto			*
 *									*
 ************************************************************************
 */

static int copia_propriedades_classe (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto)
{
	PROPRIEDADE	*prop, *clone;
	CLASSE		*classe;
	int		i;


	classe = pega_classe_nome (metamodelo, objeto -> idclasse);

	for (i = 0; i < pega_numero_itens (&classe -> propriedades); i++)
	{
		prop = pega_item (&classe -> propriedades, i);
				
		if ((clone = clona_propriedade (prop)) == NULL)
			return (FALSE);

		insere_item (&objeto -> propriedades, clone);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Copia as propriedades de uma conexão para um objeto			*
 *									*
 ************************************************************************
 */

static int copia_propriedades_conexao (METAMODELO *metamodelo, MODELO *modelo, CONMODELO *conmodelo, CENARIO *cenario)
{
	char		s[200];
	CONEXAO		*conexao;
	OBJETO		*objeto;
	PROPRIEDADE	*prop, *clone;
	int		i;


	objeto = pega_objeto_nome (modelo, conmodelo -> idobjeto);

	conexao = pega_conexao_nome (cenario, conmodelo -> idconexao);

	for (i = 0; i < pega_numero_itens (&conexao -> propriedades); i++)
	{
		prop = pega_item (&conexao -> propriedades, i);

		if (pega_propriedade_compilacao_nome (objeto, prop -> id) != NULL)
		{
			sprintf (s, "Scenario %s: the object %s used in the connection %s already has a property named %s", cenario -> id, objeto -> id, conexao -> id, prop -> id);
			return (yyerror (s));
		}

		if ((clone = clona_propriedade (prop)) == NULL)
			return (FALSE);

		insere_item (&objeto -> propriedades, clone);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Copia as propriedades de uma ativação para seus objetos		*
 *									*
 ************************************************************************
 */

static int copia_propriedades_ativacao (METAMODELO *metamodelo, MODELO *modelo, ATIVACAO *ativacao)
{
	CONMODELO	*conmodelo;
	CENARIO		*cenario;
	int		i;


	cenario = pega_cenario_nome (metamodelo, ativacao -> idcenario);

	for (i = 0; i < pega_numero_itens (&ativacao -> conexoes); i++)
	{
		conmodelo = pega_item (&ativacao -> conexoes, i);
		
		if (!copia_propriedades_conexao (metamodelo, modelo, conmodelo, cenario))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Copia os valores das definições de propriedades para o objeto	*
 *									*
 ************************************************************************
 */

static int copia_valores_propriedade_modelo (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto)
{
	PROPRIEDADE	*propriedade;
	PROPMODELO	*propmodelo;
	char		s[2048];
	int		i;


	for (i = 0; i < pega_numero_itens(&objeto -> propmodelo); i++)
	{
		propmodelo = pega_item (&objeto -> propmodelo, i);

		propriedade = pega_propriedade_compilacao_nome (objeto, propmodelo -> id);

		if (propriedade == NULL)
		{
			sprintf (s, "Neither the class %s nor any scenario activated upon object %s declares the property %s", objeto -> idclasse, objeto -> id, propmodelo -> id);
			return (yyerror (s));
		}

		propriedade -> valor = propmodelo -> valor;
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Copia as propriedades das classes e conexões para os objetos		*
 *									*
 ************************************************************************
 */

int copia_propriedades_objetos (METAMODELO *metamodelo, MODELO *modelo)
{
	OBJETO		*objeto;
	ATIVACAO	*ativacao;
	int		i;


	/*
	 * Libera as propriedades previamente capturadas para os objetos
	 */

	for (i = 0; i < pega_numero_itens (&modelo -> objetos); i++)
	{
		objeto = pega_item (&modelo -> objetos, i);
		libera_propriedades (&objeto -> propriedades);
	}


	/*
	 * Copia as propriedades das classes para os objetos
	 */

	for (i = 0; i < pega_numero_itens (&modelo -> objetos); i++)
	{
		objeto = pega_item (&modelo -> objetos, i);

		if (!copia_propriedades_classe (metamodelo, modelo, objeto))
			return (FALSE);
	}


	/*
	 * Copia as propriedades das ativações para os objetos
	 */

	for (i = 0; i < pega_numero_itens (&modelo -> ativacoes); i++)
	{
		ativacao = pega_item (&modelo -> ativacoes, i);
		
		if (!copia_propriedades_ativacao (metamodelo, modelo, ativacao))
			return (FALSE);
	}


	/*
	 * Atualiza as definições das propriedades de acordo com as declarações do modelo
	 */

	for (i = 0; i < pega_numero_itens (&modelo -> objetos); i++)
	{
		objeto = pega_item (&modelo -> objetos, i);

		if (!copia_valores_propriedade_modelo (metamodelo, modelo, objeto))
			return (FALSE);
	}

	return (TRUE);
}