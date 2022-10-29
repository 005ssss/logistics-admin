package com.logistics.order.feign;

import com.logistics.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("logistics-store")
public interface StoreFeignService {

    @GetMapping("/store/storelist/{storeId}/increase")
    public R increaseOrder(@PathVariable("storeId") int storeId);

    @GetMapping("/store/storelist/{storeId}/reduce")
    public R reduceOrder(@PathVariable("storeId") int storeId);

}
