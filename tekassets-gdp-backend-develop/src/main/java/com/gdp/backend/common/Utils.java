package com.gdp.backend.common;

import com.gdp.backend.enums.DeliveryRiskIndicator;
import com.gdp.backend.enums.ProjectOverAllStatus;
import com.gdp.backend.model.GDPUser;
import com.gdp.backend.model.Opco;
import com.gdp.backend.model.Resource;
import com.gdp.backend.repository.GDPUserRepository;
import com.gdp.backend.repository.ProjectsRepository;
import com.gdp.backend.service.GDPUserDetailsService;
import com.gdp.backend.util.AzureADUtils;
import com.gdp.backend.util.CurrentUsernameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    @Autowired
    private CurrentUsernameUtil currentUsernameUtil;

    @Autowired
    private GDPUserRepository gdpUserRepository;
    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private GDPUserDetailsService gdpUserDetailsService;

    @PersistenceContext
    EntityManager entityManager;

    public Utils(CurrentUsernameUtil currentUsernameUtil, GDPUserRepository gdpUserRepository, ProjectsRepository projectsRepository) {
        this.currentUsernameUtil = currentUsernameUtil;
        this.gdpUserRepository = gdpUserRepository;
        this.projectsRepository = projectsRepository;
    }

    public static String getFullName(String firstName, String middleName, String lastName) {
        String fullName = Stream.of(firstName, middleName, lastName)
                .filter(name -> !StringUtils.isEmpty(name))
                .collect(Collectors.joining(" "));
        return fullName;
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(date);
    }

    public static String getLastUpdatedAtFormatted(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm aaa");
        return formatter.format(date);
    }

    public static Date getDate(String dateString) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
        } catch (ParseException e) {
            try{
                date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            }catch (ParseException ex){
                ex.printStackTrace();
            }
        }
        return date;
    }

    public static Date getDateFromLastUpdatedAt(String dateString) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("MM/dd/yyyy - HH:mm aaa").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            try{
                date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            }catch (ParseException ex){
                ex.printStackTrace();
            }
        }
        return date;
    }

    public static String getStatusForFrontend(String statusFromDatabase) {
        String statusForFrontend = null;
        if (("Red").equalsIgnoreCase(statusFromDatabase)) {
            statusForFrontend = ProjectOverAllStatus.RED.getStatusForFrontend();
        } else if (("Yellow").equalsIgnoreCase(statusFromDatabase)) {
            statusForFrontend = ProjectOverAllStatus.YELLOW.getStatusForFrontend();
        } else if (("Green").equalsIgnoreCase(statusFromDatabase)) {
            statusForFrontend = ProjectOverAllStatus.GREEN.getStatusForFrontend();
        }
        return statusForFrontend;
    }

    public static String getRiskIndicatorValueFromColor(String riskIndicatorValue){
        if (("red").equalsIgnoreCase(riskIndicatorValue)) {
            riskIndicatorValue = DeliveryRiskIndicator.HIGH.getMessage();
        } else if (("orange").equalsIgnoreCase(riskIndicatorValue)) {
            riskIndicatorValue = DeliveryRiskIndicator.MODERATE.getMessage();
        } else if (("green").equalsIgnoreCase(riskIndicatorValue)) {
            riskIndicatorValue = DeliveryRiskIndicator.LOW.getMessage();
        }
        return  riskIndicatorValue;
    }

    public static String usernameFromEmail(String email){
        return email.split("@")[0];
    }

    public boolean areOpcoIdsEqual(Integer projectId){
        String Resource_OpcoId = gdpUserDetailsService.getUserOpcoId();
        String Project_OpcoId = projectsRepository.findOpcoId(projectId);
        String username = currentUsernameUtil.getCurrentUsername();
        Query domain_query = entityManager.createNativeQuery(Queries.GET_OPCO_DOMAINS);
        domain_query.setParameter(1, Resource_OpcoId);
        String[] domains=domain_query.getSingleResult().toString().split(",");
        Query query = entityManager.createNativeQuery(Queries.GET_USERNAME_FROM_GDPUSER_TABLE, GDPUser.class);
        Optional<GDPUser> user=null;
        for(String i:domains) {
            query.setParameter(1, username + "@"+i);
             user = query.getResultList().stream().findFirst();
            if(user.isPresent()){
                break;
            }
        }

        if(user.get().getRoles().getRole().equals("Executive") || user.get().getRoles().getRole().equals("Admin"))
            return true;
        return Resource_OpcoId.equals(Project_OpcoId);
    }


}
