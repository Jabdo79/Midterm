/* Candelaria, Jonathan and Rahul
 * POS Terminal Midterm Project
 */
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Payment {

	private static Scanner sc;
	private static BigDecimal subtotal = new BigDecimal(0);
	private static BigDecimal total = new BigDecimal(0);
	private static BigDecimal taxes;

	public static void receipt(ArrayList<Product> userProducts,
			int orderNumber, Scanner scan) {
		sc = scan;
		System.out.println("\n-------------------------------------------");
		System.out.println("Order #: " + orderNumber);
		System.out.println("-------------------------------------------");
		for (int i = 0; i < userProducts.size(); i++) {

			System.out.format(
					"%-25s%-10s%-5s",
					userProducts.get(i).getProductName(),
					"x" + userProducts.get(i).getProductQuantity(),
					"$"
							+ userProducts
									.get(i)
									.getProductPrice()
									.multiply(
											new BigDecimal(userProducts.get(i)
													.getProductQuantity())));
			System.out.println("");
		}
		System.out.println("-------------------------------------------");
		calcTotal(userProducts);
		System.out.println("-------------------------------------------");
		choosePayment();
	}

	public static void calcTotal(ArrayList<Product> userProducts) {

		for (int i = 0; i < userProducts.size(); i++) {
			subtotal = subtotal.add(userProducts
					.get(i)
					.getProductPrice()
					.multiply(
							new BigDecimal(userProducts.get(i)
									.getProductQuantity())));
		}

		taxes = subtotal.multiply(new BigDecimal(0.06));
		total = subtotal.add(taxes);
		subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
		System.out.println("\nYour subtotal is: $" + subtotal);

		taxes = taxes.setScale(2, RoundingMode.HALF_UP);
		System.out.println("Your taxes are: $" + taxes);

		total = total.setScale(2, RoundingMode.HALF_UP);
		System.out.println("Your total is: $" + total);
	}

	public static void choosePayment() {
		int paymentChoice = InputCheck
				.getInt(sc,
						"\nChoose a Payment Method\n1.Cash\n2.Check\n3.Credit\nHow would you like to pay? (1-3) ",
						1, 3);
		switch (paymentChoice) {
		case 1:
			cash();
			break;
		case 2:
			check();
			break;
		case 3:
			credit();
			break;
		}
	}

	// cash payment input tender amount
	public static void cash() {
		System.out.println("\nYour total is: " + total);
		BigDecimal tender = new BigDecimal(InputCheck.getDouble(sc,
				"Enter cash amount: "));
		BigDecimal change = tender.subtract(total).setScale(2,
				RoundingMode.HALF_UP);
		// check if amount is correct or not
		// ask to remain the balance
		if (tender.compareTo(total) < 0) {
			System.out.println("The remaining balance is: " + change.abs());
			total = change.abs();
			sc.nextLine();
			choosePayment();
			// amount is ok...system runs
		} else {
			if (change.equals(new BigDecimal(0.00).setScale(2,
					RoundingMode.HALF_UP))) {
				System.out.println("\nThank you! Please come again!");
			} else
				System.out.println("Your change is " + change
						+ "\nThank you! Please come again!");
		}
	}

	public static void check() {

		int checkNumber = InputCheck.getInt(sc,
				"\nPlease enter you check number: ");

		System.out.println("\nThank you! Your check number: " + checkNumber
				+ " has been aproved.");

	}

	public static void credit() {
		// input user card nr.
		System.out.print("\nEnter your credit card number: ");
		String ccnum = sc.nextLine();
		// card number validation
		while (ccnum.matches("[0-9]+") == false || ccnum.length() != 16) {
			System.out.println("Please enter a valid credit card number.");
			ccnum = sc.nextLine();
		}
		String subCCnum = ccnum.substring(12);
		LocalDate expirate = null;
		// date validation
		while (expirate == null) {
			System.out.print("Enter the expiration (yyyy/MM/dd): ");
			String exp = sc.nextLine();
			DateTimeFormatter dateFormat = DateTimeFormatter
					.ofPattern("yyyy/MM/dd");
			try {
				expirate = LocalDate.parse(exp, dateFormat);
			} catch (Exception e) {
				System.out
						.println("Your date format is invalid. Please try again.");
			}
		}
		// Card expiration date validation
		LocalDate date = LocalDate.now();
		long daysbetween = ChronoUnit.DAYS.between(date, expirate);
		if (daysbetween < 0) {
			System.out
					.println("Sorry, your card has been rejected.  It expired "
							+ Math.abs(daysbetween) + " days ago");
			choosePayment();
			// validation CVV
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
					System.out
							.println("Your credit card (ending in: "
									+ subCCnum
									+ ") has been approved.  \nThank you! Please come again!");
					cont = true;
				}
			}
		}
	}
}
