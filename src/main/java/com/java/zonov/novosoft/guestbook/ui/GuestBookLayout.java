package com.java.zonov.novosoft.guestbook.ui;

import com.java.zonov.novosoft.guestbook.data.Msg;
import com.java.zonov.novosoft.guestbook.repos.GBRepo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
public class GuestBookLayout extends VerticalLayout {

    private Grid<Msg> grid = new Grid<>();

    @Autowired
    GBRepo repository;

    @PostConstruct
    void init(){
        Label header = new Label("Messages");
        header.addStyleName(ValoTheme.LABEL_BOLD);
        addComponent(header);

        update();
    }

    private void update() {
        setMsgs(repository.findAll());
    }

    private void setMsgs(List<Msg> msgs) {

        grid.removeAllColumns();
        grid.setItems(msgs);
        grid.setHeightByRows(12);
        grid.setWidth("100%");

        grid.addColumn(Msg::getId).setCaption("id").setWidth(100);
        grid.addColumn(Msg::getUname).setCaption("name").setWidth(150);
        grid.addColumn(Msg::getEmail).setCaption("e-mail").setWidth(200);
        grid.addColumn(Msg::getText).setCaption("msg").setWidth(200);
        grid.addColumn(Msg::getIp).setCaption("IP").setWidth(150);
        grid.addColumn(Msg::getBrowser).setCaption("browser").setWidth(800);


        addComponent(grid);
    }

    public void add(Msg msg) {
        repository.save(msg);
        update();
    }

}
