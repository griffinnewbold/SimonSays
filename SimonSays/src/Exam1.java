import java.util.*;


public class Exam1 {

	private String name;
	private int numOfEmployees;
	
	public Exam1(int n, String m) {
		name = m;
		numOfEmployees = n;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWorkers() {
		return numOfEmployees;
	}
	/*
	public static int fib(int n) {
		if(n==0)
			return 1;
		else if(n=1)
			return 1;
		int nminus2;
		int nminus1 = 1;
		int current = 2;
		for(i = 2; i<=n;i++) {
			nminus2 = nminus1;
			nminus1 = current;
			current = nminus1 + nminus2;
		}
		current;
	}
	*/
	
	public static void main(String[] args) {
		int[] arr = {3,1,4,2,8,0};
		for (int i = 0; i < arr.length - 1; i++)  
        {  
            int index = i;  
            for (int j = i + 1; j < arr.length; j++){  
                if (arr[j] < arr[index]){  
                    index = j;//searching for lowest index  
                }  
            }  
            int smallerNumber = arr[index];   
            arr[index] = arr[i];  
            arr[i] = smallerNumber;  
            System.out.println(i + Arrays.toString(arr));
        }
		
		HashMap<Integer,Integer> map = new HashMap<>();
		int[] g = {5,4,4,3,1,1,2,4};
		for(int i: g) {
			if(map.get(i)==null) {
				map.put(i,1);
			}else {
				int temp = map.get(i);
				map.put(i, temp+1);
			}
		}
		System.out.println("hash:" + map.get(4));
		
		int x = 10;
		int y = 7;
		double z = 2.0;
		Integer a = new Integer(50);
		double[] b = {1.1,1.2,1.3};
		String c = "john";
		String d = "John";
		Object e = d;
		
		//System.out.println(fib(5));
		try {
			y = y+4;
			//throw new Exception();
			y++;
		}catch(Exception f) {
			y--;
		}
		System.out.println(y);
		
	}
	
}
