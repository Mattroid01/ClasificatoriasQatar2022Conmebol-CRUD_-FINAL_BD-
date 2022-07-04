package com.example.mysql.views.registrodepartidosadmin;

import com.example.mysql.data.entity.Partido;

import com.example.mysql.data.service.PartidoService;

import com.example.mysql.views.MainLayout;
import com.vaadin.flow.component.AbstractField;
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

import com.vaadin.flow.data.binder.Binder;

import com.vaadin.flow.router.*;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Registro de Partidos - ADMIN")
@Route(value = "RegistrodePartidos/:partidoID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class RegistrodePartidosADMINView extends Div implements AfterNavigationObserver {

//    @Autowired
//    private BackendService service;

    @Autowired
    private PartidoService partidoService;

    private Grid<Partido> partidos;

    private TextField id_partido = new TextField();
    private TextField jornada = new TextField();
    private DatePicker fecha = new DatePicker();
    private TextField id_local = new TextField();
    private TextField resultado = new TextField();
    private TextField id_visitante = new TextField();
    private TextField id_sede = new TextField();

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Partido> binder;

    public RegistrodePartidosADMINView() {
        setId("master-detail-view");
        // Configure Grid
        partidos = new Grid<>();
        partidos.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        partidos.setHeightFull();
        partidos.addColumn(Partido::getId_partido).setHeader("ID_Partido");
        partidos.addColumn(Partido::getJornada).setHeader("Jornada");
        partidos.addColumn(Partido::getFecha).setHeader("Fecha");
        partidos.addColumn(Partido::getId_local).setHeader("Local");
        partidos.addColumn(Partido::getResultado).setHeader("Resultado");
        partidos.addColumn(Partido::getId_visitante).setHeader("Visitante");
        partidos.addColumn(Partido::getId_sede).setHeader("Sede");


        //when a row is selected or deselected, populate form
        partidos.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

        // Configure Form
        binder = new Binder<>(Partido.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);
        // note that password field isn't bound since that property doesn't exist in
        // Employee

        binder.setBean(new Partido());

        // the grid valueChangeEvent will clear the form too
        cancel.addClickListener(e -> partidos.asSingleSelect().clear());

        save.addClickListener(e -> {
            Partido partido = binder.getBean();
            if ( partidoService.savePartido(partido) > 0) {
                partidos.setItems(partidoService.findAll());
            } else {
                Notification.show("Save error");
            }
        });

        delete.addClickListener(e -> {
            Partido partido = binder.getBean();
            if ( partidoService.deletePartido(partido) > 0) {
                partidos.setItems(partidoService.findAll());
            } else {
                Notification.show("Delete error");
            }
        });

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
        addFormItem(editorDiv, formLayout, id_partido, "id_partido");
        addFormItem(editorDiv, formLayout, jornada, "jornada");
        addFormItem(editorDiv, formLayout, fecha, "fecha");
        addFormItem(editorDiv, formLayout, id_local, "id_local");
        addFormItem(editorDiv, formLayout, resultado, "resultado");
        addFormItem(editorDiv, formLayout, id_visitante, "id_visitante");
        addFormItem(editorDiv, formLayout, id_sede, "id_sede");
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
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(delete, cancel, save);
        editorDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(partidos);
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
        partidos.setItems(partidoService.findAll());
    }

    private void populateForm(Partido value) {
        // Value can be null as well, that clears the form

        //binder.readBean(value); // commented out
        if ( value == null ) {
            value = new Partido();
        }
        binder.setBean(value);

        // The password field isn't bound through the binder, so handle that
//        password.setValue("");
    }
}