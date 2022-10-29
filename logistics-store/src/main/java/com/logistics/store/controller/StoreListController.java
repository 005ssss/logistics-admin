package com.logistics.store.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.logistics.store.entity.StoreListEntity;
import com.logistics.store.service.StoreListService;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.R;



/**
 * 
 *
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-09-26 21:00:14
 */
@RestController
@RequestMapping("store/storelist")
public class StoreListController {
    @Autowired
    private StoreListService storeListService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = storeListService.getList(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		StoreListEntity storeList = storeListService.getById(id);

        return R.ok().put("storeList", storeList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody StoreListEntity storeList){
		storeListService.save(storeList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody StoreListEntity storeList){
		storeListService.updateById(storeList);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		storeListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/{storeId}/increase")
    public R increaseOrder(@PathVariable("storeId") int storeId){
        StoreListEntity storeListEntity = storeListService.getById(storeId);
        storeListEntity.setStoreCap(storeListEntity.getStoreCap()-1);
        storeListService.updateById(storeListEntity);

        return R.ok();
    }

    @GetMapping("/{storeId}/reduce")
    public R reduceOrder(@PathVariable("storeId") int storeId){
        StoreListEntity storeListEntity = storeListService.getById(storeId);
        storeListEntity.setStoreCap(storeListEntity.getStoreCap()+1);
        storeListService.updateById(storeListEntity);

        return R.ok();
    }

}
