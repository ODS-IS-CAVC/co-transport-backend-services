package nlj.business.carrier.util;

import com.next.logistic.s3.util.S3HelperUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.DataBaseConstant.DATE_TIME_FORMAT;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class BaseUtil {

    /**
     * 整数値を取得する。
     *
     * @param value 値
     * @return 整数値またはnull
     */
    public static Integer getIntegerValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(value.trim());
    }

    /**
     * ブール値を取得する。
     *
     * @param value 値
     * @return ブール値またはnull
     */
    public static Boolean getBooleanValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
            return null;
        }
        return value.equalsIgnoreCase("true");
    }

    /**
     * BigDecimal値を取得する。
     *
     * @param value 値
     * @return BigDecimal値またはnull
     */
    public static BigDecimal getBigDecimalValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return new BigDecimal(value.trim());
    }

    /**
     * S3にファイルをアップロードする。
     *
     * @param files        ファイルリスト
     * @param s3HelperUtil S3ヘルパー
     * @return ファイル名リスト
     */
    public static List<String> uploadS3(List<MultipartFile> files, S3HelperUtil s3HelperUtil) {
        List<String> listFileName = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT.DATE_TIME_FORMAT_HHMM);
        String today = formatter.format(LocalDateTime.now());
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                String originalFileName = file.getOriginalFilename();
                String fileName = today + originalFileName;
                s3HelperUtil.upload(file, fileName);
                listFileName.add(fileName);
            }
        }
        return listFileName;
    }

    /**
     * 文字列値を取得する。
     *
     * @param value 値
     * @return トリムされた文字列またはnull
     */
    public static String getStringValue(String value) {
        return value != null ? value.trim() : null;
    }

    /**
     * 長整数値を取得する。
     *
     * @param value 値
     * @return 長整数値またはnull
     */
    public static Long getLongValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Long.parseLong(value.trim());
    }

    /**
     * ヘッダー名に基づいて値を取得する。
     *
     * @param fields     フィールド配列
     * @param headerMap  ヘッダーマップ
     * @param headerName ヘッダー名
     * @return ヘッダー名に対応する値またはnull
     */
    public static String getValueByHeader(String[] fields, Map<String, Integer> headerMap, String headerName) {
        Integer index = headerMap.get(headerName);
        if (index == null || index >= fields.length) {
            return null;
        }
        return fields[index].trim();
    }

}
