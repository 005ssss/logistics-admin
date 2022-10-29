package com.logistics.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.logistics.order.entity.OrderListEntity;
import com.logistics.order.service.OrderListService;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.R;



/**
 * 
 *
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-09-26 20:57:32
 */
@RestController
@RequestMapping("order/orderlist")
public class OrderListController {
    @Autowired
    private OrderListService orderListService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = orderListService.getList(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		OrderListEntity orderList = orderListService.getById(id);

        return R.ok().put("orderList", orderList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OrderListEntity orderList){
		orderListService.save(orderList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OrderListEntity orderList){
//		orderListService.updateById(orderList);
        orderListService.changeState(orderList);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
		orderListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
