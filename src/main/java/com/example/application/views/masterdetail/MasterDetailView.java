package com.example.application.views.masterdetail;

import com.example.application.data.entity.SamplePerson;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.Conexao;
import dao.Produto;
import dao.ProdutoDao;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Produtos")
@Route(value = "/Produtos", layout = MainLayout.class)
@Uses(Icon.class)
public class MasterDetailView extends Div implements BeforeEnterObserver {

    //private final String SAMPLEPERSON_ID = "samplePersonID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "master-detail/%s/edit";

    private final Grid<Produto> grid = new Grid<>(Produto.class, false);

    private TextField id;
    private TextField modelo;
    private TextField marca;
    private TextField quantidade;
    private DatePicker date;
    private TextField tipo;

    private final Button delete = new Button("Deletar");
    private final Button save = new Button("Salvar");

    private final Button update = new Button("Atualizar");

    private final BeanValidationBinder<SamplePerson> binder;

    private SamplePerson samplePerson;


    public MasterDetailView() {
        addClassNames("master-detail-view");

        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
        ProdutoDao produtoDao = new ProdutoDao(Conexao.conectar());

        grid.addColumn("id").setHeader("ID").setAutoWidth(true);
        grid.addColumn("modelo").setHeader("Modelo").setAutoWidth(true);
        grid.addColumn("marca").setHeader("Marca").setAutoWidth(true);
        grid.addColumn("quantidade").setHeader("Quantidade").setAutoWidth(true);
        grid.addColumn("data").setHeader("Data de Recebimento").setAutoWidth(true);
        grid.addColumn("tipo").setHeader("Tipo").setAutoWidth(true);


        var lista=produtoDao.obterTodosProdutos();
        grid.setItems(lista);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MasterDetailView.class);
            }
        });

        binder = new BeanValidationBinder<>(SamplePerson.class);

        binder.bindInstanceFields(this);

        List<Integer> idsExcluidosComSucesso = new ArrayList<>();

        delete.addClickListener(e -> {
            try {
                int idProdutoParaExcluir = Integer.parseInt(id.getValue());

                if (idsExcluidosComSucesso.contains(idProdutoParaExcluir)) {
                    Notification.show("Este produto já foi excluído anteriormente");
                } else {
                    // Verifica se o ID existe na lista de produtos
                    boolean produtoExistente = false;
                    for (Produto produto : produtoDao.obterTodosProdutos()) {
                        if (produto.getId() == idProdutoParaExcluir) {
                            produtoExistente = true;
                            break;
                        }
                    }

                    if (produtoExistente) {
                        Produto produto = new Produto();
                        produto.setId(idProdutoParaExcluir);

                        // Método para deletar o produto
                        produtoDao.deletarProduto(produto);

                        // Adicione o ID à lista de IDs excluídos
                        idsExcluidosComSucesso.add(idProdutoParaExcluir);

                        // Atualiza a tabela após a remoção do produto
                        clearForm();
                        grid.setItems(produtoDao.obterTodosProdutos());
                        refreshGrid();

                        // Exibe uma notificação de sucesso
                        Notification notification = Notification.show("");

                        Icon icon = VaadinIcon.WARNING.create();
                        Div text = new Div(new Text("Produto Deletado"));

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
                    } else {
                        Notification notification = Notification.show("");
                        Icon icon = VaadinIcon.WARNING.create();
                        Div text = new Div(new Text("O produto com o ID informado não existe"));

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
                }
            } catch (NumberFormatException ex) {
                Notification notification = Notification.show("");
                Icon icon = VaadinIcon.WARNING.create();
                Div text = new Div(new Text("Insira um ID válido"));

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
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);


        update.addClickListener((event)-> {
            try {
                int idProdutoParaAtualizar = Integer.parseInt(id.getValue());
                String novoModelo = modelo.getValue();
                String novaMarca = marca.getValue();
                int novaQuantidade = Integer.parseInt(quantidade.getValue());
                String novoTipo = tipo.getValue();
                Date novaData = Date.valueOf(date.getValue());

                Produto produtoAtualizado = new Produto(idProdutoParaAtualizar, novoModelo, novaMarca, novaQuantidade, novoTipo, novaData);

                produtoDao.atualizarProduto(produtoAtualizado);

                clearForm();
                grid.setItems(produtoDao.obterTodosProdutos());
                refreshGrid();

                Notification notification = Notification.show("");

                Icon icon = VaadinIcon.CHECK_CIRCLE.create();
                Div text = new Div(new Text("Produto Atualizado"));

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
            } catch (NumberFormatException ex) {
                Notification notification = Notification.show("");
                Icon icon = VaadinIcon.WARNING.create();
                Div text = new Div(new Text("Insira valores válidos"));

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
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        update.addThemeVariants(ButtonVariant.LUMO_SUCCESS);



        save.addClickListener(e -> {
            try {
                if (this.samplePerson == null) {
                    this.samplePerson = new SamplePerson();
                }
                Produto produto = new Produto();
                produto.setModelo(modelo.getValue());
                produto.setMarca(marca.getValue());
                produto.setQuantidade(Integer.parseInt(quantidade.getValue()));
                produto.setTipo(tipo.getValue());
                produto.setData(Date.valueOf(date.getValue()));

                produtoDao.adicionarProduto(produto);

                binder.writeBean(this.samplePerson);
                clearForm();
                grid.setItems(produtoDao.obterTodosProdutos());
                refreshGrid();
                Notification notification = Notification.show("");

                Icon icon = VaadinIcon.CHECK_CIRCLE.create();
                Div text = new Div(new Text("Salvo"));

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
                UI.getCurrent().navigate(MasterDetailView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Erro ao Salvar Alterações.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Falha no Cadastro");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // Não apagar o metodo pois se não da erro no vaadin ao rodar
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        /*
        Optional<Long> samplePersonId = event.getRouteParameters().get(SAMPLEPERSON_ID).map(Long::parseLong);
        if (samplePersonId.isPresent()) {
            Optional<SamplePerson> samplePersonFromBackend = samplePersonService.get(samplePersonId.get());
            if (samplePersonFromBackend.isPresent()) {
                populateForm(samplePersonFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %s", samplePersonId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(MasterDetailView.class);
            }
        }*/
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        id = new TextField("ID");
        id.setPlaceholder("Não Necessário para Salvar");
        modelo = new TextField("Modelo");
        marca = new TextField("Marca");
        quantidade = new TextField("Quantidade");
        date = new DatePicker("Data de Recebimento");
        tipo = new TextField("Tipo");
        formLayout.add(id, modelo, marca, quantidade, date, tipo);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, delete,update);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(SamplePerson value) {
        this.samplePerson = value;
        binder.readBean(this.samplePerson);

    }
}
