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
 *
 */

package com.qihoo360.replugin.gradle.plugin.manifest

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BasePlugin
import com.android.build.gradle.internal.TaskManager
import org.gradle.api.Project

/**
 * @author RePlugin Team
 */
public class ManifestAPI {

    def private static IManifest sManifestAPIImpl

    def static getActivities(Project project, String variantDir) {
        if (sManifestAPIImpl == null) {
            sManifestAPIImpl = new ManifestReader(manifestPath(project, variantDir))
        }
        sManifestAPIImpl.activities
    }

    /**
     * 获取 AndroidManifest.xml 路径
     * @return
     */
    def static private manifestPath(Project project, String variantDir) {

        AppPlugin appPlugin = project.plugins.getPlugin(AppPlugin)
        // taskManager 在 2.1.3 中为 protected 访问类型的，在之后的版本为 private 访问类型的，
        // 使用反射访问
        TaskManager taskManager = BasePlugin.metaClass.getProperty(appPlugin, 'taskManager')
        def globalScope = taskManager.globalScope;

        File xmlPath = new File(globalScope.getIntermediatesDir(),
                '/manifests/full/' + variantDir + '/AndroidManifest.xml')

        // 检测文件是否存在
        if (!xmlPath.exists()) {
            println 'AndroidManifest.xml not exist'
        }
        println "AndroidManifest.xml 路径：$xmlPath"

        xmlPath.absolutePath
    }
}
