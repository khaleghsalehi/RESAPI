import ir.innovera.Dictionary;
import ir.innovera.Validator;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestBlackList {
    @Test
    public void vulnerableRequest() {
        String normalVulnerableQuery = "userid=2020";

        List<String> attackBypassPayload = Arrays.asList("\u0000", "\u0000\u0000\u0000\u0000\u0000\u0000\u0000", "\0",
                "\0\0\0\0", "\u0000\0\0\u0000%00", "%00", "%00%00%00%00%00%00%00%00");

        List<String> request = new ArrayList<>();

        attackBypassPayload.forEach(bypassCharacters -> {
            //check or
            String query = normalVulnerableQuery + "'" + bypassCharacters + " or " + "'1'='1";
            request.add(query);
            query = normalVulnerableQuery + "'" + " or " + bypassCharacters + "'1'='1";
            request.add(query);
            query = normalVulnerableQuery + "'" + " o" + bypassCharacters + "r " + "'1'='1";
            request.add(query);
            //check and
            query = normalVulnerableQuery + "'" + " an" + bypassCharacters + "d " + "'1'='1";
            request.add(query);
            query = normalVulnerableQuery + "'" + " a" + bypassCharacters + "nd " + "'1'='1";
            request.add(query);
            query = normalVulnerableQuery + "'" + " " + bypassCharacters + "and " + "'1'='1";
            request.add(query);
            Dictionary.keywords.forEach(sql -> {
                String byPassedwithOrAND = "' <BYPASSED By OR/AND>'"; // we image the attacker successfully bypassed the AND/OR operator
                String sqlKeywordInjection = normalVulnerableQuery + byPassedwithOrAND + sql;
                request.add(sqlKeywordInjection);
                sqlKeywordInjection = normalVulnerableQuery + byPassedwithOrAND + bypassCharacters + sql;
                request.add(sqlKeywordInjection);
                sqlKeywordInjection = normalVulnerableQuery + byPassedwithOrAND + sql + bypassCharacters;
                request.add(sqlKeywordInjection);

                final int mid = sql.toString().length() / 2; //get the middle of the String
                String[] parts = {sql.toString().substring(0, mid), sql.toString().substring(mid)};
                sqlKeywordInjection = normalVulnerableQuery + byPassedwithOrAND + parts[0] + bypassCharacters + parts[1] + bypassCharacters;
                request.add(sqlKeywordInjection);

            });
        });
        request.add(normalVulnerableQuery + " un%00%00%00%00%00%00%00%00%00%00%00%00%00%00%00%00i%00%00%00on%20");
        request.forEach(k -> {
            try {
                Assert.assertFalse(Validator.validateParams(k));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void normalRequest() throws UnsupportedEncodingException {
        Assert.assertEquals(Validator.validateParams("union"), true);
        Assert.assertEquals(Validator.validateParams("  uN\00ion"), true);
        Assert.assertEquals(Validator.validateParams("  uN\00ioN "), false);

    }
}
