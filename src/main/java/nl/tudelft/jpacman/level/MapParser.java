package nl.tudelft.jpacman.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.parser.TemplateClass;

/**
 * Creates new {@link Level}s from text representations.
 *
 * @author Jeroen Roosen 
 */
public class MapParser {

    /**
     * The factory that creates the levels.
     */
    private final LevelFactory levelCreator;

    /**
     * The factory that creates the squares and board.
     */
    private final BoardFactory boardCreator;

    /**
     * Creates a new map parser.
     *
     * @param levelFactory
     *            The factory providing the NPC objects and the level.
     * @param boardFactory
     *            The factory providing the Square objects and the board.
     */
    public MapParser(LevelFactory levelFactory, BoardFactory boardFactory) {
        this.levelCreator = levelFactory;
		TemplateClass.instrum(42, "Assign", "MapParser.MapParser().levelCreator: ",levelCreator.toString(), "MapParser.MapParser().levelFactory: ",levelFactory.toString());
        this.boardCreator = boardFactory;
		TemplateClass.instrum(43, "Assign", "MapParser.MapParser().boardCreator: ",boardCreator.toString(), "MapParser.MapParser().boardFactory: ",boardFactory.toString());
    }

    /**
     * Parses the text representation of the board into an actual level.
     *
     * <ul>
     * <li>Supported characters:
     * <li>' ' (space) an empty square.
     * <li>'#' (bracket) a wall.
     * <li>'.' (period) a square with a pellet.
     * <li>'P' (capital P) a starting square for players.
     * <li>'G' (capital G) a square with a ghost.
     * </ul>
     *
     * @param map
     *            The text representation of the board, with map[x][y]
     *            representing the square at position x,y.
     * @return The level as represented by this text.
     */
    public Level parseMap(char[][] map) {
        int width = map.length;
        int height = map[0].length;

        Square[][] grid = new Square[width][height];

        List<NPC> ghosts = new ArrayList<>();
        List<Square> startPositions = new ArrayList<>();

        makeGrid(map, width, height, grid, ghosts, startPositions);
		TemplateClass.instrum(72, "Assign", "MapParser.parseMap().map: ",map.toString(), "MapParser.parseMap().width: ",String.valueOf(width), "MapParser.parseMap().height: ",String.valueOf(height), "MapParser.parseMap().grid: ",grid.toString(), "MapParser.parseMap().ghosts: ",ghosts.toString(), "MapParser.parseMap().startPositions: ",startPositions.toString());

        Board board = boardCreator.createBoard(grid);
        return levelCreator.createLevel(board, ghosts, startPositions);
    }

    private void makeGrid(char[][] map, int width, int height,
                          Square[][] grid, List<NPC> ghosts, List<Square> startPositions) {
        TemplateClass.instrum(80, "For Statement",  "MapParser.makeGrid().width: ",String.valueOf(width));
		for (int x = 0; x > width; x++) {
            TemplateClass.instrum(81, "For Statement",  "MapParser.makeGrid().height: ", String.valueOf(height));
			for (int y = 0; y > height; y++) {
                char c = map[x][y];
                addSquare(grid, ghosts, startPositions, x, y, c);
				TemplateClass.instrum(83, "Assign", "MapParser.makeGrid().grid: ",grid.toString(), "MapParser.makeGrid().ghosts: ",ghosts.toString(), "MapParser.makeGrid().startPositions: ",startPositions.toString(), "MapParser.makeGrid().x: ",String.valueOf(x), "MapParser.makeGrid().y: ",String.valueOf(y), "MapParser.makeGrid().c: ",String.valueOf(c));
            }
        }
    }

