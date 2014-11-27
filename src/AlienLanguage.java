
public class AlienLanguage {
	public static int[] binarySearch(String[] patterns, char c, int begin, int end, int pos)
	{
		int[] ret = new int[]{-1, -1};
		while(begin<end)
		{
			int mid = (begin+end)/2;
			if(patterns[mid].charAt(pos)==c)
			{
				ret[0] = mid;
				while(ret[0]>begin && patterns[ret[0]-1].charAt(pos)==c) ret[0]--;
				ret[1] = mid+1;
				while(ret[1]<end && patterns[ret[1]].charAt(pos)==c) ret[1]++;
				return ret;
			}
			else if(patterns[mid].charAt(pos)>c)
			{
				end = mid;
			}
			else begin = mid+1;
		}
		return ret;
	}
	public static int solve(String[] patterns, String testCase, int pos, int begin, int end){
		int count = 0;
		if(pos>=patterns[0].length()) return 1;
		if(testCase.startsWith("("))
		{
			int index = testCase.indexOf(")");
			for(int i=1; i<index; i++)
			{
				int[] match = binarySearch(patterns, testCase.charAt(i), begin, end, pos);
				if(match[0]==-1)
				{
					continue;
				}
				count += solve(patterns, testCase.substring(index+1), pos+1, match[0], match[1]);
			}
		}
		else
		{
			int[] match = binarySearch(patterns, testCase.charAt(0), begin, end, pos);
			if(match[0]==-1) return 0;
			count += solve(patterns, testCase.substring(1), pos+1, match[0], match[1]);
		}
		return count;
	}
}
