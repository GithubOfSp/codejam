import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CardGame {
	public static int solve(List<Integer> array)
	{
		boolean finished = false;
		while(!finished)
		{
			finished = true;
			for(int i=1; i<array.size()-1; i++)
			{
				if(array.get(i-1).equals(array.get(i)) && array.get(i).equals(array.get(i+1)))
				{
					array.remove(i-1);
					array.remove(i-1);
					array.remove(i-1);
					finished = false;
					break;
				}
			}
		}
		return array.size();
	}
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("C-small-attempt1.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println(i+"/"+testCaseCount);
			line = in.readLine();
			String[] split = line.split(" ");
			int n = Integer.parseInt(split[0]);
			line = in.readLine();
			split = line.split(" ");
			ArrayList<Integer> array = new ArrayList<Integer>();
			for(int j=0; j<n; j++)
			{
				array.add(Integer.parseInt(split[j]));
			}
			int result = solve(array);
			out.write("Case #"+(i+1)+": "+result);
			out.newLine();
		}
		in.close();
		out.close();
	}
}
