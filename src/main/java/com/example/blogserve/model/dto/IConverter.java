package com.example.blogserve.model.dto;

public interface IConverter<T,E> {
    E convertToPO(T t);
}
