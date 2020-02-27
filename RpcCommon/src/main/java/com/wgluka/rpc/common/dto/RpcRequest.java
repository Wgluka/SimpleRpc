package com.wgluka.rpc.common.dto;

import java.util.List;

public class RpcRequest {
    private String targetClass;

    private String targetMethod;

    private Class[] parametersClass;

    private Object[] parameters;

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public Class[] getParametersClass() {
        return parametersClass;
    }

    public void setParametersClass(Class[] parametersClass) {
        this.parametersClass = parametersClass;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
