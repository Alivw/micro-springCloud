package com.awei.order.service;

import com.awei.comm.RestBean;
import com.awei.order.service.impl.IProductServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: micro
 * @author: Awei
 * @create: 2021-08-14 13:58
 **/
@FeignClient(value = "PRODUCT",fallback = IProductServiceFallback.class)
@Component
public interface IProductService {

    @GetMapping("/product/{pid}")
    RestBean feignPort(@PathVariable("pid") Integer pid);

    @GetMapping("/product/")
    RestBean findAllProducts();
}
