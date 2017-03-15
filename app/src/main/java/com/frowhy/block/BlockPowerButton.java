package com.frowhy.block;

import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Block
 * Created by frowhy on 2017/3/15.
 */

public class BlockPowerButton implements IXposedHookLoadPackage {
    private final String PACKAGE_NAME = BlockPowerButton.class.getPackage().getName();

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        final Class<?> phoneWindowManager;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            phoneWindowManager = XposedHelpers.findClass("com.android.server.policy.PhoneWindowManager", lpparam.classLoader);
        } else {
            phoneWindowManager = XposedHelpers.findClass("com.android.internal.policy.impl.PhoneWindowManager", lpparam.classLoader);
        }

        XposedBridge.hookAllMethods(phoneWindowManager, "interceptKeyBeforeQueueing", new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                int keyCode = ((KeyEvent) param.args[0]).getKeyCode();
                if (keyCode == KeyEvent.KEYCODE_POWER) {
                    XSharedPreferences mXsp = new XSharedPreferences(PACKAGE_NAME);

                    Log.d("isBlockPowerButton", "=================" + mXsp.getBoolean("isBlockPowerButton", true));

                    if (mXsp.getBoolean("isBlockPowerButton", true)) {
                        param.setResult(0);
                    }
                }
            }
        });
    }
}
