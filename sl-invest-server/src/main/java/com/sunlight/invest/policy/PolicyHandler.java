package com.sunlight.invest.policy;

import java.util.List;

public interface PolicyHandler<T> {
    void handle(List<T> list);
}