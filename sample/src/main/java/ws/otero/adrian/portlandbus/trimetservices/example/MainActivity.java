package ws.otero.adrian.portlandbus.trimetservices.example;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import ws.otero.adrian.portlandbus.trimetservices.TrimetServices;
import ws.otero.adrian.portlandbus.trimetservices.routeconfig.RouteConfigResult;
import ws.otero.adrian.portlandbus.trimetservices.routeconfig.TrimetRouteConfig;
import ws.otero.adrian.portlandbus.trimetservices.routeconfig.models.PBRoute;


public class MainActivity extends Activity {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private ProgressDialog mPD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // show loading dialog
        mPD = new ProgressDialog(this);
        mPD.setMessage(getResources().getString(R.string.loading));
        mPD.setIndeterminate(true);
        mPD.setCancelable(false);

        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_row);
        mListView.setAdapter(mAdapter);


        // init trimet services
        TrimetServices.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // request lines and set this activity as the event handler
        TrimetRouteConfig.get().register(this).getAllRoutes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPD.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unregister the activity as a handler
        TrimetRouteConfig.get().unregister(this);
    }

    @Subscribe
    public void onLinesAvailable(RouteConfigResult result) {
        mPD.hide();
        for (PBRoute pbRoute : result.getRoutes()) {
            mAdapter.add(pbRoute.getDescription());
        }
        mAdapter.notifyDataSetChanged();
    }
}
