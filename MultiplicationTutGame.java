//this file is stored in a package called applet in Eclipse.
package applet;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import tapplet.TApplet; 

public class MultiplicationTutGame extends TApplet {
	
	public static void main(String[] args) {
		new SummativeProject();
	}
	
	//the arrays used to store backgrounds and number cards
		Image [] Entrybackground, cards, Playbackground;
	//used to regulate the order of clicked mouse
		int count = 0; 
	//the number of background used
		int bg_num = 0;
	//start phrase and change background phrase
		String start, change;
	//background music and various sound effects used
		AudioClip sound_always, sound_correct, sound_wrong, sound_combo, sound_sigh, sound_win, sound_lose;
	//setting page, multiply sign, equal sign, and the win, lose, star, and cross icons
		Image background_setting, multiply, equal, win, lose, star, x;
	
	public void init() {
		setSize(800, 600);
		Graphics offG = getScreenBuffer();
	//set up the entry page
		Entrybackground = new Image [6];
		//set up the phrases
			start = "START";
			change = "CHANGE BACKGROUND";
			MediaTracker tracker = new MediaTracker(this);
		
		//add background music
			sound_always = getAudioClip(getCodeBase(), "Background_music.wav");
		
		//add entry backgrounds to the array
			for (int i=1; i<7; i++){
				Entrybackground [i-1] = getImage(getCodeBase(), "EntryBackground_" + i + ".jpg");
					tracker.addImage(Entrybackground [i-1], 0);
			}
		
		//add background setting page
			background_setting = getImage(getCodeBase(), "Background_settingPage.jpg");
				tracker.addImage(background_setting, 0);
	
	//set up the game page	
		cards = new Image [11];
		Playbackground = new Image [6];
		
		//add number cards to the array
			for (int i=1; i < 11; i++) {
				cards [i] = getImage(getCodeBase(), i + ".jpeg");
					tracker.addImage(cards [i], 0);
			}
		
		//add the signs
			multiply = getImage(getCodeBase(), "Multiplication_sign.png");
				tracker.addImage(multiply, 0);
			equal = getImage(getCodeBase(), "Equal_sign.jpeg");
				tracker.addImage(equal, 0);
		
		//add in-game backgrounds to the array
			for (int i=1; i < 7; i++) {
				Playbackground [i-1] = getImage(getCodeBase(), "InGameBackground_" + i + ".jpg");
					tracker.addImage(Playbackground [i-1], 0);
			}
		
		//add the icons
			win = getImage(getCodeBase(), "win_icon.png");
				tracker.addImage(win, 0);
			lose = getImage(getCodeBase(), "lose_icon.png");
				tracker.addImage(lose, 0);
			star = getImage(getCodeBase(), "correct_icon.png");
				tracker.addImage(star, 0);
			x = getImage(getCodeBase(), "wrong_icon.png");
				tracker.addImage(x, 0);
			
		//add sound effects
			sound_correct = getAudioClip(getCodeBase(), "sound_correct.wav");
			sound_wrong = getAudioClip(getCodeBase(), "sound_wrong.wav");
			sound_combo = getAudioClip(getCodeBase(), "sound_combo.wav");
			sound_sigh = getAudioClip(getCodeBase(), "sound_sigh.wav");
			sound_win = getAudioClip(getCodeBase(), "sound_win.wav");
			sound_lose = getAudioClip(getCodeBase(), "sound_lose.wav");
		
		
	//ensure all images are loaded successfully
		while (tracker.checkAll(true)!= true){}

		if (tracker.isErrorAny()){
			JOptionPane.showMessageDialog(null, "Trouble loading picture");
		}
		
	//play the background music
		sound_always.play();
	//draw the default background
		offG.drawImage(Entrybackground[0], 0, 0, this);
	//draw the words and buttons
		offG.drawRect(265, 300, 270, 50);
		offG.drawString(start, 380, 330);
		offG.drawRect(265, 420, 270, 50);
		offG.drawString(change, 330, 450);
		
		repaint();
	}
	
