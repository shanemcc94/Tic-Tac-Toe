package tictactoeWeb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.sql.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shanemcc94
 */
public class gameLogic  extends JFrame implements ActionListener, Runnable {
    private Color[] players = new Color[2];
    private int currPlayer;
    private JButton[][] myButtons = new JButton[3][3];
    private JPanel panel = new JPanel();
    private int squares;
    private ServerSocket server;
    private String username;
    private int currentPlayer;
    private int uid;
    
    public gameLogic(String[] line) {
    	uid = Integer.parseInt(line[0]);
    	username = line[1];
        currPlayer = 0;
        squares = 9;
        players = new Color[]{Color.RED, Color.GREEN};
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        panel.setSize(450,450);
        panel.setBackground(Color.white);
        getContentPane().add(panel);
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                myButtons[i][j] = new JButton();
                myButtons[i][j].setPreferredSize(new Dimension(150, 150));
                myButtons[i][j].addActionListener(this);
                panel.add(myButtons[i][j]);
            }
        }
        pack();
        
        setVisible(true);

        
        //get uid
        int uid = Integer.parseInt(line[0]);
        System.out.println("UID: " + line[0]);
        
        //check u1 connected
        try
		{
			//Connect to database
			Connection myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tictactoe", "root", "");
			Statement mystat = myConn.createStatement();
			
			//Select all from gamestate
			String sql = "select * from tictactoe.gamestate";
			ResultSet myRe = mystat.executeQuery(sql);
			System.out.println("\n\n\n");
			
			
			//"update tictactoe.gamestate set cell" + i + " = '0' where gid = '0' "
			myRe.next();
			int u1connected = myRe.getInt(14);
			if (u1connected == 0)
			{
				mystat.executeUpdate("Update tictactoe.gamestate set u1connected = '1'");
				mystat.executeUpdate("Update tictactoe.gamestate set player1 = '" + uid + "'");
				mystat.executeUpdate("Update tictactoe.gamestate set currentPlayer = '" + uid + "'");
				currentPlayer = uid;// sets player1 to be current player
				System.out.println(currentPlayer);
			}
			else
			{
				mystat.executeUpdate("Update tictactoe.gamestate set u2connected = '1'");
				mystat.executeUpdate("Update tictactoe.gamestate set player2 = '" + uid + "'");
			}
			
		}
        //check u2 connected

        catch(Exception exc){
        	System.out.println("error");
        }
        

        ScheduledExecutorService executor =
        	    Executors.newSingleThreadScheduledExecutor();
    	Runnable periodicTask = new Runnable() {
    		public void run() {
    			// TODO Auto-generated method stub
    			colourSquares();
    			
    			
    			gameOver();
    		}
    	};
    	executor.scheduleAtFixedRate(periodicTask, 0, 1, TimeUnit.SECONDS); 
    	
    	
        WindowListener l = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
       
        
        addWindowListener(l);
    }
    
    /*
    public static void main(String[] args) {
        gameLogic board = new gameLogic();
    }
	*/
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        //if currentPlayer = you
        if(currentPlayer()){
        	for(int i = 0; i < 3; i++) {
        		for(int j = 0; j < 3; j++) {
	                if(source == myButtons[i][j]) {
	                	checkMove(myButtons[i][j]);
	                    if (i == 0 & j == 0)
	                    	updateDataBase(1);
	                    if (i == 0 & j == 1)
	                    	updateDataBase(2);
	                    if (i == 0 & j == 2)
	                    	updateDataBase(3);
	                    if (i == 1 & j == 0)
	                    	updateDataBase(4);
	                    if (i == 1 & j == 1)
	                    	updateDataBase(5);
	                    if (i == 1 & j == 2)
	                    	updateDataBase(6);
	                    if (i == 2 & j == 0)
	                    	updateDataBase(7);
	                    if (i == 2 & j == 1)
	                    	updateDataBase(8);
	                    if (i == 2 & j == 2 )
	                    	updateDataBase(9);
	                }
            	}
            }
        }
    }       
    public boolean currentPlayer(){
    	
    	// check if you are the current player 
    	
		try{
    		Connection myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tictactoe", "root", "");
			Statement mystat = myConn.createStatement();
			
			//check which player you are 
			String sql = "select * from tictactoe.gamestate";
			ResultSet myRe = mystat.executeQuery(sql);
			
			myRe.next();
			
			//checkPlayer = currentPlayer
			int checkplayer = myRe.getInt(11);
			System.out.println("currentPlayer: "  + checkplayer);
			
			int otherPlayerUid = 0;
			
			//otherPlayerUid = not me.
			if(myRe.getInt(12) == uid)
				otherPlayerUid = myRe.getInt(13);
			else if(myRe.getInt(13) == uid)
				otherPlayerUid = myRe.getInt(12);
			
			//if currentPlayer == Me, make move and then swap current player to other player
			if(checkplayer == uid){
				//update currentPlayer
				mystat.executeUpdate("Update tictactoe.gamestate set currentPlayer = '" + otherPlayerUid + "'");
				return true;
			}
			
			//else it is not your turn
			else{
				System.out.println("It is not your turn");
				return false;
			}
    	}
    		
		catch(Exception ex){
    			System.out.println("cant update the current player");
    	}
    	return false;
    }
	
    
    public void checkMove(JButton b) {
        if(freeSpace(b)) {
           
            if(!gameOver()) {
                currPlayer++;
                squares--;
                currPlayer %= 2;
                if(squares == 0) {
                    System.out.println("Game over!\n Draw!");
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    int response = JOptionPane.showConfirmDialog(null, "Game over - Draw!\nDo you want to play again?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    } else if (response == JOptionPane.YES_OPTION) {
                        reset();
                    } else if (response == JOptionPane.CLOSED_OPTION) {
                        System.exit(-1);
                    }    
                }
            }
            else {
                System.out.println("Player " + (currPlayer + 1) + " wins!");
                JDialog.setDefaultLookAndFeelDecorated(true);
                int response = JOptionPane.showConfirmDialog(null, "Player " + (currPlayer + 1) + " wins!\nDo you want to play again?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                	reset();
                	System.exit(0);
                } else if (response == JOptionPane.YES_OPTION) {
                    reset();
                } else if (response == JOptionPane.CLOSED_OPTION) {
                	reset();
                	System.exit(-1);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Space taken.");
        }
    }
    
    public boolean freeSpace(JButton b) {
        if(b.getBackground() == Color.RED || b.getBackground() == Color.GREEN) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean gameOver() {
        boolean flag = false;
        for(int i=0;i<3;i++) {
            if(myButtons[i][0].getBackground() == myButtons[i][1].getBackground() && myButtons[i][1].getBackground() == myButtons[i][2].getBackground() &&  !freeSpace(myButtons[i][0]) ) {
                flag = true;
                
            }
            if(myButtons[0][i].getBackground() == myButtons[1][i].getBackground() && myButtons[0][i].getBackground() == myButtons[2][i].getBackground() &&  !freeSpace(myButtons[0][i]) ) {
                flag = true;
            }            
        }
        if(myButtons[0][0].getBackground() == myButtons[1][1].getBackground() && myButtons[0][0].getBackground() == myButtons[2][2].getBackground() && !freeSpace(myButtons[0][0])) {
             flag = true;
        }
        if(myButtons[0][2].getBackground() == myButtons[1][1].getBackground() && myButtons[2][0].getBackground() == myButtons[0][2].getBackground() && !freeSpace(myButtons[0][2])) {
             flag = true;
        }
        
        //if game over
        if (flag == true)
        {
        	//reset db so all cells = 0
            try{
            	Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tictactoe", "root", "");
            	Statement mystat = myConn.createStatement();
            	for (int i = 1; i <= 9; i++)
            	{
            		String sql = "update tictactoe.gamestate set cell" + i + " = '0' where gid = '0' ";
            		mystat.executeUpdate(sql);
            	}
            	mystat.executeUpdate("update tictactoe.gamestate set player1 = '1'");
            	mystat.executeUpdate("update tictactoe.gamestate set player2 = '2'");
            	mystat.executeUpdate("update tictactoe.gamestate set u1connected = '0'");
            	mystat.executeUpdate("update tictactoe.gamestate set u2connected = '0'");
            	
            	try {
            	    Thread.sleep(3000);   
            	    System.exit(0);
            	    //1000 milliseconds is one second.
            	} catch(InterruptedException ex) {
            	    Thread.currentThread().interrupt();
            	}
            	
            	
            }
            catch(Exception exc){
            	System.out.println("Bad Error");
            }
        }
        return flag;
    }
    
    public void reset() {
    	currPlayer = 0;
        squares = 9;
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                myButtons[i][j].setBackground(new Color(238,238,238));
            }
        }
        //reset entire DB
        try{
        	Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tictactoe", "root", "");
        	Statement mystat = myConn.createStatement();
        	
        	for (int i = 1; i <= 9; i++)
        	{
        		String sql = "update tictactoe.gamestate set cell" + i + " = '0' where gid = '0' ";
        		mystat.executeUpdate(sql);
        	}
        	
        	mystat.executeUpdate("update tictactoe.gamestate set player1 = '99'");
        	mystat.executeUpdate("update tictactoe.gamestate set player2 = '99'");
        	mystat.executeUpdate("update tictactoe.gamestate set u1connected = '0'");
        	mystat.executeUpdate("update tictactoe.gamestate set u2connected = '0'");
        }
        catch(Exception exc){
        	System.out.println("Could not reset DB");
        }
        
    }

    private void updateDataBase(int selected) {
       
    	//Connect to database
        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tictactoe", "root", "");
            Statement mystat = myConn.createStatement();
            
            //WORKING HERE
            //Update cell every time a move is made.
            //if player 1
        	String sql = "SELECT * FROM tictactoe.users where username = '" + username + "'" ;
        	int user = 0;
        	
        	ResultSet myRe = mystat.executeQuery(sql);
        	while(myRe.next()){
        		user = myRe.getInt(1);
        	}
        	String sql2 = "update tictactoe.gamestate set cell" + selected + " = '" + user + "' where gid = '0' ";
        	mystat.executeUpdate(sql2);
        	System.out.println(sql2);
        }
        catch(Exception exc){
            System.out.println("error cant connnect to database ");
        }
    }

	public void colourSquares(){
		
		JButton b;
		int[] cellValues = new int[9];
    	try
    	{
    		Connection myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tictactoe", "root", "");
			Statement mystat = myConn.createStatement();
			
			String sql = "SELECT * FROM tictactoe.gamestate";
			ResultSet myRe = mystat.executeQuery(sql);
			
			int player1,player2;
			
			myRe.next();
			player1 = myRe.getInt(12);
			player2 = myRe.getInt(13);
			
			System.out.println("Player 1: " + player1);
			System.out.println("Player 2: " + player2);
			
			//craetes array fo valuse from DB
			for(int i = 0;i<cellValues.length;i++)
			{
				cellValues[i] = myRe.getInt(i+2);	
				System.out.print(cellValues[i] + " ");     				
			}
			int g = -1;
			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 3; j++)
				{
					g++;
					b = myButtons[i][j];
					System.out.print("CellValues index[" + g + "]: ");
					System.out.print(cellValues[g]);
					System.out.println("\n");
					if(cellValues[g] == player1)
					{	
						// update database 
						myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tictactoe", "root", "");
	        			mystat = myConn.createStatement();
						b.setBackground(Color.GREEN);
						
					}
					else if(cellValues[g] == player2)
					{
						b.setBackground(Color.RED);
						//System.out.println("player2 color");
					}
				}
			}
    	}
    	catch(Exception exc){
    		System.out.println("cant get array ");
    	}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
