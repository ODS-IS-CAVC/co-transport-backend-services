package jp.co.nlj.gateway.dto.semiDynamicInfo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * SemiDynamicInfoResponseDTOクラスは、セミダイナミック情報レスポンスDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SemiDynamicInfoResponseDTO {

    @JsonProperty("dataModelType")
    private String dataModelType;

    @JsonProperty("attribute")
    private Attribute attribute;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Attribute {

        @JsonProperty("result")
        private int result;

        @JsonProperty("targetTime")
        private String targetTime;

        @JsonProperty("errors")
        private Error errors;

        @JsonProperty("level1")
        private List<Level1ResponseDTO> level1;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Error {

        @JsonProperty("trafficInfoError")
        private TrafficInfoError trafficInfoError;

        @JsonProperty("eventRegulationInfoError")
        private EventRegulationInfoError eventRegulationInfoError;

        @JsonProperty("weatherInfoError")
        private WeatherInfoError weatherInfoError;

        @JsonProperty("shelterAreaInfoError")
        private ShelterAreaInfoError shelterAreaInfoError;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TrafficInfoError {

        @JsonProperty("errorCode")
        private int errorCode;

        @JsonProperty("message")
        private String message;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EventRegulationInfoError {

        @JsonProperty("errorCode")
        private int errorCode;

        @JsonProperty("message")
        private String message;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WeatherInfoError {

        @JsonProperty("errorCode")
        private int errorCode;

        @JsonProperty("message")
        private String message;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ShelterAreaInfoError {

        @JsonProperty("errorCode")
        private int errorCode;

        @JsonProperty("message")
        private String message;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Level1ResponseDTO {

        @JsonProperty("spatialID")
        private String spatialID;

        @JsonProperty("bbox")
        private double[] bbox;

        @JsonProperty("weatherInfo")
        private WeatherInfo weatherInfo;

        @JsonProperty("level2")
        private List<Level2ResponseDTO> level2;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WeatherInfo {

        @JsonProperty("dateTime")
        private String dateTime;

        @JsonProperty("weather")
        private String weather;

        @JsonProperty("temperature")
        private double temperature;

        @JsonProperty("windDirection")
        private double windDirection;

        @JsonProperty("windSpeed")
        private double windSpeed;

        @JsonProperty("humidity")
        private double humidity;

        @JsonProperty("rainfall")
        private double rainfall;

        @JsonProperty("updatedAt")
        private String updatedAt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Level2ResponseDTO {

        @JsonProperty("spatialID")
        private String spatialID;

        @JsonProperty("bbox")
        private double[] bbox;

        @JsonProperty("trafficInfo")
        private TrafficInfo trafficInfo;

        @JsonProperty("eventRegulationInfo")
        private List<EventRegulationInfo> eventRegulationInfo;

        @JsonProperty("shelterAreaInfo")
        private ShelterAreaInfo shelterAreaInfo;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TrafficInfo {

        @JsonProperty("dateTime")
        private String dateTime;

        @JsonProperty("severity")
        private int severity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EventRegulationInfo {

        @JsonProperty("dateTime")
        private String dateTime;

        @JsonProperty("cause")
        private String cause;

        @JsonProperty("regulation")
        private String regulation;

        @JsonProperty("plannedStartTimestamp")
        private String planedStartTime;

        @JsonProperty("plannedEndTimestamp")
        private String planedEndTime;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ShelterAreaInfo {

        @JsonProperty("dateTime")
        private String dateTime;

        @JsonProperty("shelterAreaName")
        private String shelterAreaName;

        @JsonProperty("roadType")
        private String roadType;

        @JsonProperty("maxCapacity")
        private int maxCapacity;

        @JsonProperty("currentCount")
        private int currentCount;
    }
}
