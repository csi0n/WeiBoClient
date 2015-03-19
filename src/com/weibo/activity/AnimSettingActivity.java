/**weibotest
 * 
 *
 *TODO
 */
package com.weibo.activity;

import com.weibo.R;
import com.weibo.utils.Utils;
import com.weibo.view.widget.UISwitchButton;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月15日 下午4:41:06
 * @com.api.example.app
 */
public class AnimSettingActivity extends BaseActivity implements OnClickListener {
	ImageView close;
	UISwitchButton ALPHA, FLIP_HORIZON, FLIP_VERTICAL, HORIZION_LEFT, HORIZION_RIGHT, HORIZON_CROSS, ROTATE, SCALE;

	public void findview() {
		close = (ImageView) findViewById(R.id.example_right);
		close.setOnClickListener(this);
		ALPHA = (UISwitchButton) findViewById(R.id.ALPHA);
		FLIP_HORIZON = (UISwitchButton) findViewById(R.id.FLIP_HORIZON);
		FLIP_VERTICAL = (UISwitchButton) findViewById(R.id.FLIP_VERTICAL);
		HORIZION_LEFT = (UISwitchButton) findViewById(R.id.HORIZION_LEFT);
		HORIZION_RIGHT = (UISwitchButton) findViewById(R.id.HORIZION_RIGHT);
		ROTATE = (UISwitchButton) findViewById(R.id.ROTATE);
		SCALE = (UISwitchButton) findViewById(R.id.SCALE);
		HORIZON_CROSS = (UISwitchButton) findViewById(R.id.HORIZON_CROSS);
	}

	public void changebox() {
		switch (Utils.getAnimationTypeNum(AnimSettingActivity.this)) {
			case 1 :
				ALPHA.setChecked(true);
				FLIP_HORIZON.setChecked(false);
				FLIP_VERTICAL.setChecked(false);
				HORIZION_LEFT.setChecked(false);
				HORIZION_RIGHT.setChecked(false);
				HORIZON_CROSS.setChecked(false);
				ROTATE.setChecked(false);
				SCALE.setChecked(false);
				break;
			case 2 :
				ALPHA.setChecked(false);
				FLIP_HORIZON.setChecked(true);
				FLIP_VERTICAL.setChecked(false);
				HORIZION_LEFT.setChecked(false);
				HORIZION_RIGHT.setChecked(false);
				HORIZON_CROSS.setChecked(false);
				ROTATE.setChecked(false);
				SCALE.setChecked(false);
				break;
			case 3 :
				ALPHA.setChecked(false);
				FLIP_HORIZON.setChecked(false);
				FLIP_VERTICAL.setChecked(true);
				HORIZION_LEFT.setChecked(false);
				HORIZION_RIGHT.setChecked(false);
				HORIZON_CROSS.setChecked(false);
				ROTATE.setChecked(false);
				SCALE.setChecked(false);
				break;
			case 4 :
				ALPHA.setChecked(false);
				FLIP_HORIZON.setChecked(false);
				FLIP_VERTICAL.setChecked(false);
				HORIZION_LEFT.setChecked(true);
				HORIZION_RIGHT.setChecked(false);
				HORIZON_CROSS.setChecked(false);
				ROTATE.setChecked(false);
				SCALE.setChecked(false);
				break;
			case 5 :
				ALPHA.setChecked(false);
				FLIP_HORIZON.setChecked(false);
				FLIP_VERTICAL.setChecked(false);
				HORIZION_LEFT.setChecked(false);
				HORIZION_RIGHT.setChecked(true);
				HORIZON_CROSS.setChecked(false);
				ROTATE.setChecked(false);
				SCALE.setChecked(false);
				break;
			case 6 :
				ALPHA.setChecked(false);
				FLIP_HORIZON.setChecked(false);
				FLIP_VERTICAL.setChecked(false);
				HORIZION_LEFT.setChecked(false);
				HORIZION_RIGHT.setChecked(false);
				HORIZON_CROSS.setChecked(true);
				ROTATE.setChecked(false);
				SCALE.setChecked(false);
				break;
			case 7 :
				ALPHA.setChecked(false);
				FLIP_HORIZON.setChecked(false);
				FLIP_VERTICAL.setChecked(false);
				HORIZION_LEFT.setChecked(false);
				HORIZION_RIGHT.setChecked(false);
				HORIZON_CROSS.setChecked(false);
				ROTATE.setChecked(true);
				SCALE.setChecked(false);
				break;
			case 8 :
				ALPHA.setChecked(false);
				FLIP_HORIZON.setChecked(false);
				FLIP_VERTICAL.setChecked(false);
				HORIZION_LEFT.setChecked(false);
				HORIZION_RIGHT.setChecked(false);
				HORIZON_CROSS.setChecked(false);
				ROTATE.setChecked(false);
				SCALE.setChecked(true);
				break;
		}
		ALPHA.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.saveAnimationType(AnimSettingActivity.this, 1);
					changebox();
				}
			}
		});
		FLIP_HORIZON.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.saveAnimationType(AnimSettingActivity.this, 2);
					changebox();
				}
			}
		});
		FLIP_VERTICAL.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.saveAnimationType(AnimSettingActivity.this, 3);
					changebox();
				}
			}
		});
		HORIZION_LEFT.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.saveAnimationType(AnimSettingActivity.this, 4);
					changebox();
				}
			}
		});
		HORIZION_RIGHT.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.saveAnimationType(AnimSettingActivity.this, 5);
					changebox();
				}
			}
		});
		HORIZON_CROSS.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.saveAnimationType(AnimSettingActivity.this, 6);
					changebox();
				}
			}
		});
		ROTATE.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.saveAnimationType(AnimSettingActivity.this, 7);
					changebox();
				}
			}
		});
		SCALE.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Utils.saveAnimationType(AnimSettingActivity.this, 8);
					changebox();
				}
			}
		});
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animisetting_layout_main);
		findview();
		changebox();
		Utils.getInstance().addActivity(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == close) {
			AnimSettingActivity.this.finish();
		}
	}
}
