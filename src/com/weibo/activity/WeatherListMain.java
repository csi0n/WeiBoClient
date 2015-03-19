/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;
import com.weibo.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.weibo.card.WeatherCard;
import com.weibo.sortview.CharacterParser;
import com.weibo.sortview.ClearEditText;
import com.weibo.sortview.PinyinComparator;
import com.weibo.sortview.SideBar;
import com.weibo.sortview.SideBar.OnTouchingLetterChangedListener;
import com.weibo.sortview.SortAdapter;
import com.weibo.utils.FaceConversionUtil;
import com.weibo.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月7日 上午11:01:29
 * @com.api.example.app
 */
public class WeatherListMain extends BaseActivity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private CharacterParser characterParser;
	private List<WeatherCard> SourceDateList;
	private PinyinComparator pinyinComparator;
	private static final String TAG = "WeatherListMain.java";
	private ImageView close;

	public void findview() {
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		close = (ImageView) findViewById(R.id.example_right);
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar.setTextView(dialog);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_list_main_layout);
		findview();
		listener();
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				SourceDateList = filledData(FaceConversionUtil.weather);
				Collections.sort(SourceDateList, pinyinComparator);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				Log.d(TAG, "jieshu");
				initViews();
			}
		}.execute(null, null, null);
	}

	private List<WeatherCard> filledData(List<WeatherCard> data) {
		List<WeatherCard> mSortList = new ArrayList<WeatherCard>();
		for (int i = 0; i < data.size(); i++) {
			WeatherCard sortModel = new WeatherCard();
			sortModel.setCity(data.get(i).getCity());
			sortModel.setCode(data.get(i).getCode());
			String pinyin = characterParser.getSelling(data.get(i).getCity());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}
		return mSortList;
	}

	public void listener() {
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				try {
					int position = adapter.getPositionForSection(s.charAt(0));
					Log.d(TAG, String.valueOf(position));
					if (position != -1) {
						sortListView.setSelection(position);
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		});
		sortListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Utils.putweathercityandcode(WeatherListMain.this,
						((WeatherCard) adapter.getItem(position)).getCity(),
						((WeatherCard) adapter.getItem(position)).getCode());
				Intent i = new Intent(WeatherListMain.this, WeatherDetail.class);
				i.putExtra("city",
						((WeatherCard) adapter.getItem(position)).getCity());
				i.putExtra("code",
						((WeatherCard) adapter.getItem(position)).getCode());
				WeatherListMain.this.startActivity(i);
			}
		});
		mClearEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	private void initViews() {
		try {
			adapter = new SortAdapter(this, SourceDateList);
			sortListView.setAdapter(adapter);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void filterData(String filterStr) {
		List<WeatherCard> filterDateList = new ArrayList<WeatherCard>();
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (WeatherCard sortModel : SourceDateList) {
				String name = sortModel.getCity();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}
