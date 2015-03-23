package com.Contacts;

import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.Contacts.utils.JSONContact;

public class ContactsPlugin extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		// TODO Auto-generated method stub
		return super.execute(action, args, callbackContext);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
	}
	
	private Activity getActivity() {
        return this.cordova.getActivity();
    }
	
	public void fetchContactInformation() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        
        List<JSONContact> list = new ArrayList<JSONContact>();
		String id, name, phoneNumber;
        while(cursor.moveToNext()) {
        	JSONContact jc = new JSONContact();
        	
            id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            phoneNumber = "";
            
            jc.setId(id);
            jc.setName(name);
            
            //Fetch Phone Number
            Cursor phoneCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id, null, null);
            while(phoneCursor.moveToNext()) {
                phoneNumber += ";" + phoneCursor.getString(
                        phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            jc.setPhoneNumber(phoneNumber);
            
            phoneCursor.close();
            
        }
        cursor.close();
    }

}
