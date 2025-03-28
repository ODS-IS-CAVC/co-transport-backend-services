package nlj.business.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.dto.ProposeAbilityDTO;
import nlj.business.transaction.dto.request.TransportAbilityProposalRequest;
import nlj.business.transaction.dto.request.TransportAbilityPublicResponseDTO;
import nlj.business.transaction.dto.request.TransportAbilityPublicSearch;
import nlj.business.transaction.dto.response.TransportAbilityPublicIXResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 車両利用可能リソース品目サービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface VehicleAvbResourceItemService {

    /**
     * ページネーションされた輸送能力を取得する。
     *
     * @param searchRequest 輸送能力の検索リクエスト
     * @return ページネーションされた輸送能力のレスポンス
     */
    Page<TransportAbilityPublicResponseDTO> getPagedTransportAbility(TransportAbilityPublicSearch searchRequest);

    /**
     * ページネーションされた輸送能力IXを取得する。
     *
     * @param searchRequest      輸送能力の検索リクエスト
     * @param httpServletRequest HTTPリクエスト
     * @return ページネーションされた輸送能力IXのレスポンス
     */
    Page<TransportAbilityPublicIXResponseDTO> getPagedTransportAbilityIX(TransportAbilityPublicSearch searchRequest,
        HttpServletRequest httpServletRequest);

    /**
     * IDによる車両利用可能リソース品目を取得する。
     *
     * @param id 車両利用可能リソース品目のID
     * @return 車両利用可能リソース品目
     */
    VehicleAvbResourceItem findById(Long id);

    /**
     * 輸送能力提案アイテムを取得する。
     *
     * @param request 輸送能力提案リクエスト
     * @return 輸送能力提案アイテムのページ
     */
    Page<ProposeAbilityDTO> getProposalItem(TransportAbilityProposalRequest request);
}
