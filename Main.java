package assignment2;
/**
 * File: Assignment1
 * Class: CSCI 3341
 * Author: Junior Meador
 * Created on: November 16, 2023
 * Last Modified: November 29, 2023
 * Description: Implementing Page Replacement Algorithms
 */
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		// Create a new scanner to read user input
		Scanner scanner = new Scanner(System.in);

		// Create a boolean to control the program loop
		boolean runProgram = true;
		while (runProgram) {
			// Input for reference string
			System.out
					.printf("Enter up to 15 single-digit reference strings (comma-separated), or enter 9999 to exit: ");
			String input = scanner.nextLine();

			// Check if the user wants to exit
			if ("9999".equals(input.trim())) {
				System.out.printf("Exiting program...\n");
				runProgram = false;
				continue;
			}

			// Validate the reference string
			if (!input.matches("([0-9](\\s*,\\s*)?){0,14}[0-9]?")) {
				System.out
						.printf("Invalid reference string. Please enter up to 15 single digits separated by commas.\n");
				continue;
			}

			// Define the referenceString as a list of strings
			String[] refStringArray = input.split("\\s*,\\s*");
			List<String> referenceString = Arrays.asList(refStringArray);

			boolean validInput = false;
			int algorithmChoice = 0;

			while (!validInput) {
				try {
					// Input for page replacement algorithm choice
					System.out.printf(
							"Select Page Replacement Algorithm (1: FIFO, 2: Optimal, 3: LRU Clock, 4: LRU Approximation, 5: LFU): ");
					algorithmChoice = scanner.nextInt();

					// Validate algorithm choice
					if (algorithmChoice < 1 || algorithmChoice > 5) {
						System.out.printf("Invalid choice. Please select a number between 1 and 5.\n");
					} else {
						validInput = true;
					}
				} catch (InputMismatchException e) {
					System.out.printf("Invalid input. Please enter a numeric value.\n");
					scanner.nextLine(); // Clear the scanner buffer
				}
			}

			// Reset flag for next input
			validInput = false;
			int frameCount = 0;

			while (!validInput) {
				try {
					// Input for the number of frames
					System.out.printf("Enter the number of frames: ");
					frameCount = scanner.nextInt();
					validInput = true;
				} catch (InputMismatchException e) {
					System.out.printf("Invalid input. Please enter a numeric value.\n");
					scanner.nextLine(); // Clear the scanner buffer
				}
			}

			// Consume the newline left-over
			scanner.nextLine();

			// Execute the selected page replacement algorithm based on the user's choice
			if (algorithmChoice == 1) {
				// Execute FIFO algorithm
				FIFO fifo = new FIFO(referenceString, frameCount);
				fifo.execute();
			} else if (algorithmChoice == 2) {
				// Execute Optimal Replacement algorithm
				OptimalReplacement optimal = new OptimalReplacement(referenceString, frameCount);
				optimal.execute();
			} else if (algorithmChoice == 3) {
				// Execute LRUClock algorithm
				LRUClock lruclock = new LRUClock(referenceString, frameCount);
				lruclock.execute();
			} else if (algorithmChoice == 4) {
				// Execute LRUApproximation
				LRUApproximation lruApproximation = new LRUApproximation(referenceString, frameCount);
				lruApproximation.execute();
			} else if (algorithmChoice == 5) {
				// Execute LFU
				LFU lfu = new LFU(referenceString, frameCount);
				lfu.execute();
			}
		}

		// Close the scanner to free resources
		scanner.close();
	}
}
