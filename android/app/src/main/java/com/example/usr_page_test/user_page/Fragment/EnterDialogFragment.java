package com.example.usr_page_test.user_page.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.usr_page_test.R;

public class EnterDialogFragment extends DialogFragment
{
    private EditText mItem;
    private EditText mCost;
    private InputListener context;
    public interface InputListener
    {
       public void onInputComplete(String item, String cost);
    }
    public void setContext(InputListener context) {
        this.context = context;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.enter_diaglog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        mItem = (EditText) view.findViewById(R.id.id_txt_item);
        mCost = (EditText) view.findViewById(R.id.id_txt_cost);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Go!",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                InputListener listener = (InputListener)context;
                                listener.onInputComplete(mItem
                                        .getText().toString(), mCost
                                        .getText().toString());
                            }
                        }).setNegativeButton("Cancel", null);
        return builder.create();
    }
}