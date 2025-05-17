package service;

import dao.PictureDAO;
import model.Picture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class PictureService {

    /**
     * Save new image for the given owner
     * @param image   the image to store
     * @param ownerId the ID of the user who owns this image
     * @return the new img_id, or throws on failure
     */
    public int addPicture(BufferedImage image, int ownerId) {
        Objects.requireNonNull(image, "image must not be null");
        try {
            int imgId = PictureDAO.addNewImg(image, ownerId);
            if (imgId < 0) {
                throw new PictureServiceException("Failed to save image (DAO returned " + imgId + ")");
            }
            return imgId;
        } catch (SQLException | IOException e) {
            throw new PictureServiceException("Error adding new image for owner " + ownerId, e);
        }
    }

    /**
     * Retrieves the Picture object for the given imgId
     * @param imgId the ID of the image
     * @return the Picture, or throws if not found or on I/O/SQL errors
     */
    public Picture getPicture(int imgId) {
        try {
            Picture pic = PictureDAO.getImgObj(imgId);
            if (pic == null) {
                throw new PictureServiceException("No image found with id " + imgId);
            }
            return pic;
        } catch (SQLException | IOException e) {
            throw new PictureServiceException("Error fetching image with id " + imgId, e);
        }
    }

    /** Unchecked exception for service‐layer errors */
    public static class PictureServiceException extends RuntimeException {
        public PictureServiceException(String msg) {
            super(msg);
        }
        public PictureServiceException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
