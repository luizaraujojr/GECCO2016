
/*
 * Metamodelo e modelo registrados na aplicação
 */

METAMODELO	*metamodelo = NULL;

MODELO		*modelo = NULL;


/*
 * Rotinas externas
 */

extern int yyparse (void);
extern int yynerrs;


/*
 ************************************************************************
 *									*
 * Registra um cenario em um metamodelo					*
 *									*
 ************************************************************************
 */

int insere_cenario_metamodelo (char *idmeta, CENARIO *cenario)
{
	if (metamodelo == NULL)
		return (FALSE);

	if (!nome_valido_cenario (metamodelo, cenario -> id))
	{
		free (cenario);
		return (yyerror ("Invalid name for a scenario"));
	}

	insere_item (&metamodelo -> cenarios, cenario);
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Registra um metamodelo						*
 *									*
 ************************************************************************
 */

int muda_metamodelo (METAMODELO *ometa)
{
	metamodelo = ometa;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Altera o modelo sendo utilizado pela aplicação			*
 *									*
 ************************************************************************
 */

int muda_modelo (MODELO *omodelo)
{
	modelo = omodelo;
	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Processa um arquivo com equacoes					*
 *									*
 ************************************************************************
 */

int processa_arquivo (char *arquivo)
{	
	int		res;


	/*
	 * Inicializa a tabela de variaveis e o analisador lexico
	 */

	if (!inicializa_lexico (arquivo))
	{
		mensagem (ERRO, "%s: file not found", arquivo);
		return (FALSE);
	}


	/*
	 * Executa o analisador sintatico
	 */

	res = yyparse ();


	/*
	 * Encerra o analisador lexico
	 */

	encerra_lexico ();


	/*
	 * Verifica se o analisador sintatico foi executado com sucesso
	 */

	if (res != 0  ||  yynerrs > 0)
		return (FALSE);

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Programa principal							*
 *									*
 ************************************************************************
 */

#ifndef DLL

void main (int argc, char *argv[])
{
	char	buffer[2049];

	
	/*
	 * Testa o número de parâmetros recebidos
	 */

	printf ("METASD - Meta modelo de dinamica de sistemas\n");

	if (argc != 3)
	{
		printf ("\t Sintaxe: metasd <arquivo_entrada> <arquivo_saida>\n\n");
		return;
	}


	/*
	 * Processa o arquivo de entrada, gerando o arquivo de saída
	 */

	if (carrega_modelo (argv[1])  &&  modelo != NULL)
		gera_arquivo_saida (argv[2], metamodelo, modelo);


	/*
	 * Imprime o conteúdo do arquivo
	 */

	if (!verifica_erros ())
	{
		imprime_metamodelo (metamodelo);
		printf ("\n");

		if (modelo != NULL)
		{
			imprime_modelo (modelo);
			printf ("\n");
		}
	}


	/*
	 * Imprime as mensagens de erro, se houver alguma
	 */

	inicializa_percurso_mensagens ();

	while (pega_proxima_mensagem (buffer))
		printf ("%s\n", buffer);

	printf ("\n");
	getchar ();
}

#endif