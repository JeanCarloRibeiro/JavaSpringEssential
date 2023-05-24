package com.devsuperior.crud.utils;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
public class ModelMapperUtils {

  private static ModelMapper modelMapper;

  public static <T, S> T entityToDto(S entity, Class<T> dtoClass) {
    modelMapper = new ModelMapper();
    return modelMapper.map(entity, dtoClass);
  }

  public static <T, S> T dtoToEntity(S dto, Class<T> entityClass) {
    modelMapper = new ModelMapper();
    return modelMapper.map(dto, entityClass);
  }

}
