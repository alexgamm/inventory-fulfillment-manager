package ifm.mapper;

import ifm.entity.ProductEntity;
import ifm.model.api.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    List<ProductDto> toDto(List<ProductEntity> productEntities);
}
