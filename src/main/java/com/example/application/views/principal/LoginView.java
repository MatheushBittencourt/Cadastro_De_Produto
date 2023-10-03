package com.example.application.views.principal;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Login")
@Route(value = "")
@Uses(Icon.class)
public class LoginView extends Composite<VerticalLayout> {

    public LoginView() {
        addClassName("login");

        H2 header = new H2("Pixau Solutions");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.addClassName("login-form");
        contentLayout.setSizeFull();
        contentLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        contentLayout.setAlignItems(Alignment.CENTER);
        contentLayout.setSpacing(false);
        contentLayout.setWidthFull();
        contentLayout.setHeightFull();
        contentLayout.add(header);

        TextField loginField = new TextField();
        loginField.setLabel("Usuário");
        loginField.setPlaceholder("");
        loginField.setRequired(true);
        loginField.setErrorMessage("Nome Inválido");
        contentLayout.add(loginField);

        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Senha");
        passwordField.setPlaceholder("");
        passwordField.setRequired(true);
        passwordField.setErrorMessage("Senha Inválida");
        contentLayout.add(passwordField);

        Button btn = new Button("Entrar", buttonClickEvent -> {
            String usuario = loginField.getValue();
            String senha = passwordField.getValue();

            if (AutenticacaoService.autenticar(usuario, senha)) {
                UI.getCurrent().navigate("/Produtos");
            } else {
                Notification notification = Notification.show("");

                Icon icon = VaadinIcon.WARNING.create();
                Div text = new Div(new Text("Credenciais inválidas!"));

                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.getElement().setAttribute("aria-label", "Close");
                closeButton.addClickListener(evento -> {
                    notification.close();
                });
                HorizontalLayout layout = new HorizontalLayout(icon, text, closeButton);
                layout.setAlignItems(Alignment.CENTER);

                notification.add(layout);
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            }
        });
        btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btn2 = new Button("Inscrever", buttonClickEvent -> UI.getCurrent().navigate("/Cadastro"));
        btn2.addClickListener((event)-> {
            UI.getCurrent().navigate("/Cadastro");
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(btn, btn2);
        buttonLayout.setSpacing(true);

        contentLayout.add(buttonLayout);

        getContent().add(contentLayout);
    }
}