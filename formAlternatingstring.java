import java.util.Scanner;

public class formAlternatingstring {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
      
		String s = sc.nextline();
		int n = s.length();
      
		int[] v =  new int[n];
		for (int i = 0; i < n; ++i) {
			v[i] = sc.nextInt();
		}

		int reg = 0;
		int lw = s.charAt(0) - '0';
		int lwv = v[0];

		for (int i = 1; i < n; ++i) {
			if (s.charAt(i) - '0' == lw) {
				reg += Math.min(lwv, v[i]);
				lwv = Math.max(lwv, v[i]);
			} else {
				lw = s.charAt(i) - '0';
				lwv = v[i];
			}
		}
		System.out.print(reg);
	}
}

