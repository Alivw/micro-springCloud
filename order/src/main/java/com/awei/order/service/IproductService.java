package com.awei.order.service;

import com.awei.comm.RestBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: micro
 * @author: Awei
 * @create: 2021-08-14 10:34
 **/
@FeignClient("PRODUCT")
public interface IproductService {

    @GetMapping("/product/port/{id}")
    public RestBean getPort(@PathVariable("id") Integer id);

}
