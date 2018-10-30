/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations und
 */

package org.wso2.custom.password.policy.validator.constants;

public class CustomPasswordPolicyConstants {

    public static final String PW_POLICY_ENABLE = "customPasswordPolicy.enable";
    public static final String PW_POLICY_MIN_LENGTH = "customPasswordPolicy.min.length";
    public static final String PW_POLICY_MAX_LENGTH = "customPasswordPolicy.max.length";
    public static final String PW_CUSTOM_POLICY_CLASS = "customPasswordPolicy.class" +
            ".CustomPasswordPolicy";

    public enum ErrorMessages {

        ERROR_CODE_LOADING_PASSWORD_POLICY_CLASSES("40001", "Error occurred while loading Password Policies"),
        ERROR_CODE_VALIDATING_PASSWORD_POLICY("40002", "Error while validating password policy");

        private final String code;
        private final String message;

        ErrorMessages(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return code + " - " + message;
        }
    }
}
