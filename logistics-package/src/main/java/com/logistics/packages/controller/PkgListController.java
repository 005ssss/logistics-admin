package com.logistics.packages.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.packages.entity.PkgListEntity;
import com.logistics.packages.service.PkgListService;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.R;



/**
 * 
 *
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-09-06 21:57:13
 */
@RestController
@RequestMapping("pkg/pkglist")
public class PkgListController {
    @Autowired
    private PkgListService pkgListService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pkgListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		PkgListEntity pkgList = pkgListService.getById(id);

        return R.ok().put("pkgList", pkgList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PkgListEntity pkgList){
		pkgListService.save(pkgList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PkgListEntity pkgList){
		pkgListService.updateById(pkgList);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		pkgListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
