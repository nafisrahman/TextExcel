/**********************************************************
 * Assignment: (Formula Cell)
 *
 * Author: (Nafis Rahman)
 *
 * Description: (It takes a formula and give the answer)
 *
 * Academic Integrity: I pledge that this program represents my own work. I
 * received help from () in designing and debugging
 * my program.!
 **********************************************************/
package textExcel5;

public class FormulaCell extends Cell
{
	Spreadsheet sheet;
	public FormulaCell(String value, Spreadsheet s)
	{
		super(value);
		if (!value.startsWith("(") || !value.endsWith(")"))
			throw new IllegalArgumentException("Formulas need to start and end with parenthesis. '" + value + "' did not.");
		sheet = s;
	}

	public String getDisplayValue()
	{
		String formula = getOriginalValue().substring(1, getOriginalValue().length() - 1);
		return String.valueOf(ExpressionSimplifier.simplify(formula, sheet));
	}
}
