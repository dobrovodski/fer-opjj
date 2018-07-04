package hr.fer.zemris.java.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class acts as a database of images. On initialization, it creates a map of metadata image classes which have the
 * image metadata in them without the source (so only id, tags, description). It also provides methods for getting
 * images and image thumbnails using their ids. Those are fetched only once needed and are not kept in memory because
 * that'd be too expensive.
 *
 * @author matej
 */
public class ImageDB {
    /**
     * Path to the WEB-INF directory. Has to be set by setWebinfPath after which the class initializes itself.
     */
    private static Path webinfPath;
    /**
     * Path to descriptor.
     */
    private static Path descriptorPath;
    /**
     * Path to thumbnail folder.
     */
    private static Path thumbnailPath;
    /**
     * Image metadata - ids, tags, descriptions (no actual base64 data).
     */
    private static Map<String, Image> images = new HashMap<>();
    /**
     * Set of all possible tags.
     */
    private static Set<String> tags = new TreeSet<>();
    /**
     * Resolution for thumbnails.
     */
    private static int THUMB_RESOLUTION = 150;

    /**
     * Called when the webinf path is set. Loads all tags from descriptor file and creates metadata map.
     *
     * @throws IOException if cannot read descriptor file
     */
    private static void initialize() throws IOException {
        List<String> lines = Files.readAllLines(descriptorPath, StandardCharsets.UTF_8);

        for (int i = 0; i < lines.size() / 3; i++) {
            String id = lines.get(i * 3);
            String description = lines.get(i * 3 + 1);
            String[] t = lines.get(i * 3 + 2).trim().split("\\s*,\\s*");

            tags.addAll(Arrays.asList(t));
            images.put(id, new Image(null, id, Arrays.asList(t), description));
        }
    }

    /**
     * Creates new {@link Image} from base metadata img using its ID. Fills newly created image with base64 data of
     * image.
     *
     * @param img metadata image object
     *
     * @return newly created image with base64 data
     *
     * @throws IOException if cannot read image
     */
    private static Image loadImage(Image img) throws IOException {
        return Image.imageFrom(ImageIO.read(webinfPath.resolve(img.getId()).toFile()), img);
    }

    /**
     * Creates new thumbnail {@link Image} of square resolution given by THUMB_RESOLUTION. If thumbnail doesn't exist on
     * disk, creates it (same for the thumbnails folder)
     *
     * @param img metadata image object
     *
     * @return newly created thumbnail image with base64 data
     *
     * @throws IOException if cannot read image
     */
    private static Image loadThumbnail(Image img) throws IOException {
        if (!Files.exists(thumbnailPath)) {
            Files.createDirectory(thumbnailPath);
        }

        Path thumbnail = thumbnailPath.resolve(img.getId());

        // No need to create thumbnail if it exists
        if (Files.isRegularFile(thumbnail)) {
            return Image.imageFrom(ImageIO.read(thumbnail.toFile()), img);
        } else {
            BufferedImage resized = createThumbnail(img, thumbnail);
            return Image.imageFrom(resized, img);
        }
    }

    private static BufferedImage createThumbnail(Image img, Path thumbnail) throws IOException {
        Path imagePath = webinfPath.resolve(img.getId());
        BufferedImage fullSized = ImageIO.read(imagePath.toFile());
        BufferedImage resized = new BufferedImage(THUMB_RESOLUTION, THUMB_RESOLUTION, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(fullSized, 0, 0, THUMB_RESOLUTION, THUMB_RESOLUTION, null);
        g.dispose();

        ImageIO.write(resized, "jpg", thumbnail.toFile());
        return resized;
    }

    /**
     * Returns set of tags.
     *
     * @return set of tags
     */
    public static Set<String> getTagSet() {
        return tags;
    }

    /**
     * Returns image with given id
     *
     * @param id id of image
     *
     * @return image with id
     *
     * @throws IOException if could not load image
     */
    public static Image getImage(String id) throws IOException {
        Image img = images.get(id);
        if (img == null) {
            return null;
        }

        return loadImage(img);
    }

    /**
     * Returns list of thumbnail images filtered by given tag
     *
     * @param tag tag to filter by
     *
     * @return list of thumbnail images
     *
     * @throws IOException if could not load images
     */
    public static List<Image> getFilteredThumbnails(String tag) throws IOException {
        List<Image> list = new ArrayList<>();

        for (String id : images.keySet()) {
            Image img = images.get(id);
            if (!img.getTags().contains(tag)) {
                continue;
            }

            list.add(loadThumbnail(img));
        }

        return list;
    }

    /**
     * Sets the path to WEB-INF folder. This is to be called during initialization. Kick-starts the ImageDB
     * initialization as well (sets other Paths relative to WEB-INF)
     *
     * @param webinfPath path to WEB-INF
     *
     * @throws IOException if could not resolve other paths relative to WEB-INF
     */
    public static void setWebinfPath(Path webinfPath) throws IOException {
        ImageDB.webinfPath = webinfPath;
        descriptorPath = webinfPath.resolve("opisnik.txt");
        thumbnailPath = webinfPath.resolve("thumbnails");
        ImageDB.initialize();
    }

}
