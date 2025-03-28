package jp.co.nlj.gateway.dto.semiDynamicInfo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.List;
import jp.co.nlj.gateway.aop.proxy.ValidateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <PRE>
 * SemiDynamicInfoRequestDTOクラスは、セミダイナミック情報リクエストDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class SemiDynamicInfoRequestDTO {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ValidateField(notNull = true)
    private LocalDateTime targetTime;

    @ValidateField(notNull = true)
    private List<RequestInfoDTO> requestInfo;
}
