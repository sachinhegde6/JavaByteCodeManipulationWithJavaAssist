package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.parser.TemplateClass;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A pellet, one of the little dots Pac-Man has to collect.
 *
 * @author Jeroen Roosen 
 */
public class Pellet extends Unit {

    /**
     * The sprite of this unit.
     */
    private final Sprite image;

    /**
     * The point value of this pellet.
     */
    private final int value;

    /**
     * Creates a new pellet.
     * @param points The point value of this pellet.
     * @param sprite The sprite of this pellet.
     */
    public Pellet(int points, Sprite sprite) {
        this.image = sprite;
		TemplateClass.instrum(29, "Assign", "Pellet.Pellet().image: ",image.toString(), "Pellet.Pellet().sprite: ",sprite.toString());
        this.value = points;
		TemplateClass.instrum(30, "Assign", "Pellet.Pellet().value: ",String.valueOf(value), "Pellet.Pellet().points: ",String.valueOf(points));
    }

    /**
     * Returns the point value of this pellet.
     * @return The point value of this pellet.
     */
    public int getValue() {
        return value;
    }

    @Override
    public Sprite getSprite() {
        return image;
    }
}
