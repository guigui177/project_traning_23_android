package com.exemple.project_traning_23.fragment.pages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.TagTechnology;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_traning_23.Project_traning_2_3;
import com.example.project_traning_23.R;
import com.exemple.project_traning_23.fragment.AFragment;

public class NfcReader extends AFragment implements OnClickListener  {

	NfcAdapter adapter;
	PendingIntent pendingIntent;
	IntentFilter writeTagFilters[];
	boolean writeMode;
	Tag mytag;
	Context ctx;
	TextView message;


	@Override
	public int getMenuTitle() {
		return R.string.nfc_title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nfc_reader, container, false);
		ctx = getActivity().getApplicationContext();

		v.findViewById(R.id.button_layout_nfc).setOnClickListener(this);
		v.findViewById(R.id.button_layout_nfc_format).setOnClickListener(this);
		v.findViewById(R.id.button_layout_nfc_write).setOnClickListener(this);
		message = (TextView)v.findViewById(R.id.edittext_layout_nfc_write);

		adapter = NfcAdapter.getDefaultAdapter(this.getActivity());
		pendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(this.getActivity(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		writeTagFilters = new IntentFilter[] { tagDetected };
		
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

	private void write(String text, Tag tag) throws IOException, FormatException {

		NdefRecord[] records = { createRecord(text) };
		NdefMessage  message = new NdefMessage(records);
		// Get an instance of Ndef for the tag.
		Ndef ndef = Ndef.get(tag);
		// Enable I/O
		ndef.connect();
		// Write the message
		ndef.writeNdefMessage(message);
		// Close the connection
		ndef.close();
	}
	
	private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
		String lang       = "en";
		byte[] textBytes  = text.getBytes();
		byte[] langBytes  = lang.getBytes("US-ASCII");
		int    langLength = langBytes.length;
		int    textLength = textBytes.length;
		byte[] payload    = new byte[1 + langLength + textLength];

		// set status byte (see NDEF spec for actual bits)
		payload[0] = (byte) langLength;

		// copy langbytes and textbytes into payload
		System.arraycopy(langBytes, 0, payload, 1,              langLength);
		System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

		NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);

		return recordNFC;
	}


	@Override
	protected void onNewIntent(Intent intent){
		if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
			mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);    
			Toast.makeText(ctx, this.getString(R.string.ok_detection) + mytag.toString(), Toast.LENGTH_LONG ).show();
		}
	}

	@Override
	public void onPause(){
		super.onPause();
		WriteModeOff();
	}

	@Override
	public void onResume(){
		super.onResume();
		WriteModeOn();
	}

	private void WriteModeOn(){
		writeMode = true;
		adapter.enableForegroundDispatch(getActivity(), pendingIntent, writeTagFilters, null);
	}

	private void WriteModeOff(){
		writeMode = false;
		adapter.disableForegroundDispatch(getActivity());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.button_layout_nfc_write :	
			try {
				if(mytag==null){
					Toast.makeText(ctx, ctx.getString(R.string.error_detected), Toast.LENGTH_LONG ).show();
				}else{
					write(message.getText().toString(),mytag);
					Toast.makeText(ctx, ctx.getString(R.string.ok_writing), Toast.LENGTH_LONG ).show();
				}
			} catch (IOException e) {
				Toast.makeText(ctx, ctx.getString(R.string.error_writing), Toast.LENGTH_LONG ).show();
				e.printStackTrace();
			} catch (FormatException e) {
				Toast.makeText(ctx, ctx.getString(R.string.error_writing) , Toast.LENGTH_LONG ).show();
				e.printStackTrace();
			}
			break;
		}
	}
}





















/*
	public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String TAG = "NfcDemo";
	private TextView textmsg;
	private NfcAdapter mNfcAdapter;

	@Override
	public int getMenuTitle() {
		return R.string.nfc_title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_nfc_reader, container, false);
		v.findViewById(R.id.button_layout_nfc).setOnClickListener(this);
		v.findViewById(R.id.button_layout_nfc_format).setOnClickListener(this);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
		textmsg = (TextView) v.findViewById(R.id.text_layout_nfc);
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.button_layout_nfc:
			if (initNfc())
			{
				statReading();
			}
			break;
		case R.id.button_layout_nfc_format:
		//	start_format();
			break;
		}
	}


	// reading
	public boolean initNfc()
	{

		if (mNfcAdapter == null) {
			// Stop here, we definitely need NFC
			textmsg.setText( "This device doesn't support NFC.");
			return false;
		}
		if (!mNfcAdapter.isEnabled()) {
			textmsg.setText("NFC is disabled. please enable it.");
		}

		return true;
	}

	public void statReading(){
		textmsg.setText("Start reading ...");
		new NdefReaderTask().execute();
	}

	private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

		@Override
		protected String doInBackground(Tag... params) {
			Tag tag = params[0];

			Ndef ndef = Ndef.get(tag);
			if (ndef == null) {
				// NDEF is not supported by this Tag. 
				return null;
			}

			NdefMessage ndefMessage = ndef.getCachedNdefMessage();

			NdefRecord[] records = ndefMessage.getRecords();
			for (NdefRecord ndefRecord : records) {
				if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
					try {
						return readText(ndefRecord);
					} catch (UnsupportedEncodingException e) {
						Log.e(TAG, "Unsupported Encoding", e);
					}
				}
			}

			return null;
		}

		private String readText(NdefRecord record) throws UnsupportedEncodingException {

			byte[] payload = record.getPayload();

			// Get the Text Encoding
			String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

			// Get the Language Code
			int languageCodeLength = payload[0] & 0063;

			// String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
			// e.g. "en"

			// Get the Text
			return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				textmsg.setText("Read content: " + result);
			}
		}
	}

 */
// format
/*
	public static Tag createMockTag(byte[] id, int[] techList, Bundle[] techListExtras) {
	    // set serviceHandle to 0 to indicate mock tag
	    return new Tag(id, techList, techListExtras, 0, null);
	}

	public void start_format()
	{

		NdefFormatable formatable=NdefFormatable.get(tag);
		if (formatable != null) {
			try {
				formatable.connect();

				try {
					formatable.format(msg);
				}
				catch (Exception e) {
					Toast.makeText(getActivity(), "the tag refused to format", Toast.LENGTH_SHORT).show();
				}
			}
			catch (Exception e) {
				Toast.makeText(getActivity(), "the tag refused to connect", Toast.LENGTH_SHORT).show();
			}
			finally {
				formatable.close();
			}
		}
		else {
			Toast.makeText(getActivity(), "the tag cannot be formatted", Toast.LENGTH_SHORT).show();
		}
	}
}
 */

