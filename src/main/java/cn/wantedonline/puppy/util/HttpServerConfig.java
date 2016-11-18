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

package cn.wantedonline.puppy.util;

import cn.wantedonline.puppy.httpserver.component.AbstractPageDispatcher;
import cn.wantedonline.puppy.spring.annotation.Config;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *     Http服务器配置信息，可以在serverconfig.properties修改配置
 * </pre>
 *
 * @author wangcheng
 * @since V0.1.0 on 2016/11/16
 */
@Service
public final class HttpServerConfig {
    public static final int PROCESSOR_NUM = Runtime.getRuntime().availableProcessors();

    @Config(resetable = true)
    private int listen_port = 8080;
    @Config
    private int work_thread_num = 0;
    @Config
    private int maxInitialLineLength = 4096;
    @Config
    private int maxHeaderSize = 8192;
    @Config
    private int maxChunkSize = 8192;
    @Config
    private String cmdSuffix = "Cmd";
    @Config
    private String cmdDefaultMethod = "process";

    @Autowired
    private AbstractPageDispatcher dispatcher;

    private NioEventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1, new NamedThreadFactory("PuppyServer:NIO boss thread $", Thread.MAX_PRIORITY));
    private NioEventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(work_thread_num <= 0 ? PROCESSOR_NUM*2 : work_thread_num, new NamedThreadFactory("PuppyServer:NIO worker thread $",Thread.NORM_PRIORITY+4));
    private ChannelInitializer httpServerHandler = new HttpServerHandler();

    public NioEventLoopGroup getBossEventLoopGroup() {
        return bossEventLoopGroup;
    }

    public NioEventLoopGroup getWorkerEventLoopGroup() {
        return workerEventLoopGroup;
    }

    public int getListenPort() {
        return listen_port;
    }

    public ChannelInitializer getHttpServerHandler() {
        return httpServerHandler;
    }

    public String getCmdSuffix() {
        return cmdSuffix;
    }

    public String getCmdDefaultMethod() {
        return cmdDefaultMethod;
    }

    private class HttpServerHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline cp = ch.pipeline();
            cp.addLast("http_request_decoder",new HttpRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize))
              .addLast("http_response_encoder", new HttpResponseEncoder())
              .addLast("pageDispatcher", dispatcher);
        }
    }

    public void stopEventLoopGroup() {
        workerEventLoopGroup.shutdownGracefully();
        bossEventLoopGroup.shutdownGracefully();
    }
}
