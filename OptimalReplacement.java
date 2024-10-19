package assignment2;

import java.util.ArrayList;
import java.util.List;

public class OptimalReplacement {
	private List<String> referenceString;
	private int frameCount;
	private int pageFaults;

	// Constructor to initialize OptimalReplacement algorithm with reference string
	// and frame count
	public OptimalReplacement(List<String> referenceString, int frameCount) {
		this.referenceString = referenceString;
		this.frameCount = frameCount;
		this.pageFaults = 0;
	}

	// Execute the Optimal Replacement page replacement algorithm
	public void execute() {
		List<String> frames = new ArrayList<>(frameCount); // Using ArrayList to maintain frame order

		for (int i = 0; i < referenceString.size(); i++) {
			String currentPage = referenceString.get(i);
			if (!frames.contains(currentPage)) {
				pageFaults++;
				if (frames.size() == frameCount) {
					String pageToReplace = findPageToReplace(frames, i);
					int replaceIndex = frames.indexOf(pageToReplace);
					frames.set(replaceIndex, currentPage); // Replace the page in the correct frame
				} else {
					frames.add(currentPage);
				}
				FramePrinter.printFrames(frames); // Print frame state only on page faults
			}
		}

		System.out.println("Total page faults: " + pageFaults);
	}

	// Find the page to replace using the Optimal Replacement algorithm
	private String findPageToReplace(List<String> frames, int currentIndex) {
		String pageToReplace = null;
		int latestFutureUse = -1;

		for (String frame : frames) {
			int nextUseIndex = findNextUseIndex(frame, currentIndex + 1);
			if (nextUseIndex == -1) { // If the page is not used in the future
				return frame; // This page should be replaced
			}
			if (nextUseIndex > latestFutureUse) {
				latestFutureUse = nextUseIndex;
				pageToReplace = frame;
			}
		}

		return pageToReplace;
	}

	// Find the index of the next use of a page in the reference string
	private int findNextUseIndex(String page, int startIndex) {
		for (int i = startIndex; i < referenceString.size(); i++) {
			if (referenceString.get(i).equals(page)) {
				return i;
			}
		}
		return -1; // Indicate that the page is not found in the future
	}
}
