import java.lang.reflect.Field;


public class IntegerSwap {

 public class IntWrapper {
	 private int value;
	 public IntWrapper(int value){
		 this.value = value;
	 }
	 public int get(){
		 return this.value;
	 }
	 public void set(int value){
		 this.value = value;
	 }
 }
	public static void main(String[] args) {
		IntegerSwap is = new IntegerSwap();
		Integer a = 10;
		Integer b = 20;
		is.swap(a, b);
		System.out.println("a : " + a +", b : " + b);
	}
	public void swap(Integer aArg , Integer bArg){
		try {
			Field valueField = Integer.class.getDeclaredField("value");
			valueField.setAccessible(true);
			int temp = aArg;
			valueField.setInt(aArg, valueField.getInt(bArg));
			valueField.setInt(bArg,temp);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} 
	}
	
	public void swap(IntWrapper aArg , IntWrapper bArg){
		IntWrapper temp = new IntWrapper(aArg.get());
		aArg.set(bArg.get());
		bArg.set(temp.get());
	}
}
