package nlj.business.transaction.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <PRE>
 * 都道府県定数。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class Prefectures {

    public static Map<Long, String> data = new HashMap<>();

    static {
        // 新東名高速道路
        data.put(10101L, "岡崎");
        data.put(10102L, "長篠設楽原");
        data.put(10103L, "浜松");
        data.put(10104L, "遠州森町");
        data.put(10105L, "掛川");
        data.put(10106L, "藤枝");
        data.put(10107L, "静岡");
        data.put(10108L, "清水");
        data.put(10109L, "駿河湾沼津");

        // 東北地方
        data.put(10201L, "青森県");
        data.put(10202L, "岩手県");
        data.put(10203L, "宮城県");
        data.put(10204L, "秋田県");
        data.put(10205L, "山形県");
        data.put(10206L, "福島県");

        // 関東地方
        data.put(10301L, "東京都");
        data.put(10302L, "神奈川県");
        data.put(10303L, "埼玉県");
        data.put(10304L, "千葉県");
        data.put(10305L, "茨城県");
        data.put(10306L, "栃木県");
        data.put(10307L, "群馬県");

        // 中部地方
        data.put(10401L, "新潟県");
        data.put(10402L, "富山県");
        data.put(10403L, "石川県");
        data.put(10404L, "福井県");
        data.put(10405L, "山梨県");
        data.put(10406L, "長野県");
        data.put(10407L, "岐阜県");
        data.put(10408L, "静岡県");
        data.put(10409L, "愛知県");

        // 近畿地方
        data.put(10501L, "三重県");
        data.put(10502L, "滋賀県");
        data.put(10503L, "京都府");
        data.put(10504L, "大阪府");
        data.put(10505L, "兵庫県");
        data.put(10506L, "奈良県");
        data.put(10507L, "和歌山県");

        // 中国地方
        data.put(10601L, "鳥取県");
        data.put(10602L, "島根県");
        data.put(10603L, "岡山県");
        data.put(10604L, "広島県");
        data.put(10605L, "山口県");

        // 四国地方
        data.put(10701L, "香川県");
        data.put(10702L, "徳島県");
        data.put(10703L, "愛媛県");
        data.put(10704L, "高知県");

        // 九州・沖縄地方
        data.put(10801L, "福岡県");
        data.put(10802L, "佐賀県");
        data.put(10803L, "長崎県");
        data.put(10804L, "熊本県");
        data.put(10805L, "大分県");
        data.put(10806L, "宮崎県");
        data.put(10807L, "鹿児島県");
        data.put(10808L, "沖縄県");

        data.put(4149999900010015L, "駿河湾沼津");
        data.put(4149999900010022L, "浜松");
        data.put(9999900010015L, "駿河湾沼津");
        data.put(9999900010022L, "浜松");
    }
}
