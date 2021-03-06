/**
 * Copyright (c) 2015, 玛雅牛［李飞］ (lifei@wellbole.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jfeat.ext.plugin.zbus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
 * @ClassName: MqHandler  
 * @Description: Zbus MQ handler处理器注解  
 * @author 李飞 (lifei@wellbole.com)   
 * @date 2015年8月22日 下午12:04:00
 * @since V1.0.0  
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface MqHandler {
	String value();
}
