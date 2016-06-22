import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Payment {

	private static Scanner sc;
	// BigDecimal Formatting to two digits after decimal

	private static MathContext mc = new MathContext(4);
	private static BigDecimal subtotal = new BigDecimal(0);
	private static BigDecimal total = new BigDecimal(0);
	private static BigDecimal taxes;

	public static void getPayment() {
		System.out.println("How would you like to pay? "
				+ "Please choose a payment method cash, check or credit: ");
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
			credit();
			break;
		}
	}

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

		taxes = subtotal.multiply(new BigDecimal(0.06, mc), mc);
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
		String ccnum = sc.nextLine();

		while (ccnum.matches("[0-9]+") == false || ccnum.length() != 16) {

			System.out.println("Please enter a valid credit card number.");
			ccnum = sc.nextLine();
		}
		String subCCnum = ccnum.substring(12);
		System.out.print("Enter the expiration (yyyy-MM-dd): ");
		String exp = sc.nextLine();
		LocalDate expirate;
		expirate = LocalDate.parse(exp);
		LocalDate date = LocalDate.now();
		long daysbetween = ChronoUnit.DAYS.between(date, expirate);
		if (daysbetween < 0) {
			System.out
					.println("Sorry, your card has been rejected.  It expired "
							+ Math.abs(daysbetween) + " days ago");
			getPayment();
		} else {
			System.out.print("Enter the CVV: ");
			String cvv = sc.nextLine();
			boolean cont = false;

			while (!cont) {
				if (cvv.length() != 3 || cvv.matches("[0-9]+") == false) {

					cont = false;
					System.out
							.println("Invalid CVV.  Please enter the three digit number on the back of your credit card.");
					cvv = sc.nextLine();
				} else {
					System.out.println("Your credit card (ending in: "
							+ subCCnum + " exp. date: " + exp
							+ ") has been approved!  Thank you.");
					cont = true;
				}
			}
		}
	}

	public static void receipt(ArrayList<Product> userProducts, Scanner scan) {
		sc = scan;
		System.out.println("\nHere's your order: ");
		for (int i = 0; i < userProducts.size(); i++) {

			System.out
					.format("%-25s%-10s%-5s",
							userProducts.get(i).getProductName(),
							"x" + userProducts.get(i).getProductQuantity(),
							"$"
									+ userProducts
											.get(i)
											.getProductPrice()
											.multiply(
													new BigDecimal(
															userProducts
																	.get(i)
																	.getProductQuantity()),
													mc));
			System.out.println("");
		}
		calcSubtotal(userProducts);
		getPayment();
	}
}
