package assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FramePrinter {

	// Print frames for Optimal algorithm
	public static void printFrames(Set<String> frames) {
		for (String frame : frames) {
			System.out.print("|" + frame);
		}
		System.out.println("|");
	}

	// Print frames for FIFO algorithm
	public static void printFrames(List<String> frames) {
		for (String frame : frames) {
			System.out.print("|" + frame);
		}
		System.out.println("|");
	}

	// Print frames vertically for LRUClock algorithm
	public static void printAllFramesVertical(List<List<String[]>> allFrames) {
		for (List<String[]> frameState : allFrames) {
			System.out.println("Page # | Clock");
			for (int i = frameState.size() - 1; i >= 0; i--) {
				String[] frame = frameState.get(i);
				String page = frame[0].equals(" ") ? "  " : frame[0];
				String clock = frame[1].equals(" ") ? "  " : frame[1];
				System.out.println(String.format("%-4s   | %-4s", page, clock));
			}
			System.out.println();
		}
	}

	// Print frames with reference bytes for LRUApproximation algorithm
	public static void printFramesWithReferenceBytes(Set<String> frames, Map<String, ArrayList<Integer>> referenceBytes,
			boolean shiftOccurred) {

		if (!frames.isEmpty()) {
			// Print only after shifting
			if (shiftOccurred) {
				System.out.println("\nPages after Shifting:");
			}

			System.out.println("Page # | Reference Byte");
			for (String page : frames) {
				ArrayList<Integer> refByteList = referenceBytes.get(page);
				System.out.printf("%-3s    | %s\n", page, refByteList.toString());
			}
			System.out.println();
		}
	}

	// Print LFU frames and their counters for LFU algorithm
	public static void printLFUFrames(int[] frames, int[] frequencies, int frameSize) {
		System.out.println("Page # | Counter");
		for (int i = 0; i < frameSize; i++) {
			String page = frames[i] != -1 ? String.valueOf(frames[i]) : " ";
			String frequency = frames[i] != -1 ? String.valueOf(frequencies[i]) : " ";
			System.out.printf("%-6s | %-9s\n", page, frequency);
		}
		System.out.println();
	}
}
