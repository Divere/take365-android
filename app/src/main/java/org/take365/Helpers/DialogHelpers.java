package org.take365.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by divere on 31/10/2016.
 */

public class DialogHelpers {

    public static void AskDialog(Context context, String question, DialogInterface.OnClickListener positiveClickListener)
    {
        AskDialog(context, question, positiveClickListener, null);
    }

    public static void AskDialog(Context context, String question, DialogInterface.OnClickListener positiveClickListener, DialogInterface.OnClickListener negativeClickListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(question).setPositiveButton("Продолжить", positiveClickListener)
                .setNegativeButton("Отмена", negativeClickListener).show();
    }

    public static void AlertDialog(Context context, String message, DialogInterface.OnClickListener continueCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton("Продолжить", continueCallback).show();
    }
}
