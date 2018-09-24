package com.example.david.demoapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImplicitActivity extends AppCompatActivity {

    private static final String ACTION_IMPLICIT = "mk.ukim.finki.mpip.IMPLICIT_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);
        setupAdapter();
    }

    private void setupAdapter() {
        List<ResolveInfo> activities = getActivities();
        RecyclerView mRecyclerView = findViewById(R.id.activity_implicit_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }

    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;

        ActivityHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView;
            mNameTextView.setOnClickListener(this);
        }
        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getPackageManager();
            String appName = mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
            mNameTextView.setTag(appName);
        }

        @Override
        public void onClick(View v) {
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            Intent i = new Intent().setClassName(activityInfo.applicationInfo.packageName,activityInfo.name);
            startActivity(i);
        }
    }

    public class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {

        private final List<ResolveInfo> mActivities;

        ActivityAdapter(List<ResolveInfo> activities) {
            mActivities = activities;
        }
        @NonNull
        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ActivityHolder(view);
        }
        @Override
        public void onBindViewHolder(ActivityHolder activityHolder, int position) {
            ResolveInfo resolveInfo = mActivities.get(position);
            activityHolder.bindActivity(resolveInfo);
        }
        @Override
        public int getItemCount() {
            return mActivities.size();
        }

        public String getItem(int position){
            ResolveInfo resolveInfo = mActivities.get(position);
            return resolveInfo.loadLabel(getPackageManager()).toString();
        }

        public List<String> getItems(){
            List<String> items = new ArrayList<>();
            for(int i=0;i<mActivities.size();i++){
                items.add(getItem(i));
            }
            return items;
        }
    }

    public static Intent newIntent(){
        Intent i = new Intent(ACTION_IMPLICIT);
        return i;
    }

    public List<ResolveInfo> getActivities(){
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent,0);
        Collections.sort(activities, (o1, o2) -> {
            PackageManager pm1 = getPackageManager();
            return String.CASE_INSENSITIVE_ORDER.compare(
                    o1.loadLabel(pm1).toString(),
                    o2.loadLabel(pm1).toString());
        });
        return activities;
    }

    @VisibleForTesting
    List<String> getApps(){
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = getActivities();
        List<String> apps = new ArrayList<>();
        for(ResolveInfo info : activities){
            apps.add(info.loadLabel(pm).toString());
        }
        return apps;
    }


}
