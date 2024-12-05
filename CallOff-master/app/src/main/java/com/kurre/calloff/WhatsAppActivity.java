package com.kurre.calloff;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WhatsAppActivity extends Activity
{
    //String userNames[] = {"Deepshikha", "Mamta", "Trisha", "Nitu", "Soumy", "Sandeep"};
    //String statusMessages[] = {"Angry", "Sleeping", "Good", "Drinking", "Running", "Hungry"};
    int profilePic[] = {R.mipmap.gini1,
            R.mipmap.gini1,
            R.mipmap.gini1,
            R.mipmap.gini1,
            R.mipmap.gini1, R.mipmap.gini1};
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_contact_list);

        lvItems = (ListView) findViewById(R.id.lvContactsList);

        WhatsAppAdapter adapter = new WhatsAppAdapter();
        lvItems.setAdapter(adapter);
    }

    private class WhatsAppAdapter extends BaseAdapter {


        public int getCount() {
           return profilePic.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.contact_list_entry, null);
            //TextView tvName = (TextView) view.findViewById(R.id.tvContactName);
            //TextView tvStatus = (TextView) view.findViewById(R.id.tvContactNumber);
            ImageView ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);

            //tvName.setText(userNames[position]);
            //tvStatus.setText(statusMessages[position]);
            ivProfilePic.setImageResource(profilePic[position]);

            
            ivProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(WhatsAppActivity.this);
                    dialog.setContentView(R.layout.cutom_dialog);

                    ImageView ivProfile = (ImageView) dialog.findViewById(R.id.Profile);
                    Button buttonLoadPicture = (Button) dialog.findViewById(R.id.buttonLoadPicture);
                    ivProfile.setImageResource(profilePic[position]);
                    dialog.show();
                    buttonLoadPicture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Create intent to Open Image applications like Gallery, Google Photos
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            // Start the Intent
                            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

                        }

                        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                            onActivityResult(requestCode, resultCode, data);
                            try {
                                // When an Image is picked
                                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                                        && null != data) {
                                    // Get the Image from data

                                    Uri selectedImage = data.getData();
                                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                                    // Get the cursor
                                    Cursor cursor = getContentResolver().query(selectedImage,
                                            filePathColumn, null, null, null);
                                    // Move to first row
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    imgDecodableString = cursor.getString(columnIndex);
                                    cursor.close();
                                    ImageView Profile = (ImageView) findViewById(R.id.Profile);
                                    // Set the Image in ImageView after decoding the String
                                    Profile.setImageBitmap(BitmapFactory
                                            .decodeFile(imgDecodableString));

                                } else {
                                    Toast.makeText(getApplicationContext(), "You haven't picked Image",
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG)
                                        .show();
                            }
                            //dialog.show();
                        }
                    });

                    //  return view;
                }
            });
            return view;
        }}}