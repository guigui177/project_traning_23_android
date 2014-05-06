package com.exemple.project_traning_23.fragment.pages;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_traning_23.Project_traning_2_3;
import com.example.project_traning_23.R;
import com.exemple.project_traning_23.fragment.AFragment;

public class NfcReader extends AFragment implements OnClickListener {

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
			/*
			 * See NFC forum specification for "Text Record Type Definition" at 3.2.1 
			 * 
			 * http://www.nfc-forum.org/specs/
			 * 
			 * bit_7 defines encoding
			 * bit_6 reserved for future use, must be 0
			 * bit_5..0 length of IANA language code
			 */

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

*/
}

