package tictactoeWeb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.*;

public class GUI implements  ActionListener
{
    JPanel buttonPanel;
    JButton exitButton, logInButton;
	JLabel userNameLabel, passwordLabel;
	JTextField userNameTextField;
	JPasswordField passwordTextField;
	JPanel totalGUI = new JPanel();
	static JFrame frame = new JFrame("Log In Screen");

    public JPanel createContentPane()
	{
        //Make bottom JPanel to place buttonPanel on
        //JPanel totalGUI = new JPanel();
        totalGUI.setLayout(null);

        //Make Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setLocation(10, 10);
        buttonPanel.setSize(295, 185);
        totalGUI.add(buttonPanel);

		//Make Labels
		userNameLabel = new JLabel("Username:");
		userNameLabel.setLocation(0, 0);
        userNameLabel.setSize(80, 30);
        buttonPanel.add(userNameLabel);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setLocation(0, 40);
        passwordLabel.setSize(80, 30);
        buttonPanel.add(passwordLabel);
		
		//Username and Password Fields
		userNameTextField = new JTextField();
		userNameTextField.setLocation(90, 0);
        userNameTextField.setSize(180, 30);
        buttonPanel.add(userNameTextField);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setLocation(90, 40);
        passwordTextField.setSize(180, 30);
        buttonPanel.add(passwordTextField);
		
		//Make Buttons
		exitButton = new JButton("Exit");
        exitButton.setLocation(0, 80);
        exitButton.setSize(85, 30);
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);
		
        logInButton = new JButton("Log In");
        logInButton.setLocation(93, 80);
        logInButton.setSize(85, 30);
        logInButton.addActionListener(this);
        buttonPanel.add(logInButton);
        
        
        totalGUI.setVisible(true);
        return totalGUI;
    }

    public void actionPerformed(ActionEvent e)
	{
        if(e.getSource() == exitButton)
        {
			System.exit(0);
			JOptionPane.showMessageDialog(null, "Register Course Director Window");
        }
        
		
        else if(e.getSource() == logInButton)
        {
        	
			//declare variables for username and password
			String userName = userNameTextField.getText();
			String password = passwordTextField.getText();
			
			//Random JOptionPane that shows username and password.
			//JOptionPane.showMessageDialog(null, userName + " " + password);
			
			//check if username and password exist
			try
			{
				//Connect to database
    			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tictactoe", "root", "");
    			Statement mystat = myConn.createStatement();
    					
    			//Get ID for user
    			String sql = "select * from tictactoe.users WHERE username = '" + userName + "'";
    			System.out.println(sql);
    			ResultSet myRe = mystat.executeQuery(sql);
    			String dbUser = "";
    			String dbName = "";
    			String[] line = new String [2];
    			
    			//get db data
    			while (myRe.next()){
    				
    				dbUser = myRe.getString(1);
    				dbName = myRe.getString(3);
    				System.out.println(dbUser +" " + dbName);  
    				line[0] = myRe.getString(1);
    				line[1] = myRe.getString(3);
    			}
    			
    			//If user/pass match, log in.
    			if (dbName.equals(password)){
    				gameLogic board = new gameLogic(line);
    				createAndShowGUI();
    				frame.setVisible(false);
    			}
				
				
			}
			
			catch(Exception exc)
			{
				System.out.println("aoigha");
				System.exit(0);
			}
		}
        
    }

    private static void createAndShowGUI()
	{
        //Create and set up the content pane.
    	GUI window = new GUI();
        frame.setContentPane(window.createContentPane());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(305, 165);
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args)
	{
        SwingUtilities.invokeLater(new Runnable() 
		{
            public void run() 
			{
                createAndShowGUI();
            }
        });
    }
    
}


