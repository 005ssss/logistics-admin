package com.logistics.packages.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.logistics.packages.entity.PkgListEntity;
import com.logistics.packages.service.PkgListService;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.R;

import javax.servlet.http.HttpServletResponse;


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

    @Value("${logistics.path.save}")
    private String savePath;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestBody Map<String, Object> params) throws ParseException {
        PageUtils page = pkgListService.getList(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/getimage/{device}/{imgNumber}")
    public void getImage(@PathVariable("device") String device, @PathVariable("imgNumber") String imgNumber, HttpServletResponse response) throws FileNotFoundException {
        String imgPath = savePath + device + "/" + imgNumber +".jpg";
        FileInputStream is = new FileInputStream(imgPath);
        // 文件后缀
        String suffix = imgPath.substring(imgPath.lastIndexOf("."));
        // 响应图片
        response.setContentType("image/" + suffix);

        try (
                FileInputStream fis = new FileInputStream(imgPath);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            System.out.println("读取图片失败");
        }
    }

    @PostMapping("/last")
    public R getLast(@RequestBody Map<String, Object> params){
        PageUtils page = pkgListService.getLast(params);
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

    @GetMapping("/total")
    public R getTotal(){

        return R.ok().put("result", pkgListService.getTotalCount());
    }

}
