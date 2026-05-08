package util;
public class Validation {
    public static boolean isOk(String s) {
        try { return Integer.parseInt(s) >= 0; }
        catch (Exception e) { return false; }
    }
}