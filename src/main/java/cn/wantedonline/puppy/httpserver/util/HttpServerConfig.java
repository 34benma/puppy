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

import cn.wantedonline.puppy.httpserver.component.HttpResponse;
import cn.wantedonline.puppy.util.Log;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by wangcheng on 2016/10/30.
 */
@Service
public final class HttpServerConfig {
    public static final Logger ALARMLOG  = Log.getLogger("alarm.cn.wantedonline.puppy.server");
    public static final int CORE_PROCESSOR_NUM = Runtime.getRuntime().availableProcessors();
    private static HttpResponse.ContentType respInnerContentType = HttpResponse.ContentType.json;

}
