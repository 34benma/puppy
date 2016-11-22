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

package cn.wantedonline.puppy.httpserver.component;

import cn.wantedonline.puppy.util.AssertUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * <pre>
 *  上层业务请求分发器，不同的业务可以继承该类实现特殊的需求
 * </pre>
 *
 * @author wangcheng
 * @since V0.1.0 on 2016/11/18
 */
public class BasePageDispatcher extends AbstractPageDispatcher {
    @Override
    public void init() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (AssertUtil.isNotNull(msg)) {
            HttpRequest request = (HttpRequest) msg;
            String param = request.getParameter("hello");
            System.out.println(param);
            System.out.println(request.getUri());
        }
    }


}