package luis.proyectofinal.com;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    // Banner
    private AdView adView;

    // FaceBook
    private CallbackManager cM;
    private LoginButton lB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        cM = CallbackManager.Factory.create();

        getFbKeyHash("ycbUZgPskhHV0ft9s8T4TVbLSDo=");

        setContentView(R.layout.activity_main);

        lB = (LoginButton)findViewById(R.id.login_button);

        // Banner
        adView = (AdView)findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);




        lB.registerCallback(cM, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "Inicio de Sesion EXITOSO !", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Inicio de Sesion CANCELADO !", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Inicio de Sesion NO EXITOSO !", Toast.LENGTH_LONG).show();
            }
        });

    }

    // Metodos Facebook
    private void getFbKeyHash(String packageName) {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("KeyHash :" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    // Metodos del Banner
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
    }

}
