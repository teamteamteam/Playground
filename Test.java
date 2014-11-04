import java.util.Random;


public class Test {

	private static int summe = 0;
	private static int[] foo;
	
	public static void main(String[] args) {
		Test.foo = new int[10];
		foo[0] = 15;
		foo[1] = 15;
		foo[2] = 15;
		foo[3] = 15;
		foo[4] = 15;
		foo[5] = 1;
		foo[6] = 15;
		foo[7] = 70;
		foo[8] = 15;
		foo[9] = 15;

		for(int i=0; i<Test.foo.length; i++) {
			Test.summe += Test.foo[i];
		}
		
		for(int h=0; h<20; h++) {
			Test.choose();
		}
	}
	
	public static void choose() {
		//dauerhaft
		Random random = new Random();
		int r = random.nextInt(Test.summe - 1) + 1;
		
		for(int k=0; k<Test.foo.length; k++) {
			r -= Test.foo[k];
			if(r <= 0) {
				System.out.println(k + " ist es geworden!");
				break;
			}
		}		
	}
}
