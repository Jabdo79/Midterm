
/* Candelaria, Jonathan and Rahul
 * POS Terminal Midterm Project
 */


public class PosTerminal {

	public static void main(String[] args) {
		// Read the products from text file into arraylist of products
		Inventory.populateProducts();

		System.out.println("Welcome to our Grocery Store!");
		// display menu
		Inventory.displayMenu();
	}
}