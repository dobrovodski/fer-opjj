package hr.fer.zemris.java.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * This class models a single image. The class can be used to store image data as well as metadata. Image data is stored
 * in a base64 string. The metadata is as follows: id (i.e. name of the image on the disk), a list of tags, a
 * description of the image. The image doesn't necessarily need to have data associated with it -- it can just be used
 * as a class for storing metadata, i.e. the data can be null. In that case, once a user wants to turn the metadata
 * class into a full Image class, they can use the method imageFrom.
 *
 * @author matej
 */
public class Image {
    /**
     * Image data in base64.
     */
    private String base64 = null;
    /**
     * List of tags.
     */
    private List<String> tags;
    /**
     * Id of image.
     */
    private String id;
    /**
     * Description of image.
     */
    private String description;

    /**
     * Constructor
     *
     * @param image buffered image from which to extract base64 information. Can be null.
     * @param id id of image
     * @param tags image tags
     * @param description description of image
     *
     * @throws IOException if image has been provided and an IOException happened while converting to base64
     */
    public Image(BufferedImage image, String id, List<String> tags, String description) throws IOException {
        if (image != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);
            this.base64 = Base64.getEncoder().encodeToString(os.toByteArray());
        }

        this.id = id;
        this.tags = tags;
        this.description = description;
    }

    /**
     * Returns a new image based on the given image metadata class and {@link BufferedImage} data.
     *
     * @param img data of image, actual image
     * @param base metadata class whose id, tags and description the new Image will inherit
     *
     * @return newly created Image class
     *
     * @throws IOException if an IOException happened while converting to base64
     */
    public static Image imageFrom(BufferedImage img, Image base) throws IOException {
        return new Image(img, base.id, base.tags, base.description);
    }

    /**
     * Getter for image source in base64.
     *
     * @return base64 of image, or null if empty
     */
    public String getBase64() {
        return base64;
    }

    /**
     * Returns image tags.
     *
     * @return image tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Returns image description.
     *
     * @return image description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns image id.
     *
     * @return image id
     */
    public String getId() {
        return id;
    }
}
