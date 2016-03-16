package cr.ac.itcr.shopadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btnLogin =
                (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                txtEmail =
                        (EditText) findViewById(R.id.txtEmail);

                txtPassword =
                        (EditText) findViewById(R.id.txtPassword);


               /* String nombre=et1.getText().toString();
                String datos=et2.getText().toString();
                SharedPreferences preferencias=getSharedPreferences("agenda", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString(nombre, datos);
                editor.commit();*/





               Intent i = new Intent(getApplicationContext(),DashboardActivity.class);;
                startActivity(i);

               /* if(txtEmail.getText().toString().equals("test@test.com")
                        && txtPassword.getText().toString().equals("12345"))
                {
                    Intent i = new Intent(getApplicationContext(),DashboardActivity.class);;
                    startActivity(i);
                }
                else {
                    Toast mensajeError = Toast.makeText(getApplicationContext(), "Usuario inválido",
                            Toast.LENGTH_SHORT);
                    mensajeError.show();
                }*/
            }
        });
    }
}
