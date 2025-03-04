package it.roccatello.wows.service;

import java.io.File;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import it.roccatello.wows.config.AppProperties;
import it.roccatello.wows.model.data.EmailData;

@Slf4j
@Service
@Transactional
public class EmailNotificationService {

  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private AppProperties appProperties;

  public void sendEmail(EmailData data) throws MessagingException {
    if (data == null) {
      return;
    }

    final MimeMessage msg = this.javaMailSender.createMimeMessage();
    final MimeMessageHelper helper = new MimeMessageHelper(msg, true);

    helper.setFrom(appProperties.getEmailFrom());

    if (data.getTo() != null && data.getTo().size() > 0) {
      helper.setTo(data.getTo().toArray(new String[] {}));
    }

    if (data.getCc() != null && data.getCc().size() > 0) {
      helper.setCc(data.getCc().toArray(new String[] {}));
    }

    if (data.getBcc() != null && data.getBcc().size() > 0) {
      helper.setBcc(data.getBcc().toArray(new String[] {}));
    }

    helper.setSubject(data.getSubject());

    if (StringUtils.isNotBlank(data.getText())) {
      helper.setText(data.getText());
    }

    if (StringUtils.isNotBlank(data.getHtml())) {
      helper.setText(data.getHtml(), true);
    }

    if (data.getAttachments() != null) {
      for (File f : data.getAttachments()) {
        if (f.exists() && f.isFile()) {
          helper.addAttachment(f.getName(), f);
        }
      }
    }

    javaMailSender.send(msg);
  }

  public void sendQueuedEmails() {
  }

}
