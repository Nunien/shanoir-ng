package org.shanoir.ng.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class
 *
 * @author jlouis
 */
public class Utils {

    /**
     * Convert Iterable to List
     *
     * @param iterable
     * @return a list
     */
    public static <E> List<E> toList(Iterable<E> iterable) {
        if (iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<E>();
        if (iterable != null) {
            for (E e : iterable) {
                list.add(e);
            }
        }
        return list;
    }
    
    public static <T> List<T> copyList(List<T> list) {
    	List<T> copy = new ArrayList<T>();
    	for (T item : list) copy.add(item);
    	return copy;
    }

    public static boolean equalsIgnoreNull(Object o1, Object o2) {
        if (o1 == null) return o2 == null;
        return o1.equals(o2)
        		|| (o2 != null && o2.equals(o1));
        		// o1.equals(o2) is not equivalent to o2.equals(o1) ! For instance with java.sql.Timestamp and java.util.Date
    }
}
