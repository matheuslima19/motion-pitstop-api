package org.motion.motion_api.application.services.observer;

import jakarta.mail.MessagingException;

public interface Observer {
    void update(Object data) throws MessagingException;
}
