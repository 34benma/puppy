/*
 * Copyright [2016-2026] wangcheng(wantedonline@outlook.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.wantedonline.puppy.httpserver.util;

import java.util.Map;

/**
 * Created by wangcheng on 2016/10/30.
 */
public class MapUtil {
    private MapUtil(){}

    public static void checkKeyValueLength(Object... keyvalue) {
        if (keyvalue.length % 2 != 0) {
            throw new IllegalArgumentException("keyvalue.length is invalid:" + keyvalue.length);
        }
    }

    public static <K, V> Map<K, V> buildMap(Map<K, V> map, Object... keyvalue) {
        checkKeyValueLength(keyvalue);
        for (int i = 0; i < keyvalue.length; i++) {
            map.put((K) keyvalue[i++], (V) keyvalue[i]);
        }
        return map;
    }
}