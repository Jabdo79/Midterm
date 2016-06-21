/* Candelaria, Jonathan and Rahul
 * POS Terminal Midterm Project
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PosTerminal {
	
	static ArrayList<Product> products = new ArrayList<Product>();
	static ArrayList<Product> cart = new ArrayList<Product>();
	static int orderNumber = 0;
	static boolean cont = true;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		//Read the products from text file into arraylist of products
		populateProducts();
		
		System.out.println("Welcome to our Grocery Store!\n");
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
				Product product = new Product(splitProduct[0], splitProduct[1], splitProduct[2], Double.parseDouble(splitProduct[3]));
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
		
		orderNumber +=1;
		System.out.println("Order #: " + orderNumber);
		int choice = InputCheck.getInt(sc, "\nCategories\n1.Fruit\n2.Vegetable\n3.Meat\n4.Dairy\n5.Snacks\n6.Exit\nChoose one: (1-6) ", 1, 5);
		
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
			cont = false;
			break;
		}
	}
	
	public static void displayCategory(String category){
		ArrayList<Product> categoryList = new ArrayList<Product>();
		boolean isEmpty = true;
		int catMenuNum = 1;
		
		System.out.println("\n"+category);
		for (int i = 0; i < products.size(); i++) {
			if(products.get(i).getProductCategory().equalsIgnoreCase(category)){
				categoryList.add(products.get(i));
				
				System.out.println(catMenuNum + "." + products.get(i).getProductName() + "\t\t$" + products.get(i).getProductPrice());
				System.out.println("  " + products.get(i).getProductDescription());
				
				catMenuNum += 1;
				isEmpty = false;
			}
		}
		if(isEmpty)
			System.out.println("Category is empty!");
		
		int prodChoice = InputCheck.getInt(sc, "\nWhat would you like? (1-" + catMenuNum + ") ", 1, catMenuNum);
		int prodQty = InputCheck.getInt(sc, "How many " + categoryList.get(prodChoice-1).getProductName() + "'s would you like? ");
		cart.add(categoryList.get(prodChoice-1).clone(prodQty));//create new method in product class that will clone name and price from object passed to it, also take qty in param.
	}
	
}