package assignment2;

import java.util.Arrays;
import java.util.List;

public class LFU {
    private int[] frames;
    private int[] frequencies;
    private List<String> referenceString;
    private int frameSize;
    private int pageFaults;

    // Constructor to initialize LFU algorithm with reference string and frame size
    public LFU(List<String> referenceString, int frameSize) {
        this.referenceString = referenceString;
        this.frameSize = frameSize;
        this.frames = new int[frameSize];
        this.frequencies = new int[frameSize];
        Arrays.fill(frames, -1); // Initialize frames with -1 indicating empty
        Arrays.fill(frequencies, 0); // Initialize frequencies
        this.pageFaults = 0;
    }

    // Execute the LFU page replacement algorithm
    public void execute() {
        int[] prevFrames = new int[frameSize];
        int[] prevFrequencies = new int[frameSize];
        for (String ref : this.referenceString) {
            System.arraycopy(frames, 0, prevFrames, 0, frameSize); // Save current state
            System.arraycopy(frequencies, 0, prevFrequencies, 0, frameSize);

            int page = Integer.parseInt(ref.trim());
            if (!isPageInFrames(page)) {
                replacePage(page);
                pageFaults++;
            }

            if (!Arrays.equals(frames, prevFrames) || !Arrays.equals(frequencies, prevFrequencies)) {
                FramePrinter.printLFUFrames(frames, frequencies, frameSize); // Print only if changed
            }
        }
        System.out.println("Total page faults: " + pageFaults);
    }

    // Check if a page is in the frames
    private boolean isPageInFrames(int page) {
        for (int i = 0; i < frameSize; i++) {
            if (frames[i] == -1) {
                return false; // Frame is empty
            }
            if (frames[i] == page) {
                frequencies[i]++; // Increment frequency
                return true;
            }
        }
        return false;
    }

    // Replace a page in the frames using the LFU algorithm
    private void replacePage(int page) {
        int emptyFrameIndex = findEmptyFrameIndex();
        if (emptyFrameIndex != -1) {
            frames[emptyFrameIndex] = page;
            frequencies[emptyFrameIndex] = 1; // Set frequency for new page
        } else {
            int leastFreqIndex = findLeastFrequentFrameIndex();
            frames[leastFreqIndex] = page;
            frequencies[leastFreqIndex] = 1; // Reset frequency for new page
        }
    }

    // Find the index of the least frequently used frame
    private int findLeastFrequentFrameIndex() {
        int minFreq = Integer.MAX_VALUE;
        int leastFreqIndex = -1;
        for (int i = 0; i < frameSize; i++) {
            if (frequencies[i] < minFreq) {
                minFreq = frequencies[i];
                leastFreqIndex = i;
            }
        }
        return leastFreqIndex;
    }

    // Find the index of an empty frame
    private int findEmptyFrameIndex() {
        for (int i = 0; i < frameSize; i++) {
            if (frames[i] == -1) {
                return i;
            }
        }
        return -1;
    }
}
