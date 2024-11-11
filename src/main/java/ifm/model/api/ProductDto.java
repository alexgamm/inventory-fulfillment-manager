package ifm.model.api;

import ifm.model.ProductStatus;

public record ProductDto(String productId, ProductStatus status, String fulfilmentCenter, Integer qty, Integer value) {
}
