package textExcel5;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ExpressionSimplifier has a static simplify method that can take a string with
 * a mathematical expression in it and reduce it down to a single term. For
 * example, 5 * 4 + 3 * 2 can be simplified to 26.
 */
public class ExpressionSimplifier
{
	/**
	 * The simplify method takes an expression like 3 + 2 as a parameter and returns
	 * a simplified version of it.
	 * 
	 * @param expression
	 *            - a valid math expression like 9 / 2
	 * 
	 * @return - a double representing the result of evaluating the expression
	 */
	public static double simplify(String expression, Spreadsheet sheet)
	{
		// first, break the expression up so that each 'token' (each number
		// or operator with spaces around it) becomes one element in an
		// ArrayList

		ArrayList<String> tokens = convertToList(expression);

		// Repeatedly find the next operand-operator-operand trio of tokens,
		// evaluate it, and replace it with the result, until we're down to
		// a single token
		for (int i = 0; i < tokens.size(); i++)
		{
			if (sheet.isCellReference(tokens.get(i)))
			{
				Cell num = sheet.getNum(tokens.get(i));
				String c = num.getDisplayValue();
				tokens.set(i, c);
			}
		}
		for (int i = 0; i < tokens.size(); i++)
		{
			if (tokens.get(i).equals("sum"))
			{
				String sum = sheet.getSum(tokens.get(i + 1));
				String a = "" + sum;
				tokens.set(i, a);
				tokens.remove(i + 1);
			}
			if (tokens.get(i).equals("avg"))
			{
				String avg = sheet.getAvg(tokens.get(i + 1));
				String a = "" + avg;
				tokens.set(i, a);
				tokens.remove(i + 1);
			}
		}
		while (tokens.size() > 1)
		{
			// find the index of the next operator in tokens
			int operatorIndex = findIndexOfNextOperator(tokens);

			// pull out the left and right operands plus the operator and
			// calculate the result of combining them
			String leftOperand = tokens.get(operatorIndex - 1);
			String operator = tokens.get(operatorIndex);
			String rightOperand = tokens.get(operatorIndex + 1);
			String result = calculate(leftOperand, operator, rightOperand);

			// then replace the left/op/right tokens in the list with the result
			replaceInList(tokens, operatorIndex - 1, 3, result);
		}
		
		// convert the final term to a double, since that's what we're
		// supposed to return
		return Double.parseDouble(tokens.get(0));
	}

	/**
	 * Given a string, break it up by spaces, so that something like "a + b" becomes
	 * a 3-element list: a, +, b
	 * 
	 * There are a couple of different ways to do this. You can use the String
	 * indexOf and substring methods to find each space and pull out the parts
	 * between them, or you can use the String split method to get an array of
	 * Strings and convert the array to an ArrayList.
	 * 
	 * @param str
	 *            - the String to break up into tokens
	 * 
	 * @return - an ArrayList containing the parts of the input string
	 */
	private static ArrayList<String> convertToList(String str)
	{
		// TODO: write code to create a new ArrayList of strings, split
		// str into chunks by finding spaces, add each chunk to your new
		// arraylist, and return it.

		return new ArrayList<String>(Arrays.asList(str.split(" ")));
	}

	/**
	 * Given a series of tokens like a, +, b, *, and c, find the index of the
	 * operator that should be evaluated next. In the example here, the next
	 * operator is the '*' at index 3.
	 * 
	 * @param tokens
	 *            - an ArrayList of numbers and math operators that form a valid
	 *            math expression.
	 * 
	 * @return - the index in the ArrayList of the next operator that should be
	 *         evaluated.
	 */
	private static int findIndexOfNextOperator(ArrayList<String> tokens)
	{
		// TODO: write code here to look through the tokens for the next
		// operator that should be evaluated. First you'll need to look
		// for a * or /, then for a + or -. If you don't find anything,
		// you can throw an exception by leaving the line below at the end
		// of your function.
		for (int i = 0; i < tokens.size(); i++)
		{
			if (tokens.get(i).equals("*") || tokens.get(i).equals("/"))
			{
				return i;
			}
		}
		for (int j = 0; j < tokens.size(); j++)
		{
			if (tokens.get(j).equals("+") || tokens.get(j).equals("-"))
			{
				return j;
			}
		}
		throw new InvalidParameterException("No operator found in token list");

	}

	/**
	 * Given left and right operands and an operator for in between them, calculate
	 * the result of the expression and return it.
	 * 
	 * @param left
	 *            - the left operand
	 * @param op
	 *            - the operator (*, /, +, or -)
	 * @param right
	 *            - the right operand
	 * 
	 * @return - the result of evaluating the expression formed by left, op, and
	 *         right
	 */
	private static String calculate(String left, String op, String right)
	{
		// TODO: write code to convert left and right to doubles, then combine
		// them based on whatever operation is represented by op. If op is not
		// a valid operator (*, /, +, or -), you can throw an exception by leaving
		// the line below at the bottom of your function.
		double leftOperand = Double.parseDouble(left);
		double rightOperand = Double.parseDouble(right);
		if (op.equals("+"))
		{
			double calculate = leftOperand + rightOperand;
			return Double.toString(calculate);
		}
		if (op.equals("-"))
		{
			double calculate = leftOperand - rightOperand;
			return Double.toString(calculate);
		}
		if (op.equals("*"))
		{
			double calculate = leftOperand * rightOperand;
			return Double.toString(calculate);
		}
		if (op.equals("/"))
		{
			double calculate = leftOperand / rightOperand;
			return Double.toString(calculate);
		}
		throw new InvalidParameterException("'" + op + "' is not a valid operator");
	}

	/**
	 * Replace some items in an ArrayList with a replacement value
	 * 
	 * @param items
	 *            - the ArrayList of items to modify
	 * @param firstIndex
	 *            - the index in the ArrayList of the first item to remove
	 * @param howManyItems
	 *            - how many items to remove
	 * @param newValue
	 *            - the value to replace the removed item(s) with
	 */
	private static void replaceInList(ArrayList<String> items, int firstIndex, int howManyItems, String newValue)
	{
		// TODO: write some code here to replace howManyItems starting at firstIndex
		// in the arraylist items with a single item, newValue.
		for (int i = 0; i < howManyItems - 1; i++)
		{
			items.remove(firstIndex);
		}
		items.set(firstIndex, newValue);
		
	}
}