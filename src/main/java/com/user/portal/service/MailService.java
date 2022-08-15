package com.user.portal.service;

import com.user.portal.config.ToggProperties;
import com.user.portal.domain.User;
import io.quarkus.mailer.MailTemplate;
import io.quarkus.qute.api.ResourcePath;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service for sending emails.
 */
@ApplicationScoped
public class MailService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    final ToggProperties toggProperties;

    final MailTemplate activationEmail;

    final MailTemplate creationEmail;

    final MailTemplate passwordResetEmail;

    @Inject
    public MailService(
        ToggProperties toggProperties,
        @ResourcePath("mail/activationEmail") MailTemplate activationEmail,
        @ResourcePath("mail/creationEmail") MailTemplate creationEmail,
        @ResourcePath("mail/passwordResetEmail") MailTemplate passwordResetEmail
    ) {
        this.toggProperties = toggProperties;
        this.activationEmail = activationEmail;
        this.creationEmail = creationEmail;
        this.passwordResetEmail = passwordResetEmail;
    }

    public CompletionStage<Void> sendEmailFromTemplate(User user, MailTemplate template, String subject) {
        return template
            .to(user.email)
            .subject(subject)
            .data(BASE_URL, toggProperties.mail.baseUrl)
            .data(USER, user)
            .send()
            .subscribeAsCompletionStage()
            .thenAccept(
                it -> {
                    log.debug("Sent email to User '{}'", user.email);
                }
            );
    }

    public CompletionStage<Void> sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.email);
        return sendEmailFromTemplate(user, activationEmail, "toggSampleApplication account activation is required");
    }

    public CompletionStage<Void> sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.email);
        return sendEmailFromTemplate(user, creationEmail, "toggSampleApplication account activation is required");
    }

    public CompletionStage<Void> sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.email);
        return sendEmailFromTemplate(user, passwordResetEmail, "toggSampleApplication password reset");
    }
}
