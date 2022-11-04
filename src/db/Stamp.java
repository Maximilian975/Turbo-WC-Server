package db;

public class Stamp {
	public String user;
	public String bathroom;
	public String date;
	public String timeDiff;
	
	public Stamp(String user, String bathroom, String date) {
		this.user = user;
		this.bathroom = bathroom;
		this.date = date;
	}
	
	public Stamp(String user, String bathroom, String date, String timeDiff) {
		this.user = user;
		this.bathroom = bathroom;
		this.date = date;
		this.timeDiff = timeDiff;
	}

}
