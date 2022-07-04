package com.example.mysql.views.tabladeposicionesadmin;

import com.example.mysql.data.entity.Posicion;
import com.example.mysql.data.service.PosicionService;
import com.example.mysql.views.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;



import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Tabla de Posiciones - ADMIN")
@Route(value = "tablaposiciones/:puntajeID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@CssImport("styles/views/masterdetail/master-detail-view.css")
@RolesAllowed("ADMIN")
public class TabladePosicionesADMINView extends Div implements AfterNavigationObserver {

//    @Autowired
//    private BackendService service;

    @Autowired
    private PosicionService posicionService;

    private Grid<Posicion> posiciones;

    private TextField pos = new TextField();
    private TextField PJ = new TextField();
    private TextField G = new TextField();
    private TextField E = new TextField();
    private TextField P = new TextField();
    private TextField GF = new TextField();
    private TextField GC = new TextField();
    private TextField DG = new TextField();
    private TextField pts = new TextField();

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Posicion> binder;

    public TabladePosicionesADMINView() {
        setId("master-detail-view");
        // Configure Grid
        posiciones = new Grid<>();
        posiciones.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        posiciones.setHeightFull();
        posiciones.addColumn(Posicion::getPos).setHeader("Posición");
        posiciones.addColumn(Posicion::getId_pais).setHeader("País");
        posiciones.addColumn(Posicion::getPJ).setHeader("PJ");
        posiciones.addColumn(Posicion::getG).setHeader("G");
        posiciones.addColumn(Posicion::getE).setHeader("E");
        posiciones.addColumn(Posicion::getP).setHeader("P");
        posiciones.addColumn(Posicion::getGF).setHeader("GF");
        posiciones.addColumn(Posicion::getGC).setHeader("GC");
        posiciones.addColumn(Posicion::getDG).setHeader("DG");
        posiciones.addColumn(Posicion::getPts).setHeader("Puntos");


        //when a row is selected or deselected, populate form
        posiciones.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

        // Configure Form
        binder = new Binder<>(Posicion.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);
        // note that password field isn't bound since that property doesn't exist in
        // Employee

        binder.setBean(new Posicion());

        // the grid valueChangeEvent will clear the form too
        cancel.addClickListener(e -> posiciones.asSingleSelect().clear());

        save.addClickListener(e -> {
            Posicion posicion = binder.getBean();
            if ( posicionService.savePosicion(posicion) > 0) {
                posiciones.setItems(posicionService.findAll());
            } else {
                Notification.show("Save error");
            }
        });
//
//        delete.addClickListener(e -> {
//            Employee employee = binder.getBean();
//            if ( employeeService.deleteEmployee(employee) > 0) {
//                employees.setItems(employeeService.findAll());
//            } else {
//                Notification.show("Delete error");
//            }
//        });

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorDiv = new Div();
        editorDiv.setId("editor-layout");
        FormLayout formLayout = new FormLayout();
        addFormItem(editorDiv, formLayout, pos, "pos");
        addFormItem(editorDiv, formLayout, PJ, "PJ");
        addFormItem(editorDiv, formLayout, G, "G");
        addFormItem(editorDiv, formLayout, E, "E");
        addFormItem(editorDiv, formLayout, P, "P");
        addFormItem(editorDiv, formLayout, GF, "GF");
        addFormItem(editorDiv, formLayout, GC, "GC");
        addFormItem(editorDiv, formLayout, DG, "DG");
        addFormItem(editorDiv, formLayout, pts, "pts");
        createButtonLayout(editorDiv);
        splitLayout.addToSecondary(editorDiv);
    }

    private void createButtonLayout(Div editorDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(cancel, save);
        editorDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(posiciones);
    }

    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        // Lazy init of the grid items, happens only when we are sure the view will be
        // shown to the user
        //employees.setItems(service.getEmployees());
        posiciones.setItems(posicionService.findAll());
    }

    private void populateForm(Posicion value) {

        if ( value == null ) {
            value = new Posicion();
        }
        binder.setBean(value);

    }
}
