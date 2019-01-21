package com.java.zonov.novosoft.guestbook.ui;

import com.java.zonov.novosoft.guestbook.data.Msg;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WebBrowser;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.wcs.wcslib.vaadin.widget.recaptcha.ReCaptcha;
import com.wcs.wcslib.vaadin.widget.recaptcha.shared.ReCaptchaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hene.expandingtextarea.ExpandingTextArea;

@SpringUI
public class GuestBookUI extends UI {
    private HorizontalLayout root;
    private static final String PRIVATE_KEY = "6LejuooUAAAAAPgEQgABiPCQV--zSSV5bHS5Aqvb";
    private static final String PUBLIC_KEY = "6LejuooUAAAAAH09ljZEv8akXEcx15CEt1WquUA6";

    @Autowired
    GuestBookLayout guestBookLayout;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        createLayout();
        placeUserBox();
        placeMsgBox();
    }


    private void createLayout() {
        root = new HorizontalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(root);
    }


    private void placeUserBox() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("400");
        Binder<Msg> binder = new Binder<>(Msg.class);

        TextField userName = new TextField();
        userName.setPlaceholder("User Name");
        userName.setRequiredIndicatorVisible(true);
        binder.forField(userName)
                .withValidator(
                        name -> name.length() >= 3,
                        "User name must contain at least three characters")
                .bind(Msg::getUname, Msg::setUname);
        userName.focus();

        TextField eMail = new TextField();
        eMail.setPlaceholder("E-mail");
        eMail.setRequiredIndicatorVisible(true);
        binder.forField(eMail)
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address"))
                .bind(Msg::getEmail, Msg::setEmail);

        ExpandingTextArea textField = new ExpandingTextArea();
        textField.setPlaceholder("Message");
        binder.forField(textField)
                .asRequired("Every Msg must have a text")
                .bind(Msg::getText, Msg::setText);

        ReCaptcha captcha = new ReCaptcha(
                PRIVATE_KEY,
                new ReCaptchaOptions() {{//your options
                    theme = "light";
                    sitekey = PUBLIC_KEY;
                }}
        );

        Button add = new Button("Add");
        add.addClickListener(click -> {
            if (!captcha.validate()) {
                Notification.show("Check CAPTCHA", Notification.Type.ERROR_MESSAGE);
                captcha.reload();
            } else {
                if (binder.isValid()) {
                    WebBrowser wb = Page.getCurrent().getWebBrowser();
                    String sessionId = VaadinSession.getCurrent().getSession().getId();

                    guestBookLayout.add(new Msg(userName.getValue(), eMail.getValue(), textField.getValue(), wb.getAddress(), wb.getBrowserApplication()));
                    captcha.reload();
                } else {
                    Notification.show("Check all required fields", Notification.Type.ERROR_MESSAGE);
                }
                captcha.reload();
                textField.clear();
            }
        });


        Label footer = new Label("* - required fields");
        footer.addStyleName(ValoTheme.LABEL_SMALL);

        verticalLayout.addComponents(userName, eMail, textField, captcha, add, footer);
        root.addComponent(verticalLayout);
    }

    private void placeMsgBox() {
        guestBookLayout.setWidth("900");
        root.addComponent(guestBookLayout);
    }

}
