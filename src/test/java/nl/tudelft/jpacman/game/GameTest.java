package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GameTest {

    Game g = mock(Game.class);
    Player p = mock(Player.class);
    Level l = mock(Level.class);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() throws Exception{
        System.setOut(new PrintStream(outContent));
    }


    @Test
    public void stop() throws Exception {
        System.out.print("hello");
        assertEquals(outContent.toString(),"hello");
        g.stop();
        assertFalse(g.isInProgress());
    }


 /*   @Test
    public void isInProgress() throws Exception {
        assertFalse(g.isInProgress());
    }

    @Test
    public void levelWon() throws Exception {
        g.levelWon();
        assertFalse(g.isInProgress());
    }

    @Test
    public void levelLost() throws Exception {
        g.levelWon();
        assertFalse(g.isInProgress());
    }

*/
}
