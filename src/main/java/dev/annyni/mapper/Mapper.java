package dev.annyni.mapper;

/**
 * todo Document type Mapper
 */
public interface Mapper<F, T>{
    T mapFrom(F object);

    F mapToDto(T object);
}
