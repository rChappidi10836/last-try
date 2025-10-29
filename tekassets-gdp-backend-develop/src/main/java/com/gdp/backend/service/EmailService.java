package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.repository.ResourceRepository;
import com.gdp.backend.util.CurrentUsernameUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * This Service class used for send and receive the Email.
 * @author gdp
 *
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration config;

    @Autowired
    private ResourceRepository resourceRepository;

    @Value("${mailDisplayEmail}")
    private String mailFrom;

    // @Value("${receiver.mail}")
    // private String mailTo;

    @Value("${receiver.create.mail}")
    private String create_mailTo;

    @Value("#{${receiver.emea.create.mail}}")
    private List<String> emea_mailTo;

    @Value("${receiver.security.mail}")
    private String security_mailTo;

    @Value("${mailDisplayName}")
    private String mailDisplayName;

    @Value("${disable.notifications}")
    private boolean disableNotifications;

    @Value("${restrict.emails}")
    private boolean restrictEmails;

    @Value("#{${allowed.csl.emails}}")
    private List<String> allowedCSLList;

    /**
     * This method would be use for sending the Mail as a notification.
     * @param model is the Map.
     * @param source is the mail source.
     * @param ccList list of MessageHelper.
     * @param isSourceOpportunity is check Source Opportunity is mapped or not.
     * @throws ActionFailureException exception would be throws, if source is not found.
     */
    public void sendEmailUsingTemplate(Map<String, Object> model, String source, String[] ccList, Boolean isSourceOpportunity, boolean isSalesOrgEMEA)
            throws ActionFailureException {
        MimeMessage message = emailSender.createMimeMessage();
        Template t;
        try {
            t = getTemplate(source, isSourceOpportunity);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(getMailSubject(source, isSourceOpportunity));
            if (source.equals(Constants.NEW_PROJECT) || source.equals(Constants.ADD_OPPORTUNITY)) {
                if(isSalesOrgEMEA){
                    helper.setTo(emea_mailTo.toArray(new String[0]));
                }
                else{
                    helper.setTo(create_mailTo);
                }
            } else if (source.equals(Constants.SECURITY_PROFILE)) {
                helper.setTo(security_mailTo);
                helper.setCc(ccList);
            }
            InternetAddress fromAddress = new InternetAddress(mailFrom, mailDisplayName);
            helper.setFrom(fromAddress);
            helper.setText(html, true);
            if (!disableNotifications) {
                emailSender.send(message);
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    /**
     * This method would be use for getting the Mail Template.
     * @param source is the mail source.
     * @param isSourceOpportunity is check Source Opportunity is mapped or not.
     * @return is check Source Opportunity is mapped or not.
     * @throws IOException exception would be throws, if source is not found.
     */
    public Template getTemplate(String source, Boolean isSourceOpportunity) throws IOException {
        Template t = null;
        switch (source) {
            case Constants.NEW_PROJECT:
                t = isSourceOpportunity ? config.getTemplate("email-template-add-opportunity.ftl")
                        : config.getTemplate("email-template-new-project.ftl");
                break;
            case Constants.SECURITY_PROFILE:
                t = config.getTemplate("email-template-security-profile.ftl");
                break;
            case Constants.ADD_OPPORTUNITY:
                t = config.getTemplate("email-template-add-opportunity.ftl");
                break;
            default:
                /* Nothing to be done */
        }
        return t;
    }

    /**
     * This method would be use for getting the Mail Subject.
     * @param source is the mail source.
     * @param isSourceOpportunity is check Source Opportunity is mapped or not.
     * @return this method would be return subject of Mail.
     */
    public String getMailSubject(String source, Boolean isSourceOpportunity) {
        String subject = null;
        switch (source) {
            case Constants.NEW_PROJECT:
                subject = isSourceOpportunity ? Constants.ADD_OPPORTUNITY_MAIL_SUBJECT : Constants.NEW_PROJECT_MAIL_SUBJECT;
                break;
            case Constants.SECURITY_PROFILE:
                subject = Constants.SECURITY_PROFILE_MAIL_SUBJECT;
                break;
            case Constants.ADD_OPPORTUNITY:
                subject = Constants.ADD_OPPORTUNITY_MAIL_SUBJECT;
                break;
            default:
                /* Nothing to be done */
        }
        return subject;
    }

    /**
     * This method would be use for sending the Mail as a notification when we create the Project Manually.
     * @param mailRequestDto is the mail request.
     * @param source is the mail source.
     * @param isSourceOpportunity is check Source Opportunity is mapped or not.
     * @throws ActionFailureException exception would be throws, if source is not found.
     */
    public void sendEmail(MailRequestDto mailRequestDto, String source, Boolean isSourceOpportunity, boolean isSalesOrgEMEA)
            throws ActionFailureException {
        Map<String, Object> model = new HashMap<>();
        model.put("ProjectName", mailRequestDto.getProjectName());
        model.put("AccountName", mailRequestDto.getAccountName());
        model.put("OpportunityId", mailRequestDto.getOpportunityId());
        model.put("GDPId", mailRequestDto.getGdpId());
        model.put("CreatedBy", mailRequestDto.getLastModifiedBy());
        sendEmailUsingTemplate(model, source, null, isSourceOpportunity, isSalesOrgEMEA);
        System.out.println("TEST");
    }

    /**
     * This method would be use for Mapping the Mail data for new Project.
     * @param project is an entity class.
     * @return this method would be return mailRequestDto.
     */
    public MailRequestDto newProjectToMailDtoMapper(CreateProjectFormDto project) {
        MailRequestDto mailRequestDto = new MailRequestDto();
        mailRequestDto.setProjectName(project.getProjectName());
        mailRequestDto.setAccountName(project.getAccountName());
        mailRequestDto.setGdpId(project.getGdpId());
        mailRequestDto.setLastModifiedBy(project.getModifiedBy());
        if (!isEmpty(project.getOpportunityId())) {
            mailRequestDto.setOpportunityId(project.getOpportunityId());
        } else {
            mailRequestDto.setOpportunityId(Constants.NOT_APPLICABLE);
        }
        return mailRequestDto;
    }

    /**
     * This method would be use for sending the Mail notification.
     * @param securitySummaryDtoList is the list of security Summary.
     * @param projectClientNameDto is the project Client.
     * @param modifiedByListOfCSLDto is the list of CSL.
     * @param source is the mail source.
     * @throws ActionFailureException exception would be throws, if source is not found.
     */
    public void setSecurityProfileValues(List<SecuritySummaryDto> securitySummaryDtoList,
                                         ProjectClientNameDto projectClientNameDto, ModifiedByListOfCSLDto modifiedByListOfCSLDto, String source) throws ActionFailureException {
        Map<String, Object> model = new HashMap<>();
        model.put("ProjectName", projectClientNameDto.getProjectName());
        model.put("AccountName", projectClientNameDto.getAccountName());
        model.put("updatedQuestionsList", securitySummaryDtoList);
        model.put("UpdatedBy", modifiedByListOfCSLDto.getModifiedBy());
        String[] emailArray = modifiedByListOfCSLDto.getCSLs();
        List<String> allowedEmailList = new ArrayList<>();
        // if restrictEmails is true then we add CSLs only if it's in the allowedCSLList.
        if(restrictEmails){
            for(String email: emailArray){
                if(allowedCSLList.contains(email)){
                    allowedEmailList.add(email);
                }
            }
        }else{
            allowedEmailList.addAll(Arrays.asList(emailArray));
        }
        sendEmailUsingTemplate(model, source, allowedEmailList.toArray(new String[0]), false,false);
    }

}
