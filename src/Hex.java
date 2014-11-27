import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class Hex {
	public static void scanAndAdd(HashMap<Integer, HashSet<Integer>> path, int pos1, int pos2)
	{
		if(path.get(pos1)!=null)
			path.get(pos1).addAll(path.get(pos2));
		else
		{
			HashSet<Integer> temp = new HashSet<Integer>();
			temp.addAll(path.get(pos2));					
			path.put(pos1, temp);						
		}		
	}
	public static String solve(char[][] board)
	{
		if(board.length==0) return "Nothing here";
		if(board.length==1) return board[0][0]=='R'?"Red wins":board[0][0]=='B'?"Blue wins":"Nobody wins";
		//check count of red and blue
		int b = 0;
		int r = 0;
		for(char[] array:board)
		{
			for(char c:array)
			{
				if(c=='B') b++;
				if(c=='R') r++;
			}
		}
		if(Math.abs(b-r)>1) return "Impossible";
		
		//make the paths
		int n = board.length;
		HashMap<Integer, HashSet<Integer>> redPath = new HashMap<Integer, HashSet<Integer>>();
		HashMap<Integer, HashSet<Integer>> bluePath = new HashMap<Integer, HashSet<Integer>>();
		for(int i=0; i<n; i++)
		{
			if(board[0][i]=='R')
			{
				HashSet<Integer> temp = new HashSet<Integer>();
				temp.add(i);
				redPath.put(i, temp);
			}
			if(board[i][0]=='B')
			{
				HashSet<Integer> temp = new HashSet<Integer>();
				temp.add(i);
				bluePath.put(i, temp);
			}
		}
		for(int i=0; i<n-1; i++)
		{
			HashMap<Integer, HashSet<Integer>> newRedPath = new HashMap<Integer, HashSet<Integer>>();
			HashMap<Integer, HashSet<Integer>> newBluePath = new HashMap<Integer, HashSet<Integer>>();
			for(int j=n-2; j>=0; j--)
			{
				if(board[i][j]=='R' && redPath.get(j+1)!=null)
				{
					scanAndAdd(redPath, j, j+1);
				}
				if(board[j][i]=='B' && bluePath.get(j+1)!=null)
				{
					scanAndAdd(bluePath, j, j+1);
				}
			}
			for(int j=0; j<n; j++)
			{
				if(board[i][j]=='R')
				{
					if(redPath.get(j-1)!=null) scanAndAdd(redPath, j, j-1);
					if(redPath.get(j)!=null)
					{
						if(board[i+1][j]=='R')
						{
							HashSet<Integer> temp = new HashSet<Integer>();
							temp.addAll(redPath.get(j));					
							newRedPath.put(j, temp);						
						}
						if(j>0 && board[i+1][j-1]=='R')
						{
							if(newRedPath.get(j-1)==null)
							{
								HashSet<Integer> temp = new HashSet<Integer>();
								temp.addAll(redPath.get(j));	
								newRedPath.put(j-1, temp);
							}
							else
							{
								newRedPath.get(j-1).addAll(redPath.get(j));
							}
						}
					}
				}
				if(board[j][i]=='B')
				{
					if(bluePath.get(j-1)!=null) scanAndAdd(bluePath, j, j-1);
					if(bluePath.get(j)!=null)
					{
						if(board[j][i+1]=='B')
						{
							HashSet<Integer> temp = new HashSet<Integer>();
							temp.addAll(bluePath.get(j));					
							newBluePath.put(j, temp);						
						}
						if(j>0 && board[j-1][i+1]=='B')
						{
							if(newBluePath.get(j-1)==null)
							{
								HashSet<Integer> temp = new HashSet<Integer>();
								temp.addAll(bluePath.get(j));	
								newBluePath.put(j-1, temp);
							}
							else
							{
								newBluePath.get(j-1).addAll(bluePath.get(j));
							}
						}					
					}
				}
			}
			redPath = newRedPath;
			bluePath = newBluePath;
		}
		if(redPath.size()==0 && bluePath.size()==0) return "Nobody wins";
		if(redPath.size()!=0 && bluePath.size()!=0) return "Impossible"; 
		if(redPath.size()==1) return "Red wins";
		if(bluePath.size()==1) return "Blue wins";
		if(redPath.size()>1)
		{
			HashSet<Integer> hs = new HashSet<Integer>();
			for(HashSet<Integer> s:redPath.values())
			{
				hs.addAll(s);
			}
			if(hs.size()>1) return "Impossible";
			return "Red wins";
		}
		else
		{
			HashSet<Integer> hs = new HashSet<Integer>();
			for(HashSet<Integer> s:bluePath.values())
			{
				hs.addAll(s);
			}
			if(hs.size()>1) return "Impossible";
			return "Blue wins";			
		}
	}
	
	public static void color(char[][] board, char c, int x, int y, int[][][] path, int number)
	{
		if(board[x][y]==c && path[x][y][number]!=1)
		{
			int n = board.length;
			path[x][y][number] = 1;
			if(x>0) color(board, c, x-1, y, path, number);
			if(x>0 && y<n-1) color(board, c, x-1, y+1, path, number);
			if(x<n-1) color(board, c, x+1, y, path, number);
			if(x<n-1 && y>0) color(board, c, x+1, y-1, path, number);
			if(y>0) color(board, c, x, y-1, path, number);
			if(y<n-1) color(board, c, x, y+1, path, number);
		}
	}
	//failed
//	B R R B R 
//	 B B R B B 
//	  B B R R R 
//	   R B B B R 
//	    B R B R R 
	public static String solve2(char[][] board)
	{
		//show matrix
		int space = 0;
		for(char[] array:board)
		{
			for(int i=0; i<space; i++) System.out.print(" ");
			space++;
			for(char c:array)
				if(c=='R') System.err.print(c+" ");
				else System.out.print(c+" ");
			System.out.println();
		}
		//check count of red and blue
		int b = 0;
		int r = 0;
		for(char[] array:board)
		{
			for(char c:array)
			{
				if(c=='B') b++;
				if(c=='R') r++;
			}
		}
		if(Math.abs(b-r)>1) return "Impossible";
		
		int n = board.length;
		int[][][] red = new int[n][n][n];
		for(int i=0; i<n; i++)
		{
			if(board[0][i]=='R') color(board, 'R', 0, i, red, i);
		}
		int[][][] blue = new int[n][n][n];
		for(int i=0; i<n; i++)
		{
			if(board[i][0]=='B') color(board, 'B', i, 0, blue, i);
		}
		
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				System.out.print(red[n-1][i][j]);
			}
			System.out.print("\t");
			for(int j=0; j<n; j++)
			{
				System.out.print(blue[i][n-1][j]);
			}
			System.out.println();
		}
		
		int[] redCount = new int[n];
		int redCountBottom = 0;
		int[] blueCount = new int[n];
		int blueCountBottom = 0;
		for(int i=0; i<n; i++)
		{
			boolean redAccess = false;
			boolean blueAccess = false;
			for(int j=0; j<n; j++)
			{
				if(red[n-1][i][j]!=0) redAccess = true;
				if(blue[i][n-1][j]!=0) blueAccess = true;
				redCount[j] += red[n-1][i][j];
				blueCount[j] += blue[i][n-1][j];
			}
			if(redAccess) redCountBottom++;
			if(blueAccess) blueCountBottom++;
		}
		System.out.println("red: "+Arrays.toString(redCount));
		System.out.println("blue: "+Arrays.toString(blueCount));
		int redCountTop = 0;
		int blueCountTop = 0;
		for(int i=0; i<n; i++)
		{
			if(redCount[i]!=0) redCountTop++;
			if(blueCount[i]!=0) blueCountTop++;
		}
		System.out.println("red:"+redCountTop+" "+redCountBottom);
		System.out.println("blue:"+blueCountTop+" "+blueCountBottom);
		if(redCountTop==0 && blueCountTop==0)
		{
			return "Nobody wins";
		}
		if(redCountTop>1 && redCountBottom>1 || blueCountTop>1 && blueCountBottom>1) return "Impossible";
		if(redCountTop==0) return "Blue wins";
		if(blueCountTop==0) return "Red wins";
		return "Impossible to here";
	}
	
	public static void color(char[][] board, char c, int x, int y, boolean[][] lebal)
	{
		if(board[x][y]==c && lebal[x][y]==false)
		{
			int n = board.length;
			lebal[x][y] = true;
			if(x>0) color(board, c, x-1, y, lebal);
			if(x>0 && y<n-1) color(board, c, x-1, y+1, lebal);
			if(x<n-1) color(board, c, x+1, y, lebal);
			if(x<n-1 && y>0) color(board, c, x+1, y-1, lebal);
			if(y>0) color(board, c, x, y-1, lebal);
			if(y<n-1) color(board, c, x, y+1, lebal);
		}		
	}
	public static boolean accessible(char[][] board, char c)
	{
		int n = board.length;
		boolean[][] lebal = new boolean[n][n];
		for(int i=0; i<n; i++)
		{
			if(c=='R') color(board, c, 0, i, lebal);
			if(c=='B') color(board, c, i, 0, lebal);
		}
//		//show matrix
//		int space = 0;
//		for(boolean[] array:lebal)
//		{
//			for(int i=0; i<space; i++) System.out.print(" ");
//			space++;
//			for(boolean b:array)
//				if(b) 
//				{
//					System.out.print("T ");
//				}
//				else 
//				{
//					System.out.print("F ");
//				}
//			System.out.println();
//		}
		for(int i=0; i<n; i++)
		{
			if(c=='R' && lebal[n-1][i]==true) return true;
			if(c=='B' && lebal[i][n-1]==true) return true;
		}
		return false;
	}
	public static String solve3(char[][] board)
	{
		int b = 0;
		int r = 0;
		for(char[] array:board)
		{
			for(char c:array)
			{
				if(c=='B') b++;
				if(c=='R') r++;
			}
		}
		if(Math.abs(b-r)>1) return "Impossible";
		
//		//show matrix
//		int space = 0;
//		for(char[] array:board)
//		{
//			for(int i=0; i<space; i++) System.out.print(" ");
//			space++;
//			for(char c:array)
//				if(c=='R')
//				{
//					System.out.print("X ");
//				}
//				else if(c=='B')
//				{
//					System.out.print("O ");
//				}
//				else
//				{
//					System.out.print(c+" ");
//				}
//			System.out.println();
//		}
		
		int n = board.length;
		if(accessible(board, 'R'))
		{
			System.out.println("get here");
			for(int i=0; i<n; i++)
			{
				for(int j=0; j<n; j++)
				{
					if(board[i][j]=='R')
					{
						board[i][j] = '.';
						if(!accessible(board, 'R')) return "Red wins";						
						board[i][j] = 'R';
					}
				}
			}
			return "Impossible";
		}
		if(accessible(board, 'B'))
		{
			for(int i=0; i<n; i++)
			{
				for(int j=0; j<n; j++)
				{
					if(board[i][j]=='B')
					{
						board[i][j] = '.';
						if(!accessible(board, 'B')) return "Blue wins";						
						board[i][j] = 'B';
					}
				}
			}
			return "Impossible";
		}
		return "Nobody wins";
	}
	public static void main(String[] args) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader("C-large-practice.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println((i+1)+"/"+testCaseCount);
			line = in.readLine();
			int n = Integer.parseInt(line);
			char[][] board = new char[n][n];
			for(int j=0; j<n; j++)
			{
				line = in.readLine();
				for(int k=0; k<n; k++)
				{
					board[j][k] = line.charAt(k);
				}
			}
//			if(i<73) continue;
			String result = solve3(board);
			System.out.println(result);
			out.write("Case #"+(i+1)+": "+result);
			out.newLine();
		}
		in.close();
		out.close();
	}
}
