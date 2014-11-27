import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Minesweeper {
	static void colorZeroes(char[][] matrix)
	{
		int n = matrix.length;
		for(int i=0; i<matrix.length; i++)
		{
			for(int j=0; j<matrix[0].length; j++)
			{
				if(matrix[i][j]=='.')
				{
					if(i-1>=0 && j-1>=0 && matrix[i-1][j-1]=='*') continue; 
					if(i-1>=0 && matrix[i-1][j]=='*') continue; 
					if(j-1>=0 && matrix[i][j-1]=='*') continue; 
					if(i-1>=0 && j+1<n && matrix[i-1][j+1]=='*') continue; 
					if(i+1<n && j-1>=0 && matrix[i+1][j-1]=='*') continue; 
					if(i+1<n && matrix[i+1][j]=='*') continue; 
					if(j+1<n && matrix[i][j+1]=='*') continue; 
					if(i+1<n && j+1<n && matrix[i+1][j+1]=='*') continue; 
					matrix[i][j] = '0';
				}
			}
		}
	}
	static int countClick(char[][] matrix)
	{
		int n = matrix.length;
		int ret = 0;
		for(int i=0; i<matrix.length; i++)
		{
			for(int j=0; j<matrix[0].length; j++)
			{
				if(matrix[i][j]=='.')
				{
					if(i-1>=0 && j-1>=0 && matrix[i-1][j-1]=='0') continue; 
					if(i-1>=0 && matrix[i-1][j]=='0') continue; 
					if(j-1>=0 && matrix[i][j-1]=='0') continue; 
					if(i-1>=0 && j+1<n && matrix[i-1][j+1]=='0') continue; 
					if(i+1<n && j-1>=0 && matrix[i+1][j-1]=='0') continue; 
					if(i+1<n && matrix[i+1][j]=='0') continue; 
					if(j+1<n && matrix[i][j+1]=='0') continue; 
					if(i+1<n && j+1<n && matrix[i+1][j+1]=='0') continue; 
					ret++;
				}
			}
		}		
		return ret;
	}
	static void colorRe(char[][] matrix, int i, int j)
	{
		if(i>=0 && i<matrix.length && j>=0 && j<matrix.length && matrix[i][j]=='0')
		{
			matrix[i][j] = '1';
			colorRe(matrix, i-1, j-1);
			colorRe(matrix, i-1, j);
			colorRe(matrix, i-1, j+1);
			colorRe(matrix, i, j-1);
			colorRe(matrix, i, j+1);
			colorRe(matrix, i+1, j-1);
			colorRe(matrix, i+1, j);
			colorRe(matrix, i+1, j+1);
		}
	}
	static int countZeroZone(char[][] matrix)
	{
		int ret = 0;
		for(int i=0; i<matrix.length; i++)
		{
			for(int j=0; j<matrix.length; j++)
			{
				if(matrix[i][j]=='0')
				{
					 ret++;
					 colorRe(matrix, i, j);
				}
			}
		}
		return ret;
	}
	public static int solve(char[][] matrix)
	{
		colorZeroes(matrix);
		return countClick(matrix)+countZeroZone(matrix);
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("A-large.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println(i+"/"+testCaseCount);
			line = in.readLine();
			int n = Integer.parseInt(line);
			char[][] matrix = new char[n][n];
			for(int j=0; j<n; j++)
			{
				line = in.readLine();
				for(int k=0; k<n; k++)
				{
					matrix[j][k] = line.charAt(k);
				}
			}
			int result = solve(matrix);
			out.write("Case #"+(i+1)+": "+result);
			out.newLine();
		}
		in.close();
		out.close();
	}
}
