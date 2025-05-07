//package com.example.demo.feign;
//
//import com.example.demo.dto.ProductDTO;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "PRODUCT-SERVICE",path="/product")
//public interface ProductClient {
//
//    @GetMapping("/products/fetchById/{id}")
//    ProductDTO getProductById(@PathVariable("id") int id);
//}
