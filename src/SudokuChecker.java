import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class SudokuChecker {
	public static boolean solve(int[][] board, int n)
	{
		final long valid = (long)Math.pow(2, n*n)-1;
		long check1 = 0;
		long check2 = 0;
		for(int i=0; i<board.length; i++)
		{
			for(int j=0; j<board[0].length; j++)
			{
				if(board[i][j]>n*n) return false;
				check1 |= 1l<<(board[i][j]-1);
				check2 |= 1l<<(board[j][i]-1);
			}
			if(check1!=valid || check2!=valid) return false;
			check1 = check2 = 0;
		}
		for(int i=0; i<board.length; i+=n)
		{
			for(int j=0; j<board.length; j+=n)
			{
				for(int k=i; k<i+n; k++)
				{
					for(int l=j; l<j+n; l++)
					{
						check1 |= 1l<<(board[k][l]-1);
						check2 |= 1l<<(board[l][k]-1);
					}
				}
				if(check1!=valid || check2!=valid) return false;
				check1 = check2 = 0;
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("A-large-practice.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			line = in.readLine();
			int n = Integer.parseInt(line);
			int[][] board = new int[n*n][n*n];
			for(int j=0; j<n*n; j++)
			{
				line = in.readLine();
				String[] split = line.split(" ");
				for(int k=0; k<n*n; k++)
				{
					board[j][k] = Integer.parseInt(split[k]);
				}
			}
			boolean result = solve(board, n);
			out.write("Case #"+(i+1)+": "+(result?"Yes":"No"));
			out.newLine();
		}
		in.close();
		out.close();
	}
}
