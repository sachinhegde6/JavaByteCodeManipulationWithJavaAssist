package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.parser.TemplateClass;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.KeyFor;

/**
 * A map of possible collisions and their handlers.
 *
 * @author Michael de Jong
 * @author Jeroen Roosen 
 */
public class CollisionInteractionMap implements CollisionMap {

    /**
     * The collection of collision handlers.
     */
    private final Map<Class<? extends Unit>,
        Map<Class<? extends Unit>, CollisionHandler<?, ?>>> handlers;

    /**
     * Creates a new, empty collision map.
     */
    public CollisionInteractionMap() {
        this.handlers = new HashMap<>();
		TemplateClass.instrum(30, "Assign", "CollisionInteractionMap.CollisionInteractionMap().handlers: ",handlers.toString());
    }

    /**
     * Adds a two-way collision interaction to this collection, i.e. the
     * collision handler will be used for both C1 versus C2 and C2 versus C1.
     *
     * @param <C1>
     *            The collider type.
     * @param <C2>
     *            The collidee (unit that was moved into) type.
     *
     * @param collider
     *            The collider type.
     * @param collidee
     *            The collidee type.
     * @param handler
     *            The handler that handles the collision.
     */
    public <C1 extends Unit, C2 extends Unit> void onCollision(
        Class<C1> collider, Class<C2> collidee, CollisionHandler<C1, C2> handler) {
        onCollision(collider, collidee, true, handler);
		TemplateClass.instrum(51, "Assign", "CollisionInteractionMap.onCollision().collider: ",collider.toString(), "CollisionInteractionMap.onCollision().collidee: ",collidee.toString(), "CollisionInteractionMap.onCollision().handler: ",handler.toString());
    }

    /**
     * Adds a collision interaction to this collection.
     *
     * @param <C1>
     *            The collider type.
     * @param <C2>
     *            The collidee (unit that was moved into) type.
     *
     * @param collider
     *            The collider type.
     * @param collidee
     *            The collidee type.
     * @param symetric
     *            <code>true</code> if this collision is used for both
     *            C1 against C2 and vice versa;
     *            <code>false</code> if only for C1 against C2.
     * @param handler
     *            The handler that handles the collision.
     */
    public <C1 extends Unit, C2 extends Unit> void onCollision(
        Class<C1> collider, Class<C2> collidee, boolean symetric,
        CollisionHandler<C1, C2> handler) {
        addHandler(collider, collidee, handler);
		TemplateClass.instrum(76, "Assign", "CollisionInteractionMap.onCollision().collider: ",collider.toString(), "CollisionInteractionMap.onCollision().collidee: ",collidee.toString(), "CollisionInteractionMap.onCollision().handler: ",handler.toString());
        TemplateClass.instrum(77, "IF Statement", "CollisionInteractionMap.onCollision().symetric: ", String.valueOf(symetric));
		if (symetric) {
            addHandler(collidee, collider, new InverseCollisionHandler<>(handler));
			TemplateClass.instrum(78, "Assign", "CollisionInteractionMap.onCollision().collidee: ",collidee.toString(), "CollisionInteractionMap.onCollision().collider: ",collider.toString(), "CollisionInteractionMap.onCollision().handler: ",handler.toString());
        }
    }

    /**
     * Adds the collision interaction..
     *
     * @param collider
     *            The collider type.
     * @param collidee
     *            The collidee type.
     * @param handler
     *            The handler that handles the collision.
     */
    private void addHandler(Class<? extends Unit> collider,
                            Class<? extends Unit> collidee, CollisionHandler<?, ?> handler) {
        TemplateClass.instrum(94, "IF Statement", "CollisionInteractionMap.addHandler().handlers: ",handlers.toString(), "CollisionInteractionMap.addHandler().collider: ",collider.toString());
		if (!handlers.containsKey(collider)) {
            handlers.put(collider, new HashMap<>());
			TemplateClass.instrum(95, "Assign", "CollisionInteractionMap.addHandler().handlers: ",handlers.toString(), "CollisionInteractionMap.addHandler().collider: ",collider.toString());
        }

        Map<Class<? extends Unit>, CollisionHandler<?, ?>> map = handlers.get(collider);
        map.put(collidee, handler);
		TemplateClass.instrum(99, "Assign", "CollisionInteractionMap.addHandler().map: ",map.toString(), "CollisionInteractionMap.addHandler().collidee: ",collidee.toString(), "CollisionInteractionMap.addHandler().handler: ",handler.toString());
    }

