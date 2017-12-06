package com.qm.omp.business.web.upload;

import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.util.Streams;

public class CropImgage
{
    private static int widht = 400;  
    private static int height = 400;  
    /** 
     * @param args 
     * @throws IOException  
     */  
    public static final Map<Key, Object> RENDERING_HINTS = new HashMap<Key, Object>();  
    public static void init(){  
        RENDERING_HINTS.put(RenderingHints.KEY_COLOR_RENDERING,  
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);  
        RENDERING_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION,  
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);  
        RENDERING_HINTS.put(RenderingHints.KEY_INTERPOLATION,  
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);  
        RENDERING_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);  
        RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
    }  
    public static void main(String[] args) throws IOException {  
        init();  
        byte[] imageBytes = null;  
        File file = new File("c:/a.jpg");  
        imageBytes = readStreamIntoMemory(new FileInputStream(file));  
        BufferedImage img = javax.imageio.ImageIO.read(new ByteArrayInputStream(imageBytes));  
        img = scaledImage(img, widht, height, true, RENDERING_HINTS);  
        FileOutputStream fos = new FileOutputStream("c:/a_1.jpg");  
        javax.imageio.ImageIO.write(img, "jpg", fos);
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);  
//        encoder.encode(img);  
//        fos.close();  
    }  
    public static byte[] readStreamIntoMemory(InputStream in) throws IOException {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        Streams.copy(in, baos, true);  
        return baos.toByteArray();  
    }  
    public static BufferedImage scaledImage(BufferedImage img, int targetWidth, int targetHeight, boolean higherQuality, Map renderingHints){  
        
        BufferedImage scaledImg = img.getSubimage(100, 100, 400, 400);
        return scaledImg;  
    }  
}  
