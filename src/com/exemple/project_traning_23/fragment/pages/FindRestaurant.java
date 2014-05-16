package com.exemple.project_traning_23.fragment.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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

import com.example.project_traning_23.Project_traning_2_3;
import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.model.User;
import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FindRestaurant extends AFragment implements OnItemClickListener {

	private ListView listresto = null;
	private String[] listeStrings = {"resto 1","resto 2","resto 3"};
	//private ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	private List<Map<String, Object>> userlist = new ArrayList<Map<String, Object>>();
	
	@Override
	public int getMenuTitle() {
		return R.string.findrestaurant_title;		
	}

	public void getrestaurant()
	{
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "restaurants/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				System.out.println(response);

				Project_traning_AdaptResponse<User> test = new Project_traning_AdaptResponse<User>();
				userlist = test.adaptToMap(response);

				Map<String, Object> t = new HashMap<String, Object>();
				for(int i = 0; i < userlist.size(); i++)
				{
					t = userlist.get(i);
					Log.d("API", "resto name = " + t.get("name"));
				}	
				
				SimpleAdapter mSchedule = new SimpleAdapter (getActivity(), userlist, R.layout.fragment_menu_resto,
						new String[] {"name", "category", "description"}, new int[] {R.id.fragment_listresto_text_restoname, R.id.fragment_listresto_text_typeresto, R.id.fragment_listresto_text_restoresume});
				listresto.setAdapter(mSchedule);
			}
			
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(getActivity().getApplicationContext(), "requette list users faild " , Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_listresto, container, false);

		listresto = (ListView) v.findViewById(R.id.fragment_listrestaurant_list_listresto);
		userlist.clear();
		getrestaurant();

//		SimpleAdapter mSchedule = new SimpleAdapter (getActivity(), userlist, R.layout.fragment_menu_resto,
//				new String[] {"name", "category", "description"}, new int[] {R.id.fragment_listresto_text_restoname, R.id.fragment_listresto_text_typeresto, R.id.fragment_listresto_text_restoresume});
//		listresto.setAdapter(mSchedule);		
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

		resto_name.setText(map2.get("name"));
		resto_type.setText(map2.get("category"));
		resto_deff.setText(map2.get("description"));
		resto_meal.setText(map2.get("address"));

		buttoncreate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Create metting button" , Toast.LENGTH_SHORT).show();
				dialog.cancel();
			}
		});
		dialog.show();
	}
}
