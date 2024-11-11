package ifm.repository;

import ifm.entity.ProductEntity;
import ifm.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByStatus(ProductStatus status);

    @Query("select sum(p.value) from ProductEntity p where p.status = :status")
    Long findSellableTotalValue(ProductStatus status);

    @Query("select coalesce(sum(p.value), 0) from ProductEntity p where p.fulfilmentCenter = :fulfilmentCenter")
    Long findFCenterTotalValue(String fulfilmentCenter);

}
