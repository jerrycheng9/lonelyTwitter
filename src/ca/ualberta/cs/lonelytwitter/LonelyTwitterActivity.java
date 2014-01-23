package ca.ualberta.cs.lonelytwitter;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cs.lonelytwitter.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LonelyTwitterActivity extends Activity {

	private Gson gson = new Gson();
	private String json;
	private EditText bodyText;
	private ListView oldTweetsList;
	protected ArrayList<ImportantTweetModel> inputtweets = new ArrayList<ImportantTweetModel>();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				ImportantTweetModel tweetModel = new ImportantTweetModel(text, new Date(System.currentTimeMillis()));
				saveInFile(tweetModel);
				String[] tweets = loadFromFile();
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(LonelyTwitterActivity.this,R.layout.list_item, tweets);
				oldTweetsList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		String[] tweets = loadFromFile();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
		
		
	}

	private String[] loadFromFile() {
		ArrayList<ImportantTweetModel> tweets = new ArrayList<ImportantTweetModel>();
		Type type = new TypeToken<ArrayList<ImportantTweetModel>>(){}.getType();
		ArrayList<ImportantTweetModel> outputtweets = gson.fromJson(json, type);
		for (ImportantTweetModel tweet : outputtweets){
			tweets.add(tweet);
		}
		return tweets.toArray(new String[tweets.size()]);
	}
	
	private void saveInFile(ImportantTweetModel tweetModel) {
			inputtweets.add(tweetModel);
		    json = gson.toJson(inputtweets);
	}
}