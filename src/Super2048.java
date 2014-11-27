import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;


public class Super2048 {
	public static void solve(int[][] board, String direction)
	{
		int n = board.length;
		if(direction.equals("up"))
		{
			for(int i=0; i<n; i++)
			{
				LinkedList<Integer> newColumn = new LinkedList<Integer>();
				for(int j=0; j<n; j++)
				{
					if(j<n-1)
					{
						int temp = j+1;
						while(temp<n && board[temp][i]==0) temp++;
						if(temp<n && board[j][i]==board[temp][i])
						{
							newColumn.add(board[j][i]*2);
							j++;
							board[temp][i] = 0;
						}
						else newColumn.add(board[j][i]);
					}
					else newColumn.add(board[j][i]);
				}
				for(int j=0; j<n; j++) board[j][i] = 0;
				for(int j=0; j<n; j++)
				{
					while(!newColumn.isEmpty() && newColumn.peek()==0) newColumn.remove();
					if(!newColumn.isEmpty()) board[j][i] = newColumn.remove();
				}
			}
		}
		if(direction.equals("down"))
		{
			for(int i=0; i<n; i++)
			{
				LinkedList<Integer> newColumn = new LinkedList<Integer>();
				for(int j=n-1; j>=0; j--)
				{
					if(j>0)
					{
						int temp = j-1;
						while(temp>=0 && board[temp][i]==0) temp--;
						if(temp>=0 && board[j][i]==board[temp][i])
						{
							newColumn.add(board[j][i]*2);
							j--;
							board[temp][i] = 0;
						}
						else newColumn.add(board[j][i]);
					}
					else newColumn.add(board[j][i]);
				}
				for(int j=0; j<n; j++) board[j][i] = 0;
				for(int j=n-1; j>=0; j--)
				{
					while(!newColumn.isEmpty() && newColumn.peek()==0) newColumn.remove();
					if(!newColumn.isEmpty()) board[j][i] = newColumn.remove();
				}
			}
		}
		if(direction.equals("left"))
		{
			for(int i=0; i<n; i++)
			{
				LinkedList<Integer> newRow = new LinkedList<Integer>();
				for(int j=0; j<n; j++)
				{
					if(j<n-1)
					{
						int temp = j+1;
						while(temp<n && board[i][temp]==0) temp++;
						if(temp<n && board[i][j]==board[i][temp])
						{
							newRow.add(board[i][j]*2);
							j++;
							board[i][temp] = 0;
						}
						else newRow.add(board[i][j]);
					}
					else newRow.add(board[i][j]);
				}
				for(int j=0; j<n; j++) board[i][j] = 0;
				for(int j=0; j<n; j++)
				{
					while(!newRow.isEmpty() && newRow.peek()==0) newRow.remove();
					if(!newRow.isEmpty()) board[i][j] = newRow.remove();
				}
			}
		}
		if(direction.equals("right"))
		{
			for(int i=0; i<n; i++)
			{
				LinkedList<Integer> newRow = new LinkedList<Integer>();
				for(int j=n-1; j>=0; j--)
				{
					if(j>0)
					{
						int temp = j-1;
						while(temp>=0 && board[i][temp]==0) temp--;
						if(temp>=0 && board[i][j]==board[i][temp])
						{
							newRow.add(board[i][j]*2);
							j--;
							board[i][temp] = 0;
						}
						else newRow.add(board[i][j]);
					}
					else newRow.add(board[i][j]);
				}
				for(int j=0; j<n; j++) board[i][j] = 0;
				for(int j=n-1; j>=0; j--)
				{
					while(!newRow.isEmpty() && newRow.peek()==0) newRow.remove();
					if(!newRow.isEmpty()) board[i][j] = newRow.remove();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("B-large.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println((i+1)+"/"+testCaseCount);
			line = in.readLine();
			String[] split = line.split(" ");
			int n = Integer.parseInt(split[0]);
			String dir = split[1];
			System.out.println(dir);
			int[][] board = new int[n][n];
			for(int j=0; j<n; j++)
			{
				line = in.readLine();
				String[] split2 = line.split(" ");
				for(int k=0; k<n; k++)
				{
					board[j][k] = Integer.parseInt(split2[k]);
					System.out.print(board[j][k]);
				}
				System.out.println();
			}
			solve(board, dir);
			out.write("Case #"+(i+1)+": ");
			out.newLine();
			for(int[] array:board)
			{
				for(int x:array)
				{
					out.write(""+x+" ");
				}
				out.newLine();
			}
		}
		in.close();
		out.close();
	}
}
