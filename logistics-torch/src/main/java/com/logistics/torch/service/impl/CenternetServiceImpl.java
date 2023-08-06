package com.logistics.torch.service.impl;

import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.Rectangle;
import ai.djl.translate.TranslateException;
import com.alibaba.fastjson.JSON;
import com.logistics.torch.model.CenternetFactoryBean;
import com.logistics.torch.service.CenternetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("centernetService")
public class CenternetServiceImpl implements CenternetService {

    @Autowired
    private Predictor centernet;

    @Override
    public String predict(Image image) throws TranslateException {
        DetectedObjects result = (DetectedObjects) centernet.predict(image);
        List<DetectedObjects.DetectedObject> items = result.items();
        StringBuilder sb = new StringBuilder();
        for(DetectedObjects.DetectedObject o : items){
            sb.append(o.getClassName());
            sb.append(',');
            Rectangle bounds = o.getBoundingBox().getBounds();
            sb.append(bounds.getX());
            sb.append(',');
            sb.append(bounds.getY());
            sb.append(',');
            sb.append(bounds.getWidth());
            sb.append(',');
            sb.append(bounds.getHeight());
            sb.append(',');
            sb.append(o.getProbability());
            sb.append(';');
        }
        if(sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } else return "";
    }

}
