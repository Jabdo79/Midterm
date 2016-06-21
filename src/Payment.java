import java.util.ArrayList;
import java.util.Scanner;

public class Payment {
	
	private static double subtotal;
	private static double total;
	private static double taxes;
	public static void calcSubtotal(ArrayList <Product> userProducts){
		for (int i = 0; i<userProducts.size();i++)
			subtotal += userProducts.get(i).getProductPrice() * userProducts.get(i).getProductQuantity();
		taxes = subtotal * 0.06;
		total = subtotal + taxes;
		System.out.println("Your subtotal is: " + subtotal);
		System.out.println("Your taxes are: " + taxes);
		System.out.println("Your total is: " + total);
		

	}
	
	public static void cash (Scanner sc) {
		System.out.print("Cash: ");
		double tender = sc.nextDouble();
		double change = tender - total;
		System.out.println("Thank you! Your change is " + change);
	}

	public static int check;
	
	public static int credit;
	
	public static void receipt(ArrayList userProducts) {
		
	}
	
}
