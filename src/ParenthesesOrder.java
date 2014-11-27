import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ParenthesesOrder {
    public static List<String> generateParenthesis(int n) {
        List<String> ret = new LinkedList<String>();
        if(n==0) return ret;
        if(n==1)
        {
            ret.add("()");
            return ret;
        }
        List<String> sub = generateParenthesis(n-1);
        for(String s:sub)
        {
            ret.add("("+s+")");
            ret.add("()"+s);
        }
        for(int i=1; i<n/2; i++)
        {
            List<String> sub1 = generateParenthesis(i);
            List<String> sub2 = generateParenthesis(n-1-i);
            for(String a:sub1)
            {
                for(String b:sub2)
                {
                    ret.add("("+a+")"+b);
                    ret.add("("+b+")"+a);
                }
            }
        }
        if(n%2==1)
        {
            sub = generateParenthesis((n-1)/2);
            for(String a:sub)
            {
                for(String b:sub)
                {
                    ret.add("("+a+")"+b);
                }
            }
        }
    	Collections.sort(ret);
        return ret;
    }
    public static String solve(int n, int k)
    {
    	List<String> s = generateParenthesis(n);
    	if(k>s.size()) return "Doesn't Exist!";
    	return s.get(k-1);
    }
    public static void main(String[] args) throws IOException
    {
		BufferedReader in = new BufferedReader(new FileReader("D-small-attempt0.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println(i+"/"+testCaseCount);
			line = in.readLine();
			String[] split = line.split(" ");
			int n = Integer.parseInt(split[0]);
			int k = Integer.parseInt(split[1]);
			String result = solve(n, k);
			out.write("Case #"+(i+1)+": "+result);
			out.newLine();
		}
		in.close();
		out.close();
    }
}
