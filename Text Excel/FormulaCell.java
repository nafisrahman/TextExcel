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
