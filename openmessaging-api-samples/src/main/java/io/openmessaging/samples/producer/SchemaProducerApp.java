/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.openmessaging.samples.producer;

import io.openmessaging.api.MessagingAccessPoint;
import io.openmessaging.api.OMS;
import io.openmessaging.api.OMSBuiltinKeys;
import io.openmessaging.api.OMSProducer;
import io.openmessaging.samples.User;
import java.util.Properties;

public class SchemaProducerApp {

    public static void main(String[] args) {
        //Load and start the vendor implementation from a specific OMS driver URL.
        final MessagingAccessPoint messagingAccessPoint =
            OMS.builder()
                .region("Shanghai")
                .endpoint("127.0.0.1:9876")
                .driver("rocketmq")
                .withCredentials(new Properties())
                .build();

        Properties properties = new Properties();
        properties.setProperty(OMSBuiltinKeys.SERIALLIZER, "io.openmessaging.openmeta.PBSerilizer");
        properties.setProperty(OMSBuiltinKeys.OPEN_META_URL, "localhost:1234");

        OMSProducer<User> producer = messagingAccessPoint.createOMSProducer(properties);
        producer.start();

        User user = new User();
        user.setName("Hello world");
        producer.send(user);

        producer.shutdown();
    }
}
