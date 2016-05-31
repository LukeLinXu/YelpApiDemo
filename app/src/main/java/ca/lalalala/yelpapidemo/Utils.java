package ca.lalalala.yelpapidemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by llin on 2016-05-31.
 */
public class Utils {

    public static void showDialog(Context context, String message, String title, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.ok, onClickListener);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
