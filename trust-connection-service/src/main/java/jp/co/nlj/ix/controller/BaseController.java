package jp.co.nlj.ix.controller;

import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.nlj.ix.constant.APIConstant;
import jp.co.nlj.ix.constant.MessageConstant.Validate;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * <PRE>
 * BaseControllerクラスは、すべてのコントローラーの基底クラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
public abstract class BaseController {

    protected String xTracking;
    protected String xField;

    @ModelAttribute
    public void setXTracking(@RequestHeader(value = APIConstant.X_TRACKING, required = false) String xTracking,
        HttpServletResponse httpServletResponse) {
        if (!BaseUtil.isNull(xTracking) && !BaseUtil.isUUID(xTracking)) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, Validate.VALID_XTRACKING_FORMAT);
        }
        this.xTracking = xTracking;
        httpServletResponse.setHeader(APIConstant.X_TRACKING, xTracking);
    }

    @ModelAttribute
    public void setXFields(@RequestHeader(value = APIConstant.X_FIELDS, required = false) String xFields,
        HttpServletResponse httpServletResponse) {
        this.xField = xFields;
        httpServletResponse.setHeader(APIConstant.X_FIELDS, xField);
    }
}
