/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.custom.password.policy.validator.custom.policy;

import org.apache.log4j.Logger;
import org.wso2.carbon.identity.mgt.policy.AbstractPasswordPolicyEnforcer;

import java.util.Map;

public class CustomPasswordPolicy extends AbstractPasswordPolicyEnforcer {

    private int MIN_LENGTH;
    private int MAX_LENGTH;

    private static Logger log = Logger.getLogger(CustomPasswordPolicy.class);

    public void init(Map<String, String> params) {

        if (params.size() > 0) {
            MIN_LENGTH = Integer.parseInt(params.get("min.length"));
            MAX_LENGTH = Integer.parseInt(params.get("max.length"));
        }
    }

    public boolean enforce(Object... args) {

        if (log.isDebugEnabled()) {
            log.debug("Validating password...");
        }

        String password = args[0].toString();

        if (password.length() < MIN_LENGTH) {
            errorMessage = "Password validation failed. Password should have at least " + MIN_LENGTH + " characters.";
            return false;
        } else if (password.length() > MAX_LENGTH) {
            errorMessage = "Password validation failed. Password should have lest than " + MAX_LENGTH + " characters.";
            return false;
        }
        return true;
    }
}
