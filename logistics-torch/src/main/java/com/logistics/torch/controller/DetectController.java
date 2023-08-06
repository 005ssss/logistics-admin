package com.logistics.torch.controller;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.translate.TranslateException;
import com.logistics.torch.service.CenternetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("torch/detect")
public class DetectController {

    @Autowired
    private CenternetService centernetService;

    @GetMapping("/image")
    public String getPredict(@RequestParam("img") String image_name) throws IOException, TranslateException {
        Path imgPath = Paths.get("C:/Users/HP/Desktop/result/1001/", image_name);
        Image image = ImageFactory.getInstance().fromFile(imgPath);
        return centernetService.predict(image);

    }

    @PostMapping("/centernet")
    public String centernetPredict(@RequestBody String img) throws IOException, TranslateException {
        byte[] pictureBytes = new BASE64Decoder().decodeBuffer(img);
        InputStream in = new ByteArrayInputStream(pictureBytes);
        Image image = ImageFactory.getInstance().fromInputStream(in);
        return centernetService.predict(image);

    }

}
