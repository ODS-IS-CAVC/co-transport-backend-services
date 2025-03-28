package nlj.business.transaction.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 * <PRE>
 * ページ応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
public class PageResponseDTO<T> {

    private List<T> data;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public PageResponseDTO(Page<T> page) {
        this.data = page.getContent();
        this.currentPage = page.getNumber() + 1;
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
