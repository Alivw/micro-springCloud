package com.awei.product.controller;


import com.awei.comm.Constant;
import com.awei.comm.RestBean;
import com.awei.product.entity.Product;
import com.awei.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shizuwei
 * @since 2021-08-09
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/")
    public RestBean findAll() {
        List<Product> products = productService.list();
        if (null != products) {
            return new RestBean("查询商品成功", Constant.OPEN_SUCCESS, products);
        } else return new RestBean("查询商品失败", Constant.OPEN_FAILURE, null);
    }

    @GetMapping("/{pids}")
    public RestBean findByid(@PathVariable Integer pids) {
        Product product = productService.getById(pids);
        if (null != product) {
            return new RestBean("查询商品成功", Constant.OPEN_SUCCESS, product);
        } else return new RestBean("查询商品失败", Constant.OPEN_FAILURE, null);
    }

}
