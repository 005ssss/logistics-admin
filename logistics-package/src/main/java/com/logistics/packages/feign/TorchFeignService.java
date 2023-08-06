package com.logistics.packages.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("logistics-torch")
public interface TorchFeignService {

    @GetMapping("/torch/detect/image")
    public String getPredict(@RequestParam("img") String image_name);

    @PostMapping("/torch/detect/centernet")
    public String centernetPredict(@RequestBody String image);

}
