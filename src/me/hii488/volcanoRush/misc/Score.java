package me.hii488.volcanoRush.misc;

import java.io.Serializable;
import java.util.Comparator;

public class Score implements Comparator<Score>, Serializable{
	
	private static final long serialVersionUID = -7156916902531681012L;
	
	protected int score = 0;
	public int multiplier = 1;
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
		return a.score - b.score;
	}
	
	public void addToScore(int i){
		score += multiplier * i;
	}
	
	public int getScore(){
		return score;
	}
	
	
}
