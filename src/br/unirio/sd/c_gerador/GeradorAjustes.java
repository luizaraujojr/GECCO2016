
/*
 ************************************************************************
 *									*
 * Resolve os ajustes de uma conexão de modelo				*
 *									*
 ************************************************************************
 */

static void resolve_ajustes_conexao (METAMODELO *metamodelo, MODELO *modelo, CENARIO *cenario, CONMODELO *conmodelo)
{
	EXPRESSAO	*no, *expressao;
	CONEXAO		*conexao;
	OBJETO		*objeto;
	COMPORTAMENTO	*comp;
	AJUSTE		*ajuste;
	int		i;


	objeto = pega_objeto_nome (modelo, conmodelo -> idobjeto);

	conexao = pega_conexao_nome (cenario, conmodelo -> idconexao);

	for (i = 0; i < pega_numero_itens (&conexao -> ajustes); i++)
	{
		ajuste = pega_item (&conexao -> ajustes, i);

		comp = pega_comportamento_compilacao_nome (objeto, ajuste -> id);

		expressao = clona_expressao (ajuste -> expressao);

		no = pega_no_variavel (expressao, ajuste -> id);

		no -> operacao = OP_PARENTESES;
		no -> esq = comp -> expressao;
		no -> dir = NULL;
		comp -> expressao = expressao;
	}
}


/*
 ************************************************************************
 *									*
 * Resolve os ajustes de uma ativação de cenário			*
 *									*
 ************************************************************************
 */

static void resolve_ajustes_ativacao (METAMODELO *metamodelo, MODELO *modelo, ATIVACAO *ativacao)
{
	CONMODELO	*conmodelo;
	CENARIO		*cenario;
	int		i;


	cenario = pega_cenario_nome (metamodelo, ativacao -> idcenario);

	for (i = 0; i < pega_numero_itens (&ativacao -> conexoes); i++)
	{
		conmodelo = pega_item (&ativacao -> conexoes, i);
		resolve_ajustes_conexao (metamodelo, modelo, cenario, conmodelo);
	}
}


/*
 ************************************************************************
 *									*
 * Resolve os ajustes dos cenários ativados sobre um modelo		*
 *									*
 ************************************************************************
 */

void resolve_ajustes (METAMODELO *metamodelo, MODELO *modelo)
{
	ATIVACAO	*ativacao;
	int		i;


	for (i = 0; i < pega_numero_itens (&modelo -> ativacoes); i++)
	{
		ativacao = pega_item (&modelo -> ativacoes, i);
		resolve_ajustes_ativacao (metamodelo, modelo, ativacao);
	}
}