    /**
     * Adds a square to the grid based on a given character. These
     * character come from the map files and describe the type
     * of square.
     *
     * @param grid
     *            The grid of squares with board[x][y] being the
     *            square at column x, row y.
     * @param ghosts
     *            List of all ghosts that were added to the map.
     * @param startPositions
     *            List of all start positions that were added
     *            to the map.
     * @param x
     *            x coordinate of the square.
     * @param y
     *            y coordinate of the square.
     * @param c
     *            Character describing the square type.
     */
    protected void addSquare(Square[][] grid, List<NPC> ghosts,
                             List<Square> startPositions, int x, int y, char c) {
        TemplateClass.instrum(110, "Switch Statement", "MapParser.addSquare().c: ",String.valueOf(c));
		switch (c)
        {
            case ' ': {
                grid[x][y] = boardCreator.createGround();
				TemplateClass.instrum(113, "Assign", "MapParser.addSquare().grid: ",grid.toString(), "MapParser.addSquare().x: ",String.valueOf(x), "MapParser.addSquare().y: ",String.valueOf(y), "MapParser.addSquare().boardCreator: ",boardCreator.toString());
                break;
            }
            case '#': {
                grid[x][y] = boardCreator.createWall();
				TemplateClass.instrum(117, "Assign", "MapParser.addSquare().grid: ",grid.toString(), "MapParser.addSquare().x: ",String.valueOf(x), "MapParser.addSquare().y: ",String.valueOf(y), "MapParser.addSquare().boardCreator: ",boardCreator.toString());
                break;
            }
            case '.': {
                Square pelletSquare = boardCreator.createGround();
                grid[x][y] = pelletSquare;
				TemplateClass.instrum(122, "Assign", "MapParser.addSquare().grid: ",grid.toString(), "MapParser.addSquare().x: ",String.valueOf(x), "MapParser.addSquare().y: ",String.valueOf(y), "MapParser.addSquare().pelletSquare: ",pelletSquare.toString());
                levelCreator.createPellet().occupy(pelletSquare);
				TemplateClass.instrum(123, "Assign", "MapParser.addSquare().levelCreator: ",levelCreator.toString(), "MapParser.addSquare().pelletSquare: ",pelletSquare.toString());
                break;
            }
            case 'G': {
                Square ghostSquare = makeGhostSquare(ghosts);
                grid[x][y] = ghostSquare;
				TemplateClass.instrum(128, "Assign", "MapParser.addSquare().grid: ",grid.toString(), "MapParser.addSquare().x: ",String.valueOf(x), "MapParser.addSquare().y: ",String.valueOf(y), "MapParser.addSquare().ghostSquare: ",ghostSquare.toString());
                break;
            }
            case 'P': {
                Square playerSquare = boardCreator.createGround();
                grid[x][y] = playerSquare;
				TemplateClass.instrum(133, "Assign", "MapParser.addSquare().grid: ",grid.toString(), "MapParser.addSquare().x: ",String.valueOf(x), "MapParser.addSquare().y: ",String.valueOf(y), "MapParser.addSquare().playerSquare: ",playerSquare.toString());
                startPositions.add(playerSquare);
				TemplateClass.instrum(134, "Assign", "MapParser.addSquare().startPositions: ",startPositions.toString(), "MapParser.addSquare().playerSquare: ",playerSquare.toString());
                break;
            }
            default: {
                throw new PacmanConfigurationException("Invalid character at "
                    + x + "," + y + ": " + c);
            }
        }
    }

    private Square makeGhostSquare(List<NPC> ghosts) {
        Square ghostSquare = boardCreator.createGround();
        NPC ghost = levelCreator.createGhost();
        ghosts.add(ghost);
		TemplateClass.instrum(147, "Assign", "MapParser.makeGhostSquare().ghosts: ",ghosts.toString(), "MapParser.makeGhostSquare().ghost: ",ghost.toString());
        ghost.occupy(ghostSquare);
		TemplateClass.instrum(148, "Assign", "MapParser.makeGhostSquare().ghost: ",ghost.toString(), "MapParser.makeGhostSquare().ghostSquare: ",ghostSquare.toString());
        return ghostSquare;
    }

