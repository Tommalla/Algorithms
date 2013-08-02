package algorithms;

/**
 *
 * @author tommalla
 */
public class Common {
    /**
     * Swaps two elements of an array
     * @param data Input array
     * @param i Index of the first element to be swapped
     * @param j Index of the second element to be swapped
     */
    public static void swap(float[] data, int i, int j) {
        float tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
    
    /**
     * Returns the length of a segment
     * @param left The left end of the segment (inclusive)
     * @param right The right end of the segment (inclusive)
     */
    public static int segmentLength(int left, int right) {
	return right - left + 1;
    }
}
