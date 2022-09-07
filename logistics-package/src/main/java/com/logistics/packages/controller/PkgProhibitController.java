package com.logistics.packages.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.packages.entity.PkgProhibitEntity;
import com.logistics.packages.service.PkgProhibitService;
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
@RequestMapping("pkg/pkgprohibit")
public class PkgProhibitController {
    @Autowired
    private PkgProhibitService pkgProhibitService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pkgProhibitService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		PkgProhibitEntity pkgProhibit = pkgProhibitService.getById(id);

        return R.ok().put("pkgProhibit", pkgProhibit);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PkgProhibitEntity pkgProhibit){
		pkgProhibitService.save(pkgProhibit);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PkgProhibitEntity pkgProhibit){
		pkgProhibitService.updateById(pkgProhibit);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		pkgProhibitService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
