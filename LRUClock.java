package assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LRUClock {
	private int[] frames;
	private int[] lastUsed;
	private List<String> referenceString; // Declare referenceString as a field
	private int frameSize;
	private int pageFaults;
	private int time;

	// Constructor to initialize LRUClock algorithm with reference string and frame
	// size
	public LRUClock(List<String> referenceString, int frameSize) {
		this.frameSize = frameSize;
		this.frames = new int[frameSize];
		this.lastUsed = new int[frameSize];
		this.referenceString = referenceString; // Initialize referenceString
		Arrays.fill(frames, -1); // Initialize frames with -1 indicating empty
		Arrays.fill(lastUsed, 0); // Initialize last used times
		this.pageFaults = 0;
		this.time = 0;
	}

	// Execute the LRUClock page replacement algorithm
	public void execute() {
		for (String ref : this.referenceString) {
			int page = Integer.parseInt(ref.trim());
			if (!isPageInFrames(page)) {
				replacePage(page);
				pageFaults++;
			}
			time++;
			allFramesWithTime.add(getFrameContentsWithTime());
		}
		FramePrinter.printAllFramesVertical(allFramesWithTime);

		System.out.println("Total page faults: " + pageFaults);
	}

	private List<List<String[]>> allFramesWithTime = new ArrayList<>();

	// Check if a page is in the frames
	private boolean isPageInFrames(int page) {
		for (int i = 0; i < frameSize; i++) {
			if (frames[i] == page) {
				lastUsed[i] = time; // Update last used time
				return true;
			}
		}
		return false;
	}

	// Replace a page in the frames using the LRUClock algorithm
	private void replacePage(int page) {
		int lruIndex = findLRUFrameIndex();
		frames[lruIndex] = page;
		lastUsed[lruIndex] = time;
	}

	// Find the index of the least recently used frame
	private int findLRUFrameIndex() {
		int minTime = Integer.MAX_VALUE;
		int lruIndex = -1;
		for (int i = 0; i < frameSize; i++) {
			if (frames[i] == -1 || lastUsed[i] < minTime) {
				minTime = lastUsed[i];
				lruIndex = i;
			}
		}
		return lruIndex;
	}

	// Get the contents of frames with their last used times
	private List<String[]> getFrameContentsWithTime() {
		List<String[]> contents = new ArrayList<>();
		for (int i = 0; i < frameSize; i++) {
			String[] frameInfo = new String[2];
			frameInfo[0] = (frames[i] != -1) ? String.valueOf(frames[i]) : " ";
			frameInfo[1] = (frames[i] != -1) ? String.valueOf(lastUsed[i]) : " ";
			contents.add(frameInfo);
		}
		return contents;
	}
}
