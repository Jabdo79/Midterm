/* Candelaria, Jonathan and Rahul
 * POS Terminal Midterm Project
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class PosTerminal {
	
	static ArrayList<Product> products = new ArrayList<Product>();
	static ArrayList<Product> cart = new ArrayList<Product>();
	static int orderNumber = 1;
	static boolean cont = true;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		//Read the products from text file into arraylist of products
		populateProducts();
		
		System.out.println("Welcome to our Grocery Store!");
		while (cont) {
			//display menu
			displayMenu();
		}
		
	}

	public static void populateProducts() {

		File file;
		final Charset charset = Charset.forName("US-ASCII");
		
		// creates a path including filename
		Path p = Paths.get("src/products.txt");
		// creates a new file using the path
		file = new File(p.toString());
		
		//BigDecimal Formatting to two digits after decimal
		MathContext mc = new MathContext(4);
		
		String line = null;
		boolean empty = true;

		try {
			// create new reader using the file's path and US char set
			BufferedReader br = Files.newBufferedReader(file.toPath(), charset);
			
			// while the line is not empty, create a product from it and store in arraylist
			while ((line = br.readLine()) != null) {
				//splits line by tab
				String[] splitProduct = line.split("\t");
				//creates a product object from split line
				Product product = new Product(splitProduct[0], splitProduct[1], splitProduct[2], new BigDecimal(splitProduct[3], mc));
				//adds product to arraylist
				products.add(product);
				empty = false;
			}
			br.close();

		} catch (IOException e) {
			System.out.println(e);
		}
		//prints if list is empty
		if (empty)
			System.out.println("List is empty!");
	}
	
	public static void displayMenu(){
		
		System.out.println("\nOrder #: " + orderNumber);
		int choice = InputCheck.getInt(sc, "\nCategories\n1.Fruit\n2.Vegetable\n3.Meat\n4.Dairy\n5.Snacks\n6.Checkout\n7.Exit\nChoose one: (1-7) ", 1, 7);
		
		switch(choice){
		case 1:
			displayCategory("Fruit");
			break;
		case 2:
			displayCategory("Vegetable");
			break;
		case 3:
			displayCategory("Meat");
			break;
		case 4:
			displayCategory("Dairy");
			break;
		case 5:
			displayCategory("Snacks");
			break;
		case 6:
			Payment.receipt(cart);//
			orderNumber +=1;
			break;
		case 7:
			System.out.println("\nPlease come again!");
			cont = false;
			break;
		}
	}
	
	public static void displayCategory(String category){
		ArrayList<Product> categoryList = new ArrayList<Product>();
		boolean isEmpty = true;
		int catMenuNum = 0;
		
		System.out.println("\n"+category);
		for (int i = 0; i < products.size(); i++) {
			if(products.get(i).getProductCategory().equalsIgnoreCase(category)){
				isEmpty = false;
				catMenuNum += 1;
				categoryList.add(products.get(i));
				
				System.out.format("%-1d%-25s%-10s%-10s", 
						catMenuNum, 
						"." + products.get(i).getProductName(), 
						"$" + products.get(i).getProductPrice(),
						"-" + products.get(i).getProductDescription());
				System.out.println("");
			}
		}
		if(isEmpty)
			System.out.println("Category is empty!");
		
		System.out.println("0.Go back");
		int prodChoice = InputCheck.getInt(sc, "\nWhat would you like? (0-" + catMenuNum + ") ", 0, catMenuNum);
		if (prodChoice != 0) {
			int prodQty = InputCheck.getInt(sc,
					"How many " + categoryList.get(prodChoice - 1).getProductName() + "'s would you like: ");
			cart.add(categoryList.get(prodChoice - 1).addToCart(prodQty));
			displayCart();
			displayCategory(category);
		}
		
	}
	
	public static void displayCart(){
		System.out.println("\nShopping cart:");
		for (int i = 0; i < cart.size(); i++) {
			System.out.println(cart.get(i).getProductName() + "\t(x" + cart.get(i).getProductQuantity() + ")");
		}
	}
	
}