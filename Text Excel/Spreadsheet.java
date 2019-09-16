/**********************************************************
 * Assignment: (Spreadsheet)
 *
 * Author: (Nafis Rahman)
 *
 * Description: (It prints the whole spreadsheet)
 *
 * Academic Integrity: I pledge that this program represents my own work. I
 * received help from (my mentor who is a software engineer) in designing and debugging
 * my program.!
 **********************************************************/
package textExcel5;

/* Spreadsheet stores a 2d array of Cell objects and is able to 
 * modify the array and display it by printing to System.out.
 */
public class Spreadsheet
{
	private final static int COLS = 7;
	private final static int ROWS = 10;
	private final static int CELL_WIDTH = 12;

	// store the cells in a 2D array
	private Cell[][] data;

	// construct a new spreadsheet. each cell in 'data' will be
	// null initially, representing an empty cell.
	public Spreadsheet()
	{
		data = new Cell[ROWS][COLS];
	}

	// print the spreadsheet to system.out
	public void print()
	{
		printHorizontalLine();
		printColumnHeaders();
		printHorizontalLine();

		for (int r = 0; r < ROWS; r++)
		{
			printRow(r);
			printHorizontalLine();
		}
	}

	// check to see if 'ref' is a valid cell reference, like A3 or G10. if
	// ref can't be parsed as a column and row, or if it is not in the
	// appropriate range for this spreadsheet, return false.
	public boolean isCellReference(String ref)
	{
		if (ref == null || ref.length() < 2 || ref.length() > 3)
			return false;

		if (ref.charAt(0) < 'A' || ref.charAt(0) > 'A' + COLS)
			return false;

		int row = Integer.parseInt(ref.substring(1));
		if (row < 1 || row > ROWS)
			return false;

		return true;
	}

	// display the value of a single cell, represented by ref, by printing
	// it to system.out.
	public void displayCell(String ref)
	{
		Cell c = data[getRow(ref)][getCol(ref)];
		String value = (c == null) ? "<empty>" : c.getOriginalValue(); // "ternary operator" - bing it :)

		System.out.println(ref + " = " + value);
	}

	// store a cell at the specified location in the grid, replacing whatever
	// might be there already.
	public void setCell(String ref, Cell cell)
	{
		data[getRow(ref)][getCol(ref)] = cell;
	}

	// given a string that is supposed to be a reference to a cell, parse the
	// row index from it (i.e. F4 will return 3 because 3 is the index of the
	// 4th row in 'data').
	private int getRow(String ref)
	{
		if (!isCellReference(ref))
			throw new IllegalArgumentException(ref + " is not a valid cell reference");

		return Integer.parseInt(ref.substring(1)) - 1;
	}

	// given a string that is supposed to be a reference to a column, parse
	// the column index from it (e.g. C7 will return 2, since C is the 3rd column
	// and its zero-based index in data would therefore be 2).
	private int getCol(String ref)
	{
		if (!isCellReference(ref))
			throw new IllegalArgumentException(ref + " is not a valid cell reference");

		return ref.charAt(0) - 'A';
	}

	// print one line of +------------+--- etc.
	private void printHorizontalLine()
	{
		for (int col = 0; col < COLS + 1; col++)
		{
			System.out.print("+");
			for (int ch = 0; ch < CELL_WIDTH; ch++)
				System.out.print("-");
		}
		System.out.println("+");
	}

	// print the column header row
	private void printColumnHeaders()
	{
		System.out.print("|" + center("", CELL_WIDTH)); // blank cell at top left

		for (int col = 0; col < COLS; col++)
		{
			System.out.print("|");
			System.out.print(center((char) (col + 'A') + "", CELL_WIDTH));
		}
		System.out.println("|");
	}

