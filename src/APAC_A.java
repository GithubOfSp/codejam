import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;


public class APAC_A {
	public static int[] getSegment(int number)
	{
		switch(number)
		{
		case 0: return new int[]{1,1,1,1,1,1,0};
		case 1: return new int[]{0,1,1,0,0,0,0};
		case 2: return new int[]{1,1,0,1,1,0,1};
		case 3: return new int[]{1,1,1,1,0,0,1};
		case 4: return new int[]{0,1,1,0,0,1,1};
		case 5: return new int[]{1,0,1,1,0,1,1};
		case 6: return new int[]{1,0,1,1,1,1,1};
		case 7: return new int[]{1,1,1,0,0,0,0};
		case 8: return new int[]{1,1,1,1,1,1,1};
		case 9: return new int[]{1,1,1,1,0,1,1};
		}
		return null;
	}
	
	public static boolean match(int number, int[] show, int[] segment)
	{
		int[] should = getSegment(number);
		for(int i=0; i<should.length; i++)
		{
			should[i] &= segment[i];
		}
		for(int i=0; i<show.length; i++)
		{
			if(should[i]!=show[i])
			{
				return false;
			}
		}
		return true;
	}
	
	public static int exist(int[][] number, int[] segment)
	{
		int n = number.length;
		int count = 0;
		for(int i=0; i<10; i++)
		{
			boolean m = true;
			for(int j=0; j<n; j++)
			{
				if(!match((i-j+10)%10, number[j], segment))
				{
					m = false;
					break;
				}
			}
			if(m) count++;
		}
		if(count==1)
		{
			for(int i=0; i<10; i++)
			{
				boolean m = true;
				for(int j=0; j<n; j++)
				{
					if(!match((i-j+10)%10, number[j], segment))
					{
						m = false;
						break;
					}
				}
				if(m) return (i-n+10)%10;
			}			
		}
		return -1;
	}
	
	public static int[] solve(int[][] number)
	{
		int[] segment = new int[7];
		for(int i=0; i<number.length; i++)
		{
			for(int j=0; j<7; j++)
			{
				segment[j] |= number[i][j];
			}
		}
		HashSet<String> hs = new HashSet<String>();
		int count = 0;
		int[] ret = null;
		for(int mask=0; mask<128; mask++)
		{
			int[] seg = new int[7];
			for(int i=0; i<7; i++)
			{
				seg[i] = segment[i];
			}
			for(int i=0; i<7; i++)
			{
				int temp = mask;
				temp &= (1<<i);
				if(temp!=0) seg[i] = 1;
			}
			if(!hs.contains(Arrays.toString(seg)))
			{
				hs.add(Arrays.toString(seg));
				int next = exist(number, seg);
				if(next!=-1)
				{
					count++;
					ret = getSegment(next);
					for(int j=0; j<7; j++)
					{
						ret[j] &= segment[j];
					}
				}
			}
		}
		System.out.println(hs.size());
		if(count==1)
		{
			return ret;
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("A-small-practice.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println((i+1)+"/"+testCaseCount);
			line = in.readLine();
			String[] split = line.split(" ");
			int[][] numbers = new int[Integer.parseInt(split[0])][7];
			for(int j=0; j<numbers.length; j++)
			{
				for(int k=0; k<7; k++)
				{
					numbers[j][k] = split[j+1].charAt(k)-'0';
				}
			}
			int[] result = solve(numbers);
			System.out.println(Arrays.toString(result));
			if(result==null) out.write("Case #"+(i+1)+": ERROR!");
			else
			{
				out.write("Case #"+(i+1)+": ");				
				for(int j=0; j<7; j++) out.write(""+result[j]);
			}
			out.newLine();
		}
		in.close();
		out.close();
	}
}
