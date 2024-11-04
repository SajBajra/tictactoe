package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TicTacToe extends JFrame {
    private static final int SIZE = 3;
    private static final char EMPTY = '.';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    
    private char[][] board;
    private char currentPlayer;
    private Cell[][] cells;

    public TicTacToe() {
        board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        currentPlayer = PLAYER_X;
        cells = new Cell[SIZE][SIZE];

        setTitle("Tic-Tac-Toe Game");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new Cell(i, j);
                add(cells[i][j]);
            }
        }
    }

    private void makeMove(int row, int col) {
        if (board[row][col] == EMPTY) {
            board[row][col] = currentPlayer;
            cells[row][col].setMark(currentPlayer);
            if (checkWin()) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                resetBoard();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "The game is a tie!");
                resetBoard();
            } else {
                switchPlayer();
            }
        }
    }

    private boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if (checkLine(board[i][0], board[i][1], board[i][2]) ||
                checkLine(board[0][i], board[1][i], board[2][i])) {
                return true;
            }
        }
        // Check diagonals
        return checkLine(board[0][0], board[1][1], board[2][2]) ||
               checkLine(board[0][2], board[1][1], board[2][0]);
    }

    private boolean checkLine(char c1, char c2, char c3) {
        return (c1 == currentPlayer && c2 == currentPlayer && c3 == currentPlayer);
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
                cells[i][j].clear();
            }
        }
        currentPlayer = PLAYER_X;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }

    private class Cell extends JPanel {
        private int row, col;
        private char mark;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            this.mark = EMPTY;
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (mark == EMPTY) {
                        makeMove(row, col);
                    }
                }
            });
        }

        public void setMark(char mark) {
            this.mark = mark;
            repaint();
        }

        public void clear() {
            mark = EMPTY;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (mark != EMPTY) {
                g.setFont(new Font("Arial", Font.BOLD, 60));
                g.drawString(String.valueOf(mark), getWidth() / 2 - 15, getHeight() / 2 + 20);
            }
        }
    }
}
