import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Inventory {
	
	private static ArrayList<Product> products = new ArrayList<Product>();
	private static ArrayList<Product> cart = new ArrayList<Product>();
	private static int orderNumber = 1;
	private static Scanner sc = new Scanner(System.in);
	private static File file;
	private static final Charset charset = Charset.forName("US-ASCII");
	
	public static void populateProducts() {

		Path p = Paths.get("src/products.txt");
		file = new File(p.toString());
		
		//BigDecimal Formatting to two digits after decimal
		MathContext mc = new MathContext(4);
		
		String line = null;
		boolean empty = true;

		try {
			BufferedReader br = Files.newBufferedReader(file.toPath(), charset);
			
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
		boolean cont = true;
		
		while (cont) {
			System.out.println("\nOrder #: " + orderNumber);
			int choice = InputCheck.getInt(sc,
					"\nCategories\n1.Fruit\n2.Vegetable\n3.Meat\n4.Dairy\n5.Snacks\n6.Checkout\n7.Add Product\n8.Exit\nChoose one: (1-8) ",
					1, 8);
			switch (choice) {
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
				Payment.receipt(cart, sc);
				orderNumber += 1;
				break;
			case 7:
				addProduct();
				break;
			case 8:
				System.out.println("\nPlease come again!");
				cont = false;
				break;
			}
		}
	}
	
	private static void displayCategory(String category){
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
			//CATCH 0 QTY, if they enter 0 do nothing
			int prodQty = InputCheck.getInt(sc,
					"How many " + categoryList.get(prodChoice - 1).getProductName() + "'s would you like: ");
			cart.add(categoryList.get(prodChoice - 1).addToCart(prodQty));
			displayCart();
			displayCategory(category);
		}
		
	}
	
	private static void displayCart(){
		System.out.println("\nShopping cart:");
		for (int i = 0; i < cart.size(); i++) {
			System.out.println(cart.get(i).getProductName() + "\t(x" + cart.get(i).getProductQuantity() + ")");
		}
	}
	
	private static void addProduct(){
		System.out.print("\nEnter product name: ");
		String name = sc.nextLine().trim();
		System.out.print("Enter product category: ");
		String cat = sc.nextLine().trim();
		System.out.print("Enter product description: ");
		String desc = sc.nextLine().trim();
		System.out.print("Enter product price: ");
		BigDecimal price = new BigDecimal(InputCheck.getDouble(sc)).setScale(2, RoundingMode.HALF_UP);
		String prod = name + "\t" + cat + "\t" + desc + "\t" + price;
		
		if (!containsProduct(name)) {
			try {
				// opens file with intention of appending
				FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				// write to file
				bw.write(prod + "\n");
				bw.close();

			} catch (IOException e) {
				System.out.println(e);
			}
			System.out.println(name + " has been saved!");
			populateProducts();
		}
		else
			System.out.println(name+" is already in the list.");
	}
	
	private static boolean containsProduct(String name){
		boolean found = false;
		
		for(int i = 0; i < products.size(); i++){
			if(products.get(i).getProductName().equalsIgnoreCase(name)){
				found = true;
				break;
			}	
		}
		return found;
	}
}
