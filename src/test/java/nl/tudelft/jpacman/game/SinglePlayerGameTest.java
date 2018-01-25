package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SinglePlayerGameTest {
    SinglePlayerGame spg;
    Player p = mock(Player.class);
    Level l = mock(Level.class);
    @Before
    public void init() throws Exception {
        spg = new SinglePlayerGame(p,l);
    }
    @Test
    public void getPlayers() throws Exception {
        assertTrue(spg.getPlayers().contains(p));
    }

    @Test
    public void getLevel() throws Exception {
        assertEquals(spg.getLevel(),l);
    }

}
