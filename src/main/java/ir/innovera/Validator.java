package ir.innovera;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    /**
     * method returns boolean, it's make it more flexible. e.g. if find the attack then forward to request honeypot
     *
     * @param input (string to validation)
     * @return boolean
     * @throws UnsupportedEncodingException
     */
    public static boolean validateParams(String input) throws UnsupportedEncodingException {
        if (input != null) {

            // Step 1: first check string length, don't processing next steps(regex) that make it so faster
            if (input.length() > Dictionary.maxLength) {
                //todo log  replacement with console println
                if (Dictionary.VERBOSE_STATUS)
                    System.out.println("reject params, maxLength");
                return false;
            }

            // Step 2: decode, normalize and transform lower case (bypass techniques)
            input = URLDecoder.decode(input, "UTF-8");
            for (String s : Dictionary.nullCharacters) {
                input = input.replaceAll(s, "");
            }
            input = input.toLowerCase();

            for (Pattern pattern : Dictionary.keywords) {
                Matcher matcher = pattern.matcher(input);
                if (matcher.find()) {
                    if (Dictionary.VERBOSE_STATUS)
                        System.out.printf("final filtered request %80s\t\t%s%n", input, "attack");
                    return false;
                }
            }
        }
        if (Dictionary.VERBOSE_STATUS)
            System.out.printf("final filtered request %80s\t\t%s%n", input, "safe");
        return true;
    }
}
