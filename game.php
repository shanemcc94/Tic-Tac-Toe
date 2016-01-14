<html>
	<body>
	<?php
		//set up server login
		$servername = "localhost";
		$dbUsername = "root";
		$dbPassword = "";
		$dbName = "tictactoe";
		
		$uid = $_GET["uid"];

		//connect to DB
		$conn = new mysqli($servername, $dbUsername, $dbPassword, $dbName);
		
		// Check connection
		if ($conn->connect_error) {
			die("Connection failed: " . $conn->connect_error);
		} 
		
		$sql = "SELECT * FROM gamestate";
		$result = $conn->query($sql);
		$row = $result->fetch_assoc();
		
		//make variables for each cell
		
		$cell1 = $row["cell1"];
		$cell2 = $row["cell2"];
		$cell3 = $row["cell3"];
		$cell4 = $row["cell4"];
		$cell5 = $row["cell5"];
		$cell6 = $row["cell6"];
		$cell7 = $row["cell7"];
		$cell8 = $row["cell8"];
		$cell9 = $row["cell9"];
		
		$player1 = $row["player1"];
		$player2 = $row["player2"];
		
		//if row 1
		if (($cell1 == $player1 && $cell2 == $player1 && $cell3 == $player1) || ($cell1 == $player2 && $cell2 == $player2 && $cell3 ==$player2))
			header("Location: gameOver.php");
		
		//if row 2
		if (($cell4 == $player1 && $cell5 == $player1 && $cell6 == $player1) || ($cell4 == $player2 && $cell5 == $player2 && $cell6 ==$player2))
			header("Location: gameOver.php");
		
		//if row 3
		if (($cell7 == $player1 && $cell8 == $player1 && $cell9 == $player1) || ($cell7 == $player2 && $cell8 == $player2 && $cell9 ==$player2))
			header("Location: gameOver.php");
		
		//if column 1
		if (($cell1 == $player1 && $cell4 == $player1 && $cell7 == $player1) || ($cell1 == $player2 && $cell4 == $player2 && $cell7 ==$player2))
			header("Location: gameOver.php");
		
		//if column 2
		if (($cell2 == $player1 && $cell5 == $player1 && $cell8 == $player1) || ($cell2 == $player2 && $cell5 == $player2 && $cell8 ==$player2))
			header("Location: gameOver.php");
			
		//if column 3
		if (($cell3 == $player1 && $cell6 == $player1 && $cell9 == $player1) || ($cell3 == $player2 && $cell6 == $player2 && $cell9 ==$player2))
			header("Location: gameOver.php");
		
		//if TLBR diag
		if (($cell1 == $player1 && $cell5 == $player1 && $cell9 == $player1) || ($cell1 == $player2 && $cell5 == $player2 && $cell9 ==$player2))
			header("Location: gameOver.php");
		
		// if TR BL diag
		if (($cell3 == $player1 && $cell5 == $player1 && $cell7 == $player1) || ($cell3 == $player2 && $cell5 == $player2 && $cell7 ==$player2))
			header("Location: gameOver.php");
			
		//$check = new playerChecks;
		//$X = $check->isPlayer1();
		//echo $X;
		$conn->close();
		
		 /*<meta http-equiv="refresh" content="5; URL=game.php">
		 setTimeout(function(){
			window.location.reload(1);
			echo "hello";
		 }, 1000);*/
	
		/*REFRESH EVERY SECOND*/
		$url1=$_SERVER['PHP_SELF'];
		header("Refresh: 1; URL=$url1?uid=$uid");
		
		
	?>
		<h3>Click a Square</h3>
		
		<?php echo $cell1 ?>
		<?php echo $cell2 ?>
		<?php echo $cell3 ?>
		<?php echo $cell4 ?>
		<?php echo $cell5 ?>
		<?php echo $cell6 ?>
		<?php echo $cell7 ?>
		<?php echo $cell8 ?>
		<?php echo "$cell9<br>" ?>
		
		<!-- Button 1 -->
		<form style= "float: left" action = "updateCell1.php" method="get">					
			<input type="hidden" name="uid" value=<?php echo $uid ?>>
			<input type="submit" value=<?php 
											if($cell1 == '1') echo 'X'; 
											else if($cell1 == '2') echo 'O';
											else echo "__";
										?> id="cell1" name = "cell1"/>
		</form>
		
		<!-- Button 2 -->
		<form style= "float: left" action = "updateCell2.php" method="get">					
			<input type="hidden" name="uid" value=<?php echo $uid ?>>
			<input type="submit" value=<?php 
											if($cell2 == '1') echo 'X'; 
											else if($cell2 == '2') echo 'O';
											else echo "__";
										?> id="cell2" name = "cell2"/>
		</form>
		
		<!-- Button 3 -->
		<form action = "updateCell3.php" method="get">
			<input type="hidden" name="uid" value=<?php echo $uid ?>>
			<input type="submit" value=<?php 
											if($cell3 == '1') echo 'X'; 
											else if($cell3 == '2') echo 'O';
											else echo '__';
										?> id="cell3" name = "cell3"/>
			<br>
		</form>
		
		<!-- Button 4 -->
		<form style= "float: left" action = "updateCell4.php" method="get">
			<input type="hidden" name="uid" value=<?php echo $uid ?>>
			<input type="submit" value=<?php 
											if($cell4 == '1') echo 'X'; 
											else if($cell4 == '2') echo 'O';
											else echo '__';
										?> id="cell4" name = "cell4"/>
		</form>
		
		<!-- Button 5 -->
		<form style= "float: left" action = "updateCell5.php" method="get">
			<input type="hidden" name="uid" value=<?php echo $uid ?>>
			<input type="submit" value=<?php 
											if($cell5 == '1') echo 'X'; 
											else if($cell5 == '2') echo 'O';
											else echo '__';
										?> id="cell5" name = "cell5"/>
		</form>
		<!-- Button 6 -->
		<form action = "updateCell6.php" method="get">
			<input type="hidden" name="uid" value=<?php echo $uid ?>>
			<input type="submit" value=<?php 
											if($cell6 == '1') echo 'X'; 
											else if($cell6 == '2') echo 'O';
											else echo '__';
										?> id="cell6" name = "cell6"/>
			<br>
		</form>
		<!-- Button 7 -->
		<form style= "float: left" action = "updateCell7.php" method="get">
			<input type="hidden" name="uid" value=<?php echo $uid ?>>
			<input type="submit" value=<?php 
											if($cell7 == '1') echo 'X'; 
											else if($cell7 == '2') echo 'O';
											else echo '__';
										?> id="cell7" name = "cell7"/>
		</form>
		<!-- Button 8 -->
		<form style= "float: left" action = "updateCell8.php" method="get">
			<input type="hidden" name="uid" value=<?php echo $uid ?>>
			<input type="submit" value=<?php 
											if($cell8 == '1') echo 'X'; 
											else if($cell8 == '2') echo 'O';
											else echo '__';
											?> id="cell8" name = "cell8"/>
		
		</form>
		<!-- Button 9 -->
		<form action = "updateCell9.php" method="get">
			<input type="hidden" name="uid" value=<?php echo $uid ?>>
			<input type="submit" value=<?php 
											if($cell9 == '1') echo 'X'; 
											else if($cell9 == '2') echo 'O';
											else echo '__';
											?> id="cell9" name = "cell9" />
			<br>
			<br>
		</form>
	</body>
</html>
