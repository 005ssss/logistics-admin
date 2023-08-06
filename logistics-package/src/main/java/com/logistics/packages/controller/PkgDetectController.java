package com.logistics.packages.controller;

import com.logistics.packages.component.WebSocketServer;
import com.logistics.packages.entity.PkgImgEntity;
import com.logistics.packages.entity.PkgListEntity;
import com.logistics.packages.entity.PkgProhibitEntity;
import com.logistics.packages.feign.TorchFeignService;
import com.logistics.packages.service.PkgImgService;
import com.logistics.packages.service.PkgListService;
import com.logistics.packages.service.PkgProhibitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("pkg/detect")
public class PkgDetectController {

    @Autowired
    private PkgListService listService;

    @Autowired
    private PkgProhibitService prohibitService;

    @Autowired
    private PkgImgService imgService;

    @Autowired
    private WebSocketServer socketServer;

    @Autowired
    private TorchFeignService torchService;

    @Value("${logistics.path.save}")
    private String savePath;

    @GetMapping("/getimage")
    public void imageShow(HttpServletResponse response, @RequestParam("device") int device) throws IOException {
        String imgPath = savePath + listService.getDetectImage(device);
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

    //本地检测上传图片与结果接口
    @PostMapping("/centernet")
    @Transactional
    public String getImage(@RequestParam("name") String name, @RequestParam("image") String image,
                           @RequestParam("labels") String labels, @RequestParam("boxes") String boxes,
                           @RequestParam("device") int device){
        System.out.println(name);
        byte[] pictureBytes = null;
        Date date = new Date();
        String newPath = String.format("%d/", device) + name;
        try{
            pictureBytes = new BASE64Decoder().decodeBuffer(image);
            String imagePath = savePath + newPath;
            OutputStream os = new FileOutputStream(imagePath);
            os.write(pictureBytes);
            os.flush();
            os.close();
        }
        catch (Exception e){
            return null;
        }

        listService.putDetectImage(device, newPath);

        PkgImgEntity img = new PkgImgEntity();
        img.setId(0);
        img.setImgNumber(name.split("\\.")[0]);
        img.setDetectDevice(device);
        img.setDetectTime(date);
        PkgListEntity pkg = new PkgListEntity();
        pkg.setId(0);
        pkg.setImgNumber(img.getImgNumber());
        pkg.setDetectDevice(device);
        pkg.setPkgNumber(img.getImgNumber());
        pkg.setDetectTime(date);
        HashMap<String, Integer> map = new HashMap<String, Integer>(){{
            put("刀", 0);
            put("枪", 0);
            put("钳子", 0);
            put("扳手", 0);
            put("剪刀", 0);
        }};
        if (labels.length() == 0){
            pkg.setPkgType(0);
            img.setPkgType(0);
        } else {
            pkg.setPkgType(1);
            img.setPkgType(1);
            String[] data = boxes.split(",");
            int i = 0;
            for(String label :labels.split(",")){
                PkgProhibitEntity prohibit = new PkgProhibitEntity();
                prohibit.setItemType(label);
                map.put(label, map.get(label)+1);
                prohibit.setId(0);
                prohibit.setPkgNumber(img.getImgNumber());
                prohibit.setItemX(Integer.parseInt(data[i*5+1]));
                prohibit.setItemY(Integer.parseInt(data[i*5]));
                prohibit.setItemW(Integer.parseInt(data[i*5+3])-prohibit.getItemX());
                prohibit.setItemH(Integer.parseInt(data[i*5+2])-prohibit.getItemY());
                prohibit.setItemConf(Float.parseFloat(data[i*5+4]));
                prohibit.setDetectTime(date);
                prohibitService.save(prohibit);
                i++;
            }
        }
        img.setDao(map.get("刀"));
        img.setQiang(map.get("枪"));
        img.setBanshou(map.get("扳手"));
        img.setQianzi(map.get("钳子"));
        img.setJiandao(map.get("剪刀"));
        imgService.save(img);
        listService.save(pkg);
        socketServer.sendMessage("ok",String.format("%d", device));
        return "/centernet";
    }

    @PostMapping("/detect")
    @Transactional
    public String detectImage(@RequestParam("name") String name, @RequestParam("device") int device){
        System.out.println(name);
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(savePath + "1001/" + name);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String image = encoder.encode(data);
        String result = torchService.centernetPredict(image);
        byte[] pictureBytes = null;
        Date date = new Date();

        PkgImgEntity img = new PkgImgEntity();
        img.setId(0);
        img.setImgNumber(name.split("\\.")[0]);
        img.setDetectDevice(device);
        img.setDetectTime(date);
        PkgListEntity pkg = new PkgListEntity();
        pkg.setId(0);
        pkg.setImgNumber(img.getImgNumber());
        pkg.setDetectDevice(device);
        pkg.setPkgNumber(img.getImgNumber());
        pkg.setDetectTime(date);

        HashMap<String, Integer> map = new HashMap<String, Integer>(){{
            put("刀", 0);
            put("枪", 0);
            put("钳子", 0);
            put("扳手", 0);
            put("剪刀", 0);
        }};
        if (!StringUtils.hasLength(result)){
            pkg.setPkgType(0);
            img.setPkgType(0);
        } else {
            pkg.setPkgType(1);
            img.setPkgType(1);
            String[] objects = result.split(";");
            for(String object : objects){
                PkgProhibitEntity prohibit = new PkgProhibitEntity();
                String[] values = object.split(",");
                prohibit.setItemType(values[0]);
                map.put(values[0], map.get(values[0])+1);
                prohibit.setId(0);
                prohibit.setPkgNumber(img.getImgNumber());
                prohibit.setItemX((int)Float.parseFloat(values[1]));
                prohibit.setItemY((int) Float.parseFloat(values[2]));
                prohibit.setItemW((int) Float.parseFloat(values[3]));
                prohibit.setItemH((int) Float.parseFloat(values[4]));
                prohibit.setItemConf(Float.parseFloat(values[5]));
                prohibit.setDetectTime(date);
                prohibitService.save(prohibit);
            }
        }

        img.setDao(map.get("刀"));
        img.setQiang(map.get("枪"));
        img.setBanshou(map.get("扳手"));
        img.setQianzi(map.get("钳子"));
        img.setJiandao(map.get("剪刀"));
        imgService.save(img);
        listService.save(pkg);

        String newPath = String.format("%d/", device) + name;
        try{
            pictureBytes = new BASE64Decoder().decodeBuffer(image);
            String imagePath = savePath + newPath;
            OutputStream os = new FileOutputStream(imagePath);
            os.write(pictureBytes);
            os.flush();
            os.close();
        }
        catch (Exception e){
            return null;
        }

        listService.putDetectImage(device, newPath);

        return result;

//        PkgImgEntity img = new PkgImgEntity();
//        img.setId(0);
//        img.setImgNumber(name.split("\\.")[0]);
//        img.setDetectDevice(device);
//        img.setDetectTime(date);
//        PkgListEntity pkg = new PkgListEntity();
//        pkg.setId(0);
//        pkg.setImgNumber(img.getImgNumber());
//        pkg.setDetectDevice(device);
//        pkg.setPkgNumber(img.getImgNumber());
//        pkg.setDetectTime(date);
//        HashMap<String, Integer> map = new HashMap<String, Integer>(){{
//            put("刀", 0);
//            put("枪", 0);
//            put("钳子", 0);
//            put("扳手", 0);
//            put("剪刀", 0);
//        }};
//        if (labels.length() == 0){
//            pkg.setPkgType(0);
//            img.setPkgType(0);
//        } else {
//            pkg.setPkgType(1);
//            img.setPkgType(1);
//            String[] data = boxes.split(",");
//            int i = 0;
//            for(String label :labels.split(",")){
//                PkgProhibitEntity prohibit = new PkgProhibitEntity();
//                prohibit.setItemType(label);
//                map.put(label, map.get(label)+1);
//                prohibit.setId(0);
//                prohibit.setPkgNumber(img.getImgNumber());
//                prohibit.setItemX(Integer.parseInt(data[i*5+1]));
//                prohibit.setItemY(Integer.parseInt(data[i*5]));
//                prohibit.setItemW(Integer.parseInt(data[i*5+3])-prohibit.getItemX());
//                prohibit.setItemH(Integer.parseInt(data[i*5+2])-prohibit.getItemY());
//                prohibit.setItemConf(Float.parseFloat(data[i*5+4]));
//                prohibit.setDetectTime(date);
//                prohibitService.save(prohibit);
//                i++;
//            }
//        }
//        img.setDao(map.get("刀"));
//        img.setQiang(map.get("枪"));
//        img.setBanshou(map.get("扳手"));
//        img.setQianzi(map.get("钳子"));
//        img.setJiandao(map.get("剪刀"));
//        imgService.save(img);
//        listService.save(pkg);
//        socketServer.sendMessage("ok",String.format("%d", device));
//        return "/centernet";
    }


    public void startMsg() {
        try {
            URL url = new URL("http://127.0.1.3:5000/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;

            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
