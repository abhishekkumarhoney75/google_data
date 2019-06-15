package honey.google.maps;

import java.util.Arrays;

public class Location {
    private Activity[] activity;

    private String longitudeE7;

    private String accuracy;

    private String latitudeE7;

    private String timestampMs;

    public Activity[] getActivity() {
        return activity;
    }

    public void setActivity(Activity[] activity) {
        this.activity = activity;
    }

    public String getLongitudeE7() {
        return longitudeE7;
    }

    public void setLongitudeE7(String longitudeE7) {
        this.longitudeE7 = longitudeE7;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getLatitudeE7() {
        return latitudeE7;
    }

    public void setLatitudeE7(String latitudeE7) {
        this.latitudeE7 = latitudeE7;
    }

    public String getTimestampMs() {
        return timestampMs;
    }

    public void setTimestampMs(String timestampMs) {
        this.timestampMs = timestampMs;
    }

    @Override
    public String toString() {
        return "honey.google.maps.Location{" +
                "activity=" + Arrays.toString(activity) +
                ", longitudeE7='" + longitudeE7 + '\'' +
                ", accuracy='" + accuracy + '\'' +
                ", latitudeE7='" + latitudeE7 + '\'' +
                ", timestampMs='" + timestampMs + '\'' +
                '}';
    }

}