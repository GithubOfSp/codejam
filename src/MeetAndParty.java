import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class MeetAndParty {
	public static long[] solve(int[][] region)
	{
		long min = Long.MAX_VALUE;
		long[] ret = new long[3];
		for(int[] block:region)
		{
			for(int i=block[0]; i<=block[2]; i++)
			{
				for(int j=block[1]; j<=block[3]; j++)
				{
//					System.out.println(i+" "+j);
					long sum = 0;
					for(int[] inner:region)
					{
						if(i>inner[0] && i<inner[2])
						{
							sum += ((long)(1+i-inner[0])*(i-inner[0])/2+(1+inner[2]-i)*(inner[2]-i)/2)*(inner[3]-inner[1]+1);
						}
						else
						{
							sum += ((long)Math.abs(i-inner[0])+Math.abs(i-inner[2]))*(inner[2]-inner[0]+1)/2*(inner[3]-inner[1]+1);
						}
						if(j>inner[1] && j<inner[3])
						{
							sum += ((long)(1+j-inner[1])*(j-inner[1])/2+(1+inner[3]-j)*(inner[3]-j)/2)*(inner[2]-inner[0]+1);
						}
						else
						{
							sum += ((long)Math.abs(j-inner[1])+Math.abs(j-inner[3]))*(inner[3]-inner[1]+1)/2*(inner[2]-inner[0]+1);
						}
						//a trivial method
//						for(int k=inner[0]; k<=inner[2]; k++)
//						{
//							for(int l=inner[1]; l<=inner[3]; l++)
//							{
//								sum += Math.abs(k-i)+Math.abs(l-j);
//							}
//						}
					}
					if(sum<min)
					{
						min = sum;
						ret[0] = i;
						ret[1] = j;
						ret[2] = min;
					}
					else if(sum==min)
					{
						if(i<ret[0])
						{
							ret[0] = i;
							ret[1] = j;
						}
						else if(ret[0]==i && j<ret[1])
						{
							ret[1] = j;
						}
					}
				}
			}
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
			System.out.println(i+"/"+testCaseCount);
			line = in.readLine();
			int n = Integer.parseInt(line);
			int[][] region = new int[n][4];
			for(int j=0; j<n; j++)
			{
				line = in.readLine();
				String[] split = line.split(" ");
				for(int k=0; k<4; k++)
				{
					region[j][k] = Integer.parseInt(split[k]);
				}
			}
			long[] result = solve(region);
			out.write("Case #"+(i+1)+": "+result[0]+" "+result[1]+" "+result[2]);
			out.newLine();
		}
		in.close();
		out.close();
	}
}
