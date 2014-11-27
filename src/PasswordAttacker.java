import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class PasswordAttacker {
	private static LinkedList<byte[]> pick(int m, int n)
	{
		LinkedList<byte[]> ret = new LinkedList<byte[]>();
		HashSet<String> hs = new HashSet<String>();
		for(int i=0; i<Math.pow(m, n); i++)
		{
			byte[] combi = new byte[n];
			int temp = i;
			for(int j=0; j<n; j++)
			{
				combi[j] = (byte)(temp%m);
				temp /= m;
			}
			Arrays.sort(combi);
			if(!hs.contains(Arrays.toString(combi)))
			{
				hs.add(Arrays.toString(combi));
				ret.add(combi);
			}
		}
		return ret;
	}
    static List<List<Integer>> permuteRe(List<Integer> l)
    {
        List<List<Integer>> ret = new LinkedList<List<Integer>>();
        if(l.size()==1)
        {
            ret.add(l);
        }
        else
        {
            for(int i=0; i<l.size(); i++)
            {
                if(i>0 && l.get(i)==l.get(i-1)) continue;
                List<Integer> clone = new ArrayList<Integer>(l);
                int remove = clone.remove(i);
                List<List<Integer>> sublist = permuteRe(clone);
                for(List<Integer> temp:sublist)
                {
                    temp.add(0, remove);
                }
                ret.addAll(sublist);
            }
        }
        return ret;
    }
    public static int permuteUnique(int[] num) {
        if(num.length==0) return 0;
        List<Integer> input = new ArrayList<Integer>();
        Arrays.sort(num);
        for(int i:num) input.add(i);
        return permuteRe(input).size();       
    }
    static long factorial(int n)
    {
        long ret=1;
        while(n>1)
        {
            ret*=n;
            n--;
        }
        return ret;
    }
    public static int solve(int m, int n)
    {
    	if(m==n) return (int)(factorial(n)%1000000007);
    	List<byte[]> list = pick(m, n-m);
    	int ret = 0;
    	for(byte[] b:list)
    	{
	    	int[] input = new int[n];
	    	for(int i=0; i<m; i++)
	    	{
	    		input[i] = i;
	    	}
	    	for(int i=m; i<n; i++)
	    	{
	    		input[i] = b[i-m];
	    	}
	    	ret += permuteUnique(input);
    	}
    	return ret;
    }
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("A-small-attempt1.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println(i+"/"+testCaseCount);
			line = in.readLine();
			String[] split = line.split(" ");
			int m = Integer.parseInt(split[0]);
			int n = Integer.parseInt(split[1]);
			int result = solve(m, n);
			out.write("Case #"+(i+1)+": "+result);
			out.newLine();
		}
		in.close();
		out.close();
	}
}
