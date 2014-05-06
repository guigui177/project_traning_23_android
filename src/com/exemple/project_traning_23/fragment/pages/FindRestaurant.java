package com.exemple.project_traning_23.fragment.pages;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_traning_23.R;
import com.exemple.project_traning_23.fragment.AFragment;

public class FindRestaurant extends AFragment implements OnItemClickListener {

	private ListView listresto = null;
	private String[] listeStrings = {"resto 1","resto 2","resto 3"};
	private ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

	@Override
	public int getMenuTitle() {
		return R.string.findrestaurant_title;		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_listresto, container, false);

		listresto = (ListView) v.findViewById(R.id.fragment_listrestaurant_list_listresto);
		listItem.clear();
		
		//  list resto requette get
		HashMap<String, String> map;

		//  restaurant list get  

		map = new HashMap<String, String>();
		map.put("resto_title", "resto name 1");
		map.put("resto_type", "spetialiter indienne ");
		map.put("resto_deff", "Indienne");
		map.put("resto_meal", "menu ...");
		listItem.add(map);

		map = new HashMap<String, String>();
		map.put("resto_title", "resto name 2");
		map.put("resto_type", "spetialiter italienne ");
		map.put("resto_deff", "Italien");
		map.put("resto_meal", "menu ...");
		listItem.add(map);

		SimpleAdapter mSchedule = new SimpleAdapter (getActivity(), listItem, R.layout.fragment_menu_resto,
				new String[] {"resto_title", "resto_type", "resto_deff"}, new int[] {R.id.fragment_listresto_text_restoname, R.id.fragment_listresto_text_typeresto, R.id.fragment_listresto_text_restoresume});
		listresto.setAdapter(mSchedule);		
		listresto.setOnItemClickListener(this);
		return v; 
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
		//on récupère la HashMap contenant les infos de notre item (titre, description, img)
		HashMap<String, String> map2 = (HashMap<String, String>) listresto.getItemAtPosition(position);
		final Dialog dialog = new Dialog(getActivity());

		dialog.setTitle("Sélection Item");
		dialog.setContentView(R.layout.fragment_listresto_expanded);


		TextView resto_name = (TextView) dialog.findViewById(R.id.fragment_listrestoexpande_text_name);
		TextView resto_type = (TextView) dialog.findViewById(R.id.fragment_listrestoexpande_text_type);
		TextView resto_deff = (TextView) dialog.findViewById(R.id.fragment_listrestoexpande_text_deff);
		TextView resto_meal = (TextView) dialog.findViewById(R.id.fragment_listrestoexpande_text_meal);
		Button buttoncreate=(Button)dialog.findViewById(R.id.fragment_listrestoexpande_button_order);

		resto_name.setText(map2.get("resto_title"));
		resto_type.setText(map2.get("resto_type"));
		resto_deff.setText(map2.get("resto_deff"));
		resto_meal.setText(map2.get("resto_meal"));

		buttoncreate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Create metting button" , Toast.LENGTH_SHORT).show();
				dialog.cancel();
			}
		});
		dialog.show();
	}
}
