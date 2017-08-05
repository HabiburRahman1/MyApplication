package com.example.habib.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends Activity {


    ArrayList<App> appList;
    GridView gridView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        final PackageManager manager = getPackageManager();
        appList = getApps();
        showApps();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent i = manager.getLaunchIntentForPackage(appList.get(arg2).packname
                        .toString());
                AppListActivity.this.startActivity(i);
            }
        });
    }

    private void showApps() {
        // TODO Auto-generated method stub

        gridView = (GridView) findViewById(R.id.gridView1);

        ArrayAdapter<App> adapter = new ArrayAdapter<App>(this, R.layout.grid_item,
                appList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.grid_item,
                            null);
                }

                ImageView appIcon = (ImageView) convertView
                        .findViewById(R.id.imageView1);
                appIcon.setImageDrawable(appList.get(position).icon);

                TextView appName = (TextView) convertView
                        .findViewById(R.id.textView1);
                appName.setText(appList.get(position).appName);

                return convertView;
            }
        };

        gridView.setAdapter(adapter);
    }

    private ArrayList<App> getApps() {
        // TODO Auto-generated method stub
        PackageManager manager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(
                i, 0);
        ArrayList<App> temp = new ArrayList<App>();
        for (ResolveInfo ri : availableActivities) {
            App app = new App();
            app.packname = ri.activityInfo.packageName;
            app.appName = app.packname
                    .substring(app.packname.lastIndexOf(".") + 1);
            app.icon = ri.activityInfo.loadIcon(manager);
            temp.add(app);
        }
        return temp;
    }


}

class App {
    String appName, packname;
    Drawable icon;
}
