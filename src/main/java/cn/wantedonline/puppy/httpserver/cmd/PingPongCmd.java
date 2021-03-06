/*
 *  Copyright [2016-2026] wangcheng(wantedonline@outlook.com)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package cn.wantedonline.puppy.httpserver.cmd;

import cn.wantedonline.puppy.httpserver.annotation.Cmd;
import cn.wantedonline.puppy.httpserver.annotation.CmdAuthor;
import cn.wantedonline.puppy.httpserver.annotation.CmdDescr;
import cn.wantedonline.puppy.httpserver.annotation.CmdReturn;
import cn.wantedonline.puppy.httpserver.common.BaseCmd;
import cn.wantedonline.puppy.httpserver.component.HttpRequest;
import cn.wantedonline.puppy.httpserver.component.HttpResponse;
import cn.wantedonline.puppy.httpserver.component.session.Session;
import cn.wantedonline.puppy.httpserver.httptools.JsonUtil;
import cn.wantedonline.puppy.httpserver.httptools.RtnConstants;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *     测试Http请求响应头
 * </pre>
 *
 * @author wangcheng
 * @Sincd V0.1.0 on 16/11/25.
 */
@CmdDescr("Ping-PongCmd,打印Http请求头信息")
@Service
public class PingPongCmd implements BaseCmd {
    @Cmd("打印Http请求信息")
    @CmdReturn({
            ""
    })
    @CmdAuthor("wangcheng")
    public Object pingMyRequest(HttpRequest request, HttpResponse response) throws Exception {
        Session session = request.getSession(true);
        session.setAttribute("hello","this is from session");
        return JsonUtil.getOnlyOKJSON();
    }

    @Cmd("打印Cookie信息")
    @CmdReturn({
            ""
    })
    @CmdAuthor("wangcheng")
    public Object printSessionValue(HttpRequest request, HttpResponse response) throws Exception {
        Session session = request.getSession(false);

        return JsonUtil.getRtnAndDataJsonObject(RtnConstants.OK, session.getAttribute("hello"));
    }
}
