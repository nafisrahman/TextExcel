package textExcel5;

public class Sorting
{
	public static void selectionSort(Cell[] nums)
	{
		// TODO: write your code to sort numbers here. Remember,
		// selection sort finds the next smallest number in the array
		// and swaps it into the next available location, after the
		// part of the array that is already sorted. Be sure to
		// increment operations each time you access an array
		// element (e.g. to compare it or copy it).
		for (int i = 0; i < nums.length - 1; i++)
		{
			int min = i;
			for (int j = i + 1; j < nums.length; j++)
			{
				if (Double.parseDouble(nums[j].getDisplayValue()) < Double.parseDouble(nums[min].getDisplayValue()))
				{
					min = j;
				}
			}
			if (min != i)
			{
				Cell num = nums[min];
				nums[min] = nums[i];
				nums[i] = num;
			}
		}
	}
}
