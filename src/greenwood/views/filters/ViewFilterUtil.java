/**
 * @author John Steele
 * @file   ViewFilterUtil.java
 * @date   07/17/2011 - 07/18/2011: Super late ;-)
 * 
 * <p>A utility used by the patient navigation tree viewer.</p>
 */
package greenwood.views.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A utility class used by the patient navigation tree viewer.</p>
 * <p>The searchTermOccurrences method finds the occurrences of the
 * search term. Once all the occurrences are found an array is returned
 * containing the pairs of occurrences (index, length).
 * For example: 
 * word = "abcdefgabcdefg"
 * searchTerm = "abc"
 * --> return [0, 3, 7, 3]
 * word = "abcabcefghiabc"
 * searchTerm = "abc"
 * -->return [0, 3, 3, 3, 11, 3]
 */
public class ViewFilterUtil {

	
	/**
	 * 
	 * @param searchTerm The term to search for within word. Can be null or empty. 
	 * @param word The word being searched by searchTerm.
	 * 
	 * @return An array of int pairs. 
	 */
	public static int [] getSearchTermOccurrences (
			final String searchTerm, final String word) {
		
		if (searchTerm == null || searchTerm.length() == 0) 
			return new int [0];
		
		if (word == null) throw new IllegalArgumentException("Word is null");
		
		List<Integer> pairList = new ArrayList<Integer>();
		
		int currentSearchIndex = 0;
		int searchTermLength   = searchTerm.length();
		int foundOccurrenceIndex;
		
		
		while (true) {
			
			foundOccurrenceIndex = word.indexOf(searchTerm, currentSearchIndex);
			
			if (foundOccurrenceIndex != -1) {
				// But there was a previous occurrence.
				pairList.add(Integer.valueOf(foundOccurrenceIndex));
				pairList.add(Integer.valueOf(searchTermLength));
			}	
			
			else 
				break;
			
			currentSearchIndex = foundOccurrenceIndex + searchTermLength;
		}
			
		
		int n = pairList.size();
		int [] result = new int [n];
		
		for (int i = 0; i < n; i++) 
			result[i] = pairList.get(i);
		
		return result;
	}
}
