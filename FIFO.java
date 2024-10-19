package assignment2;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class FIFO {
	private Queue<String> pagesQueue; // Queue to manage page order
	private Set<String> currentPages; // Set to track current pages in memory
	private List<String> referencePages; // List of reference pages
	private int maxFrames; // Maximum number of frames
	private int pageFaultCount; // Count of page faults

	// Constructor to initialize FIFO algorithm with reference pages and frame count
	public FIFO(List<String> referencePages, int maxFrames) {
		this.referencePages = referencePages;
		this.maxFrames = maxFrames;
		this.pagesQueue = new LinkedList<>();
		this.currentPages = new LinkedHashSet<>(); // Maintains insertion order
		this.pageFaultCount = 0;
	}

	// Execute the FIFO page replacement algorithm
	public void execute() {
		Set<String> prevFrames = new LinkedHashSet<>();
		for (String page : referencePages) {
			prevFrames.clear();
			prevFrames.addAll(currentPages); // Save the current state
			processPage(page);
			if (!currentPages.equals(prevFrames)) { // Check for changes
				FramePrinter.printFrames(currentPages); // Print only if changed
			}
		}
		System.out.println("Total page faults: " + pageFaultCount);
	}

	// Process a page reference
	private void processPage(String page) {
		if (!currentPages.contains(page)) {
			pageFaultCount++;
			if (currentPages.size() >= maxFrames) {
				removeOldestPage();
			}
			addNewPage(page);
		}
	}

	// Remove the oldest page from memory
	private void removeOldestPage() {
		String oldestPage = pagesQueue.poll();
		currentPages.remove(oldestPage);
	}

	// Add a new page to memory
	private void addNewPage(String page) {
		pagesQueue.add(page);
		currentPages.add(page);
	}
}
