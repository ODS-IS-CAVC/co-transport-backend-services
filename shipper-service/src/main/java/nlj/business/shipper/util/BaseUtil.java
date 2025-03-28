package nlj.business.shipper.util;

import com.next.logistic.s3.util.S3HelperUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.constant.DataBaseConstant.DATE_TIME_FORMAT;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * ベースユーティリティクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RequiredArgsConstructor
public class BaseUtil {

    /**
     * 整数値を取得する。
     *
     * @param value 値
     * @return 値
     */
    public static Integer getIntegerValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(value.trim());
    }

    /**
     * BigDecimal値を取得する。
     *
     * @param value 値
     * @return 値
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
     * @param files ファイルリスト
     * @return ファイル名リスト
     */
    public static List<String> uploadS3(List<MultipartFile> files, S3HelperUtil s3HelperUtil) {
        List<String> listFileName = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT.DATE_TIME_FORMAT_YYYYMMDDHHMMSS);
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
     * @return 値
     */
    public static String getStringValue(String value) {
        return value != null ? value.trim() : null;
    }

    /**
     * 長整数値を取得する。
     *
     * @param value 値
     * @return 値
     */
    public static Long getLongValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Long.parseLong(value.trim());
    }

    /**
     * ヘッダーから値を取得する。
     *
     * @param fields     フィールドリスト
     * @param headerMap  ヘッダーマップ
     * @param headerName ヘッダー名
     * @return 値
     */
    public static String getValueByHeader(String[] fields, Map<String, Integer> headerMap, String headerName) {
        Integer index = headerMap.get(headerName);
        if (index == null || index >= fields.length) {
            return null;
        }
        return fields[index].trim();
    }

    /**
     * 整数リスト値を取得する。
     *
     * @param value 値
     * @return 値
     */
    public static List<Integer> getIntegerListValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Arrays.asList(value.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    /**
     * 時間文字列を取得する。
     *
     * @param value 値
     * @return 値
     */
    public static String getStringValueTime(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String trimmedValue = value.trim();
        // If length is less than 4, pad with zeros on the left
        while (trimmedValue.length() < 4) {
            trimmedValue = "0" + trimmedValue;
        }
        return trimmedValue;
    }
}
