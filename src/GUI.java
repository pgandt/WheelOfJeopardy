import java.io.IOException;
import java.util.HashMap;
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
        
        grid.setGridLinesVisible(true);
        
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
                  
        Scene scene = new Scene(grid, 1920, 1000);
        
        primaryStage.setScene(scene);
              
        primaryStage.show(); 
        
        this.setTurn(true);
        
    }
    
    private void setTurn(boolean player1)
    {
    	if(player1)
    	{
    		this.player1Txt.setFill(Color.RED);
    		this.player1ScoreTxt.setFill(Color.RED);
    		this.player1TokensTxt.setFill(Color.RED);
    		
    		this.player2Txt.setFill(Color.BLACK);
    		this.player2ScoreTxt.setFill(Color.BLACK);
    		this.player2TokensTxt.setFill(Color.BLACK);
    		
    		this.turnTxt.setText("Turn: Player 1");
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
    	}
    	
    	
    }
    
    private void spinWheel(Sector sector)
    {
    	this.setTurn(false);
    	
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
    	
    	rotate.setDuration(Duration.millis(8000)); 
    	rotate.setFromAngle(0);
    	rotate.setToAngle(spinWheelOffset + endingAngle);
    	
    	rotate.setNode(this.wheelImageView);  
    	
    	rotate.setOnFinished(e -> 
    	{qPoints[0][0].setStyle("-fx-background-color: lightgreen;");
        qPoints[1][0].setStyle("-fx-background-color: lightgreen;");
        qPoints[2][0].setStyle("-fx-background-color: red;");
        
        qPoints[0][4].setStyle("-fx-background-color: lightgreen;");
        qPoints[1][4].setStyle("-fx-background-color: red;");
        qPoints[2][4].setStyle("-fx-background-color: lightgreen;");
        qPoints[3][4].setStyle("-fx-background-color: red;");});
    	
    	rotate.play();
    	
    	// reference game object in future
    	this.spinsRemainTxt.setText("Spins Remaining: " + 49);
    	

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
        
        Button startGameBtn = new Button("Start Game");
        startGameBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(startGameBtn, 82, 2, 50, 1);
        
        startGameBtn.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent e) {
            	
            	startGameBtn.setDisable(true);
            	
            	openPlayerPrompt();  
            		

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
        grid.add(arrowImageView, 51 + gridCol, 10 + gridRow, 5,5);
        
        this.spinWheelBtn = new Button("Spin Wheel");
        this.spinWheelBtn.setDisable(true);
        this.spinWheelBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(this.spinWheelBtn, 34, 35, 50, 1);
        
        this.spinWheelBtn.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent e) {  
            	
            	Sector currentSector;
            	Player currentPlayer;
            	
            	// obtain sector from game object
            	currentSector = game.wheel.spinWheel();
            	currentPlayer = game.getCurrentPlayer();
            	
            	// if the sector is lose turn, then prompt if user wants to use free turn token if they have any
            	switch (currentSector)
                {
                case LOSE_TURN :
                   System.out.println("You lose this turn!");
                   if(currentPlayer.getFreeTurn() != 0)
                   {
                   }
                   // print lose turn here or in spin wheel? Probably in spin wheel
                   break;

                case FREE_TURN :
                	currentPlayer.addFreeTurn();
                   System.out.println("You get a free turn token!");
                   return;

                case BANKRUPT :

                   System.out.println("You've gone bankrupt!");
                   if (currentPlayer.getScore() > 0)
                   {
                      // bankrupt player by setting score to 0
                	   currentPlayer.setScore(0);
                   }
                   break;

                case PLAYER_CHOICE :
                	
                	/*
                   System.out.println("Player's choice");
                   ew.myEnum = p.chooseCategory();
                   q = this.board.askQuestion(ew.myEnum);
                   */

                   
                	/*
                   if(q == null) {
                      System.out.println("Category had no questions left, skipping turn.");
                   }
                   else {

                      System.out.println(q.getQuestion() + "\n\n");
                      System.out.println("Enter the number of the answer you would like to select:");
                      System.out.println("\t 1.) " + q.getCorrectAnswer());
                      System.out.println("\t 2.) " + q.getWrongAnswer1());
                      System.out.println("\t 3.) " + q.getWrongAnswer2());

                      int decision = 0;
                      }*/
                   break;

                case OPPONENT_CHOICE :

                   System.out.println("Opponent's choice");
                   
                   /*
                   ew.myEnum = p.nextPlayer.chooseCategory();
                   q = this.board.askQuestion(ew.myEnum);

                   

                   if(q == null) {
                      System.out.println("Category had no questions left, skipping turn.");
                   }
                   else {

                      System.out.println(q.getQuestion() + "\n\n");
                      System.out.println("Enter the number of the answer you would like to select:");
                      System.out.println("\t 1.) " + q.getCorrectAnswer());
                      System.out.println("\t 2.) " + q.getWrongAnswer1());
                      System.out.println("\t 3.) " + q.getWrongAnswer2());

                      int decision = 0;

                      if(decision == 1) {
                         System.out.println("Correct!");
                         i.i = 1;
                      }
                      else {
                         System.out.println("Incorrect...");
                         i.i = -1;
                      }

                      p.setScore(p.getScore() + i.i * q.points);
                   }
                   */
                   break;

                case SPIN_AGAIN :
                   
                   System.out.println("Spin again!");
                   return;

                   // all remaining sectors
                default :

                	/*
                   // sector will be the category name
                   q = this.board.askQuestion(ew.myEnum);
					
                   

                   if(q == null) {
                      System.out.println("Category had no questions left, skipping turn.");
                   }
                   else {

                      System.out.println(q.getQuestion() + "\n\n");
                      System.out.println("Enter the number of the answer you would like to select:");
                      System.out.println("\t 1.) " + q.getCorrectAnswer());
                      System.out.println("\t 2.) " + q.getWrongAnswer1());
                      System.out.println("\t 3.) " + q.getWrongAnswer2());

                      p.setScore(p.getScore() + i.i * q.points);
                   }*/
                }
            	
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

    		this.category[i] = new Text(" Category " + i + " ");
    		this.category[i].setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

    		HBox currentBox = new HBox(this.category[i]);
    		currentBox.setAlignment(Pos.CENTER);
    		currentBox.setPrefHeight(50);
    		currentBox.setStyle("-fx-background-color: pink;");

    		
    		
    		grid.add(currentBox, gridCol+i, gridRow);
    	}
    	
    	this.qPoints = new TextField[5][6];
    	
    	
    	// obtain point values from game object
    	for(int row=0; row < this.qPoints.length; row++)
    	{
    		for(int col=0; col < this.qPoints[row].length; col++)
    		{
    			this.qPoints[row][col] = new TextField(Integer.toString((row+1)*200));
    			this.qPoints[row][col].setPrefWidth(1000);
    			this.qPoints[row][col].setStyle("-fx-background-color: lightblue;");
    			this.qPoints[row][col].setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    			this.qPoints[row][col].setAlignment(Pos.CENTER);
    			this.qPoints[row][col].setEditable(false);
    			grid.add(this.qPoints[row][col], gridCol + col%6, gridRow + 1 + row%5);
    			 			
    		}
    	}
    	
    	
    }
    
    public void openPlayerPrompt()
    {
        GridPane playerGrid = new GridPane();
        
        // default position of the grid from the top left of the scene to the center
        playerGrid.setAlignment(Pos.CENTER);
        
        // spacing between the rows and the columns
        playerGrid.setHgap(10);
        playerGrid.setVgap(10);
        
        // space around the edge of the grid pane (top, right, bottom, left)
        playerGrid.setPadding(new Insets(25, 25, 25, 25));
    	
        playerGrid.setGridLinesVisible(true);
    	
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
    
    private Game game;
    
    private Button spinWheelBtn;
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
    
    private String picBasePath;
}