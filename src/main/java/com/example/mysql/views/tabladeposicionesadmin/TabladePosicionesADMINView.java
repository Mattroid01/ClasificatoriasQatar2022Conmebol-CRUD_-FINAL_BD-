package com.example.mysql.views.tabladeposicionesadmin;

import com.example.mysql.data.entity.Puntaje;
import com.example.mysql.data.service.PuntajeService;
import com.example.mysql.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Tabla de Posiciones - ADMIN")
@Route(value = "tablaposiciones/:puntajeID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class TabladePosicionesADMINView extends Div implements BeforeEnterObserver {

    private final String PUNTAJE_ID = "puntajeID";
    private final String PUNTAJE_EDIT_ROUTE_TEMPLATE = "tablaposiciones/%s/edit";

    private Grid<Puntaje> grid = new Grid<>(Puntaje.class, false);

    private TextField ranking;
    private TextField pais;
    private TextField pj;
    private TextField g;
    private TextField e;
    private TextField p;
    private TextField gf;
    private TextField gc;
    private TextField dg;
    private TextField pts;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Puntaje> binder;

    private Puntaje puntaje;

    private final PuntajeService puntajeService;

    @Autowired
    public TabladePosicionesADMINView(PuntajeService puntajeService) {
        this.puntajeService = puntajeService;
        addClassNames("tablade-posiciones-admin-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("ranking").setAutoWidth(true);
        grid.addColumn("pais").setAutoWidth(true);
        grid.addColumn("pj").setAutoWidth(true);
        grid.addColumn("g").setAutoWidth(true);
        grid.addColumn("e").setAutoWidth(true);
        grid.addColumn("p").setAutoWidth(true);
        grid.addColumn("gf").setAutoWidth(true);
        grid.addColumn("gc").setAutoWidth(true);
        grid.addColumn("dg").setAutoWidth(true);
        grid.addColumn("pts").setAutoWidth(true);
        grid.setItems(query -> puntajeService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PUNTAJE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(TabladePosicionesADMINView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Puntaje.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(ranking).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("ranking");
        binder.forField(pj).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("pj");
        binder.forField(g).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("g");
        binder.forField(e).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("e");
        binder.forField(p).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("p");
        binder.forField(gf).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("gf");
        binder.forField(gc).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("gc");
        binder.forField(dg).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("dg");
        binder.forField(pts).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("pts");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.puntaje == null) {
                    this.puntaje = new Puntaje();
                }
                binder.writeBean(this.puntaje);

                puntajeService.update(this.puntaje);
                clearForm();
                refreshGrid();
                Notification.show("Puntaje details stored.");
                UI.getCurrent().navigate(TabladePosicionesADMINView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the puntaje details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> puntajeId = event.getRouteParameters().get(PUNTAJE_ID).map(UUID::fromString);
        if (puntajeId.isPresent()) {
            Optional<Puntaje> puntajeFromBackend = puntajeService.get(puntajeId.get());
            if (puntajeFromBackend.isPresent()) {
                populateForm(puntajeFromBackend.get());
            } else {
                Notification.show(String.format("The requested puntaje was not found, ID = %s", puntajeId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(TabladePosicionesADMINView.class);
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
        ranking = new TextField("Ranking");
        pais = new TextField("Pais");
        pj = new TextField("Pj");
        g = new TextField("G");
        e = new TextField("E");
        p = new TextField("P");
        gf = new TextField("Gf");
        gc = new TextField("Gc");
        dg = new TextField("Dg");
        pts = new TextField("Pts");
        Component[] fields = new Component[]{ranking, pais, pj, g, e, p, gf, gc, dg, pts};

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

    private void populateForm(Puntaje value) {
        this.puntaje = value;
        binder.readBean(this.puntaje);

    }
}
