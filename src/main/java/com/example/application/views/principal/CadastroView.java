package com.example.application.views.principal;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import dao.Conexao;
import dao.Pessoa;
import dao.PessoaDAO;

import javax.swing.*;
import java.sql.Date;

@PageTitle("Signin")
@Route(value = "/Cadastro")
@Uses(Icon.class)
public class CadastroView extends Composite<VerticalLayout> {

    public CadastroView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn5 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow5 = new HorizontalLayout();
        H3 h3 = new H3();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        TextField textField = new TextField();
        DatePicker datePicker = new DatePicker();
        EmailField emailField = new EmailField();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        TextField textField2 = new TextField();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        TextField textField3 = new TextField();
        PasswordField textField4 = new PasswordField();
        HorizontalLayout layoutRow4 = new HorizontalLayout();

        Button salvar = new Button("Salvar", buttonClickEvent -> UI.getCurrent().navigate(""));
        salvar.addClickListener((event)-> {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(textField.getValue());
            pessoa.setSobrenome(textField2.getValue());
            pessoa.setUsuario(textField3.getValue());
            pessoa.setSenha(textField4.getValue());
            pessoa.setEmail(emailField.getValue());
            pessoa.setDataNascimento(Date.valueOf(datePicker.getValue()));

            PessoaDAO pessoaDAO = new PessoaDAO(Conexao.conectar());
            pessoaDAO.inserirPessoa(pessoa);

            UI.getCurrent().navigate("");
            Notification notification = Notification.show("");

            Icon icon = VaadinIcon.CHECK_CIRCLE.create();
            Div text = new Div(new Text("Conta criada com Sucesso!"));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(evento -> {
                notification.close();
        });
            HorizontalLayout layout = new HorizontalLayout(icon, text, closeButton);
            layout.setAlignItems(Alignment.CENTER);

            notification.add(layout);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        salvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelar = new Button("Cancelar", buttonClickEvent -> UI.getCurrent().navigate(""));
        cancelar.addClickListener((event)-> {
            UI.getCurrent().navigate("");
            Notification notification = Notification.show("");

            Icon icon = VaadinIcon.WARNING.create();
            Div text = new Div(new Text("Cadastro Cancelado"));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(evento -> {
                notification.close();
        });
            HorizontalLayout layout = new HorizontalLayout(icon, text, closeButton);
            layout.setAlignItems(Alignment.CENTER);

            notification.add(layout);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        // Teste de metodo para estar realizando remoção de um usuario.
        Button excluir = new Button("Excluir", buttonClickEvent -> {
            String nomeUsuarioParaExcluir = textField3.getValue(); // Obtenha o nome de usuário do campo de texto
            String senhaParaExcluir = textField4.getValue(); // Obtenha a senha do campo de texto

            if (!nomeUsuarioParaExcluir.isEmpty() && !senhaParaExcluir.isEmpty()) {
                // Verifique se o usuário e a senha correspondem
                if (AutenticacaoService.autenticar(nomeUsuarioParaExcluir, senhaParaExcluir)) {
                    // O usuário e a senha correspondem, permita a exclusão
                    PessoaDAO pessoaDAO = new PessoaDAO(Conexao.conectar());
                    pessoaDAO.excluirPessoa(nomeUsuarioParaExcluir);

                    Notification notification = Notification.show("");
                    Icon icon = VaadinIcon.CHECK_CIRCLE.create();
                    Div text = new Div(new Text("Usuário excluído com sucesso!"));

                    Button closeButton = new Button(new Icon("lumo", "cross"));
                    closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                    closeButton.getElement().setAttribute("aria-label", "Close");
                    closeButton.addClickListener(evento -> {
                        notification.close();
                    });

                    HorizontalLayout layout = new HorizontalLayout(icon, text, closeButton);
                    layout.setAlignItems(Alignment.CENTER);

                    notification.add(layout);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } else {
                    Notification notification = Notification.show("");

                    Icon icon = VaadinIcon.WARNING.create();
                    Div text = new Div(new Text("Usuário ou Senha Inválidos"));

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
            } else {
                Notification notification = Notification.show("");

                Icon icon = VaadinIcon.WARNING.create();
                Div text = new Div(new Text("Digite um Usuário e Senha"));

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
        excluir.addThemeVariants(ButtonVariant.LUMO_ERROR);

        VerticalLayout layoutColumn6 = new VerticalLayout();
        getContent().setWidthFull();
        getContent().addClassName(Padding.LARGE);
        getContent().setHeightFull();
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.setFlexGrow(1.0, layoutColumn5);
        layoutColumn5.setWidth(null);
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setHeightFull();
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth(null);
        layoutRow5.setWidthFull();
        layoutRow5.addClassName(Gap.MEDIUM);
        h3.setText("Informe seus Dados");
        layoutRow2.setWidthFull();
        layoutRow2.addClassName(Gap.LARGE);
        layoutRow2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth(null);
        textField.setLabel("Nome");
        textField.setWidthFull();
        textField.setRequired(true);
        textField.setErrorMessage("Insira o seu Nome");
        datePicker.setLabel("Data de Nascimento");
        datePicker.setWidthFull();
        textField3.setLabel("Usuário");
        textField3.setWidthFull();
        textField3.setRequired(true);
        textField3.setErrorMessage("Informe o Usuário");
        layoutRow2.setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.setWidth(null);
        textField2.setLabel("Sobrenome");
        textField2.setWidthFull();
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidthFull();
        emailField.setLabel("E-mail");
        emailField.setWidthFull();
        emailField.setRequired(true);
        emailField.setErrorMessage("Insira o E-mail");
        layoutRow3.setFlexGrow(1.0, emailField);
        textField4.setLabel("Senha");
        textField4.setWidthFull();
        textField4.setRequired(true);
        textField4.setErrorMessage("Informe a Senha");
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setAlignSelf(FlexComponent.Alignment.END, salvar);
        layoutRow.setFlexGrow(1.0, layoutColumn6);
        layoutColumn6.setWidth(null);
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn5);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(layoutRow5);
        layoutColumn2.add(h3);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(layoutColumn3);
        layoutColumn3.add(textField);
        layoutColumn3.add(datePicker);
        layoutColumn3.add(emailField);
        layoutRow2.add(layoutColumn4);
        layoutColumn4.add(textField2);
        layoutColumn4.add(layoutRow3);
        layoutRow3.add(textField3);
        layoutColumn4.add(textField4);
        layoutColumn2.add(layoutRow4);
        layoutRow4.add(salvar);
        layoutRow4.add(cancelar);
        layoutRow4.add(excluir);
        layoutRow.add(layoutColumn6);
    }
}