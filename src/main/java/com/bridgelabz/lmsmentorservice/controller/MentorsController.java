package com.bridgelabz.lmsmentorservice.controller;

import com.bridgelabz.lmsmentorservice.dto.MentorsDTO;
import com.bridgelabz.lmsmentorservice.model.MentorsModel;
import com.bridgelabz.lmsmentorservice.service.IMentorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mentors")
public class MentorsController {
    @Autowired
    IMentorsService mentorsService;

    /*
     * Purpose : Create Mentor Details
     * @author : Aviligonda Sreenivasulu
     * @Param : mentorDTO and token
     * */
    @PostMapping("/addMentor")
    public MentorsModel addMentor(@Valid @RequestBody MentorsDTO mentorsDTO) {
        return mentorsService.addMentor(mentorsDTO);
    }

    /*
     * Purpose : Add ProfilePic Url to Mentor
     * @author : Aviligonda Sreenivasulu
     * @Param :  token,profilePic and id
     * */
    @PostMapping("/addProfilePicUrl")
    public MentorsModel addProfilePic(@RequestParam String profilePic,
                                      @RequestParam Long id) {
        return mentorsService.addProfilePic(id, profilePic);
    }

    /*
     * Purpose : Retrieve all Metors Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     * */
    @GetMapping("/getAllMentrs")
    public List<MentorsModel> getAllMentors() {
        return mentorsService.getAllMentors();
    }

    /*
     * Purpose : Update Existing Mentor Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  mentorsDTO,token and id
     * */
    @PutMapping("/updateMentorDetails/{id}")
    public MentorsModel updateMentorDetails(@Valid @RequestBody MentorsDTO mentorsDTO,
                                            @PathVariable Long id) {
        return mentorsService.updateMentorDetails(id, mentorsDTO);
    }

    /*
     * Purpose : Delete Existing Mentor Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id and token
     * */
    @DeleteMapping("/deleteMentorDetails/{id}")
    public MentorsModel deleteDetails(@PathVariable Long id) {
        return mentorsService.deleteDetails(id);
    }

    /*
     * Purpose : Retrieve Particular Mentor By Id
     * @author : Aviligonda Sreenivasulu
     * @Param :  id and token
     * */
    @GetMapping("/getMentorById/{id}")
    public MentorsModel getByMentorId(@PathVariable Long id) {
        return mentorsService.getMentorsDetailsById(id);
    }

    /*
     * Purpose : Retrieve All Mentors Count
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     * */
    @GetMapping("/mentorsCont")
    public Long mentorsCount() {
        return mentorsService.mentorsCount();
    }

    /*
     * Purpose : Retrieve Mentor Details By mentorRole
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and mentorRole
     * */
    @GetMapping("/getMentorByRole")
    public Long mentorRole(@RequestParam String mentorRole) {
        return mentorsService.getMentorByRole(mentorRole);
    }

    // Without Query get Count
    @GetMapping("/getAllCount")
    public Long getAllCount() {
        return mentorsService.getAllCount();
    }
}
