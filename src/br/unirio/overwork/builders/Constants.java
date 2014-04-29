package br.unirio.overwork.builders;

/**
 * 
 * 
 * REFERÊNCIAS:
 * 
 * Jones, C., 2007, Estimating Software Costs: Bringing Realism to Estimating
 * McGraw-Hill Osborne Media; 2nd edition, ISBN: 0071483004
 * 
 * Abdel-Hamid, T., Madnick, S., 1991, Software Project Dynamics: An Integrated Approach
 * Prentice Hall; 1st edition, ISBN: 0138220409
 * 
 * @author Marcio Barros
 */
public interface Constants
{
	/**
	 * Number of working-days in a month
	 */
	public static final double DAYS_IN_MONTH = 21;
	
	/**
	 * Base number of requirement errors per function point (Jones, 2007, pg 114)
	 */
	public static final double REQUIREMENT_ERRORS_FP = 1.0; 
	
	/**
	 * Base number of design errors per function point (see regeneration below)
	 */
	public static final double DESIGN_ERRORS_FP = 1.0; 
	
	/**
	 * Base number of coding errors per function point (see regeneration below)
	 */
	public static final double CODIGN_ERRORS_FP = 1.0; 
	
	/**
	 * Abdel-Hamid and Madnick (1991) introduce the concept of error regeneration
	 * through which active undetected errors produced by one activity give birth 
	 * to new errors in succeeding activities. All requirement errors are active,
	 * serving as blueprints for design activities.
	 * 
	 * In normal circumstances, a requirement activity creates 1 error/FP (Jones, 
	 * 2007, pg 114). A design activity, on its turn, creates 1 error/FP and 
	 * regenerates 25% of the errors it receives from requirement activities,
	 * thus leading to a total of 1.25 errors/FP (Jones, 2007, pg 114).
	 */
	public static final double DESIGN_ERROR_REGENERATION = 0.25;
	
	/**
	 * In normal circumstances, a design activity creates 1.25 error/FP (Jones, 
	 * 2007, pg 114), which accumulates with 1 error/FP from requirements. A
	 * coding activity regenerates 33% of the errors it receives from design,
	 * thus leading to a total of 1.75 errors/FP (Jones, 2007, pg 114).
	 */
	public static final double CODING_ERROR_REGENERATION = 0.33;
}