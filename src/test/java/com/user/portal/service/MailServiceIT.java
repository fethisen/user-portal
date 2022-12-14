package com.user.portal.service;

import com.user.portal.domain.User;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link MailService}.
 */
@QuarkusTest
public class MailServiceIT {
    @Inject
    MockMailbox mailbox;

    @Inject
    MailService mailService;


    @BeforeEach
    void init() {
        mailbox.clear();
    }

    User user() {
        User user = new User();
        user.login = "john";
        user.email = "john.doe@example.com";
        user.langKey = "en";

        return user;
    }

    @Test
    void should_containsActivationInfosWhenCallSendActivationEmail() {
        User user = user();

        mailService.sendActivationEmail(user);

        List<Mail> sent = mailbox.getMessagesSentTo(user.email);
        assertThat(sent).hasSize(1);
        Mail actual = sent.get(0);
        assertThat(actual.getHtml()).contains("Your Togg account has been created, please click on the URL below to activate it:");
        assertThat(actual.getSubject()).isEqualTo("toggSampleApplication account activation is required");
    }

    @Test
    void should_containsActivationInfosWhenCallSendCreationEmail() {
        User user = user();

        mailService.sendCreationEmail(user);

        List<Mail> sent = mailbox.getMessagesSentTo(user.email);
        assertThat(sent).hasSize(1);
        Mail actual = sent.get(0);
        assertThat(actual.getHtml()).contains("Your Togg account has been created, please click on the URL below to access it:");
        assertThat(actual.getSubject()).isEqualTo("toggSampleApplication account activation is required");
    }

    @Test
    void should_containsResetInfosWhenCallSendPasswordResetMail() {
        User user = user();

        mailService.sendPasswordResetMail(user);

        List<Mail> sent = mailbox.getMessagesSentTo(user.email);
        assertThat(sent).hasSize(1);
        Mail actual = sent.get(0);
        assertThat(actual.getHtml()).contains("For your Togg account a password reset was requested, please click on the URL below to reset it:");
        assertThat(actual.getSubject()).isEqualTo("toggSampleApplication password reset");
    }
}
