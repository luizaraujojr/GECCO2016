package br.unirio.sd.control.parser;

import br.unirio.sd.model.common.Expressao;

/**
 * BYACC/J Semantic Value for parser: Parser This class provides some of the
 * functionality of the yacc/C 'union' directive
 */
public class ParserVal
{
	/**
	 * int value of this 'union'
	 */
	public int ival;

	/**
	 * double value of this 'union'
	 */
	public double dval;

	/**
	 * string value of this 'union'
	 */
	public String sval;

	/**
	 * object value of this 'union'
	 */
	public Expressao obj;

	/**
	 * Initialize me without a value
	 */
	public ParserVal()
	{
	}

	/**
	 * Initialize me as a double
	 */
	public ParserVal(double val)
	{
		dval = val;
	}

	/**
	 * Initialize me as a string
	 */
	public ParserVal(String val)
	{
		sval = val;
	}

	/**
	 * Initialize me as an Object
	 */
	public ParserVal(Expressao val)
	{
		obj = val;
	}
}