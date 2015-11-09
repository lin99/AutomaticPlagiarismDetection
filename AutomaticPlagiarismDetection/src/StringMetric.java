/*
 * This class encapsulates different algorithms to measure
 * how different are two strings, the method distance returns
 * a number to indicate how far are 
 * Strings a and b. 
 */
public interface StringMetric {
	double distance(String a, String b);
}
