package algorithms;

import static java.lang.Math.min;
import static algorithms.Common.*;
import java.util.Arrays;

/**
 *
 * @author tommalla
 */
public class QuickSelect {
    public static float median(float[] data, int size) {
	return quickSelect(data, 0, size - 1, size / 2);
    }
    
    public static float quickSelect(float[] data, int left, int right, int k) {
	//System.out.println("Root: k = " + k);
	return data[quickSelect(data, left, right, k, 1)];
    }
    
    public static int quickSelect(float[] data, int left, int right, int k, int pivot) {
	while (left < right) {
	    
	    //System.out.printf("quickSelect(%d, %d, %d, %d)\n", left, right, k, pivot);	
	    //dbgPrint(data);
	    
	    if (right == left + 1) {
		if (data[left] > data[right])
		    swap(data, left, right);
		return left + k - 1;
	    }
	    
	    int pivotIndex = (pivot < 0) ? medianOfThree(data, left, right) : medianOfMedians(data, left, right); //left + rand.nextInt(right - left + 1);    // select a pivotIndex between left and right
	    //pivot = -1;
	    int idx = partition(data, left, right, pivotIndex);
	
	    int pivotDist = idx - left + 1;
	    //System.out.println("idx: " + idx + ", dist = " + pivotDist + ", k = " + k + "\n");
	    
	    // The pivot is in its final sorted position,
	    // so pivotDist reflects its 1-based position if list were sorted
	    if (pivotDist == k)
		    return idx;
	    else if (k < pivotDist)
		right = idx - 1;
	    else {
		left = idx + 1;
		k -= pivotDist;
	    }
	}
	return left;
    }
    
    //returns an array of indexes:
    //end of smaller-than-privot, end of equal-as-pivot segments 
    public static int partition(float[] data, int left, int right, int pivot) {
	int res;
	float pivotValue = data[pivot];
	//System.out.printf("Partition: %d, %d, %f\n", left, right, pivotValue);
	//dbgPrint(data); 
	
	swap(data, right, pivot);
	
	res = left;
	for (int i = left; i <= right - 1; i++)
	    if (data[i] <= pivotValue) {
		swap(data, i, res);
		++res;
	    }
	
	swap(data, right, res);
	//dbgPrint(data);
	return res;
    }  

    public static int medianOfThree(float[] data, int left, int right) {
	//System.out.printf("medianOfThree(%d, %d)\n", left, right);
	
	if (segmentLength(left, right) < 3)
	    return ((left + 1 <= right) ?
		    ((data[left] < data[right]) ? left : right) : left);
	
        int center = (left + right) / 2;

        if (data[left] > data[right]) {
            swap(data, left, center);
        }

        if (data[left] > data[right]) {
            swap(data, left, right);
        }

        if (data[center] > data[right]) {
            swap(data, center, right);
        }

        swap(data, center, right - 1);

        return right - 1;
    }
    
    public static int medianOfMedians(float[] data, int left, int right) {
	int segLen = segmentLength(left, right);
	
	if (segLen < 5) {
	    Arrays.sort(data, left, right);
	    return left + segLen / 2;
	}
	
	int numMedians = segLen / 5;
	
	for (int i = 0; i < numMedians; ++i) {
	    //System.out.println("median number " + i + " of " + numMedians);
	    //get the median of the five-element subgroup
	    int subLeft = left + i * 5;
	    int subRight = min(subLeft + 4, right);
	    
	    int medianIdx = quickSelect(data, subLeft, subRight, segmentLength(subLeft, subRight) / 2, -1);
	   // System.out.println("swapping: " + (left + i) + " " + medianIdx);
	    //move the median to a contiguous block at the beginning of the list
	    swap(data, left + i, medianIdx);
	    
	    //System.out.println("Step:");
	    //dbgPrint(data);
	}
	
	//move the remaining end to the beginning
	/*int end = left + numMedians * 5;
	if (end <= right) {
	    Arrays.sort(data, end, right);
	    swap(data, end + segmentLength(end, right) / 2, left + numMedians);
	    numMedians++;
	}*/
	
	//System.out.println(">>After one median:");
	//dbgPrint(data);
	//select the median from the contiguous block
	return quickSelect(data, left, left + numMedians - 1, (numMedians + 1) / 2, -1);
    }
    
        public static void dbgPrint(float[] data) {
	for (int i = 0; i < data.length; i++) {
	    System.out.printf("%.2f ", data[i]);
	}
	System.out.println("");
    }
}
