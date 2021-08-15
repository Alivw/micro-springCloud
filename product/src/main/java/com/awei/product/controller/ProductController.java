package com.awei.product.controller;


import com.awei.comm.Constant;
import com.awei.comm.RestBean;
import com.awei.comm.product.entity.Product;
import com.awei.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
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

    @GetMapping("/{pid}")
    public RestBean findByid(@PathVariable Integer pid) throws InterruptedException {
        System.out.println("进入提供者");
        Thread.sleep(pid * 1000);
//        Product product = productService.getById(pid);
//        if (null != product) {
//            return new RestBean("查询商品成功", Constant.OPEN_SUCCESS, product);
//        } else return new RestBean("查询商品失败", Constant.OPEN_FAILURE, null);
        return new RestBean("商品查询成功", Constant.OPEN_SUCCESS, pid);
    }

    @GetMapping("/port/{pid}")
    public RestBean findPortByid(@PathVariable Integer pid, HttpServletRequest request) {
        int port = request.getServerPort();
        Product product = productService.getById(pid);
        if (null != product) {
            return new RestBean("查询商品成功", Constant.OPEN_SUCCESS, port);
        } else return new RestBean("查询商品失败", Constant.OPEN_FAILURE, null);
    }
    @DeleteMapping("/{pid}")
    public RestBean removeById(@PathVariable Integer pid) {
        System.out.println("商品获取到：" + pid);
        return new RestBean("查询商品成功", Constant.OPEN_SUCCESS, pid);
    }

    @PutMapping("/")
    public RestBean updateById(Product product) {
        if (productService.updateById(product)) {
            return new RestBean("修改商品成功", Constant.OPEN_SUCCESS, product);
        }
        return new RestBean("修改商品失败", Constant.OPEN_FAILURE, null);
    }

    @GetMapping("/cookie")
    public RestBean cookie(HttpServletRequest request) {
        // 获取当前容器中所有的cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println("cookies:" + cookie.getName() + "  " + cookie.getValue());
        }
        //获取所有请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println("请求头" + name + "====" + value);
        }
        return new RestBean("获取cookie成功", Constant.OPEN_SUCCESS, null);
    }
}
