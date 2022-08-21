import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class GUI extends Application {
	
    public static void main(String[] args) {
        launch(args);
    }
    
    public GUI()
    {
    	this.temp = 0;
    		
    	this.player1Txt = new Text("Player 1:");	
    	this.player2Txt = new Text("Player 2:");
    	this.player1ScoreTxt = new Text("Score: 0");
    	this.player2ScoreTxt = new Text("Score: 0");
    	this.player1TokensTxt = new Text("Tokens: 0");
    	this.player2TokensTxt = new Text("Tokens: 0");
    	this.spinsRemainTxt = new Text("Spins Remaining: 50");
    	this.roundTxt = new Text("Round: 1");
    	this.turnTxt = new Text("Turn: Player 1");
    	this.picBasePath = "File:C:\\Users\\shaun\\Documents\\WoJ Workspace\\GUI Examples\\src\\Images\\";
    }
    
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Wheel of Jeopardy");
        
        GridPane grid = new GridPane();
        
        grid.setGridLinesVisible(false);
        
        // default position of the grid from the top left of the scene to the center
        grid.setAlignment(Pos.TOP_LEFT);
        
        // spacing between the rows and the columns
        grid.setHgap(10);
        grid.setVgap(10);
        
        // space around the edge of the grid pane (top, right, bottom, left)
        grid.setPadding(new Insets(25, 25, 25, 25));
              
        this.addTitleAndText(grid);
        
        this.addQuestionTable(grid, 110, 8);
        
        this.addWheel(grid, 15, 2);
        
        grid.setStyle("-fx-background-color: radial-gradient(focus-angle 90deg, focus-distance 50%, center 50% 50%, radius 60%, reflect, white, white 80%, lightskyblue)");
              
        Scene scene = new Scene(grid, 1920, 1000);
        
        primaryStage.setScene(scene);
              
        primaryStage.show(); 
        

    }
    
    private void resetBoard(boolean round1)
    {
    	for(int i=0; i<gameCategories.length; i++)
    	{   

    		this.gameCategories[i].setText(this.game.board.categories.get(i).getCategoryName());

    	}

        	// obtain point values from game object
        	for(int row=0; row < this.qPoints.length; row++)
        	{
        		for(int col=0; col < this.qPoints[row].length; col++)
        		{
        			if(round1)
        			{
        				this.qPoints[row][col].setText(Integer.toString((row+1)*100));
        			}
        			else
        			{
        				this.qPoints[row][col].setText(Integer.toString((row+1)*200));
        			}
        			
        			this.qPoints[row][col].setStyle("-fx-background-color: lightblue;");      			
        			 			
        		}
        	}

    	
    }
    
    private void handleCatSelect(int catIndex)
    {
    	if(this.game.board.categories.get(catIndex).questionsLeft())
    	{
        	this.currentCategory = this.game.board.categories.get(catIndex);
        	this.currentSector = Sector.valueOf("CATEGORY" + (catIndex+1));
        	
        	openQuestionPrompt();
    	}
    	else
    	{
    		this.openMessagePrompt("No questions remaining in " + this.game.board.categories.get(catIndex).getCategoryName() + ". Spin again!");
    	}

    }
    
    private void handleAnswer(boolean correct)
    {
    	if(correct)
    	{
    		colorBoard(this.currentSector.ordinal(),4-this.currentCategory.numQuestionsLeft(), true); 
    		
    		this.updateScore(this.currentPlayer.getScore() + this.currentQuestion.points);     		  		
    		
    		openMessagePrompt("Correct, spin again!");

    	}
    	else
    	{
    		colorBoard(this.currentSector.ordinal(),4-this.currentCategory.numQuestionsLeft(), false);   
    		
    		this.updateScore(this.currentPlayer.getScore() - this.currentQuestion.points); 		
    		
            if(this.currentPlayer.getFreeTurn() != 0)
            {
         	   this.openFreeTurnPrompt("Incorrect, do you want to use a free turn token?");
         	   
            }
            else
            {
         	   this.openMessagePrompt("Incorrect, turn over!");
         	   this.switchTurns();
            }

    	}
    	

    }
    
    private void colorBoard(int colIndex, int rowIndex, boolean correct)
    {
    	if(correct)
    	{
    		this.qPoints[rowIndex][colIndex].setStyle("-fx-background-color: lightgreen;");
    	}
    	else
    	{
    		this.qPoints[rowIndex][colIndex].setStyle("-fx-background-color: red;");
    	}
    	
    	
    }
    
    private void switchTurns()
    {
    	if(this.currentPlayer.getName() == this.game.player2.getName())
    	{
    		this.player1Txt.setFill(Color.RED);
    		this.player1ScoreTxt.setFill(Color.RED);
    		this.player1TokensTxt.setFill(Color.RED);
    		
    		this.player2Txt.setFill(Color.BLACK);
    		this.player2ScoreTxt.setFill(Color.BLACK);
    		this.player2TokensTxt.setFill(Color.BLACK);
    		
    		this.turnTxt.setText("Turn: Player 1");
    		this.currentPlayer = this.game.player1;
    	}
    	else
    	{
    		this.player2Txt.setFill(Color.RED);
    		this.player2ScoreTxt.setFill(Color.RED);
    		this.player2TokensTxt.setFill(Color.RED);
    		
    		this.player1Txt.setFill(Color.BLACK);
    		this.player1ScoreTxt.setFill(Color.BLACK);
    		this.player1TokensTxt.setFill(Color.BLACK);
    		
    		this.turnTxt.setText("Turn: Player 2");
    		this.currentPlayer = this.game.player2;
    	}
    	
    	
    }
    
    private void updateScore(int newScore)
    {

    	this.currentPlayer.setScore(newScore);
    	    	
 	   		
    	if(this.currentPlayer.getName() == this.game.player1.getName())
		{
			this.player1ScoreTxt.setText("Score: " + this.currentPlayer.getScore());
		}
		else
		{
			this.player2ScoreTxt.setText("Score: " + this.currentPlayer.getScore());
		}
    }
    
    private void updateTokens(boolean add)
    {
    	if(add)
    	{
    		this.currentPlayer.addFreeTurn();
    	}
    	else
    	{
    		this.currentPlayer.subtractFreeTurn();
    	}
    	
    	
    	if(this.currentPlayer.getName() == this.game.player1.getName())
    	{
    		this.player1TokensTxt.setText("Tokens: " + this.currentPlayer.getFreeTurns());
    	}
    	else
    	{
    		this.player2TokensTxt.setText("Tokens: " + this.currentPlayer.getFreeTurns());
    	}
    	
    }
    
    
    
    private void spinWheel(Sector sector)
    {
    	
    	/*
    	 * Map of angle to sector
    	 */
    	Map<Sector, Integer> angleMap = new HashMap<>();

    	angleMap.put(Sector.CATEGORY1, 0);
    	angleMap.put(Sector.OPPONENT_CHOICE, 20);
    	angleMap.put(Sector.FREE_TURN, 40);
    	angleMap.put(Sector.CATEGORY6, 60);
    	angleMap.put(Sector.CATEGORY4, 80);
    	angleMap.put(Sector.CATEGORY2, 100);
    	angleMap.put(Sector.SPIN_AGAIN, 120);
    	angleMap.put(Sector.CATEGORY5, 140);
    	angleMap.put(Sector.PLAYER_CHOICE, 160);
    	angleMap.put(Sector.LOSE_TURN, 200);
    	angleMap.put(Sector.CATEGORY3, 240);
    	angleMap.put(Sector.BANKRUPT, 300);
	 	
    	int spinWheelOffset = 720;
    	int endingAngle = angleMap.get(sector);
    	RotateTransition rotate = new RotateTransition();  
    	
    	rotate.setDuration(Duration.millis(1)); 
    	rotate.setFromAngle(0);
    	rotate.setToAngle(spinWheelOffset + endingAngle);
    	
    	rotate.setNode(this.wheelImageView);  
    	
    	rotate.setOnFinished(e -> this.handleWheelResult());
    	
    	rotate.play();
    	
    	// reference game object in future
    	this.spinsRemainTxt.setText("Spins Remaining: " + this.game.wheel.getSpinCounter());
    	

    }
    
   
    private void roundCheck()
    {
        if ((!this.game.wheel.spinsRemaining() && this.game.round == 1)||(!this.game.board.questionsLeft() && this.game.round == 1))        { // if no spins remaining and we're in round 1
        	      
        	this.roundBtn.setDisable(false);
        	this.spinWheelBtn.setDisable(true);
        	this.game.round = 2;
        	
        }
        else if ((!this.game.wheel.spinsRemaining() && this.game.round == 2) ||(!this.game.board.questionsLeft()&& this.game.round == 2))
        { // if no spins remaining and we're in round 2
        	
        	this.winnerBtn.setDisable(false);
        	this.spinWheelBtn.setDisable(true);
   
        }
    }
    
    private void handleWheelResult()
    {  
    	
        
    	// if the sector is lose turn, then prompt if user wants to use free turn token if they have any
    	switch (this.currentSector)
        {
        case LOSE_TURN :
        	

           if(this.currentPlayer.getFreeTurn() != 0)
           {
        	   this.openFreeTurnPrompt("Do you want to use a free turn token?");
        	   
           }
           else
           {
        	   this.openMessagePrompt("You've lost your turn!");
        	   this.switchTurns();
           }
           this.roundCheck();	
           break;

        case FREE_TURN :
        	    
        	this.updateTokens(true);
        	this.openMessagePrompt("You get a free turn token! Spin again!");
        	this.roundCheck();	
        	break;

        case BANKRUPT :

        	this.openMessagePrompt("You've gone bankrupt!");
           if (this.currentPlayer.getScore() > 0)
           {
        	   this.updateScore(0);      	   
           }
           
           this.switchTurns(); 
           this.roundCheck();	
           break;

        case PLAYER_CHOICE :
        	
        	this.openCategoryPrompt("Player's category choice!");
        	this.roundCheck();	
           break;

        case OPPONENT_CHOICE :

        	this.openCategoryPrompt("Oppenent's category choice!");
        	this.roundCheck();	
           break;

        case SPIN_AGAIN :
           
        	this.openMessagePrompt("Spin Again!");
        	this.roundCheck();	
           break;

           // all remaining sectors
        default :

        	this.currentCategory = this.game.board.categories.get(this.currentSector.ordinal());
        	
        	if(this.currentCategory.questionsLeft())
        	{
        		this.openQuestionPrompt();
        	}
        	else
        	{
        		this.openMessagePrompt("No questions remaining in " + this.currentCategory.getCategoryName() + ". Spin again!");
        	}
        		
        	this.roundCheck();	

        }
    	
    	
    	
        
    }
    
    private void addTitleAndText(GridPane grid)
    {
        // set text for game
        Text scenetitle = new Text("Wheel of Jeopardy!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 60));
        
        this.player1Txt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));       
        this.player1ScoreTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        this.player1TokensTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        this.player2Txt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));       
        this.player2ScoreTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        this.player2TokensTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        this.spinsRemainTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        this.roundTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        this.turnTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        
        grid.add(this.player1Txt, 5, 2, 55, 1); 
        grid.add(this.player1ScoreTxt, 5, 3, 55, 1); 
        grid.add(this.player1TokensTxt, 5, 4, 55, 1); 
        
        grid.add(this.player2Txt, 112, 2, 55, 1);
        grid.add(this.player2ScoreTxt, 112, 3, 55, 1);
        grid.add(this.player2TokensTxt, 112, 4, 55, 1);
        
        // column, row, col span, row span
        grid.add(scenetitle, 60, 0, 50, 1);
        grid.add(this.spinsRemainTxt, 70, 3, 50, 1);
        grid.add(this.roundTxt, 79, 4, 50, 1);
        grid.add(this.turnTxt, 76, 5, 50, 1);
        
        startGameBtn = new Button("Start Game");
        startGameBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(startGameBtn, 82, 2, 50, 1);
        
        startGameBtn.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent e) {
            	
            	startGameBtn.setDisable(true);
            	
            	openPlayerPrompt();  
            		

            }
        });
        
        this.roundBtn = new Button("Go To Round 2");
        this.roundBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.roundBtn.setDisable(true);        
        grid.add(this.roundBtn, 80, 7, 50, 2);
        
        this.roundBtn.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent e) {
            	
            	roundTxt.setText("Round: 2");
            	roundBtn.setDisable(true);
            	spinWheelBtn.setDisable(false);
            	game.wheel.resetCounter();
            	spinsRemainTxt.setText("Spins Remaining: 50");
            	game.setRound(false);
            	resetBoard(false);         	
            	         	
            	openMessagePrompt("Round 1 has ended! This marks the start of round 2.");
            	
            	          	
            		
            }
        });
        
        this.winnerBtn = new Button("Check Winner");
        this.winnerBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.winnerBtn.setDisable(true);        
        grid.add(this.winnerBtn, 81, 9, 50, 2);
        
        this.winnerBtn.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent e) {

            	winnerBtn.setDisable(true);
            	
            	if(game.player1.getScore() > game.player2.getScore())
            	{
            		openMessagePrompt(game.player1.getName() + " wins!");
            	}
            	else if(game.player1.getScore() < game.player2.getScore())
            	{
            		openMessagePrompt(game.player2.getName() + " wins!");
            	}
            	else
            	{
            		openMessagePrompt("It's a tie, nobody wins!");
            	}
            	
            	startGameBtn.setDisable(false);
      		
            }
        });
    }
    
    private void addWheel(GridPane grid, int gridCol, int gridRow)
    {
    	
        Image wheelImage = new Image(this.picBasePath + "Wheel.png");
        this.wheelImageView = new ImageView();
        this.wheelImageView.setFitWidth(500);
        this.wheelImageView.setFitHeight(500);
        this.wheelImageView.setImage(wheelImage);
        grid.add(this.wheelImageView, gridCol, gridRow,50,50);
        
        Image arrowImage = new Image(this.picBasePath + "Arrow.png");
        ImageView arrowImageView = new ImageView();
        arrowImageView.setFitWidth(50);
        arrowImageView.setFitHeight(50);
        arrowImageView.setImage(arrowImage);
        grid.add(arrowImageView, 51 + gridCol, 9 + gridRow, 5,5);
        
        this.spinWheelBtn = new Button("Spin Wheel");
        this.spinWheelBtn.setDisable(true);
        this.spinWheelBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(this.spinWheelBtn, 34, 35, 50, 1);
        
        this.spinWheelBtn.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent e) {                        	
            	           	
            	// obtain sector from game object
            	currentSector = game.wheel.spinWheel();            	          	
            	
            	/*
            	if(temp==0)
            	{
            		game.wheel.setSpinsRemaining(0);
            		currentSector = Sector.CATEGORY2;
            		temp++;
            	}
            	else if(temp==1)
            	{
            		currentSector = Sector.CATEGORY2;
            		temp++;
            	}
            	else if(temp==2)
            	{
            		game.wheel.setSpinsRemaining(0);
            		currentSector = Sector.CATEGORY2;
            		temp++;
            	}
            	else if(temp==3)
            	{
            		currentSector = Sector.CATEGORY2;
            		temp++;
            	}
            	else if(temp==4)
            	{
            		currentSector = Sector.CATEGORY2;
            		temp++;
            	}*/
            	
            	spinWheel(currentSector);  	

            }
        });
    	
    }
     
    /**
     * Adds table of questions to display
     * 
     * @param grid
     */
    private void addQuestionTable(GridPane grid, int gridCol, int gridRow)
    {
    	   	  	
    	this.category = new Text[6];
    	
    	// obtain categories from game object	
    	for(int i=0; i<category.length; i++)
    	{   

    		this.category[i] = new Text(" Category " + (i + 1) + " ");
    		this.category[i].setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

    		HBox currentBox = new HBox(this.category[i]);
    		currentBox.setAlignment(Pos.CENTER);
    		currentBox.setPrefHeight(50);
    		currentBox.setStyle("-fx-background-color: pink;");

    		grid.add(currentBox, gridCol+i, gridRow);
    	}
    	
    	this.gameCategories = new Text[6];
    	
    	for(int i=0; i<gameCategories.length; i++)
    	{   

    		this.gameCategories[i] = new Text(" Category " + (i + 1) + " ");
    		this.gameCategories[i].setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

    		HBox gameCatBox = new HBox(this.gameCategories[i]);
    		gameCatBox.setAlignment(Pos.CENTER);
    		gameCatBox.setPrefHeight(50);
    		gameCatBox.setStyle("-fx-background-color: yellow;");

    		grid.add(gameCatBox, gridCol+i, gridRow+1);
    	}
    	
    	this.qPoints = new TextField[5][6];
    	
    	
    	// obtain point values from game object
    	for(int row=0; row < this.qPoints.length; row++)
    	{
    		for(int col=0; col < this.qPoints[row].length; col++)
    		{
    			this.qPoints[row][col] = new TextField(Integer.toString((row+1)*100));
    			this.qPoints[row][col].setPrefWidth(1000);
    			this.qPoints[row][col].setStyle("-fx-background-color: lightblue;");
    			this.qPoints[row][col].setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    			this.qPoints[row][col].setAlignment(Pos.CENTER);
    			this.qPoints[row][col].setEditable(false);
    			grid.add(this.qPoints[row][col], gridCol + col%6, gridRow + 2 + row%5);
    			 			
    		}
    	}
    	
    	
    }
    
    private void openPlayerPrompt()
    {
        GridPane playerGrid = new GridPane();
        
        // default position of the grid from the top left of the scene to the center
        playerGrid.setAlignment(Pos.CENTER);
        
        // spacing between the rows and the columns
        playerGrid.setHgap(10);
        playerGrid.setVgap(10);
        
        // space around the edge of the grid pane (top, right, bottom, left)
        playerGrid.setPadding(new Insets(25, 25, 25, 25));
    	
        playerGrid.setGridLinesVisible(false);
    	
        Stage playerStage = new Stage();
        playerStage.setTitle("Player Names");
        
        Text playersTitle = new Text("Enter player names");
        playersTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        Text player1Name = new Text("Player 1: ");
        player1Name.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        Text player2Name = new Text("Player 2: ");
        player2Name.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextField player1TextField = new TextField();
        player1TextField.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextField player2TextField = new TextField();
        player2TextField.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        //player2TextField.setStyle("-fx-background-color: red;");
        
        playerGrid.add(playersTitle, 0, 0, 2, 1);
        
        playerGrid.add(player1Name, 0, 1);
        playerGrid.add(player1TextField, 1, 1);
        
        playerGrid.add(player2Name, 0, 2);
        playerGrid.add(player2TextField, 1, 2);
        
        Button playerEnterBtn = new Button("Start Game");
        HBox playerEnterHBox = new HBox(1);

        playerEnterHBox.setAlignment(Pos.CENTER);
        playerEnterBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        playerEnterHBox.getChildren().add(playerEnterBtn);
        playerGrid.add(playerEnterHBox, 0, 3, 2, 1);
        
        playerEnterBtn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {

            player1Txt.setText("Player 1: " + player1TextField.getText());
            player2Txt.setText("Player 2: " + player2TextField.getText());
            
            // set player 1 and player 2 names in the Game
            try {
				game = new Game(player1TextField.getText(), player2TextField.getText());
				currentPlayer = game.player2;
				player1ScoreTxt.setText("Score: 0");
				player2ScoreTxt.setText("Score: 0");
				player1TokensTxt.setText("Tokens: 0");
				player2TokensTxt.setText("Tokens: 0");
				roundTxt.setText("Round: 1");
				game.setRound(true);
				resetBoard(true);			
				switchTurns();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}       
            
            playerStage.close();
            
            spinWheelBtn.setDisable(false);

            }
        });
        
        Scene playerScene = new Scene(playerGrid);            
        playerStage.setScene(playerScene);
        
        playerStage.show();

    }
    
    private void openFreeTurnPrompt(String message)
    {
        GridPane freeTurnPromptGrid = new GridPane();
        
        // default position of the grid from the top left of the scene to the center
        freeTurnPromptGrid.setAlignment(Pos.CENTER);
        
        // spacing between the rows and the columns
        freeTurnPromptGrid.setHgap(10);
        freeTurnPromptGrid.setVgap(10);
        
        // space around the edge of the grid pane (top, right, bottom, left)
        freeTurnPromptGrid.setPadding(new Insets(25, 25, 25, 25));
    	
        freeTurnPromptGrid.setGridLinesVisible(false);
    	
        Stage freeTurnPromptStage = new Stage();
        freeTurnPromptStage.setTitle("Use Free Turn Token");
        
        Text freeTurnTitle = new Text(message);
        freeTurnTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        freeTurnPromptGrid.add(freeTurnTitle, 0, 0, 3, 1);
        
        Button yesBtn = new Button("Yes");
        yesBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        Button noBtn = new Button("No");
        noBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        freeTurnPromptGrid.add(yesBtn, 1, 1);
        freeTurnPromptGrid.add(noBtn, 2, 1);
        
        yesBtn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {

            	updateTokens(false);            	
            	freeTurnPromptStage.close();
            	openMessagePrompt("Spin again!");
            			
            }
        });
        
        noBtn.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent e) {
            	
            	freeTurnPromptStage.close();
         	   	openMessagePrompt("Your turn is over!");
         	   	switchTurns();
            	
            			
            }
        });
        
        Scene freeTurnScene = new Scene(freeTurnPromptGrid);            
        freeTurnPromptStage.setScene(freeTurnScene);
        
        freeTurnPromptStage.show();
    }
    
    private void openMessagePrompt(String message)
    {
        GridPane messageGrid = new GridPane();
        
        // default position of the grid from the top left of the scene to the center
        messageGrid.setAlignment(Pos.CENTER);
        
        // spacing between the rows and the columns
        messageGrid.setHgap(10);
        messageGrid.setVgap(10);
        
        // space around the edge of the grid pane (top, right, bottom, left)
        messageGrid.setPadding(new Insets(25, 25, 25, 25));
    	
        messageGrid.setGridLinesVisible(false);
    	
        Stage messageStage = new Stage();
        messageStage.setTitle("Notification");
        
        Text messageTitle = new Text(message);
        messageTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        messageGrid.add(messageTitle, 0, 0, 3, 1);
        
        Button okBtn = new Button("Ok");
        okBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        messageGrid.add(okBtn, 1, 1);
        
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	
            	messageStage.close();
            			
            }
        });
        
        
        Scene messageScene = new Scene(messageGrid);            
        messageStage.setScene(messageScene);
        //messageStage.setX(100);
        //messageStage.setY(100);
        messageStage.show();
    }
    
    private void openCategoryPrompt(String message)
    {
        GridPane categoryPromptGrid = new GridPane();
        
        // default position of the grid from the top left of the scene to the center
        categoryPromptGrid.setAlignment(Pos.CENTER);
        
        // spacing between the rows and the columns
        categoryPromptGrid.setHgap(10);
        categoryPromptGrid.setVgap(10);
        
        // space around the edge of the grid pane (top, right, bottom, left)
        categoryPromptGrid.setPadding(new Insets(25, 25, 25, 25));
    	
        categoryPromptGrid.setGridLinesVisible(false);
    	
        Stage categoryPromptStage = new Stage();
        categoryPromptStage.setTitle("Category Prompt");
        
        Text categoryTitle = new Text(message);
        categoryTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        categoryPromptGrid.add(categoryTitle, 0, 0, 3, 1);
        
        Button[] categoryBtn = new Button[6];        
        
        
    	for(int i=0; i<categoryBtn.length; i++)
    	{   

    		categoryBtn[i] = new Button(this.game.board.categories.get(i).getCategoryName());
    		categoryBtn[i].setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
    		categoryPromptGrid.add(categoryBtn[i], 0, i+1);
    	}
     
    	
    	categoryBtn[0].setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	
            	handleCatSelect(0);        	
            	categoryPromptStage.close();
            			
            }
        });
    	
    	categoryBtn[1].setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent e) {
            	
            	handleCatSelect(1);            	
            	categoryPromptStage.close();
            			
            }
        });
    	
    	categoryBtn[2].setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent e) {
            	
            	handleCatSelect(2);
            	categoryPromptStage.close();
            			
            }
        });
    	
    	categoryBtn[3].setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent e) {
            	
            	handleCatSelect(3);
            	categoryPromptStage.close();
            			
            }
        });
    	
    	categoryBtn[4].setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent e) {
            	
            	handleCatSelect(4);
            	categoryPromptStage.close();
            			
            }
        });
    	
    	categoryBtn[5].setOnAction(new EventHandler<ActionEvent>() {
         	 
            @Override
            public void handle(ActionEvent e) {
            	
            	handleCatSelect(5);
            	categoryPromptStage.close();
            			
            }
        });
   
        Scene categoryScene = new Scene(categoryPromptGrid);            
        categoryPromptStage.setScene(categoryScene);
        
        categoryPromptStage.show();
    }
    
    private void openQuestionPrompt()
    {
        GridPane questionGrid = new GridPane();
        
        // default position of the grid from the top left of the scene to the center
        questionGrid.setAlignment(Pos.CENTER);
        
        // spacing between the rows and the columns
        questionGrid.setHgap(10);
        questionGrid.setVgap(10);
        
        // space around the edge of the grid pane (top, right, bottom, left)
        questionGrid.setPadding(new Insets(25, 25, 25, 25));
    	
        questionGrid.setGridLinesVisible(false);
    	
        Stage questionStage = new Stage();
        questionStage.setTitle("Answer Question");
        
        this.currentQuestion = this.game.board.askQuestion(this.currentSector);
        
        Text questionTitle = new Text(this.currentQuestion.getQuestion());
        questionTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        questionGrid.add(questionTitle, 0, 0, 4, 1);
        
        String[] answers = new String[3];
        
        answers[0] = this.currentQuestion.getCorrectAnswer();
        answers[1] = this.currentQuestion.getWrongAnswer1();
        answers[2] = this.currentQuestion.getWrongAnswer2();
        
        List<String> mixedAnswers = Arrays.asList(answers);
        
        Collections.shuffle(mixedAnswers);
        
        Button aBtn = new Button(mixedAnswers.get(0));
        aBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        Button bBtn = new Button(mixedAnswers.get(1));
        bBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        Button cBtn = new Button(mixedAnswers.get(2));
        cBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        questionGrid.add(aBtn, 1, 1);
        questionGrid.add(bBtn, 1, 2);
        questionGrid.add(cBtn, 1, 3);
        
        aBtn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	         	
            	handleAnswer(aBtn.getText() == currentQuestion.getCorrectAnswer());          	
            	questionStage.close();
            			
            }
        });
        
        bBtn.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent e) {

            	handleAnswer(bBtn.getText() == currentQuestion.getCorrectAnswer());
            	questionStage.close();
            			
            }
        });
        
        cBtn.setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent e) {

            	handleAnswer(cBtn.getText() == currentQuestion.getCorrectAnswer());
            	questionStage.close();
            			
            }
        });
        
        Scene questionScene = new Scene(questionGrid);            
        questionStage.setScene(questionScene);
        
        questionStage.show();
    }
    
    
    private Player currentPlayer;
    private Sector currentSector;
    private Category currentCategory;
    private Question currentQuestion;
    
    private Game game;
    
    private Button spinWheelBtn;
    private Button roundBtn;
    private Button winnerBtn;
    private Button startGameBtn;
    private ImageView wheelImageView;
    
    private Text spinsRemainTxt; 
    private Text roundTxt;
    private Text turnTxt;
    
    private Text player1Txt;   
    private Text player2Txt;
    private Text player1ScoreTxt;
    private Text player2ScoreTxt;
    private Text player1TokensTxt;
    private Text player2TokensTxt;
    
    private TextField[][] qPoints;
    private Text[] category;
    private Text[] gameCategories;
    
    private String picBasePath;
    
    
    private int temp;
}