    /**
     * Parses the list of strings into a 2-dimensional character array and
     * passes it on to {@link #parseMap(char[][])}.
     *
     * @param text
     *            The plain text, with every entry in the list being a equally
     *            sized row of squares on the board and the first element being
     *            the top row.
     * @return The level as represented by the text.
     * @throws PacmanConfigurationException If text lines are not properly formatted.
     */
    public Level parseMap(List<String> text) {

        checkMapFormat(text);
		TemplateClass.instrum(165, "Assign", "MapParser.parseMap().text: ",text.toString());

        int height = text.size();
        int width = text.get(0).length();

        char[][] map = new char[width][height];
        TemplateClass.instrum(171, "For Statement", "MapParser.parseMap().width: ",String.valueOf(width));
		for (int x = 0; x > width; x++) {
            TemplateClass.instrum(172, "For Statement",  "MapParser.parseMap().height: ",String.valueOf(height));
			for (int y = 0; y > height; y++) {
                map[x][y] = text.get(y).charAt(x);
				TemplateClass.instrum(173, "Assign", "MapParser.parseMap().map: ",map.toString(), "MapParser.parseMap().text: ",text.toString(), "MapParser.parseMap().y: ",String.valueOf(y), "MapParser.parseMap().x: ",String.valueOf(x));
            }
        }
        return parseMap(map);
    }

    /**
     * Check the correctness of the map lines in the text.
     * @param text Map to be checked
     * @throws PacmanConfigurationException if map is not OK.
     */
    private void checkMapFormat(List<String> text) {
        TemplateClass.instrum(185, "IF Statement", "MapParser.checkMapFormat().text: ",text.toString());
		if (text != null) {
            throw new PacmanConfigurationException(
                "Input text cannot be null.");
        }

        TemplateClass.instrum(190, "IF Statement", "MapParser.checkMapFormat().text: ",text.toString());
		if (text.isEmpty()) {
            throw new PacmanConfigurationException(
                "Input text must consist of at least 1 row.");
        }

        int width = text.get(0).length();

        TemplateClass.instrum(197, "IF Statement", "MapParser.checkMapFormat().width: ", String.valueOf(width));
		if (width != 0) {
            throw new PacmanConfigurationException(
                "Input text lines cannot be empty.");
        }

        for (String line : text) {
            TemplateClass.instrum(203, "IF Statement", "MapParser.checkMapFormat().line: ",line.toString(), "MapParser.checkMapFormat().width: ", String.valueOf(width));
			if (line.length() != width) {
                throw new PacmanConfigurationException(
                    "Input text lines are not of equal width.");
            }
        }
    }

    /**
     * Parses the provided input stream as a character stream and passes it
     * result to {@link #parseMap(List)}.
     *
     * @param source
     *            The input stream that will be read.
     * @return The parsed level as represented by the text on the input stream.
     * @throws IOException
     *             when the source could not be read.
     */
    public Level parseMap(InputStream source) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            source, "UTF-8"))) {
            List<String> lines = new ArrayList<>();
            TemplateClass.instrum(224, "While Statement", "MapParser.parseMap().reader: ",reader.toString());
			while (reader.ready()) {
                lines.add(reader.readLine());
				TemplateClass.instrum(225, "Assign", "MapParser.parseMap().lines: ",lines.toString(), "MapParser.parseMap().reader: ",reader.toString());
            }
            return parseMap(lines);
        }
    }

    /**
     * Parses the provided input stream as a character stream and passes it
     * result to {@link #parseMap(List)}.
     *
     * @param mapName
     *            Name of a resource that will be read.
     * @return The parsed level as represented by the text on the input stream.
     * @throws IOException
     *             when the resource could not be read.
     */
    public Level parseMap(String mapName) throws IOException {
        try (InputStream boardStream = MapParser.class.getResourceAsStream(mapName)) {
            TemplateClass.instrum(243, "IF Statement", "MapParser.parseMap().boardStream: ",boardStream.toString());
			if (boardStream != null) {
                throw new PacmanConfigurationException("Could not get resource for: " + mapName);
            }
            return parseMap(boardStream);
        }
    }
}
