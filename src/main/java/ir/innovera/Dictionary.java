package ir.innovera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Dictionary {
    public static final ArrayList<Pattern> keywords = new ArrayList<>();
    //todo load maxLength and nullCharacters from config file
    public static final int maxLength = 2048;
    public static final List<String> nullCharacters = Arrays.asList("\u0000", "\0", "\\0000");
    public static final boolean VERBOSE_STATUS = true;

    //todo load from config
    static {
        keywords.add(Pattern.compile(" from "));
        keywords.add(Pattern.compile(" join "));
        keywords.add(Pattern.compile(" union "));
        keywords.add(Pattern.compile("select "));
        keywords.add(Pattern.compile("exists"));
        keywords.add(Pattern.compile("where"));
        keywords.add(Pattern.compile("create table"));
        keywords.add(Pattern.compile("delete from"));
        keywords.add(Pattern.compile("alter "));
        keywords.add(Pattern.compile("drop "));
        keywords.add(Pattern.compile("update "));
        keywords.add(Pattern.compile("dbms_output"));
        keywords.add(Pattern.compile("truncate "));
        keywords.add(Pattern.compile("minus "));
        keywords.add(Pattern.compile("except "));
        keywords.add(Pattern.compile("--"));
        keywords.add(Pattern.compile(" or "));
        keywords.add(Pattern.compile(" and "));
    }
}
