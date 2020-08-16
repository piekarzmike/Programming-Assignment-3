/*
**********************************************************************************************
	Author: Mike Piekarz

	Date: 8/14/2020
	
	Course Name: Programming Fundamentals	
	
	Semester: 2020 Summer

	Assignment Name: Programming Assignment 3 – Machine Learning
**********************************************************************************************
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NearestNeighbor {
	// Declare variables
	static Scanner scan = new Scanner(System.in);
	static double[][] trainingAttArray;
	static String[] trainingClassArray;
	static double[][] testingAttArray;
	static String[] testingClassArray;
	static double[] arrayDist;

	// Set array max for row and column for arrays
	static int rowMax = 75;
	static int colMax = 4;

	public static void main(String[] args) {
		// Print program header and prompt user for file name
		System.out.println("Programming Fundamentals" + "\nName: Mike Piekarz" + "\nPROGRAMMING ASSIGNMENT 3" + "\n");

		// Prompt user to input training file name and instantiate entered name to a
		// variable
		System.out.print("Enter the name of the training file: ");
		String trainingFile = scan.nextLine();

		// Prompt user to input training file name and instantiate entered name to a
		// variable
		System.out.print("Enter the name of the testing file: ");
		String testingFile = scan.nextLine();

		// Print header results
		System.out.println("\nEX#: TRUE LABEL, PREDICTED LABEL");

		// Instantiate array variables through the use of calling methods calling
		trainingAttArray = createAttributeArray(trainingFile);
		trainingClassArray = createClassArray(trainingFile);
		testingAttArray = createAttributeArray(testingFile);
		testingClassArray = createClassArray(testingFile);

		testDistance(testingAttArray, trainingAttArray, testingClassArray, trainingClassArray);

	}

// ------------------------------------------------------------------------------------------------------
// Returns a 2d Array based upon the attribute data within the training and testing file 
// ------------------------------------------------------------------------------------------------------
	static double[][] createAttributeArray(String attributesFile) {
		// Create file object based upon the inputed file name
		File file = new File(attributesFile);

		// Declare and instantiate variables
		double[][] attributeArray = new double[rowMax][colMax];
		String line = "";
		int row = 0;

		try {
			// Create scanner
			Scanner scanFile = new Scanner(file);

			// Loop through lines of the file until blank
			while (scanFile.hasNextLine()) {
				// read line from file
				line = scanFile.nextLine();
				// split line into an array at the commas
				String[] attArray = line.split(",");

				// copy the content of the trainArray to the arrayTrainA
				for (int col = 0; col < attArray.length - 1; col++) {
					attributeArray[row][col] = Double.parseDouble(attArray[col]);
				}
				row++;
			}
			scanFile.close();// Close file

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return attributeArray;
	}

// ------------------------------------------------------------------------------------------------------
// Returns a Array based upon the class data within the training and testing file 
// ------------------------------------------------------------------------------------------------------		
	static String[] createClassArray(String trainingFileName) {
		// Set file path and create file object
		File file = new File(trainingFileName);
		
		// Declare and instantiate variables
		String[] classArray = new String[rowMax];
		String line = "";

		int row = 0;

		try {
			// setup scanner
			Scanner scanFile = new Scanner(file);

			// while input line = trainFile. next line()) != null)
			while (scanFile.hasNextLine()) {
				// read line in form file
				line = scanFile.nextLine();
				// split the inputLine into an array at the commas
				String[] clArray = line.split(",");
				// copy the content of the trainArray to the arrayTrainCSV
				classArray[row] = clArray[clArray.length - 1];
				row++;
			}
			scanFile.close();// Close file

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return classArray;
	}

// ------------------------------------------------------------------------------------------------------
// Return the calculated distance between the attributes for the test and training file utilizing the Nearest Neighbor algorithm 
// ------------------------------------------------------------------------------------------------------
	static double distanceCalc(double[] testArray, double[] trainArray) {

		double dist = 0;

		dist = Math.sqrt(Math.pow(testArray[0] - trainArray[0], 2) + Math.pow(testArray[1] - trainArray[1], 2)
				+ Math.pow(testArray[2] - trainArray[2], 2) + Math.pow(testArray[3] - trainArray[3], 2));

		return dist;
	}

// ------------------------------------------------------------------------------------------------------
// Compare class names within testing file to training file and identify if match based upon the closest distances 
// ------------------------------------------------------------------------------------------------------		
	static boolean classCompare(int testRow, int trainRow, String[] testClass, String[] trainClass) {

		// Check to see if class name matches between testing and training file
		if (testClass[testRow].equals(trainClass[trainRow])) {
			return true;
		} else {
			return false;
		}
	}

// ------------------------------------------------------------------------------------------------------
// Loops through and identifies the row within the training file that has the shortest distance.
// ------------------------------------------------------------------------------------------------------	
	static void testDistance(double[][] testAttributes, double[][] trainAttributes, String[] testClass,
			String[] trainClass) {

		int count = 0;
		String[] predictiveClass = new String[testAttributes.length];

		// Loop through testing file
		for (int row = 0; row < testAttributes.length; row++) {
			double currentMinDist = 0;
			int currentMinTrainRow = 0;

			// Loop through array of training file and call the distanceCalc method to calculate the closest distance
			for (int trainRow = 0; trainRow < trainAttributes.length; trainRow++) {
				double currentDist = distanceCalc(testAttributes[row], trainAttributes[trainRow]);
				if (currentDist < currentMinDist || currentMinDist == 0) {
					currentMinDist = currentDist;
					currentMinTrainRow = trainRow;
				}
			}
			// Print out class name of testing file and training file based upon the closet match
			predictiveClass[row] = trainClass[currentMinTrainRow];
			System.out.println((row + 1) + ":" + " " + testClass[row] + " " + predictiveClass[row]);

			// Track number of matches
			if (classCompare(row, currentMinTrainRow, testClass, trainClass)) {
				count += 1;
			}
		}
		// Calculate the % match of class names and print overall accuracy
		System.out.println("ACCURACY: " + (double) count / testAttributes.length);
	}

}
