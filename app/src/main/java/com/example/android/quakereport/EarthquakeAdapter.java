package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by s238780 on 27/02/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    /**
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context       The current context. Used to inflate the layout file.
     * @param earthquakes   A List of Earthquake objects to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Earthquake currentQuake = getItem(position);

        //  Find the objects to display by ID
        TextView magnitud =(TextView) listItemView.findViewById(R.id.txt_magnitud);
        TextView location =(TextView) listItemView.findViewById(R.id.txt_location);
        TextView offsetDistance =(TextView) listItemView.findViewById(R.id.txt_offset_distance);
        TextView date =(TextView) listItemView.findViewById(R.id.txt_date);
        TextView time =(TextView) listItemView.findViewById(R.id.txt_time);

        //  retrieve values from the object and set the values in layout
        //      1. Magnitude
        DecimalFormat formatter = new DecimalFormat("0.0");
        magnitud.setText(formatter.format(currentQuake.getMagnitude()));

        //              Set the proper background color on the magnitude circle.
        //              Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitud.getBackground();

        //              Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentQuake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        //      2. Location ("so much distance of" / "location")
        //  TODO this code is not efficient; the division of the location string is done twice !!
        offsetDistance.setText(buildPlaceDetails(currentQuake.getLocation(), "offset"));
        location.setText(buildPlaceDetails(currentQuake.getLocation(), "location"));

        //      3. Date and Time
        date.setText(formatDateTime(currentQuake.getDate(), "date"));
        time.setText(formatDateTime(currentQuake.getDate(), "time"));

        // Return the whole list item layout
        // so that it can be shown in the ListView
        return listItemView;

    } //    End of the @Overrido of Get View


    /*  From the location field split the base location and the distance to it (offset)
        this is needed to divide the location fied in two lines that will have different
        formatting
        @param: location the timestamp in UNIX format
        @param: whatReturn  either "offset" or "baselocation"
    */
    public String buildPlaceDetails(String location, String whatReturn) {
        // declare the strings and initialize to blanks
        String distance = "";
        String basePlace = "";

        //  Some locations do not have a section with the distance, in that case will use the
        //      word "near"
        if (location.contains("of")) {
            int pointInString = location.indexOf("of");

            //  The location has "distance"; therefore divide
            distance = location.substring(0, pointInString + 3);
            basePlace = location.substring(pointInString + 3, location.length());

        } else {
            //  Has no "distance" so will use the formula "Near of"
            distance = "Near the";
            basePlace = location;
        }

        //  The string is already splitted; depending on the parameter either returns the offset
        //      or the location.
        if (whatReturn == "offset") {
            return distance;
        } else if (whatReturn == "location") {
            return basePlace;
        } else {
            return "unexpected location";
        }
    }


    /*  Format the data and the time from UNIX time to String in a selected format
            @param: timeAndDateinUnixFormat the timestamp in UNIX format
            @param: whatReturn  either "date or "time to be returned once formatted
     */
    public String formatDateTime(long timeAndDateinUnixFormat, String whatReturn) {

        if (whatReturn == "date") {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("DD MMM, yyyy");
            String formattedDate = dateFormatter.format(timeAndDateinUnixFormat);
            return formattedDate;
        } else if (whatReturn == "time") {
            SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
            String formattedTime = timeFormatter.format(timeAndDateinUnixFormat);
            return formattedTime;
        } else {
            return "unexpected time";
        }
    }

    /*  Format the data and the time from UNIX time to String in a selected format
        @param: magnitude   the value of the magnitude
        @return: color      The color assigned to that magnitude
    */
    public int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 1:
                magnitudeColorResourceId= R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId= R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId= R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId= R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId= R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId= R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId= R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId= R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId= R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId= R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }




} //    End the class definition
