package ru.suleymanovtat.moneytracker.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

import ru.suleymanovtat.moneytracker.MoneyTrackerApp;
import ru.suleymanovtat.moneytracker.R;
import ru.suleymanovtat.moneytracker.api.MoneyTrackerApi;
import ru.suleymanovtat.moneytracker.models.AuthResult;

public class AuthFragment extends Fragment {

    private static final int RC_AUTHORIZE_CONTACTS = 103;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        view.findViewById(R.id.btn_auth).setOnClickListener(view1 -> authorizeContactsAccess());
        return view;
    }

    private void authorizeContactsAccess() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
                getActivity().getSupportLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<AuthResult>() {
                    @Override
                    public Loader<AuthResult> onCreateLoader(int id, Bundle args) {
                        return new AsyncTaskLoader<AuthResult>(getActivity()) {
                            @Override
                            public AuthResult loadInBackground() {
                                try {
                                    MoneyTrackerApi api = ((MoneyTrackerApp) getActivity().getApplication()).getApi();
                                    return api.auth(account.getId()).execute().body();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        };
                    }

                    @Override
                    public void onLoadFinished(Loader<AuthResult> loader, AuthResult result) {
                        if (result != null && result.isSuccess()) {
                            ((MoneyTrackerApp) getActivity().getApplication()).setAuthToken(result.authToken);
                            new Handler().post(() -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maim_container, new MainFragment(), "main").commit());
                        } else
                            Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLoaderReset(Loader<AuthResult> loader) {
                    }
                }).forceLoad();
            } else {
                Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_LONG).show();
            }
        }
    }
}
