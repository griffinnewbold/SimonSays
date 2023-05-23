
public class Exam2 extends Exam1{
	
	
	
	private int numberOfContractors;
	
	public Exam2(int n, int m, String s) {
		super(n,s);
		this.numberOfContractors = m;
	}
	
	
	public int getWorkers() {
		return super.getWorkers() + numberOfContractors;
				
	}
	
	public static void main(String[] args) {
		Exam2 test = new Exam2(5,7,"ashley");
		System.out.println(test.getName());
		System.out.println(test.getWorkers());
		int[] set = {1,2,3,4,5,6,7,8,9,8,7,6,5,4,3,2,1};
		System.out.println(findSecond(set,4));
	}
	
	
	public static int findSecond(int[] a, int x) {
		int index = -1;
		int count = 0;
		for(int i = 0; i < a.length; i++) {
			if(a[i] == x) {
				count++;
				if(count == 2) {
					index = i;
				}
			}
		}
		return index;
	}

}
