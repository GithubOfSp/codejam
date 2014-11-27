import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class TakingMetro {
	static class Vertex
	{
		HashMap<Vertex, Integer> edges;
		int label;
		Vertex()
		{
			label = Integer.MAX_VALUE;
		}
		public void addEdge(Vertex neighbour, int right)
		{
			if(edges==null)
			{
				edges = new HashMap<Vertex, Integer>();
			}
			edges.put(neighbour, right);
		}
	}
	//Dijkstra
	static int minDistance(Vertex start, Vertex target)
	{
		Comparator<Vertex> compare = new Comparator<Vertex>()
		{
			public int compare(Vertex v1, Vertex v2)
			{
				return v1.label-v2.label;
			}
		};
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(11, compare);
		start.label = 0;
		queue.offer(start);
		while(!queue.isEmpty())
		{
			Vertex cur = queue.poll();
			for(Vertex v:cur.edges.keySet())
			{
				int distance = cur.label+cur.edges.get(v);
				if(distance<0) System.out.println(distance+"="+cur.label+"+"+cur.edges.get(v));
				if(distance<v.label)
				{
					v.label = distance;
					queue.offer(v);
				}
			}
		}
		return target.label;
	}
	public static int[] solve(int[][] n, int[][] m, int[][] q)
	{
		Vertex[][] stations = new Vertex[n.length][];
		Vertex[][] tunnels = new Vertex[n.length][];
		for(int i=0; i<stations.length; i++)
		{
			stations[i] = new Vertex[n[i].length];
			tunnels[i] = new Vertex[n[i].length];
			for(int j=0; j<stations[i].length; j++)
			{
				stations[i][j] = new Vertex();
				if(j>0)
				{
					stations[i][j-1].addEdge(stations[i][j], n[i][j]);
					stations[i][j].addEdge(stations[i][j-1], n[i][j]);
				}				
			}
		}
		for(int i=0; i<m.length; i++)
		{
			if(tunnels[m[i][0]-1][m[i][1]-1]==null) 
				tunnels[m[i][0]-1][m[i][1]-1] = new Vertex();
			if(tunnels[m[i][2]-1][m[i][3]-1]==null) 
				tunnels[m[i][2]-1][m[i][3]-1] = new Vertex();
			if(tunnels[m[i][0]-1][m[i][1]-1].edges!=null
				&& tunnels[m[i][0]-1][m[i][1]-1].edges.containsKey(tunnels[m[i][2]-1][m[i][3]-1]) 
				&& tunnels[m[i][0]-1][m[i][1]-1].edges.get(tunnels[m[i][2]-1][m[i][3]-1])<=m[i][4])
				continue;
			tunnels[m[i][0]-1][m[i][1]-1].addEdge(tunnels[m[i][2]-1][m[i][3]-1], m[i][4]);
			tunnels[m[i][2]-1][m[i][3]-1].addEdge(tunnels[m[i][0]-1][m[i][1]-1], m[i][4]);
			stations[m[i][0]-1][m[i][1]-1].addEdge(tunnels[m[i][0]-1][m[i][1]-1], 0);
			tunnels[m[i][0]-1][m[i][1]-1].addEdge(stations[m[i][0]-1][m[i][1]-1], n[m[i][0]-1][0]);
			stations[m[i][2]-1][m[i][3]-1].addEdge(tunnels[m[i][2]-1][m[i][3]-1], 0);
			tunnels[m[i][2]-1][m[i][3]-1].addEdge(stations[m[i][2]-1][m[i][3]-1], n[m[i][2]-1][0]);
		}
		//debug
//		for(Vertex[] lines:stations)
//		{
//			System.out.print("line: ");
//			for(Vertex station:lines)
//			{
//				System.out.print(station+" (");
//				for(Vertex neighbour:station.edges.keySet())
//				{
//					System.out.print(neighbour+":"+station.edges.get(neighbour)+",");
//				}
//				System.out.print(") ");
//			}
//			System.out.println();
//		}
		int[] ret = new int[q.length];
		for(int i=0; i<q.length; i++)
		{
			for(Vertex[] lines:stations)
			{
				for(Vertex station:lines)
				{
					station.label = Integer.MAX_VALUE;
				}
			}
			for(Vertex[] linesTunnel:tunnels)
			{
				for(Vertex tunnel:linesTunnel)
				{
					if(tunnel!=null)
						tunnel.label = Integer.MAX_VALUE;
				}
			}
			Vertex start, end;
			if(tunnels[q[i][0]-1][q[i][1]-1]==null)
			{
				start = stations[q[i][0]-1][q[i][1]-1];
				ret[i] += n[q[i][0]-1][0];
			}
			else
				start = tunnels[q[i][0]-1][q[i][1]-1];
			if(tunnels[q[i][2]-1][q[i][3]-1]==null)
				end = stations[q[i][2]-1][q[i][3]-1];
			else
				end = tunnels[q[i][2]-1][q[i][3]-1];
			int min = minDistance(start, end);
			ret[i] = min==Integer.MAX_VALUE?-1:min+ret[i];
		}
		return ret;
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("B-large-practice.in"));
		String line = in.readLine();
		int testCaseCount = Integer.parseInt(line);
		BufferedWriter out = new BufferedWriter(new FileWriter("output"));
		for(int i=0; i<testCaseCount; i++)
		{
			System.out.println("Case:"+(i+1));
			in.readLine();
			int lineCount = Integer.parseInt(in.readLine());
			int[][] n = new int[lineCount][];
			for(int j=0; j<lineCount; j++)
			{
				line = in.readLine();
				String[] split = line.split(" ");
				int stationCount = Integer.parseInt(split[0]);
				int timeToGetOn = Integer.parseInt(split[1]);
				n[j] = new int[stationCount];
				n[j][0] = timeToGetOn;
				line = in.readLine();
				split = line.split(" ");
				for(int k=1; k<stationCount; k++)
				{
					n[j][k] = Integer.parseInt(split[k-1]);
				}
			}
			int tunnelCount = Integer.parseInt(in.readLine());
			int[][] m = new int[tunnelCount][5];
			for(int j=0; j<tunnelCount; j++)
			{
				line = in.readLine();
				String[] split = line.split(" ");
				for(int k=0; k<5; k++)
				{
					m[j][k] = Integer.parseInt(split[k]);
				}
			}
			int queryCount = Integer.parseInt(in.readLine());
			int[][] q = new int[queryCount][4];
			for(int j=0; j<queryCount; j++)
			{
				line = in.readLine();
				String[] split = line.split(" ");
				for(int k=0; k<4; k++)
				{
					q[j][k] = Integer.parseInt(split[k]);
				}
			}			
//			if(i!=0) continue;
			int[] result = solve(n, m, q);
			out.write("Case #"+(i+1)+": ");
			out.newLine();
			for(int j:result)
			{
				out.write(""+j);
				out.newLine();
			}
		}
		in.close();
		out.close();
	}
}
