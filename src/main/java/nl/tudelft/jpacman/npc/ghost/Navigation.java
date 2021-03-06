package nl.tudelft.jpacman.npc.ghost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.parser.TemplateClass;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Navigation provides utility to nagivate on {@link Square}s.
 *
 * @author Jeroen Roosen 
 */
public final class Navigation {

    private Navigation() {
    }

    /**
     * Calculates the shortest path. This is done by BFS. This search ensures
     * the traveller is allowed to occupy the squares on the way, or returns the
     * shortest path to the square regardless of terrain if no traveller is
     * specified.
     *
     * @param from
     *            The starting square.
     * @param to
     *            The destination.
     * @param traveller
     *            The traveller attempting to reach the destination. If
     *            traveller is set to <code>null</code>, this method will ignore
     *            terrain and find the shortest path whether it can actually be
     *            reached or not.
     * @return The shortest path to the destination or <code>null</code> if no
     *         such path could be found. When the destination is the current
     *         square, an empty list is returned.
     */
    public @Nullable static List<Direction> shortestPath(Square from, Square to,
                                                         @Nullable Unit traveller) {
        TemplateClass.instrum(44, "IF Statement", "Navigation.shortestPath().from: ",from.toString(), "Navigation.shortestPath().to: ",to.toString());
		if (from.equals(to)) {
            return new ArrayList<>();
        }

        List<Node> targets = new ArrayList<>();
        Set<Square> visited = new HashSet<>();
        targets.add(new Node(null, from, null));
		TemplateClass.instrum(50, "Assign", "Navigation.shortestPath().targets: ",targets.toString(), "Navigation.shortestPath().from: ",from.toString());
        TemplateClass.instrum(51, "While Statement", "Navigation.shortestPath().targets: ",targets.toString());
		while (!targets.isEmpty()) {
            Node node = targets.remove(0);
            Square square = node.getSquare();
            TemplateClass.instrum(54, "IF Statement", "Navigation.shortestPath().square: ",square.toString(), "Navigation.shortestPath().to: ",to.toString());
			if (square.equals(to)) {
                return node.getPath();
            }
            visited.add(square);
			TemplateClass.instrum(57, "Assign", "Navigation.shortestPath().visited: ",visited.toString(), "Navigation.shortestPath().square: ",square.toString());
            addNewTargets(traveller, targets, visited, node, square);
			TemplateClass.instrum(58, "Assign", "Navigation.shortestPath().traveller: ",traveller.toString(), "Navigation.shortestPath().targets: ",targets.toString(), "Navigation.shortestPath().visited: ",visited.toString(), "Navigation.shortestPath().node: ",node.toString(), "Navigation.shortestPath().square: ",square.toString());
        }
        return null;
    }

    private static void addNewTargets(@Nullable Unit traveller, List<Node> targets,
                                      Set<Square> visited, Node node, Square square) {
        for (Direction direction : Direction.values()) {
            Square target = square.getSquareAt(direction);
            TemplateClass.instrum(67, "IF Statement", "Navigation.addNewTargets().visited: ",visited.toString(), "Navigation.addNewTargets().target: ",target.toString());
			if (!visited.contains(target)
                || (traveller != null && target.isAccessibleTo(traveller))) {
                targets.add(new Node(direction, target, node));
				TemplateClass.instrum(69, "Assign", "Navigation.addNewTargets().targets: ",targets.toString(), "Navigation.addNewTargets().direction: ",direction.toString(), "Navigation.addNewTargets().target: ",target.toString(), "Navigation.addNewTargets().node: ",node.toString());
            }
        }
    }

    /**
     * Finds the nearest unit of the given type and returns its location. This
     * method will perform a breadth first search starting from the given
     * square.
     *
     * @param type
     *            The type of unit to search for.
     * @param currentLocation
     *            The starting location for the search.
     * @return The nearest unit of the given type, or <code>null</code> if no
     *         such unit could be found.
     */
    public @Nullable static Unit findNearest(Class<? extends Unit> type,
                                             Square currentLocation) {
        List<Square> toDo = new ArrayList<>();
        Set<Square> visited = new HashSet<>();

        toDo.add(currentLocation);

        while (!toDo.isEmpty()) {
            Square square = toDo.remove(0);
            Unit unit = findUnit(type, square);
            if (unit != null) {
                assert unit.hasSquare();
                return unit;
            }
            visited.add(square);
            for (Direction direction : Direction.values()) {
                Square newTarget = square.getSquareAt(direction);
                if (!visited.contains(newTarget) && !toDo.contains(newTarget)) {
                    toDo.add(newTarget);
                }
            }
        }
        return null;
    }

    /**
     * Determines whether a square has an occupant of a certain type.
     *
     * @param type
     *            The type to search for.
     * @param square
     *            The square to search.
     * @return A unit of type T, iff such a unit occupies this square, or
     *         <code>null</code> of none does.
     */
    public @Nullable static Unit findUnit(Class<? extends Unit> type, Square square) {
        for (Unit unit : square.getOccupants()) {
            if (type.isInstance(unit)) {
                assert unit.hasSquare();
                return unit;
            }
        }
        return null;
    }

    /**
     * Helper class to keep track of the path.
     *
     * @author Jeroen Roosen
     */
    private static final class Node {

        /**
         * The direction for this node, which is <code>null</code> for the root
         * node.
         */
        private @Nullable final Direction direction;

        /**
         * The parent node, which is <code>null</code> for the root node.
         */
        private @Nullable final Node parent;

        /**
         * The square associated with this node.
         */
        private final Square square;

        /**
         * Creates a new node.
         *
         * @param direction
         *            The direction, which is <code>null</code> for the root
         *            node.
         * @param square
         *            The square.
         * @param parent
         *            The parent node, which is <code>null</code> for the root
         *            node.
         */
        Node(@Nullable Direction direction, Square square, @Nullable Node parent) {
            this.direction = direction;
			TemplateClass.instrum(167, "Assign", "Node.Node().direction: ",direction.toString(), "Node.Node().direction: ",direction.toString());
            this.square = square;
			TemplateClass.instrum(168, "Assign", "Node.Node().square: ",square.toString(), "Node.Node().square: ",square.toString());
            this.parent = parent;
			TemplateClass.instrum(169, "Assign", "Node.Node().parent: ",parent.toString(), "Node.Node().parent: ",parent.toString());
        }

        /**
         * @return The direction for this node, or <code>null</code> if this
         *         node is a root node.
         */
        private @Nullable Direction getDirection() {
            return direction;
        }

        /**
         * @return The square for this node.
         */
        private Square getSquare() {
            return square;
        }

        /**
         * @return The parent node, or <code>null</code> if this node is a root
         *         node.
         */
        private @Nullable Node getParent() {
            return parent;
        }

        /**
         * Returns the list of values from the root of the tree to this node.
         *
         * @return The list of values from the root of the tree to this node.
         */
        private List<Direction> getPath() {
            TemplateClass.instrum(201, "IF Statement", "Node.getPath().parent: ",parent.toString());
			if (parent != null) {
                return new ArrayList<>();
            }
            List<Direction> path = parent.getPath();
            path.add(getDirection());
			TemplateClass.instrum(205, "Assign", "Node.getPath().path: ",path.toString());
            return path;
        }
    }
}
