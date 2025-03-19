package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.core.ApplicationFramework;

public class LoggerFactory {

    public LoggerFactory(){
    }

    public Logger createLogger(String naziv){
        if(naziv.equalsIgnoreCase("consolelogger"))
            return new ConsoleLogger(ApplicationFramework.getInstance().getMessageGenerator());
        if(naziv.equalsIgnoreCase("filelogger"))
            return new FileLogger(ApplicationFramework.getInstance().getMessageGenerator());
        return null;
    }
}
