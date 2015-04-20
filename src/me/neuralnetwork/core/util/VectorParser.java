package me.neuralnetwork.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Provides methods to parse strings as Integer or Double vectors.
 */
public class VectorParser {
	static public ArrayList<Integer> parseInteger(String str) {
		StringTokenizer tok = new StringTokenizer(str);
		ArrayList<Integer> ret = new ArrayList<>();
		while (tok.hasMoreTokens()) {
			Integer d = new Integer(tok.nextToken());
			ret.add(d);
		}
		return ret;
	}

	/**
	 * This method parses input String and returns double array
         * 
	 * @param inputStr
	 *            input String
	 * @return double array
	 */
	static public double[] parseDoubleArray(String inputStr) {
		String[] inputsArrStr = inputStr.split(" ");

		double[] ret = new double[inputsArrStr.length];
		for (int i = 0; i < inputsArrStr.length; i++) {
			ret[i] = Double.parseDouble(inputsArrStr[i]);
		}

		return ret;
	}

	public static double[] toDoubleArray(List<Double> list) {
		double[] ret = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i);
		}
		return ret;
	}

	public static ArrayList<Double> convertToVector(double[] array) {
		ArrayList<Double> vector = new ArrayList<>(array.length);

		for (double val : array) {
			vector.add(val);
		}

		return vector;
	}

}