package com.awei.order.controller;


import cn.hutool.core.bean.BeanUtil;
import com.awei.comm.Constant;
import com.awei.comm.RestBean;
import com.awei.comm.order.entity.Order;
import com.awei.comm.product.entity.Product;
import com.awei.order.service.IOrderService;
import com.awei.order.service.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shizuwei
 * @since 2021-08-09
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Resource
    private IProductService productService;
    @Value("${serverUrl}")
    private String serverUrl;
    @GetMapping("/")
    public RestBean findAll() {
//        return new RestBean("查询订单成功", Constant.OPEN_SUCCESS, orderService.list());
        return productService.findAllProducts();
    }


    @GetMapping("/{pid}")
    public RestBean resTmplateGet4Object(@PathVariable Integer pid) {
        return productService.feignPort(pid);
    }



    @GetMapping("/port/{pid}")
    public RestBean getRemote11(@PathVariable Integer pid) {
        ResponseEntity<RestBean> exchange = restTemplate.exchange(serverUrl+"product/port/  " + pid, HttpMethod.GET, null, RestBean.class);
        System.out.println(exchange);
        RestBean body = exchange.getBody();
        return body;
    }

    @GetMapping("/restemplate/{pid}")
    @HystrixCommand(fallbackMethod = "restemplatePidFallback")
    public RestBean restemplatePid(@PathVariable Integer pid) {
        ResponseEntity<RestBean> exchange = restTemplate.exchange(serverUrl+"/product/" + pid, HttpMethod.GET, null, RestBean.class);
        System.out.println(exchange);
        RestBean body = exchange.getBody();
        return body;
    }
    public RestBean restemplatePidFallback(@PathVariable Integer pid) {
        return new RestBean("给你个果盘", Constant.OPEN_SUCCESS, pid);
    }

    @PostMapping("/{pid}/{uname}")
    public RestBean putOrder(@PathVariable Integer pid, @PathVariable String uname) {
        RestBean forObject = restTemplate.getForObject("http://localhost:8080/product/" + pid, RestBean.class);
        if (Constant.OPEN_SUCCESS == forObject.getStatus()) {
            Order order = new Order();
            Map product = (Map) forObject.getObj();
            order.setUname(uname);
            BeanUtil.fillBeanWithMap(product, order, false);
            if (orderService.save(order)) {
                return new RestBean("订单生成成功", Constant.OPEN_SUCCESS, order);
            }
            return new RestBean("订单生成失败", Constant.OPEN_FAILURE, null);
        }
        return forObject;
    }

    @GetMapping("/remote/{pid}")
    public RestBean getRemote1(@PathVariable Integer pid) {
        List<ServiceInstance> list = discoveryClient.getInstances("PRODUCT");
        String url = list.get(0).getUri().toString();
        ResponseEntity<RestBean> exchange = restTemplate.exchange(url+"/product/" + pid, HttpMethod.GET, null, RestBean.class);
        System.out.println(exchange);
        RestBean body = exchange.getBody();
        return body;
    }

    @DeleteMapping("/remote/{pid}")
    public RestBean getRemote2(@PathVariable Integer pid) {
        ResponseEntity<RestBean> exchange = restTemplate.exchange("http://localhost:8080/product/" + pid, HttpMethod.DELETE, null, RestBean.class);
        System.out.println(exchange);
        RestBean body = exchange.getBody();
        return body;
    }

    @PutMapping("/remote")
    public RestBean updateRemote(@RequestBody Product product) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = new ObjectMapper().writeValueAsString(product);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<RestBean> exchange = restTemplate.exchange("http://localhost:8080/product/", HttpMethod.PUT, entity, RestBean.class, product);
        return exchange.getBody();
    }

    @GetMapping("/cookie")
    public RestBean cookie() {
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList<>();
        cookies.add("uname1=aaa");
        cookies.add("uname2=vvv");
        cookies.add("uname3=bbb");
        headers.put("Cookie", cookies);
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<RestBean> exchange = restTemplate.exchange("http://localhost:8080/product/cookie", HttpMethod.GET, entity, RestBean.class);
        return exchange.getBody();
    }


}
