package com.twu.biblioteca.representations;
import com.sun.xml.internal.ws.util.StringUtils;

public class Option {

    private String code;
    private String optionTitle;
    private String methodName;
    private Class[] parameterTypes;

    public Option(String code, String optionTitle, String methodName, Class[] parameterTypes) {
        this.code = code;
        this.optionTitle = optionTitle;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    public String getCode() {
        return code;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public String getOptionPrintFormat() {
        return "[" + code + "] " + StringUtils.capitalize(optionTitle);
    }
}
