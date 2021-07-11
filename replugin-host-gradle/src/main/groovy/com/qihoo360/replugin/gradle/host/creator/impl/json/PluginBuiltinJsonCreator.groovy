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

package com.qihoo360.replugin.gradle.host.creator.impl.json

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.AndroidSourceSet
import com.qihoo360.replugin.gradle.host.AppConstant
import com.qihoo360.replugin.gradle.host.creator.IFileCreator
import groovy.io.FileType
import groovy.json.JsonOutput
import org.gradle.api.NamedDomainObjectContainer

/**
 * @author RePlugin Team
 */
public class PluginBuiltinJsonCreator implements IFileCreator {

    def config
    File fileDir
    def fileName
    def pluginInfos = []

    def PluginBuiltinJsonCreator(def project, def cfg) {
        config = cfg

        NamedDomainObjectContainer<AndroidSourceSet> sourceSets = project.extensions.getByType(AppExtension).getSourceSets()
        fileDir = sourceSets.findByName('main')['assetsDirectories'][0]
        fileName = config.builtInJsonFileName
    }

    @Override
    String getFileName() {
        fileName
    }

    @Override
    File getFileDir() {
        fileDir
    }

    @Override
    String getFileContent() {
        // 查找插件文件并抽取信息
        new File(fileDir.getAbsolutePath() + File.separator + config.pluginDir)
                .traverse(type: FileType.FILES, nameFilter: ~/.*\${config.pluginFilePostfix}/) {

            PluginInfoParser parser = null
            try {
                parser = new PluginInfoParser(it.absoluteFile, config)
            } catch (Exception e) {
                if (config.enablePluginFileIllegalStopBuild) {
                    System.err.println "${AppConstant.TAG} the plugin(${it.absoluteFile.absolutePath}) is illegal !!!"
                    throw new Exception(e)
                }
            }

            if (null != parser) {
                pluginInfos << parser.pluginInfo
            }
        }


        //构建插件们的json信息
        def jsonOutput = new JsonOutput()
        String pluginInfosJson = jsonOutput.toJson(pluginInfos)
        //格式化打印插件们的json信息
        println "${AppConstant.TAG} pluginsInfo=${jsonOutput.prettyPrint(pluginInfosJson)}"

        return pluginInfosJson
    }
}
