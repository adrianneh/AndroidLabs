package android.labs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {
    protected static final String ACTIVITY_NAME = "WeatherForecast";

    ProgressBar pb;
    TextView currentText;
    TextView minText;
    TextView maxText;
    ImageView weatherPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather_forecast);
        currentText = findViewById(R.id.currTemp);
        minText = findViewById(R.id.minTemp);
        maxText = findViewById(R.id.maxTemp);
        weatherPic = findViewById(R.id.weatherImage);

        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        ForecastQuery forecast = new ForecastQuery();
        String site = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        forecast.execute(site);
    }


    public class ForecastQuery extends AsyncTask<String, Integer, String>{

        private String currTemp;
        private String minTemp;
        private String maxTemp;
        private String iconName;
        private Bitmap img;


        @Override
        public String doInBackground(String ...sURL) {
            InputStream in;
            try {
                URL url = new URL(sURL[0]);
                //create new connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                in = conn.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();
               // parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, "UTF-8");
                //parser.nextTag();
                //String text = null;
               // int event = parser.getEventType();

                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    String name = parser.getName();
                    switch (parser.getEventType()){
                        case XmlPullParser.START_TAG:
                        //text = parser.getText();
                        if (name.equals("temperature")){
                            currTemp = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            minTemp = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxTemp = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        }else if (name.equals("weather")){
                            iconName = parser.getAttributeValue(null, "icon");
                            String iconFile = iconName+".png";
                           if (fileExistence(iconFile)){
                                FileInputStream fis = null;
                                try {
                                    fis = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));   }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                img = BitmapFactory.decodeStream(fis);
                                Log.i(ACTIVITY_NAME, "Image already exists");
                            } else {
                               URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                               img = getImage(iconUrl);
                               FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                               img.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                               outputStream.flush();
                               outputStream.close();
                               Log.i(ACTIVITY_NAME, "Adding new image");
                           }
                            Log.i(ACTIVITY_NAME, "file name="+iconFile);
                            publishProgress(100);
                        }
                    }parser.next();
                }
                //return readFeed(parser);
            }catch(Exception e){
                return e.toString();
            }
            return null;
        }

        public boolean fileExistence(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

        public void onProgressUpdate(Integer ...value){
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(value[0]);
        }


        public void onPostExecute(String result){
            currentText.setText(currentText.getText() + currTemp+"C");
            minText.setText(minText.getText() + minTemp+"C");
            maxText.setText(maxText.getText() + maxTemp+"C");
            weatherPic.setImageBitmap(img);

        }
    }
}
