package com.aylingunes.instagramcloneparseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.GetFileCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
ListView listView;
ArrayList<String> userNamesFromParse;
ArrayList<String> userCommentFromParse;
ArrayList<Bitmap> userImageFromParse;
PostClass postClass;

    //itemleri override et

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // menu bağlama
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);



        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {// menuden bir şey tıklanırsa ne olacak


        if (item.getItemId() == R.id.add_post){

            Intent intent = new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);

        }else if(item.getItemId() == R.id.logout){ // logout işlemi
            ParseUser.logOutInBackground(new LogOutCallback() {


                @Override
                public void done(ParseException e) {

                    if (e != null) {
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }else{

                        Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Good Bye ",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        listView = findViewById(R.id.listView);

        userNamesFromParse = new ArrayList<>();
        userCommentFromParse = new ArrayList<>();
        userImageFromParse = new ArrayList<>();

        postClass = new PostClass(userNamesFromParse,userCommentFromParse,userImageFromParse,this);
        listView.setAdapter(postClass);
    download();

    }

    public void download() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Posts");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                if( e!= null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }else {


                    if(objects.size()>0 ){
                        System.out.println("if size control");
                        for( final ParseObject object: objects){

                            ParseFile parseFile = (ParseFile) object.get("image");
                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if( e == null && data != null){

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);

                                        userImageFromParse.add(bitmap);
                                        userNamesFromParse.add(object.getString("username"));
                                        userCommentFromParse.add(object.getString("comment"));
                                        System.out.println("if e null controlll");
                                       postClass.notifyDataSetChanged();
                                }
                            }


                            });


                        }
                    }



                }
            }
        });

    }



}