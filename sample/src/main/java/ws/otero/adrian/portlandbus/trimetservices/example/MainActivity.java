package ws.otero.adrian.portlandbus.trimetservices.example;

import android.app.Activity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_row);
        mListView.setAdapter(mAdapter);


        // init trimet services
        TrimetServices.init(this);
        // request lines and set this activity as the event handler
        TrimetRouteConfig.get().register(this).getAllRoutes();
    }


    @Override
    protected void onDestroy() {
        // unregister the activity as a handler
        TrimetRouteConfig.get().unregister(this);

        super.onDestroy();
    }

    @Subscribe
    public void onLinesAvailable(RouteConfigResult result) {
        for (PBRoute pbRoute : result.getRoutes()) {
            mAdapter.add(pbRoute.getDescription());
        }
        mAdapter.notifyDataSetChanged();
    }
}
