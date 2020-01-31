package com.elaniin.nitro.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lu1tr0n
 */
public class Utils {

	/**
	 * @param <T>
	 * @param iterator
	 * @return
	 */
	public static <T> List<T> getListFromIterator(Iterator<T> iterator) { 
		  
        // Create an empty list 
        List<T> list = new ArrayList<>(); 
  
        // Add each element of iterator to the List 
        iterator.forEachRemaining(list::add); 
  
        // Return the List 
        return list; 
    }
}
