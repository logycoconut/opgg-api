import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

/**
 * @author hall
 * @description
 * @date 2021-01-12 22:46
 */
class Tester {

    @Test
    void testUrl() throws Exception {
        // 解析URL地址  第一个参数为访问的url 第二个参数是访问时候的超时时间
        Document document = Jsoup.connect("https://www.op.gg/champion/statistics/")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                .timeout(60000)
                .get();

        Elements select = document.select(".champion-index__champion-list .champion-index__champion-item");
        System.out.println(select.first().classNames());
    }

}
