package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.PlayerFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GameFactoryTest {
    PlayerFactory playerFactory = mock(PlayerFactory.class);
    GameFactory g;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void init() throws Exception {
        g = new GameFactory(playerFactory);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void getPlayerFactory() throws Exception {
        assertEquals(playerFactory,g.getPlayerFactory());
    }

}
