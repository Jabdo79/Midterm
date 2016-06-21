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
	
	public static ArrayList<Product> products;

	public static void main(String[] args) {
		
		//Read the products from text file into arraylist of products
		populateProducts();
		
		Scanner sc = new Scanner(System.in);
		
		
		
		
		System.out.println("Welcome to our Grocery Store!\n");
		

	}

	public static void populateProducts() {

		File file;
		final Charset charset = Charset.forName("US-ASCII");
		
		// creates a path including filename
		Path p = Paths.get("products.txt");
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
}