package com.github.bitfexl.splparser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public final class ImgUtils {
    private ImgUtils() { }

    /**
     * Convert image to base64 encoded png.
     * Prefix with "data:image/png;base64," for html image source.
     * @param image The image to convert.
     * @return A base64 encoded image.
     * @throws IOException Error encoding/writing image.
     */
    public static String toBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
