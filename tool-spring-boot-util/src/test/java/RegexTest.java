import com.github.wang.utils.regex.RegexUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RegexTest {

    @Test
    public void RegexTest1() {
        String targetStr = "/file/bcdf/abc";
        String regexStr = "/file/.*";
        System.out.println(RegexUtil.checkContain(targetStr, regexStr));
        System.out.println(RegexUtil.replaceAll(targetStr, regexStr, "hello,world!"));
    }

    @Test
    public void RegexTest2() {
        String targetStr = "hello,world";
        String regexStr = "l{1}";
        System.out.println(RegexUtil.replaceAll(targetStr, regexStr, "a"));
    }

    @Test
    public void RegexTest3() {
        String targetStr = "hello,world";
        String regexStr = "l+";
        Map<String, Object> map = new HashMap<>();
        map.put("ll", "b");
        map.put("l", "c");
        System.out.println(RegexUtil.replaceAll(targetStr, regexStr, map));
    }

}
