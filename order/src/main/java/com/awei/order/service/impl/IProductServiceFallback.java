package com.awei.order.service.impl;

import com.awei.comm.Constant;
import com.awei.comm.RestBean;
import com.awei.order.service.IProductService;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-08-15 09:40
 **/
@Component
public class IProductServiceFallback implements IProductService {

    @Override
    public RestBean feignPort(Integer pid) {
        return new RestBean("feign给的果盘", Constant.OPEN_FAILURE, pid);
    }

    @Override
    public RestBean findAllProducts() {
        return null;
    }
}
