package uz.mediasolutions.siryo24bot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.siryo24bot.entity.Subcategory;
import uz.mediasolutions.siryo24bot.payload.request.SubcategoryReqDTO;
import uz.mediasolutions.siryo24bot.payload.response.SubcategoryResDTO;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {

    @Mapping(source = "categoryId", target = "category.id")
    Subcategory toEntity(SubcategoryReqDTO dto);

    SubcategoryResDTO toDto(Subcategory subcategory);

}
