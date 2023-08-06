package com.logistics.torch.model;

import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.BoundingBox;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.Rectangle;
import ai.djl.modality.cv.transform.Normalize;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.repository.zoo.Criteria;
import ai.djl.translate.NoBatchifyTranslator;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CenternetFactoryBean implements FactoryBean<Predictor<Image, DetectedObjects>> {


    @Override
    public Predictor<Image, DetectedObjects> getObject() throws Exception {
        Translator<Image, DetectedObjects> centerTranslator = new NoBatchifyTranslator<Image,DetectedObjects>(){

            @Override
            public NDList processInput(TranslatorContext translatorContext, Image image) throws Exception {
                NDArray ndArray = image.toNDArray(translatorContext.getNDManager(), Image.Flag.COLOR);
                Resize resize = new Resize(512, 512);
                ndArray = resize.transform(ndArray);

                ndArray = new ToTensor().transform(ndArray);

                Normalize normalize = new Normalize(new float[]{0.4078f, 0.24471f, 0.4703f}, new float[]{0.2886f, 0.2741f, 0.2781f});
                ndArray = normalize.transform(ndArray);
                ndArray = ndArray.reshape(1,3,512,512);

                return new NDList(ndArray);
            }

            @Override
            public DetectedObjects processOutput(TranslatorContext translatorContext, NDList ndList) throws Exception {
                float[] flattened = ((NDArray)ndList.get(0)).toFloatArray();
                float[] wh = ((NDArray)ndList.get(1)).toFloatArray();
                float[] offset = ((NDArray)ndList.get(2)).toFloatArray();
                List<Integer> index = new ArrayList<>();
                for (int i=0; i < flattened.length; i++){
                    if (flattened[i] > 0.4){
                        index.add(i);
                    }
                }
                String[] classes = {"枪","刀","扳手","钳子","剪刀"};
                int temp = 0;
                int[][] result = new int[index.size()][5];
                float[] confidence = new float[index.size()];
                for (int i=0; i < index.size(); i++){
                    result[i][0] = index.get(i) /128/128;
                    confidence[i] = flattened[index.get(i)];
                    temp = index.get(i)-128*128*result[i][0];
                    result[i][3] = temp%128*4;
                    result[i][4] = temp/128*4;
                    result[i][1] = (int)wh[temp]*4;
                    result[i][2] = (int)wh[temp+128*128]*4;
                    result[i][3] += (int)(offset[temp]*4);
                    result[i][4] += (int)(offset[temp+128*128]*4);

                }
                List<String> retClasses = new ArrayList();
                List<Double> retProbs = new ArrayList();
                List<BoundingBox> retBB = new ArrayList();
                if (index.size() == 0){
                    return new DetectedObjects(retClasses,retProbs,retBB);
                }

                Set<Integer> set = new HashSet<>();
                for (int j = 0; j < index.size(); j++){
                    if(set.contains(j)){
                        continue;
                    }
                    temp = result[j][0];
                    float tempConf = confidence[j];
                    int x1 = result[j][3] - result[j][1] / 2;
                    int y1 = result[j][4] - result[j][2] / 2;
                    int w = result[j][1];
                    int h = result[j][2];
                    for(int i = j+1; i < index.size();i++){
                        int temp2 = result[i][0];
                        float tempConf2 = confidence[i];
                        int x2 = result[i][3] - result[i][1] / 2;
                        int y2 = result[i][4] - result[i][2] / 2;
                        int w2 = result[i][1];
                        int h2 = result[i][2];
                        if (temp2 != temp)
                        {
                            break;
                        } else if (bbIou(x1, y1, w, h, x2, y2, w2, h2) > 0.4){
                            set.add(i);
                            if (tempConf2 > tempConf) {
                                temp = temp2;
                                tempConf = tempConf2;
                                x1 = x2;
                                y1 = y2;
                                w = w2;
                                h = h2;
                            }
                        }
                    }
                    retClasses.add(classes[temp]);
                    retProbs.add((double)tempConf);
                    retBB.add(new Rectangle(x1, y1, w, h));
                }



                return new DetectedObjects(retClasses,retProbs,retBB);
            }

            private float bbIou(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
                int w = Math.min(x1+w1,x2+w2) - Math.max(x1, x2);
                int h = Math.min(y1+h1,y2+h2) - Math.max(y1, y2);
                int inter = w >= 0 && h >= 0 ? w * h : 0;
                int union = w1 * h1 + w2 * h2 - inter;
                return (float) inter / union;
            }
        };
        String resources = "C:\\Users\\HP\\IdeaProjects\\torchJava\\src\\main\\resources\\";
        Path modeldir = Paths.get(resources,"ep071-loss1.658-val_loss1.688.pt");

        Criteria<Image, DetectedObjects> criteria = Criteria.builder()
                .setTypes(Image.class, DetectedObjects.class)
                .optModelPath(modeldir)
                .optTranslator(centerTranslator)
                .build();

        return criteria.loadModel().newPredictor();
    }

    @Override
    public Class<?> getObjectType() {
        return Predictor.class;
    }
}
