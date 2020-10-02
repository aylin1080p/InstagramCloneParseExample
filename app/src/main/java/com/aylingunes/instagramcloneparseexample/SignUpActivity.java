package com.aylingunes.instagramcloneparseexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    EditText userNameText , passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
userNameText = findViewById(R.id.sign_up_activity_name_text);
passwordText = findViewById(R.id.sign_up_activity_password_text);
// uygulama her açıldığında username ve password sorması hatadır bunu kontrol etmeliyiz
        ParseUser parseUser = ParseUser.getCurrentUser(); // güncel kullanıcıyı al anlamında
if(parseUser != null) { // eğer parseUser boş değilse direkt feedactiviyi intent et
    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
    startActivity(intent);

}

    }

public  void  signIn (View view){
ParseUser.logInInBackground(userNameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
    @Override
    public void done(ParseUser user, ParseException e) {
        if (e != null){
            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"welcome "+user.getUsername(),Toast.LENGTH_LONG).show();
        // intent etmemiz gerek ve feed activity'e göndermemiz gerekiyor
            Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
            startActivity(intent);


        }
    }
}); //kullanıcı adı şifre ister Logıncallback blok verir


}

public void  signUp (View view) {
    ParseUser user = new ParseUser();
    user.setUsername(userNameText.getText().toString());
    user.setPassword(passwordText.getText().toString());

    user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            if(e!=null) {

                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_LONG).show();            }
                // nereye gidilecekse oraya intent edilmesi gerekiyor aartık burada gidiş yeri
            Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
            startActivity(intent);
        }
    });


}

}