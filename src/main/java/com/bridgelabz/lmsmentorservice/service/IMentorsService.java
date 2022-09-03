package com.bridgelabz.lmsmentorservice.service;

import com.bridgelabz.lmsmentorservice.dto.MentorsDTO;
import com.bridgelabz.lmsmentorservice.model.MentorsModel;

import java.util.List;

public interface IMentorsService {
    MentorsModel addMentor(MentorsDTO mentorsDTO);

    MentorsModel addProfilePic(Long id, String profilePic);

    List<MentorsModel> getAllMentors();

    MentorsModel updateMentorDetails(Long id, MentorsDTO mentorsDTO);

    MentorsModel deleteDetails(Long id);

    MentorsModel getMentorsDetailsById(Long id);

    Long mentorsCount();

    Long getMentorByRole(String token);

    Long getAllCount();

}
