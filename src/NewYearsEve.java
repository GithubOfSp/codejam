import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class NewYearsEve {
	static int glassesOfLevel(int l)
	{
		if(l==0) return 0;
		return l+glassesOfLevel(l-1);
	}
	static double capacityOfLevelTotal(int l)
	{
		if(l==0) return glassesOfLevel(l)*250;
		return glassesOfLevel(l)*250+capacityOfLevelTotal(l-1);
	}
	public static double solve(int b, int l, int n)
	{
		double total = b*750;
		if(capacityOfLevelTotal(l)<=total) return 250;
		if(total<=capacityOfLevelTotal(l-1)) return 0;
		return (total-capacityOfLevelTotal(l-1))/glassesOfLevel(l);
	}
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("sample.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println(i+"/"+testCaseCount);
			line = in.readLine();
			String[] split = line.split(" ");
			int b = Integer.parseInt(split[0]);
			int l = Integer.parseInt(split[1]);
			int n = Integer.parseInt(split[2]);
			double result = solve(b, l ,n);
			out.write("Case #"+(i+1)+": "+result);
			out.newLine();
		}
		in.close();
		out.close();	
	}
}
