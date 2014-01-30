package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LonelyTwitterActivity extends Activity {

        private static final String FILENAME = "file.sav";
        private EditText bodyText;
        private ListView oldTweetsList;
        private ArrayList<AbstractTweetModel> tweet_models;
        private ArrayAdapter<String> adapter;
        private Gson gson=new Gson();
        
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
                                AbstractTweetModel New_tweet = new ImportantTweetModel(text, new Date(System.currentTimeMillis()));
                                
                                saveInFile(text, new Date(System.currentTimeMillis()),New_tweet);
                                //finish();
                                onStart();

                        }
                });
        }

        @Override
        protected void onStart() {
                // TODO Auto-generated method stub
                super.onStart();
                AbstractTweetModel[] tweets = loadFromFile();
                ArrayList<String> items=new ArrayList<String>();
                for(AbstractTweetModel c : tweets){
                        items.add(new String(c.getTimestamp().toString()+" | "+c.getText()));
                }
                adapter = new ArrayAdapter<String>(this,
                                R.layout.list_item,items.toArray(new String[items.size()]));
                oldTweetsList.setAdapter(adapter);
        }

        private AbstractTweetModel[] loadFromFile() {
                tweet_models = new ArrayList<AbstractTweetModel>();
                try {
                        FileInputStream fis = openFileInput(FILENAME);
                        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                        String line = in.readLine();
                        while (line != null) {
                                tweet_models.add(gson.fromJson(line,ImportantTweetModel.class));
                                line = in.readLine();
                        }
                        fis.close();
                        in.close();
                        
                } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                return tweet_models.toArray(new AbstractTweetModel[tweet_models.size()]);
        }
        
        private void saveInFile(String text, Date date,AbstractTweetModel ltm) {
                try {
                        FileOutputStream fos = openFileOutput(FILENAME,Context.MODE_APPEND);
                        OutputStreamWriter fout = new OutputStreamWriter(fos);
                        fout.write(new String(gson.toJson(ltm)+'\n'));
                        fout.close();
                        fos.close();
                        
                } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
}