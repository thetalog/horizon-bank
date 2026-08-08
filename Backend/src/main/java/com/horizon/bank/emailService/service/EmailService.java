package com.horizon.bank.emailService.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  /*
     Plain Text Email
  */
  public void sendTextEmail(String to, String subject, String body) {

    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);
  }

  /*
     HTML Email
  */
  public void sendHtmlEmail(String to, String subject, String html) {

    try {

      MimeMessage message = mailSender.createMimeMessage();

      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(html, true);

      mailSender.send(message);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /*
     OTP Email
  */
  public void sendOtp(String to, String otp) {

    String html =
        """
                <div style='font-family:Arial;padding:20px'>
                    <h2 style='color:#1E88E5'>Horizon Bank</h2>

                    <p>Your OTP is</p>

                    <h1 style='
                        letter-spacing:8px;
                        color:#2E7D32'>
                        %s
                    </h1>

                    <p>Valid for 5 minutes.</p>

                    <hr>

                    <small>
                    Do not share this OTP with anyone.
                    </small>

                </div>
                """
            .formatted(otp);

    sendHtmlEmail(to, "OTP Verification", html);
  }

  /*
     Welcome Email
  */
  public void sendWelcomeEmail(String to, String customerName) {

    String html =
        """
                <div style="font-family:Arial;padding:30px">

                    <h1 style="color:#1565C0">
                        Welcome to Horizon Bank
                    </h1>

                    <p>Hello <b>%s</b>,</p>

                    <p>
                        Your account has been created successfully.
                    </p>

                    <p>
                        Thank you for banking with us.
                    </p>

                </div>
                """
            .formatted(customerName);

    sendHtmlEmail(to, "Welcome to Horizon Bank", html);
  }

  /*
     Transaction Alert
  */
  public void sendTransactionEmail(
      String to,
      String customerName,
      String transactionType,
      String amount,
      String accountNumber,
      String balance) {

    String html =
        """
                <div style="font-family:Arial;padding:25px">

                    <h2 style="color:#2E7D32">
                        Transaction Alert
                    </h2>

                    <p>Hello <b>%s</b>,</p>

                    <table cellpadding="8">

                        <tr>
                            <td><b>Type</b></td>
                            <td>%s</td>
                        </tr>

                        <tr>
                            <td><b>Amount</b></td>
                            <td>%s</td>
                        </tr>

                        <tr>
                            <td><b>Account</b></td>
                            <td>%s</td>
                        </tr>

                        <tr>
                            <td><b>Available Balance</b></td>
                            <td>%s</td>
                        </tr>

                    </table>

                    <hr>

                    <small>
                    If you did not perform this transaction,
                    contact Horizon Bank immediately.
                    </small>

                </div>
                """
            .formatted(customerName, transactionType, amount, accountNumber, balance);

    sendHtmlEmail(to, "Transaction Alert", html);
  }

  /*
     Generic Template
  */
  public void sendGenericEmail(String to, String title, String heading, String message) {

    String html =
        """
                <div style="
                    font-family:Arial;
                    padding:30px">

                    <h2 style="color:#1565C0">
                        %s
                    </h2>

                    <p>%s</p>

                    <hr>

                    <small>
                    Horizon Bank
                    </small>

                </div>
                """
            .formatted(heading, message);

    sendHtmlEmail(to, title, html);
  }
}
