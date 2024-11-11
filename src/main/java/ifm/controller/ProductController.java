package ifm.controller;

import ifm.mapper.ProductMapper;
import ifm.model.ProductStatus;
import ifm.model.api.ProductResponse;
import ifm.model.api.TotalValueResponse;
import ifm.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory/products")
@RequiredArgsConstructor
@Tag(
        name = "InventoryManagerAPI",
        description = "API для управления продуктами в различных центрах выполнения " +
                "(Fulfillment Centers) и поддержания данных о состоянии запасов."
)
public class ProductController implements ApiControllerHelper {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    @Operation(
            summary = "Получить все продукты по статусу",
            description = "Возвращает список всех продуктов определенного статуса"
    )
    public ProductResponse getFilteredProducts(@RequestParam ProductStatus status) {
        return new ProductResponse(
                productMapper.toDto(productRepository.findAllByStatus(status))
        );
    }

    @GetMapping("/sellable/total-value")
    @Operation(
            summary = "Получить общую стоимость всех продаваемых продуктов",
            description = "Возращает общее значение (value) всех продуктов с состоянием Sellable."
    )
    public TotalValueResponse getSellableTotalValue() {
        return new TotalValueResponse(productRepository.findSellableTotalValue(ProductStatus.Sellable));
    }

    @GetMapping("/fcenter/total-value")
    @Operation(
            summary = "Получить общую стоимость всех продуктов центра выполнения заказов",
            description = "Возвращает общее значения (value) всех продуктов для конкретного Fulfillment Center."
    )
    public TotalValueResponse getFCenterTotalValue(@RequestParam String fulfilmentCenter) {
        return new TotalValueResponse(productRepository.findFCenterTotalValue(fulfilmentCenter.toLowerCase()));
    }
}
