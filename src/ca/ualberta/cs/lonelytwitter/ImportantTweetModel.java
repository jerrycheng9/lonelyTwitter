package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

public class ImportantTweetModel extends AbstractTweetModel {
	public ImportantTweetModel(String text, Date timestamp) {
		super(text, timestamp);
	}
	public ImportantTweetModel(String text) {
		super(text);
	}
	public String getText(){
		return "Important! " + super.getText();
	}
	@Override
	public Date getTimestamp() {
		return super.timestamp;
	}
	@Override
	public boolean isimportant() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
