TrimetServices for Android
==========================

Trimet Services is a simple and easy to use library to use Portland's [Trimet's web services](http://developer.trimet.org). I'll be improving it in the future, for now it's just a small fraction of what I'm using on [Portland Bus app](http://play.google.com/store/apps/details?id=ws.otero.adrian.portlandbus).


## Include it in your project

Copy the library folder to your project and add this line to your gradle build file:

```
compile project(':trimetservices')
```

## Configuration

You need to add the following line to your `AndroidManifest.xml` file:

```
<meta-data
   android:name="TRIMET_APP_ID"
   android:value="your_app_id" />
```

Of course, you have to add your App ID (you can get it) from Trimet developers site.


## Using it

I've included a sample app. But it's really easy to use. First of all, we need to initialize the `TrimetServices`:

```
// we can pass an activity or any other context to read the app ID from the AndroidManifest.xml file
TrimetServices.init(context);

// ...

// or we can pass it directly
TrimetServices.init("my_app_id");
```

Once we have this service initialized, and since this library uses [Otto](http://square.github.io/otto/) we have to set the activity up as follows:

```
@Override
protected void onResume() {
    super.onResume();

    // request lines and set this activity as the event handler
    TrimetRouteConfig.get().register(this).getAllRoutes();
}

@Override
protected void onDestroy() {
    super.onDestroy();

    // unregister the activity as a handler
    TrimetRouteConfig.get().unregister(this);
}
```

On the `onResume` method we register the activity  as the subscriber, and on the `onDestroy` method we have to unregister it. This way, if the activity gets destroyed we won't be keeping a reference to it and it will be _garbage collected_. As soon as the user comes back to the activity and a new instance is created, thanks to the `register` command on the `onResume` method, this new activity will get the response from the Trimet web services.

To get the results, we have to add a new method (we can name it as we want) with the `@Subscribe` annotation so we get the result there.

```
@Subscribe
public void onLinesAvailable(RouteConfigResult result) {
    mPD.hide();
    for (PBRoute pbRoute : result.getRoutes()) {
        mAdapter.add(pbRoute.getDescription());
    }
    mAdapter.notifyDataSetChanged();
}
```

## Supported request

This library supports `RouteConfig` and `Arrivals` out of the box. This means we can get all the lines, their stops with all their info, and the buses times for those stops.


## Examples

```
// get all the routes
TrimetRouteConfig.get().getAllRoutes();

// get info about one route
TrimetRouteConfig.get().getRoute(100);

// get times for a stop
TrimetArrivals.get().getArrivals("1793");
```
