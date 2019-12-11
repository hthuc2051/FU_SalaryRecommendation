/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author HP
 */
public class AppHelper {

    public static int hasingString(String s) {
        int mod = 1000000007;
        int base = 30757; // random prime number
        int hasValue = 0;
        for (int i = 0; i < s.length(); i++) {
            hasValue = (int) (((long) hasValue * base + (long) s.charAt(i)) % mod);
        }
        return hasValue;
    }

    public static String generateLevel(String name, double year) {
        // Nên check trong khoảng thì hay hơn là check số
        String level = "";
        String s = name.toLowerCase();
        if (s.contains("software architecture") || year > 10) {
            return "SA";
        } else if (s.contains("technical lead") || (year > 6 && year <= 10)) {
            return "TL";
        } else if (s.contains("senior") || (year > 3 && year <= 6)) {
            return "Senior";
        } else if (s.contains("developer") || (year > 1 && year <= 3)) {
            return "Dev";
        } else if (s.contains("junior") || (year > 0.5 && year <= 1)) {
            return "Junior";
        } else if (s.contains("fresher") || (year > 0 && year <= 0.5)) {
            return "Fresher";
        }
        return level;
    }

    public static String getFullLevelStr(String shortStr) {
        String level = "";
        switch (shortStr) {
            case "SA":
                return "Software architecture";
            case "TL":
                return "Technical lead";
            case "Senior":
                return "Senior developer";
            case "Dev":
                return "Developer";
            case "Junior":
                return "Junior developer";
            case "Fresher":
                return "Fresher developer";
        }
       
        return level;
    }

    public static double convertRangeToNum(String s) {
        double mutipleValue = 1;
        if (s.contains("USD")) {
            mutipleValue = 23000;
        }
        String[] arr = s.split("-");
        try {
            double from = Double.parseDouble(arr[0].replaceAll("\\D+", ""));
            if (arr.length <= 1) {
                return from * mutipleValue;
            }
            double to = Double.parseDouble(arr[1].replaceAll("\\D+", ""));
            return to * mutipleValue;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Double getBeautySalary(Double param) {
        try {
            Double salary = param * 29000 / 12;
            String s = String.format("%.0f", salary);
            int length = s.length();
            if (length < 4) {
                return 0.0;
            }
            double a = Math.pow(10, (length - 2));
            return Integer.parseInt(s.substring(0, 2)) * a;

        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static Double getBeautyNumber(Double num) {
        try {
            String s = String.format("%.0f", num);
            int length = s.length();
            if (length < 4) {
                return 0.0;
            }
            double a = Math.pow(10, (length - 2));
            return Integer.parseInt(s.substring(0, 2)) * a;

        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static Double calculateDistance(Double num1, Double num2) {
        if (num1 > num2) {
            return num1 - num2;
        } else {
            return num2 - num1;
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            Entry<K, V> entry = list.get(i);
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
