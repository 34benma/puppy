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

package cn.wantedonline.puppy.exception;

/**
 * <pre>
 *     {@code @Config}或{@code @AfterBootstrap}配置错误，抛出异常
 * </pre>
 *
 * 引用并doc 迅雷 hujiachao
 *
 * @author 迅雷 hujiachao
 * @author wangcheng
 * @since V0.1.0 on 2016/10/30
 */
public class ServerConfigError extends Error {

    private String errorMessage;

    public ServerConfigError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
