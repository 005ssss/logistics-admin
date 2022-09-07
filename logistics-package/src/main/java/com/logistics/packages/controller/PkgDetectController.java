package com.logistics.packages.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@RestController
@RequestMapping("pkg/detect")
public class PkgDetectController {

    @GetMapping("/getimage")
    public void imageShow(HttpServletResponse response) throws IOException {
        String imgPath = "C:/Users/HP/Desktop/detect-system-main/results.jpg";
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

    @GetMapping("/centernet")
    public String predictImage(){
        System.out.println("centernet");
        return "/centernet";
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
