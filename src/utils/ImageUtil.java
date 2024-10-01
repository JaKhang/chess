package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
        public static Image loadingImage(String fileName, double zoom) {
            String link = "src/data/" + fileName;
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(link));
            } catch (IOException e) {
                e.printStackTrace();
            }
            int width = (int) (image.getWidth() * zoom);
            int height = (int) (image.getHeight() * zoom);
            return image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        }

        public static Image rotate(Image image, float rotation, int width, int height) {

        BufferedImage imageDraw = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        imageDraw.getGraphics().drawImage(image, 0, 0, null);

        AffineTransform tx = new AffineTransform();
        tx.rotate(rotation, (float)width / 2, (float)height / 2);

        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        imageDraw = op.filter(imageDraw, null);
        return imageDraw;
    }
}