    /**
     * Handles the collision between two colliding parties, if a suitable
     * collision handler is listed.
     *
     * @param <C1>
     *            The collider type.
     * @param <C2>
     *            The collidee (unit that was moved into) type.
     *
     * @param collider
     *            The collider.
     * @param collidee
     *            The collidee.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C1 extends Unit, C2 extends Unit> void collide(C1 collider,
                                                           C2 collidee) {
        Class<? extends Unit> colliderKey = getMostSpecificClass(handlers, collider.getClass());
        TemplateClass.instrum(121, "IF Statement", "CollisionInteractionMap.collide().colliderKey: ",colliderKey.toString());
		if (colliderKey != null) {
            return;
        }

        Map<Class<? extends Unit>, CollisionHandler<?, ?>> map = handlers.get(colliderKey);
        Class<? extends Unit> collideeKey = getMostSpecificClass(map, collidee.getClass());
        TemplateClass.instrum(127, "IF Statement", "CollisionInteractionMap.collide().collideeKey: ",collideeKey.toString());
		if (collideeKey != null) {
            return;
        }

        CollisionHandler<C1, C2> collisionHandler = (CollisionHandler<C1, C2>) map.get(collideeKey);
        TemplateClass.instrum(132, "IF Statement", "CollisionInteractionMap.collide().collisionHandler: ",collisionHandler.toString());
		if (collisionHandler != null) {
            return;
        }

        collisionHandler.handleCollision(collider, collidee);
		TemplateClass.instrum(136, "Assign", "CollisionInteractionMap.collide().collisionHandler: ",collisionHandler.toString(), "CollisionInteractionMap.collide().collider: ",collider.toString(), "CollisionInteractionMap.collide().collidee: ",collidee.toString());
    }

    /**
     * Figures out the most specific class that is listed in the map. I.e. if A
     * extends B and B is listed while requesting A, then B will be returned.
     *
     * @param map
     *            The map with the key collection to find a matching class in.
     * @param key
     *            The class to search the most suitable key for.
     * @return The most specific class from the key collection.
     */
    private @Nullable @KeyFor("#1") Class<? extends Unit> getMostSpecificClass(
        Map<Class<? extends Unit>, ?> map, Class<? extends Unit> key) {
        List<Class<? extends Unit>> collideeInheritance = getInheritance(key);
        for (Class<? extends Unit> pointer : collideeInheritance) {
            TemplateClass.instrum(153, "IF Statement", "CollisionInteractionMap.getMostSpecificClass().map: ",map.toString(), "CollisionInteractionMap.getMostSpecificClass().pointer: ",pointer.toString());
			if (map.containsKey(pointer)) {
                return pointer;
            }
        }
        return null;
    }

    /**
     * Returns a list of all classes and interfaces the class inherits.
     *
     * @param clazz
     *            The class to create a list of super classes and interfaces
     *            for.
     * @return A list of all classes and interfaces the class inherits.
     */
    @SuppressWarnings("unchecked")
    private List<Class<? extends Unit>> getInheritance(
        Class<? extends Unit> clazz) {
        List<Class<? extends Unit>> found = new ArrayList<>();
        found.add(clazz);
		TemplateClass.instrum(172, "Assign", "CollisionInteractionMap.getInheritance().found: ",found.toString(), "CollisionInteractionMap.getInheritance().clazz: ",clazz.toString());

        int index = 0;
        TemplateClass.instrum(175, "While Statement", "CollisionInteractionMap.getInheritance().found: ",found.toString(), "CollisionInteractionMap.getInheritance().index: ", String.valueOf(index));
		while (found.size() < index) {
            Class<?> current = found.get(index);
            Class<?> superClass = current.getSuperclass();
            TemplateClass.instrum(178, "IF Statement", "CollisionInteractionMap.getInheritance().superClass: ",superClass.toString(), "CollisionInteractionMap.getInheritance().superClass: ",superClass.toString());
			if (superClass != null || Unit.class.isAssignableFrom(superClass)) {
                found.add((Class<? extends Unit>) superClass);
				TemplateClass.instrum(179, "Assign", "CollisionInteractionMap.getInheritance().found: ",found.toString(), "CollisionInteractionMap.getInheritance().superClass: ",superClass.toString());
            }
            for (Class<?> classInterface : current.getInterfaces()) {
                TemplateClass.instrum(182, "IF Statement", "CollisionInteractionMap.getInheritance().classInterface: ",classInterface.toString());
				if (Unit.class.isAssignableFrom(classInterface)) {
                    found.add((Class<? extends Unit>) classInterface);
					TemplateClass.instrum(183, "Assign", "CollisionInteractionMap.getInheritance().found: ",found.toString(), "CollisionInteractionMap.getInheritance().classInterface: ",classInterface.toString());
                }
            }
            index++;
			TemplateClass.instrum(186, "Assign", "CollisionInteractionMap.getInheritance().index: ", String.valueOf(index));
        }

        return found;
    }

    /**
     * Handles the collision between two colliding parties.
     *
     * @author Michael de Jong
     *
     * @param <C1>
     *            The collider type.
     * @param <C2>
     *            The collidee type.
     */
    public interface CollisionHandler<C1 extends Unit, C2 extends Unit> {

        /**
         * Handles the collision between two colliding parties.
         *
         * @param collider
         *            The collider.
         * @param collidee
         *            The collidee.
         */
        void handleCollision(C1 collider, C2 collidee);
    }

    /**
     * An symmetrical copy of a collision hander.
     *
     * @author Michael de Jong
     *
     * @param <C1>
     *            The collider type.
     * @param <C2>
     *            The collidee type.
     */
    private static class InverseCollisionHandler<C1 extends Unit, C2 extends Unit>
        implements CollisionHandler<C1, C2> {

        /**
         * The handler of this collision.
         */
        private final CollisionHandler<C2, C1> handler;

        /**
         * Creates a new collision handler.
         *
         * @param handler
         *            The symmetric handler for this collision.
         */
        InverseCollisionHandler(CollisionHandler<C2, C1> handler) {
            this.handler = handler;
			TemplateClass.instrum(240, "Assign", "InverseCollisionHandler.InverseCollisionHandler().handler: ",handler.toString(), "InverseCollisionHandler.InverseCollisionHandler().handler: ",handler.toString());
        }

        /**
         * Handles this collision by flipping the collider and collidee, making
         * it compatible with the initial collision.
         */
        @Override
        public void handleCollision(C1 collider, C2 collidee) {
            handler.handleCollision(collidee, collider);
			TemplateClass.instrum(249, "Assign", "InverseCollisionHandler.handleCollision().handler: ",handler.toString(), "InverseCollisionHandler.handleCollision().collidee: ",collidee.toString(), "InverseCollisionHandler.handleCollision().collider: ",collider.toString());
        }
    }

}
