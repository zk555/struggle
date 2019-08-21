package com.gy.struggle.qrCode.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName QRCodeUtil
 * @Description TODO 二维码工具类
 * @Author SZeke
 * @Date 2019/8/20 11:12
 * @Version 1.0
 */
public class QRCodeUtil {
    private static final Logger log = LoggerFactory.getLogger(QRCodeUtil.class);


    /**
     * @Author SZeke
     * @Description //TODO  生成二维码文件测试
     * @Date 11:13 2019/8/20
     * @Param [filePath, fileName, number, phone]
     * @return void
     **/
    public static void generatEncodeTest(String filePath, String fileName, String number, String phone) {
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        JSONObject json = new JSONObject();
        json.put("number",number);
        json.put("phone", phone);
        String content = json.toJSONString();// 内容
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try{
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
            String path = FileSystems.getDefault().getPath(filePath, fileName).toString();
            File file = new File(path);
            MatrixToImageWriter.writeToFile(bitMatrix, format, file);// 输出图像
            System.out.println("二维码输出成功");
            System.out.println("图片地址：" + filePath + fileName);
            System.out.println("---------------------------");
        }catch (WriterException e)
        {
            e.printStackTrace();
            System.out.println("二维码输出异常");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("二维码输出异常");
        }
    }

    public static void parseDecodeTest(String filePath) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            JSONObject content = JSONObject.parseObject(result.getText());
            StringBuffer sb = new StringBuffer();
            sb.append("编号：")
                    .append(content.getString("number"))
                    .append("\r\n")
                    .append("手机号码:")
                    .append(content.getString("phone"));
            String returnText = sb.toString();
            System.out.println(returnText);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author SZeke
     * @Description //TODO 生成二维码输出流
     * @Date 11:18 2019/8/20
     * @Param [response, number, phone]
     * @return void
     **/
    public static void generatEncode(HttpServletResponse response, String number, String phone) {
        JSONObject json = new JSONObject();
        json.put("number",number);
        json.put("phone", phone);
        String content = json.toJSONString();// 内容
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try
        {

            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
            MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());// 输出图像
            log.info("二维码输出成功");
        }
        catch (WriterException e)
        {
            e.printStackTrace();
            log.error("二维码输出异常");
        }catch (IOException e)
        {
            e.printStackTrace();
            log.error("二维码输出异常");
        }
    }

    private boolean orCode(String content, String path) {
        /*
         * 图片的宽度和高度
         */
        int width = 300;
        int height = 300;
        // 图片的格式
        String format = "png";
        // 二维码内容
        // String content = "hello,word";

        // 定义二维码的参数
        HashMap hints = new HashMap();
        // 定义字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 纠错的等级 L > M > Q > H 纠错的能力越高可存储的越少，一般使用M
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置图片边距
        hints.put(EncodeHintType.MARGIN, 2);

        try {
            // 最终生成 参数列表 （1.内容 2.格式 3.宽度 4.高度 5.二维码参数）
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 写入到本地
            File file = new File(path);
            MatrixToImageWriter.writeToFile(bitMatrix, format, file);
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args)
    {
//        generatEncodeTest("D:\\","zxing.png","001","13019931996");
//        parseDecodeTest("D:\\zxing.png");

        QRCodeUtil zxing = new QRCodeUtil();

        // 传参：二维码内容和生成路径
        if (zxing.orCode("https://www.cnblogs.com/lsy131479/", "D:\\zxing.png")) {
            System.out.println("ok,成功");
        } else {
            System.out.println("no,失败");
        }

    }
}
