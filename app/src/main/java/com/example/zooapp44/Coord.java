package com.example.zooapp44;

import android.location.Location;

import androidx.annotation.NonNull;

import com.example.zooapp44.utils.LatLngs;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
//import com.google.common.base.Objects;


//Implementation given from start Code from class
public class Coord {
    public Coord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public final double lat;
    public final double lng;

    public static Coord of(double lat, double lng) {
        return new Coord(lat, lng);
    }

    public static Coord fromLatLng(LatLng latLng) {
        return Coord.of(latLng.latitude, latLng.longitude);
    }

    public LatLng toLatLng() {
        return new LatLng(lat, lng);
    }

    public static Coord fromLocation(Location location) {
        return Coord.of(location.getLatitude(), location.getLongitude());
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return Double.compare(coord.lat, lat) == 0 && Double.compare(coord.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lat, lng);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Coord{lat=%s, lng=%s}", lat, lng);
    }

    public static boolean isInBetween(Coord first,Coord second,Coord between){
        double ac=calcDistance(first,second);
        double ab=calcDistance(first,between);
        double bc= calcDistance(between,second);
        double together=ab+bc;
        if(Math.abs(ac-together) < 1){
            return true;
        }
        /**
        if(calcDistance(first,second)==calcDistance(first,between)+calcDistance(between,second)){
            return true;
        }
         **/
        return false;
    }

    public static double calcDistance(Coord x, Coord y){
        double d_lat = x.lat - y.lat;
        double d_lng = x.lng - y.lng;

        double d_ft_v = d_lat * 363843.57;
        double d_ft_h = d_lng * 307515.50;
        return Math.sqrt(d_ft_v * d_ft_v + d_ft_h * d_ft_h);
    }

    public static List<Coord> interpolate(Coord p1, Coord p2, int n){
        return IntStream.range(0, n)
                .mapToDouble(i -> (double) i / (double) n)
                .mapToObj(t -> Coord.of(
                        p1.lat + (p2.lat - p1.lat) * t,
                        p1.lng + (p2.lng - p1.lng) * t
                )).collect(Collectors.toList());
    }

    public static Coord midPoint(Coord p1, Coord p2){
        return Coord.of((p1.lat + p2.lat) / 2, (p1.lng + p2.lng) / 2);
    }




}