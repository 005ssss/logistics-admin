package com.logistics.torch.service;

import ai.djl.modality.cv.Image;
import ai.djl.translate.TranslateException;

public interface CenternetService {

    String predict(Image image) throws TranslateException;
}
