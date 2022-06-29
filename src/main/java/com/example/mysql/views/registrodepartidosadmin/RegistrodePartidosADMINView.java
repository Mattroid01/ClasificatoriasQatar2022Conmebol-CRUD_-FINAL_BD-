package com.example.mysql.views.registrodepartidosadmin;

import com.example.mysql.data.entity.Partido;
import com.example.mysql.data.service.PartidoService;
import com.example.mysql.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Registro de Partidos - ADMIN")
@Route(value = "RegistrodePartidos/:partidoID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class RegistrodePartidosADMINView extends Div implements BeforeEnterObserver {

    private final String PARTIDO_ID = "partidoID";
    private final String PARTIDO_EDIT_ROUTE_TEMPLATE = "RegistrodePartidos/%s/edit";

    private Grid<Partido> grid = new Grid<>(Partido.class, false);

    private TextField id_partido;
    private DatePicker fecha_hora;
    private TextField local;
    private TextField visitante;
    private TextField resultado;
    private TextField id_sede;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Partido> binder;

    private Partido partido;

    private final PartidoService partidoService;

    @Autowired
    public RegistrodePartidosADMINView(PartidoService partidoService) {
        this.partidoService = partidoService;
        addClassNames("registrode-partidos-admin-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("id_partido").setAutoWidth(true);
        grid.addColumn("fecha_hora").setAutoWidth(true);
        grid.addColumn("local").setAutoWidth(true);
        grid.addColumn("visitante").setAutoWidth(true);
        grid.addColumn("resultado").setAutoWidth(true);
        grid.addColumn("id_sede").setAutoWidth(true);
        grid.setItems(query -> partidoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PARTIDO_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(RegistrodePartidosADMINView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Partido.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(id_partido).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("id_partido");
        binder.forField(id_sede).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("id_sede");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.partido == null) {
                    this.partido = new Partido();
                }
                binder.writeBean(this.partido);

                partidoService.update(this.partido);
                clearForm();
                refreshGrid();
                Notification.show("Partido details stored.");
                UI.getCurrent().navigate(RegistrodePartidosADMINView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the partido details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> partidoId = event.getRouteParameters().get(PARTIDO_ID).map(UUID::fromString);
        if (partidoId.isPresent()) {
            Optional<Partido> partidoFromBackend = partidoService.get(partidoId.get());
            if (partidoFromBackend.isPresent()) {
                populateForm(partidoFromBackend.get());
            } else {
                Notification.show(String.format("The requested partido was not found, ID = %s", partidoId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(RegistrodePartidosADMINView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        id_partido = new TextField("Id_partido");
        fecha_hora = new DatePicker("Fecha_hora");
        local = new TextField("Local");
        visitante = new TextField("Visitante");
        resultado = new TextField("Resultado");
        id_sede = new TextField("Id_sede");
        Component[] fields = new Component[]{id_partido, fecha_hora, local, visitante, resultado, id_sede};

        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
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
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Partido value) {
        this.partido = value;
        binder.readBean(this.partido);

    }
}
