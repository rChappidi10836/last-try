package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.common.Utils;
import com.gdp.backend.dto.*;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.mapper.MilestonesMapper;
import com.gdp.backend.model.AgileSprintMilestones;
import com.gdp.backend.model.DeliveryMilestoneType;
import com.gdp.backend.model.DeliveryMilestones;
import com.gdp.backend.model.Project;
import com.gdp.backend.repository.AgileSprintMilestonesRepository;
import com.gdp.backend.repository.DeliveryMilestonesRepository;
import com.gdp.backend.repository.DeliveryMilestonesTypeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This Service class used for retrieving and saving the information related to the Milestones data.
 * @author gdp
 *
 */
@Service
public class MilestonesService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    CreateAndEditProjectService createAndEditProjectService;

    @Autowired
    DeliveryMilestonesRepository deliveryMilestonesRepository;

    @Autowired
    AgileSprintMilestonesRepository agileSprintMilestonesRepository;

    @Autowired
    DeliveryMilestonesTypeRepository deliveryMilestonesTypeRepository;

    /**
     * This Service would be use for getting Milestones related information using projectId.
     * @param projectId is the id of existing Project.
     * @param weekEndingDate is the week ending Date.
     * @param pageNumber this is the number of current page.
     * @param pageSize this is the number of items which would be populate.
     * @return this method would be return milestoneReadOnlyDto.
     */
    public MilestoneReadOnlyDto getMilestoneByProjectId(int projectId, String weekEndingDate, int pageNumber, int pageSize) {
        MilestonesReadOnlyRequestDto milestonesReadOnlyRequestDto = new MilestonesReadOnlyRequestDto(projectId, pageNumber, pageSize);
        MilestoneReadOnlyDto milestoneReadOnlyDto = new MilestoneReadOnlyDto();
        milestoneReadOnlyDto.setProjectId(milestonesReadOnlyRequestDto.getProjectId());
        milestoneReadOnlyDto.setDeliveryMilestoneDto(getDeliveryMilestoneByProjectId(milestonesReadOnlyRequestDto, weekEndingDate));
        milestoneReadOnlyDto.setAgileSprintMilestoneDto(getAgileSprintMilestoneByProjectId(milestonesReadOnlyRequestDto, weekEndingDate));
        milestoneReadOnlyDto.setDefaultMilestone(getDefaultMilestone(milestoneReadOnlyDto.getDeliveryMilestoneDto().getDeliveryMilestoneReadOnlyDtoList(),
                milestoneReadOnlyDto.getAgileSprintMilestoneDto().getAgileSprintMilestonesReadOnlyDtoList()));
        return milestoneReadOnlyDto;
    }

    /**
     * This method would be use for getting the default Milestones related information.
     * @param deliveryMilestoneReadOnlyDtoList is the list of DeliveryMilestoneReadOnlyDto.
     * @param agileSprintMilestonesReadOnlyDtoList is the list of AgileSprintMilestoneReadOnlyDto.
     * @return this method would be return default Milestone data.
     */
    public String getDefaultMilestone(List<DeliveryMilestoneReadOnlyDto> deliveryMilestoneReadOnlyDtoList,
                                      List<AgileSprintMilestoneReadOnlyDto> agileSprintMilestonesReadOnlyDtoList) {
        String defaultMilestone = Constants.DELIVERY_MILESTONE;
        if (CollectionUtils.isEmpty(deliveryMilestoneReadOnlyDtoList) && !CollectionUtils.isEmpty(agileSprintMilestonesReadOnlyDtoList)) {
            defaultMilestone = Constants.AGILE_SPRINT_MILESTONE;
        } else if (!CollectionUtils.isEmpty(deliveryMilestoneReadOnlyDtoList) && !CollectionUtils.isEmpty(agileSprintMilestonesReadOnlyDtoList)) {
            List<DeliveryMilestoneReadOnlyDto> dummy1 = new ArrayList<>(deliveryMilestoneReadOnlyDtoList);
            List<AgileSprintMilestoneReadOnlyDto> dummy2 = new ArrayList<>(agileSprintMilestonesReadOnlyDtoList);
            sortDeliveryListByLastUpdatedAt(dummy1);
            sortAgileSprintListByLastUpdatedAt(dummy2);
            String lastModifiedDeliveryMilestone = dummy1.get(0).getLastUpdatedAt();
            String lastModifiedAgileSprintMilestone = dummy2.get(0).getLastUpdatedAt();
            defaultMilestone = getLastUpdatedMilestone(lastModifiedDeliveryMilestone, lastModifiedAgileSprintMilestone);
        }
        return defaultMilestone;
    }

    /**
     * This method would be use for sorting the deliveryList using lastUpdatedAt field.
     * @param list is the list of DeliveryMilestoneReadOnlyDto.
     * @return this method would be return list of delivery Milestone ReadOnlyDto.
     */
    public List<DeliveryMilestoneReadOnlyDto> sortDeliveryListByLastUpdatedAt(List<DeliveryMilestoneReadOnlyDto> list) {
        Collections.sort(list, (o1, o2) -> {
            if (o1.getLastUpdatedAt() == null || o2.getLastUpdatedAt() == null)
                return 0;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy - HH:mm aaa");
            try {
                return sdf.parse(o2.getLastUpdatedAt()).compareTo(sdf.parse(o1.getLastUpdatedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });
        return list;
    }

    /**
     * This method would be use for sorting the AgileSprint using lastUpdatedAt field.
     * @param list is the list of AgileSprintMilestoneReadOnlyDto.
     * @return this method would be return list of delivery AgileSprint ReadOnlyDto.
     */
    public List<AgileSprintMilestoneReadOnlyDto> sortAgileSprintListByLastUpdatedAt(List<AgileSprintMilestoneReadOnlyDto> list) {
        Collections.sort(list, (o1, o2) -> {
            if (o1.getLastUpdatedAt() == null || o2.getLastUpdatedAt() == null)
                return 0;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy - HH:mm aaa");
            try {
                return sdf.parse(o2.getLastUpdatedAt()).compareTo(sdf.parse(o1.getLastUpdatedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });
        return list;
    }

    /**
     * This method would be use for getting Milestone details using LastUpdatedMilestone field.
     * @param lastModifiedDeliveryMilestone is the last Modified Delivery Milestone data.
     * @param lastModifiedAgileSprintMilestone is the last Modified Agile Sprint Milestone data.
     * @return this method would be return default Milestone.
     */
    public String getLastUpdatedMilestone(String lastModifiedDeliveryMilestone, String lastModifiedAgileSprintMilestone) {
        String defaultMilestone = Constants.DELIVERY_MILESTONE;
        if ((Utils.getDateFromLastUpdatedAt(lastModifiedAgileSprintMilestone)).compareTo(Utils.getDateFromLastUpdatedAt(lastModifiedDeliveryMilestone)) > 0) {
            // When Date Agile > Date Delivery
            defaultMilestone = Constants.AGILE_SPRINT_MILESTONE;
        }
        return defaultMilestone;
    }

    /**
     * This method would be use for getting Delivery Milestone details using ProjectId.
     * @param milestonesReadOnlyRequestDto is the request of milestonesReadOnlyRequestDto.
     * @param weekEndingDate is the week ending Date.
     * @return this method would be return Delivery Milestone data.
     */
    public DeliveryMilestoneDto getDeliveryMilestoneByProjectId(MilestonesReadOnlyRequestDto milestonesReadOnlyRequestDto, String weekEndingDate) {
        DeliveryMilestoneDto deliveryMilestoneDto = new DeliveryMilestoneDto();
        StringBuilder queryString = new StringBuilder(Queries.GET_DELIVERY_MILESTONES);
        if(weekEndingDate!=null){
            queryString.append(" AND WeekEndingDate = ")
                    .append("'").append(weekEndingDate).append("'");
        }
        Query query = entityManager.createNativeQuery(queryString.toString());
        query.setParameter(1, milestonesReadOnlyRequestDto.getProjectId());
        deliveryMilestoneDto.setTotalCount(query.getResultStream().count());
        if(milestonesReadOnlyRequestDto.getPageNumber()!=-1&&milestonesReadOnlyRequestDto.getPageSize()!=-1) {
            query.setFirstResult((milestonesReadOnlyRequestDto.getPageNumber()) * milestonesReadOnlyRequestDto.getPageSize());
            query.setMaxResults(milestonesReadOnlyRequestDto.getPageSize());
        }
        List<Object[]> queryResponseList = query.getResultList();
        if (CollectionUtils.isEmpty(queryResponseList)) {
            return deliveryMilestoneDto;
        }
        List<DeliveryMilestoneReadOnlyDto> deliveryMilestoneReadOnlyDtoList = new ArrayList<>();
        for (Object[] row : queryResponseList) {
            deliveryMilestoneReadOnlyDtoList.add(MilestonesMapper.mapToDeliveryMilestoneReadOnlyDto(row));
        }
        deliveryMilestoneDto.setDeliveryMilestoneReadOnlyDtoList(deliveryMilestoneReadOnlyDtoList);
        return deliveryMilestoneDto;
    }

    /**
     * This method would be use for getting Agile Sprint Milestone data using ProjectId.
     * @param milestonesReadOnlyRequestDto is the request of milestonesReadOnly.
     * @param weekEndingDate is the week ending Date.
     * @return this method would be return agile Sprint Milestone data.
     */
    public AgileSprintMilestoneDto getAgileSprintMilestoneByProjectId(MilestonesReadOnlyRequestDto milestonesReadOnlyRequestDto, String weekEndingDate) {
        AgileSprintMilestoneDto agileSprintMilestoneDto = new AgileSprintMilestoneDto();
        StringBuilder queryString = new StringBuilder(Queries.GET_AGILE_SPRINT_MILESTONES);
        if(weekEndingDate!=null){
            queryString.append(" AND WeekEndingDate = ")
                    .append("'").append(weekEndingDate).append("'");
        }
        Query query = entityManager.createNativeQuery(queryString.toString());
        query.setParameter(1, milestonesReadOnlyRequestDto.getProjectId());

        agileSprintMilestoneDto.setTotalCount(query.getResultStream().count());
        if(milestonesReadOnlyRequestDto.getPageNumber()!=-1&&milestonesReadOnlyRequestDto.getPageSize()!=-1) {
            query.setFirstResult((milestonesReadOnlyRequestDto.getPageNumber()) * milestonesReadOnlyRequestDto.getPageSize());
            query.setMaxResults(milestonesReadOnlyRequestDto.getPageSize());
        }
        List<Object[]> queryResponseList = query.getResultList();
        if (CollectionUtils.isEmpty(queryResponseList)) {
            return agileSprintMilestoneDto;
        }
        List<AgileSprintMilestoneReadOnlyDto> agileSprintMilestoneReadOnlyDtoList = new ArrayList<>();
        for (Object[] row : queryResponseList) {
            agileSprintMilestoneReadOnlyDtoList.add(MilestonesMapper.mapToAgileSprintMilestoneReadOnlyDto(row));
        }
        agileSprintMilestoneDto.setAgileSprintMilestonesReadOnlyDtoList(agileSprintMilestoneReadOnlyDtoList);
        return agileSprintMilestoneDto;
    }

    /**
     * This Service would be use for saving the Milestones related data.
     * @param milestoneAddDto is the milestone Add data.
     * @throws ActionFailureException exception would be throws if Milestones data not found.
     */
    public void saveMilestones(MilestoneAddDto milestoneAddDto) throws ActionFailureException {
        Project project = createAndEditProjectService.getProjectById(milestoneAddDto.getProjectId());
        MilestoneReadOnlyDto milestoneReadOnlyDto = getMilestoneByProjectId(milestoneAddDto.getProjectId(), null, -1, -1);

        if (!CollectionUtils.isEmpty(milestoneAddDto.getDeliveryMilestoneDto())) {
            DeliveryMilestones deliveryMilestones;
            List<DeliveryMilestoneReadOnlyDto> updatedList = milestoneAddDto.getDeliveryMilestoneDto();
            List<DeliveryMilestoneReadOnlyDto> listFromDb = milestoneReadOnlyDto.getDeliveryMilestoneDto().getDeliveryMilestoneReadOnlyDtoList();

            if (CollectionUtils.isEmpty(listFromDb)) {
                for (DeliveryMilestoneReadOnlyDto deliveryMilestoneReadOnlyDto : updatedList) {
                    Optional<DeliveryMilestoneType> deliveryMilestoneType = deliveryMilestonesTypeRepository.findById(deliveryMilestoneReadOnlyDto.getTypeId());
                    deliveryMilestones = MilestonesMapper.updateDeliveryMilestones(deliveryMilestoneReadOnlyDto, project, deliveryMilestoneType.orElse(null), milestoneAddDto.getWeekEndingDate());
                    deliveryMilestonesRepository.save(deliveryMilestones);
                }
            } else {
                List<DeliveryMilestoneReadOnlyDto> differences = updatedList.stream().filter(fromRequest ->
                        listFromDb.stream().anyMatch(fromDB -> (fromRequest.getId() == null) ||
                                (fromRequest.getId().equals(fromDB.getId()) && !fromRequest.equals(fromDB))))
                        .collect(Collectors.toList());
                for (DeliveryMilestoneReadOnlyDto deliveryMilestoneReadOnlyDto : differences) {
                    Optional<DeliveryMilestoneType> deliveryMilestoneType = deliveryMilestonesTypeRepository.findById(deliveryMilestoneReadOnlyDto.getTypeId());
                    deliveryMilestones = MilestonesMapper.updateDeliveryMilestones(deliveryMilestoneReadOnlyDto, project, deliveryMilestoneType.orElse(null), milestoneAddDto.getWeekEndingDate());
                    deliveryMilestonesRepository.save(deliveryMilestones);
                }
            }
        }
        if (!CollectionUtils.isEmpty(milestoneAddDto.getAgileSprintMilestoneDto())) {
            AgileSprintMilestones agileSprintMilestones;
            List<AgileSprintMilestoneReadOnlyDto> updatedList = milestoneAddDto.getAgileSprintMilestoneDto();
            List<AgileSprintMilestoneReadOnlyDto> listFromDb = milestoneReadOnlyDto.getAgileSprintMilestoneDto().getAgileSprintMilestonesReadOnlyDtoList();
            if (CollectionUtils.isEmpty(listFromDb)) {
                for (AgileSprintMilestoneReadOnlyDto agileSprintMilestoneReadOnlyDto : updatedList) {
                    agileSprintMilestones = MilestonesMapper.updateAgileSprintMilestones(agileSprintMilestoneReadOnlyDto, project, milestoneAddDto.getWeekEndingDate());
                    agileSprintMilestonesRepository.save(agileSprintMilestones);
                }
            } else {
                List<AgileSprintMilestoneReadOnlyDto> differences = updatedList.stream().filter(fromRequest ->
                        listFromDb.stream().anyMatch(fromDB -> (fromRequest.getId() == null) ||
                                (fromRequest.getId().equals(fromDB.getId()) && !fromRequest.equals(fromDB))))
                        .collect(Collectors.toList());
                for (AgileSprintMilestoneReadOnlyDto agileSprintMilestoneReadOnlyDto : differences) {
                    agileSprintMilestones = MilestonesMapper.updateAgileSprintMilestones(agileSprintMilestoneReadOnlyDto, project, milestoneAddDto.getWeekEndingDate());
                    agileSprintMilestonesRepository.save(agileSprintMilestones);
                }
            }
        }
    }

    /**
     * This Service would be use for getting the list of Delivery Milestones related information.
     * @return this method would be return list of DeliveryMilestoneTypeDto.
     */
    public List<DeliveryMilestoneTypeDto> getAllDeliveryMilestoneType() {
        return deliveryMilestonesTypeRepository.findAll().stream()
                .map(deliveryMilestoneType -> new DeliveryMilestoneTypeDto(deliveryMilestoneType.getId(), deliveryMilestoneType.getDeliveryMilestoneTypeName()))
                .collect(Collectors.toList());
    }


}
