
import java.util.*;
public class Hw1 {
	public static void main(String[] args){
		Scanner s =new Scanner(System.in);
		int k =s.nextInt();
		int n =s.nextInt();
		for(int i=0;i<=n;i++) {
			int line2= 2*i;
			int star2=2*k;
			for(int space=n-i;space>0;space--) {//printing space
				System.out.print(" ");
			}
			if(line2+1<=star2) {
				for(int star=1;star<=line2+1;star++) {
					System.out.print("*");
				}
				System.out.println();
			}
			else {
				for(int star=1;star<=k;star++) {
					System.out.print("*");
				}
				for(int space=1;space<=line2-star2+1;space++) {
					System.out.print(" ");
				}
				for(int star=1;star<=k;star++) {
					System.out.print("*");
				}
				System.out.println();
			}
		}
		for(int i=1;i<=n;i++) {
			int line2=2*i;
			int star2=2*k;
			for(int space=1;space<=i;space++) {//printing space
				System.out.print(" ");
			}
			if(2*n-line2+1<=star2) {
				for(int star=1;star<=2*n+1-line2;star++) {
					System.out.print("*");
				}
				System.out.println();
			}
			else {
				for(int star=1;star<=k;star++) {
					System.out.print("*");
				}
				for(int space=1;space<=2*n+1-star2-line2;space++) {
					System.out.print(" ");
				}
				for(int star=1;star<=k;star++) {
					System.out.print("*");
				}
				System.out.println();
			}
		}
		
		


}
}
