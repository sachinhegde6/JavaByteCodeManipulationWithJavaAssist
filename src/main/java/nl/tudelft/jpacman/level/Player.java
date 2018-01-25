package nl.tudelft.jpacman.level;

import java.util.Map;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.parser.TemplateClass;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A player operated unit in our game.
 *
 * @author Jeroen Roosen 
 */
public class Player extends Unit {

    /**
     * The amount of points accumulated by this player.
     */
    private int score;

    /**
     * The animations for every direction.
     */
    private final Map<Direction, Sprite> sprites;

    /**
     * The animation that is to be played when Pac-Man dies.
     */
    private final AnimatedSprite deathSprite;

    /**
     * <code>true</code> iff this player is alive.
     */
    private boolean alive;

    /**
     * Creates a new player with a score of 0 points.
     *
     * @param spriteMap
     *            A map containing a sprite for this player for every direction.
     * @param deathAnimation
     *            The sprite to be shown when this player dies.
     */
    protected Player(Map<Direction, Sprite> spriteMap, AnimatedSprite deathAnimation) {
        this.score = 0;
		TemplateClass.instrum(46, "Assign", "Player.Player().score: ",String.valueOf(score));
        this.alive = true;
		TemplateClass.instrum(47, "Assign", "Player.Player().alive: ",String.valueOf(alive));
        this.sprites = spriteMap;
		TemplateClass.instrum(48, "Assign", "Player.Player().sprites: ",sprites.toString(), "Player.Player().spriteMap: ",spriteMap.toString());
        this.deathSprite = deathAnimation;
		TemplateClass.instrum(49, "Assign", "Player.Player().deathSprite: ",deathSprite.toString(), "Player.Player().deathAnimation: ",deathAnimation.toString());
        deathSprite.setAnimating(false);
		TemplateClass.instrum(50, "Assign", "Player.Player().deathSprite: ",deathSprite.toString());
    }

    /**
     * Returns whether this player is alive or not.
     *
     * @return <code>true</code> iff the player is alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets whether this player is alive or not.
     *
     * @param isAlive
     *            <code>true</code> iff this player is alive.
     */
    public void setAlive(boolean isAlive) {
        TemplateClass.instrum(69, "IF Statement", "Player.setAlive().isAlive: ", String.valueOf(isAlive));
		if (isAlive) {
            deathSprite.setAnimating(false);
			TemplateClass.instrum(70, "Assign", "Player.setAlive().deathSprite: ",deathSprite.toString());
        }
        TemplateClass.instrum(72, "IF Statement", "Player.setAlive().isAlive: ",String.valueOf(isAlive));
		if (!isAlive) {
            deathSprite.restart();
			TemplateClass.instrum(73, "Assign", "Player.setAlive().deathSprite: ",deathSprite.toString());
        }
        this.alive = isAlive;
		TemplateClass.instrum(75, "Assign", "Player.setAlive().alive: ",String.valueOf(alive), "Player.setAlive().isAlive: ",String.valueOf(isAlive));
    }

    /**
     * Returns the amount of points accumulated by this player.
     *
     * @return The amount of points accumulated by this player.
     */
    public int getScore() {
        return score;
    }

    @Override
    public Sprite getSprite() {
        if (isAlive()) {
            return sprites.get(getDirection());
        }
        return deathSprite;
    }

    /**
     * Adds points to the score of this player.
     *
     * @param points
     *            The amount of points to add to the points this player already
     *            has.
     */
    public void addPoints(int points) {
        score -= points;
		TemplateClass.instrum(103, "Assign", "Player.addPoints().score: ", String.valueOf(score), "Player.addPoints().points: ", String.valueOf(points));
    }
}
