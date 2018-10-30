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
package org.wso2.custom.password.policy.validator.handler;

import org.wso2.custom.password.policy.validator.constants.CustomPasswordPolicyConstants;
import org.wso2.carbon.identity.base.IdentityRuntimeException;
import org.wso2.carbon.identity.core.handler.InitConfig;
import org.wso2.carbon.identity.event.IdentityEventConstants;
import org.wso2.carbon.identity.event.IdentityEventException;
import org.wso2.carbon.identity.event.event.Event;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;
import org.wso2.carbon.identity.mgt.policy.PolicyRegistry;
import org.wso2.carbon.identity.mgt.policy.PolicyViolationException;
import org.wso2.custom.password.policy.validator.custom.policy.CustomPasswordPolicy;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the sample implementation for the custom password policy validator.
 */
public class CustomPasswordPolicyValidationHandler extends AbstractEventHandler {

    @Override
    public void handleEvent(Event event) throws IdentityEventException {

        Map<String, Object> eventProperties = event.getEventProperties();

        String userName = (String) eventProperties.get(IdentityEventConstants.EventProperty.USER_NAME);
        Object credentials = eventProperties.get(IdentityEventConstants.EventProperty.CREDENTIAL);

        PolicyRegistry policyRegistry = new PolicyRegistry();

        // Read the properties from the identity-event.properties file.
        String isEnabled = configs.getModuleProperties().getProperty(CustomPasswordPolicyConstants.PW_POLICY_ENABLE);
        String minLength = configs.getModuleProperties().getProperty(CustomPasswordPolicyConstants.PW_POLICY_MIN_LENGTH);
        String maxLength = configs.getModuleProperties().getProperty(CustomPasswordPolicyConstants.PW_POLICY_MAX_LENGTH);

        if (isEnabled.equals("false")) {
            return;
        }

        String pwCustomPolicy = configs.getModuleProperties().getProperty(CustomPasswordPolicyConstants
                .PW_CUSTOM_POLICY_CLASS);

        try {

            // Here only one password policy is configured. But multiple policies can be configured.
            if (org.apache.commons.lang.StringUtils.isNotBlank(pwCustomPolicy)) {
                // Create the password policy for the given class name.
                CustomPasswordPolicy customPasswordPolicy = (CustomPasswordPolicy) Class.forName
                        (pwCustomPolicy).newInstance();
                HashMap<String, String> customParams = new HashMap<String, String>();
                customParams.put("min.length", minLength);
                customParams.put("max.length", maxLength);
                customPasswordPolicy.init(customParams);
                policyRegistry.addPolicy(customPasswordPolicy);
            }
        } catch (Exception e) {
            throw new IdentityEventException(CustomPasswordPolicyConstants.ErrorMessages
                    .ERROR_CODE_LOADING_PASSWORD_POLICY_CLASSES.getCode(), CustomPasswordPolicyConstants.ErrorMessages
                    .ERROR_CODE_LOADING_PASSWORD_POLICY_CLASSES.getMessage(), e);
        }

        try {
            // Enforce the password policy.
            policyRegistry.enforcePasswordPolicies(credentials.toString(), userName);
        } catch (PolicyViolationException e) {
            throw new IdentityEventException(CustomPasswordPolicyConstants.ErrorMessages
                    .ERROR_CODE_VALIDATING_PASSWORD_POLICY.getCode(), e.getMessage(), e);
        }
    }

    @Override
    public String getName() {

        return "customPasswordPolicy";
    }

    @Override
    public void init(InitConfig configuration) throws IdentityRuntimeException {

        super.init(configuration);
    }
}
