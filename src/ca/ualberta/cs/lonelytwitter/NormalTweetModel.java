package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

public class NormalTweetModel extends AbstractTweetModel {

	public NormalTweetModel(String text) {
		super(text);
	}

	@Override
	public Date getTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isimportant() {
		// TODO Auto-generated method stub
		return false;
	}
}
