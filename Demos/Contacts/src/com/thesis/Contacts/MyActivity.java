package com.thesis.Contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class MyActivity extends Activity {

    private LinearLayout root;
    CursorAdapter adapter;
    EditText editText, editText2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        addContentView(root, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        editText = new EditText(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("myandroid", s + " " + start + " " + count + " " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("myandroid", s + " " + start + " " + before + " " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myandroid", s.toString());
                adapter.changeCursor(getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {
                        ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                }, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE \'%" + s.toString() + "%\' OR " + ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE \'%" + s.toString() + "%\'", null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"));
            }
        });

        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        root.addView(editText);

        editText2 = new EditText(this);
        root.addView(editText2);

        Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        }, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");


        c.moveToFirst();

        ListView listView = new ListView(this);
        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(listView);

        adapter = new CursorAdapter(this, c, true) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                TextView view = new TextView(context);
//                Log.d("myandroid", "bind called");
//                view.setTag();

                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor c) {
                String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                Log.d("myandroid", name);
                ((TextView) view).setText(name + "....." + number);
            }
        };

        listView.setAdapter(adapter);
        Log.d("myandroid", "adaptercount " + adapter.getCount());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("myandroid", adapter.getItem(position).toString());
                CursorWrapper cw = (CursorWrapper) adapter.getItem(position);
                final String number = cw.getString(cw.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                // send a call
//                Intent call = new Intent(Intent.ACTION_CALL);
//                call.setData(Uri.parse("tel:" + number));
//                startActivity(call);

                // send a text
//                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
//                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                sendIntent.setData(Uri.parse("sms:" + number));
//                sendIntent.putExtra("sms_body", "hihi");
                Dialog d = new AlertDialog.Builder(MyActivity.this)
                        .setMessage("Are you sure you want to send?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(number, null, editText2.getText().toString(), null, null);

                                editText.setText("");
                                editText2.setText("");
                                Toast.makeText(MyActivity.this, "Text sent!", Toast.LENGTH_SHORT);
                            }
                        })
                        .setCancelable(true)
                        .create();
                d.show();

//                startActivity(sendIntent);

            }
        });
    }
}
