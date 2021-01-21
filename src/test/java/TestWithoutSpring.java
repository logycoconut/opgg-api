import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

/**
 * @author hall
 * @date 2021/1/21
 */
//@SpringBootTest
class TestWithoutSpring {

    @Test
    void urlFormatTest(){
//        String format = MessageFormat.format("{champion} is at the age of {role}", "object", "dsf");
        String s = "/champion/:champion/statistics/:role/rune".replace(":champion", "yasuo")
                .replace(":role", "top");
        System.out.println(s);
    }

}