	// print the specified row of cells, including their left and right borders
	private void printRow(int row)
	{
		System.out.print("|" + center(row + 1 + "", CELL_WIDTH)); // the header column

		for (int col = 0; col < COLS; col++)
		{
			String value = (data[row][col] == null) ? "" : data[row][col].getDisplayValue();
			System.out.print("|" + center(value, CELL_WIDTH));
		}

		System.out.println("|");
	}

	// given a string 'text', truncate or pad it to make it fit exactly in
	// 'width' characters
	private String center(String text, int width)
	{
		if (text.length() > width)
			return text.substring(0, width - 1) + ">";

		String centered = "";
		int leftSpaces = (width - text.length()) / 2;
		for (int c = 0; c < leftSpaces; c++)
			centered += " ";
		centered += text;
		for (int c = centered.length(); c < width; c++)
			centered += " ";

		return centered;
	}

	public void clear()
	{
		// TODO Auto-generated method stub
		for (int i = 0; i < data.length; i++)
		{
			for (int j = 0; j < data[i].length; j++)
			{
				data[i][j] = null;
			}
		}
		print();
	}

	public void clear(String ref)
	{
		setCell(ref, null);
	}

	public Cell getNum(String ref)
	{
		return data[getRow(ref)][getCol(ref)];
	}

	public String getSum(String ref)
	{
		int colon = ref.indexOf(':');
		String startCell = ref.substring(0, colon);
		String endCell = ref.substring(colon + 1);
		int startRowIndex = getRow(startCell);
		int startColIndex = getCol(startCell);
		int endRowIndex = getRow(endCell);
		int endColIndex = getCol(endCell);
		double sum = 0;
		for (int i = startRowIndex; i <= endRowIndex; i++)
		{
			for (int j = startColIndex; j <= endColIndex; j++)
			{
				sum = sum + Double.parseDouble(data[i][j].getDisplayValue());
			}
		}
		return sum + "";
	}

	public String getAvg(String ref)
	{
		int colon = ref.indexOf(':');
		String startCell = ref.substring(0, colon);
		String endCell = ref.substring(colon + 1);
		int startRowIndex = getRow(startCell);
		int startColIndex = getCol(startCell);
		int endRowIndex = getRow(endCell);
		int endColIndex = getCol(endCell);
		double sum = 0;
		double avg;
		int total = 0;
		for (int i = startRowIndex; i <= endRowIndex; i++)
		{
			for (int j = startColIndex; j <= endColIndex; j++)
			{
				sum = sum + Double.parseDouble(data[i][j].getDisplayValue());
				total++;
			}
		}
		avg = sum / total;
		return avg + "";
	}

	public void sort(String ref, boolean figure)
	{
		int colon = ref.indexOf(':');
		String startCell = ref.substring(0, colon);
		String endCell = ref.substring(colon + 1);
		int startRowIndex = getRow(startCell);
		int startColIndex = getCol(startCell);
		int endRowIndex = getRow(endCell);
		int endColIndex = getCol(endCell);
		int rowDifference = (endRowIndex - startRowIndex) + 1;
		int colDifference = (endColIndex - startColIndex) + 1;
		Cell[] nums = new Cell[rowDifference * colDifference];
		int index = 0;
		for (int i = startRowIndex; i <= endRowIndex; i++)
		{
			for (int j = startColIndex; j <= endColIndex; j++)
			{
				nums[index] = data[i][j];
				index++;
			}
		}
		Sorting.selectionSort(nums);
		if (figure == true)
		{
			index = 0;
			for (int i = startRowIndex; i <= endRowIndex; i++)
			{
				for (int j = startColIndex; j <= endColIndex; j++)
				{
					data[i][j] = nums[index];
					index++;
				}
			}
		}
		if (figure == false)
		{
			index = nums.length - 1;
			for (int i = startRowIndex; i <= endRowIndex; i++)
			{
				for (int j = startColIndex; j <= endColIndex; j++)
				{
					data[i][j] = nums[index];
					index--;
				}
			}
		}
	}
}
