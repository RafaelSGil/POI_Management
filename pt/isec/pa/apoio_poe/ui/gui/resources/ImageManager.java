package pt.isec.pa.apoio_poe.ui.gui.resources;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;

/**
 * <p>Class that enables the usage of images in the GUI</p>
 *
 * @author RafelGil and HugoFerreira
 */
public class ImageManager {
    private ImageManager() { }
    private static final HashMap<String, Image> images = new HashMap<>();

    /**
     * <p>Reads an image through the path given and returns the an Image</p>
     * @param filename path to image
     * @return Image - image read
     */
    public static Image getImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try (InputStream is = ImageManager.class.getResourceAsStream("images/"+filename)) {
                image = new Image(is, 600, 440, true, false);
                images.put(filename,image);
            } catch (Exception e) { return null; }
        return image;
    }
    public static Image getExternalImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try {
                image = new Image(filename);
                images.put(filename,image);
            } catch (Exception e) { return null; }
        return image;
    }
    public static void purgeImage(String filename) { images.remove(filename); }
}
