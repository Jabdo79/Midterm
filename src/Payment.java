import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class Payment {

	private static Scanner sc;
	// BigDecimal Formatting to two digits after decimal

	private static MathContext mc = new MathContext(4);
	private static BigDecimal subtotal = new BigDecimal(0);
	private static BigDecimal total = new BigDecimal(0);
	private static BigDecimal taxes;

	public static void calcSubtotal(ArrayList<Product> userProducts) {

		for (int i = 0; i < userProducts.size(); i++) {
			subtotal = subtotal.add(
					userProducts
							.get(i)
							.getProductPrice()
							.multiply(
									new BigDecimal(userProducts.get(i)
											.getProductQuantity())), mc);
		}

		taxes = subtotal.multiply(new BigDecimal(0.06), mc);
		total = subtotal.add(taxes, mc);
		subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
		System.out.println("\nYour subtotal is: $" + subtotal);

		taxes = taxes.setScale(2, RoundingMode.HALF_UP);
		System.out.println("Your taxes are: $" + taxes);

		total = total.setScale(2, RoundingMode.HALF_UP);
		System.out.println("Your total is: $" + total);
	}

	public static void cash() {
		System.out.print("Cash: ");
		BigDecimal tender = new BigDecimal(sc.nextDouble());
		BigDecimal change = tender.subtract(total, mc);
		if (tender.compareTo(total) < 0) {
			System.out.println("The remaining balance is: " + change.abs()
					+ " Please settle your balance.");

		} else {

			System.out.println("Thank you! Your change is " + change);
		}
	}

	public static void check() {
		System.out.print("Please enter you check number: ");

		int checkNumber = sc.nextInt();

		System.out.println("Thank you! Your check number: " + checkNumber
				+ "has been aproved.");

	}

	public static void credit() {
		System.out.print("Enter your credit card number: ");
		int ccnum = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter the expiration: ");
		String exp = sc.nextLine();
		System.out.print("Enter the CVV: ");
		int cvv = sc.nextInt();
		System.out.println("Your credit card (number: " + ccnum
				+ " exp. date: " + exp + ") has been approved!  Thank you.");
	}

	public static void receipt(ArrayList<Product> userProducts, Scanner scan) {
		sc = scan;
		System.out.println("\nHere's your order: ");
		for (int i = 0; i < userProducts.size(); i++) {

			System.out.print(userProducts.get(i).getProductName()
					+ "\t"
					+ (userProducts.get(i).getProductQuantity())
					+ "\t\t$"
					+ userProducts
							.get(i)
							.getProductPrice()
							.multiply(
									new BigDecimal(userProducts.get(i)
											.getProductQuantity()), mc) + "\n");

		}
		calcSubtotal(userProducts);
		System.out.println("How would you like to pay? "
				+ "Please choose a payment method cash, payment or credit: ");
		String paymentChoice = sc.nextLine().toLowerCase();
		System.out.println("Your choice: " + paymentChoice);
		switch (paymentChoice) {
		case "cash":

			cash();
			break;

		case "check":
			check();
			break;

		case "credit":
			break;
		}

	}
}
