package nl.tudelft.jpacman.level;

import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.parser.TemplateClass;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Factory that creates levels and units.
 *
 * @author Jeroen Roosen 
 */
public class LevelFactory {

    private static final int GHOSTS = 4;
    private static final int BLINKY = 0;
    private static final int INKY = 1;
    private static final int PINKY = 2;
    private static final int CLYDE = 3;

    /**
     * The default value of a pellet.
     */
    private static final int PELLET_VALUE = 10;

    /**
     * The sprite store that provides sprites for units.
     */
    private final PacManSprites sprites;

    /**
     * Used to cycle through the various ghost types.
     */
    private int ghostIndex;

    /**
     * The factory providing ghosts.
     */
    private final GhostFactory ghostFact;

    /**
     * Creates a new level factory.
     *
     * @param spriteStore
     *            The sprite store providing the sprites for units.
     * @param ghostFactory
     *            The factory providing ghosts.
     */
    public LevelFactory(PacManSprites spriteStore, GhostFactory ghostFactory) {
        this.sprites = spriteStore;
		TemplateClass.instrum(59, "Assign", "LevelFactory.LevelFactory().sprites: ",sprites.toString(), "LevelFactory.LevelFactory().spriteStore: ",spriteStore.toString());
        this.ghostIndex = -1;
		TemplateClass.instrum(60, "Assign", "LevelFactory.LevelFactory().ghostIndex: ", String.valueOf(ghostIndex));
        this.ghostFact = ghostFactory;
		TemplateClass.instrum(61, "Assign", "LevelFactory.LevelFactory().ghostFact: ",ghostFact.toString(), "LevelFactory.LevelFactory().ghostFactory: ",ghostFactory.toString());
    }

    /**
     * Creates a new level from the provided data.
     *
     * @param board
     *            The board with all ghosts and pellets occupying their squares.
     * @param ghosts
     *            A list of all ghosts on the board.
     * @param startPositions
     *            A list of squares from which players may start the game.
     * @return A new level for the board.
     */
    public Level createLevel(Board board, List<NPC> ghosts,
                             List<Square> startPositions) {

        // We'll adopt the simple collision map for now.
        CollisionMap collisionMap = new PlayerCollisions();

        return new Level(board, ghosts, startPositions, collisionMap);
    }

    /**
     * Creates a new ghost.
     *
     * @return The new ghost.
     */
    NPC createGhost() {
        ghostIndex++;
		TemplateClass.instrum(90, "Assign", "LevelFactory.createGhost().ghostIndex: ", String.valueOf(ghostIndex));
        ghostIndex %= GHOSTS;
		TemplateClass.instrum(91, "Assign", "LevelFactory.createGhost().ghostIndex: ",String.valueOf(ghostIndex), "LevelFactory.createGhost().GHOSTS: ", String.valueOf(GHOSTS));
        TemplateClass.instrum(92, "Switch Statement", "LevelFactory.createGhost().ghostIndex: ",String.valueOf(ghostIndex));
		switch (ghostIndex) {
            case BLINKY:
                return ghostFact.createBlinky();
            case INKY:
                return ghostFact.createInky();
            case PINKY:
                return ghostFact.createPinky();
            case CLYDE:
                return ghostFact.createClyde();
            default:
                return new RandomGhost(sprites.getGhostSprite(GhostColor.RED));
        }
    }

    /**
     * Creates a new pellet.
     *
     * @return The new pellet.
     */
    public Pellet createPellet() {
        return new Pellet(PELLET_VALUE, sprites.getPelletSprite());
    }

    /**
     * Implementation of an NPC that wanders around randomly.
     *
     * @author Jeroen Roosen
     */
    private static final class RandomGhost extends Ghost {

        /**
         * The suggested delay between moves.
         */
        private static final long DELAY = 175L;

        /**
         * Creates a new random ghost.
         *
         * @param ghostSprite
         *            The sprite for the ghost.
         */
        RandomGhost(Map<Direction, Sprite> ghostSprite) {
            super(ghostSprite, (int) DELAY, 0);
        }

        @Override
        public @Nullable Direction nextMove() {
            return randomMove();
        }
    }
}
