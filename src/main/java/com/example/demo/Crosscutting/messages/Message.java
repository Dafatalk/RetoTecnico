package com.example.demo.Crosscutting.messages;

import com.example.demo.Crosscutting.helper.ObjectHelper;
import com.example.demo.Crosscutting.helper.StringHelper;
import com.example.demo.Crosscutting.messages.enumeration.MessageLevel;

public class Message {
    
    private MessageLevel level;
    private String Content;
    
    
    
    public Message() {
        setLevel(MessageLevel.FATAL);
        setContent(StringHelper.EMPTY);    
    }
    
    public Message(MessageLevel level, String content) {
        super();
        setLevel(level);
        setContent(content);
    }
    
    public static Message createFatalMessage(final String content) {
        return new Message(MessageLevel.FATAL, content);
    }
    public static Message createErrorMessage(final String content) {
        return new Message(MessageLevel.ERROR, content);
    }
    public static Message createWarningMessage(final String content) {
        return new Message(MessageLevel.WARNING, content);
    }
    public static Message createInfoMessage(final String content) {
        return new Message(MessageLevel.INFO, content);
    }
    public static Message createSuccessMessage(final String content) {
        return new Message(MessageLevel.SUCCESS, content);
    }

    public MessageLevel getLevel() {
        return level;
    }

    public void setLevel(final MessageLevel level) {
        this.level = ObjectHelper.getDefaultIfNull(level, MessageLevel.FATAL);
    }

    public String getContent() {
        return Content;
    }

    public void setContent(final String content) {
        Content = StringHelper.applyTrim(content);
    }
    
    

    
}
