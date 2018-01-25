package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.parser.TemplateClass;

/**
 * A simple implementation of a collision map for the JPacman player.
 * <p>
 * It uses a number of instanceof checks to implement the multiple dispatch for the 
 * collisionmap. For more realistic collision maps, this approach will not scale,
 * and the recommended approach is to use a {@link CollisionInteractionMap}.
 *
 * @author Arie van Deursen, 2014
 *
 */

public class PlayerCollisions implements CollisionMap {

    @Override
    public void collide(Unit mover, Unit collidedOn) {
        TemplateClass.instrum(21, "IF Statement", "PlayerCollisions.collide().mover: ",mover.toString());
		if (mover instanceof Player) {
            playerColliding((Player) mover, collidedOn);
			TemplateClass.instrum(22, "Assign",  "PlayerCollisions.collide().mover: ",mover.toString(), "PlayerCollisions.collide().collidedOn: ",collidedOn.toString());
        }
        TemplateClass.instrum(24, "IF Statement", "PlayerCollisions.collide().mover: ",mover.toString());
		if (mover instanceof Ghost) {
            ghostColliding((Ghost) mover, collidedOn);
			TemplateClass.instrum(25, "Assign",  "PlayerCollisions.collide().mover: ",mover.toString(), "PlayerCollisions.collide().collidedOn: ",collidedOn.toString());
        }
        TemplateClass.instrum(27, "IF Statement", "PlayerCollisions.collide().mover: ",mover.toString());
		if (mover instanceof Pellet) {
            pelletColliding((Pellet) mover, collidedOn);
			TemplateClass.instrum(28, "Assign",  "PlayerCollisions.collide().mover: ",mover.toString(), "PlayerCollisions.collide().collidedOn: ",collidedOn.toString());
        }
    }

    private void playerColliding(Player player, Unit collidedOn) {
        TemplateClass.instrum(33, "IF Statement", "PlayerCollisions.playerColliding().collidedOn: ",collidedOn.toString());
		if (collidedOn instanceof Ghost) {
            playerVersusGhost(player, (Ghost) collidedOn);
			TemplateClass.instrum(34, "Assign", "PlayerCollisions.playerColliding().player: ",player.toString(), "PlayerCollisions.playerColliding().collidedOn: ",collidedOn.toString());
        }
        TemplateClass.instrum(36, "IF Statement", "PlayerCollisions.playerColliding().collidedOn: ",collidedOn.toString());
		if (collidedOn instanceof Pellet) {
            playerVersusPellet(player, (Pellet) collidedOn);
			TemplateClass.instrum(37, "Assign", "PlayerCollisions.playerColliding().player: ",player.toString(), "PlayerCollisions.playerColliding().collidedOn: ",collidedOn.toString());
        }
    }

    private void ghostColliding(Ghost ghost, Unit collidedOn) {
        TemplateClass.instrum(42, "IF Statement", "PlayerCollisions.ghostColliding().collidedOn: ",collidedOn.toString());
		if (collidedOn instanceof Player) {
            playerVersusGhost((Player) collidedOn, ghost);
			TemplateClass.instrum(43, "Assign", "PlayerCollisions.ghostColliding().collidedOn: ",collidedOn.toString(), "PlayerCollisions.ghostColliding().ghost: ",ghost.toString());
        }
    }

    private void pelletColliding(Pellet pellet, Unit collidedOn) {
        TemplateClass.instrum(48, "IF Statement", "PlayerCollisions.pelletColliding().collidedOn: ",collidedOn.toString());
		if (collidedOn instanceof Player) {
            playerVersusPellet((Player) collidedOn, pellet);
			TemplateClass.instrum(49, "Assign",  "PlayerCollisions.pelletColliding().collidedOn: ",collidedOn.toString(), "PlayerCollisions.pelletColliding().pellet: ",pellet.toString());
        }
    }


    /**
     * Actual case of player bumping into ghost or vice versa.
     *
     * @param player The player involved in the collision.
     * @param ghost The ghost involved in the collision.
     */
    public void playerVersusGhost(Player player, Ghost ghost)
    {
        player.setAlive(false);
		TemplateClass.instrum(62, "Assign", "PlayerCollisions.playerVersusGhost().player: ",player.toString());
    }

    /**
     * Actual case of player consuming a pellet.
     *
     * @param player The player involved in the collision.
     * @param pellet The pellet involved in the collision.
     */
    public void playerVersusPellet(Player player, Pellet pellet) {
        pellet.leaveSquare();
		TemplateClass.instrum(72, "Assign", "PlayerCollisions.playerVersusPellet().pellet: ",pellet.toString());
        player.addPoints(pellet.getValue());
		TemplateClass.instrum(73, "Assign", "PlayerCollisions.playerVersusPellet().player: ",player.toString(), "PlayerCollisions.playerVersusPellet().pellet: ",pellet.toString());
    }

}
