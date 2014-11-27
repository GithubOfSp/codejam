import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Addition {
	public static void link(HashMap<String, HashMap<String, Integer>> g, String v)
	{
		if(g.containsKey(v) && !g.get(v).isEmpty())
		{
			LinkedList<String> newETotal = new LinkedList<String>();
			String[] e = new String[g.get(v).keySet().size()];
			g.get(v).keySet().toArray(e);
			while(e.length!=0)
			{
				LinkedList<String> newE = new LinkedList<String>();
				for(String first:e)
				{
					for(String second:g.get(first).keySet())
					{
						if(second.equals(v)) continue;
						String[] eOfSecond = new String[g.get(second).keySet().size()];
						g.get(second).keySet().toArray(eOfSecond);
						for(String third:eOfSecond)
						{
							if(third.equals(first)) continue;
							if(g.get(third).containsKey(v))
							{
								assert(g.get(v).containsKey(third));
							}
							else
							{
								int value = g.get(v).get(first)+g.get(second).get(third)-g.get(first).get(second);
								g.get(v).put(third, value);
								g.get(third).put(v, value);
								newE.add(third);
								newETotal.add(third);
							}
						}
					}
				}
				e = new String[newE.size()];
				newE.toArray(e);
			}
			for(String s:newETotal)
			{
				link(g, s);
			}
		}
	}
	public static void link1(HashMap<String, HashMap<String, Integer>> g, String oldV, String newV)
	{
//		System.out.println("link1:"+oldV+" "+newV);
		LinkedList<String> newE = new LinkedList<String>();
		for(String first:g.get(oldV).keySet())
		{
			if(first.equals(newV)) continue;
			String[] e = new String[g.get(first).keySet().size()];
			g.get(first).keySet().toArray(e);
			for(String second:e)
			{
				if(second.equals(oldV)) continue;
				if(g.get(second).containsKey(newV))
				{
					assert(g.get(newV).containsKey(second));
				}
				else
				{
					int value = g.get(oldV).get(newV)+g.get(first).get(second)-g.get(oldV).get(first);
					g.get(newV).put(second, value);
					g.get(second).put(newV, value);
					newE.add(second);
				}
			}
		}
//		System.out.println(newE);
		for(String s:newE)
		{
			link1(g, s, newV);
//			link2(g, newV, s);
		}
	}
	public static void link2(HashMap<String, HashMap<String, Integer>> g, String a, String b)
	{
//		System.out.println("link2:"+a+" "+b);
		LinkedList<String[]> newE = new LinkedList<String[]>();
		String[] eA = new String[g.get(a).keySet().size()];
		String[] eB = new String[g.get(b).keySet().size()];
		g.get(a).keySet().toArray(eA);		
		g.get(b).keySet().toArray(eB);		
		for(String eOfA:eA)
		{
			if(eOfA.equals(b)) continue;
			for(String eOfB:eB)
			{
				if(eOfB.equals(a)) continue;
				if(g.get(eOfA).containsKey(eOfB))
				{
					assert(g.get(eOfB).containsKey(eOfA));
				}
				else
				{
					int value = g.get(a).get(eOfA)+g.get(b).get(eOfB)-g.get(a).get(b);
					g.get(eOfA).put(eOfB, value);
					g.get(eOfB).put(eOfA, value);
					newE.add(new String[]{eOfA, eOfB});					
				}
			}
		}
//		link1(g, a, b);
//		link1(g, b, a);
		for(String[] s:newE)
		{
			link1(g, s[0], s[1]);
			link1(g, s[1], s[0]);
			link2(g, s[0], s[1]);
		}
	}
	public static void add(HashMap<String, HashMap<String, Integer>> g, String a, String b, int value)
	{
		if(!g.containsKey(a) && !g.containsKey(b))
		{
			HashMap<String, Integer> e = new HashMap<String, Integer>();
			e.put(b, value);
			g.put(a, e);
			e = new HashMap<String, Integer>();
			e.put(a, value);
			g.put(b, e);			
		}
		else if(g.containsKey(a) && g.containsKey(b))
		{
			if(g.get(a).containsKey(b))
			{
				assert(g.get(b).containsKey(a));
			}
			else
			{
				g.get(a).put(b, value);
				g.get(b).put(a, value);
				link2(g, a, b);
			}
		}
		else if(g.containsKey(a))
		{
			HashMap<String, Integer> e = new HashMap<String, Integer>();
			e.put(a, value);
			g.put(b, e);
			g.get(a).put(b, value);
			link1(g, a, b);
		}
		else
		{
			HashMap<String, Integer> e = new HashMap<String, Integer>();
			e.put(b, value);
			g.put(a, e);
			g.get(b).put(a, value);				
			link1(g, b, a);
		}
	}
	public static List<String> solve(String[] in, String[] out)
	{
		List<String> ret = new LinkedList<String>();
		HashMap<String, HashMap<String, Integer>> g = new HashMap<String, HashMap<String, Integer>>();
		int count = 0;
		for(String s:in)
		{
			System.out.println(s+" "+count+++"/"+in.length);
			String[] split = s.split("=");
			String a = split[0].split("\\+")[0];
			String b = split[0].split("\\+")[1];
			int value = Integer.parseInt(split[1]);
			add(g, a, b, value);
			
//			//show graph
//			for(String ssss:g.keySet())
//			{
//				System.out.print(ssss+": ");
//				for(String ss:g.get(ssss).keySet())
//				{
//					System.out.print(ss+" ");
//				}
//				System.out.println();
//			}
		}
		for(int i=0; i<out.length; i++)
		{
			String[] split = out[i].split("\\+");
			if(g.containsKey(split[0]) && g.get(split[0]).containsKey(split[1]))
			{
				assert(g.containsKey(split[1]) && g.get(split[1]).containsKey(split[0]));
				ret.add(out[i]+"="+g.get(split[0]).get(split[1]));
			}
		}
		return ret;
	}
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("C-small-practice.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println((i+1)+"/"+testCaseCount);
			line = in.readLine();
			int n = Integer.parseInt(line);
			String[] input = new String[n];
			for(int j=0; j<n; j++)
			{
				input[j] = in.readLine();
			}
			n = Integer.parseInt(in.readLine());
			String[] output = new String[n];
			for(int j=0; j<n; j++)
			{
				output[j] = in.readLine();
			}
			out.write("Case #"+(i+1)+": ");
			out.newLine();
			for(String s:solve(input, output))
			{
				out.write(s);
				out.newLine();
			}
		}
		in.close();
		out.close();
	}
}
