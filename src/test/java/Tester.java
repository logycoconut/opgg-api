import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.net.URL;

/**
 * @author hall
 * @description
 * @date 2021-01-12 22:46
 */
class Tester {
    @Test
    void testUrl() throws Exception {
        // 解析URL地址  第一个参数为访问的url 第二个参数是访问时候的超时时间
        Document doc = Jsoup.parse(new URL("https://www.op.gg/champion/statistics/"), 60000);
        Elements elementsByClass = doc.getElementsByClass("champion-index__champion-item__name");
        for (Element byClass : elementsByClass) {
            System.out.println(byClass.text());
        }
    }

}
