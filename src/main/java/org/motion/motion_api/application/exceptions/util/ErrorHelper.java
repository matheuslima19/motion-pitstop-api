package org.motion.motion_api.application.exceptions.util;

import java.util.ArrayList;
import java.util.List;

public class ErrorHelper {
    public static List<String> getStackTracePersonalizado(StackTraceElement[] stackTraceElements){
        List<String> stackTraceList = new ArrayList<>();
        int stackSize = 2;
        for (int i = 0; i < stackSize; i++) {
            stackTraceList.add(stackTraceElements[i].toString());
        }
        return stackTraceList;
    }
    public static List<String> getStackTracePersonalizado(StackTraceElement[] stackTraceElements, int stackSize){
        List<String> stackTraceList = new ArrayList<>();
        if(stackSize >= stackTraceElements.length) stackSize = stackTraceElements.length -1;
        for (int i = 0; i < stackSize; i++) {
            stackTraceList.add(stackTraceElements[i].toString());
        }
        return stackTraceList;
    }
}
