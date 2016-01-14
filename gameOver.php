<html>
	<head>
		GAME OVER
		<?php
			//DB INFO
			$servername = "localhost";
			$dbUsername = "root";
			$dbPassword = "";
			$dbName = "tictactoe";
			
			//connect to DB
			$conn = new mysqli($servername, $dbUsername, $dbPassword, $dbName);
			
			// Check connection
			if ($conn->connect_error) {
				die("Connection failed: " . $conn->connect_error);
			}
			
			//sleep for 2 seconds to allow other clients to catch up
			sleep(2);
			
			//Reset all cells
			$update = "UPDATE tictactoe.gamestate SET cell1 = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET cell2 = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET cell3 = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET cell4 = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET cell5 = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET cell6 = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET cell7 = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET cell8 = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET cell9 = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET currentPlayer = 1 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET player1 = 1 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET player2 = 2 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET u1connected = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET u2connected = 0 WHERE gid = '0'";
			$conn->query($update);
			
			$update = "UPDATE tictactoe.gamestate SET gameOver = 0 WHERE gid = '0'";
			$conn->query($update);
			
		?>
	</head>
</html>
