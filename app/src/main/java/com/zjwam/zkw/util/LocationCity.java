package com.zjwam.zkw.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import com.zjwam.zkw.customview.BasicDialog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class LocationCity {
    private LocationManager locationManager;
    private String city;
    private Context activity;

    public LocationCity(Context activity) {
        this.activity = activity;
    }

    public String updateVersion(Location location) {
        String cityName = "";
        List<Address> addList = null;
        Geocoder ge = new Geocoder(activity, Locale.getDefault());
        try {
            addList = ge.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addList != null && addList.size() > 0) {
            for (int i = 0; i < addList.size(); i++) {
                Address ad = addList.get(i);
                cityName = ad.getSubAdminArea();
            }
        }
        return cityName;
    }


    public String getLocation() {
        Location location = getLastKnownLocation();
        if (location != null) {
            city= updateVersion(location);
        }else {
            city = "点我";
        }
        return city;
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        if(providers.contains(LocationManager.GPS_PROVIDER)){
            //如果是GPS

        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){
            //如果是Network

        }else{
            final BasicDialog basicDialog = new BasicDialog(activity,"位置服务已关闭,打开后重试!");
            basicDialog.show();
            basicDialog.setDialog(new BasicDialog.BasicDialogListener() {
                @Override
                public void confirm() {
                    basicDialog.dismiss();
                }

                @Override
                public void cancel() {
                    basicDialog.dismiss();
                }
            });
            return null;
        }
        Location bestLocation = null, location;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        for (String provider : providers) {
            location = locationManager.getLastKnownLocation(provider);
            if (location == null) {
                continue;
            }
            if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = location;
            }
        }
        return bestLocation;
    }
}
