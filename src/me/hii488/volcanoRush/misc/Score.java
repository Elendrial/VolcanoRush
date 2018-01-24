package me.hii488.volcanoRush.misc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import me.hii488.handlers.FileHandler;

public class Score implements Comparator<Score>, Serializable{
	
	private static final long serialVersionUID = -7156916902531681012L;
	
	protected int score = 0;
	public float multiplier = 1;
	public String name = "";
	
	public Score(){}
	
	public Score(String name, int score){
		this.name = name;
		this.score = score;
	}
	
	public Score(int score, String name){
		this.name = name;
		this.score = score;
	}
	
	public int compare(Score a, Score b){
		return a.getScore() - b.getScore();
	}
	
	public void addToScore(int i){
		score += i;
	}
	
	public int getScore(){
		return (int) (score * multiplier);
	}
	
	public String toString(){
		return "" + getScore();
	}
	
	public void saveScore(String fileName){
		ArrayList<Score> scores = loadScores(fileName);
		scores.add(this);
		scores.sort(new Score());
		try {
			FileHandler.serialize(fileName, scores);
		} catch (Exception e) {
			System.err.println("Error saving scores");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Score> loadScores(String fileName){
		try {
			return (ArrayList<Score>) FileHandler.deserialize(fileName);
		} catch (Exception e) {
			System.err.println("Error loading scores");
			e.printStackTrace();
			return new ArrayList<Score>();
		}
	}
	
}
