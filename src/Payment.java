import java.util.ArrayList;
import java.util.Scanner;

public class Payment {

	private static double subtotal;

	public static void calcSubtotal(ArrayList<Product> userProducts) {

	}

	public static void cash(Scanner sc) {
		System.out.print("Cash: ");
		double tender = sc.nextDouble();
		double change = tender - subtotal;
		System.out.println("Thank you! Your change is " + change);
	}

	public static int check;

	public static int credit;

	public static void receipt(ArrayList<Product> userProducts) {

		for (int i = 0; i < userProducts.size(); i++) {

			System.out.print(userProducts.get(i).getProductName()
					+ "\t"
					+ (userProducts.get(i).getProductQuantity())
					+ "\t\t$"
					+ ((userProducts.get(i).getProductPrice()) * (userProducts
							.get(i).getProductQuantity())));

			// call: CalcSubtotal();
		}
	}
}
