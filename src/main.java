import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class main {
	public static void main(String[] args) throws IOException
	{
		String x = "a+a";
		String a = x.split("\\+")[0];
		String b = x.split("\\+")[1];
		System.out.println(Arrays.toString(x.split("\\+")));
	}
}
