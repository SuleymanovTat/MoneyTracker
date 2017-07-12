package ru.suleymanovtat.moneytracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.suleymanovtat.moneytracker.api.MoneyTrackerApi;
import ru.suleymanovtat.moneytracker.models.AuthResult;

public class AuthActivity extends Activity {

    private static final int RC_AUTHORIZE_CONTACTS = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        findViewById(R.id.btn_auth).setOnClickListener(view -> authorizeContactsAccess());
    }

    private void authorizeContactsAccess() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(AuthActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_AUTHORIZE_CONTACTS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_AUTHORIZE_CONTACTS) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess() && result.getSignInAccount() != null) {
                final GoogleSignInAccount account = result.getSignInAccount();
                MoneyTrackerApi api = ((MoneyTrackerApp) AuthActivity.this.getApplication()).getApi();
                api.auth(account.getId()).enqueue(new Callback<AuthResult>() {
                    @Override
                    public void onResponse(@NonNull Call<AuthResult> call, @NonNull Response<AuthResult> response) {
                        if (response.isSuccessful()) {
                            AuthResult result = response.body();
                            if (result != null) {
                                ((MoneyTrackerApp) AuthActivity.this.getApplication()).setAuthToken(result.authToken);
                                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(AuthActivity.this, "Ошибка " + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AuthResult> call, @NonNull Throwable t) {
                        Toast.makeText(AuthActivity.this, "Ошибка " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(AuthActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
            }
        }
    }
}
