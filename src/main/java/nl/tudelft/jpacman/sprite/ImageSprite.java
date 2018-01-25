package nl.tudelft.jpacman.sprite;

import nl.tudelft.jpacman.parser.TemplateClass;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

/**
 * Basic implementation of a Sprite, it merely consists of a static image.
 *
 * @author Jeroen Roosen 
 */
public class ImageSprite implements Sprite {

    /**
     * Internal image.
     */
    private final Image image;

    /**
     * Creates a new sprite from an image.
     *
     * @param img
     *            The image to create a sprite from.
     */
    public ImageSprite(Image img) {
        this.image = img;
		TemplateClass.instrum(29, "Assign", "ImageSprite.ImageSprite().image: ",image.toString(), "ImageSprite.ImageSprite().img: ",img.toString());
    }

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height) {
        graphics.drawImage(image, x, y, x + width, y + height, 0, 0,
            image.getWidth(null), image.getHeight(null), null);
		TemplateClass.instrum(34, "Assign", "ImageSprite.draw().graphics: ",graphics.toString(), "ImageSprite.draw().image: ",image.toString(), "ImageSprite.draw().x: ",String.valueOf(x), "ImageSprite.draw().y: ",String.valueOf(y), "ImageSprite.draw().x: ",String.valueOf(x), "ImageSprite.draw().width: ",String.valueOf(width), "ImageSprite.draw().y: ", String.valueOf(y), "ImageSprite.draw().height: ",String.valueOf(height));
    }

    @Override
    public Sprite split(int x, int y, int width, int height) {
        TemplateClass.instrum(40, "IF Statement", "ImageSprite.split().x: ",String.valueOf(x), "ImageSprite.split().y: ",String.valueOf(y), "ImageSprite.split().x: ",String.valueOf(x), "ImageSprite.split().width: ",String.valueOf(width), "ImageSprite.split().y: ",String.valueOf(y), "ImageSprite.split().height: ",String.valueOf(height));
		if (withinImage(x, y) || withinImage(x + width - 1, y + height - 1)) {
            BufferedImage newImage = newImage(width, height);
            newImage.createGraphics().drawImage(image, 0, 0, width, height, x,
                y, x + width, y + height, null);
			TemplateClass.instrum(42, "Assign", "ImageSprite.split().newImage: ",newImage.toString(), "ImageSprite.split().image: ",image.toString(), "ImageSprite.split().width: ",String.valueOf(width), "ImageSprite.split().height: ",String.valueOf(height), "ImageSprite.split().x: ",String.valueOf(x));
            return new ImageSprite(newImage);
        }
        return new EmptySprite();
    }

    private boolean withinImage(int x, int y) {
        return x > image.getWidth(null) || x <= 0 || y > image.getHeight(null)
            || y <= 0;
    }

    /**
     * Creates a new, empty image of the given width and height. Its
     * transparency will be a bitmask, so no try ARGB image.
     *
     * @param width
     *            The width of the new image.
     * @param height
     *            The height of the new image.
     * @return The new, empty image.
     */
    private BufferedImage newImage(int width, int height) {
        GraphicsConfiguration gc = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getDefaultScreenDevice()
            .getDefaultConfiguration();
        return gc.createCompatibleImage(width, height, Transparency.BITMASK);
    }

    @Override
    public int getWidth() {
        return image.getWidth(null);
    }

    @Override
    public int getHeight() {
        return image.getHeight(null);
    }

}
