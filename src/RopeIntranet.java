import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class RopeIntranet {
	public static int insert(ArrayList<Integer> a, int b)
	{
		if(a.size()==0)
		{
			a.add(b);
			return 0;
		}
		int head = 0;
		int tail = a.size();
		while(head<tail)
		{
			int mid = (head+tail)/2;
			if(a.get(mid)>b)
			{
				if(mid==0)
				{
					a.add(0, b);
					return a.size()-1;
				}
				if(a.get(mid-1)<b)
				{
					a.add(mid, b);
					return a.size()-mid-1;
				}
				tail = mid;
			}
			else
			{
				if(mid==a.size()-1)
				{
					a.add(b);
					return 0;
				}
				if(a.get(mid+1)>b)
				{
					a.add(mid+1, b);
					return a.size()-mid-2;
				}
				head = mid+1;
			}
		}
		return 0;
	}
	public static int solve(ArrayList<int[]> ropes)
	{
		Collections.sort(ropes, new Comparator(){
			@Override
			public int compare(Object a, Object b)
			{
				return ((int[])a)[0]-((int[])b)[0];
			}
		});
		ArrayList<Integer> b = new ArrayList<Integer>();
		int ret = 0;
		for(int i=0; i<ropes.size(); i++)
		{
//			System.out.println(ropes.get(i)[1]);
			ret += insert(b, ropes.get(i)[1]);
		}
		return ret;
	}
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("A-large-practice.in"));
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		for(int i=0; i<testCaseCount; i++)
		{
			line = in.readLine();
			int ropeCount = Integer.parseInt(line);
			ArrayList<int[]> ropes = new ArrayList<int[]>();
			for(int j=0; j<ropeCount; j++)
			{
				line = in.readLine();
				String[] split = line.split(" ");
				ropes.add(new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])});
			}
			out.write("Case #"+(i+1)+": "+solve(ropes));
			out.newLine();
		}
		in.close();
		out.close();
	}
}
