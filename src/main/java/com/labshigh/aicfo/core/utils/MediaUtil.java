package com.labshigh.aicfo.core.utils;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 미디어 유틸
 * 이미지 리사이즈 & 크롭
 */
@Component
public class MediaUtil {

    public static final String MEDIA_TYPE_LARGE = "L";
    public static final String MEDIA_TYPE_THUMBNAIL = "T";

    public static final String MEDIA_TYPE_PROFILE = "P";

    public static final String MEDIA_TYPE_COMMUNICATION = "C";

    private static final int LARGE_WIDTH = 800;
    private static final int LARGE_HEIGHT = 500;
    private static final int THUMBNAIL_WIDTH = 240;
    private static final int THUMBNAIL_HEIGHT = 150;

    private static final int PROFILE_SQUARE = 120;

    private static final int COMM_WIDTH = 255;
    private static final int COMM_HEIGHT = 158;

    private static final String EXT_JPG = "jpg";
    private static final String EXT_JPEG = "jpeg";
    private static final String EXT_PNG = "png";
    private static final String EXT_BMP = "bmp";
    private static final String EXT_GIF = "gif";
    private static final String EXT_HEIC = "HEIC";

    private static List<String> ImageTypes;

    static {
        ImageTypes = new ArrayList<>();
        ImageTypes.add(EXT_JPG);
        ImageTypes.add(EXT_JPEG);
        ImageTypes.add(EXT_PNG);
        ImageTypes.add(EXT_BMP);
        ImageTypes.add(EXT_HEIC);
//        ImageTypes.add(EXT_GIF);
    }

    /**
     * 미디어 수용 가능 여부
     * @param extension
     * @return
     */
    public static boolean acceptable(String extension) {
        return ImageTypes.contains(extension.toLowerCase());
    }

    /**
     * 사용자 프로필용 가공
     * @param original
     * @param type
     * @return
     * @throws Exception
     */
    public static File transformProfile(File original, String type) throws Exception {
        String originPath = original.getAbsolutePath();

        File profileFile = new File(originPath + "_" + MEDIA_TYPE_PROFILE);

        BufferedImage originImage = ImageIO.read(original);

        BufferedImage profileImage = resizeAndCropImage(originImage, PROFILE_SQUARE, PROFILE_SQUARE);

        ImageIO.write(profileImage, type, profileFile);

        return profileFile;
    }



    /**
     * 리사이즈 및 크롭 처리
     * @param originImage
     * @param width
     * @param height
     * @return
     */
    private static BufferedImage resizeAndCropImage( BufferedImage originImage, int width, int height ) {
        int originWidth = originImage.getWidth();
        int originHeight = originImage.getHeight();

        if( originWidth == 800 && originHeight == 600 ){
            return originImage;
        }

        Scalr.Mode mode = (double) width / (double) height >= (double) originWidth / (double) originHeight ? Scalr.Mode.FIT_TO_WIDTH
                : Scalr.Mode.FIT_TO_HEIGHT;

        BufferedImage resizedImage = Scalr.resize(originImage, Scalr.Method.ULTRA_QUALITY, mode, width, height);

        int x = 0;
        int y = 0;

        if (mode == Scalr.Mode.FIT_TO_WIDTH) {
            y = (resizedImage.getHeight() - height) / 2;
        } else if (mode == Scalr.Mode.FIT_TO_HEIGHT) {
            x = (resizedImage.getWidth() - width) / 2;
        }
//        if( originHeight > originWidth) {
//            resizedImage = rotateImage( resizedImage , 90);
//        }

        return Scalr.crop(resizedImage, x, y, width, height);
    }

    public static BufferedImage rotateImage(BufferedImage src, int rotationAngle) {
        double theta = (Math.PI * 2) / 360 * rotationAngle;
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest;
        if (rotationAngle == 90 || rotationAngle == 270) {
            dest = new BufferedImage(src.getHeight(), src.getWidth(), src.getType());
        } else {
            dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        }

        Graphics2D graphics2D = dest.createGraphics();

        if (rotationAngle == 90) {
            graphics2D.translate((height - width) / 2, (height - width) / 2);
            graphics2D.rotate(theta, height / 2, width / 2);
        } else if (rotationAngle == 270) {
            graphics2D.translate((width - height) / 2, (width - height) / 2);
            graphics2D.rotate(theta, height / 2, width / 2);
        } else {
            graphics2D.translate(0, 0);
            graphics2D.rotate(theta, width / 2, height / 2);
        }
        graphics2D.drawRenderedImage(src, null);
        return dest;
    }

}
