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

			// while the line is not empty, print it
			while ((line = br.readLine()) != null) {
				String[] splitProduct = line.split("\t");
				Product product = new Product();
				empty = false;
			}
			br.close();

		} catch (IOException e) {
			System.out.println(e);
		}

		if (empty)
			System.out.println("List is empty!");
	}
}