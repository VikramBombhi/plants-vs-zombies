package controller;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

import event.StatsEvent;
import model.Board;
import model.Stats;
import model.TurnStackBoard;
import model.TurnStackStats;
import view.StatsPanel;

/*
 * This controller handles when the user wants to go to the next turn, redo, or undo 
 */
public class NextTurnController implements ActionListener {

    private Board board;
    private StatsPanel statsPanel;
    private Stats stats;
    private TurnStackBoard turnStackBoard;
    private TurnStackStats turnStackStats;

    // Create controller
    public NextTurnController(StatsPanel panel) {
        this.board = Board.getBoard();
        this.statsPanel = panel;
        this.stats = Stats.getStats();
        this.turnStackBoard = TurnStackBoard.getTurnStackBoard();
        this.turnStackStats = TurnStackStats.getTurnStackStats();
    }

    // Handle what the user wants to do, for now only the ability to goto the next turn is supported
    public void actionPerformed(ActionEvent e) {
        try {
            this.turnStackBoard.saveTurn(this.board.saveBoard());
            this.turnStackStats.saveTurn(this.stats.saveStats());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.board.turn();
        this.stats.incrementTurn();
        this.stats.addSunPoints(this.stats.getSunPointsGenerationRate());
	}
}
