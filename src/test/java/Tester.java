import com.logycoconut.opgg.api.entity.Champion;
import com.logycoconut.opgg.api.parser.ChampionEndpoint;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hall
 * @description
 * @date 2021-01-12 22:46
 */
//@SpringBootTest
class Tester {

    @Test
    void testUrl() throws Exception {
        ChampionEndpoint championEndpoint = new ChampionEndpoint();
        Document document = championEndpoint.request();
        Elements championItems = document.select(".champion-trend-tier .champion-index-table tbody");

        Map<String, List<Champion>> champions = championEndpoint.getChampionTier(championItems);
        System.out.println(champions);

    }




}
