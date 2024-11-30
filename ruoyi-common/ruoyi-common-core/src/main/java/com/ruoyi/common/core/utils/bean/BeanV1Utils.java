package com.ruoyi.common.core.utils.bean;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class BeanV1Utils {

    public static <T> T toBean(Object source, Class<T> targetClass) {
        return BeanUtil.toBean(source, targetClass);
    }

    public static <T> T toBean(Object source, Class<T> targetClass, Consumer<T> peek) {
        T target = toBean(source, targetClass);
        if (target != null) {
            peek.accept(target);
        }
        return target;
    }

    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        return convertList(source, s -> toBean(s, targetType));
    }

    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType, Consumer<T> peek) {
        List<T> list = toBean(source, targetType);
        if (list != null) {
            list.forEach(peek);
        }
        return list;
    }

    public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <S, T> Page<T> toBean(Page<S> source, Class<T> targetType) {
        return toBean(source, targetType, null);
    }

    public static <S, T> Page<T> toBean(Page<S> source, Class<T> targetType, Consumer<T> peek) {
        if (source == null) {
            return null;
        }
        List<T> list = toBean(source.getRecords(), targetType);
        if (peek != null) {
            list.forEach(peek);
        }

        Page<T> newPage = new Page<>();
        newPage.setRecords(list);
        newPage.setTotal(source.getTotal());
        newPage.setCurrent(source.getCurrent());
        newPage.setSize(source.getSize());
        newPage.setOrders(source.getOrders());
        newPage.setOptimizeCountSql(source.optimizeCountSql());
        newPage.setSearchCount(source.isSearchCount());
        newPage.setMaxLimit(source.getMaxLimit());
        newPage.setCountId(source.getCountId());
        newPage.setPages(source.getPages());
        return newPage;
    }

}