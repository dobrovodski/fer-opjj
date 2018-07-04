package hr.fer.zemris.java.rest;

import com.google.gson.Gson;
import hr.fer.zemris.java.image.Image;
import hr.fer.zemris.java.image.ImageDB;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * REST API for getting tags, thumbnails and images.
 *
 * @author matej
 */
@Path("/images")
public class ImageJSON {
    /**
     * Mapped to http://localhost:8080/galerija/rest/images. Returns set of tags.
     *
     * @return Response with set of tags
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTags() {
        Gson gson = new Gson();
        return Response.status(Response.Status.OK).entity(gson.toJson(ImageDB.getTagSet())).build();
    }


    /**
     * /** Mapped to http://localhost:8080/galerija/rest/images/img. Returns the image with the given id in the query
     * parameter or 404 if image not found.
     *
     * @param id id of image to get
     *
     * @return Response with image in database with given id or 404
     *
     * @throws IOException if couldn't read image
     */
    @Path("img")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImage(@QueryParam("id") String id) throws IOException {
        Gson gson = new Gson();
        Image image = ImageDB.getImage(id);

        if (image == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(gson.toJson(image)).build();
    }

    /**
     * Mapped to http://localhost:8080/galerija/rest/images/thumbnail. Returns the image with the given tag in the query
     * parameter or empty list if no images with given tag are found.
     *
     * @param tag tag to filter by
     *
     * @return Response with list of images filtered by tag
     *
     * @throws IOException if couldn't read images
     */
    @Path("/thumbnail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getThumbnail(@QueryParam("tag") String tag) throws IOException {
        Gson gson = new Gson();
        List<Image> images = ImageDB.getFilteredThumbnails(tag);
        return Response.status(Response.Status.OK).entity(gson.toJson(images)).build();
    }
}
