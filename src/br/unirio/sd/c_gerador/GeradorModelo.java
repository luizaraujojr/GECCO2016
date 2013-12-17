

/*
 * Protótipos de rotinas do módulo
 */

static int pega_conjunto_objetos (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, EXPRESSAO *no);


/*
 ************************************************************************
 *									*
 * Monta um conjunto de objetos a partir de um identificador		*
 *									*
 ************************************************************************
 */

static int pega_conjunto_objetos_identificador (OBJETO *objeto, EXPRESSAO *no)
{
	RELMODELO	*relmodelo;


	no -> operacao = OP_OBJSET;
	no -> dir = NULL;

	if ((relmodelo = pega_relacao_modelo_nome (objeto, no -> nome)) != NULL)
		no -> esq = clona_expressao (relmodelo -> elementos);
	else
		no -> esq = NULL;

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Monta um conjunto de objetos a partir de um conjunto de classe	*
 *									*
 ************************************************************************
 */

static int pega_conjunto_objetos_conjunto_classe (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, EXPRESSAO *no)
{
	CLASSE		*classe;
	EXPRESSAO	*notemp;
	char		*nomeclasse;
	char		s[1024];
	int		i;


	/*
	 * Pega o nome da classe
	 */

	nomeclasse = no -> esq -> nome;


	/*
	 * Pega a classe no metamodelo
	 */

	if ((classe = pega_classe_nome (metamodelo, nomeclasse)) == NULL)
	{
		sprintf (s, "The class %s, used in the set operator within object %s, was not defined in the metamodelo", nomeclasse, objeto -> id);
		return (yyerror (s));
	}


	/*
	 * Transforma o conjunto de classe em conjunto de objetos
	 */

	no -> operacao = OP_OBJSET;
	no -> dir = NULL;
	no -> esq = NULL;


	/*
	 * Insere os nomes dos objetos da classe no conjunto
	 */

	for (i = pega_numero_itens (&modelo -> objetos) - 1; i >= 0; i--)
	{
		objeto = pega_item (&modelo -> objetos, i);

		if (stricmp (objeto -> idclasse, nomeclasse) != 0)
			continue;

		notemp = constroi_no_variavel (objeto -> id);

		if (no -> esq != NULL)
			no -> esq = constroi_no (OP_COMMA, no -> esq, notemp);
		else
			no -> esq = notemp;
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica se os objetos de um conjunto tem a mesma classe		*
 *									*
 ************************************************************************
 */

static int verifica_objetos_mesma_classe (MODELO *modelo, EXPRESSAO *no)
{
	char		*nomeclasse = NULL;
	EXPRESSAO	*no_nome;
	OBJETO		*objeto;


	for (no = no -> esq; no != NULL; no = no -> esq)
	{
		no_nome = (no -> operacao == OP_COMMA) ? no -> dir : no;

		objeto = pega_objeto_nome (modelo, no_nome -> nome);

		if (nomeclasse == NULL)
			nomeclasse = objeto -> idclasse;

		if (stricmp (nomeclasse, objeto -> idclasse) != 0)
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Verifica se um conjunto de objetos contém um objeto			*
 *									*
 ************************************************************************
 */

static int verifica_conjunto_contem_objeto (EXPRESSAO *conjunto, char *nome)
{
	EXPRESSAO	*no, *no_nome;


	for (no = conjunto; no != NULL; no = no -> esq)
	{
		no_nome = (no -> operacao == OP_COMMA) ? no -> dir : no;

		if (stricmp (no_nome -> nome, nome) == 0)
			return (TRUE);
	}

	return (FALSE);
}


/*
 ************************************************************************
 *									*
 * Verifica se existe um relacionamento entre dois objetos no modelo	*
 *									*
 ************************************************************************
 */

static int verifica_relacionamento_objetos (char *nomerel, OBJETO *contedor, OBJETO *contido)
{
	RELMODELO	*relmodelo;


	if ((relmodelo = pega_relacao_modelo_nome (contedor, nomerel)) == NULL)
		return (FALSE);

	return (verifica_conjunto_contem_objeto (relmodelo -> elementos, contido -> id));
}


/*
 ************************************************************************
 *									*
 * Resolve o conjunto de objetos a esquerda do operador BOUND		*
 *									*
 ************************************************************************
 */

static int pega_conjunto_objetos_bound (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, EXPRESSAO *no)
{
	char		s[1024], *nomeobjeto, *nomerelac;
	CLASSE		*classe;
	RELACAO		*relacao;
	EXPRESSAO	*os, *anterior;
	OBJETO		*o;


	/*
	 * Resolve o conjunto de objetos a esquerda do operador BOUND
	 */

	if (!pega_conjunto_objetos (metamodelo, modelo, objeto, no -> esq))
		return (FALSE);


	/*
	 * O operadr BOUND deve ter um conjunto de objetos a esquerda
	 */

	if (no -> esq -> operacao != OP_OBJSET)
	{
		sprintf (s, "The bound operator within object %s should have an object set as first parameter", objeto -> id);
		return (yyerror (s));
	}


	/*
	 * Se o conjunto for vazio, o resultado do BOUND é um conjunto vazio
	 */

	if (no -> esq -> esq == NULL)
	{
		libera_expressao (no -> esq);
		libera_expressao (no -> dir);
		no -> operacao = OP_OBJSET;
		no -> esq = no -> dir = NULL;
		return (TRUE);
	}


	/*
	 * Os objetos do conjunto devem ser da mesma classe (tb garantido por construção)
	 */

	if (!verifica_objetos_mesma_classe (modelo, no -> esq))
	{
		sprintf (s, "The object set for the bound operator within object %s should have objects from a single class", objeto -> id);
		return (yyerror (s));
	}


	/*
	 * Pega a classe dos objetos do conjunto (pelo primeiro objeto)
	 */

	nomeobjeto = (no -> esq -> esq -> operacao == OP_COMMA) ? no -> esq -> esq -> dir -> nome : no -> esq -> esq -> nome;
	o = pega_objeto_nome (modelo, nomeobjeto);
	classe = pega_classe_nome (metamodelo, objeto -> idclasse);


	/*
	 * O operadr BOUND deve ter um identificador a direita
	 */

	if (no -> dir -> operacao != OP_VARIAVEL)
	{
		sprintf (s, "The bound operator within object %s should have an identifier as second parameter", objeto -> id);
		return (yyerror (s));
	}


	/*
	 * O identificador deve ser um relacionamento do metamodelo
	 */

	relacao = pega_relacionamento_nome (metamodelo, no -> dir -> nome);

	if (relacao == NULL)
	{
		sprintf (s, "The identifier in the bound operator within object %s should be a relationship in the metamodel", objeto -> id);
		return (FALSE);
	}


	/*
	 * Verifica se o relacionamento tem origem ou destino na classe do conjunto de objetos
	 */

	if (stricmp (relacao -> idclasse_origem, classe -> id) == 0)
	{
		nomerelac = relacao -> id;
	}
	else if (stricmp (relacao -> idclasse_destino, classe -> id) == 0)
	{
		nomerelac = (strlen(relacao -> papel_destino) > 0) ? relacao -> papel_destino : relacao -> id;
	}
	else
	{
		sprintf (s, "The relationship %s in the bound operator within object %s should have an origin or target for class %s", relacao -> id, objeto -> id, classe -> id);
		return (FALSE);
	}


	/*
	 * Remove os objetos que não se conectam ao atual através do relacionamento
	 */

	anterior = no -> esq;

	for (os = anterior -> esq; os != NULL; os = os -> esq)
	{
		if (os -> operacao == OP_COMMA)
			nomeobjeto = os -> dir -> nome;
		else
			nomeobjeto = os -> nome;

		o = pega_objeto_nome (modelo, nomeobjeto);

		if (!verifica_relacionamento_objetos (nomerelac, o, objeto))
		{
			anterior -> esq = os -> esq;
			os -> esq = NULL;
			libera_expressao (os);
			os = anterior;
		}

		anterior = os;
	}


	/*
	 * Transforma o operador BOUND em um conjunto de objetos
	 */

	no -> operacao = OP_OBJSET;
	no -> dir = NULL;
	no -> esq = no -> esq -> esq;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Gera um conjunto de objetos a partir de seus componentes		*
 *									*
 ************************************************************************
 */

static int pega_conjunto_objetos (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, EXPRESSAO *no)
{
	if (no -> operacao == OP_VARIAVEL)
		return (pega_conjunto_objetos_identificador (objeto, no));

	if (no -> operacao == OP_CLSSET)
		return (pega_conjunto_objetos_conjunto_classe (metamodelo, modelo, objeto, no));

	if (no -> operacao == OP_BOUND)
		return (pega_conjunto_objetos_bound (metamodelo, modelo, objeto, no));

	return (FALSE);
}


/*
 ************************************************************************
 *									*
 * Gera o código da expressão de função de grupo			*
 *									*
 ************************************************************************
 */

static int gera_codigo_expressao_funcao (int operacao, int separador, METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, EXPRESSAO *no)
{
	EXPRESSAO	*elementos, *expressao, *walker;
	char		*idcampo = no -> dir -> nome;
	char		nome[200];


	/*
	 * Cria o conjunto de objetos relacionados com o operador de funcao
	 */

	if (!pega_conjunto_objetos (metamodelo, modelo, objeto, no -> esq))
		return (FALSE);


	/*
	 * Verifica se o conjunto contém algum objeto
	 */

	elementos = no -> esq -> esq;

	if (elementos == NULL)
	{
		no -> operacao = OP_CONSTANTE;
		no -> valor = 0.0;
		libera_expressao (no -> dir);
		libera_expressao (no -> esq);
		no -> esq = no -> dir = NULL;
		return (TRUE);
	}

	
	/*
	 * Percorre os elementos da relação, imprimindo a função
	 */

	expressao = walker = NULL;

	while (elementos != NULL)
	{
		if (elementos -> operacao == OP_COMMA)
		{
			sprintf (nome, "%s_%s", elementos -> dir -> nome, idcampo);
		}
		else if (elementos -> operacao == OP_VARIAVEL)
		{
			sprintf (nome, "%s_%s", elementos -> nome, idcampo);
		}

		expressao = constroi_no_variavel (nome);

		if (walker != NULL)
			expressao = constroi_no (separador, walker, expressao);

		walker = expressao;
		elementos = elementos -> esq;
	}

	no -> operacao = operacao;
	libera_expressao (no -> dir);
	libera_expressao (no -> esq);
	no -> dir = NULL;
	no -> esq = expressao;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Conta o número de elementos em um conjunto de objetos		*
 *									*
 ************************************************************************
 */

static int pega_numero_elementos_conjunto (EXPRESSAO *no)
{
	if (no == NULL)
		return (0);

	if (no -> operacao == OP_COMMA)
		return (pega_numero_elementos_conjunto (no -> dir) + pega_numero_elementos_conjunto (no -> esq));

	return (1);
}


/*
 ************************************************************************
 *									*
 * Gera o código da expressão COUNT					*
 *									*
 ************************************************************************
 */

static int gera_codigo_expressao_contagem (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, EXPRESSAO *no)
{
	/*
	 * Cria o conjunto de objetos relacionados com o operador de contagem
	 */

	if (!pega_conjunto_objetos (metamodelo, modelo, objeto, no -> esq))
		return (FALSE);


	/*
	 * Imprime o número de elementos da relação
	 */

	no -> operacao = OP_CONSTANTE;
	no -> valor = pega_numero_elementos_conjunto (no -> esq -> esq);
	libera_expressao (no -> dir);
	libera_expressao (no -> esq);
	no -> esq = no -> dir = NULL;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Gera o código da expressão PONTO					*
 *									*
 ************************************************************************
 */

static int gera_codigo_expressao_ponto (OBJETO *objeto, EXPRESSAO *no, OBJETO *relac)
{
	RELMODELO	*relmodelo;
	char		*nomeobjeto;
	char		*idrelacao;
	char		*idcampo;
	char		nome[200];


	idrelacao = no -> esq -> nome;
	idcampo = no -> dir -> nome;

	relmodelo = pega_relacao_modelo_nome (objeto, idrelacao);

	if (relmodelo == NULL)
	{
		no -> operacao = OP_CONSTANTE;
		no -> valor = 0.0;
		libera_expressao (no -> dir);
		libera_expressao (no -> esq);
		no -> esq = no -> dir = NULL;
		return (TRUE);
	}

	if (relmodelo -> elementos -> operacao == OP_VARIAVEL)
		nomeobjeto = relmodelo -> elementos -> nome;
	else
		nomeobjeto = relac -> id;

	sprintf (nome, "%s_%s", nomeobjeto, idcampo);

	no -> operacao = OP_VARIAVEL;
	no -> nome = strdup (nome);
	libera_expressao (no -> dir);
	libera_expressao (no -> esq);
	no -> esq = no -> dir = NULL;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Gera o código da equação de um comportamento do modelo		*
 *									*
 ************************************************************************
 */

//
// PENDENTE: PARA FAZER O SELECT FUNCIONAR, TENHO QUE MEXER POR AQUI !
//

static int processa_expressao (METAMODELO *metamodelo, MODELO *modelo, EXPRESSAO *no, OBJETO *objeto, OBJETO *relac)
{
	char	nome[200];

	switch (no -> operacao)
	{
	    case OP_VARIAVEL:
		sprintf (nome, "%s_%s", objeto -> id, no -> nome);
		no -> nome = strdup (nome);
		return (TRUE);

	    case OP_PONTO:
		return (gera_codigo_expressao_ponto (objeto, no, relac));

	    case OP_GRUPO_SOMA:
		return (gera_codigo_expressao_funcao (OP_PARENTESES, OP_SOMA, metamodelo, modelo, objeto, no));

	    case OP_GRUPO_MAX:
		return (gera_codigo_expressao_funcao (OP_MAX, OP_COMMA, metamodelo, modelo, objeto, no));

	    case OP_GRUPO_MIN:
		return (gera_codigo_expressao_funcao (OP_MIN, OP_COMMA, metamodelo, modelo, objeto, no));

	    case OP_COUNT:
		return (gera_codigo_expressao_contagem (metamodelo, modelo, objeto, no));
	}

	if (no -> esq != NULL)
		if (!processa_expressao (metamodelo, modelo, no -> esq, objeto, relac))
			return (FALSE);

	if (no -> dir != NULL)
		if (!processa_expressao (metamodelo, modelo, no -> dir, objeto, relac))
			return (FALSE);

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Gera o código do comportamento de uma taxa em um modelo		*
 *									*
 ************************************************************************
 */

static int gera_codigo_taxa (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, COMPORTAMENTO *rate)
{
	char		*nomeobjeto;
	char		*nome_relacao;
	char		*nome_repositorio;
	char		nomestk[200], nome[200];
	OBJETO		*relac;
	EXPRESSAO	*walker;
	RELMODELO	*relacao;
	EXPRESSAO	*repositorio;
	EXPRESSAO	*expressao;
	int		contador;



	/*
	 * Se a taxa for simples, gera o código para o seu repositório
	 */
	
	if (rate -> repositorio -> operacao == OP_VARIAVEL)
	{
		sprintf (nomestk, "%s_%s", objeto -> id, rate -> repositorio -> nome);

		if ((repositorio = constroi_no_variavel (nomestk)) == NULL)
		{
			yyerror ("Out of memory");
			return (FALSE);
		}

		if ((expressao = clona_expressao (rate -> expressao)) == NULL)
		{
			yyerror ("Out of memory");
			return (FALSE);
		}

		if (!processa_expressao (metamodelo, modelo, expressao, objeto, NULL))
			return (FALSE);

		sprintf (nome, "%s_%s", objeto -> id, rate -> id);

		return (adiciona_equacao (TP_RATE, nome, repositorio, expressao));
	}


	/*
	 * Se a taxa for múltipla, pega o nome do relacionamento e do repositório
	 */

	nome_relacao = rate -> repositorio -> esq -> nome;
	nome_repositorio = rate -> repositorio -> dir -> nome;


	/*
	 * Percorre os objetos do relacionamento no objeto, gerando a taxa
	 */

	relacao = pega_relacao_modelo_nome (objeto, nome_relacao);
	walker = relacao -> elementos;
	contador = 1;

	while (walker != NULL)
	{
		if (walker -> operacao == OP_COMMA)
			nomeobjeto = walker -> esq -> nome;
		else
			nomeobjeto = walker -> nome;

		relac = pega_objeto_nome (modelo, nomeobjeto);

		sprintf (nomestk, "%s_%s", nomeobjeto, nome_repositorio);

		if ((repositorio = constroi_no_variavel (nomestk)) == NULL)
			return (yyerror ("Out of memory"));

		if ((expressao = clona_expressao (rate -> expressao)) == NULL)
			return (yyerror ("Out of memory"));

		if (!processa_expressao (metamodelo, modelo, expressao, objeto, relac))
			return (FALSE);

		sprintf (nome, "%s_%s%d", objeto -> id, rate -> id, contador);

		if (!adiciona_equacao (TP_RATE, nome, repositorio, expressao))
			return (FALSE);

		walker = walker -> dir;
		contador++;
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Gera código para um comportamento de um objeto			*
 *									*
 ************************************************************************
 */

static int gera_codigo_comportamento (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, COMPORTAMENTO *comp)
{
	EXPRESSAO	*expressao;
	char		nome[100];


	if (comp -> tipo == TP_RATE)
		return (gera_codigo_taxa (metamodelo, modelo, objeto, comp));

	if ((expressao = clona_expressao (comp -> expressao)) == NULL)
		return (yyerror ("Out of memory"));

	if (!processa_expressao (metamodelo, modelo, expressao, objeto, NULL))
		return (FALSE);

	sprintf (nome, "%s_%s", objeto -> id, comp -> id);

	return (adiciona_equacao (comp -> tipo, nome, NULL, expressao));
}


/*
 ************************************************************************
 *									*
 * Gera código para uma propriedade de um objeto			*
 *									*
 ************************************************************************
 */

static int gera_codigo_propriedade (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto, PROPRIEDADE *prop)
{
	EXPRESSAO	*expressao;
	char		nome[100];


	sprintf (nome, "%s_%s", objeto -> id, prop -> id);

	if ((expressao = constroi_no_constante (prop -> valor)) == NULL)
	{
		yyerror ("Out of memory");
		return (FALSE);
	}

	return (adiciona_equacao (TP_PROC, nome, NULL, expressao));
}


/*
 ************************************************************************
 *									*
 * Gera o código de um objeto do modelo					*
 *									*
 ************************************************************************
 */

static int gera_codigo_objeto (METAMODELO *metamodelo, MODELO *modelo, OBJETO *objeto)
{
	COMPORTAMENTO	*comportamento;
	PROPRIEDADE	*propriedade;
	char		s[2049];
	int		i;


	/*
	 * Gera o comentário do objeto do modelo
	 */

	sprintf (s, "\n# Code for object \"%s\"", objeto -> id);

	if (!adiciona_comentario (s))
		return (FALSE);


	/*
	 * Gera código para as propriedades do objeto
	 */

	for (i = 0; i < pega_numero_itens (&objeto -> propriedades); i++)
	{
		propriedade = pega_item (&objeto -> propriedades, i);

		if (!gera_codigo_propriedade (metamodelo, modelo, objeto, propriedade))
			return (FALSE);
	}


	/*
	 * Gera código para os comportamentos do objeto
	 */

	for (i = 0; i < pega_numero_itens (&objeto -> comportamentos); i++)
	{
		comportamento = pega_item (&objeto -> comportamentos, i);

		if (!gera_codigo_comportamento (metamodelo, modelo, objeto, comportamento))
			return (FALSE);
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Gera o arquivo de saída, com o código de simulação do modelo		*
 *									*
 ************************************************************************
 */

int gera_arquivo_saida (char *nome_arquivo, int otimizacoes, METAMODELO *metamodelo, MODELO *modelo)
{
	OBJETO		*objeto;
	int		otimizados, i;


	/*
	 * Se não houver modelo ou metamodelo, retorna com erro
	 */

	if (metamodelo == NULL)
		return (yyerror ("There is no metamodel to compile"));

	if (modelo == NULL)
		return (yyerror ("There is no model to compile"));


	/*
	 * Copia as propriedades das classes e cenários para os objetos
	 */

	if (!copia_propriedades_objetos (metamodelo, modelo))
		return (FALSE);


	/*
	 * Copia os comportamentos das classes e cenários para os objetos
	 */

	if (!copia_comportamentos_objetos (metamodelo, modelo))
		return (FALSE);


	/*
	 * Resolve os ajustes de cenários aplicados sobre os objetos
	 */

	resolve_ajustes (metamodelo, modelo);


	/*
	 * Prepara a geração das equações que descrevem o código compilado do modelo
	 */

	prepara_equacoes ();


	/*
	 * Percorre os objetos do modelo, gerando seu código
	 */

	for (i = 0; i < pega_numero_itens (&modelo -> objetos); i++)
	{
		objeto = pega_item (&modelo -> objetos, i);
		
		if (!gera_codigo_objeto (metamodelo, modelo, objeto))
			return (FALSE);
	}


	/*
	 * Realiza as otimizações disponíveis
	 */

	for (; otimizacoes > 0; otimizacoes--)
	{
		otimizados = otimiza_equacoes_constantes ();
		otimizados += otimiza_expressoes_constantes ();

		if (otimizados == 0)
			break;
	}


	/*
	 * Publica as equações no arquivo texto
	 */

	if (!publica_equacoes (nome_arquivo, modelo -> passo))
		return (FALSE);


	/*
	 * Libera a memória consumida pelas equações
	 */

	libera_equacoes ();
	return (TRUE);
}