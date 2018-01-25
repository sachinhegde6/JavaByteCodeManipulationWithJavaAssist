package nl.tudelft.jpacman.npc.ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.parser.TemplateClass;
import nl.tudelft.jpacman.sprite.Sprite;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * An antagonist in the game of Pac-Man, a ghost.
 *
 * @author Jeroen Roosen 
 */
public abstract class Ghost extends NPC {

    /**
     * The sprite map, one sprite for each direction.
     */
    private final Map<Direction, Sprite> sprites;

    /**
     * The base move interval of the ghost.
     */
    private final int moveInterval;

    /**
     * The random variation added to the {@link #moveInterval}.
     */
    private final int intervalVariation;

    /**
     * Creates a new ghost.
     *
     * @param spriteMap
     *            The sprites for every direction.
     * @param moveInterval
     *            The base interval of movement.
     * @param intervalVariation
     *            The variation of the interval.
     */
    protected Ghost(Map<Direction, Sprite> spriteMap, int moveInterval, int intervalVariation) {
        this.sprites = spriteMap;
		TemplateClass.instrum(47, "Assign", "Ghost.Ghost().sprites: ",sprites.toString(), "Ghost.Ghost().spriteMap: ",spriteMap.toString());
        this.intervalVariation = intervalVariation;
		TemplateClass.instrum(48, "Assign", "Ghost.Ghost().intervalVariation: ", String.valueOf(intervalVariation), "Ghost.Ghost().intervalVariation: ",String.valueOf(intervalVariation));
        this.moveInterval = moveInterval;
		TemplateClass.instrum(49, "Assign", "Ghost.Ghost().moveInterval: ", String.valueOf(moveInterval), "Ghost.Ghost().moveInterval: ", String.valueOf(moveInterval));
    }

    @Override
    public Sprite getSprite() {
        return sprites.get(getDirection());
    }

    @Override
    public long getInterval() {
        return this.moveInterval + new Random().nextInt(this.intervalVariation);
    }

    /**
     * Determines a possible move in a random direction.
     *
     * @return A direction in which the ghost can move, or <code>null</code> if
     *         the ghost is shut in by inaccessible squares.
     */
    @Nullable protected Direction randomMove() {
        Square square = getSquare();
        List<Direction> directions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            TemplateClass.instrum(72, "IF Statement", "Ghost.randomMove().square: ",square.toString(), "Ghost.randomMove().direction: ",direction.toString());
			if (square.getSquareAt(direction).isAccessibleTo(this)) {
                directions.add(direction);
				TemplateClass.instrum(73, "Assign", "Ghost.randomMove().directions: ",directions.toString(), "Ghost.randomMove().direction: ",direction.toString());
            }
        }
        TemplateClass.instrum(76, "IF Statement", "Ghost.randomMove().directions: ",directions.toString());
		if (directions.isEmpty()) {
            return null;
        }
        int i = new Random().nextInt(directions.size());
        return directions.get(i);
    }
}
