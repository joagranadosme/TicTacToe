package co.edu.unal.tictactoe;

/**
 * Created by Jonathan on 29/10/17.
 */

public class TicTacToe {

    private char[] board;

    public TicTacToe(){
        board = new char[9];
    }

    public boolean free(int position){
        return board[position]==' ';
    }

    public int state(char player1, char player2){
        //Horizontal
        if(
            (board[0]==player1 && board[1]==player1 && board[2]==player1) ||
            (board[3]==player1 && board[4]==player1 && board[5]==player1) ||
            (board[6]==player1 && board[7]==player1 && board[8]==player1)
                )
            return 1;

        if(
            (board[0]==player2 && board[1]==player2 && board[2]==player2) ||
            (board[3]==player2 && board[4]==player2 && board[5]==player2) ||
            (board[6]==player2 && board[7]==player2 && board[8]==player2)
                )
            return -1;

        //Vertical
        if(
            (board[0]==player1 && board[3]==player1 && board[6]==player1) ||
            (board[1]==player1 && board[4]==player1 && board[7]==player1) ||
            (board[2]==player1 && board[5]==player1 && board[8]==player1)
                )
            return 1;

        if(
            (board[0]==player2 && board[3]==player2 && board[6]==player2) ||
            (board[1]==player2 && board[4]==player2 && board[7]==player2) ||
            (board[2]==player2 && board[5]==player2 && board[8]==player2)
                )
            return -1;

        //Diagonal
        if(
            (board[0]==player1 && board[4]==player1 && board[8]==player1) ||
            (board[2]==player1 && board[4]==player1 && board[6]==player1)
                )
            return 1;

        if(
            (board[0]==player2 && board[4]==player2 && board[8]==player2) ||
            (board[2]==player2 && board[4]==player2 && board[6]==player2)
                )
            return -1;

        return 0;
    }

    public int make_move(char player, int pos){
        if(board[pos] == ' ') {
            board[pos] = player;
            return 1;
        }
        return -1;
    }

    public void clearBoard(){
        for(int i=0; i<9; i++)
            board[i] = ' ';
    }

}
