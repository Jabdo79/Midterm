import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Payment {
	// String.format("%.2f", floatValue);

	public static NumberFormat nd = new DecimalFormat("#0.00");
	//BigDecimal Formatting to two digits after decimal
	private static MathContext mc = new MathContext(4);
	private static BigDecimal subtotal = new BigDecimal(0);
	private static BigDecimal total;
	private static BigDecimal taxes;

	public static void calcSubtotal(ArrayList<Product> userProducts) {
		
		for (int i = 0; i < userProducts.size(); i++){
			subtotal = subtotal.add(userProducts.get(i).getProductPrice().multiply(new BigDecimal(userProducts.get(i).getProductQuantity())), mc);
		}
		
		taxes = subtotal.multiply(new BigDecimal(0.06), mc);
		total = subtotal.add(taxes, mc);
		System.out.println("\nYour subtotal is: $" + subtotal);

		System.out.println("Your taxes are: $" + taxes);
		System.out.println("Your total is: $" + total);
	}

	public static void cash(Scanner sc) {
		System.out.print("Cash: ");
		BigDecimal tender = new BigDecimal(sc.nextDouble());
		BigDecimal change = tender.subtract(total, mc);
		System.out.println("Thank you! Your change is " + change);
	}

	public static int check;

	public static int credit;

	public static void receipt(ArrayList<Product> userProducts) {
		System.out.println("\nHere's your order: ");
		for (int i = 0; i < userProducts.size(); i++) {

			System.out.print(userProducts.get(i).getProductName()
					+ "\t"
					+ (userProducts.get(i).getProductQuantity())
					+ "\t\t$"
					+ userProducts.get(i).getProductPrice().multiply(new BigDecimal(userProducts.get(i).getProductQuantity()), mc) + "\n");

		}
		calcSubtotal(userProducts);
	}
}