	//the game method
	public void game (int n) {
		Graphics g = getScreenBuffer();
		//number of correct answers
			int pt = 0;
		//used to record number of consecutive wrong answers
			int wrong = 0;
		//number of crosses
			int x_num = 0;
		//used to record number of consecutive correct answers
			int star_pre = 0;
		//number of stars
			int star_num = 0;
		Random randNum = new Random();
	
	/* 
	 * main part of the game, will continue until the user reaches a total of 
	 * 25 correct answers or reaches 3 crosses.
	 */
		while (pt < 25 && x_num < 3) {
		//set up the background
			g.drawImage(Playbackground[n], 0, 0, this);
			//draw the stars
				g.drawImage(star, 0, 30, this);
				String star_line = "X " + star_num;
				g.drawString(star_line, 50, 80);
			//draw the crosses
				g.drawImage(x, 0, 100, this);
				String x_line = "X " + x_num;
				g.drawString(x_line, 50, 150);
			
			repaint();
		
			//print the point on the upper right corner
				String pt_line = "Points: " + pt; 
				g.drawString(pt_line, 700, 50);
			
		//print the first mathematical expression	
			//the first random number
				int num1 = randNum.nextInt(10)+1;
			//the second random number
				int num2 = randNum.nextInt(10)+1;
			//prints the corresponding pictures
				g.drawImage(cards[num1], 50, 200, this);
				g.drawImage(cards[num2], 350, 200, this);
				g.drawImage(multiply, 200, 200, this);
				g.drawImage(equal, 500, 200, this);
				
				repaint();
			
		//ask the user to enter the answer
			int answer = Integer.parseInt(JOptionPane.showInputDialog("Please enter the answer: "));
		//check if the answer is correct
			if (answer == num2 * num1) {
				//if correct, get one point, sound will be played
					pt ++;
					sound_correct.play();
				//have one + consecutive correct answer
					star_pre ++;
				//check if she reaches five consecutive correct
					if (star_pre == 5) {
						//if yes, play the sound and # of star +1
						sound_correct.stop();
						sound_combo.play();
						star_num ++;
						//# of consecutive correct becomes zero
						star_pre = 0;
					}
				//if user gets 1/1+ correct answer, # of consecutive wrong becomes zero
					wrong = 0;
			}
		//if the user enters the wrong answer
			else {
				//# of consecutive wrong +1, sound played
					wrong ++;
					sound_wrong.play();
				//check if the user reaches three consecutive wrong
					if (wrong == 3) {
						//if yes, sound played and # of cross +1
						sound_wrong.stop();
						sound_sigh.play();
						x_num ++;
						//# of consecutive wrong becomes zero
						wrong = 0;
				}
				//if the user gets 1/1+ wrong answer. # of consecutive correct becomes zero
					star_pre = 0;
			}
		}
		
	//check if the user reaches 25 correct answers in total
		if (pt == 25) {
		//if yes, play the winning sound
			sound_always.stop();
			sound_correct.stop();
			sound_combo.stop();
			sound_win.play();
		//print the winning page
			g.drawImage(Playbackground[n], 0, 0, this);
			g.drawImage(win, 0, 0, this);
			
			repaint();
		}
		
	//check if the user reaches three crosses
		if (x_num == 3) {
		//if yes, play the losing sound
			sound_always.stop();				
			sound_wrong.stop();
			sound_sigh.stop();
			sound_lose.play();
		//print the losing page				
			g.drawImage(Playbackground[n], 0, 0, this);
			g.drawImage(lose, 0, 0, this);
			
			repaint();
		}	
	}
	
	public void mouseDown(int x, int y) {
		Graphics g = getScreenBuffer();
		
	//if the user clicks on the START button
		if((x > 265 && x < 535) && (y > 300 && y < 350) && count == 0) {
			game(bg_num);
		}
		
	//if the user clicks on the CHANGE BACKGROUND button
		else if ((x > 265 && x < 535) && (y > 420 && y < 470) && count == 0) {
			g.drawImage(background_setting, 0, 0, this);
			count ++;
			
			repaint();
		}

		//if the user choose the first background picture
			else if ((x > 60 && x < 240) && (y > 70 && y < 205) && count == 1) {
				//draw the default background
				g.drawImage(Entrybackground[0], 0, 0, this);
				//draw the words and buttons
				g.drawRect(265, 300, 270, 50);
				g.drawString(start, 380, 330);
				g.drawRect(265, 420, 270, 50);
				g.drawString(change, 330, 450);
				count = 0;
				bg_num = 0;
				repaint();
			}
		//if the user choose the second background picture
			else if ((x > 304 && x < 492) && (y > 69 && y < 209) && count == 1) {
				//draw the default background
				g.drawImage(Entrybackground[1], 0, 0, this);
				//draw the words and buttons
				g.drawRect(265, 300, 270, 50);
				g.drawString(start, 380, 330);
				g.drawRect(265, 420, 270, 50);
				g.drawString(change, 330, 450);
				count = 0;
				bg_num = 1;
				repaint();
			}
		//if the user choose the third background picture
			else if ((x > 552 && x < 742) && (y > 67 && y < 207) && count == 1) {
				//draw the default background
				g.drawImage(Entrybackground[2], 0, 0, this);
				//draw the words and buttons
				g.drawRect(265, 300, 270, 50);
				g.drawString(start, 380, 330);
				g.drawRect(265, 420, 270, 50);
				g.drawString(change, 330, 450);
				count = 0;
				bg_num = 2;
				repaint();
			}
		//if the user choose the fourth background picture
			else if ((x > 55 && x < 244) && (y > 302 && y < 445) && count == 1) {
				//draw the default background
				g.drawImage(Entrybackground[3], 0, 0, this);
				//draw the words and buttons
				g.drawRect(265, 300, 270, 50);
				g.drawString(start, 380, 330);
				g.drawRect(265, 420, 270, 50);
				g.drawString(change, 330, 450);
				count = 0;				
				bg_num = 3;
				repaint();
			}
		//if the user choose the fifth background picture
			else if ((x > 302 && x < 490) && (y > 304 && y < 445) && count == 1) {
				//draw the default background
				g.drawImage(Entrybackground[4], 0, 0, this);
				//draw the words and buttons
				g.drawRect(265, 300, 270, 50);
				g.drawString(start, 380, 330);
				g.drawRect(265, 420, 270, 50);
				g.drawString(change, 330, 450);
				count = 0;
				bg_num = 4;
				repaint();
			}
		//if the user choose the sixth background picture
			else if ((x > 552 && x < 744) && (y > 302 && y < 446) && count == 1) {
				//draw the default background
				g.drawImage(Entrybackground[5], 0, 0, this);
				//draw the words and buttons
				g.drawRect(265, 300, 270, 50);
				g.drawString(start, 380, 330);
				g.drawRect(265, 420, 270, 50);
				g.drawString(change, 330, 450);
				count = 0;
				bg_num = 5;
				repaint();
			}
	}
}
