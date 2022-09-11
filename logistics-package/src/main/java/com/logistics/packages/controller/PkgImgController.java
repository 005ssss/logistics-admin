package com.logistics.packages.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.logistics.packages.entity.PkgImgEntity;
import com.logistics.packages.service.PkgImgService;
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
@RequestMapping("pkg/pkgimg")
public class PkgImgController {
    @Autowired
    private PkgImgService pkgImgService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = pkgImgService.getList(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		PkgImgEntity pkgImg = pkgImgService.getById(id);

        return R.ok().put("pkgImg", pkgImg);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PkgImgEntity pkgImg){
		pkgImgService.save(pkgImg);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PkgImgEntity pkgImg){
		pkgImgService.updateById(pkgImg);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		pkgImgService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
