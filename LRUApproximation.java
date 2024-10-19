package assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LRUApproximation {
	// Stores the reference bytes for each page
	private Map<String, ArrayList<Integer>> referenceBytes;
	// Set of current pages in memory
	private Set<String> frames;
	// Number of frames available
	private int frameCount;
	// Counter for page faults
	private int pageFaults;
	// List of page references
	private List<String> referenceString;
	// Tracks pages accessed since the last reference byte shift
	private Set<String> accessedSinceLastShift;

	// Constructor
	public LRUApproximation(List<String> referenceString, int frameCount) {
		this.referenceString = referenceString;
		this.frameCount = frameCount;
		this.referenceBytes = new HashMap<>();
		this.frames = new LinkedHashSet<>();
		this.pageFaults = 0;
		this.accessedSinceLastShift = new HashSet<>();
	}

	// Executes the LRU approximation algorithm
	public void execute() {
		int referenceCounter = 0;
		accessedSinceLastShift.clear();

		for (String page : referenceString) {
			// Check for page fault
			boolean isPageFault = !frames.contains(page);

			// Handle page fault or mark page as accessed
			if (isPageFault) {
				handlePageFault(page);
			} else {
				accessedSinceLastShift.add(page);
			}

			referenceCounter++;

			// Shift reference bytes and print frames periodically
			if (referenceCounter % 3 == 0 || referenceCounter == referenceString.size()) {
				shiftReferenceBytes();
				FramePrinter.printFramesWithReferenceBytes(frames, referenceBytes, true);
				accessedSinceLastShift.clear();
			} else {
				FramePrinter.printFramesWithReferenceBytes(frames, referenceBytes, false);
			}
		}

		// Print total page faults at the end
		System.out.println("Total Page Faults: " + pageFaults);
	}

	// Handles a page fault
	private void handlePageFault(String page) {
	    pageFaults++;
	    // Check if the frame count is exceeded
	    if (frames.size() >= frameCount) {
	        String pageToReplace = findPageToReplace();
	        if (pageToReplace != null) {
	            // Remove the least recently used page
	            frames.remove(pageToReplace);
	            referenceBytes.remove(pageToReplace);
	        } else {
	            // If no page to replace is found, skip adding the new page
	            return;
	        }
	    }
	    // Add the new page and initialize its reference bytes
	    frames.add(page);
	    referenceBytes.put(page, new ArrayList<>(Collections.nCopies(8, 1)));
	}


	// Shifts the reference bytes for each page
	private void shiftReferenceBytes() {
		for (String page : frames) {
			ArrayList<Integer> refByte = referenceBytes.get(page);
			// Shift the reference byte, adding 1 if page was accessed
			refByte.remove(refByte.size() - 1);
			refByte.add(0, accessedSinceLastShift.contains(page) ? 1 : 0);
			referenceBytes.put(page, refByte);
		}
	}

	// Finds the page to replace based on the reference bytes
	private String findPageToReplace() {
	    String pageToReplace = null;
	    ArrayList<Integer> lowestRefByte = new ArrayList<>(Collections.nCopies(8, 1)); // Initialize with the highest possible value

	    // First, find the page with the lowest reference byte value
	    for (Map.Entry<String, ArrayList<Integer>> entry : referenceBytes.entrySet()) {
	        if (compareRefBytes(entry.getValue(), lowestRefByte) < 0) {
	            lowestRefByte = entry.getValue();
	            pageToReplace = entry.getKey();
	        }
	    }

	    // If there is a tie, replace the oldest page
	    if (pageToReplace == null) {
	        for (String page : frames) {
	            pageToReplace = page; // The first page in the LinkedHashSet is the oldest
	            break;
	        }
	    }

	    return pageToReplace;
	}

	// Compares two sets of reference bytes
	private int compareRefBytes(ArrayList<Integer> refByte1, ArrayList<Integer> refByte2) {
		for (int i = 0; i < refByte1.size(); i++) {
			if (refByte1.get(i) != refByte2.get(i)) {
				return refByte1.get(i) - refByte2.get(i);
			}
		}
		return 0;
	}

}
