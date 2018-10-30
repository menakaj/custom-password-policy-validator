# wso2-custom-password-validator
Sample implementation of a custom password validator and Password Policy.

### Supported Identity Server version
5.6.0

### Installation Instructions

- Goto ../custom-password-policy-validator.
- Run mvn clean install 
- Copy the custom-password-policy-validator/target/custom-password-validator-1.0.SNAPSHOT.jar to 
<IS_5.6.0_HOME>/repository/components/dropins directory.

### Configure the password validator and the password policy.
- Open the <IS_5.6.0_HOME>/repository/conf/identity/identity.xml file and verify that the following event listener is 
enabled.
```
<EventListener type="org.wso2.carbon.user.core.listener.UserOperationEventListener"
   name="org.wso2.carbon.identity.governance.listener.IdentityMgtEventListener"
   orderId="95" enable="true"/>
   ```
   
- Open <IS_5.6.0_HOME>/repository/conf/identity/identity-event.properties file in a test editor and add the following
 properties.
 
 ```
 module.name.13=customPasswordPolicy
 customPasswordPolicy.subscription.1=PRE_UPDATE_CREDENTIAL
 customPasswordPolicy.subscription.2=PRE_UPDATE_CREDENTIAL_BY_ADMIN
 customPasswordPolicy.subscription.3=PRE_ADD_USER
 customPasswordPolicy.min.length=5
 customPasswordPolicy.max.length=11
 customPasswordPolicy.pattern=^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*])).{0,100}$
 customPasswordPolicy.errorMsg='Password pattern policy violated. Password should contain a digit[0-9], a lower case letter[a-z], an upper case letter[A-Z], one of !@#$%&* characters'
 customPasswordPolicy.class.CustomPasswordPolicy=org.wso2.custom.password.policy.validator.custom.policy
 .CustomPasswordPolicy
 customPasswordPolicy.enable=true
 ```
 
 Save the file and start the server by running
 ```<IS_HOME>/bin/wso2server.sh``` in linux or ```<IS_HOME>/bin/wso2server.bat``` in windows.
