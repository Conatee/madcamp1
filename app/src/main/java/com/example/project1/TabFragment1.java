package com.example.project1;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;

public class TabFragment1 extends Fragment {

    private ArrayList<ContactData> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private static final int REQUEST_READ_CONTACTS = 79;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.tab_fragment1, container, false);

        //recyclerview, set adapter
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerAdapter(getActivity(), dataList);

//        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//            }
//        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            dataList = getAllContacts();
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
    }

    private ArrayList getAllContacts() {
        ArrayList<ContactData> contactDataList = new ArrayList<>();
        ContentResolver cr = getActivity().getContentResolver();
        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                ContactsContract.Data.DISPLAY_NAME+","+ContactsContract.Data._ID+" COLLATE LOCALIZED ASC");

        if((c != null ? c.getCount() : 0) > 0) {
            while(c.moveToNext()) {
                ContactData cd = new ContactData("temp", "default", "default");
                // Get ID and name
                String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                cd.setName(name);

                // Get the phone number for targeted ID
                if(c.getInt(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?",
                            new String[]{id}, null);
                    while(phoneCursor.moveToNext()) {
                        String phoneNo = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        cd.setNumber(phoneNo);
                    }
                    phoneCursor.close();
                }

                // Get the photo for targeted ID if exists
                Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(id));
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(getActivity().getContentResolver(), uri);
                cd.setPhoto(BitmapFactory.decodeStream(input));
                cd.photoExists();

                //Get the email for targeted ID if exists
                Cursor emailCursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?",
                        new String[]{id}, null);
                if(emailCursor != null && emailCursor.moveToFirst()) {
                    String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    cd.setEmail(email);
                    cd.emailExists();
                    emailCursor.close();
                }
                else
                    cd.setEmail("None");

                contactDataList.add(cd);
            }
        }
        if(c != null) {
            c.close();
        }

        return contactDataList;
    }

}
