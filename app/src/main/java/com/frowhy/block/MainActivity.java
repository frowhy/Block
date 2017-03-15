package com.frowhy.block;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Block
 * Created by frowhy on 2017/3/15.
 */

public class MainActivity extends Activity {
    private SharedPreferences mSp;
    private SharedPreferences.Editor mSpEditor;
    private ToggleButton mTbBlockPowerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSp = getSharedPreferences(getPackageName() + "_preferences", MODE_WORLD_READABLE);
        mSpEditor = mSp.edit();
        mSpEditor.apply();
        initView();
        initHandle();
    }

    private void initHandle() {
        mTbBlockPowerButton.setChecked(mSp.getBoolean("isBlockPowerButton", true));
        mTbBlockPowerButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSpEditor.putBoolean("isBlockPowerButton", isChecked);
                mSpEditor.commit();

                Log.d("isBlockPowerButton", "----------------" + mSp.getBoolean("isBlockPowerButton", true));
            }
        });
    }

    private void initView() {
        mTbBlockPowerButton = (ToggleButton) findViewById(R.id.tb_block_power_button);
    }
}
