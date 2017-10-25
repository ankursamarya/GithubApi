package com.example.githubusers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.githubusers.adapter.ListAdapter;
import com.example.githubusers.api.RetrofitInterface;
import com.example.githubusers.model.User;
import com.example.githubusers.model.UsersData;
import com.example.githubusers.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    public static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private ListAdapter adapter;

    private EditText searchTextView;

    private List<User> users = new ArrayList<>();
    private PublishSubject<String> publishSubject;
    private Subscription searchSubscriber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new ListAdapter(this, users);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvUserList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        searchTextView = (EditText) findViewById(R.id.searchUser);
        searchTextView.addTextChangedListener(this);
        publishSubject = PublishSubject.create();


    }

    @Override
    protected void onStart() {
        super.onStart();


        searchSubscriber = publishSubject
//                .compose(Util.<String>applySchedulers2())
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String userName) {
                        Log.d(TAG, "call: " + userName);
                        return !TextUtils.isEmpty(userName);
                    }
                }).flatMap(new Func1<String, Observable<UsersData>>() {
                    @Override
                    public Observable<UsersData> call(String userName) {
                        return RetrofitInterface.getInstance().getApiInterface().getUser(userName);

                    }
                })
                .compose(Util.<UsersData>applySchedulers())
                .subscribe(new Subscriber<UsersData>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(UsersData userData) {
                        Log.d(TAG, "onNext: " + userData);
                        users.clear();
                        users.addAll(userData.users);
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (searchSubscriber != null) {
            searchSubscriber.unsubscribe();
        }

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable text) {
        publishSubject.onNext(text.toString());
    }
}
