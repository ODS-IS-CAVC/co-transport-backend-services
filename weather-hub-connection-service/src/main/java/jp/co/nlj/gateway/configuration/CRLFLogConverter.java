package jp.co.nlj.gateway.configuration;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;

/**
 * <PRE>
 * CRLFLogConverterクラスは、ログの改行をアンダースコアに変換するためのクラスです。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public class CRLFLogConverter extends CompositeConverter<ILoggingEvent> {

    public static final Marker CRLF_SAFE_MARKER = MarkerFactory.getMarker("CRLF_SAFE");

    private static final String[] SAFE_LOGGERS = {"org.hibernate"};
    private static final Map<String, AnsiElement> ELEMENTS;

    static {
        Map<String, AnsiElement> ansiElements = new HashMap<>();
        ansiElements.put("faint", AnsiStyle.FAINT);
        ansiElements.put("red", AnsiColor.RED);
        ansiElements.put("green", AnsiColor.GREEN);
        ansiElements.put("yellow", AnsiColor.YELLOW);
        ansiElements.put("blue", AnsiColor.BLUE);
        ansiElements.put("magenta", AnsiColor.MAGENTA);
        ansiElements.put("cyan", AnsiColor.CYAN);
        ELEMENTS = Collections.unmodifiableMap(ansiElements);
    }

    /**
     * ログイベントを変換し、改行をアンダースコアに置き換えます。
     *
     * @param event ログイベント
     * @param in    入力文字列
     * @return 変換後の文字列
     */
    @Override
    protected String transform(ILoggingEvent event, String in) {
        AnsiElement element = ELEMENTS.get(getFirstOption());
        if ((event.getMarker() != null && event.getMarker().contains(CRLF_SAFE_MARKER)) || isLoggerSafe(event)) {
            return in;
        }
        String replacement = element == null ? "_" : toAnsiString("_", element);
        return in.replaceAll("[\n\r\t]", replacement);
    }

    /**
     * 指定されたログイベントが安全なロガーかどうかを判断します。
     *
     * @param event ログイベント
     * @return 安全なロガーであればtrue、そうでなければfalse
     */
    protected boolean isLoggerSafe(ILoggingEvent event) {
        for (String safeLogger : SAFE_LOGGERS) {
            if (event.getLoggerName().startsWith(safeLogger)) {
                return true;
            }
        }
        return false;
    }

    /**
     * ANSIスタイルを適用した文字列を生成します。
     *
     * @param in      入力文字列
     * @param element ANSIスタイル要素
     * @return ANSIスタイルが適用された文字列
     */
    protected String toAnsiString(String in, AnsiElement element) {
        return AnsiOutput.toString(element, in);
    }
}
