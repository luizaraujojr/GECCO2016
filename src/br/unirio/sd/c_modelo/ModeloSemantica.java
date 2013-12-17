

/*
 ************************************************************************
 *									*
 * Verifica os elementos de uma relação do modelo			*
 *									*
 ************************************************************************
 */

static int verifica_elementos_relacao_modelo (EXPRESSAO *no, RELMODELO *relacao, RELACAO *metarel, MODELO *modelo, OBJETO *objeto, CLASSE *classe)
{
	char		s[1024];
	OBJETO		*relobjeto;


	if (no -> operacao == OP_COMMA)
	{
		if (!verifica_elementos_relacao_modelo (no -> dir, relacao, metarel, modelo, objeto, classe))
			return (FALSE);

		if (!verifica_elementos_relacao_modelo (no -> esq, relacao, metarel, modelo, objeto, classe))
			return (FALSE);
	}
	else if (no -> operacao == OP_VARIAVEL)
	{
		relobjeto = pega_objeto_nome (modelo, no -> nome);

		if (relobjeto == NULL)
		{
			sprintf (s, "The class %s, used by the %s relation of the %s class, was not defined in the model", no -> nome, relacao -> id, objeto -> id);
			return (yyerror (s));
		}

		if (relobjeto == objeto)
		{
			sprintf (s, "Circular references are not allowed in the %s relation of the %s object", relacao -> id, objeto -> id);
			return (yyerror (s));
		}

		if (stricmp (relobjeto -> idclasse, metarel -> idclasse_destino) != 0)
		{
			sprintf (s, "The class %s, used by the %s relation of the %s class, was not defined as the %s class", no -> nome, relacao -> id, objeto -> id, metarel -> idclasse_destino);
			return (yyerror (s));
		}

	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica as relações de um objeto					*
 *									*
 ************************************************************************
 */

static int verifica_relacao_modelo (RELMODELO *relacao, METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, CLASSE *classe)
{
	RELACAO		*metarel;
	char		s[1024];


	metarel = pega_relacionamento_nome_origem (metamodelo, relacao -> id);

	if (metarel == NULL)
	{
		sprintf (s, "The metamodel %s does not convey the relation %s, defined for the object %s", metamodelo -> id, relacao -> id, objeto -> id);
		return (yyerror (s));
	}

	if (metarel -> tipo == SIMPLES  &&  relacao -> elementos -> operacao == OP_COMMA)
	{
		sprintf (s, "The relation %s is defined as single in the class %s (error in the object %s)", relacao -> id, objeto -> idclasse, objeto -> id);
		return (yyerror (s));
	}

	return (verifica_elementos_relacao_modelo (relacao -> elementos, relacao, metarel, modelo, objeto, classe));
}


/*
 ************************************************************************
 *									*
 * Verifica a semântica de um objeto					*
 *									*
 ************************************************************************
 */

static int verifica_semantica_objeto (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto)
{
	CLASSE		*classe;
	RELMODELO	*relacao;
	char		s[1024];
	int		i;


	/*
	 * Verifica a existência da classe no metamodelo
	 */

	classe = pega_classe_nome (metamodelo, objeto -> idclasse);

	if (classe == NULL)
	{
		sprintf (s, "The class %s, associated with the object %s, is not defined in the metamodel %s", objeto -> idclasse, objeto -> id, metamodelo -> id);
		return (yyerror (s));
	}


	/*
	 * Verifica as propriedades definidas no modelo (verificado durante a compilação)
	 */

	/*
	 * Verifica as relações da classe do modelo
	 */

	for (i = 0; i < pega_numero_itens (&objeto -> relmodelo); i++)
	{
		relacao = pega_item (&objeto -> relmodelo, i);

		if (!verifica_relacao_modelo (relacao, metamodelo, modelo, objeto, classe))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Retorna o comportamento de um determinado nome definido no cenario	*
 *									*
 ************************************************************************
 */

static COMPORTAMENTO *pega_comportamento_cenario (char *id, OBJETO *objeto, MODELO *modelo, METAMODELO *metamodelo)
{
	COMPORTAMENTO	*comp;
	ATIVACAO	*ativacao;
	CONMODELO	*conmodelo;
	CONEXAO		*conexao;
	CENARIO		*cenario;
	int		i, j;


	for (i = 0; i < pega_numero_itens(&modelo -> ativacoes); i++)
	{
		ativacao = pega_item (&modelo -> ativacoes, i);

		for (j = 0; j < pega_numero_itens (&ativacao -> conexoes); j++)
		{
			conmodelo = pega_item (&ativacao -> conexoes, j);

			if (stricmp (conmodelo -> idobjeto, objeto -> id) != 0)
				continue;

			cenario = pega_cenario_nome (metamodelo, ativacao -> idcenario);

			conexao = pega_conexao_nome (cenario, conmodelo -> idconexao);

			if ((comp = pega_comportamento_cenario_nome (conexao, id)) != NULL)
				return (comp);
		}
	}

	return (NULL);
}


/*
 ************************************************************************
 *									*
 * Retorna a conexão de modelo de um objeto que declare um elemento	*
 *									*
 ************************************************************************
 */

static CONMODELO *pega_conexao_modelo_elemento (char *id, OBJETO *objeto, MODELO *modelo, METAMODELO *metamodelo)
{
	ATIVACAO	*ativacao;
	CONMODELO	*conmodelo;
	CONEXAO		*conexao;
	CENARIO		*cenario;
	int		i, j;


	for (i = 0; i < pega_numero_itens(&modelo -> ativacoes); i++)
	{
		ativacao = pega_item (&modelo -> ativacoes, i);

		for (j = 0; j < pega_numero_itens (&ativacao -> conexoes); j++)
		{
			conmodelo = pega_item (&ativacao -> conexoes, j);

			if (stricmp (conmodelo -> idobjeto, objeto -> id) != 0)
				continue;

			cenario = pega_cenario_nome (metamodelo, ativacao -> idcenario);

			conexao = pega_conexao_nome (cenario, conmodelo -> idconexao);

			if (pega_propriedade_cenario_nome (conexao, id) != NULL)
				return (conmodelo);

			if (pega_comportamento_cenario_nome (conexao, id) != NULL)
				return (conmodelo);
		}
	}

	return (NULL);
}


/*
 ************************************************************************
 *									*
 * Verifica a validade de uma propriedade/comportamento de cenário	*
 *									*
 ************************************************************************
 */

static int verifica_elemento_conexao_ativacao (char *id, char *tipo_elemento, CLASSE *classe, OBJETO *objeto, CONMODELO *conmodelo, ATIVACAO *ativacao, MODELO *modelo, METAMODELO *metamodelo)
{
	char		s[2049];

	
	/*
	 * A classe com a conexão que contém o comportamento não pode definir uma propriedade
	 * com o mesmo nome do elemento do cenário
	 */

	if (pega_propriedade_nome (classe, id) != NULL)
	{
		sprintf (s, "The class %s already has a property %s as used by a %s in the scenario %s", classe -> id, id, tipo_elemento, ativacao -> idcenario);
		return (yyerror (s));
	}


	/*
	 * A classe com a conexão que contém o comportamento não pode definir um comportamento
	 * com o mesmo nome do elemento do cenário
	 */

	if (pega_comportamento_nome (classe, id) != NULL)
	{
		sprintf (s, "The class %s already has a behavior %s as used by a %s in the scenario %s", classe -> id, id, tipo_elemento, ativacao -> idcenario);
		return (yyerror (s));
	}


	/*
	 * Verifica se alguma conexão de outro cenário define um comportamento ou propriedade
	 * com o mesmo nome do elemento
	 */

	if (pega_conexao_modelo_elemento (id, objeto, modelo, metamodelo) != conmodelo)
	{
		sprintf (s, "Another scenario activated upon the object %s already has a property or behavior %s as used by a %s in the scenario %s", conmodelo -> idobjeto, id, tipo_elemento, ativacao -> idcenario);
		return (yyerror (s));
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica a validade de um ajuste de cenário				*
 *									*
 ************************************************************************
 */

static int verifica_ajuste_conexao_ativacao (AJUSTE *ajuste, CLASSE *classe, OBJETO *objeto, CONMODELO *conmodelo, ATIVACAO *ativacao, CENARIO *cenario, MODELO *modelo, METAMODELO *metamodelo)
{
	COMPORTAMENTO	*comp;
	char		s[2049];


	/*
	 * Pega o comportamento associado ao ajuste na classe
	 */

	comp = pega_comportamento_nome (classe, ajuste -> id);


	/*
	 * Se o comportamento não foi definido, procura no cenario
	 */

	if (comp == NULL)
		comp = pega_comportamento_cenario (ajuste -> id, objeto, modelo, metamodelo);


	/*
	 * Se o comportamento também não foi definido no cenário, retorna com erro
	 */

	if (comp == NULL)
	{
		sprintf (s, "The behavior %s, affected by an AFFECT clause in the scenario %s, was not defined by the class %s nor by any other scenario", ajuste -> id, ativacao -> idcenario, classe -> id);
		return (yyerror (s));
	}


	/*
	 * Se o comportamento não for taxa ou processo, retorna com erro
	 */

	if (comp -> tipo != TP_RATE  &&  comp -> tipo != TP_PROC)
	{
		sprintf (s, "The behavior %s, affected by an AFFECT clause in the scenario %s, is not a rate nor a process", ajuste -> id, cenario -> id);
		return (yyerror (s));
	}



	/*
	 * A expressão do ajuste deve referenciar o nome do comportamento
	 */

	if (pega_no_variavel (ajuste -> expressao, comp -> id) == NULL)
	{
		sprintf (s, "The expression for the AFFECT clause %s in the scenario %s does not reference itself", ajuste -> id, cenario -> id);
		return (yyerror (s));
	}


	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica a semântica de uma conexão de ativação			*
 *									*
 ************************************************************************
 */

static int verifica_ativacao_cenario (char *cenario, char *papel, OBJETO *objeto, ATIVACAO *ativacao, MODELO *modelo, METAMODELO *metamodelos)
{
	ATIVACAO	*ativacao2;
	CONMODELO	*conmodelo;
	char		s[2049];
	int		i, j;


	for (i = 0; i < pega_numero_itens (&modelo -> ativacoes); i++)
	{
		ativacao2 = pega_item (&modelo -> ativacoes, i);

		if (stricmp (ativacao2 -> idcenario, cenario) != 0)
			continue;

		for (j = 0; j < pega_numero_itens (&ativacao2 -> conexoes); j++)
		{
			conmodelo = pega_item (&ativacao2 -> conexoes, j);

			if (stricmp (conmodelo -> idobjeto, objeto -> id) == 0  &&  stricmp (conmodelo -> idconexao, papel) == 0)
				return (TRUE);
		}
	}

	sprintf (s, "The scenario %s is not activated with the object %s fulfilling the role %s, as required by the scenario %s", cenario, objeto -> id, papel, ativacao -> idcenario);
	return (yyerror (s));
}


/*
 ************************************************************************
 *									*
 * Verifica a semântica de uma restrição sobre uma ativacao		*
 *									*
 ************************************************************************
 */

static int verifica_semantica_restricao_ativacao (RESTRICAO *restricao, OBJETO *objeto, ATIVACAO *ativacao, CENARIO *cenario, MODELO *modelo, METAMODELO *metamodelo)
{
	EXPRESSAO	*elementos;
	RELMODELO	*relacao;
	OBJETO		*obj;


	/*
 	 * REGRA 7: Para cada restrição sem relacionamento, o objeto relacionado deve
	 * ter o cenário associado ativado sobre ele
	 */

	if (strlen(restricao -> idrelacao) == 0)
		return (verifica_ativacao_cenario (restricao -> idcenario, restricao -> idconexao_destino, objeto, ativacao, modelo, metamodelo));


	/*
	 * REGRA 8: Para cada restrição com relacionamento, os relacionamento do objeto
	 * relacionado devem ter o cenário ativado sobre ele
	 */

	relacao = pega_relacao_modelo_nome (objeto, restricao -> idrelacao);

	if (relacao == NULL)
		return (TRUE);

	elementos = relacao -> elementos;

	while (elementos != NULL)
	{
		if (elementos -> operacao == OP_COMMA)
		{
			obj = pega_objeto_nome (modelo, elementos -> dir -> nome);
			
			if (!verifica_ativacao_cenario (restricao -> idcenario, restricao -> idconexao_destino, obj, ativacao, modelo, metamodelo))
				return (FALSE);
		}
		else if (elementos -> operacao == OP_VARIAVEL)
		{
			obj = pega_objeto_nome (modelo, elementos -> nome);

			if (!verifica_ativacao_cenario (restricao -> idcenario, restricao -> idconexao_destino, obj, ativacao, modelo, metamodelo))
				return (FALSE);
		}

		elementos = elementos -> esq;
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica a semântica de uma conexão de uma ativação de cenário	*
 *									*
 ************************************************************************
 */

static int verifica_semantica_conexao_ativacao (CONMODELO *conmodelo, ATIVACAO *ativacao, CENARIO *cenario, MODELO *modelo, METAMODELO *metamodelo)
{
	COMPORTAMENTO	*comp;
	PROPRIEDADE	*prop;
	RESTRICAO	*restricao;
	AJUSTE		*ajuste;
	CONEXAO		*conexao;
	CLASSE		*classe;
	OBJETO		*objeto;
	char		s[2049];
	int		i;


	/*
	 * Pega a conexão de metamodelo relacionada com a conexão de modelo
	 */

	conexao = pega_conexao_nome (cenario, conmodelo -> idconexao);


	/*
	 * REGRA 2: a conexão do objeto deve estar definida no cenário do metamodelo
	 */

	if (conexao == NULL)
	{
		sprintf (s, "The scenario %s, is activated upon the object %s playing the role of %s, which is not defined in the scenario", ativacao -> idcenario, conmodelo -> idobjeto, conmodelo -> idconexao);
		return (yyerror (s));
	}


	/*
	 * Pega a classe relacionada com a conexão no metamodelo. A classe é válida pq
	 * foi analisada na verificação semântica entre o cenário e o metamodelo
	 */

	classe = pega_classe_nome (metamodelo, conexao -> idclasse);

	
	/*
	 * Pega o objeto relacionado com a ativação no modelo. O objeto é garantido como
	 * válido por construção no analisador sintático
	 */

	objeto = pega_objeto_nome (modelo, conmodelo -> idobjeto);


	/*
	 * REGRA 3: os comportamentos definidos pela conexão não devem ser definidos na
	 * classe associada ou nos demais cenários ativados sobre o objeto
	 */

	for (i = 0; i < pega_numero_itens (&conexao -> comportamentos); i++)
	{
		comp = pega_item (&conexao -> comportamentos, i);
		
		if (!verifica_elemento_conexao_ativacao (comp -> id, "behavior", classe, objeto, conmodelo, ativacao, modelo, metamodelo))
			return (FALSE);
	}


	/*
	 * REGRA 4: as propriedades definidas pela conexão não devem ser definidas na
	 * classe associada ou nos demais cenários ativados sobre o objeto
	 */

	for (i = 0; i < pega_numero_itens (&conexao -> propriedades); i++)
	{
		prop = pega_item (&conexao -> propriedades, i);
		
		if (!verifica_elemento_conexao_ativacao (prop -> id, "property", classe, objeto, conmodelo, ativacao, modelo, metamodelo))
			return (FALSE);
	}


	/*
	 * REGRA 5: As cláusulas de ajuste de um cenário devem atuar sobre um processo
	 * ou sobre uma taxa definida no metamodelo ou cenário anterior
	 */

	for (i = 0; i < pega_numero_itens (&conexao -> ajustes); i++)
	{
		ajuste = pega_item (&conexao -> ajustes, i);

		if (!verifica_ajuste_conexao_ativacao (ajuste, classe, objeto, conmodelo, ativacao, cenario, modelo, metamodelo))
			return (FALSE);
	}


	/*
	 * REGRA 6: os identificadores utilizados em um cenário devem estar definidos
	 * no metamodelo ou em algum cenário aplicado anteriormente (não verificada).
	 */


	/*
	 * Verifica a semântica das restrições da ativação do cenário
	 */
	
	for (i = 0; i < pega_numero_itens (&cenario -> restricoes); i++)
	{
		restricao = pega_item (&cenario -> restricoes, i);

		if (stricmp (restricao -> idconexao_origem, conmodelo -> idconexao) != 0)
			continue;
		
		if (!verifica_semantica_restricao_ativacao (restricao, objeto, ativacao, cenario, modelo, metamodelo))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica a semântica de uma ativação de cenário sobre um modelo	*
 *									*
 ************************************************************************
 */

static int verifica_semantica_ativacao (ATIVACAO *ativacao, MODELO *modelo, METAMODELO *metamodelo)
{
	CENARIO		*cenario;
	CONMODELO	*conmodelo;
	char		s[2049];
	int		i;


	/*
	 * Pega o cenário relacionado com a ativação
	 */

	cenario = pega_cenario_nome (metamodelo, ativacao -> idcenario);


	/*
 	 * REGRA 1: o cenário ativado deve estar definido no metamodelo
	 */

	if (cenario == NULL)
	{
		sprintf (s, "The scenario %s, activated upon the model %s, is not defined in the metamodel %s", ativacao -> idcenario, modelo -> id, metamodelo -> id);
		return (yyerror (s));
	}


	/*
	 * Verifica a semântica das conexões da ativação do cenário
	 */
	
	for (i = 0; i < pega_numero_itens (&ativacao -> conexoes); i++)
	{
		conmodelo = pega_item (&ativacao -> conexoes, i);
		
		if (!verifica_semantica_conexao_ativacao (conmodelo, ativacao, cenario, modelo, metamodelo))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica a semântica das ativações de cenários sobre um modelo	*
 *									*
 ************************************************************************
 */

static int verifica_semantica_ativacoes (MODELO *modelo, METAMODELO *metamodelo)
{
	ATIVACAO	*ativacao;
	int		i;


	for (i = 0; i < pega_numero_itens (&modelo -> ativacoes); i++)
	{
		ativacao = pega_item (&modelo -> ativacoes, i);

		if (!verifica_semantica_ativacao (ativacao, modelo, metamodelo))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica a semântica de um modelo					*
 *									*
 ************************************************************************
 */

int verifica_semantica_modelo (METAMODELO *metamodelo, MODELO *modelo)
{
	OBJETO		*objeto;
	int		i;


	/*
	 * Verifica a existência do meta modelo
	 */

	if (stricmp (modelo -> idmeta, metamodelo -> id) != 0)
		return (yyerror ("The model's metamodel is not defined"));


	/*
	 * Verifica a semântica dos cenários ativados sobre o modelo
	 */

	if (!verifica_semantica_ativacoes (modelo, metamodelo))
		return (FALSE);


	/*
	 * Verifica a semântica das objetos do modelo
	 */

	for (i = 0; i < pega_numero_itens (&modelo -> objetos); i++)
	{
		objeto = pega_item (&modelo -> objetos, i);

		if (!verifica_semantica_objeto (metamodelo, modelo, objeto))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica se o nome de um objeto é válido para um modelo		*
 *									*
 ************************************************************************
 */

int nome_valido_objeto (MODELO *modelo, char *id)
{
	if (pega_objeto_nome (modelo, id) != NULL)
		return (yyerror ("The model already has an object with the same name"));

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Determina se o nome de uma propriedade é válido para uma classe	*
 *									*
 ************************************************************************
 */

int nome_valido_propriedade_objeto (OBJETO *classe, char *id)
{
	if (pega_propriedade_modelo_nome (classe, id) != NULL)
		return (yyerror ("This object's property is already defined"));

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Determina se o nome de uma relação é válido para uma classe		*
 *									*
 ************************************************************************
 */

int nome_valido_relacao_objeto (OBJETO *classe, char *id)
{
	if (pega_relacao_modelo_nome (classe, id) != NULL)
		return (yyerror ("This object's relation is already defined"));

	return (TRUE);
}