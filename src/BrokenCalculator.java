import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;


public class BrokenCalculator {
	static boolean avaliable(int n, int[] broken)
	{
		while(n!=0)
		{
			if(broken[n%10]==0) return false;
			n /= 10;
		}
		return true;
	}
	public static int solve(int n, int[] broken, HashMap<Integer, Integer> map)
	{
		if(avaliable(n, broken)) return String.valueOf(n).length();
		if(map==null) map = new HashMap<Integer, Integer>();
		if(map.containsKey(n)) return map.get(n);
		int ret = Integer.MAX_VALUE;
		boolean exist = false;
		for(int i=(int)Math.sqrt(n); i>1; i--)
		{
			if(n%i==0 && avaliable(i, broken))
			{
				int count = solve(n/i, broken, map);
				if(count!=0)
				{
					exist = true;
					count += String.valueOf(i).length()+1;
					if(count<ret)
						ret = count;
				}
			}
		}
		map.put(n, exist?ret:0);
		return exist?ret:0;
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("C-large.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println(i+"/"+testCaseCount);
			line = in.readLine();
			String[] split = line.split(" ");
			int[] broken = new int[10];
			for(int j=0; j<10; j++) broken[j] = Integer.parseInt(split[j]);
			int n  = Integer.parseInt(in.readLine());
			int result = solve(n, broken, null);
			System.out.println(n+" "+Arrays.toString(broken)+" "+result);
			out.write("Case #"+(i+1)+": "+(result==0?"Impossible":result+1));
			out.newLine();
		}
		in.close();
		out.close();
	}
}
