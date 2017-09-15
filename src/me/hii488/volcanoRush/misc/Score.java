package me.hii488.volcanoRush.misc;

import java.util.Comparator;

public class Score implements Comparator<Score>{
	
	public int score = 0;
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
	
}
