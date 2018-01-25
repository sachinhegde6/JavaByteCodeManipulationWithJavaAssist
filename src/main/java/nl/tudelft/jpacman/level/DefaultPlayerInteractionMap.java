package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.parser.TemplateClass;

/**
 * An extensible default interaction map for collisions caused by the player.
 *
 * The implementation makes use of the interactionmap, and as such can be easily
 * and declaratively extended when new types of units (ghosts, players, ...) are
 * added.
 *
 * @author Arie van Deursen
 * @author Jeroen Roosen
 *
 */
public class DefaultPlayerInteractionMap implements CollisionMap {

    private final CollisionMap collisions = defaultCollisions();

    @Override
    public void collide(Unit mover, Unit movedInto) {
        collisions.collide(mover, movedInto);
		TemplateClass.instrum(23, "Assign", "DefaultPlayerInteractionMap.collide().collisions: ",collisions.toString(), "DefaultPlayerInteractionMap.collide().mover: ",mover.toString(), "DefaultPlayerInteractionMap.collide().movedInto: ",movedInto.toString());
    }

    /**
     * Creates the default collisions Player-Ghost and Player-Pellet.
     *
     * @return The collision map containing collisions for Player-Ghost and
     *         Player-Pellet.
     */
    private static CollisionInteractionMap defaultCollisions() {
        CollisionInteractionMap collisionMap = new CollisionInteractionMap();

        collisionMap.onCollision(Player.class, Ghost.class,
            (player, ghost) -> player.setAlive(false));
		TemplateClass.instrum(35, "Assign", "DefaultPlayerInteractionMap.defaultCollisions().collisionMap: ",collisionMap.toString());

        collisionMap.onCollision(Player.class, Pellet.class,
            (player, pellet) -> {
                pellet.leaveSquare();
				TemplateClass.instrum(40, "Assign", "DefaultPlayerInteractionMap.defaultCollisions().pellet: ",pellet.toString());
                player.addPoints(pellet.getValue());
				TemplateClass.instrum(41, "Assign", "DefaultPlayerInteractionMap.defaultCollisions().player: ",player.toString(), "DefaultPlayerInteractionMap.defaultCollisions().pellet: ",pellet.toString());
            });
		TemplateClass.instrum(38, "Assign", "DefaultPlayerInteractionMap.defaultCollisions().collisionMap: ",collisionMap.toString());
        return collisionMap;
    }
}
