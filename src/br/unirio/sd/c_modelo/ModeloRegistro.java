
/*
 * Objetos utilizados durante a construção do modelo
 */

static MODELO		*modelo_atual = NULL;

static OBJETO		*objeto_atual = NULL;

static ATIVACAO		*ativacao_atual = NULL;


/*
 ************************************************************************
 *									*
 * Inicializa o modelo convertido na aplicação				*
 *									*
 ************************************************************************
 */

static int prepara_modelo_atual ()
{
	if (modelo_atual != NULL)
		return (TRUE);

	if ((modelo_atual = (MODELO *) malloc (sizeof (MODELO))) == NULL)
		return (yyerror ("Out of memory"));

	prepara_vetor (&modelo_atual -> objetos);
	prepara_vetor (&modelo_atual -> ativacoes);

	*(modelo_atual -> id) = 0;
	*(modelo_atual -> idmeta) = 0;
	modelo_atual -> passo = 1;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Registra um modelo, dado seu nome e o nome do metamodelo		*
 *									*
 ************************************************************************
 */

MODELO *registra_modelo (char *id, char *idmeta)
{
	MODELO		*modelo;


	if (!prepara_modelo_atual ())
		return (NULL);

	strcpy (modelo_atual -> id, id);
	strcpy (modelo_atual -> idmeta, idmeta);
	modelo = modelo_atual;

	modelo_atual = NULL;
	return (modelo);
}


/*
 ************************************************************************
 *									*
 * Cria, se necessário, um novo objeto para ser composto		*
 *									*
 ************************************************************************
 */

static int prepara_objeto_atual ()
{
	if (!prepara_modelo_atual ())
		return (FALSE);

	if (objeto_atual != NULL)
		return (TRUE);

	if ((objeto_atual = (OBJETO *) malloc (sizeof(OBJETO))) == NULL)
		return (yyerror ("Out of memory"));

	prepara_vetor (&objeto_atual -> propmodelo);
	prepara_vetor (&objeto_atual -> relmodelo);
	prepara_vetor (&objeto_atual -> comportamentos);
	prepara_vetor (&objeto_atual -> propriedades);
	*(objeto_atual -> id) = 0;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Registra um objeto no modelo sendo composto				*
 *									*
 ************************************************************************
 */

int registra_objeto (char *id, char *idmeta)
{
	if (!prepara_objeto_atual ())
		return (FALSE);

	if (!nome_valido_objeto (modelo_atual, id))
	{
		free (objeto_atual);
		objeto_atual = NULL;
		return (FALSE);
	}

	strcpy (objeto_atual -> id, id);
	strcpy (objeto_atual -> idclasse, idmeta);

	insere_item (&modelo_atual -> objetos, objeto_atual);
	objeto_atual = NULL;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Cria uma propriedade e insere no objeto sendo composto atualmente	*
 *									*
 ************************************************************************
 */

int registra_propriedade_objeto (char *id, double valor)
{
	PROPMODELO	*prop;


	if (!prepara_objeto_atual ())
		return (FALSE);

	if (!nome_valido_propriedade_objeto (objeto_atual, id))
		return (FALSE);

	if ((prop = (PROPMODELO *) calloc (1, sizeof(PROPMODELO))) == NULL)
		return (yyerror ("Out of memory"));

	strcpy (prop -> id, id);
	prop -> valor = valor;
	
	insere_item (&objeto_atual -> propmodelo, prop);
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Cria uma relação e insere no objeto sendo composto atualmente	*
 *									*
 ************************************************************************
 */

int registra_relacao_objeto (char *idrelacao, EXPRESSAO *elementos)
{
	RELMODELO		*relacao;


	if (!prepara_objeto_atual ())
		return (FALSE);

	if (!nome_valido_relacao_objeto (objeto_atual, idrelacao))
		return (FALSE);

	if ((relacao = (RELMODELO *) calloc (1, sizeof(RELMODELO))) == NULL)
		return (yyerror ("Out of memory"));

	strcpy (relacao -> id, idrelacao);
	relacao -> elementos = elementos;

	insere_item (&objeto_atual -> relmodelo, relacao);
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Cria, se necessário, uma ativação de cenário para ser composta	*
 *									*
 ************************************************************************
 */

static int prepara_ativacao_atual ()
{
	if (!prepara_modelo_atual ())
		return (FALSE);

	if (ativacao_atual != NULL)
		return (TRUE);

	if ((ativacao_atual = (ATIVACAO *) malloc (sizeof(ATIVACAO))) == NULL)
		return (yyerror ("Out of memory"));

	prepara_vetor (&ativacao_atual -> conexoes);
	*(ativacao_atual -> idcenario) = 0;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Registra uma ativação no modelo sendo composto			*
 *									*
 ************************************************************************
 */

int registra_ativacao (char *idcenario)
{
	if (!prepara_ativacao_atual ())
		return (FALSE);

	strcpy (ativacao_atual -> idcenario, idcenario);
	insere_item (&modelo_atual -> ativacoes, ativacao_atual);
	ativacao_atual = NULL;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Registra uma conexão de objeto na ativação sendo composta		*
 *									*
 ************************************************************************
 */

int registra_conexao_objeto (char *idconexao, char *idobjeto)
{
	CONMODELO	*conexao;


	if (!prepara_ativacao_atual ())
		return (FALSE);

	if ((conexao = (CONMODELO *) calloc (1, sizeof(CONMODELO))) == NULL)
		return (yyerror ("Out of memory"));

	strcpy (conexao -> idconexao, idconexao);
	strcpy (conexao -> idobjeto, idobjeto);

	insere_item (&ativacao_atual -> conexoes, conexao);
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Indica o passo de simulação do modelo				*
 *									*
 ************************************************************************
 */

int muda_passo_modelo (double passo)
{
	if (!prepara_modelo_atual ())
		return (FALSE);

	if (passo <= 0.0)
		return (yyerror ("The simulation step cannot be negative or zero"));

	modelo_atual -> passo = passo;
	return (TRUE);
}
