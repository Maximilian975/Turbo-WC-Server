package db;

public class Stamp {
	public String bathroom;
	public String date;
	public String timeDiff;
	
	public Stamp(String bathroom, String date) {
		this.bathroom = bathroom;
		this.date = date;
	}
	
	public Stamp(String bathroom, String date, String timeDiff) {
	
		this.bathroom = bathroom;
		this.date = date;
		this.timeDiff = timeDiff;
	}

}
