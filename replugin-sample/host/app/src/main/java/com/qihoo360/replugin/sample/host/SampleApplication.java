/*
 * Copyright (C) 2005-2017 Qihoo 360 Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.qihoo360.replugin.sample.host;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.qihoo360.replugin.RePlugin;

/**
 * @author RePlugin Team
 */
public class SampleApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // ======= REPLUGIN =======
        RePlugin.App.attachBaseContext(this);
        // ========================

        // For Tester
        // 支持接收一些Debug相关的广播
        // 注意：仅在Debug环境中使用，请不要用于【发布环境】，以免出现危险
        if (BuildConfig.DEBUG) {
            DebugReceivers.registerReceivers(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // ======= REPLUGIN =======
        RePlugin.App.onCreate();
        // ========================
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        // ======= REPLUGIN =======
        RePlugin.App.onLowMemory();
        // ========================
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        // ======= REPLUGIN =======
        RePlugin.App.onTrimMemory(level);
        // ========================
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // ======= REPLUGIN =======
        RePlugin.App.onConfigurationChanged(newConfig);
        // ========================
    }
}
