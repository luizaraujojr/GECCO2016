package br.unirio.sd.c_common;
/*
 ************************************************************************
 *									*
 * Modulo: EQUACAOPUBLICA						*
 *									*
 * Funcao: Publica as equações de um modelo compilado			*
 *									*
 ************************************************************************
 */

#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include "Common.h"
#include "Arvore.h"
#include "Lexico.h"
#include "VetorDinamico.h"
#include "MetaRegistro.h"
#include "Equacao.h"
#include "EquacaoPublica.h"


/*
 ************************************************************************
 *									*
 * Imprime uma expressão de uma equação					*
 *									*
 ************************************************************************
 */

static int imprime_expressao (FILE *handle, EXPRESSAO *no)
{
	switch (no -> operacao)
	{
	    case OP_VARIAVEL:
		fprintf (handle, "%s", no -> nome);
		break;

	    case OP_CONSTANTE:
		fprintf (handle, "%.6f", no -> valor);
		break;

	    case OP_SOMA:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " + ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_SUBTRACAO:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " - ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_PRODUTO:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " * ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_DIVISAO:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " / ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_POTENC:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " ^ ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_IGUAL:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " = ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_DIFERENTE:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " <> ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_MAIOR:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " > ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_MAIORIG:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " >= ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_MENOR:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " < ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_MENORIG:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, " <= ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_MAX:
		fprintf (handle, "MAX (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_MIN:
		fprintf (handle, "MIN (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_IF:
		fprintf (handle, "IF (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_LOOKUP:
		fprintf (handle, "LOOKUP (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_AND:
		fprintf (handle, "AND (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_OR:
		fprintf (handle, "OR (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_NOT:
		fprintf (handle, "NOT (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_UMINUS:
		fprintf (handle, " -");
		imprime_expressao (handle, no -> esq);
		break;

	    case OP_PARENTESES:
		fprintf (handle, "(");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_NORMAL:
		fprintf (handle, "NORMAL (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ", ");
		imprime_expressao (handle, no -> dir);
		fprintf (handle, ")");
		break;

	    case OP_UNIFORM:
		fprintf (handle, "UNIFORM (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ", ");
		imprime_expressao (handle, no -> dir);
		fprintf (handle, ")");
		break;

	    case OP_BETAPERT:
		fprintf (handle, "BetaPERT (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ", ");
		imprime_expressao (handle, no -> dir);
		fprintf (handle, ")");
		break;

	    case OP_ROUND:
		fprintf (handle, "ROUND (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_EXPN:
		fprintf (handle, "EXP (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_LOGN:
		fprintf (handle, "LN (");
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ")");
		break;

	    case OP_COMMA:
		imprime_expressao (handle, no -> esq);
		fprintf (handle, ", ");
		imprime_expressao (handle, no -> dir);
		break;

	    case OP_DT:
		fprintf (handle, "DT");
		break;

	    case OP_TIME:
		fprintf (handle, "TIME");
		break;

	    default:
		fprintf (handle, "<Expression not found!>");
		break;
	}

	return (TRUE);
}


/*
 ************************************************************************
 *									*
 * Imprime as características de uma equação				*
 *									*
 ************************************************************************
 */

static void publica_equacao (FILE *handle, EQUACAO *equacao)
{
	switch (equacao -> tipo)
	{
	    case TP_STOCK:
		fprintf (handle, "STOCK %s ", equacao -> id);
		break;

	    case TP_RATE:
		fprintf (handle, "RATE  (SOURCE, %s) %s ", equacao -> repositorio -> nome, equacao -> id);
		break;

	    case TP_PROC:
		fprintf (handle, "PROC %s ", equacao -> id);
		break;

	    case TP_TABLE:
		fprintf (handle, "TABLE %s ", equacao -> id);
		break;
	}
	
	imprime_expressao (handle, equacao -> expressao);
	fprintf (handle, ";\n");
}


/*
 ************************************************************************
 *									*
 * Publica as equações em um arquivo					*
 *									*
 ************************************************************************
 */

int publica_equacoes (char *nome_arquivo, double passo)
{
	EQUACAO		*equacao;
	FILE		*handle;
	int		i;


	/*
	 * Abre o arquivo de saída
	 */

	if ((handle = fopen (nome_arquivo, "wt")) == NULL)
		return (yyerror ("Error opening the output file"));


	/*
	 * Gera o código do passo de simulação
	 */

	fprintf (handle, "# Simulation step\n");
	fprintf (handle, "SPEC DT %.4f;\n", passo);


	/*
	 * Gera o código de cada equação registrada
	 */

	for (i = 0; i < pega_numero_equacoes(); i++)
	{
		equacao = pega_equacao_indice (i);

		if (equacao -> tipo == TP_COMMENT)
		{
			if (i == pega_numero_equacoes()-1  ||  pega_equacao_indice(i+1) -> tipo != TP_COMMENT)
				fprintf (handle, "%s\n", equacao -> comentario);
		}
		else
		{
			publica_equacao (handle, equacao);
		}
	}


	/*
	 * Fecha o arquivo de saída
	 */

	fclose (handle);
	return (TRUE);
}