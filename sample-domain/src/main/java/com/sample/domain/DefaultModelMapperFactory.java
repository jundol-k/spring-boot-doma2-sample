package com.sample.domain;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.PropertyInfo;

import com.sample.domain.dto.common.DomaDto;
import com.sample.domain.dto.common.ID;

import lombok.val;

/**
 * ModelMapper 팩토리
 */
public class DefaultModelMapperFactory {

    /**
     * ModelMapper를 반환합니다.
     * 
     * @return
     */
    public static ModelMapper create() {
        // ObjectMapping 을 위한 매퍼
        val modelMapper = new ModelMapper();
        val configuration = modelMapper.getConfiguration();

        configuration.setPropertyCondition(
                // ID 필드 이외 매핑
                context -> {
                    // DomaDto 의 ID 컬럼은 덮어 쓰지 않도록 한다.
                    PropertyInfo propertyInfo = context.getMapping().getLastDestinationProperty();
                    return !(context.getParent().getDestination() instanceof DomaDto
                            && propertyInfo.getName().equals("id"));
                });

        // STRICT (엄격한) 모드로 매핑한다.
        configuration.setMatchingStrategy(MatchingStrategies.STRICT);

        // 변환기
        val idToInt = new AbstractConverter<ID<?>, Integer>() {
            @Override
            protected Integer convert(ID<?> source) {
                return source == null ? null : source.getValue();
            }
        };

        modelMapper.addConverter(idToInt);

        return modelMapper;
    }
}
