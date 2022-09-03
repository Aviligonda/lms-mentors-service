package com.bridgelabz.lmsmentorservice.service;


import com.bridgelabz.lmsmentorservice.dto.MentorsDTO;
import com.bridgelabz.lmsmentorservice.exception.LMSException;
import com.bridgelabz.lmsmentorservice.model.MentorsModel;
import com.bridgelabz.lmsmentorservice.repository.MentorsRepository;
import com.bridgelabz.lmsmentorservice.util.Response;
import com.bridgelabz.lmsmentorservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
 * Purpose : MentorService to Implement the Business Logic
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Service
public class MentorsService implements IMentorsService {
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;
    @Autowired
    MentorsRepository mentorsRepository;
    @Autowired
    RestTemplate restTemplate;

    /*
     * Purpose : Implement the Logic of Creating Mentor Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and mentorsDTO
     * */
    @Override
    public Response addMentor(String token, MentorsDTO mentorsDTO) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8080/admin/validate/" + token, Boolean.class);
        if (isUserPresent) {
            MentorsModel mentorsModel = new MentorsModel(mentorsDTO);
            mentorsModel.setCreatedTimeStamp(LocalDateTime.now());
            mentorsRepository.save(mentorsModel);
            String body = "Mentor Added Successfully With Id is : " + mentorsModel.getId();
            String subject = "Mentor Registration Successfully ...";
            mailService.send(mentorsModel.getEmail(), body, subject);
            return new Response(200, "Success", mentorsModel);
        }
        throw new LMSException(400, "Token Wrong");

    }

    /*
     * Purpose : Implement the Logic of Adding Mentor profilePic
     * @author : Aviligonda Sreenivasulu
     * @Param :  token,id and profilePic
     * */
    @Override
    public Response addProfilePic(String token, Long id, String profilePic) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8080/admin/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<MentorsModel> isMentorPresent = mentorsRepository.findById(id);
            if (isMentorPresent.isPresent()) {
                isMentorPresent.get().setProfilePic(profilePic);
                mentorsRepository.save(isMentorPresent.get());
                String body = "Mentor profilePic Added With Id is : " + isMentorPresent.get().getId();
                String subject = "Mentor ProfilePic Uploaded ...";
                mailService.send(isMentorPresent.get().getEmail(), body, subject);
                return new Response(200, "Success", isMentorPresent.get());
            } else {
                throw new LMSException(400, "Not found with this id");
            }
        }
        throw new LMSException(400, "Token Wrong");

    }

    /*
     * Purpose : Implement the Logic of Get All Mentors Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     * */
    @Override
    public List<MentorsModel> getAllMentors(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8080/admin/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<MentorsModel> isMentors = mentorsRepository.findAll();
            if (isMentors.size() > 0) {
                return isMentors;
            } else {
                throw new LMSException(400, "Mentors not found");
            }
        }
        throw new LMSException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Update Mentor Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token,id and mentorsDTO
     * */
    @Override
    public Response updateMentorDetails(String token, Long id, MentorsDTO mentorsDTO) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8080/admin/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<MentorsModel> isMentorPresent = mentorsRepository.findById(id);
            if (isMentorPresent.isPresent()) {
                isMentorPresent.get().setEmployeeId(mentorsDTO.getEmployeeId());
                isMentorPresent.get().setFirstName(mentorsDTO.getFirstName());
                isMentorPresent.get().setLastName(mentorsDTO.getLastName());
                isMentorPresent.get().setMentorDesc(mentorsDTO.getMentorDesc());
                isMentorPresent.get().setMentorRole(mentorsDTO.getMentorRole());
                isMentorPresent.get().setMentorsType(mentorsDTO.getMentorsType());
                isMentorPresent.get().setEmail(mentorsDTO.getEmail());
                isMentorPresent.get().setStatus(mentorsDTO.getStatus());
                isMentorPresent.get().setExperienceYears(mentorsDTO.getExperienceYears());
                isMentorPresent.get().setMobileNumber(mentorsDTO.getMobileNumber());
                isMentorPresent.get().setStartDate(mentorsDTO.getStartDate());
                isMentorPresent.get().setPreferredTime(mentorsDTO.getPreferredTime());
                isMentorPresent.get().setCreatorUser(mentorsDTO.getCreatorUser());
                isMentorPresent.get().setSupervisorId(mentorsDTO.getSupervisorId());
                isMentorPresent.get().setUpdatedTimeStamp(LocalDateTime.now());
                mentorsRepository.save(isMentorPresent.get());
                String body = "Mentors Details Updated With Id is : " + isMentorPresent.get().getId();
                String subject = "Mentors Details Updated Successfully ...";
                mailService.send(isMentorPresent.get().getEmail(), body, subject);
                return new Response(200, "Success", isMentorPresent.get());
            } else {
                throw new LMSException(400, "Mentors not found");
            }
        }
        throw new LMSException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Delete Mentors Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and id
     * */
    @Override
    public Response deleteDetails(String token, Long id) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8080/admin/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<MentorsModel> isMentor = mentorsRepository.findById(id);
            if (isMentor.isPresent()) {
                mentorsRepository.delete(isMentor.get());
                String body = "Mentor Details Deleted With Id is : " + isMentor.get().getId();
                String subject = "Mentor Details Deleted Successfully ...";
                mailService.send(isMentor.get().getEmail(), body, subject);
                return new Response(200, "Success", isMentor.get());

            } else {
                throw new LMSException(400, "Not found with this id");
            }
        }
        throw new LMSException(400, "Token Wrong");

    }

    /*
     * Purpose : Implement the Logic of Get Mentors Details By I'd
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and bankDetailsDTO
     * */
    @Override
    public Response getMentorsDetailsById(String token, Long id) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8080/admin/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<MentorsModel> isMentor = mentorsRepository.findById(id);
            if (isMentor.isPresent()) {
                return new Response(200, "Success", isMentor);
            } else {
                throw new LMSException(400, "Not found with this id");
            }
        }
        throw new LMSException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Get All Mentors Count
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and bankDetailsDTO
     * */
    @Override
    public Long mentorsCount() {
        Long mentorsCount = mentorsRepository.mentorsCount();
        return mentorsCount;
    }

    /*
     * Purpose : Implement the Logic of Get Mentor Detail By Role
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and bankDetailsDTO
     * */
    @Override
    public Long getMentorByRole(String mentorRole) {
        Long mentorCountByRole = mentorsRepository.mentorsCountByRole(mentorRole);
        return mentorCountByRole;
    }

    //Without Query get count;
    @Override
    public Long getAllCount() {
        long count = mentorsRepository.count();
        if (count > 0) {
            return count;
        } else {
            throw new LMSException(400, "No Data Found");
        }
    }
}
