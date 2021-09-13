package com.example.blogserve.model.vo;

public interface IConverter<T,E> {
    E convertToVO(T t);
}
