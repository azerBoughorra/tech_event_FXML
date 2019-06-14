/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services.implementation;

import edu.esprit.models.Event;
import edu.esprit.models.Participation;
import edu.esprit.models.Session;
import edu.esprit.services.IEventService;
import edu.esprit.services.ServiceUtils;
import edu.esprit.services.exeptions.ComposedIDExeption;
import edu.esprit.utils.ServiceManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EventService extends ServiceUtils implements IEventService {

    @Override
    public Event find(int id) throws ComposedIDExeption {
        Optional<Event> o = findAll().stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        return o.isPresent() ? o.get() : null;

    }

    @Override
    public List<Event> findAll() {
        List<Event> l = new ArrayList<>();
        try {
            ResultSet rs = executeSelect("select * from events where isdeleted=0");
            while (rs.next()) {
                Event ev = new Event(rs.getInt("EVENT_ID_PK"),
                        rs.getString("EVENT_TITLE"), rs.getString("EVENT_DESCRIPTION"), rs.getString("EVENT_PHOTO_URL"),
                        ServiceManager.getInstance().getUserService().find(rs.getInt("EVENT_ORGANISATOR_ID_FK")),
                        ServiceManager.getInstance().getLocationService().find(rs.getInt("EVENT_LOCATION_ID_FK")),
                        ServiceManager.getInstance().getCategoryService().find(rs.getInt("EVENT_CATEGORY_ID_FK")),
                        rs.getDouble("EVENT_PRICE"),
                        ServiceManager.getInstance().getRatingService().findByEvent(rs.getInt("EVENT_ID_PK")),
                        ServiceManager.getInstance().getParticipationService().findByEvent(rs.getInt("EVENT_ID_PK")),
                        ServiceManager.getInstance().getSessionService().findByEvent(rs.getInt("EVENT_ID_PK")),
                        ServiceManager.getInstance().getCommentService().findByEvent(rs.getInt("EVENT_ID_PK"))
                );
                ev.setReports(ServiceManager.getInstance().getReportService().findByEvent(rs.getInt("EVENT_ID_PK")));
                l.add(ev);
            }
        } catch (Exception ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    @Override
    public boolean create(Event obj) {
        String sql = "insert into events (`EVENT_ID_PK`,"
                + "`EVENT_TITLE`,"
                + "`EVENT_DESCRIPTION`,"
                + "`EVENT_PHOTO_URL`,"
                + "`EVENT_ORGANISATOR_ID_FK`,"
                + "`EVENT_LOCATION_ID_FK`,"
                + "`EVENT_CATEGORY_ID_FK`,"
                + "`EVENT_PRICE`,"
                + "`ISDELETED`)"
                + "values ("
                + obj.getId()
                + "," + obj.getTitle()
                + ",'" + obj.getDescription()
                + "','" + obj.getPhotoURL()
                + "'," + obj.getOrganisator().getId()
                + "," + obj.getLocation().getId()
                + ",'" + obj.getCategory().getId()
                + ",'" + obj.getPrice()
                + "',0"
                + ")";

        boolean r = execute(sql);

        for (Participation p : obj.getParticipations()) {
            if (!ServiceManager.getInstance().getParticipationService().create(p)) {
                r = false;
            }
        }
        for (Session p : obj.getSessions()) {
            if (!ServiceManager.getInstance().getSessionService().create(p)) {
                r = false;
            }
        }

        return r;
    }

    @Override
    public boolean edit(Event obj) {
        String req = "UPDATE events"
                + "SET"
                + "`EVENT_DESCRIPTION` = '" + obj.getDescription() + ","
                + "`EVENT_TITLE` = '" + obj.getTitle() + ","
                + "`EVENT_PHOTO_URL` = '" + obj.getPhotoURL() + ","
                + "' WHERE `EVENT_ID_PK` ='" + obj.getId() + "'";

        return execute(req);
    }

    @Override
    public boolean delete(Event obj) {
        return execute("update events set isdeleted='1' where EVENT_ID_PK=" + obj.getId());
    }

}
