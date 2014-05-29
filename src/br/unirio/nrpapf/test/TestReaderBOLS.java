package br.unirio.nrpapf.test;

import junit.framework.TestCase;
import br.unirio.nrpapf.calculator.FunctionPointCalculator;
import br.unirio.nrpapf.model.FunctionPointSystem;
import br.unirio.nrpapf.reader.InstanceReader;
import br.unirio.nrpapf.report.Report;

public class TestReaderBOLS extends TestCase
{
	public void testAll() throws Exception
	{
		InstanceReader reader = new InstanceReader();
		FunctionPointSystem fps = reader.run("data/nrp-apf/BOLS/functions-point.xml");
		Report report = new FunctionPointCalculator().calculate(fps);

		/* Data Functions */

		assertEquals(4, report.getDataFunctionName("DF0000").getRets());
		assertEquals(46, report.getDataFunctionName("DF0000").getDets());

		assertEquals(1, report.getDataFunctionName("DF0001").getRets());
		assertEquals(6, report.getDataFunctionName("DF0001").getDets());

		assertEquals(5, report.getDataFunctionName("DF0002").getRets());
		assertEquals(90, report.getDataFunctionName("DF0002").getDets());

		assertEquals(1, report.getDataFunctionName("DF0003").getRets());
		assertEquals(6, report.getDataFunctionName("DF0003").getDets());

		assertEquals(2, report.getDataFunctionName("DF0004").getRets());
		assertEquals(5, report.getDataFunctionName("DF0004").getDets());

		assertEquals(1, report.getDataFunctionName("DF0005").getRets());
		assertEquals(7, report.getDataFunctionName("DF0005").getDets());

		assertEquals(1, report.getDataFunctionName("DF0006").getRets());
		assertEquals(8, report.getDataFunctionName("DF0006").getDets());

		assertEquals(1, report.getDataFunctionName("DF0007").getRets());
		assertEquals(8, report.getDataFunctionName("DF0007").getDets());

		assertEquals(1, report.getDataFunctionName("DF0008").getRets());
		assertEquals(10, report.getDataFunctionName("DF0008").getDets());

		assertEquals(1, report.getDataFunctionName("DF0009").getRets());
		assertEquals(6, report.getDataFunctionName("DF0009").getDets());

		assertEquals(1, report.getDataFunctionName("DF0010").getRets());
		assertEquals(7, report.getDataFunctionName("DF0010").getDets());

		assertEquals(2, report.getDataFunctionName("DF0011").getRets());
		assertEquals(17, report.getDataFunctionName("DF0011").getDets());

		assertEquals(1, report.getDataFunctionName("DF0012").getRets());
		assertEquals(7, report.getDataFunctionName("DF0012").getDets());

		assertEquals(1, report.getDataFunctionName("DF0013").getRets());
		assertEquals(9, report.getDataFunctionName("DF0013").getDets());

		assertEquals(1, report.getDataFunctionName("DF0014").getRets());
		assertEquals(18, report.getDataFunctionName("DF0014").getDets());

		assertEquals(1, report.getDataFunctionName("DF0015").getRets());
		assertEquals(12, report.getDataFunctionName("DF0015").getDets());

		assertEquals(1, report.getDataFunctionName("DF0016").getRets());
		assertEquals(11, report.getDataFunctionName("DF0016").getDets());

		assertEquals(1, report.getDataFunctionName("DF0017").getRets());
		assertEquals(8, report.getDataFunctionName("DF0017").getDets());

		assertEquals(1, report.getDataFunctionName("DF0018").getRets());
		assertEquals(10, report.getDataFunctionName("DF0018").getDets());

		assertEquals(1, report.getDataFunctionName("DF0019").getRets());
		assertEquals(24, report.getDataFunctionName("DF0019").getDets());

		assertEquals(1, report.getDataFunctionName("DF0020").getRets());
		assertEquals(5, report.getDataFunctionName("DF0020").getDets());

		assertEquals(1, report.getDataFunctionName("DF0021").getRets());
		assertEquals(3, report.getDataFunctionName("DF0021").getDets());

		assertEquals(1, report.getDataFunctionName("DF0022").getRets());
		assertEquals(5, report.getDataFunctionName("DF0022").getDets());

		assertEquals(1, report.getDataFunctionName("DF0023").getRets());
		assertEquals(13, report.getDataFunctionName("DF0023").getDets());

		assertEquals(1, report.getDataFunctionName("DF0024").getRets());
		assertEquals(11, report.getDataFunctionName("DF0024").getDets());

		assertEquals(1, report.getDataFunctionName("DF0025").getRets());
		assertEquals(11, report.getDataFunctionName("DF0025").getDets());

		assertEquals(1, report.getDataFunctionName("DF0026").getRets());
		assertEquals(8, report.getDataFunctionName("DF0026").getDets());

		assertEquals(1, report.getDataFunctionName("DF0027").getRets());
		assertEquals(5, report.getDataFunctionName("DF0027").getDets());

		assertEquals(1, report.getDataFunctionName("DF0028").getRets());
		assertEquals(6, report.getDataFunctionName("DF0028").getDets());

		assertEquals(1, report.getDataFunctionName("DF0029").getRets());
		assertEquals(7, report.getDataFunctionName("DF0029").getDets());

		assertEquals(1, report.getDataFunctionName("DF0030").getRets());
		assertEquals(6, report.getDataFunctionName("DF0030").getDets());

		assertEquals(1, report.getDataFunctionName("DF0031").getRets());
		assertEquals(9, report.getDataFunctionName("DF0031").getDets());

		assertEquals(1, report.getDataFunctionName("DF0032").getRets());
		assertEquals(6, report.getDataFunctionName("DF0032").getDets());

		assertEquals(1, report.getDataFunctionName("DF0033").getRets());
		assertEquals(7, report.getDataFunctionName("DF0033").getDets());

		assertEquals(1, report.getDataFunctionName("DF0034").getRets());
		assertEquals(5, report.getDataFunctionName("DF0034").getDets());

		assertEquals(1, report.getDataFunctionName("DF0035").getRets());
		assertEquals(8, report.getDataFunctionName("DF0035").getDets());

		assertEquals(1, report.getDataFunctionName("DF0036").getRets());
		assertEquals(5, report.getDataFunctionName("DF0036").getDets());

		assertEquals(1, report.getDataFunctionName("DF0037").getRets());
		assertEquals(5, report.getDataFunctionName("DF0037").getDets());

		assertEquals(1, report.getDataFunctionName("DF0038").getRets());
		assertEquals(6, report.getDataFunctionName("DF0038").getDets());

		assertEquals(1, report.getDataFunctionName("DF0039").getRets());
		assertEquals(3, report.getDataFunctionName("DF0039").getDets());

		/* Transaction Functions */

		assertEquals(1, report.getTransactionFunctionName("TF0000").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0000").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0001").getFtrs());
		assertEquals(22, report.getTransactionFunctionName("TF0001").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0002").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0002").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0003").getFtrs());
		assertEquals(32, report.getTransactionFunctionName("TF0003").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0004").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0004").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0005").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("TF0005").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0006").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0006").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0007").getFtrs());
		assertEquals(18, report.getTransactionFunctionName("TF0007").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0008").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0008").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0009").getFtrs());
		assertEquals(18, report.getTransactionFunctionName("TF0009").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0010").getFtrs());
		assertEquals(18, report.getTransactionFunctionName("TF0010").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0011").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0011").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0012").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0012").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0013").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0013").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0014").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0014").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0015").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("TF0015").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0016").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("TF0016").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0017").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("TF0017").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0018").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("TF0018").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0019").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("TF0019").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0020").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("TF0020").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0021").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0021").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0022").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0022").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0023").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0023").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0024").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0024").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0025").getFtrs());
		assertEquals(13, report.getTransactionFunctionName("TF0025").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0026").getFtrs());
		assertEquals(16, report.getTransactionFunctionName("TF0026").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0027").getFtrs());
		assertEquals(17, report.getTransactionFunctionName("TF0027").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0028").getFtrs());
		assertEquals(25, report.getTransactionFunctionName("TF0028").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0029").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0029").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0030").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0030").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0031").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0031").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0032").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0032").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0033").getFtrs());
		assertEquals(18, report.getTransactionFunctionName("TF0033").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0034").getFtrs());
		assertEquals(18, report.getTransactionFunctionName("TF0034").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0035").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0035").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0036").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0036").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0037").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0037").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0038").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0038").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0039").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0039").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0040").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0040").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0041").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("TF0041").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0042").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0042").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0043").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0043").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0044").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0044").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0045").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0045").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0046").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0046").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0047").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0047").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0048").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0048").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0049").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0049").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0050").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0050").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0051").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("TF0051").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0052").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0052").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0053").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0053").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0054").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0054").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0055").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0055").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0056").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0056").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0057").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0057").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0058").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0058").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0059").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0059").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0060").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("TF0060").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0061").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("TF0061").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0062").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0062").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0063").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0063").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0064").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("TF0064").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0065").getFtrs());
		assertEquals(16, report.getTransactionFunctionName("TF0065").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0066").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0066").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0067").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("TF0067").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0068").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("TF0068").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0069").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("TF0069").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0070").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0070").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0071").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("TF0071").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0072").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0072").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0073").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("TF0073").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0074").getFtrs());
		assertEquals(2, report.getTransactionFunctionName("TF0074").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0075").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0075").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0076").getFtrs());
		assertEquals(26, report.getTransactionFunctionName("TF0076").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0077").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0077").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0078").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0078").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0079").getFtrs());
		assertEquals(23, report.getTransactionFunctionName("TF0079").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0080").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0080").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0081").getFtrs());
		assertEquals(16, report.getTransactionFunctionName("TF0081").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0082").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0082").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0083").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0083").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0084").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0084").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0085").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0085").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0086").getFtrs());
		assertEquals(21, report.getTransactionFunctionName("TF0086").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0087").getFtrs());
		assertEquals(21, report.getTransactionFunctionName("TF0087").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0088").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0088").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0089").getFtrs());
		assertEquals(3, report.getTransactionFunctionName("TF0089").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0090").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0090").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0091").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("TF0091").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0092").getFtrs());
		assertEquals(6, report.getTransactionFunctionName("TF0092").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0093").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("TF0093").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0094").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0094").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0095").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0095").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0096").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0096").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0097").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0097").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0098").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("TF0098").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0099").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0099").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0100").getFtrs());
		assertEquals(13, report.getTransactionFunctionName("TF0100").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0101").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0101").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0102").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0102").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0103").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0103").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0104").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0104").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0105").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0105").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0106").getFtrs());
		assertEquals(16, report.getTransactionFunctionName("TF0106").getDets());

		assertEquals(6, report.getTransactionFunctionName("TF0107").getFtrs());
		assertEquals(42, report.getTransactionFunctionName("TF0107").getDets());

		assertEquals(6, report.getTransactionFunctionName("TF0108").getFtrs());
		assertEquals(40, report.getTransactionFunctionName("TF0108").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0109").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0109").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0110").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0110").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0111").getFtrs());
		assertEquals(28, report.getTransactionFunctionName("TF0111").getDets());

		assertEquals(0, report.getTransactionFunctionName("TF0112").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0112").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0113").getFtrs());
		assertEquals(13, report.getTransactionFunctionName("TF0113").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0114").getFtrs());
		assertEquals(18, report.getTransactionFunctionName("TF0114").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0115").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0115").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0116").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0116").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0117").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0117").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0118").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0118").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0119").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0119").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0120").getFtrs());
		assertEquals(19, report.getTransactionFunctionName("TF0120").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0121").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0121").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0122").getFtrs());
		assertEquals(29, report.getTransactionFunctionName("TF0122").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0123").getFtrs());
		assertEquals(35, report.getTransactionFunctionName("TF0123").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0124").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0124").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0125").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0125").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0126").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0126").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0127").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0127").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0128").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("TF0128").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0129").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0129").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0130").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0130").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0131").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0131").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0132").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0132").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0133").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0133").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0134").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("TF0134").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0135").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("TF0135").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0136").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("TF0136").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0137").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("TF0137").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0138").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("TF0138").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0139").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("TF0139").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0140").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("TF0140").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0141").getFtrs());
		assertEquals(9, report.getTransactionFunctionName("TF0141").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0142").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("TF0142").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0143").getFtrs());
		assertEquals(7, report.getTransactionFunctionName("TF0143").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0144").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0144").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0145").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0145").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0146").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0146").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0147").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0147").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0148").getFtrs());
		assertEquals(16, report.getTransactionFunctionName("TF0148").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0149").getFtrs());
		assertEquals(17, report.getTransactionFunctionName("TF0149").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0150").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0150").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0151").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0151").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0152").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0152").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0153").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0153").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0154").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0154").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0155").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0155").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0156").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0156").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0157").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0157").getDets());

		assertEquals(1, report.getTransactionFunctionName("TF0158").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0158").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0159").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0159").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0160").getFtrs());
		assertEquals(4, report.getTransactionFunctionName("TF0160").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0161").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0161").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0162").getFtrs());
		assertEquals(5, report.getTransactionFunctionName("TF0162").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0163").getFtrs());
		assertEquals(8, report.getTransactionFunctionName("TF0163").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0164").getFtrs());
		assertEquals(20, report.getTransactionFunctionName("TF0164").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0165").getFtrs());
		assertEquals(21, report.getTransactionFunctionName("TF0165").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0166").getFtrs());
		assertEquals(10, report.getTransactionFunctionName("TF0166").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0167").getFtrs());
		assertEquals(13, report.getTransactionFunctionName("TF0167").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0168").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0168").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0169").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0169").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0170").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0170").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0171").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0171").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0172").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0172").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0173").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0173").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0174").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0174").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0175").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0175").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0176").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0176").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0177").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0177").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0178").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0178").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0179").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0179").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0180").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0180").getDets());

		assertEquals(2, report.getTransactionFunctionName("TF0181").getFtrs());
		assertEquals(11, report.getTransactionFunctionName("TF0181").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0182").getFtrs());
		assertEquals(14, report.getTransactionFunctionName("TF0182").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0183").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0183").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0184").getFtrs());
		assertEquals(13, report.getTransactionFunctionName("TF0184").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0185").getFtrs());
		assertEquals(17, report.getTransactionFunctionName("TF0185").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0186").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0186").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0187").getFtrs());
		assertEquals(13, report.getTransactionFunctionName("TF0187").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0188").getFtrs());
		assertEquals(17, report.getTransactionFunctionName("TF0188").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0189").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0189").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0190").getFtrs());
		assertEquals(13, report.getTransactionFunctionName("TF0190").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0191").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0191").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0192").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0192").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0193").getFtrs());
		assertEquals(13, report.getTransactionFunctionName("TF0193").getDets());

		assertEquals(4, report.getTransactionFunctionName("TF0194").getFtrs());
		assertEquals(17, report.getTransactionFunctionName("TF0194").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0195").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0195").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0196").getFtrs());
		assertEquals(15, report.getTransactionFunctionName("TF0196").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0197").getFtrs());
		assertEquals(12, report.getTransactionFunctionName("TF0197").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0198").getFtrs());
		assertEquals(16, report.getTransactionFunctionName("TF0198").getDets());

		assertEquals(3, report.getTransactionFunctionName("TF0199").getFtrs());
		assertEquals(16, report.getTransactionFunctionName("TF0199").getDets());
	}
}