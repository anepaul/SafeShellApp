package com.safeshell.safeshell.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import com.safeshell.safeshell.MainActivity;

/**
 * Created by ANepaul on 4/19/16.
 */
public class AlertDialogFragment extends AppCompatDialogFragment {
    public static final String DIALOG_TITLE = "title";
    public static final String DIALOG_MESSAGE = "message";
    public static final String DIALOG_REQUEST = "request";
    /* initializer */
    public static AlertDialogFragment newInstance(int title, int message, int requestCode) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt(DIALOG_TITLE, title);
        args.putInt(DIALOG_MESSAGE, message);
        args.putInt(DIALOG_REQUEST, requestCode);
        frag.setArguments(args);
        return frag;
    }
    /* creates dialog for the alert */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt(DIALOG_TITLE);
        int msg = getArguments().getInt(DIALOG_MESSAGE);
        final int reqCode = getArguments().getInt(DIALOG_REQUEST);

        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(msg)
                // For positive button, clicking Ok
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity) getActivity()).doPositiveClick(reqCode);
                            }
                        }
                )
                // For negative button, clicking cancel
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity) getActivity()).doNegativeClick(reqCode);
                            }
                        }
                )
                .create();
    }
}
